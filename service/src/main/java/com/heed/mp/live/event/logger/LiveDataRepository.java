package com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.entities.Score;
import com.heed.mp.live.event.logger.entities.ScoreDetails;

import java.util.List;

public interface LiveDataRepository {

    ScoreDetails save(ScoreDetails score);
    List<ScoreDetails> get(String eventId, Long upto);
}
