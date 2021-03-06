package io.wia.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class WiaRawJsonObjectDeserializer implements JsonDeserializer<WiaRawJsonObject> {
    public WiaRawJsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        WiaRawJsonObject object = new WiaRawJsonObject();
        object.json = json.getAsJsonObject();
        return object;
    }

}
