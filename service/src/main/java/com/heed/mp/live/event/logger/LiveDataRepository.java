package com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.entities.Score;
import com.heed.mp.live.event.logger.entities.ScoreDetails;

public interface LiveDataRepository {
    ScoreDetails save(ScoreDetails score);
}
