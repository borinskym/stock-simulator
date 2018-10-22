package test.com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.LiveDataRepository;
import com.heed.mp.live.event.logger.ScoresListener;
import com.heed.mp.live.event.logger.entities.internal.Score;
import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ScoresListenerTest {

    private static final String eventId = "soccer-1234";
    private static final String triggerTimeStr = "2018-10-22T12:52:23.189Z";
    private static final Long triggerTimestamp = 1540212743189L;

    private static String raw = "{\"eventId\":\"" + eventId + "\"," +
            "\"originalTrigger\":{\"header\":{\"eventTime\":\"" + triggerTimeStr + "\"},\"body\":{\"MATCH_ID\":\"" + eventId + "\",\"homeScore\":0,\"awayScore\":1}}}";

    private static final Message message = MessageBuilder
            .withBody(raw.getBytes())
            .build();

    private LiveDataRepository repository = mock(LiveDataRepository.class);
    private ScoresListener listener = ScoresListener.builder()
            .repository(repository)
            .build();

    private ScoreDetails parsedMessage = ScoreDetails.builder()
            .eventId(eventId)
            .score(Score.builder().awayScore(1).homeScore(0).build())
            .timestamp(triggerTimestamp)
            .build();


    @Test
    public void shouldSaveParsedScoreInRepository(){
        listener.receiveMessage(message);
        verify(repository).save(parsedMessage);
    }
}
