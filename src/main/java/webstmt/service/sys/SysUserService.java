package webstmt.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.SysUser;
import webstmt.repo.sys.SysUserRepository;

@Service
public class SysUserService {

	@Autowired
	private SysUserRepository repo;
	
	public List<SysUser> getAllForDisplay() {
		
		List<SysUser> originalList = repo.findAll();
		
		List<SysUser> displayList = new ArrayList<>();
		
		for (SysUser originalUser : originalList) {
			
			SysUser displayUser = new SysUser();
			displayUser.setId(originalUser.getId());
			displayUser.setLpar(originalUser.getLpar());
			displayUser.setUsername(originalUser.getUsername());
			displayUser.setRoles(originalUser.getRoles());
			
			displayList.add(displayUser);
		}
		
		return displayList;
	}
	
	public List<SysUser> readAll()
	{
		return repo.findAll();
	}
	
	public SysUser findById(long id)
	{
		return repo.getOne(id);
	}
	
	public SysUser save(SysUser user)
	{
		return repo.save(user);
	}
	
	public void remove(long id)
	{
		repo.deleteById(id);
	}
	
	public boolean existsByUsername(String username)
	{
		SysUser user = repo.getUserByUsername(username);
		return user != null;
	}
	
	public SysUser findByUsername(String username) {
		
		return repo.findUserByUsername(username);
	}
	
	public List<SysUser> searchUser(String username) {
		return repo.searchUserByName(username);
	}
}
