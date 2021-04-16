package webstmt.controller.sys;

import java.util.ArrayList;
import java.util.Dictionary;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.entity.sys.datasource.DataType;
import webstmt.entity.sys.datasource.DictionaryCategory;
import webstmt.entity.sys.datasource.SystemID;
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
	
	@RequestMapping("")
	public String findAllDictionary(Model model){
		
		List<DataDictionary> dictionary=service.findAll();
		
		List<DataDictionary> dictionaryList = service.findAllBySystemId("RM");
		
		model.addAttribute("dictionary",dictionaryList);
		
		return "dashboard/data-dictionary";
		
	}
	
	@GetMapping(value="/new")
	public String newDictionary(Model model){
		
		model.addAttribute("dictionary", new DataDictionary());
		model.addAttribute("allSystemIds", SystemID.values());
		model.addAttribute("allDataTypes", DataType.values());
		model.addAttribute("allCategories",DictionaryCategory.values());
		
		return "dashboard/data-dictionary-new";
		
	}
	
	@PostMapping(value="/save")
	public String saveDictionary(Model model,DataDictionary dic){
		
		DataDictionary dictionary=new DataDictionary();
		
		
		dictionary.setSysId(dic.getSysId());
		dictionary.setName(dic.getName());
		dictionary.setCategory(dic.getCategory());
		dictionary.setDescription(dic.getDescription());
		dictionary.setFormat(dic.getFormat());
		dictionary.setType(dic.getType());
		
		Long id=service.save(dictionary);
		
		//System.out.println(dictionary);
		
		if(id>0){
			model.addAttribute("succ",true);
			model.addAttribute("result","Save Successfully.");
		}else{
			
			model.addAttribute("succ",false);
			model.addAttribute("result","Failed to Save.");
			
		}
		
		return "dashboard/data-dictionary-save-result";
	}
	
	@GetMapping(value="/edit/{id}")
	public String showEdit(@PathVariable("id") long id, Model model){
		
		DataDictionary dictionary=service.read(id);
		
		if(dictionary==null){
			
			model.addAttribute("exist",false);
			
		}else{
			
			model.addAttribute("exist",true);
			
			model.addAttribute("dictionary",dictionary);
			model.addAttribute("allSystemIds", SystemID.values());
			model.addAttribute("allDataTypes", DataType.values());
			model.addAttribute("allCategories",DictionaryCategory.values());
			
		}		
		
		return "dashboard/data-dictionary-edit";
	}
	
	@PostMapping(value="/edit")
	public String editDictionary(Model model,DataDictionary dic){
		
		DataDictionary dictionary=service.read(dic.getId());
		
		dictionary.setSysId(dic.getSysId());
		dictionary.setName(dic.getName());
		dictionary.setCategory(dic.getCategory());
		dictionary.setDescription(dic.getDescription());
		dictionary.setFormat(dic.getFormat());
		dictionary.setType(dic.getType());
		
		Long saveId=service.save(dictionary);
		
		if(saveId>0){
			model.addAttribute("succ",true);
			model.addAttribute("result","Save Successfully.");
		}else{
			model.addAttribute("succ",false);
			model.addAttribute("result","Failed to Save.");
		}
		
		return "dashboard/data-dictionary-edit-result";
		
	}
		
	
	@DeleteMapping(value="/remove")
	@ResponseBody
	public String removeDictionary(Long id){
		
		service.remove(id);
		
		return "dashboard/data-dictionary";
		
	}
	
	@GetMapping(value="/search")
	@ResponseBody
	public List<DataDictionary> searchDictionary(String name){
		
		List<DataDictionary> dictionary=service.search(name);
		
		return dictionary;
		
	}
	
	@GetMapping(value="/category/{sysId}")
	@ResponseBody
	public List<Object> findCategory(@PathVariable("sysId") SystemID sysId){
		
		List<Object> sysCategory=new ArrayList<>();	
		
		
		
		for(Object c:DictionaryCategory.values()){
			
			//System.out.println(c);
			
			if(c.equals(sysId)){
				
				System.out.println("1");
				
			}else{
				System.out.println("2");
			}
			
		}
			
		
		return sysCategory;
		
	}
	
	
	
}
