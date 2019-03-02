package com.borinskym.stock.simulator.csv;

import com.borinskym.stock.simulator.StockInfo;
import com.borinskym.stock.simulator.date.SparseDate;
import com.google.common.collect.ImmutableMap;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.FilenameUtils.removeExtension;

public class StockParser {


    private String stocksFolder;

    public StockParser(String stocksFolder) {
        this.stocksFolder = stocksFolder;
    }

    public TreeMap<SparseDate, Map<String, Double>> parse(){
        verifyFolderExist(stocksFolder);
        TreeMap<SparseDate, Map<String, Double>> ans = new TreeMap<>();
        for(File stockFile: requireNonNull(new File(stocksFolder).listFiles())){
            addStockInfoToMap(ans, stockFile);
        }
        return ans;
    }

    private void verifyFolderExist(String stocksFolder) {
        File stockFolder = new File(stocksFolder);
        if(!stockFolder.exists()){
            throw new CouldNotParseFile("stocks folder not found");
        }
    }

    private void addStockInfoToMap(Map<SparseDate, Map<String, Double>> ans, File stockFile) {
        for(StockInfo stockInfo: stockInfo(stockFile.getAbsolutePath())){
            Map<String, Double> current = ans.getOrDefault(stockInfo.getTime(), new HashMap<>());
            current.put(removeExtension(stockFile.getName()), stockInfo.getValue());
            ans.put(stockInfo.getTime(), current);
        }
    }

    private Map<SparseDate, Map<String, Double>> from(List<StockInfo> stockInfos, String name){
        return stockInfos.stream().collect(Collectors.toMap(
                StockInfo::getTime,
                stockInfo -> ImmutableMap.of(name, stockInfo.getValue())));
    }

    private List<StockInfo> stockInfo(String file) {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(1).build()) {
           return csvReader.readAll().stream()
                    .map(line -> StockInfo.from(toSparseDate(line[0]), Double.parseDouble(line[1])))
                    .collect(Collectors.toList());
        }catch (IOException e){
            throw new CouldNotParseFile(e);
        }
    }

    private SparseDate toSparseDate(String s) {
        String[] date = s.split("/");
        return SparseDate.from(parseInt(date[1]), parseInt(date[0]));
    }

    public class CouldNotParseFile extends RuntimeException{
        public CouldNotParseFile(Throwable cause) {
            super(cause);
        }

        public CouldNotParseFile(String message) {
            super(message);
        }
    }
}
