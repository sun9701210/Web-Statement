package webstmt.service.sys.datasource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.datasource.DataBinding;
import webstmt.repo.sys.datasource.DataBindingRepository;

@Service
public class DataBindingService {

	@Autowired
	private DataBindingRepository repo;
	
	public List<DataBinding> getDataBindingByTemplateId(long templateId)
	{
		return repo.getAllDataBindingByTemplateId(templateId);
	}
	
	public List<Long> getDataBindingIdsByTemplateId(long templateId)
	{
		return repo.getAllDataBindingIdsByTemplateId(templateId);
	}
	
	private static final int _skipStep = 20;
	
	public Long getNextId()
	{
		Long currentMaxId = repo.findNextId();
		
		return currentMaxId + _skipStep;
	}
	
	public boolean exists(long dataBindingId)
	{
		return repo.existsById(dataBindingId);
	}
	
	public void delete(long id)
	{
		repo.deleteById(id);
	}
}
