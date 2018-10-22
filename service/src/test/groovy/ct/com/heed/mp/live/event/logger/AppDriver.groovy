package ct.com.heed.mp.live.event.logger

import com.heed.mp.live.event.logger.Application
import ct.com.heed.mp.live.event.logger.support.ScoresApi
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.springframework.http.HttpStatus

@Singleton
class AppDriver {

    RESTClient client = new RESTClient('http://localhost:8080')

    def start() {
        Application.main()
    }

    def shutdown() {
        Application.close();
    }

    ScoresApi scores(String eventId, Long upto = 0L) {
        def response = client.get(
                [path : "/v1/events/${eventId}/scores",
                 query: ['upto': upto]])
        response.data
    }

    static AppDriverErrorMatcher failOn(command) {
        HttpResponseException error
        try {
            command.call()
        } catch (HttpResponseException e) {
            error = e
        }

        return new AppDriverErrorMatcher(error)
    }

    static class AppDriverErrorMatcher {
        HttpResponseException error

        AppDriverErrorMatcher(HttpResponseException e) {
            error = e
        }

        void withResourceNotFoundError() {
            assert error.response.status == HttpStatus.NOT_FOUND.value()
        }

    }
}
