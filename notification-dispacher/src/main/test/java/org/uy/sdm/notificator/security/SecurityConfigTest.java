package org.uy.sdm.notificator.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

	@InjectMocks
	private SecurityConfig securityConfig;

	@Test
	public void securityConfigTest() throws Exception {
		final HttpSecurity httpSecurity = mock(HttpSecurity.class);
		when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
		when(httpSecurity.cors(any())).thenReturn(httpSecurity);
		when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
		when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);
		when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
		when(httpSecurity.formLogin(any())).thenReturn(httpSecurity);
		when(httpSecurity.httpBasic(any())).thenReturn(httpSecurity);
		final SecurityFilterChain mockFilterChain = mock(SecurityFilterChain.class);
		doReturn(mockFilterChain).when(httpSecurity).build();

		final SecurityFilterChain filterChain = securityConfig.filterChain(
			httpSecurity
		);
		assertNotNull(filterChain);
		assertThat(filterChain).isSameAs(mockFilterChain);
	}

	@Test
	public void corsConfigurationSourceTest(){
		final CorsConfigurationSource configurationSource = securityConfig.corsConfigurationSource();
		assertNotNull(configurationSource);
	}
}
