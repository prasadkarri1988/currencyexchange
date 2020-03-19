package com.conichi.codechallenge.currencyexchangeservice;

public interface CurrencyExchangeService {

	CurrencyExchange retrieveExchangeValue(String from, String to);

}