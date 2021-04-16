package webstmt.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import webstmt.entity.sys.SysLpar;
import webstmt.entity.sys.SysRole;
import webstmt.entity.sys.SysUser;
import webstmt.service.sys.SysUserService;
import webstmt.utils.PasswordUtils;

@Controller
@RequestMapping("/dashboard/user-management")
public class SysUserController {

	@Autowired
	private SysUserService service;
	
	@RequestMapping(value="exists/{username}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String exists(@PathVariable("username") String username)
	{
		boolean userExists = service.existsByUsername(username);
	
		String json = userExists ? "{\"exists\":true}":"{\"exists\":false}";
		
		return json;
	}
	
	@GetMapping("")
	public String viewAll(Model model)
	{
		List<SysUser> users = service.readAll();
		
		model.addAttribute("users", users);
		
		return "dashboard/user-view-all.html";
	}
	
	@GetMapping("/new")
	public String createView(Model model)
	{
		model.addAttribute("allRoles", SysRole.values());
		model.addAttribute("allLpars", SysLpar.values());
		return "dashboard/user-create.html";
	}
	
	@PostMapping("/new")
	public String create(SysUser user, Model model)
	{
		user.setPassword(PasswordUtils.generateInitialPassword());
		
		SysUser savedUser = service.save(user);
		
		System.out.println("New User Saved: "+savedUser);
		
		return "redirect:/dashboard/user-management";
	}
	
	@GetMapping("/edit/{id}")
	public String editView(@PathVariable("id")long id, Model model)
	{
		System.out.println("Finding ID: "+id);
		
		SysUser sysUser = service.findById(id);
		
		System.out.println(sysUser);
		
		model.addAttribute("exist", sysUser!=null);
		model.addAttribute("sysUser", sysUser);
		model.addAttribute("allRoles", SysRole.values());
		model.addAttribute("allLpars", SysLpar.values());
		model.addAttribute("sysRole",sysUser.getRole());
		model.addAttribute("sysLpar",sysUser.getLpar());
		
		return "dashboard/user-edit.html";
	}
	
	@PostMapping(value="/edit")
	public String edit(Model model, SysUser sysUser)
	{
		//copy to prevent change other field
		SysUser user = service.findById(sysUser.getId());
		user.setRole(sysUser.getRole());
		user.setLpar(sysUser.getLpar());

		System.out.println("Saving "+user);
		
		service.save(user);
		
		List<SysUser> users = service.readAll();
		
		model.addAttribute("users", users);
		
		return "redirect:/dashboard/user-management";
	}
	
	@DeleteMapping("/remove")
	public String delete(long id)
	{
		service.remove(id);
		
		return "dashboard/user-view-all.html";
	}
}
