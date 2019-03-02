package com.borinskym.stock.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v1")
public class AdminController {
    @Autowired
    @Qualifier("stocksSymbols")
    Set<String> stocksSymbols;


    @GetMapping("/symbols")
    public ResponseEntity<Set<String>> getStocksSymbols(){
        return ResponseEntity.ok(stocksSymbols);
    }
}
