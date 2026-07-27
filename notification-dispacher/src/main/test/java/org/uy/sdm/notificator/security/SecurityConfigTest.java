package org.uy.sdm.notificator.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.uy.sdm.notificator.controllers.NotificationController;
import org.uy.sdm.notificator.modules.notification.service.NotificationService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

	@InjectMocks
	private SecurityConfig securityConfig;

	@Mock
	private ApiKeyFilter apiKeyFilter;

	@Mock
	private HttpSecurity http;

	@Test
	public void securityConfigTest() throws Exception {
		assertNotNull(securityConfig.filterChain(http).getFilters());
	}
}
