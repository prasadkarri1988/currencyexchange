package com.conichi.codechallenge.vatvalidation.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cloudmersive.client.VatApi;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;
import com.cloudmersive.client.model.VatLookupRequest;
import com.cloudmersive.client.model.VatLookupResponse;
import com.conichi.codechallenge.vatvalidation.exception.VatNumberIsNotValid;

@Service
public class VatValidationServiceImpl implements VatValidationService {

	Logger logger = LoggerFactory.getLogger(VatValidationServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Environment env;

	public VatResponse retrieveVatInformation(String vatNumber, boolean checkValidity) {

		ApiClient defaultClient = Configuration.getDefaultApiClient();
		ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
		Apikey.setApiKey(env.getProperty("com.cloudmersive.client.api.key"));
		VatApi apiInstance = new VatApi();
		VatLookupRequest input = new VatLookupRequest();
		input.setVatCode(vatNumber);
		try {
			ModelMapper modelMapper = new ModelMapper();
			VatLookupResponse result = apiInstance.vatVatLookup(input);
			VatResponse response = modelMapper.map(result, VatResponse.class);
			if (checkValidity) {
				if (!response.getValid())
					throw new VatNumberIsNotValid(messageSource.getMessage("vat.number.is.not.valid",
							new Object[] { vatNumber }, LocaleContextHolder.getLocale()));
			}

			
			logger.info("successfully fetched vat information using com.cloudmersive and the details are {}", response);

			return response;

		} catch (ApiException e) {
			throw new VatNumberIsNotValid(messageSource.getMessage("error.while.accessing.vat.validation.api",
					new Object[] { vatNumber }, LocaleContextHolder.getLocale()));
		}
	}

}
