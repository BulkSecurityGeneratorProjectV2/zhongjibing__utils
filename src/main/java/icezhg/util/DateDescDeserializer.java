package icezhg.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by zhongjibing on 2016/11/2.
 */
public class DateDescDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            String value = jp.getText();
            if (!StringUtil.isBlank(value)) {
                JsonNode node = jp.getCodec().readTree(jp);
                String desc = node.get("desc").asText();
                return new SimpleDateFormat("yyyy-MM-dd").parse(desc);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
