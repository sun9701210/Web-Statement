package webstmt.service.sys.datasource;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.entity.sys.datasource.SystemID;
import webstmt.repo.sys.datasource.DataDictionaryRepository;

@Service
public class DataDictionaryService {

	@Autowired
	private DataDictionaryRepository repo;
	
	public List<DataDictionary> findAll()
	{
		return repo.findAll();
	}
	
	public DataDictionary read(Long id){
		
		DataDictionary dictionary=repo.getOne(id);
		
		return dictionary;		
	}
	
	public List<DataDictionary> findAllBySystemId(String systemId)
	{
		return repo.findAllBySystemId(SystemID.valueOf(systemId));
	}
	
	public Long save(DataDictionary dataDictionary){
		
		repo.save(dataDictionary);
		
		return dataDictionary.getId();
		
	}
	
	public void remove(Long id){
		
		repo.deleteById(id);
		
	}
	
	public List<DataDictionary> search(String name){
		
		List<DataDictionary> dictionary=repo.findByName(name);
			
		return dictionary;		
		
	}
	
	
	
}
