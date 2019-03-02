package test.com.borinskym.stock.simulator.csv;

import com.borinskym.stock.simulator.csv.StockParser;
import com.borinskym.stock.simulator.date.SparseDate;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StockParserTest {

    @Test
    public void shouldParseFile() {
        assertThat(new StockParser(folder()).parse(), is(expected()));
    }

    private String folder() {
        return getClass().getClassLoader().getResource("stocks-light").getFile();
    }

    private ImmutableMap<SparseDate, ImmutableMap<String, Double>> expected() {
        return ImmutableMap.of(
                SparseDate.from(2004, 1), ImmutableMap.of("bank", 1000.0),
                SparseDate.from(2004, 2), ImmutableMap.of("bank", 2000.0));
    }

    @Test(expected = StockParser.CouldNotParseFile.class)
    public void shouldFail_whenFileNotFound() {
        new StockParser("non-existingFolder").parse();
    }

}