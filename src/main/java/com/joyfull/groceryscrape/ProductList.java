package com.joyfull.groceryscrape;

import java.util.List; 

/**
 * @author Joy
 *
 */
public class ProductList {
	private List<Product> products;

	/**
	 * @param products
	 */
	public ProductList(List<Product> products) {
		this.products = products;
	}
	
	/**
	 * 
	 * @return total of all unit prices
	 */
	public double getTotal() {
		double total = 0;
		for (Product prod : this.products){
			total += prod.getUnit_price();
		}
		return total;
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
