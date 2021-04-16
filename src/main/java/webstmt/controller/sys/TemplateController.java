package webstmt.controller.sys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cksource.ckfinder.utils.PathUtils;
import com.google.gson.Gson;

import webstmt.entity.sys.Template;
import webstmt.entity.sys.TemplateFolder;
import webstmt.entity.sys.TemplateVersion;
import webstmt.entity.sys.datasource.DataBinding;
import webstmt.entity.sys.datasource.DataBindingString;
import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.entity.sys.datasource.DataType;
import webstmt.entity.sys.datasource.DatabaseDataSource;
import webstmt.entity.sys.datasource.DictionaryCategory;
import webstmt.entity.sys.datasource.LegalVehicle;
import webstmt.entity.sys.datasource.SystemID;
import webstmt.service.sys.TemplateFolderService;
import webstmt.service.sys.TemplateService;
import webstmt.service.sys.TemplateVersionService;
import webstmt.service.sys.datasource.DataBindingService;
import webstmt.service.sys.datasource.DataDictionaryService;
import webstmt.utils.HtmlUtil;
import webstmt.utils.ImageUtils;
import webstmt.utils.PDFUtils;
import webstmt.utils.PathUtil;

@Controller
@RequestMapping("/dashboard/template")
public class TemplateController 
{
	private static Logger log = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateService service;
	
	@Autowired
	private TemplateFolderService folderService;
	
	@Autowired
	private DataBindingService dataBindingService;
	
	@Autowired
	private DataDictionaryService dictionaryService;
	
	@Value("${user.upload.root}")
	private String userUploadPhysicalPath;
	
	private String replaceImageByBase64(String rawImgPath, String loginUsername)
	{
		String imagePhysicalPath = PathUtil.convertToPhysicalPath(rawImgPath, userUploadPhysicalPath, loginUsername);
		log.info("Reading image - "+imagePhysicalPath);
		
		return ImageUtils.convertImageBytesToBase64String(imagePhysicalPath);
	}
	
	private String replaceAllImages(String rawHtml, String loginUsername)
	{	
		List<String> imageStrings = HtmlUtil.extractAllImages(rawHtml);
		
		Map<String, String> rawNewImagesMap = new HashMap<>();
		
		for (String rawImageHtml : imageStrings) 
		{
			String newImageHtml = replaceImageByBase64(StringUtils.uriDecode(rawImageHtml, Charset.forName("UTF-8")), loginUsername);
			
			rawNewImagesMap.put(rawImageHtml, newImageHtml);
		}
		
		String replacedHtml = rawHtml;
		
		for (Entry<String, String> oldNewImgTextPair : rawNewImagesMap.entrySet())
		{
			String preHtml = replacedHtml.substring(0, replacedHtml.indexOf(oldNewImgTextPair.getKey()));
			String postHtml = replacedHtml.substring(replacedHtml.indexOf(oldNewImgTextPair.getKey())+oldNewImgTextPair.getKey().length());
			
			replacedHtml = preHtml + oldNewImgTextPair.getValue() + postHtml;
		}
		
		return replacedHtml;
	}
	
	@Autowired
	private HtmlUtil htmlUtil;
	
	@RequestMapping("generate/{id}")
	public void generateStatement(HttpServletResponse response, @PathVariable("id") long templateId, @RequestParam(value="custNo", required=false) String custNo)
	{
		Template t = service.read(templateId);
		
		log.info("Cust# - "+custNo);
		log.info("Raw HTML Content:");
		log.info(t.getContent());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String html = replaceAllImages(t.getContent(), auth.getName());
		html = htmlUtil.wrapContainer(html);
		
		log.debug("Wrapped HTML Content:");
		log.debug(html);
		
		PDFUtils util = new PDFUtils();
		ByteArrayOutputStream output = util.html2Pdf(html);
		
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setHeader("Content-Disposition", "attachment;filename=statement.pdf");
		response.setContentType("application/pdf");
		
		try {
			
			OutputStream os = response.getOutputStream();
			output.writeTo(os);
			os.flush();
			os.close();
			
		} catch (IOException e) {
			log.error("Fail to write pdf stream to response.", e);
		}
	}
	
