package com.borinskym.stock.simulator.config;

import com.borinskym.stock.simulator.StockInfo;
import com.borinskym.stock.simulator.csv.FileNameToStockInfoConvertor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "simulator")
public class StocksConfig {
    private Map<String, String> stockFiles;

    @Bean(value = "stockFiles")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Map<String, String> stocksFiles() {
        return stockFiles;
    }


    @Bean(value = "stocksInfo")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Map<String, List<StockInfo>> stocksInfo(@Qualifier("stockFiles") Map<String, String> fileByStock) {
        return new FileNameToStockInfoConvertor(fileByStock).convert();
    }
}