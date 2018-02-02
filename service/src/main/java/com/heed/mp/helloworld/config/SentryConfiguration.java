package com.heed.mp.helloworld.config;

import io.sentry.Sentry;
import io.sentry.spring.SentryExceptionResolver;
import io.sentry.spring.SentryServletContextInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class SentryConfiguration {

    @Value("${sentry.dsn:}")
    public String dsn;

    @PostConstruct
    public void init() {
        if(!dsn.isEmpty()) {
            Sentry.init(dsn + "?servername=" + serverName() + "&tags=env:int,lang:java&mdctags=X-B3-TraceId");
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

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new SentryExceptionResolver();
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new SentryServletContextInitializer();
    }

}
