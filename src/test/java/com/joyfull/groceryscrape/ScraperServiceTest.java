package com.joyfull.groceryscrape;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.junit.rules.ExpectedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ScraperServiceTest {

	private ScraperService scraperService;

	private static Logger logger = LoggerFactory.getLogger(ScraperServiceTest.class);
	private static final String URL = new String("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html");
	
	public ScraperServiceTest() throws Exception {
		scraperService = new ScraperService();
	}

	@Test
	public void testGetFileSize() throws IOException, MalformedURLException {
		long file_size = scraperService.getFileSize(URL);
		logger.debug("file size=" + file_size);
		Assert.assertEquals(84542, file_size);
	}
	
	@Test
	public void testParsePrice() {
		double price = scraperService.parsePrice("&pound3.20");
		Assert.assertEquals(3.2, price, .001);
	}
	
	@Test
	public void testParsePriceNoValue() {
		double price = scraperService.parsePrice("&pound six");
		Assert.assertEquals(0.0, price, .001);
	}
	
	@Test
	public void testParsePriceEmptyValue() {
		double price = scraperService.parsePrice("");
		Assert.assertEquals(0.0, price, .001);
	}

	@Test
	public void testGetPrice() throws UnexpectedFormatException {
		String html = "<div class=\"product \">" +
				"<p class=\"pricePerUnit\">" +
				"&pound;1.50<abbr title=\"per\">/</abbr><abbr title=\"unit\"><span class=\"pricePerUnitUnit\">unit</span>" +
				"</abbr></p></div>";
		logger.info("html=" + html);
		Document document = Jsoup.parse(html);
		Elements products = document.getElementsByClass("product");
		double price = scraperService.getPrice(products.first());
		Assert.assertEquals(1.5, price, .001);
	}
	
	@Test
	public void testGetLinkToDetailsPage() throws UnexpectedFormatException {
		String html = "<div class=\"product \">" +
				"<a href=\"http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html\" >" +
                "Sainsbury's Apricot Ripe & Ready x5" +
	            "</a></div>";
		logger.info("html=" + html);
		Document document = Jsoup.parse(html);
		Elements products = document.getElementsByClass("product");
		String detail_link = scraperService.getLinkToDetailsPage(products.first());
		Assert.assertEquals("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html", detail_link);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testGetLinkToDetailsPageFails() throws UnexpectedFormatException {
		String html = "<div class=\"product \">" +
				"<p href=\"http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html\" >" +
                "Sainsbury's Apricot Ripe & Ready x5" +
	            "</p></div>";
		logger.info("html=" + html);
		Document document = Jsoup.parse(html);
		Elements products = document.getElementsByClass("product");
		thrown.expect(UnexpectedFormatException.class);
		scraperService.getLinkToDetailsPage(products.first());
	}
	
	@Test
	public void testGetTitle() throws IOException, UnexpectedFormatException {
		String detail_link = new String("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-avocados--ripe---ready-x4.html");
		Document detail_doc = Jsoup.connect(detail_link).get();
		String title = scraperService.getTitle(detail_doc);
		Assert.assertEquals("Sainsbury's Avocados, Ripe & Ready x4 | Sainsbury's", title);
	}
	
	@Test
	public void testGetDescription() {
		String html = "<meta name=\"description\" content=\"Buy Sainsbury's Avocados, Ripe & Ready x4 online from Sainsbury's, the same " +
				"great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.\"/>" + 
				"<meta name=\"keyword\" content=\"\"/>" +
				"<meta property=\"fb:app_id\" content=\"258691960829999\" />"; 
		logger.info("html=" + html);
		Document document = Jsoup.parse(html);
		String description = scraperService.getDescription(document);
		Assert.assertEquals("Buy Sainsbury's Avocados, Ripe & Ready x4 online from Sainsbury's, the same great quality, freshness and choice you'd find in store. Choose from 1 hour delivery slots and collect Nectar points.", description);
	}
}
