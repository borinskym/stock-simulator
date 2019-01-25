package ct.com.borinskym.stock.simulator

import spock.lang.Shared
import spock.lang.Specification

class HelloWorldSpec extends Specification {

    @Shared
    AppDriver app = new AppDriver()

    def setupSpec() {
        app.start()
    }

    def "should retrieve greeting"() {
        when:
        app.isGreeting()

        then:
        app.retrievedGreetingIs('Hello World!')
    }

}