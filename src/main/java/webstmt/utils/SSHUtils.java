package webstmt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHUtils 
{
	private static Logger log = LoggerFactory.getLogger(SSHUtils.class);
	
	public static boolean authenticate(String username, String password, String ip, int port)
	{
		JSch jsch = new JSch();
		
		Session session = null;
		
		try
		{
			session = jsch.getSession(username, ip, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			
			log.info("SSH Connected");
			return true;
		}
		catch(JSchException e)
		{
			log.error("Failed to Connect.",e);
			return false;
		}
	}
}
