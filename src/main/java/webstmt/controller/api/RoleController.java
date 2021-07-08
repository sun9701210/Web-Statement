package webstmt.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/vue-element-admin/roles")
public class RoleController {
	
	@GetMapping
	@ResponseBody
	public Map<String, Object> roles(HttpServletRequest request){
		System.out.println("here in role controller");
		HttpSession session = request.getSession();
		
		String roleId = (String) session.getAttribute("role_id");
		
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> data1 = new HashMap<String, Object>();
		
		data1.put("key", roleId);
		data1.put("name", roleId);
		data1.put("description", "Testing role");
		
		List<Map<String, Object>> routes = new ArrayList<>();
		
		Map<String, Object> route1 = new HashMap<>();
		route1.put("path", "");
		route1.put("redirect", "dashboard");
		
		List<Map<String, Object>> children = new ArrayList<>();
		
		Map<String, Object> route2 = new HashMap<>();
		route2.put("path", "dashboard");
		route2.put("name", "Dashboard");
		
		Map<String, Object> meta2 = new HashMap<>();
		
		meta2.put("title", "dashboard");
		meta2.put("icon", "dashboard");
		
		route2.put("meta", meta2);
		
		children.add(route2);

		route1.put("children", children);
		
		routes.add(route1);
		data1.put("routes", routes.toArray());
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		dataList.add(data1);
		
		response.put("code", 20000);
		response.put("msg", "succ");
		response.put("data", dataList);
		
		return response;
	}

}
