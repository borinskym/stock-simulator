package com.heed.mp.live.event.logger.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreDetails {
    private String eventId;
    private Long timestamp;
    private Score score;

    public Integer homeScore() {
        return score.getHomeScore();
    }

    public Integer awayScore() {
        return score.getAwayScore();
    }
}
