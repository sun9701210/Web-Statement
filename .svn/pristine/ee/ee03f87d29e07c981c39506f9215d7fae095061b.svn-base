package webstmt.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.service.sys.datasource.DataDictionaryService;

@Controller
@RequestMapping("/dashboard/dictionary")
public class DataDictionaryController {

	@Autowired
	private DataDictionaryService service;
	
	@RequestMapping(value="/system/{sysId}", method = RequestMethod.GET, produces= "application/json;charset=utf-8")
	@ResponseBody
	public String findDictionaryBySystemId(@PathVariable("sysId") String systemId)
	{
		List<DataDictionary> dictionaryList = service.findAllBySystemId(systemId);
		
		Gson gson = new Gson();
		
		return gson.toJson(dictionaryList);
	}
}
