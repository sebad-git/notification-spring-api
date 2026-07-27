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

	@Value("${server.servlet.context-path}")
	@Valid
	private String contextPath;

	@Value("${spring.security.api-key}")
	@Valid
	private String secretKey;
}
