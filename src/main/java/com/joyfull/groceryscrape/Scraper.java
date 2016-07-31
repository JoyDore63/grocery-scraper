package com.joyfull.groceryscrape;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scraper {

	private static final String URL = new String("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html");
	private static final Logger logger = LoggerFactory.getLogger(Scraper.class);
	
	/**
	 * 
	 * @param link
	 * @return
	 */
	private static long getFileSize(String link) throws IOException, MalformedURLException {
		URL url = new URL(link);
	    HttpURLConnection conn = null;
	    try {
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.getInputStream();
	        return conn.getContentLengthLong();
	    }
	    finally {
	        conn.disconnect();
	    }
	}
	
	/**
	 * 
	 * @param l
	 * @return Long value formatted to string with kb units 
	 */
	private static String formatSize(long l) {
		return FileUtils.byteCountToDisplaySize(l).toLowerCase();
	}
	
	private static double getPrice(Element element) {
		String price_str = element.getElementsByClass("pricePerUnit").first().ownText();
		Scanner scanner = new Scanner(price_str);
		double price = scanner.nextDouble();
		scanner.close();
		return(price);
	}
	
	public static void main(String[] args) throws IOException, MalformedURLException {

		List<Product> products = new ArrayList<Product>();
		
		// Get main URL as doc
		Document doc = Jsoup.connect(URL).get();
		
		// Get products list
		Elements source_products = doc.getElementsByClass("product");
		// Each product: get details
		for (Element source_prod : source_products) {
			// Unit price is in products list on first page, ie within current element
			double unit_price = getPrice(source_prod);
			// Get link to details page
			Elements references = source_prod.getElementsByTag("a");		
			String detail_link = references.first().attr("abs:href");
			// Get size of linked file
			long file_size = getFileSize(detail_link);
			// Connect to link to get title from details page
			Document detail_doc = Jsoup.connect(detail_link).get();
			String title = detail_doc.getElementsByTag("title").first().ownText();
			Product product = new Product(title, file_size, unit_price, "dummy description");
			products.add(product);
		}
		
		ProductList product_list = new ProductList(products);
		logger.info("Product List:" + product_list.toString());
	}

}
