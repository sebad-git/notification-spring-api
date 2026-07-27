package org.uy.sdm.notificator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

public class NotificationDispatcherAppTest {

	@Test
	public void mainTest() {
		try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
			NotificationDispatcherApp.main(new String[]{});
			mockedSpringApplication.verify(() ->
				SpringApplication.run(NotificationDispatcherApp.class, new String[]{})
			);
		}
	}

}
