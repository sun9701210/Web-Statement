package webstmt.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webstmt.entity.sys.datasource.DataColumn;
import webstmt.entity.sys.datasource.DatabaseDataSource;
import webstmt.entity.sys.datasource.DataTable;
import webstmt.service.sys.datasource.DataSourceService;

@Controller
@RequestMapping("/dashboard/datasource")
public class DataSourceController 
{
	@Autowired
	private DataSourceService service;
	

	@RequestMapping("")	
	public String datasourceTable(Model model)
	{
		List<DatabaseDataSource> dbSources = service.getAllDatabaseDataSources();
		
		for (DatabaseDataSource dbSrc : dbSources) 
		{
			System.out.println(dbSrc);
			
			List<DataTable> tables = dbSrc.getTables();
			
			for (DataTable table : tables) 
			{
				System.out.println(table);
				
				List<DataColumn> cols = table.getColumns();
				
				for (DataColumn col : cols) 
				{
					System.out.println(col);
				}
			}
		}
		
		model.addAttribute("datasources", dbSources);
		
		return "/dashboard/datasource-table";
	}
}
