package icezhg.util;

import java.io.IOException;
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

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .registerModule(new SimpleModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private JsonUtil() {
    }

    public static String toString(Object obj) {
        String retJson = null;
        try {
            retJson = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
        return retJson;
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            if (!StringUtil.isBlank(json)) {
                return objectMapper.readValue(json, clazz);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toBean(String json, TypeReference<T> typeRef) {
        try {
            if (!StringUtil.isBlank(json)) {
                return objectMapper.readValue(json, typeRef);
            }
        } catch (IOException e) {
            System.out.println(e);
            LOG.error(e.getMessage(), e);
        }
        return null;
    }


}
