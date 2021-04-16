package webstmt.entity.sys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String password;
	private String role;
	private String lpar;
	
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
	public String getRole() {
		return role;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLpar() {
		return lpar;
	}
	public void setLpar(String lpar) {
		this.lpar = lpar;
	}
	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", lpar="
				+ lpar + "]";
	}
	
	
	
	
	
	
}
