package test.com.heed.mp.live.event.logger.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.heed.mp.live.event.logger.common.JsonUtils;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonUtilsTest {

    private static final String eventId = "12345";
    private static final String nestedValue = "0.13962210982693746";
    private static String message = "{\n" +
            "  \"eventId\": \"" + eventId + "\",\n" +
            "  \"competitors\": {\n" +
            "    \"away\": {\n" +
            "      \"dangerIndex\": {\n" +
            "        \"value\": " + nestedValue + ",\n" +
            "        \"value30\": 0.16,\n" +
            "        \"value120\": 0.13\n" +
            "      }\n" +
            "    },\n" +
            "    \"home\": {\n" +
            "      \"dangerIndex\": {\n" +
            "        \"value\": 0.30963765289519857,\n" +
            "        \"value30\": 0.47,\n" +
            "        \"value120\": 0.39\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"time\": 1540124860000\n" +
            "}";

    private static JsonObject input = new Gson().fromJson(message, JsonObject.class);

    @Test
    public void shouldGetValueFromJsonByKeys(){
        Optional<JsonElement> x = JsonUtils.getIn(input, "eventId");
        assert x.isPresent();
        assertThat(x.get().getAsString(), is(eventId));
    }

    @Test
    public void shouldGetNestedValueFromJsonByKeys(){
        Optional<JsonElement> x = JsonUtils.getIn(input, "competitors.away.dangerIndex.value");
        assert x.isPresent();
        assertThat(x.get().getAsString(), is(nestedValue));
    }

    @Test
    public void shouldReturnOptionalEmptyForInvalidKey(){
        Optional<JsonElement> x = JsonUtils.getIn(input, "competitors.invalidKey.dangerIndex.value");
        assertThat(x.isPresent(), is(false));
    }
}
