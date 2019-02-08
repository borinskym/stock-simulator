package test.com.borinskym.stock.simulator.csv;

import com.borinskym.stock.simulator.csv.StockParser;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;
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

    private ImmutableMap<Long, ImmutableMap<String, Double>> expected() {
        return ImmutableMap.of(
                DateTime.parse("2004-01-01").getMillis(), ImmutableMap.of("bank", 1000.0),
                DateTime.parse("2004-02-01").getMillis(), ImmutableMap.of("bank", 2000.0));
    }

    @Test(expected = StockParser.CouldNotParseFile.class)
    public void shouldFail_whenFileNotFound() {
        new StockParser("non-existingFolder").parse();
    }

}