package webstmt.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import webstmt.entity.sys.TemplateFolder;
import webstmt.service.sys.TemplateFolderService;

@RestController
@RequestMapping("/apis/vue-element-admin/template-folder")
public class TemplateFolderApiController {

	@Autowired
	TemplateFolderService service;
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(name="market", required=false) String market) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("code", 20000);
		
		List<TemplateFolder> folders = new ArrayList<TemplateFolder>();
		
		if("all".equalsIgnoreCase(market)) {
		
			folders.addAll(service.findAllActive());
			
		} else {
			
			folders.addAll(service.findAllActiveByMarket(market));
		}
		
		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put("items", folders);
		responseData.put("total", folders.size());
		
		response.put("data", responseData);
		
		return response;
	}
	
	@PutMapping
	@ResponseBody
	public Map<String, Object> update(@RequestBody TemplateFolder folder) {
		
		System.out.println(folder);
		
		service.update(folder);
		
		Map<String, Object> response = new HashMap<>();;
		
		response.put("code", 20000);
		response.put("msg", "succ");
		
		return response;
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> create(@RequestBody TemplateFolder folder) {
		
		System.out.println(folder);
		folder.setActive(true);
		
		TemplateFolder newFolder = service.create(folder);
		
		Map<String, Object> response = new HashMap<>();;
		
		response.put("code", 20000);
		response.put("data", newFolder);
		
		return response;
	}
	
	@DeleteMapping("{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") long id) {
		
		System.out.println("Deleting Template Folder --> "+id);
		
		service.remove(id);
		
		Map<String, Object> response = new HashMap<>();;
		
		response.put("code", 20000);
		response.put("msg", "succ");
		
		return response;
	}
}
