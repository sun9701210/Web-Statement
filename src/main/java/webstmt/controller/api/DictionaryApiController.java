package webstmt.controller.api;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.service.sys.datasource.DataDictionaryService;
import webstmt.viewmodel.MapResult;

@RestController
@RequestMapping("/apis/vue-element-admin/dictionary")
public class DictionaryApiController {
	
	@Autowired
	private DataDictionaryService service;
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list() {
		
		List<DataDictionary> list = service.findAll();
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", "items", list);
		result.setNode("data", "total", list.size());
		
		return result.getMap();
	}
	
	@GetMapping("{id}")
	@ResponseBody
	public Map<String, Object> get(@PathVariable("id") long id) {
		
		DataDictionary dict = service.read(id);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", dict);
		
		return result.getMap();
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> create(@RequestBody DataDictionary dictionary) {
		
		dictionary.setId(0);
		
		System.out.println(dictionary);
		
		
		DataDictionary newDict = new DataDictionary();
		
		newDict.setCategory(dictionary.getCategory());
		newDict.setDefaultValue(dictionary.getDefaultValue());
		newDict.setDescription(dictionary.getDescription());
		newDict.setFormat(dictionary.getFormat());
		newDict.setName(dictionary.getName());
		newDict.setSource(dictionary.getSource());
		newDict.setType(dictionary.getType());
		
		long newDictId = service.save(newDict);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", newDictId);
		
		return result.getMap();
	}
	
	@PutMapping
	@ResponseBody
	public Map<String, Object> update(@RequestBody DataDictionary dictionary) {
		
		long savedDictId = service.save(dictionary);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", savedDictId==dictionary.getId());
		
		return result.getMap();
	}
	
	@DeleteMapping("{id}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable("id") long id) {
		
		service.remove(id);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		return result.getMap();
	}
}
