package uk.sky;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
    @Test
    public void whenLogsIsEmptyFilteringReturnsEmptyCollection() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(
                openFile("src/test/resources/empty"), "GB", 200).isEmpty());
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(
                openFile("src/test/resources/empty")).isEmpty());
    }

    @Test
    public void ForValidInputFilterByCountryFetchesCorrectRecord() throws FileNotFoundException {
        assertEquals("Filter By Country GB should retrieve 1 record", 1,
                DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "GB").size());
        assertEquals("Filter By Country US should retrieve 2 record", 3,
                DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "US").size());
    }

    @Test
    public void ForInValidInputFilterByCountryFetchesEmptyCollection() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "TEST").isEmpty());

    }

    @Test
    public void ForValidInputFilterByCountryWithResponseTimeAboveLimitFetchesCorrectRecord() throws FileNotFoundException {
        assertEquals("Filter By Country GB and Response time limit 400 should retrieve 1 record", 1,
                DataFilterer.filterByCountryWithResponseTimeAboveLimit(
                        openFile("src/test/resources/multi-lines"), "DE", 400).size());
        assertEquals("Filter By Country US  and Response time limit 600 should retrieve 2 record", 2,
                DataFilterer.filterByCountryWithResponseTimeAboveLimit(
                        openFile("src/test/resources/multi-lines"), "US", 600).size());
    }

    @Test
    public void ForInValidInputFilterByCountryWithResponseTimeAboveLimitFetchesEmptyCollection() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(
                openFile("src/test/resources/multi-lines"), "US", 1000).isEmpty());
    }

    @Test
    public void ensureFilterByResponseTimeAboveAverageFetchesCorrectRecord() throws FileNotFoundException {
        assertEquals(3, DataFilterer.filterByResponseTimeAboveAverage(
                        openFile("src/test/resources/multi-lines")).size());
    }

    @Test
    public void ForSingleLineFileResponseTimeAboveAverageFetchesEmptyCollection() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(
                openFile("src/test/resources/single-line")).isEmpty());
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
