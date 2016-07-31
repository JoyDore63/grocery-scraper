package com.joyfull.groceryscrape;

/**
 * @author Joy
 *
 */
public class Product {

	private String title;
	private long file_size;
	private double unit_price;
	private String description;
	
	/**
	 * @param title
	 * @param file_size
	 * @param unit_price
	 * @param description
	 */
	public Product (String title, long file_size, double unit_price, String description) {
		this.title = title;
		this.file_size = file_size;
		this.unit_price = unit_price;
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the file_size
	 */
	public long getFile_size() {
		return file_size;
	}

	/**
	 * @param file_size the file_size to set
	 */
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}

	/**
	 * @return the unit_price
	 */
	public double getUnit_price() {
		return unit_price;
	}

	/**
	 * @param unit_price the unit_price to set
	 */
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [title=" + title + ", file_size=" + file_size
				+ ", unit_price=" + unit_price + ", description=" + description
				+ "]";
	}

}
