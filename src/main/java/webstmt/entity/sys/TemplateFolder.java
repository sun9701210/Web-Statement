package webstmt.entity.sys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import webstmt.entity.sys.datasource.LegalVehicle;

@Entity
public class TemplateFolder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String description;
	private Boolean active;
	@Deprecated
	private String legalVehicle;
	
	private String market;
	private String source;
	private String category;
	
	@ManyToOne
	private TemplateFolder parent;
	
	public String getDisplayName()
	{
		return formatParent(this);
	}
	
	private static String formatParent(TemplateFolder parentFolder)
	{
//		if(parentFolder==null) return "";
		
		if(parentFolder.parent==null)
		{
			return parentFolder.name;
		}
		else
		{
			return formatParent(parentFolder.parent)+"-"+parentFolder.name;
		}
	}
	
	@Transient
	private int fileAmount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public TemplateFolder getParent() {
		return parent;
	}
	public void setParent(TemplateFolder parent) {
		this.parent = parent;
	}	
	public int getFileAmount() {
		return fileAmount;
	}
	public void setFileAmount(int fileAmount) {
		this.fileAmount = fileAmount;
	}	

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getLegalVehicle() {
		return legalVehicle;
	}

	public void setLegalVehicle(String legalVehicle) {
		this.legalVehicle = legalVehicle;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getActive() {
		return active;
	}

	@Override
	public String toString() {
		return "TemplateFolder [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active
				+ ", legalVehicle=" + legalVehicle + ", parent=" + parent + ", fileAmount=" + fileAmount + "]";
	}
}
