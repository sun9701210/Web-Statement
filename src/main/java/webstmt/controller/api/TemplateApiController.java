package webstmt.controller.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webstmt.entity.sys.Template;
import webstmt.entity.sys.TemplateFolder;
import webstmt.entity.sys.datasource.DataBinding;
import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.repo.sys.TemplateQueryCriteria;
import webstmt.service.sys.TemplateFolderService;
import webstmt.service.sys.TemplateService;
import webstmt.utils.HtmlUtil;
import webstmt.utils.ImageUtils;
import webstmt.utils.PDFUtils;
import webstmt.utils.PathUtil;
import webstmt.viewmodel.TemplateDto;

@RestController
@RequestMapping("/apis/vue-element-admin/template")
public class TemplateApiController {
	
	private static Logger log = LoggerFactory.getLogger(TemplateApiController.class);
	
	@Autowired
	private TemplateService service;
	
	@Autowired
	private TemplateFolderService folderService;
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> create(@RequestBody TemplateDto dto) {
		
		System.out.println(dto);
		
		Template newTemplate = new Template();
		newTemplate.setName(dto.getName());
		newTemplate.setContent("");
		newTemplate.setDescription(dto.getDescription());
		newTemplate.setOppm(dto.getOppm());
		newTemplate.setMarket(dto.getMarket());
		newTemplate.setStatus(dto.getStatus());
		newTemplate.setAuthor(dto.getAuthor());
		newTemplate.setPriority(dto.getPriority());
		newTemplate.setLastUpdatedBy(dto.getLastUpdatedBy());
		newTemplate.setReleaseDate(dto.getReleaseDate());
		newTemplate.setLastUpdatedTime(new Date());
		
//		long typeId = dto.getFolder();
//		TemplateFolder folder = folderService.findById(typeId);
		
		newTemplate.setFolder(dto.getFolder());
		
		long templateId = service.save(newTemplate);
		
		Map<String, Object> response = new HashMap<>();
		
		response.put("code", 20000);
		response.put("msg", "succ");
		response.put("data", templateId);
		
		return response;
	}
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(defaultValue="1") int page, 
										@RequestParam(defaultValue="10")int limit,
										@RequestParam(name="name", required=false) String nameWildcard,
										@RequestParam(name="priority", required=false) Integer priority,
										@RequestParam(defaultValue="All")String market,
										@RequestParam(defaultValue="-id") String sort,
										@RequestParam(name="typeId",required=false) Long folderId){
	
		System.out.format("Requesting page/limit/name/priority/market/sort/folderId -> %s/%s/%s/%s/%s/%s/%s", page, limit, nameWildcard, priority, market, sort, folderId);
		
		int offsetPage = page > 0 ? page-1 : 0;
		
		TemplateQueryCriteria queryCriteria = new TemplateQueryCriteria();
		queryCriteria.setIdSort(sort);
		
		if(!"All".equalsIgnoreCase(market)) {

			queryCriteria.setMarket(market);
		}
		
		queryCriteria.setNameWildcard(nameWildcard);
		queryCriteria.setPriority(priority);
		queryCriteria.setTypeId(folderId);

		List<Template> templates = service.readPage(queryCriteria, offsetPage, limit);
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		Map<String, Object> responseData = new HashMap<>();
		
		long total = service.count(queryCriteria);
		responseData.put("total", total);
		responseData.put("items", templates);
		
		response.put("code", 20000);
		response.put("data", responseData);
		
		return response;
	}
	
	@GetMapping("{id}")
	@ResponseBody
	public Map<String, Object> get(@PathVariable("id") long id) throws ParseException {
		
		System.out.println("Retrieving template -> "+id);

		Template template = service.read(id);
		
		Template jsonTpl = new Template();
		jsonTpl.setId(template.getId());
		jsonTpl.setName(template.getName());
		jsonTpl.setDescription(template.getDescription());
		jsonTpl.setContent(template.getContent());
		jsonTpl.setMarket(template.getMarket());
		jsonTpl.setOppm(template.getOppm());
		jsonTpl.setStatus(template.getStatus());
		jsonTpl.setAuthor(template.getAuthor());
		jsonTpl.setReviewer(template.getReviewer());
		jsonTpl.setPriority(template.getPriority());
		jsonTpl.setLastUpdatedBy(template.getLastUpdatedBy());
		jsonTpl.setLastUpdatedTime(template.getLastUpdatedTime());
		jsonTpl.setReleaseDate(template.getReleaseDate());
		jsonTpl.setFolder(template.getFolder());
		jsonTpl.setDataBindings(template.getDataBindings());
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("code", 20000);
		response.put("data", jsonTpl);
		
		return response;
		
	}
	
	@PutMapping
	@ResponseBody
	public Map<String, Object> update(@RequestBody TemplateDto dto) throws ParseException {
		
		System.out.println(dto);
		
		Template updatedTemplate = service.read(dto.getId());
		updatedTemplate.setName(dto.getName());
		updatedTemplate.setMarket(dto.getMarket());
		updatedTemplate.setOppm(dto.getOppm());
		updatedTemplate.setReviewer(dto.getReviewer());
		updatedTemplate.setReleaseDate(dto.getReleaseDate());
		updatedTemplate.setPriority(dto.getPriority());
		updatedTemplate.setDescription(dto.getDescription());
		
//		long typeId = dto.getFolder();
//		
//		TemplateFolder updatedFolder = folderService.findById(typeId);
//		
		System.out.println(dto.getFolder());
		
		updatedTemplate.setFolder(dto.getFolder());
		
		long id = service.save(updatedTemplate);
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("code", 20000);
		response.put("success", id == dto.getId());
		
		return response;
	}
	
	@PutMapping("/editor")
	@ResponseBody
	public Map<String, Object> updateEditor(@RequestBody Template savedTemplate) throws ParseException {
		
		System.out.println(savedTemplate);
		
		Template temp = service.read(savedTemplate.getId());
		temp.setReviewer(savedTemplate.getReviewer());
		temp.setReleaseDate(savedTemplate.getReleaseDate());
		temp.setPriority(savedTemplate.getPriority());
		temp.setDescription(savedTemplate.getDescription());
		temp.setContent(savedTemplate.getContent());
		
		long id = service.save(temp);
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("code", 20000);
		response.put("success", id == savedTemplate.getId());
		
		return response;
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		service.remove(id);
		
		response.put("code", 20000);
		response.put("data", id);
		
		return response;
	}

	@Autowired
	private HtmlUtil htmlUtil;
	
	@GetMapping("download/{id}")
	public void download(HttpServletResponse response, @PathVariable("id") long id) {
		
		String custNo = "C123456";
		Template t = service.read(id);
		
		log.info("Cust# - "+custNo);
		log.info("Raw HTML Content:");
		log.info(t.getContent());
		
		Map<String, String> params = new HashMap<>();
		String html = render(t, params);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		html = replaceAllImages(html, auth.getName());
		html = htmlUtil.wrapContainer(html);
		
		log.debug("Wrapped HTML Content:");
		log.debug(html);
		
		PDFUtils util = new PDFUtils();
		ByteArrayOutputStream output = util.html2Pdf(html);
		
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setHeader("Content-Disposition", "attachment;filename=advice-"+t.getFolder().getName()+"-"+custNo+".pdf");
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
	
	private String replaceAllImages(String rawHtml, String loginUsername)
	{	
		List<String> imageStrings = HtmlUtil.extractAllImages(rawHtml);
		
		Map<String, String> rawNewImagesMap = new HashMap<>();
		
		for (String rawImageHtml : imageStrings) 
		{
			String newImageHtml = replaceImageByBase64(StringUtils.uriDecode(rawImageHtml, Charset.forName("UTF-8")));
			
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
	
	@Value("${user.upload.root}")
	private String userUploadPhysicalPath;
	
	private String replaceImageByBase64(String rawImgPath, String loginUsername)
	{
		String imagePhysicalPath = PathUtil.convertToPhysicalPath(rawImgPath, userUploadPhysicalPath, loginUsername);
		log.info("Reading image - "+imagePhysicalPath);
		
		return ImageUtils.convertImageBytesToBase64String(imagePhysicalPath);
	}
	
	@Value("${user.upload.tmp}")
	private String imageTempPath;
	
	private String replaceImageByBase64(String rawImgPath)
	{
		String imagePhysicalPath = PathUtil.convertToPhysicalPath(rawImgPath, imageTempPath);
		log.info("Reading image - "+imagePhysicalPath);
		
		return ImageUtils.convertImageBytesToBase64String(imagePhysicalPath);
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
}
