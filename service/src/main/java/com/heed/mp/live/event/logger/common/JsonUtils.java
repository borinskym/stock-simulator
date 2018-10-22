package com.heed.mp.live.event.logger.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class JsonUtils {



    public static Optional<JsonElement> getIn(JsonObject json, String dotSeparatedKeys){
        List<String> keys = parseKeys(dotSeparatedKeys);
        return getInJson(json, keys);
    }

    private static List<String> parseKeys(String dotSeparatedKeys) {
        String[] keys = dotSeparatedKeys.split("\\.");
        if (keys.length == 0)
            return Collections.singletonList(dotSeparatedKeys);
        return Arrays.asList(keys);
    }

    private static Optional<JsonElement> getInJson(JsonObject json, List<String> keys) {
        Optional<JsonElement> current = Optional.ofNullable(json.get(keys.get(0)));
        if (!current.isPresent())
            return Optional.empty();
        if (keys.size() == 1)
            return current;
        else
            return getInJson(current.get().getAsJsonObject(), keys.subList(1, keys.size()));



    }
}
