package org.uy.sdm.notificator.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class RestConfigTest {

	@InjectMocks
	private RestConfig restConfig;

	@Test
	public void restTemplateTest() {
		final RestTemplate template = restConfig.restTemplate();
		assertNotNull(template);
		assertFalse(template.getInterceptors().isEmpty());
	}
}
