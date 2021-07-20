package webstmt.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webstmt.entity.sys.datasource.DataBinding;
import webstmt.service.sys.datasource.DataBindingService;
import webstmt.viewmodel.MapResult;

@RestController
@RequestMapping("/apis/vue-element-admin/data-binding")
public class DataBindingApiController {
	
	@Autowired
	private DataBindingService service;
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list() {
		
		List<DataBinding> list = service.getAllDataBindingList();
		
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
}
