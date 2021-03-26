package webstmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailService;
	
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService)
				.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/h2/**").hasRole("ADMIN")
			.antMatchers("/dashboard/**", "/gen-stmt/**","/ckfinder/**").hasRole("ADMIN")
			.antMatchers("/ccs-web-stmt","/stmt/**").hasAnyRole("ADMIN", "CUSTOMER")
			.antMatchers("/webjars/**", "/static/**").permitAll()
			.anyRequest().permitAll()
			.and().anonymous()
			.and().formLogin().defaultSuccessUrl("/", true)
			.and().httpBasic()
			.and().exceptionHandling().accessDeniedPage("/accessDenied");
		
		http.headers().frameOptions().sameOrigin();
	}
}
