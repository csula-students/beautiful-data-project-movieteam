package edu.csula.datascience.acquisition.elasticsearch;

import static org.junit.Assert.*;
import org.junit.Test;

public class Exporter {
	
	@Test
	public void testMovie() {
		String testStr = "Hello Love Me Not...";
		assertEquals(true, testStr.contains("#Love ") || testStr.contains("Love "));
	}
}
