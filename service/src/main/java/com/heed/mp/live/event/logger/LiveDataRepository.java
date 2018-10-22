package com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;

import java.util.List;

public interface LiveDataRepository {

    ScoreDetails save(ScoreDetails score);
    List<ScoreDetails> get(String eventId, Long upto);


    class EventNotFound extends RuntimeException {
        public EventNotFound(String message){
            super(message);
        }
    }
}
