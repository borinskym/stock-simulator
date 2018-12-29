package com.heed.mp.helloworld;

import io.ticktok.Ticktok;
import io.ticktok.TicktokOptions;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TicktokController {

    private final List<TickRow> ticks = new ArrayList<>();

    private Ticktok createTicktok() {
        return new Ticktok(TicktokOptions.builder().domain("http://int-ticktok.heed-dev.io").token("ticktok@heed").build());
    }

    @PostConstruct
    public void init() {
        scheduleTick("hello-world-20", "every.20.seconds");
        scheduleTick("hello-world-60", "every.60.seconds");
        createTicktok().newClock("clear").on("every.86400.seconds").invoke(ticks::clear);
    }

    private void scheduleTick(String clockName, String schedule) {
        createTicktok().newClock(clockName).on(schedule).invoke(() -> ticks.add(new TickRow(clockName, schedule)));
    }

    @GetMapping(value = "/v1/ticks.csv")
    public void fooAsCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(ticks.stream().map(TickRow::toString).collect(Collectors.joining("\n")));
    }

    @EqualsAndHashCode
    private class TickRow {
        private String clockName;
        private String schedule;
        private long timestamp;

        public TickRow(String clockName, String schedule) {
            this.clockName = clockName;
            this.schedule = schedule;
            timestamp = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return clockName + "," +
                    schedule + "," +
                    new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(timestamp));
        }
    }
}
