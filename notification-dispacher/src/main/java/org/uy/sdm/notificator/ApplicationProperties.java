package org.uy.sdm.notificator;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Validated
public class ApplicationProperties {

	public static final String API_KEY = "X-API-KEY";

	@Value("${server.servlet.context-path}")
	@Valid
	private String contextPath;

	@Value("${app.api-key}")
	@Valid
	private String secretKey;

	@Value("${app.service.url:http://localhost:3001/api/notify}")
	@Valid
	private String receiverServiceUrl;


}
