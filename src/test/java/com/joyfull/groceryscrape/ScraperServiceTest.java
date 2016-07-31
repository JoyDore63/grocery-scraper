package com.joyfull.groceryscrape;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScraperServiceTest {

	private static Logger logger = LoggerFactory.getLogger(ScraperServiceTest.class);
	
	@Test
	public void testgetFileSize() throws IOException, MalformedURLException {
		long file_size = ScraperService.getFileSize(ScraperService.URL);
		logger.debug("file size=" + file_size);
		Assert.assertEquals(84542, file_size);
	}

}
