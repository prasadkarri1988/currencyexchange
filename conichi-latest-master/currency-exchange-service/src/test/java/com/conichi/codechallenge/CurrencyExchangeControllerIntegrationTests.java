package com.conichi.codechallenge;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyExchangeControllerIntegrationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	

	@Test
	public void testRetrieveExchangeValue() throws JSONException {
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/currency-exchange/from/USD/to/INR"),
				HttpMethod.GET, entity, String.class);

		String expected = "{id:10001,from:USD,to:INR,conversionMultiple:65.00}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	public void testRetrieveExchangeValueForException() throws JSONException {
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/currency-exchange/from/XXX/to/INR"),
				HttpMethod.GET, entity, String.class);
		
		assertThat((response.getStatusCode().equals(HttpStatus.NOT_FOUND)));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}



