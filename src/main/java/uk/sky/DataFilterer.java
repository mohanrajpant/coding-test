package uk.sky;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class DataFilterer {
    public static Collection<?> filterByCountry(Reader source, String country) {
        return new BufferedReader(source).lines().map(line -> line.split(",")).
                filter(data -> data[1].equals(country))
                .collect(Collectors.toList());
    }

    public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
        return new BufferedReader(source).lines().map(line -> line.split(",")).
                filter(data -> data[1].equals(country) && Long.parseLong(data[2]) > limit)
                .collect(Collectors.toList());
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
        List<String[]> logList =  new BufferedReader(source).lines().skip(1).map(line -> line.split(","))
                .collect(Collectors.toList());

        OptionalDouble average = logList.stream().
                mapToDouble(data -> Double.parseDouble(data[2])).average();

        if(average.isPresent()) {
            return logList.stream().filter(data -> Double.parseDouble(data[2]) > average.getAsDouble())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();


    }
}