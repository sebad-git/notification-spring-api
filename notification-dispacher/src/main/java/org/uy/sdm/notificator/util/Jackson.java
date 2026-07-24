package org.uy.sdm.notificator.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class Jackson {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Jackson() {}

	static {
		//OBJECT_MAPPER.registerModule(new GuavaModule());
		//OBJECT_MAPPER.registerModule(new AfterburnerModule());
		OBJECT_MAPPER.registerModule(new Jdk8Module());
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		OBJECT_MAPPER.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
	}

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
