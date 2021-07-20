package webstmt.viewmodel;

import java.util.Date;

import webstmt.entity.sys.TemplateFolder;

public class TemplateDto {
	
	private long id;
	private String name;
	private String content;
	private String description;
	private String oppm;
	private String market;
	private String status;
	private String author;
	private String reviewer;
	private int priority;
	private String lastUpdatedBy;
	private Date releaseDate;
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
	public String getOppm() {
		return oppm;
	}
	public void setOppm(String oppm) {
		this.oppm = oppm;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public TemplateFolder getFolder() {
		return folder;
	}
	public void setFolder(TemplateFolder folder) {
		this.folder = folder;
	}
	@Override
	public String toString() {
		return "TemplateDto [id=" + id + ", name=" + name + ", content=" + content + ", description=" + description
				+ ", oppm=" + oppm + ", market=" + market + ", status=" + status + ", author=" + author + ", reviewer="
				+ reviewer + ", priority=" + priority + ", lastUpdatedBy=" + lastUpdatedBy + ", releaseDate="
				+ releaseDate + ", folder=" + folder + "]";
	}
}
