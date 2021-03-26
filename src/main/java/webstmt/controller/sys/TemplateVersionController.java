package webstmt.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import webstmt.entity.sys.TemplateVersion;
import webstmt.service.sys.TemplateVersionService;

@Controller
@RequestMapping("/dashboard/template")
public class TemplateVersionController {

	@Autowired
	private TemplateVersionService service;

	@GetMapping(value="/history/{tpl-id}/{version}")
	public String showHistoryVersion(@PathVariable("tpl-id") long templateId, @PathVariable("version")String uuid, Model model)
	{
		TemplateVersion version = service.findVersionByUUIDAndTemplateId(uuid, templateId);
		
		if(version==null)
		{
			model.addAttribute("exist", false);
		}
		else
		{
			model.addAttribute("exist", true);
			model.addAttribute("version", version);
		}
		
		return "dashboard/template-history";
	}
}
