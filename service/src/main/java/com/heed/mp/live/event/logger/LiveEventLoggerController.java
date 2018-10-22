package com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.common.TimeConversion;
import com.heed.mp.live.event.logger.entities.external.ScoresApi;
import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class LiveEventLoggerController {


    private final LiveDataRepository repository;

    @Autowired
    public LiveEventLoggerController(LiveDataRepository repository){
        this.repository = repository;
    }

    @GetMapping("/events/{eventId}/scores")
    public ResponseEntity<ScoresApi> scores(@PathVariable("eventId") String eventId, @RequestParam("upto") Long upto) {
        log.info("request scores of event {} up to {}", eventId, TimeConversion.toUtc(upto));
        List<ScoreDetails> body = repository.get(eventId, upto);
        log.info("found scores of event {}: {}", eventId, body);
        return ResponseEntity.ok(createResponse(eventId, upto, body));
    }

    private ScoresApi createResponse(String eventId, Long upto, List<ScoreDetails> body) {
        return ScoresApi.builder()
                .eventId(eventId)
                .timestamp(upto)
                .scores(body)
                .build();
    }


}

