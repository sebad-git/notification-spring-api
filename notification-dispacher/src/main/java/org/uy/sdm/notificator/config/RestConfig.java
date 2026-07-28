package org.uy.sdm.notificator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@Slf4j
public class RestConfig {

	private static final int CONNECTION_TIMEOUT = 5 * 1000;
	private static final int RESPONSE_TIMEOUT = 10 * 1000;

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(CONNECTION_TIMEOUT);
		factory.setReadTimeout(RESPONSE_TIMEOUT);
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.setInterceptors(List.of((request, body, execution) -> {
			log.debug("Request: Method:[{}] Url:[{}]", request.getMethod(), request.getURI());
			ClientHttpResponse response = execution.execute(request, body);
			log.debug("Response status: [{}]", response.getStatusCode());
			return response;
		}));
		return restTemplate;
	}
}
