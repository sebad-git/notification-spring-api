package org.uy.sdm.notificator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonTest {

	@Test
	public void toJsonStringTest() throws JsonProcessingException {
		final TestObject testObject = new TestObject();
		testObject.setName("TestName");
		testObject.setAge(25);
		final String json = Jackson.toJsonString(testObject);
		assertEquals("{\"name\":\"TestName\",\"age\":25}",json);
	}

	@Test
	public void toObjectTest() throws JsonProcessingException {
		final String json = "{\"name\":\"TestName\",\"age\":25}";
		final TestObject testObject = Jackson.toObject(json, TestObject.class);
		assertEquals(25, testObject.getAge());
		assertEquals("TestName", testObject.getName());
	}

	@Data
	private static class TestObject implements Serializable {
		private String name;
		private int age;
	}

}
