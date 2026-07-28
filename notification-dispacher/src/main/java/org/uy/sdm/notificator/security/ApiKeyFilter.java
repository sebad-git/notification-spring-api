package org.uy.sdm.notificator.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.uy.sdm.notificator.ApplicationProperties;
import org.uy.sdm.notificator.util.Jackson;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

	private final String secretKey;
	private final String contextPath;

	public ApiKeyFilter(ApplicationProperties properties) {
		this.secretKey = properties.getSecretKey();
		this.contextPath= properties.getContextPath();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException {
		String path = request.getRequestURI();
		if (path.startsWith(String.format("%s/notifications",this.contextPath))) {
			final String apiKey = request.getHeader(ApplicationProperties.API_KEY);
			if (Strings.isEmpty(apiKey) || !apiKey.equals(this.secretKey)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write(new CredentialNotFoundError().toJson());
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	private static class CredentialNotFoundError {
		private String error = "No se encontaron las credenciales o son invalidas";
		public String toJson() throws JsonProcessingException {
			return Jackson.toJsonString(this);
		}
	}

}
