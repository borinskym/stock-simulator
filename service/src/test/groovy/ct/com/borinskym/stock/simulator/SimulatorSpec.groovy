package ct.com.borinskym.stock.simulator

import spock.lang.Shared
import spock.lang.Specification

class SimulatorSpec extends Specification {

    @Shared
    AppDriver app = new AppDriver()

    def setupSpec() {
        app.start()
    }


    def "should run simulation"(){
        when:
        def ans = app.runSimulation([initialAmount: 4000,
                                     percentageBySymbol: [
                                             "bank": 0.25,
                                             "debentures": 0.25,
                                             "gold": 0.25,
                                             "S&P500": 0.25
                                     ]])
        then:
        assert ans['endAmount'] == 8000
    }

}