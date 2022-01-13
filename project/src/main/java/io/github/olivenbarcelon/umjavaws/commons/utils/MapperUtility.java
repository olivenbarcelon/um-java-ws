package io.github.olivenbarcelon.umjavaws.commons.utils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @since 2021-11-25 [JDK11]
 * @version 2022-01-13
 * @author Oliven C. Barcelon
 */
public class MapperUtility {
    private static ObjectMapper mapper = new ObjectMapper();

    private static boolean isJson(String input) {
        try {
            mapper.readTree(input);
            return true;
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Convert {@link Object} to JSON {@link String}
     * @param object input that convert into json
     * @return {@link String}
     * @since 2021-11-25 [JDK11]
     * @version 2022-01-13
     * @author Oliven C. Barcelon
     */
    public static String toJson(Object object) {
        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Convert Object to JSON Failed");
        }
    }

    public static String toJson(Map<String, Object> map) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Convert Map to JSON Failed");
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object object) {
        try {
            if(object instanceof String) {
                String json = object.toString();
                if(isJson(json)) return mapper.readValue(json, Map.class);
            }
            return toMap(toJson(object));
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Convert from " + object.getClass().getSimpleName() + " to Map Failed");
        }
    }
}
