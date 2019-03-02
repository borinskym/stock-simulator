package ct.com.borinskym.stock.simulator

import groovyx.net.http.HttpResponseException
import spock.lang.Shared
import spock.lang.Specification

class SimulatorSpec extends Specification {

    @Shared
    AppDriver app = new AppDriver()

    def setupSpec() {
        app.start()
    }

    def "should fail when percentages doesnt sum to 100"() {
        when:
            app.runSimulation([initialAmount     : 4000,
                                     percentageBySymbol: [
                                             "bank"      : 0.25,
                                             "debentures": 0.25,
                                             "gold"      : 0.25,
                                             "S&P500"    : 0.15
                                     ]])

        then:
        HttpResponseException restException = thrown();
        assert restException.response.status == 400
    }

    def "should run simulation"() {
        when:
        def ans = app.runSimulation([initialAmount     : 4000,
                                     percentageBySymbol: [
                                             "bank"      : 0.25,
                                             "debentures": 0.25,
                                             "gold"      : 0.25,
                                             "S&P500"    : 0.25
                                     ]])
        then:
        assert ans['endAmount'] == 8000
    }

}