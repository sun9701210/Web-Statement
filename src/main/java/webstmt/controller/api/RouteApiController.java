package webstmt.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import webstmt.entity.sys.Route;
import webstmt.entity.sys.RouteMeta;
import webstmt.service.sys.RouteService;
import webstmt.viewmodel.MapResult;

@RestController
@RequestMapping("/apis/vue-element-admin/route")
public class RouteApiController {

	@Autowired
	private RouteService service;
	
	@GetMapping("routes")
	@ResponseBody
	public Map<String, Object> routes() {
		
		System.out.println("Fetching hierachical routes");
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		List<Route> routeTree = service.tree();
		
		result.setNode("data", "items", routeTree);
		result.setNode("data", "total", routeTree.size());
		
		return result.getMap();
	}
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		
		System.out.println("Fetching routes");
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		List<Route> routes = service.allRoutes();
		
		result.setNode("data", "items", routes);
		result.setNode("data", "total", routes.size());
		
		return result.getMap();
	}
	
	@GetMapping("{id}")
	@ResponseBody
	public Map<String, Object> get(@PathVariable("id") long id) {
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		Route route = service.get(id);
		
		result.setNode("data", route);
		
		return result.getMap();
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> create(@RequestBody Route route, @RequestParam(name = "pid", required = false) Long parentId) {
		
		System.out.println("Parent ID => "+parentId);
		System.out.println(route);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		if(route.getMeta()==null) {
			route.setMeta(new RouteMeta());
		}
		
		Route savedObj = service.saveOrUpdate(route);
		
		//TODO need to build children manually
		
//		if(parentId != null) {
//			
//			service.addToParent(parentId, savedObj);
//		}
		
		result.setNode("data", savedObj);
		
		return result.getMap();
	}
	
	@PutMapping
	@ResponseBody
	public Map<String, Object> update(@RequestBody Route route, @RequestParam(name = "pid", required = false) Long parentId) {
		
		System.out.println("Parent => "+parentId);
		System.out.println(route);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
//		if(parentId == null) {
//			route.setParent(null);
//		}
		
		Route updatedObj = service.saveOrUpdate(route);

//		if(updatedObj.getParent() != null) {
//			service.addToParent(updatedObj.getParent().getId(), updatedObj);
//		}
		result.setNode("data", updatedObj);
		
		return result.getMap();
	}
	
	@DeleteMapping("{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") long id) {
		
		Route deletingRoute = service.get(id);
		
		System.out.println(deletingRoute);
		
		//TODO need to add check method to check used parent
//		if(deletingRoute.getChildren().size()>0) {
//			MapResult result = MapResult.newInstance(50000, "Please remove sub menus first.");
//			return result.getMap();
//		}
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		service.delete(id);
		
		result.setNode("data", id);
		
		return result.getMap();
	}
}
