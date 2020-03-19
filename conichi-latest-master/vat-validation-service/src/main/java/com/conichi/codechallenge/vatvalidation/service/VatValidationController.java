package com.conichi.codechallenge.vatvalidation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vat/validation")
public class VatValidationController {
	
	@Autowired
	private VatValidationService vatValidationService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	@Cacheable("vatData")
	public VatResponse retrieveVatCuntryCode(@RequestParam("vatNumber") String vatNumber,
            @RequestParam("checkValidity") boolean checkValidity) {
		
		VatResponse response = vatValidationService.retrieveVatInformation(vatNumber,checkValidity);
		return response;
	}
	
	@GetMapping("/evictcachce")
	@CacheEvict(value="vatData", allEntries=true)
	public String evictCache() {
		return messageSource.getMessage("vat.data.cache.cleared",null, LocaleContextHolder.getLocale());
	}
	
}
