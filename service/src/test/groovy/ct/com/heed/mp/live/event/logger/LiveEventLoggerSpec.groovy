package ct.com.heed.mp.live.event.logger

import ct.com.heed.mp.live.event.logger.support.RabbitDriver
import ct.com.heed.mp.live.event.logger.support.ScoreDetailsApi
import ct.com.heed.mp.live.event.logger.support.ScoresApi
import ct.com.heed.mp.live.event.logger.support.TestUtils
import groovy.json.JsonOutput
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static ct.com.heed.mp.live.event.logger.AppDriver.failOn

class LiveEventLoggerSpec extends Specification {


    private static final String soccerMatchId = 'soccer-1234'

    static final String triggersExchangeName = "triggersExchange"

    @Shared
    AppDriver app = AppDriver.instance

    RabbitDriver rabbitDriver = RabbitDriver.newExchange(triggersExchangeName)

    def setupSpec() {
        app.start()
    }

    def cleanupSpec(){
        app.shutdown()
    }

    def "should fail when event id not found"() {
        expect:
        failOn({app.scores(soccerMatchId)})
                .withResourceNotFoundError()
    }

    def "should retrieve scores given event id according to timestamp"() {
        given:
        def now = TestUtils.now()
        def score = scoreAt(now)
        def requestTime = TestUtils.after(now, 5)
        rabbitDriver.sendTrigger(score, "soccer.goal.sologoal")

        when:
        waitForTrigger()
        ScoresApi scoresSoFar = app.scores(soccerMatchId, requestTime)

        then:
        assert scoresSoFar != null
        assert scoresSoFar.eventId == soccerMatchId
        assert scoresSoFar.timestamp == requestTime
    }

    void waitForTrigger() {
        TimeUnit.SECONDS.sleep(2)
    }

    String scoreAt(Long date) {
        def trigger = [
                'eventId'        : soccerMatchId,
                'originalTrigger': [
                        'header': [
                                'eventTime': TestUtils.toUtc(date)
                        ],
                        'body'  : [
                                'MATCH_ID' : soccerMatchId,
                                'homeScore': 0,
                                'awayScore': 1
                        ]
                ]
        ]

        JsonOutput.toJson(trigger)
    }
}

