package test.com.borinskym.stock.simulator.date;

import org.junit.Test;

import static com.borinskym.stock.simulator.date.SparseDate.from;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertThat;

public class SparseDateTest {


    @Test
    public void shouldBeLessThen() {
        assertThat(from(2014, 2).compareTo(from(2015, 3)), lessThan(0));
    }

    @Test
    public void shouldBeLessThen_whenOnlyMonthDifference() {
        assertThat(from(2014, 2).compareTo(from(2014, 3)), lessThan(0));
    }

    @Test
    public void shouldBeGreaterThen() {
        assertThat(from(2015, 2).compareTo(from(2014, 3)), greaterThan(0));
    }

    @Test
    public void shouldBeGreaterThen_whenMonthGreater() {
        assertThat(from(2015, 3).compareTo(from(2015, 2)), greaterThan(0));
    }

    @Test
    public void shouldBeEquals() {
        assertThat(from(2015, 3).compareTo(from(2015, 3)), is(0));
    }

}