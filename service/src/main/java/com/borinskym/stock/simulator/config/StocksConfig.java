package com.borinskym.stock.simulator.config;

import com.borinskym.stock.simulator.csv.StockParser;
import com.borinskym.stock.simulator.date.SparseDate;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Configuration
public class StocksConfig {

    @Bean(value = "stocksSymbols")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Set<String> stocksSymbols(@Value("${simulator.stockFolder}") String stocksFolder) {
        return Arrays.stream(Objects.requireNonNull(new File(stocksFolder).listFiles()))
                .map(file -> FilenameUtils.removeExtension(file.getName()))
                .collect(Collectors.toSet());
    }

    @Bean(value = "stocksInfo")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public TreeMap<SparseDate, Map<String, Double>> stocksInfo(@Value("${simulator.stockFolder}") String stocksFolder) {
        return new StockParser(stocksFolder).parse();
    }
}