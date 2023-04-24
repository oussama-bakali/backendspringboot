package tn.enicarthage.useful;

import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import tn.enicarthage.entities.Role;

@JsonComponent
public class RoleDeserializer extends JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String roleString = node.asText();
        return Role.valueOf(roleString.toUpperCase());
    }
}
