package org.uy.sdm.notificator.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uy.sdm.notificator.ApplicationProperties;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ApiKeyFilterTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private FilterChain filterChain;
	private ApiKeyFilter apiKeyFilter;

	private static final String CONTEXT_URL = "/api/notifications";

	@BeforeEach
	public void setup() {
		final ApplicationProperties applicationProperties = new ApplicationProperties();
		applicationProperties.setContextPath("/api");
		applicationProperties.setSecretKey("test-key");
		apiKeyFilter = new ApiKeyFilter(applicationProperties);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		filterChain = mock(FilterChain.class);
		when(request.getRequestURI()).thenReturn(CONTEXT_URL);
	}

	@Test
	public void apyKeyFilterSuccess() throws ServletException, IOException {
		when(request.getHeader(anyString())).thenReturn("test-key");
		apiKeyFilter.doFilterInternal(request,response,filterChain);
		verify(filterChain,atLeastOnce()).doFilter(any(),any());
	}

	@Test
	public void apyKeyFilterForbidden() throws ServletException, IOException {
		when(request.getHeader(anyString())).thenReturn("wrong-key");
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));
		apiKeyFilter.doFilterInternal(request,response,filterChain);
		verify(filterChain,never()).doFilter(any(),any());
	}

}
