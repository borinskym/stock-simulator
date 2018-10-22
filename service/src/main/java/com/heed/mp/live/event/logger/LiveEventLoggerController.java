package com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.common.TimeConversion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class LiveEventLoggerController {

    @GetMapping("/events/{eventId}/scores")
    public ResponseEntity<String> scores(@PathVariable("eventId") String eventId, @RequestParam("upto") Long upto) {
        log.info("request scores of event {} up to {}", eventId, TimeConversion.toUtc(upto));
        return ResponseEntity.ok(null);
    }


}

