package com.joyfull.groceryscrape;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Scrapes a grocery site, looking for product data in an expected format
 * @author Joy
 *
 */
public class ScraperService {

	private static final Logger logger = LoggerFactory.getLogger(ScraperService.class);

	/**
	 * Get the size of the html from the link
	 * @param link
	 * @return
	 */
	protected long getFileSize(String link) throws IOException, MalformedURLException {
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
	 * Get the price from the product element
	 * @param source_prod
	 * @return price of the product
	 * @throws UnexpectedFormatException
	 */
	protected double getPrice(Element source_prod) throws UnexpectedFormatException {
		Elements pricePerUnitElements = source_prod.getElementsByClass("pricePerUnit");
		if (pricePerUnitElements.size() != 1) {
			throw new UnexpectedFormatException("Failed to find pricePerUnit");
		}
		String price_str = pricePerUnitElements.first().ownText();
		return(parsePrice(price_str));
	}

	/**
	 * Get the price as a double from the string like "&pound3.2"
	 * @param price_str
	 * @return
	 */
	protected double parsePrice(String price_str) {
		double price = 0.0;
		Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+))");
		Matcher matcher = pattern.matcher(price_str);
		matcher.find();
		try {
			price = Double.parseDouble(matcher.group(1));
		}
		catch (Exception exception) {
			logger.error("Invalid price string:" + price_str);
		}
		return(price);
	}

	/**
	 * Get a link to details from the product element
	 * @param source_prod
	 * @return
	 * @throws UnexpectedFormatException
	 */
	protected String getLinkToDetailsPage(Element source_prod) throws UnexpectedFormatException {
		Elements references = source_prod.getElementsByTag("a");
		if (references.size() < 1) {
			throw new UnexpectedFormatException("Link to details page not found");
		}
		String detail_link = references.first().attr("abs:href");
		
		return detail_link;
	}

	/**
	 * Get the product title from details
	 * @param detail_doc
	 * @return
	 * @throws IOException
	 * @throws UnexpectedFormatException
	 */
	protected String getTitle(Document detail_doc) throws IOException, UnexpectedFormatException {
		
		Elements titleElements = detail_doc.getElementsByTag("title");
		if (titleElements.size() < 1) {
			throw new UnexpectedFormatException("Failed to find title for product");
		}

		return(titleElements.first().ownText());
	}

	/**
	 * Get the product description from the details
	 * @param detail_doc
	 * @return
	 */
	protected String getDescription(Document detail_doc) {
		String description = "";

		Elements metaElements = detail_doc.getElementsByTag("meta");
		for (Element meta: metaElements) {
			String name = meta.attr("name");
			if (name != null && name.equals("description")){
				description = meta.attr("content");
			}
		}

		return description;
	}

	/**
	 * Get the product data from the given URL, as a list of Product
	 * @param url
	 * @return List of products as Result objects
	 * @throws IOException
	 * @throws UnexpectedFormatException
	 */
	public List<Result> getResults(String url) throws IOException, UnexpectedFormatException {
		List<Result> results = new ArrayList<Result>();
		
		// Get main URL as doc
		Document doc = Jsoup.connect(url).get();
		
		// Get products list
		Elements source_products = doc.getElementsByClass("product");
		logger.debug("Found this many products " + source_products.size());
		// Each product: get details
		for (Element source_prod : source_products) {
			// Unit price is in products list on first page, ie within current element
			double unit_price = getPrice(source_prod);
			// Get link to details page
			String detail_link = getLinkToDetailsPage(source_prod);
			// Get size of linked file
			long file_size = getFileSize(detail_link);
			// Connect to link to get title and description from details page
			Document detail_doc = Jsoup.connect(detail_link).get();
			String title = getTitle(detail_doc);
			String description = getDescription(detail_doc);
			// Build up results list
			Result result = new Result(title, file_size, unit_price, description);
			results.add(result);
		}

		return results;
	}

}
