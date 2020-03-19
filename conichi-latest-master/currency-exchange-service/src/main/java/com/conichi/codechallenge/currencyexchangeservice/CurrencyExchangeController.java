package com.conichi.codechallenge.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private CurrencyExchangeService  currencyExchangeService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@Cacheable("currencyExchangeData")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		CurrencyExchange exchangeValue = currencyExchangeService.retrieveExchangeValue(from, to);
		return exchangeValue;

	}
	
	@GetMapping("/currency-exchange/evictcachce")
	@CacheEvict(value="currencyExchangeData", allEntries=true)
	public String evictCache() {
		return messageSource.getMessage("currency.exchange.cache.cleared",null, LocaleContextHolder.getLocale());
	}

}
