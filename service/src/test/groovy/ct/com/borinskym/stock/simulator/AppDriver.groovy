package ct.com.borinskym.stock.simulator

import com.borinskym.stock.simulator.Application
import groovyx.net.http.RESTClient
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

class AppDriver {

    RESTClient client = new RESTClient('http://localhost:8080')
    def receivedGreeting
    ConfigurableApplicationContext runContext;
    def start() {
        runContext = SpringApplication.run(Application.class);
    }
    def stop(){
        runContext.stop();
    }

    def runSimulation(def simulationRequest) {

        def response = client.post(path: "/v1/simulation/run",
                body: simulationRequest,
                contentType: "application/json"

        )

        response.data
    }

    def symbols(){
        def response = client.get(path: "/v1/symbols",
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
