package com.borinskym.stock.simulator.csv;

import com.borinskym.stock.simulator.StockInfo;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.joda.time.DateTime;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class FileNameToStockInfoConvertor {

    private Map<String, String> fileNameByStockName;

    public FileNameToStockInfoConvertor(Map<String, String> fileNameByStockName) {
        this.fileNameByStockName = fileNameByStockName;
    }

    public Map<String, List<StockInfo>> convert(){
        return fileNameByStockName.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e ->  convert(e.getValue())));
    }

    private List<StockInfo> convert(String file) {
        try (CSVReader csvReader = new CSVReaderBuilder(filePath(file)).withSkipLines(1).build()) {
           return csvReader.readAll().stream()
                    .map(line -> StockInfo.from(DateTime.parse(line[0]).getMillis(), Double.parseDouble(line[1])))
                    .collect(Collectors.toList());
        }catch (IOException e){
            throw new CouldNotParseFile(e);
        }
    }

    private FileReader filePath(String file) throws IOException {
        URL resource = Optional.ofNullable(getClass().getClassLoader().getResource(file))
                .orElseThrow(() -> new IOException(String.format("resource file not found %s", file)));

        return new FileReader(resource.getFile());
    }

    public class CouldNotParseFile extends RuntimeException{
        public CouldNotParseFile(Throwable cause) {
            super(cause);
        }
    }
}
