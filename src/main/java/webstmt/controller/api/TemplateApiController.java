package webstmt.controller.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webstmt.entity.sys.Template;
import webstmt.entity.sys.TemplateFolder;
import webstmt.service.sys.TemplateService;

@RestController
@RequestMapping("/apis/vue-element-admin/template")
public class TemplateApiController {
	
	private static Logger log = LoggerFactory.getLogger(TemplateApiController.class);
	
	@Autowired
	private TemplateService service;
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10")int limit){
	
		System.out.println("Requesting page/limit -> "+page + "/" + limit);
		
		List<Template> templates = service.readAll();
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put("total", templates.size());
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

}
