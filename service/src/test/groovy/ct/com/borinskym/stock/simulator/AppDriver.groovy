package ct.com.borinskym.stock.simulator

import com.borinskym.stock.simulator.Application
import groovyx.net.http.RESTClient

class AppDriver {

    RESTClient client = new RESTClient('http://localhost:8080')
    def receivedGreeting

    def start() {
        Application.main()
    }

    def runSimulation(def simulationRequest) {

        def response = client.post(path: "/v1/simulation/run",
                body: simulationRequest,
                contentType: "application/json"

        )

        response.data
    }

    void retrievedGreetingIs(String greeting) {
        assert this.receivedGreeting.greeting == greeting
    }

    public class RestException extends RuntimeException {
        private Object response;

        public RestException(Object response) {
            this.response = response;
        }

        Object getResponse() {
            return response
        }
    }
}
