package com.conichi.codechallenge.currencyexchangeservice;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.conichi.codechallenge.exception.CurrencyExchangeInfoNotFound;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public CurrencyExchange retrieveExchangeValue(String from, String to) {
		
		Optional<CurrencyExchange> exchangeValue = currencyExchangeRepository.findByFromAndTo(from, to);
		if (!exchangeValue.isPresent())
			throw new CurrencyExchangeInfoNotFound(messageSource.getMessage("currency.exchange.data.not.found",
					new Object[] { from, to }, LocaleContextHolder.getLocale()));

		return exchangeValue.get();
	}

}
