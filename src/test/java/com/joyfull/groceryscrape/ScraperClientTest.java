package com.joyfull.groceryscrape;

import junit.framework.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScraperClientTest {

	private static final Logger logger = LoggerFactory.getLogger(ScraperClientTest.class);
	
	@Test
	public void testProductToJSON() throws JsonProcessingException {
		String expected = "{\r\n  \"title\" : \"title\",\r\n" +
				"  \"size\" : \"88kb\",\r\n" +
				"  \"unit_price\" : 1.8,\r\n" +
				"  \"description\" : \"description\"\r\n}";
		Product product = new Product("title", 90600, 1.8, "description");
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json_str = writer.writeValueAsString(product);
		logger.info("product JSON=" + json_str);
		Assert.assertEquals(expected, json_str);
	}

}
