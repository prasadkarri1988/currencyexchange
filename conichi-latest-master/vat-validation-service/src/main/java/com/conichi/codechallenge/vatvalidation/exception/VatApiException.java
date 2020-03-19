package com.conichi.codechallenge.vatvalidation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class VatApiException extends RuntimeException {

	public VatApiException(String message) {
		super(message);
	}

}
