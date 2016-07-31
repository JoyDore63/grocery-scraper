package com.joyfull.groceryscrape;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ScraperClient {

	private static final String URL = new String("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html");
	private static final Logger logger = LoggerFactory.getLogger(ScraperClient.class);
	
	public static void main(String[] args) throws IOException, MalformedURLException, UnexpectedFormatException {
	
		ScraperService scraperService = new ScraperService();
		List<Result> result_list = scraperService.getResults(URL);

		Results results = new Results(result_list);
		logger.info("Results:" + results.toString());
		
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonString = writer.writeValueAsString(results);
		System.out.print(jsonString);
	}
}
