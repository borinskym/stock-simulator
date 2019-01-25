package ct.com.borinskym.stock.simulator

import com.borinskym.stock.simulator.Application
import groovyx.net.http.RESTClient

class AppDriver {

    RESTClient client = new RESTClient('http://localhost:8080')
    def receivedGreeting

    def start() {
        Application.main()
    }

    def isGreeting() {
        def response = client.get(path: '/v1/greeting')
        assert response.status == 200
        receivedGreeting = response.data
    }

    void retrievedGreetingIs(String greeting) {
        assert this.receivedGreeting.greeting == greeting
    }
}
