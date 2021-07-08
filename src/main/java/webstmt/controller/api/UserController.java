package webstmt.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webstmt.entity.sys.SysUser;
import webstmt.service.sys.SysUserService;
import webstmt.viewmodel.LoginDto;
import webstmt.viewmodel.LoginUser;
import webstmt.viewmodel.Result;

@RestController
@RequestMapping("/apis/vue-element-admin/user")
public class UserController {
	
	@Autowired
	private SysUserService userService;
	
	@GetMapping("search")
	@ResponseBody
	public Map<String, Object> search(@RequestParam("name") String name) {

		System.out.println("Search user name => "+name);
		
		List<SysUser> list = userService.searchUser(name);
		
		List<String> nameList = new ArrayList<String>();
		
		for (SysUser user : list) {
			nameList.add(user.getUsername());
		}
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("code", 20000);
		response.put("msg", "Succ");
		response.put("data", nameList);
		
		return response;
	}
	
	@PostMapping("login")
	@ResponseBody
	public Map<String, Object> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
		
		LoginUser user = new LoginUser();
		user.setName("admin");
		user.setAvatar("111");
		user.setIntroduction("222");
		user.setRoles(new String[] {"admin"});
		
//		Result result = Result.result(20000, "Succ", user);
		
		System.out.println("Username: "+loginDto.getUsername());
		System.out.println("Password: "+loginDto.getPassword());

		Map<String, Object> responseData = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String roleId = "none";
		
		if("admin".equalsIgnoreCase(loginDto.getUsername()))
		{
			session.setAttribute("role_id", "admin");
		}
		
		if("editor".equalsIgnoreCase(loginDto.getUsername()))
		{
			session.setAttribute("role_id", "editor");
		}
		
		if("visitor".equalsIgnoreCase(loginDto.getUsername()))
		{
			session.setAttribute("role_id", "visitor");
		}

		responseData.put("token", "1A2B3C4D");

		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("code", 20000);
		response.put("msg", "Succ");
		response.put("data", responseData);
		
		return response;
	}
	
	@GetMapping("info")
	@ResponseBody
	public Map<String, Object> info(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		String roleId = (String) session.getAttribute("role_id");
		
		System.out.println("Role ID in session: "+roleId);
		
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put("roles", new String[] {roleId});
		responseData.put("name", "Super "+roleId);
		
		responseData.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
		
		response.put("code", 20000);
		response.put("msg", "Succ");
		response.put("data", responseData);
		
		return response;
	}
	
	@PostMapping("/logout")
	public Map<String, Object> logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("userInfo");
		session.removeAttribute("role_id");
		
		Map<String, Object> response = new HashMap<>();
		
		response.put("code", 20000);
		response.put("msg", "succ");
		
		return response;
	}
}
