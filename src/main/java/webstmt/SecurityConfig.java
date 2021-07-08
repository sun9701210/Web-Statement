package webstmt;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private AuthenticationProvider racfAuthProvider;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//		auth.userDetailsService(userDetailService)
//				.passwordEncoder(passwordEncoder)
//				.and()
				auth.authenticationProvider(racfAuthProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/h2/**").hasRole("ADMIN")
			.antMatchers("/dashboard/**", "/gen-stmt/**","/ckfinder/**").hasAnyRole("ADMIN","BIZ")
			.antMatchers("/ccs-web-stmt","/stmt/**").hasAnyRole("ADMIN","BIZ", "CUSTOMER")
			.antMatchers("/webjars/**", "/static/**").permitAll()
			.anyRequest().permitAll()
			.and().anonymous()
			.and().formLogin()
						//.loginPage("/login.html").permitAll()
						.defaultSuccessUrl("/", true)
			.and().httpBasic()
			.and().exceptionHandling().accessDeniedPage("/accessDenied");
		
		http.headers().frameOptions().sameOrigin();
	}
}
