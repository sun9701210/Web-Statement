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

	public List<DataBinding> getAllDataBindingList() {
		return repo.findAll();
	}
	
	public List<DataBinding> getDataBindingByTemplateId(long templateId)
	{
		return repo.getAllDataBindingByTemplateId(templateId);
	}
	
	public List<Long> getDataBindingIdsByTemplateId(long templateId)
	{
		return repo.getAllDataBindingIdsByTemplateId(templateId);
	}
	
	public DataBinding getDataBindingById(long id) {
		return repo.getOne(id);
	}
	
	public DataBinding updateDataBinding(DataBinding dataBinding) {
		
		return repo.save(dataBinding);
	}
	
	public DataBinding createDataBinding(DataBinding dataBinding) {
		
//		DataBinding newDb = new DataBinding();
//		
//		newDb.setBindingType(dataBinding.getBindingType());
//		newDb.setDictionary(dataBinding.getDictionary());
//		newDb.setPlaceholder(dataBinding.getPlaceholder());
//		newDb.setProcessorClassName(dataBinding.getProcessorClassName());
//		newDb.setTemplate(dataBinding.getTemplate());
		
		return repo.save(dataBinding);
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
