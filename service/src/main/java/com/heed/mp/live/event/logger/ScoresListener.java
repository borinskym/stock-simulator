package com.heed.mp.live.event.logger;

import com.heed.mp.live.event.logger.entities.internal.ScoreDetails;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;


@Service
@Slf4j
@Builder
public class ScoresListener {

    private LiveDataRepository repository;

    @Autowired
    public ScoresListener(LiveDataRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${message-queue.names.liveEventLogger}")
    public void receiveMessage(Message message) {
        ScoreDetails score = new TriggerParser().parse(bodyOf(message));
        repository.save(score);
    }

    private String bodyOf(Message message) {
        return new String(message.getBody(), Charset.forName("UTF-8"));
    }
}
