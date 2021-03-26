package webstmt.entity.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class TemplateVersion {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String UUID;
	private Date savedTime;
	@Lob
	@Column(length=522000)
	private String image;
	private String maker;
	
	@ManyToOne
	private Template template;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public Date getSavedTime() {
		return savedTime;
	}

	public void setSavedTime(Date savedTime) {
		this.savedTime = savedTime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
}
