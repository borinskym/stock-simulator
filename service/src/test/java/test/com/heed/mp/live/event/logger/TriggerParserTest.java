package test.com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.TriggerParser;
import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TriggerParserTest {
    private static final String eventId = "soccer-1234";
    private static final String triggerTimeStr = "2018-10-22T12:52:23.189Z";
    private static final Long triggerTimestamp = 1540212743189L;

    private String message = "{\"eventId\":\"" + eventId + "\"," +
            "\"originalTrigger\":{\"header\":{\"eventTime\":\"" + triggerTimeStr + "\"},\"body\":{\"MATCH_ID\":\"" + eventId + "\",\"homeScore\":0,\"awayScore\":1}}}";


    @Test
    public void shouldParseEventIdFromMessage() {
        ScoreDetails result = new TriggerParser().parse(message);
        assertThat(result.getEventId(), is(eventId));
    }

    @Test
    public void shouldParseDateToTimestamp() {
        ScoreDetails result = new TriggerParser().parse(message);
        assertThat(result.getTimestamp(), is(triggerTimestamp));
    }

    @Test
    public void shouldParseScores(){
        ScoreDetails result = new TriggerParser().parse(message);
        assertThat(result.homeScore(), is(0));
        assertThat(result.awayScore(), is(1));
    }

}
