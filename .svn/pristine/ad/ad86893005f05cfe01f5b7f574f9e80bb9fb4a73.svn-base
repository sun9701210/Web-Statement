package webstmt.service.sys.datasource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.datasource.DatabaseDataSource;
import webstmt.repo.sys.datasource.ColumnRepository;
import webstmt.repo.sys.datasource.DatabaseDataSourceRepository;
import webstmt.repo.sys.datasource.TableRepository;

@Service
public class DataSourceService 
{
	@Autowired
	private DatabaseDataSourceRepository dbRepo;
	@Autowired
	private TableRepository tableRepo;
	@Autowired
	private ColumnRepository colRepo;
	
	public List<DatabaseDataSource> getAllDatabaseDataSources()
	{
		return dbRepo.findAll();
	}
}
