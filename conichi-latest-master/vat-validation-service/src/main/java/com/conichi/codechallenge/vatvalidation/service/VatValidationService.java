package com.conichi.codechallenge.vatvalidation.service;

public interface VatValidationService {

	public VatResponse retrieveVatInformation(String from,boolean checkValidity);


}