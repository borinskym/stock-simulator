package ct.com.agt.mes.helloworld

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
        app.fetchGreeting()

        then:
        app.retrievedGreetingIs('Hello World!')
    }

}