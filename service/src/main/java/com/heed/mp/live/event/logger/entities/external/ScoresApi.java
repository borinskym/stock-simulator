package com.heed.mp.live.event.logger.entities.external;

import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ScoresApi {
    private String eventId;
    private Long timestamp;
    private List<ScoreDetails> scores;
}
