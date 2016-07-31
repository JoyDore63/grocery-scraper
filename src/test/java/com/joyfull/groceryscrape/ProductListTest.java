package com.joyfull.groceryscrape.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.joyfull.groceryscrape.Product;
import com.joyfull.groceryscrape.ProductList;

public class ProductListTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductListTest.class);

	@Test
	public void testProductTotal() {
		Product p1 = new Product("Avacodo", 12345L, 1.8, "Fresh and green");
		Product p2 = new Product("Peach", 12345L, 1.2, "Fresh and yellow");
		Product p3 = new Product("Plum", 12345L, 1.5, "Fresh and purple");
		ArrayList<Product> products = new ArrayList<Product>(3);
		products.add(p1);
		products.add(p2);
		products.add(p3);
		
		ProductList list = new ProductList(products);
		logger.info(list.toString());
		
		assertEquals(4.5, list.getTotal(), 0.0001);
	}

}
