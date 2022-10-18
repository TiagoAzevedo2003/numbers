package com.aor.numbers;

import org.graalvm.compiler.hotspot.stubs.Stub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ListDeduplicatorTest {

    @Test
    public void deduplicate() {
        List<Integer> list = helper();
        List<Integer> expected = Arrays.asList(1,2,4,5);

        class StubListSorter implements GenericListSorter{
            @Override
            public List<Integer> sort(List<Integer> list){
                return Arrays.asList(1, 2, 2, 4, 5);
            }
        }

        ListDeduplicator deduplicator = new ListDeduplicator();
        StubListSorter stub = new StubListSorter();
        List<Integer> distinct = deduplicator.deduplicate(list, stub);

        Assertions.assertEquals(expected, distinct);
    }

    public List helper(){
        List<Integer> list = Arrays.asList(1,2,4,2,5);
        return list;
    }

    @Test
    public void deduplicate_bug_8726(){
        List<Integer> list = Arrays.asList(1, 2, 4, 2);
        class StubListSorter implements GenericListSorter{
            @Override
            public List<Integer> sort(List<Integer> list){
                return Arrays.asList(1, 2, 2, 4);
            }
        }

        ListDeduplicator deduplicator = new ListDeduplicator();
        StubListSorter stub = new StubListSorter();
        List<Integer> distinct = deduplicator.deduplicate(list, stub);

        Assertions.assertEquals(Arrays.asList(1, 2, 4), distinct);
    }
}
