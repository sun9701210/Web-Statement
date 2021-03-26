package webstmt.entity.sys.datasource;

public class DataBindingString 
{
	private String bindingType;
	private String datasourceType;
	private String databaseName;
	private String tableName;
	private String columnName;
	
	public DataBindingString(String bindingString) throws IllegalArgumentException
	{
		String[] bindingArguments = bindingString.split(":");
		
		if(bindingArguments==null||bindingArguments.length<4)
		{
			throw new IllegalArgumentException("Insufficient Binding String. Less than 4 arguments.");
		}
		
		this.bindingType = bindingArguments[0];
		
		if(this.bindingType==null||(!DataBinding.DATA_BINDING_TYPE_SINGLE_VALUE.equalsIgnoreCase(this.bindingType)&&!DataBinding.DATA_BINDING_TYPE_LOOP_VALUE.equalsIgnoreCase(this.bindingType)))
		{
			throw new IllegalArgumentException("Illegal Binding Type: "+this.bindingType);
		}
		
		this.datasourceType = bindingArguments[1];
		
		if(DataSource.DATASOURCE_TYPE_DATABASE.equalsIgnoreCase(this.datasourceType))
		{
			this.databaseName=bindingArguments[2];
			
			if(this.databaseName==null)
			{
				throw new IllegalArgumentException("Illegal Null Data Base Name: "+this.databaseName);
			}
			
			this.tableName=bindingArguments[3];
			
			if(this.tableName==null)
			{
				throw new IllegalArgumentException("Illegal Null Data Table Name: "+this.databaseName);
			}
			
			if(bindingArguments.length==5)
			{
				this.columnName=bindingArguments[4];
			}
		}
		else if(DataSource.DATASOURCE_TYPE_DATAFILE.equalsIgnoreCase(this.datasourceType))
		{
			
		}
		else if(DataSource.DATASOURCE_TYPE_PARAMETER.equalsIgnoreCase(this.datasourceType))
		{
			
		}
		else
		{
			throw new IllegalArgumentException("Illegal Data Source Type: "+this.datasourceType);
		}
	}
	
	public boolean isBoundSingleValue()
	{
		return DataBinding.DATA_BINDING_TYPE_SINGLE_VALUE.equalsIgnoreCase(this.bindingType);
	}
	
	public boolean isBoundLoopValue()
	{
		return DataBinding.DATA_BINDING_TYPE_LOOP_VALUE.equalsIgnoreCase(this.bindingType);
	}
	
	public boolean isBoundDatabase()
	{
		return DataSource.DATASOURCE_TYPE_DATABASE.equalsIgnoreCase(this.datasourceType);
	}
	
	public boolean isBoundDataFile()
	{
		return DataSource.DATASOURCE_TYPE_DATAFILE.equalsIgnoreCase(this.datasourceType);
	}
	
	public boolean isBoundParameter()
	{
		return DataSource.DATASOURCE_TYPE_PARAMETER.equalsIgnoreCase(this.datasourceType);
	}

	public String getBindingType() {
		return bindingType;
	}

	public String getDatasourceType() {
		return datasourceType;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getTableName() {
		return tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}

	@Override
	public String toString() {
		return "DataBindingString [bindingType=" + bindingType + ", datasourceType=" + datasourceType
				+ ", databaseName=" + databaseName + ", tableName=" + tableName + ", columnName=" + columnName + "]";
	}
}
