package com.aor.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.Arrays;
import java.util.List;

public class ListAggregatorTest {
    @Test
    public void sum() {
        List<Integer> list = helper();

        ListAggregator aggregator = new ListAggregator();
        int sum = aggregator.sum(list);

        Assertions.assertEquals(14, sum);
    }

    @Test
    public void max() {
        List<Integer> list = helper();

        ListAggregator aggregator = new ListAggregator();
        int max = aggregator.max(list);

        Assertions.assertEquals(5, max);
    }

    @Test
    public void min() {
        List<Integer> list = helper();

        ListAggregator aggregator = new ListAggregator();
        int min = aggregator.min(list);

        Assertions.assertEquals(1, min);
    }

    @Test
    public void distinct() {
        List<Integer> list = helper();

        class StubListDeduplicator implements GenericListDeduplicator{
            @Override
            public List<Integer> deduplicate(List<Integer> list) {
                return Arrays.asList(1, 2, 4, 5);
            }
        }

        ListAggregator aggregator = new ListAggregator();
        StubListDeduplicator stub = new StubListDeduplicator();
        int distinct = aggregator.distinct(list, stub);

        Assertions.assertEquals(4, distinct);
    }

    public List helper(){
        List<Integer> list = Arrays.asList(1,2,4,2,5);
        return list;
    }

    @Test

    public void max_bug_7263(){
        List<Integer> list = Arrays.asList(-1, -4, -5);
        ListAggregator aggregator = new ListAggregator();
        int max = aggregator.max(list);

        Assertions.assertEquals(-1, max);
    }

    @Test
    public void distinct_bug_8726(){
        List<Integer> list = Arrays.asList(1, 2, 4, 2);

        class StubListDeduplicator implements GenericListDeduplicator{
            @Override
            public List<Integer> deduplicate(List<Integer> list){
                return Arrays.asList(1, 2, 4);
            }
        }

        ListAggregator aggregator = new ListAggregator();
        StubListDeduplicator stub = new StubListDeduplicator();
        int distinct = aggregator.distinct(list, stub);

        Assertions.assertEquals(3, distinct);
    }
}
