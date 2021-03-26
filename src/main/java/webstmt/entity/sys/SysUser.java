package webstmt.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//import lombok.Data;

@Entity
@Table(name="sys_user")
//@Data
public class SysUser 
{
	@Id
	private long id;
	private String username;
	private String password;
	private String role;
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	
}
