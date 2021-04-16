package webstmt.ckfinder.listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.cksource.ckfinder.config.Config;
import com.cksource.ckfinder.event.GetConfigForRequestEvent;
import com.cksource.ckfinder.exception.InvalidRequestException;
import com.cksource.ckfinder.listener.Listener;

@Named
public class PreRequestConfigListener implements Listener<GetConfigForRequestEvent> {

	private static final Logger logger = LoggerFactory.getLogger(PreRequestConfigListener.class);
	
	@Override
	public void onApplicationEvent(GetConfigForRequestEvent event) {
		
		Config config = event.getConfig();
        Map<String, Config.Backend> backendConfigs = config.getBackends();
        
        Config.Backend backendConfig = backendConfigs.get("default");
        
        String userFileRoot = backendConfig.getRoot();
        
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(username==null) {
			throw new InvalidRequestException("User is not authenticated.");
		}
		
		String userFolderPath = userFileRoot+"/"+username;
		
		
		Path dirPath = Paths.get(userFolderPath);
		
		createNewDirectory(dirPath, username, userFolderPath);
		
		String baseUrl = backendConfig.getBaseUrl();
		
		backendConfig.setBaseUrl(baseUrl+"/"+username);
		backendConfig.setRoot(userFolderPath);
	}

	private synchronized void createNewDirectory(Path dirPath, String username, String userFolderPath)
	{
		 if (Files.exists(dirPath) && Files.isDirectory(dirPath)) {
             logger.info(String.format("Private directory for user \"%s\": %s", username, dirPath));
		 }
		 else {
			 logger.info(String.format("Private directory for user '%s' does not exist.", username));
				
				try {
					Files.createDirectories(dirPath);
					logger.info(String.format("Created private directory for user '%s' under '%s' ", username, userFolderPath));
				} catch (IOException e) {
					logger.error(String.format("Could not create private directory for user '%s' under '%s' ", username, userFolderPath));
				}
		 }
	}
}
