package webstmt.entity.sys.datasource;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DataSource implements Serializable
{
	public static final String DATASOURCE_TYPE_DATABASE="db";
	public static final String DATASOURCE_TYPE_DATAFILE="dat";
	public static final String DATASOURCE_TYPE_PARAMETER="parm";
	
	private static final long serialVersionUID = 8921713315823761638L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long id;
	private int type;
	private String name;
	private String keyList;
	private String location;
	private String country;
	private String segment;
	private String description;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyList() {
		return keyList;
	}
	public void setKeyList(String keyList) {
		this.keyList = keyList;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
