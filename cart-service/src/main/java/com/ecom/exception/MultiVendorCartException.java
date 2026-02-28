package com.ecom.exception;

@SuppressWarnings("serial")
public class MultiVendorCartException extends RuntimeException {

	public MultiVendorCartException(String message) {
		super(message);
	}
}
