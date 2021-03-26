package webstmt.service.sys.datasource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<DataDictionary> findAllBySystemId(String systemId)
	{
		return repo.findAllBySystemId(SystemID.valueOf(systemId));
	}
}
