package webstmt.entity.sys.datasource;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DataDictionary {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	private String description;
	private String defaultValue;
	private String format;
	
	@Enumerated(EnumType.STRING)
	private SystemID sysId;
	@Enumerated(EnumType.STRING)
	private DataType type;
	@Enumerated(EnumType.STRING)
	private DictionaryCategory category;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public SystemID getSysId() {
		return sysId;
	}
	public void setSysId(SystemID sysId) {
		this.sysId = sysId;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	public DictionaryCategory getCategory() {
		return category;
	}
	public void setCategory(DictionaryCategory category) {
		this.category = category;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	@Override
	public String toString() {
		return "DataDictionary [id=" + id + ", name=" + name + ", description=" + description + ", defaultValue="
				+ defaultValue + ", format=" + format + ", sysId=" + sysId + ", type=" + type + ", category=" + category
				+ "]";
	}
}
