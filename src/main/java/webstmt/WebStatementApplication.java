package webstmt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cksource.ckfinder.servlet.CKFinderServlet;

@SpringBootApplication
@EnableJpaRepositories(basePackages= {"webstmt.repo"})
@EntityScan({"webstmt.entity"})
//@ComponentScan(basePackages= {"webstmt.ckfinder.**.**"})
public class WebStatementApplication implements ServletContextInitializer, WebMvcConfigurer
{
	@Value("${user.upload.dir}")
	private String userUploadFileDir;
	
	@Value("${user.upload.tmp}")
	private String userUploadTempDir;

	public static void main(String[] args) {
		SpringApplication.run(WebStatementApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// Register CKFinder servlet
		Servlet ckfinderServlet = new CKFinderServlet();
//		ckfinderServlet.
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("ckfinder", ckfinderServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/ckfinder/*");
		dispatcher.setInitParameter("scan-path", "webstmt.ckfinder");
		
		FilterRegistration.Dynamic filter = servletContext.addFilter("x-content-options", new Filter() {
			
			@Override
			public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
					throws IOException, ServletException {
				
				HttpServletResponse servletResp = ((HttpServletResponse)resp);
				
				servletResp.setHeader("X-Content-Type-Options", "nosniff");
				
				chain.doFilter(req, resp);
			}
			
			@Override
			public void destroy() {
			}
		});
		
		filter.addMappingForUrlPatterns(null, false, "/dashboard/userfiles/*");
		
		String tempDirectory;
		
		try {
			tempDirectory = Files.createTempDirectory(Paths.get(userUploadTempDir), "ckfinder").toString();
			
			System.out.println("System Temp Dir - "+tempDirectory);
		} catch(IOException e) {
			tempDirectory = null;
		}
		
		dispatcher.setMultipartConfig(new MultipartConfigElement(tempDirectory));
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Configure the resource handler to serve files uploaded with CKFinder
		String publicFileDir = String.format("file:%s/userfiles/", userUploadFileDir);
		
		System.out.println("Listening on User Upload Files in "+publicFileDir);
		
		registry.addResourceHandler("/dashboard/userfiles/**").addResourceLocations(publicFileDir);
		
		registry.addResourceHandler("/static/ckfinder/**").addResourceLocations("classpath:/static/ckfinder/");
	}

}
