package icezhg.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhongjibing on 2016/11/2.
 */
public final class JsonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = ObjectMapperFactory.getObjectMapper();
    }

    private JsonUtil() {
    }

    private static class StringUtil {

        private static boolean isBlank(String str) {
            if (str == null || str.length() == 0) {
                return true;
            }

            for (int i = 0; i < str.length(); i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    private static class ObjectMapperFactory {

        private static ObjectMapper getObjectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                    .registerModule(new SimpleModule());
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return objectMapper;
        }
    }

    public static String toString(Object obj) {
        String retJson = null;
        try {
            retJson = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
        return retJson;
    }

    public static String toString(Object obj, DateFormat dateFormat) {
        String retJson = null;
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            objectMapper.setDateFormat(dateFormat);
            retJson = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
        return retJson;
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        if (!StringUtil.isBlank(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, clazz);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static <T> T toBean(String json, TypeReference<T> typeRef) {
        if (!StringUtil.isBlank(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, typeRef);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

}
