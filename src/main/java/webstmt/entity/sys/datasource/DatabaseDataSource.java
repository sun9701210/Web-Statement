package webstmt.entity.sys.datasource;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class DatabaseDataSource extends DataSource implements Serializable
{
	private static final long serialVersionUID = 2571330303100066570L;

	private String databaseName;
	private String schema;
	
	@OneToMany
	private List<DataTable> tables;


	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<DataTable> getTables() {
		return tables;
	}

	public void setTables(List<DataTable> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "DatabaseDataSource [name=" + databaseName + ", schema=" + schema + ", tables=" + tables + ", getId()=" + getId()
				+ ", getType()=" + getType() + ", getKeyList()=" + getKeyList() + ", getLocation()=" + getLocation()
				+ ", getCountry()=" + getCountry() + ", getSegment()=" + getSegment() + ", getDescription()="
				+ getDescription() + "]";
	}
	
	
}
