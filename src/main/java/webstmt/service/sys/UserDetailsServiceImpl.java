package webstmt.service.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.SysUser;
import webstmt.repo.sys.SysUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private SysUserRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		log.info("Handling user: "+username);
		
		SysUser sysUser = repo.getUserByUsername(username);
		
		if(sysUser==null) 
		{
			throw new UsernameNotFoundException("User not found");
		}
		
		log.info("User's Role: "+sysUser.getRole());
		
//		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysUser.getRole());
//		grantedAuthorities.add(grantedAuthority);
		
		return new User(sysUser.getUsername(), sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(sysUser.getRole()));
	}

}
