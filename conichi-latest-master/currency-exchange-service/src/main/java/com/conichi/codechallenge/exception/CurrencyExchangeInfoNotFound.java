package com.conichi.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyExchangeInfoNotFound extends RuntimeException {

	public CurrencyExchangeInfoNotFound(String message) {
		super(message);
	}

}
