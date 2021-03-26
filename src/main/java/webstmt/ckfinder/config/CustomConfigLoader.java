package webstmt.ckfinder.config;

import com.cksource.ckfinder.config.Config;
import com.cksource.ckfinder.config.loader.ConfigLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import webstmt.SpringCKFinderContextBridge;

import javax.annotation.Resource;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Named
public class CustomConfigLoader implements ConfigLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomConfigLoader.class);
	
    @Override
    public Config loadConfig() throws Exception {
    	
    	ApplicationContext ctx = SpringCKFinderContextBridge.getContext();
    	
    	String configName=ctx.getEnvironment().getProperty("config.backends.name");
    	
    	String yamlName="";
    	if("default".equalsIgnoreCase(configName)||configName==null||"".equalsIgnoreCase(configName))
    	{
    		yamlName="ckfinder.yml";
    	}
    	else
    	{
    		yamlName="ckfinder-"+configName+".yml";
    	}
    	
    	URL ckfinderConfig = CustomConfigLoader.class.getClassLoader().getResource(yamlName);
    	logger.info(String.format("Loading ckfinder yaml config file '%s'", ckfinderConfig));
    	InputStream input = CustomConfigLoader.class.getClassLoader().getResourceAsStream(yamlName);
    	
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        
        
        return mapper.readValue(input, CustomConfig.class);
    }
}
