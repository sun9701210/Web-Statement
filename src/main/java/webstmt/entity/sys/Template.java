package webstmt.entity.sys;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import webstmt.entity.sys.datasource.DataBinding;

@Entity
public class Template implements Serializable
{
	private static final long serialVersionUID = -7831627288646326100L;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	@Lob
	@Column(length=522000)
	private String content;
	private String description;
	private String oppm;
	private String market;
	private String status;
	
	private String author;
	private String reviewer;
	@Column(nullable=true)
	private int priority;
//	private int variableCount;
	
	private String lastUpdatedBy;
	@OrderBy("lastUpdatedTime desc")
	private Date lastUpdatedTime;
	
	private Date releaseDate;
	
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
	
	@JsonBackReference(value="databindings")
	public List<DataBinding> getDataBindings() {
		return dataBindings;
	}
//	@JsonBackReference
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
	@JsonBackReference(value="versions")
	public List<TemplateVersion> getVersions() {
		return versions;
	}
//	@JsonBackReference
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
	public String getMarket() {
		return market;
	}
	public void setMarket(String legalVehicle) {
		this.market = legalVehicle;
	}
	public String getReleaseDate() {
		
		return sdf.format(releaseDate);
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public void setReleaseDate(String releaseDate) throws ParseException {
		
		this.releaseDate = sdf.parse(releaseDate);
		
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getVariableCount() {
		
//		variableCount = this.dataBindings.size();
		if(this.dataBindings==null) return 0;
		
		return this.dataBindings.size();
	}
	
//	public void setVariableCount(int variableCount) {
//		//just ignore this cause request body json will not contain such value
//	}
	
	@Override
	public String toString() {
		return "Template [id=" + id + ", name=" + name + ", template=" + content + ", description=" + description
				+ ", oppm=" + oppm + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedTime=" + lastUpdatedTime
				+ ", dataBindings=" + dataBindings + ", versions=" + versions + ", folder=" + folder + "]";
	}
}
