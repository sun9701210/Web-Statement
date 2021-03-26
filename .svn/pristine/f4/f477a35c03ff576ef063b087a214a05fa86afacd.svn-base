package webstmt.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import webstmt.entity.sys.datasource.DataBinding;
import webstmt.service.sys.datasource.DataBindingService;

@RestController
@RequestMapping("/dashboard/databinding")
public class DataBindingController 
{
	@Autowired
	private DataBindingService service;
	
	@RequestMapping(value="/template/{id}", method= RequestMethod.GET, produces= {"application/json;charset=UTF-8"})
	public String getAllDataBindingByTemplateId(@PathVariable("id") long templateId)
	{
		List<DataBinding> bindings = service.getDataBindingByTemplateId(templateId);
		
		Gson gson = new Gson();
		
		return gson.toJson(bindings);
	}
}
