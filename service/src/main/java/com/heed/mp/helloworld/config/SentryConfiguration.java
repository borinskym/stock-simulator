package com.heed.mp.helloworld.config;

import io.sentry.Sentry;
import io.sentry.spring.SentryExceptionResolver;
import io.sentry.spring.SentryServletContextInitializer;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class SentryConfiguration {

    @Value("${sentry.dsn:}")
    public String dsn;

    @PostConstruct
    public void init() {
        if(!dsn.isEmpty()) {
            Sentry.init(dsn +
                    "?servername=" + serverName() +
                    "&environment=" + env() +
                    "&stacktrace.app.packages=com.heed" +
                    "&tags=lang:java");
        }
    }

    private String serverName() {
        Pattern pattern = Pattern.compile("(.*)-(green|blue)-.*");
        String hostname = System.getenv("HOSTNAME");
        Matcher matcher = pattern.matcher(hostname);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return hostname;
    }

    private String env() {
        return System.getenv("MY_ENV");
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new SentryExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                Sentry.getContext().addTag("traceId", MDC.get("X-B3-TraceId"));
                return super.resolveException(request, response, handler, ex);
            }
        };
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new SentryServletContextInitializer();
    }

}
