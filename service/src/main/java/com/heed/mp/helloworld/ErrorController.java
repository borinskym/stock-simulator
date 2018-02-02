package com.heed.mp.helloworld;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ErrorController {

    @GetMapping("/error")
    public void error() {
        throw new IllegalArgumentException("error");
    }

}

