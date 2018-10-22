package test.com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.InMemoryLiveDataRepository;
import com.heed.mp.live.event.logger.LiveDataRepository;
import com.heed.mp.live.event.logger.common.TimeConversion;
import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InMemoryLiveDataRepositoryTest {

    private static String eventId = "eventId-1234";
    private static LiveDataRepository repository;
    private DateTime now;

    @Before
    public void setup(){
        now = new DateTime();;
        repository = new InMemoryLiveDataRepository();
    }

    @Test(expected = InMemoryLiveDataRepository.EventNotFound.class)
    public void shouldFailIfEventNotFound(){
        repository.get("someEvent", 0L);
    }

    @Test
    public void shouldSaveScore(){
        ScoreDetails score = scoreAt(now);
        repository.save(score);
        List<ScoreDetails> scores = repository.get(eventId, TimeConversion.toTimestamp(now.plusSeconds(1)));
        assertThat(scores.get(0), is(score));
    }

    @Test
    public void shouldReturnScoresAccordingToTimestamp(){
        repository.save(scoreAt(now));
        repository.save(scoreAt(now.plusSeconds(1)));
        repository.save(scoreAt(now.plusSeconds(4)));
        long upto = TimeConversion.toTimestamp(now.plusSeconds(2));
        List<ScoreDetails> scores = repository.get(eventId, upto);
        assert scores.size() == 2;
        assert scores.stream().allMatch(s -> s.getTimestamp() <= upto);
    }

    private ScoreDetails scoreAt(DateTime dateTime) {
        return ScoreDetails.builder()
                .timestamp(TimeConversion.toTimestamp(dateTime))
                .eventId(eventId)
                .build();
    }
}
