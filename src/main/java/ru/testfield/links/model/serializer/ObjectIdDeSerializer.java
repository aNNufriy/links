package ru.testfield.links.model.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdDeSerializer extends JsonDeserializer<ObjectId> {
    @Override
    public ObjectId deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return new ObjectId(jp.getText());
    }
}
