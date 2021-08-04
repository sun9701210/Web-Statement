package webstmt.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import webstmt.entity.sys.Role;
import webstmt.service.sys.RoleService;
import webstmt.viewmodel.MapResult;

@RestController
@RequestMapping("/apis/vue-element-admin/role")
public class RoleApiController {
	
	@Autowired
	private RoleService service;
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> roles(HttpServletRequest request){
		System.out.println("here in role controller");

		List<Role> allRoles = service.list();
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", "items", allRoles);
		result.setNode("data", "total", allRoles.size());
		
		return result.getMap();
	}

	@PostMapping
	@ResponseBody
	public Map<String, Object> create(@RequestBody Role role) {
		
		Role createdObj = service.saveOrUpdate(role);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		result.setNode("data", createdObj);
		
		return result.getMap();
	}
	
	@PutMapping
	@ResponseBody
	public Map<String, Object> update(@RequestBody Role role) {
		
		Role updatedObj = service.saveOrUpdate(role);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		result.setNode("data", updatedObj);
		
		return result.getMap();
	}
	
	@DeleteMapping("{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") long id) {
		
		System.out.println("Removing Role Id --> "+id);
		
		service.delete(id);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", id);
		
		return result.getMap();
	}
}
