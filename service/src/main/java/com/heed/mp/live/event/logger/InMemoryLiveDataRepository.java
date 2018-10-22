package com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.entities.Score;
import com.heed.mp.live.event.logger.entities.ScoreDetails;
import org.springframework.stereotype.Service;

@Service
public class InMemoryLiveDataRepository implements LiveDataRepository {

    @Override
    public ScoreDetails save(ScoreDetails score) {
        return null;
    }
}
