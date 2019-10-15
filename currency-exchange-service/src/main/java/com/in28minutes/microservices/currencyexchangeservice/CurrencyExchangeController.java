package com.in28minutes.microservices.currencyexchangeservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepository exchangeValueRepository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable final String from, @PathVariable final String to)
			throws NotFoundException {

		ExchangeValue exchangeValue = new ExchangeValue(null, from, to, null);

		final Optional<ExchangeValue> findOne = exchangeValueRepository.findOne(Example.of(exchangeValue));

		exchangeValue = findOne.orElseThrow(() -> new NotFoundException("Exchange not found"));

		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}
}
