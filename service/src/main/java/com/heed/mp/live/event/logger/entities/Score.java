package com.heed.mp.live.event.logger.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Score {
    private Integer homeScore;
    private Integer awayScore;
}
