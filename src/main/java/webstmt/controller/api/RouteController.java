package webstmt.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/vue-element-admin/routes")
public class RouteController {

	@GetMapping
	@ResponseBody
	public Map<String, Object> routes(HttpServletRequest request) {
		
		System.out.println("Fetching routes");
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> route1 = new HashMap<>();
		route1.put("path", "/icon");
		route1.put("component", "layout/Layout");
		
		List<Map<String, Object>> children = new ArrayList<>();
		
		Map<String, Object> route2 = new HashMap<>();
		route2.put("path", "index");
		route2.put("component", "views/icons/index");
		route2.put("name", "Icons");
		
		Map<String, Object> meta2 = new HashMap<>();
		
		meta2.put("title", "Icons");
		meta2.put("icon", "icons");
		meta2.put("noCache", true);
		
		route2.put("meta", meta2);
		
		children.add(route2);

		route1.put("children", children);
		
		dataList.add(route1);
		
		response.put("code", 20000);
		response.put("msg", "succ");
		response.put("data", dataList);
		
		return response;
	}
}
