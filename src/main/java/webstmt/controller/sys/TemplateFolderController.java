package webstmt.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import webstmt.entity.sys.TemplateFolder;
import webstmt.service.sys.TemplateFolderService;

@RestController
@RequestMapping("/dashboard/template-folder")
public class TemplateFolderController {
	
	@Autowired
	private  TemplateFolderService folderService;
	
	@RequestMapping(value="/load",method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public List<Object> loadTree(){				
		
		List<Object> folder=folderService.load();
		
		//Gson gson=new Gson();		
		
		return folder;
	}
	

}
