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


    def "should run dummy simulation"(){
        when:
        def ans = app.runSimulation([strategy: 'harry-light',
                           initialAmount: 4000])
        then:
        assert ans['endAmount'] == 8000
    }

    def "should fail when strategy not found"(){
        when:
        def ans = app.runSimulation([strategy: 'not-found',
                                     initialAmount: 4000])
        then:
        HttpResponseException ex = thrown()
        ex.response.status == 400
    }

}