	@GetMapping(value="/new")
	public String newTemplate(Model model)
	{
		model.addAttribute("template", new Template());
		model.addAttribute("allFolders", folderService.findAllActive());
		model.addAttribute("allLegalVehicles", LegalVehicle.values());
		
		return "dashboard/template-new";
	}
	
	@PostMapping(value="/save")
	public String saveTemplate(@ModelAttribute Template template, Model model)
	{
		System.out.println("Template: "+template.getContent());
		
		Template tpl = new Template();
		tpl.setName(template.getName());
		tpl.setDescription(template.getDescription());
		
		log.info("Removing ckeditor line break...");
		String html = template.getContent();
		html = htmlUtil.removeHtmlSegment(html, "<p><br data-cke-filler=\"true\"></p>");
		tpl.setContent(html);
		
		tpl.setOppm(template.getOppm());
		tpl.setLegalVehicle(template.getLegalVehicle());
		
		long folderId = template.getFolder().getId();
		TemplateFolder folder = folderService.findById(folderId);
		
		if(folder!=null)
		{
			tpl.setFolder(folder);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		tpl.setLastUpdatedBy(auth.getName());
		
		tpl.setLastUpdatedTime(new Date());
		
		long id = service.save(tpl);
		
		if(id>0)
		{
			model.addAttribute("succ", true);
			model.addAttribute("result", "Save Successfully.");
		}
		else
		{
			model.addAttribute("succ", false);
			model.addAttribute("result", "Failed to Save.");
		}
		
		model.addAttribute("templateId", id);
		
		return "dashboard/template-save-result";
	}
	
	@GetMapping(value="/edit/{id}")
	public String showEdit(@PathVariable("id") long id, Model model)
	{
		Template template = service.read(id);
		
		if(template==null)
		{
			model.addAttribute("exist", false);
		}
		else
		{
			model.addAttribute("exist", true);
			model.addAttribute("template", template);
			model.addAttribute("allCategories", DictionaryCategory.values());
			model.addAttribute("allDataTypes", DataType.values());
			model.addAttribute("allSystemIds", SystemID.values());
			model.addAttribute("allDataDictionary", dictionaryService.findAll());
			model.addAttribute("allActiveFolders", folderService.findAllActive());
			model.addAttribute("allLegalVehicles", LegalVehicle.values());
			
//			model.addAttribute("dataBindingSize", template.getDataBindings().size());
		}
		
		return "dashboard/template-edit";
	}
	
	@PostMapping(value="/edit")
	public String editTemplate(@ModelAttribute Template template, Model model)
	{
		System.out.println("Hello");
		
		System.out.println("Template: "+template.getContent());
		
		Template tpl = service.read(template.getId());

		TemplateVersion newVersion = new TemplateVersion();
		newVersion.setImage(tpl.getContent());
		newVersion.setSavedTime(new Date());
		newVersion.setUUID(TemplateVersionService.generateUUID());
		newVersion.setTemplate(tpl);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		newVersion.setMaker(auth.getName());
		
		if(tpl.getVersions()!=null)
		{
			tpl.getVersions().add(newVersion);
		}
		
		tpl.setName(template.getName());
		tpl.setDescription(template.getDescription());
		
		log.info("Removing ckeditor line break...");
		String html = template.getContent();
		html = htmlUtil.removeHtmlSegment(html, "<p><br data-cke-filler=\"true\"></p>");//&lt;p&gt;&lt;br data-cke-filler=&quot;true&quot;&gt;&lt;/p&gt;
		tpl.setContent(html);
		
		tpl.setOppm(template.getOppm());
		tpl.setLastUpdatedBy(auth.getName());
		tpl.setLastUpdatedTime(new Date());
		tpl.setLegalVehicle(template.getLegalVehicle());
		
		//change folder
		long newFolderId = template.getFolder().getId();
		System.out.println("New Folder ID: "+newFolderId);
		if(tpl.getFolder()==null||tpl.getFolder().getId()!=newFolderId)
		{
			TemplateFolder newFolder = folderService.findById(newFolderId);
			tpl.setFolder(newFolder);
		}
		
		List<DataBinding> oldBindingList = dataBindingService.getDataBindingByTemplateId(tpl.getId());
		List<DataBinding> newBindingList = new ArrayList<>();
		//change data bindings
		if(template.getDataBindings()==null)
		{
			System.out.println("No data binding send back.");
			tpl.setDataBindings(null);
		}
		else if(template.getDataBindings().size()==0)
		{
			System.out.println("Zero data binding send back.");
			tpl.getDataBindings().clear();
		}
		else
		{
			Map<Long, DataBinding> oldBindingMap = new HashMap<>();
			for (DataBinding oldBinding : oldBindingList) {
				oldBindingMap.put(oldBinding.getId(), oldBinding);
			}
			
			for (DataBinding binding : template.getDataBindings()) {
				System.out.println(binding.getId());
				System.out.println(binding.getPlaceholder());
				System.out.println(binding.getDictionary());

				if(binding.getPlaceholder()==null||binding.getDictionary()==null)
				{
					//skip empty data
					System.out.println("Placeholder or Dictionary is null. Nothing will happen.");
				}
				else if(oldBindingMap.containsKey(binding.getId()))
				{
					System.out.println("Such Data Binding is existing. Will change placeholder and dictionary.");
					//keeping binding
					
					DataBinding existingDataBinding = oldBindingMap.get(binding.getId());
					existingDataBinding.setPlaceholder(binding.getPlaceholder());
					existingDataBinding.setDictionary(binding.getDictionary());
					
					newBindingList.add(existingDataBinding);
				}
				else
				{
					System.out.println("Such Data Binding is new. Will create new one.");
					//not in db, new added binding
					//TODO set as constant single, support more type in the future
					binding.setBindingType("single");
					//TODO set empty, support page selection in the future
					binding.setProcessorClassName("");
					
					binding.setTemplate(template);
					
					newBindingList.add(binding);
				}
			}
			
			tpl.setDataBindings(newBindingList);
		}
		
		
		long savedId = service.save(tpl);
		
		for (DataBinding oldBinding : oldBindingList) 
		{
			if(!newBindingList.contains(oldBinding))
			{
				dataBindingService.delete(oldBinding.getId());
			}
		}
		
		if(savedId>0)
		{
			model.addAttribute("succ", true);
			model.addAttribute("result", "Save Successfully.");
		}
		else
		{
			model.addAttribute("succ", false);
			model.addAttribute("result", "Failed to Save.");
		}

		model.addAttribute("templateId", savedId);
		
		return "dashboard/template-edit-result";
	}
	
	@RequestMapping(value="", method= RequestMethod.GET)
	public String viewAllTemplates(Model model, Authentication auth)
	{
		List<Template> templates = service.readAll();
		
		model.addAttribute("templates", templates);
		
		//TODO put legal vehicle part of role into response
		
		List<String> legalVehicleAuthorityList = new ArrayList<>();
		
		for (GrantedAuthority authority : auth.getAuthorities()) {
			
			if(authority!=null&&!"".equalsIgnoreCase(authority.getAuthority()))
			{
				if("ROLE_ADMIN".equalsIgnoreCase(authority.getAuthority()))
				{
					legalVehicleAuthorityList.add("ADMIN");
				}
				
				if(authority.getAuthority().startsWith("ROLE_BIZ_"))
				{
					legalVehicleAuthorityList.add(authority.getAuthority().substring("ROLE_BIZ_".length()));
				}
			}
		}
		
		System.out.println(legalVehicleAuthorityList);
		
		model.addAttribute("legalVehiclesAuth", legalVehicleAuthorityList);
		//TODO let thymeleaf to judge display buttons
		
		return "dashboard/template-view-all";
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.GET, produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String readTemplate(@PathVariable("id") long id)
	{
		Template template = service.read(id);
		
		Template jsonTpl = new Template();
		jsonTpl.setId(template.getId());
		jsonTpl.setName(template.getName());
		jsonTpl.setDescription(template.getDescription());
		jsonTpl.setContent(template.getContent());
		jsonTpl.setLegalVehicle(template.getLegalVehicle());
		
		Gson gson = new Gson();
		
		String resp = gson.toJson(jsonTpl);
		
		return resp;
	}
	
	@DeleteMapping(value="/remove")
	@ResponseBody
	public String removeTemplate(Long id){
		
		service.remove(id);
		
		return "dashboard/template-view-all";
	}
	
	
	
	@GetMapping(value="/search")
	@ResponseBody
	public List<Template> searchTemplate(String templateName){
		
		List<Template> template=service.search(templateName);		
		
		
		return template;
	}
	
	@GetMapping(value="/find")
	@ResponseBody
	public List<Template> findByFolderId(String folderName){
		
		Long folderId=folderService.getByName(folderName);
		
		List<Template> template=service.find(folderId);		
		
		return template;
	}
		
	
	@GetMapping(value="/oppm")
	@ResponseBody
	public List<Template> searchOppm(Long oppm){
		
		List<Template> template=service.oppmSearch(oppm);
		
		return template;
		
	}
	
	@GetMapping(value="/preview/{id}")
	public String preview(@PathVariable("id")long templateId, Model model)
	{
		Template template = service.read(templateId);
		
		System.out.println(template);
		
		//TODO render by dictionary default data
		Map<String, String> params = new HashMap<>();
		
		String renderResult = render(template, params);
		
		model.addAttribute("render", renderResult);
		model.addAttribute("template", template);
		
		//verify permission
		String templateLegalVehicle = template.getLegalVehicle();
		String templateRequiredRole = "ROLE_BIZ_"+templateLegalVehicle;
		
		boolean authorizedUser = false;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		for (GrantedAuthority authority : auth.getAuthorities()) 
		{
			String authorityText = authority.getAuthority();
			
			if(authorityText.equalsIgnoreCase("ROLE_ADMIN")||authorityText.equalsIgnoreCase(templateRequiredRole))
			{
				authorizedUser = true;
				break;
			}
		}
		
		model.addAttribute("authorized", authorizedUser);
		
		return "dashboard/template-preview";
	}
	
	private String render(Template template, Map<String, String> queryParams)
	{
		List<DataBinding> dataBindings=template.getDataBindings();
		
		String renderResult = template.getContent();
		
		for (DataBinding binding : dataBindings) 
		{
			
			System.out.println(binding);
			
			String placeholder = binding.getPlaceholder();
			
//			DataBindingString bindingStr = new DataBindingString(binding.getBindingString());
			
			switch (binding.getBindingType().toLowerCase())
			{
				case DataBinding.DATA_BINDING_TYPE_SINGLE_VALUE:
//					DatabaseDataSource dbSrc = binding.getDbDatasource();
//					String value = fetchDatabaseData(dbSrc, bindingStr, queryParams);
//					
					
					DataDictionary dictionary = binding.getDictionary();
					
					renderResult = renderResult.replace(placeholder, dictionary.getDefaultValue());
					break;
				case DataBinding.DATA_BINDING_TYPE_LOOP_VALUE:
					throw new RuntimeException("Not implemented yet.");
//					break;

				default: throw new IllegalArgumentException("Unsupported Data Binding Type: "+binding.getBindingType().toLowerCase());
			}
		}
		
		return renderResult;
	}
	
	@PersistenceContext
	EntityManager entityManager;
	
	private String fetchDatabaseData(DatabaseDataSource dbSrc, DataBindingString bindingStr, Map<String, String> queryParams)
	{
//		binding.get
		//TODO run native sql by entity manager
		
		System.out.println(bindingStr);
		
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append("select ");
		
		if(bindingStr.isBoundSingleValue())
		{
			sqlBuilder.append(bindingStr.getColumnName());
		}
		else 
		{
			sqlBuilder.append("*");
		}
		
		sqlBuilder.append(" from ");
		sqlBuilder.append(bindingStr.getTableName());
		
		if(bindingStr.isBoundSingleValue())
		{
			sqlBuilder.append(" where ");
			
			String[] keys = dbSrc.getKeyList().split(",");
			
			for (int i = 0; i < keys.length; i++) 
			{
				String colName = keys[i];
				
				String value = queryParams.get(colName);
				
				sqlBuilder.append(colName);
				sqlBuilder.append("='");
				sqlBuilder.append(value);
				sqlBuilder.append("'");
				
				if((i+1)!=keys.length)
				{
					sqlBuilder.append(" and ");
				}
			}
			
//			sqlBuilder.append(";");
		}
		
		String sql = sqlBuilder.toString();
		
		System.out.println(sql);
		
		Query query = entityManager.createNativeQuery(sql);
		
		Object result = query.getSingleResult();
		
		
		return result.toString();
	}
	
	private Connection getConnectionByDatabaseName(String dbName)
	{
		throw new RuntimeException("Not implemented yet. Here need a connection pool.");
	}
	
	
}
