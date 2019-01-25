package test.com.borinskym.stock.simulator.csv;

import com.borinskym.stock.simulator.StockInfo;
import com.borinskym.stock.simulator.csv.FileNameToStockInfoConvertor;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileNameToStockInfoConvertorTest {

    @Test
    public void shouldParseFile() {
        assertThat(new FileNameToStockInfoConvertor(ImmutableMap.of("bank-cash", "bank.csv")).convert(),
                is(ImmutableMap.of("bank-cash", stockInfos())));

    }

    private List<StockInfo> stockInfos() {
        return Arrays.asList(
                StockInfo.from(DateTime.parse("2004-01-01").getMillis(), 1000.0),
                StockInfo.from(DateTime.parse("2004-02-01").getMillis(), 2000.0)
        );
    }

    @Test(expected = FileNameToStockInfoConvertor.CouldNotParseFile.class)
    public void shouldFail_whenFileNotFound() {
        new FileNameToStockInfoConvertor(ImmutableMap.of("bank", "notFound.csv")).convert();
    }

}