package com.conichi.codechallenge.currencyexchangeservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long>{
	
	Optional<CurrencyExchange> findByFromAndTo(String from,String to);

}
