package com.joyfull.groceryscrape;

import java.text.DecimalFormat;
import java.util.List; 

/**
 * @author Joy
 *
 */
public class ProductList {
	
	private static DecimalFormat decimalFormat2 = new DecimalFormat(".##");
	
	private List<Product> products;

	/**
	 * @param products
	 */
	public ProductList(List<Product> products) {
		this.products = products;
	}
	
	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * 
	 * @return total of all unit prices
	 */
	public String getTotal() {
		double total = 0;
		for (Product prod : this.products){
			total += prod.getUnit_price();
		}
		return (decimalFormat2.format(total));
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder(300);
		str.append("Products:\n");
		for (Product prod : this.products) {
			str.append(prod.toString() + "\n");
		}
		str.append("Total=" + this.getTotal());
		return str.toString();
	}

}
