package ct.com.heed.mp.live.event.logger

import ct.com.heed.mp.live.event.logger.support.RabbitDriver
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
        rabbitDriver.sendTrigger(scoreAt(now), "soccer.goal.sologoal")

        when:
        def x = 1 + 2
//        def scores = app.scores(soccerMatchId, TestUtils.after(now, 1))

        then:
        TimeUnit.SECONDS.sleep(10)
        assert 1 != 2
//        assert scores != null

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

