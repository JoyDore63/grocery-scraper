package com.joyfull.groceryscrape;

import java.text.DecimalFormat;
import java.util.List; 

/**
 * @author Joy
 *
 */
public class Results {
	
	private static DecimalFormat decimalFormat2 = new DecimalFormat(".##");
	
	private List<Result> results;

	/**
	 * @param results
	 */
	public Results(List<Result> results) {
		this.results = results;
	}
	
	/**
	 * @return the results
	 */
	public List<Result> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Result> results) {
		this.results = results;
	}

	/**
	 * 
	 * @return total of all unit prices
	 */
	public String getTotal() {
		double total = 0;
		for (Result prod : this.results){
			total += prod.getUnit_price();
		}
		return (decimalFormat2.format(total));
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder(300);
		str.append("Results:\n");
		for (Result prod : this.results) {
			str.append(prod.toString() + "\n");
		}
		str.append("Total=" + this.getTotal());
		return str.toString();
	}

}
