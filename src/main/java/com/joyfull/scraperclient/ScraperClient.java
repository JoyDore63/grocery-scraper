package com.joyfull.scraperclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.joyfull.groceryscrape.Result;
import com.joyfull.groceryscrape.Results;
import com.joyfull.groceryscrape.ScraperService;
import com.joyfull.groceryscrape.UnexpectedFormatException;

public class ScraperClient {

	private static final String URL = new String("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html");
	private static final Logger logger = LoggerFactory.getLogger(ScraperClient.class);
	
	/**
	 * Main entry point, run with optional -u <url_string>
	 * @param args
	 * @throws JsonProcessingException
	 * @throws UnexpectedFormatException
	 */
	public static void main(String[] args) throws UnexpectedFormatException, JsonProcessingException {
		
		String url = URL;
		List<Result> result_list;
		
		// Allow for command line arg of url, if not there use the default
		if (args.length > 1 && args[0].equals("-u")) {
			System.out.println("Using input URL");
			url = args[1];
		}
		else {
			System.out.println("Using default URL");
		}
	
		ScraperService scraperService = new ScraperService();
		try {
			result_list = scraperService.getResults(url);
		}
		catch (MalformedURLException urlException) {
			System.out.println("Error:Invalid URL");
			logger.error("Caught exception", urlException);
			return;
		}
		catch (IOException ioException) {
			System.out.println("Error:Connection failure");
			logger.error("Caught exception", ioException);
			return;
		}

		Results results = new Results(result_list);
		logger.debug("Results:" + results.toString());
		
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonString = writer.writeValueAsString(results);
		System.out.println("JSON RESULT:");
		System.out.println(jsonString);
	}
}
