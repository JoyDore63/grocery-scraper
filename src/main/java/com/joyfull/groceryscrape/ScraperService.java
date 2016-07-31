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


public class ScraperService {

	private static final Logger logger = LoggerFactory.getLogger(ScraperService.class);

	/**
	 * 
	 * @param link
	 * @return
	 */
	protected static long getFileSize(String link) throws IOException, MalformedURLException {
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

	protected static double getPrice(Element source_prod) throws UnexpectedFormatException {
		Elements pricePerUnitElements = source_prod.getElementsByClass("pricePerUnit");
		if (pricePerUnitElements.size() != 1) {
			throw new UnexpectedFormatException("Failed to find pricePerUnit");
		}
		String price_str = pricePerUnitElements.first().ownText();
		return(parsePrice(price_str));
	}

	protected static double parsePrice(String price_str) {
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

	protected static String getLinkToDetailsPage(Element source_prod) throws UnexpectedFormatException {
		Elements references = source_prod.getElementsByTag("a");
		if (references.size() < 1) {
			throw new UnexpectedFormatException("Link to details page not found");
		}
		String detail_link = references.first().attr("abs:href");
		
		return detail_link;
	}

	protected static String getTitle(Document detail_doc) throws IOException, UnexpectedFormatException {
		
		Elements titleElements = detail_doc.getElementsByTag("title");
		if (titleElements.size() < 1) {
			throw new UnexpectedFormatException("Failed to find title for product");
		}

		return(titleElements.first().ownText());
	}

	protected static String getDescription(Document detail_doc) {
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
	 * @return
	 * @throws IOException
	 * @throws UnexpectedFormatException
	 */
	public List<Product> getProducts(String url) throws IOException, UnexpectedFormatException {
		List<Product> products = new ArrayList<Product>();
		
		// Get main URL as doc
		Document doc = Jsoup.connect(url).get();
		
		// Get products list
		Elements source_products = doc.getElementsByClass("product");
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
			// Build up product list
			Product product = new Product(title, file_size, unit_price, description);
			products.add(product);
		}

		return products;
	}

}
