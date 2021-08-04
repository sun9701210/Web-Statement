package webstmt.entity.sys;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_route_meta")
public class RouteMeta {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String roles="";
	private String title="";
	private String icon="";
	private boolean noCache;
	private boolean breadcrumb;
	private boolean affix;
	private String activeMenu="";
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isNoCache() {
		return noCache;
	}
	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}
	public boolean isBreadcrumb() {
		return breadcrumb;
	}
	public void setBreadcrumb(boolean breadcrumb) {
		this.breadcrumb = breadcrumb;
	}
	public boolean isAffix() {
		return affix;
	}
	public void setAffix(boolean affix) {
		this.affix = affix;
	}
	public String getActiveMenu() {
		return activeMenu;
	}
	public void setActiveMenu(String activeMenu) {
		this.activeMenu = activeMenu;
	}
	@Override
	public String toString() {
		return "RouteMeta [id=" + id + ", roles=" + roles + ", title=" + title + ", icon=" + icon + ", noCache="
				+ noCache + ", breadcrumb=" + breadcrumb + ", affix=" + affix + ", activeMenu=" + activeMenu + "]";
	}
	
	
}
