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

    def cleanupSpec() {
        app.stop();
    }

    def "should get all symbols"() {
        when:
        def ans = app.symbols()

        then:
        assert ans == ["gold", "bank", "debentures", "S&P500"]
    }

    def "should fail when symbol not found"() {
        when:
        app.runSimulation([initialAmount     : 4000,
                           percentageBySymbol: [
                                   "NOT_FOUND": 1.0
                           ]])

        then:
        HttpResponseException restException = thrown();
        assert restException.response.status == 400
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
        def ans = app.runSimulation([
                initialAmount     : 4000,
                percentageBySymbol: [
                        "bank"      : 0.25,
                        "debentures": 0.25,
                        "gold"      : 0.25,
                        "S&P500"    : 0.25
                ],
                startDate: "01/2004",
                endDate: "02/2004"
        ])
        then:
        assert ans['endAmount'] == 8000
    }

}