package com.conichi.codechallenge.vatvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VatValidationControllerIntegrationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testRetrieveVatCountryCode() throws JSONException {
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/vat/validation/")
				.queryParam("vatNumber", "DE257486969").queryParam("checkValidity", "true");
		String uriBuilder = builder.build().encode().toUriString();

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(uriBuilder), HttpMethod.GET, entity,
				String.class);

		String expected = "{countryCode:DE,vatNumber:257486969}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	public void testRetrieveVatDataForException() throws JSONException {
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/vat/validation/")
				.queryParam("vatNumber", "DB257486969").queryParam("checkValidity", "true");
		String uriBuilder = builder.build().encode().toUriString();

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(uriBuilder), HttpMethod.GET, entity,
				String.class);

		assertThat((response.getStatusCode().equals(HttpStatus.NOT_FOUND)));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
