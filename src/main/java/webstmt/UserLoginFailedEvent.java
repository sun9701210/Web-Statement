package webstmt;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

public class UserLoginFailedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1620352966999901428L;

	public UserLoginFailedEvent(Authentication auth) {
		super(auth);
	}

}
