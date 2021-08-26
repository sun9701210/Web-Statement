package webstmt.entity.sys;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import lombok.Data;

@Entity
@Table(name="sys_user")
//@Data
public class SysUser 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String username;
	
	@JsonIgnore
	private String password;
//	private String role;
	private String lpar;
	private String roleList;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Role> roles;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
//	public String getRole() {
//		return role;
//	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public void setRole(String role) {
//		this.role = role;
//	}
	public String getLpar() {
		return lpar;
	}
	public void setLpar(String lpar) {
		this.lpar = lpar;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
		this.roleList = null;
	}
	public String getRoleList() {
		
		if(this.roleList==null||this.roleList.length()==0) {
			
			this.roleList = "";
			for (Role role : roles) {
				this.roleList += role.getName() + ",";
			}
			
			if(this.roleList.length()>0) {
				this.roleList = this.roleList.substring(0, this.roleList.length()-1);
			}
			
			return this.roleList;
		}
		
		return this.roleList;
	}
	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", role=" + roles + ", lpar="
				+ lpar + "]";
	}
	
	
	
	
	
	
}
