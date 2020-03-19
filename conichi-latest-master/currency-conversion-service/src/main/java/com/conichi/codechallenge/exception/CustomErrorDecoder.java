package com.conichi.codechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
@Component
public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
		case 400:
			// Do something
			// return new BadRequestException();
			break;
		case 404: {
			if (methodKey.contains("retrieveExchangeValue")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Currency exchange information not found");
			}
			break;
		}
		default:
			return new Exception(response.reason());
		}
		return null;
	}
}
