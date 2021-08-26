package webstmt.entity.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="sys_route_meta")
public class RouteMeta {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String title="";
	private String icon="";
	private boolean noCache;
	private boolean breadcrumb;
	private boolean affix;
	private String activeMenu="";

//	@ManyToMany
//	private List<Role> roleList;
	
	@Transient
	private String[] roles=null;
	
//	private boolean rolesRefreshed = true;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String[] getRoles() {
		
//		if(this.roles == null) {
//			
//			List<String> roleStrArr = new ArrayList<>();
//			
//			for (Role role : this.roleList) {
//				
//				roleStrArr.add(role.getKey());
//			}
//			
//			this.roles = roleStrArr.toArray(new String[roleStrArr.size()]);
//		}
		
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
//		this.rolesRefreshed = true;
	}
//	public List<Role> getRoleList() {
		
//		if(this.rolesRefreshed) {
//			this.roles = new ArrayList<>();
//			String[] roleStrArr = this.roleList.split(",");
//			for (String roleStr : roleStrArr) {
//				this.roles.add(roleStr);
//			}
//			this.rolesRefreshed = false;
//		}
		
//		return this.roleList;
//	}
//	public void setRoleList(List<Role> roles) {
//		List<String> newRoleList = new ArrayList<String>();
//		this.roleList = "";
//
//		for (Role role : roles) {
//			newRoleList.add(role.getKey());
//			this.roleList += role.getKey() + ",";
//		}
//		
//		if(this.roleList.length()>0) {
//			this.roleList = this.roleList.substring(0, this.roleList.length()-1);
//		}
//		this.roles = newRoleList;
//		this.rolesRefreshed = true;

//		this.roles = null;
//		this.roleList = roles;
//	}
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
