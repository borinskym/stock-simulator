package com.heed.mp.live.event.logger;

import com.google.common.collect.Lists;
import com.heed.mp.live.event.logger.common.TimeConversion;
import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InMemoryLiveDataRepository implements LiveDataRepository {

    private HashMap<String, List<ScoreDetails>> eventToScores;

    public InMemoryLiveDataRepository(){
        init();
    }

    @PostConstruct
    public void init(){
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
        List<ScoreDetails> scoreDetails = Optional.ofNullable(eventToScores.get(eventId)).orElseThrow(() -> new
                EventNotFound(eventId));
        return scoreDetails.stream()
                .filter(s -> s.getTimestamp() <= upto)
                .collect(Collectors.toList());
    }
}
