package com.heed.mp.live.event.logger;

import com.google.common.collect.Lists;
import com.heed.mp.live.event.logger.common.TimeConversion;
import com.heed.mp.live.event.logger.entities.ScoreDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InMemoryLiveDataRepository implements LiveDataRepository {

    private final HashMap<String, List<ScoreDetails>> eventToScores;

    public InMemoryLiveDataRepository(){
        eventToScores = new HashMap<>();
    }

    @Override
    public ScoreDetails save(ScoreDetails score) {
        String eventId = score.getEventId();
        List<ScoreDetails> scores = eventToScores.getOrDefault(eventId, Lists.newArrayList());
        scores.add(score);
        eventToScores.put(eventId, scores);
        log.info("saved score of {} at {}", eventId, TimeConversion.toUtc(score.getTimestamp()));
        return score;
    }

    @Override
    public List<ScoreDetails> get(String eventId, Long upto) {
        List<ScoreDetails> scoreDetails = eventToScores.get(eventId);
        return scoreDetails.stream()
                .filter(s -> s.getTimestamp() <= upto)
                .collect(Collectors.toList());
    }
}
