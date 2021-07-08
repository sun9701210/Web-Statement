package webstmt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index(Authentication auth)
	{
		if(auth!=null&&auth.getAuthorities()!=null)
		{
			for (GrantedAuthority grantedAuth : auth.getAuthorities()) 
			{
				if(grantedAuth.getAuthority()==null||"".equals(grantedAuth.getAuthority().trim())) break;
				
				if("ROLE_ADMIN".equals(grantedAuth.getAuthority()))
				{
					return "redirect:/dashboard";
				}
				
				if(grantedAuth.getAuthority().startsWith("ROLE_BIZ"))
				{
					return "redirect:/dashboard";
				}
				
				if("ROLE_CUSTOMER".equals(grantedAuth.getAuthority()))
				{
					return "redirect:/ccs-web-stmt";
				}
			}
		}
		
		return "redirect:/index.html";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard()
	{
		return "dashboard/dashboard";
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied()
	{
		return "accessDenied";
	}
	
	@RequestMapping("/prod-setup")
	public String productSetup()
	{
		return "prod-setup";
	}
	
	@RequestMapping("/prod-setup-table")
	public String productSetupTable()
	{
		return "prod-setup-table";
	}
	
	@RequestMapping("/format-type-setup-table")
	public String formatTypeSetupTable()
	{
		return "format-type-setup-table";
	}
}
