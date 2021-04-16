package webstmt.entity.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonBackReference;

import webstmt.entity.sys.datasource.DataBinding;

@Entity
public class Template implements Serializable
{
	private static final long serialVersionUID = -7831627288646326100L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	@Lob
	@Column(length=522000)
	private String content;
	private String description;
	private String oppm;
	private String legalVehicle;
	
	private String lastUpdatedBy;
	@OrderBy("lastUpdatedTime desc")
	private Date lastUpdatedTime;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<DataBinding> dataBindings;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OrderBy("savedTime desc")
	private List<TemplateVersion> versions;
	
	@ManyToOne
	private TemplateFolder folder;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonBackReference
	public List<DataBinding> getDataBindings() {
		return dataBindings;
	}
	@JsonBackReference
	public void setDataBindings(List<DataBinding> dataBindings) {
		this.dataBindings = dataBindings;
	}	
	
	public TemplateFolder getFolder() {
		return folder;
	}
	
	public void setFolder(TemplateFolder folder) {
		this.folder = folder;
	}
	
	public String getOppm() {
		return oppm;
	}
	public void setOppm(String oppm) {
		this.oppm = oppm;
	}
	@JsonBackReference
	public List<TemplateVersion> getVersions() {
		return versions;
	}
	@JsonBackReference
	public void setVersions(List<TemplateVersion> versions) {
		this.versions = versions;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public String getLegalVehicle() {
		return legalVehicle;
	}
	public void setLegalVehicle(String legalVehicle) {
		this.legalVehicle = legalVehicle;
	}
	@Override
	public String toString() {
		return "Template [id=" + id + ", name=" + name + ", template=" + content + ", description=" + description
				+ ", oppm=" + oppm + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedTime=" + lastUpdatedTime
				+ ", dataBindings=" + dataBindings + ", versions=" + versions + ", folder=" + folder + "]";
	}
}
