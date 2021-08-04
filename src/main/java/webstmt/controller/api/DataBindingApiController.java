package webstmt.controller.api;

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

import webstmt.entity.sys.Template;
import webstmt.entity.sys.datasource.DataBinding;
import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.service.sys.TemplateService;
import webstmt.service.sys.datasource.DataBindingService;
import webstmt.service.sys.datasource.DataDictionaryService;
import webstmt.viewmodel.DataBindingForm;
import webstmt.viewmodel.MapResult;

@RestController
@RequestMapping("/apis/vue-element-admin/data-binding")
public class DataBindingApiController {
	
	@Autowired
	private DataBindingService service;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private DataDictionaryService dictionaryService;
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(name="templateId",required=false) Long templateId) {
		
		List<DataBinding> list = null;
		
		if(templateId !=null) {
			list = service.getDataBindingByTemplateId(templateId);
		} else {
			list = service.getAllDataBindingList();
		}
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", "items", list);
		result.setNode("data", "total", list.size());
		
		return result.getMap();
	}

	@GetMapping("{id}")
	@ResponseBody
	public Map<String, Object> get(@PathVariable("id") long id) {
		
		DataBinding db = service.getDataBindingById(id);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", db);
		
		return result.getMap();
	}
	
	@DeleteMapping("{id}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable("id") long id) {
		
		service.delete(id);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", id);
		
		return result.getMap();
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> create(@RequestBody DataBindingForm form) {
	
		System.out.println(form);
		
		Template template = templateService.read(form.getTemplateId());
		DataDictionary dictionary = dictionaryService.read(form.getDictionaryId());
		
		DataBinding binding = new DataBinding();
		
		binding.setTemplate(template);
		binding.setDictionary(dictionary);
		binding.setPlaceholder(form.getPlaceholder());
		
		binding.setBindingType("single");
		binding.setProcessorClassName("");
		
		DataBinding createdObj =  service.createDataBinding(binding);
		
		template.getDataBindings().add(createdObj);
		
		templateService.save(template);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", createdObj.getId());
		
		return result.getMap();
	}
	
	@PutMapping
	@ResponseBody
	public Map<String, Object> update(@RequestBody DataBindingForm form) {
		
		DataBinding binding = service.getDataBindingById(form.getId());
		
		DataDictionary newDictionary = dictionaryService.read(form.getDictionaryId());
		
		binding.setDictionary(newDictionary);
		binding.setPlaceholder(form.getPlaceholder());
		
		DataBinding updatedObj = service.updateDataBinding(binding);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", updatedObj);
		
		return result.getMap();
	}
}
