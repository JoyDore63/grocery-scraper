package com.joyfull.scraperclient;

import java.util.ArrayList;

import junit.framework.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.joyfull.groceryscrape.Result;
import com.joyfull.groceryscrape.Results;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScraperClientTest {

	private static final Logger logger = LoggerFactory.getLogger(ScraperClientTest.class);
	private static ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
	
	@Test
	public void testResultToJSON() throws JsonProcessingException {
		String expected = "{\r\n  \"title\" : \"Oranges\",\r\n" +
				"  \"size\" : \"88kb\",\r\n" +
				"  \"unit_price\" : 1.8,\r\n" +
				"  \"description\" : \"Large navel\"\r\n}";
		Result result = new Result("Oranges", 90600, 1.8, "Large navel");
		String json_str = writer.writeValueAsString(result);
		logger.info("Result JSON=" + json_str);
		Assert.assertEquals(expected, json_str);
	}

	@Test
	public void testResultsToJSON() throws JsonProcessingException {
		String expected = "{\r\n  \"results\" : [ {\r\n    \"title\" : \"title1\",\r\n" +
				"    \"size\" : \"88kb\",\r\n" +
				"    \"unit_price\" : 1.8,\r\n" +
				"    \"description\" : \"description1\"\r\n  }, " + 
				"{\r\n    \"title\" : \"title2\",\r\n" +
				"    \"size\" : \"97kb\",\r\n" +
				"    \"unit_price\" : 2.5,\r\n" +
				"    \"description\" : \"description2\"\r\n  } ],\r\n  \"total\" : \"4.3\"\r\n}";
		Result result1 = new Result("title1", 90600, 1.8, "description1");
		Result result2 = new Result("title2", 99600, 2.5, "description2");
		ArrayList<Result> result_list = new ArrayList<Result>(2);
		result_list.add(result1);
		result_list.add(result2);
		Results results = new Results(result_list);
		String json_str = writer.writeValueAsString(results);
		logger.info("Results JSON=" + json_str);
		Assert.assertEquals(expected, json_str);
	}
	
	
	
	
	
}
