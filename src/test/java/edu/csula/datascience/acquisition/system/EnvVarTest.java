package edu.csula.datascience.acquisition.system;
import static org.junit.Assert.*;
import org.junit.Test;

public class EnvVarTest {
	
	private String TWITTER_CONSUMER_KEY = "KXjY39ROD2QMa0LUIkEBSnJMM";
	private String TWITTER_CONSUMER_SECRET = "lX27EskdFXqFCYwQCo7zK8c7FT9NgL2YpDox0anq4U9g6SrOKG";
	private String TWITTER_ACCESS_TOKEN = "722849671593402368-Fx0XHOaSsIyCQy0VJ6ZWnZ97TmWJ3CG";
	private String TWITTER_ACCESS_SECRET = "ByBD930AcAK9Ue00PvWXMFQ3o7j4RSRSZXthz0rigzKOk";

	@Test
	public void sysVars() {
		assertEquals("/usr/bin:/bin:/usr/sbin:/sbin", System.getenv("PATH"));
		assertEquals("KXjY39ROD2QMa0LUIkEBSnJMM", System.getenv("TWITTER_CONSUMER_KEY"));
//		assertEquals("hi", System.getenv("hi"));
	}
	
}
