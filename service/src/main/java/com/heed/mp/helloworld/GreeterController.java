package com.heed.mp.helloworld;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class GreeterController {

    @GetMapping("/greeting")
    public Greeting greeting() {
        return new Greeting("Hello World!", "");
    }

    @Getter
    @AllArgsConstructor
    @ToString
    private static class Greeting {
        private String greeting;
        private String county;
    }


}

