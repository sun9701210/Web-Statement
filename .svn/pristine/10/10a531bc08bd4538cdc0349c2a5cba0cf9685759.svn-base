package webstmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCKFinderContextBridge {

	private static final Logger logger = LoggerFactory.getLogger(SpringCKFinderContextBridge.class);
	private static ApplicationContext _ctx;
	
	public SpringCKFinderContextBridge(ApplicationContext context) {
		logger.info("Inject context in bridge.");
		_ctx=context;
	}
	
	public static ApplicationContext getContext()
	{
		return _ctx;
	}
}
