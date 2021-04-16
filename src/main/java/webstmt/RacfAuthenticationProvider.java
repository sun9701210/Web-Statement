package webstmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import webstmt.utils.SSHUtils;

@Component
public class RacfAuthenticationProvider implements AuthenticationProvider {

	private Logger log = LoggerFactory.getLogger(RacfAuthenticationProvider.class);
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	

	@Value("${app.auth.racf.enable}")
	private boolean racfEnabled;
	@Value("${app.auth.racf.ip}")
	private String racfIp;
	@Value("${app.auth.racf.port}")
	private int racfPort;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		log.info("Doing authentication...");
		
		String username = auth.getName();
		String password = (String) auth.getCredentials();
		
		if(StringUtils.isEmpty(username))
		{
			throw new UsernameNotFoundException("User Name is empty.");
		}
		
		if(StringUtils.isEmpty(password))
		{
			throw new BadCredentialsException("Bad Credential.");
		}
		
		UserDetails user = userDetailService.loadUserByUsername(username);
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		
		if(!passwordEncoder.matches(password, encryptedPassword))
		{
			log.info("Wrong password.");
			publisher.publishEvent(new UserLoginFailedEvent(auth));
			throw new BadCredentialsException("Bad Credential.");
		}
		

		if(racfEnabled)
		{
			log.info("Doing RACF authentication...");
			
			boolean authenticated = SSHUtils.authenticate(user.getUsername(), user.getPassword(), racfIp, racfPort);
			
			if(!authenticated)
			{
				log.info("RACF Authentication failed.");
				publisher.publishEvent(new UserLoginFailedEvent(auth));
				throw new BadCredentialsException("Bad Credential.", new AuthenticationCredentialsNotFoundException("RACF Authentication Failed."));
			}
			
			log.info("RACF authenticated.");
		}
		
		return new UsernamePasswordAuthenticationToken(user.getUsername(), encryptedPassword, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UsernamePasswordAuthenticationToken.class);
	}

}
