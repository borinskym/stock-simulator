package test.com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.InMemoryLiveDataRepository;
import com.heed.mp.live.event.logger.LiveDataRepository;
import com.heed.mp.live.event.logger.common.TimeConversion;
import com.heed.mp.live.event.logger.entities.Score;
import com.heed.mp.live.event.logger.entities.ScoreDetails;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InMemoryLiveDataRepositoryTest {

    private static LiveDataRepository repository = new InMemoryLiveDataRepository();
    private static String eventId = "eventId-1234";
    private static final long scoreTimestamp = TimeConversion.toTimestamp(new DateTime());
    private static final ScoreDetails score = ScoreDetails.builder()
            .timestamp(scoreTimestamp)
            .eventId(eventId)
            .score(Score.builder().awayScore(1).homeScore(0).build())
            .build();

    @Before
    public void setup(){
        repository = new InMemoryLiveDataRepository();
    }

    @Test
    public void shouldSaveScore(){
        repository.save(score);
        List<ScoreDetails> scores = repository.get(eventId, scoreTimestamp + 1000L);
        assertThat(scores.get(0), is(score));
    }

    @Test
    public void shouldReturnScoresAccordingToTimestamp(){
        DateTime now  = new DateTime();
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
