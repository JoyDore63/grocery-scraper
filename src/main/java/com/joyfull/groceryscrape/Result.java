package com.joyfull.groceryscrape;

import org.apache.commons.io.FileUtils;

/**
 * @author Joy
 *
 */
public class Result {

	private String title;
	private long file_size;
	private String size;
	private double unit_price;
	private String description;
	
	/**
	 * @param title
	 * @param file_size
	 * @param unit_price
	 * @param description
	 */
	public Result (String title, long file_size, double unit_price, String description) {
		this.title = title;
		this.file_size = file_size;
		this.size = FileUtils.byteCountToDisplaySize(file_size).toLowerCase().replace(" ", "");
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
	public String getSize() {
		return size;
	}

	/**
	 * @param file_size the file_size to set
	 */
	public void setFile_size(long file_size) {
		this.file_size = file_size;
		this.size = FileUtils.byteCountToDisplaySize(file_size).toLowerCase();
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
		return "Result: [title=" + title + ", file_size=" + file_size
				+ ", unit_price=" + unit_price + ", description=" + description
				+ "]";
	}

}
