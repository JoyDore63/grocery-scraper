package com.joyfull.groceryscrape;

/**
 * Exception used when the html is not formatted as expected
 * @author Joy
 *
 */
public class UnexpectedFormatException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UnexpectedFormatException() {
		// TODO Auto-generated constructor stub
	}

	public UnexpectedFormatException(String message) {
		super(message);
	}

	public UnexpectedFormatException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UnexpectedFormatException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UnexpectedFormatException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
