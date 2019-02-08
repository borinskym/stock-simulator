package com.borinskym.stock.simulator.config;

import com.borinskym.stock.simulator.csv.StockParser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Map;

@Data
@Configuration
public class StocksConfig {



    @Bean(value = "stocksInfo")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Map<Long, Map<String, Double>> stocksInfo(@Value("${simulator.stockFolder}") String  stocksFolder) {
        return new StockParser(stocksFolder).parse();
    }
}