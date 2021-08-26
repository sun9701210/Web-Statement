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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webstmt.entity.sys.SysUser;
import webstmt.service.sys.SysUserService;
import webstmt.utils.PasswordUtils;
import webstmt.viewmodel.LoginDto;
import webstmt.viewmodel.LoginUser;
import webstmt.viewmodel.MapResult;
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
		
		MapResult result = MapResult.newInstance(20000, "Succ");
		
//		LoginUser user = new LoginUser();
//		user.setName("admin");
//		user.setAvatar("111");
//		user.setIntroduction("222");
//		user.setRoles(new String[] {"admin"});
		
		String username = loginDto.getUsername();
		
		if(!userService.existsByUsername(username)) {
			
			System.out.println("User not exist -> "+username);
			
			result = MapResult.newInstance(50000, "Fail to login.");
			
			result.setNode("data", null);
			
			return result.getMap();
		}
		
		System.out.println("Username: "+loginDto.getUsername());
		System.out.println("Password: "+loginDto.getPassword());

		SysUser user = userService.findByUsername(username);
		
		if(!user.getPassword().contentEquals(loginDto.getPassword())) {

			System.out.println("Wrong password -> "+username);
			
			result = MapResult.newInstance(50000, "Fail to login.");
			
			result.setNode("data", "test");
			result.setNode("message", "Invalid username or password.");
			
			return result.getMap();
			
		}
		
		
//		Map<String, Object> responseData = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		String roleId = user.getRoleList();
		
		session.setAttribute("role_id", roleId);

		result.setNode("data", "token", "1A2B3C4D");
		
//		responseData.put("token", "1A2B3C4D");
//
//		Map<String, Object> response = new HashMap<String, Object>();
//		
//		response.put("code", 20000);
//		response.put("msg", "Succ");
//		response.put("data", responseData);
		
		return result.getMap();
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
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list() {
		
		List<SysUser> displayUserList = userService.getAllForDisplay();
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		result.setNode("data", "items", displayUserList);
		result.setNode("data", "total", displayUserList.size());
		
		return result.getMap();
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> add(@RequestBody SysUser user) {
		
		System.out.println(user);
		
		user.setPassword(PasswordUtils.generateInitialPassword());
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		SysUser savedUser = userService.save(user);
		
		result.setNode("data", savedUser);
		
		return result.getMap();
	}
	
	@DeleteMapping("{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") Long id) {
		
		System.out.println("Removing user -> "+id);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		userService.remove(id);
		
		return result.getMap();
	}
	
	@PutMapping
	@ResponseBody
	public Map<String, Object> update(@RequestBody SysUser user) {
		
		System.out.println(user);
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		return result.getMap();
	}
	
	@PutMapping("role")
	@ResponseBody
	public Map<String, Object> updateRole(@RequestBody SysUser user) {
		
		System.out.println(user);

		MapResult result = MapResult.newInstance(20000, "User's Role updated successfully!");
		
		SysUser originalUser = userService.findById(user.getId());
		
		if(originalUser==null) {
			result = MapResult.newInstance(50000, "Invalid User.");
			return result.getMap();
		}
		
		originalUser.setRoles(user.getRoles());
		
		SysUser savedUser = userService.save(originalUser);
		
		result.setNode("data", savedUser);
		
		return result.getMap();
	}
}
