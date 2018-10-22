package com.heed.mp.live.event.logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.heed.mp.live.event.logger.common.JsonUtils;
import com.heed.mp.live.event.logger.common.TimeConversion;
import com.heed.mp.live.event.logger.entities.Score;
import com.heed.mp.live.event.logger.entities.ScoreDetails;

import java.util.function.Function;

public class TriggerParser {

    public ScoreDetails parse(String json) {
        JsonObject raw = new Gson().fromJson(json, JsonObject.class);
        return ScoreDetails.builder()
                .eventId(getIfPresent(raw, "eventId", JsonElement::getAsString))
                .timestamp(parseDate(raw))
                .score(parseScores(raw))
                .build();
    }

    private Long parseDate(JsonObject raw) {
        return TimeConversion.toTimestamp(getIfPresent(raw,
                "originalTrigger.header.eventTime",
                JsonElement::getAsString));
    }

    private Score parseScores(JsonObject raw) {
        return Score.builder()
                .awayScore(getIfPresent(raw, "originalTrigger.body.awayScore", JsonElement::getAsInt))
                .homeScore(getIfPresent(raw, "originalTrigger.body.homeScore", JsonElement::getAsInt))
                .build();
    }

    private <T> T getIfPresent(JsonObject raw, String key, Function<JsonElement, T> getAs) {
        return JsonUtils.getIn(raw, key).map(getAs).orElse(null);
    }

}
