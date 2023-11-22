package com.github.bitros;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * @see java.util.stream.Stream
 */
public class FeatureExplorerC {
    private Stream<Integer> stream;

    private Stream<Integer> duplicated_stream;

    private Stream<Integer> p_stream;

    private Stream<Integer> duplicated_p_stream;

    private Stream<Integer> infinite_constant_stream;

    private Stream<Integer> infinite_accumulated_stream;

    /**
     * of generate iterate
     * stream creation and parallel
     */
    @Before
    public void generate() {
        stream = Stream.of(1, 2, 3, 4, 5);
        duplicated_stream = Stream.of(1, 2, 3, 4, 5, 4, 5);

        p_stream = Stream.of(1, 2, 3, 4, 5).parallel();
        duplicated_p_stream = Stream.of(1, 2, 3, 4, 5, 4, 5).parallel();

        infinite_constant_stream = Stream.generate(() -> 1);
        infinite_accumulated_stream = Stream.iterate(1, i -> i + 1);
    }

    /**
     * - allMatch
     * - anyMatch
     * - noneMatch
     */
    @Test
    public void testA() {
        boolean allMatch = stream.allMatch(i -> i > 3);
        System.out.println(allMatch);

        boolean anyMatch = duplicated_stream.anyMatch(i -> i > 3);
        System.out.println(anyMatch);

        boolean noneMatch = p_stream.noneMatch(i -> i > 3);
        System.out.println(noneMatch);

        noneMatch = duplicated_p_stream.noneMatch(i -> i > 100);
        System.out.println(noneMatch);
    }

    /**
     * concat collect
     */
    @Test
    public void testB() {
        List<Integer> list = Stream.concat(stream, duplicated_stream).collect(Collectors.toList());
        System.out.println(list);

//        generate again otherwise IllegalStateException: stream has already been operated upon or closed
        generate();
        Set<Integer> set = Stream.concat(stream, duplicated_stream).collect(Collectors.toSet());
        System.out.println(set);
    }

    /**
     * count distinct
     */
    @Test
    public void testC() {
        long count = stream.count();
        System.out.println(count);

        count = duplicated_stream.distinct().count();
        System.out.println(count);
    }

    /**
     * filter findAny findFirst
     */
    @Test
    public void testD() {
        Integer peek = stream.filter(i -> i > 0).findFirst().orElse(-1);
        System.out.println(peek);

        Integer any = p_stream.filter(i -> i > 0).findAny().orElse(-1);
        System.out.println(any);
    }

    /**
     * flatMap flatMapToDouble forEach forEachOrdered
     * flatMap maps each element to stream and combine all the sub-streams into a final stream
     */
    @Test
    public void testE() {
        stream.flatMap(i -> Stream.of(i, i * i)).forEach(System.out::println);

        p_stream.flatMapToDouble(i -> DoubleStream.of(i * 1.0)).forEach(System.out::println);

//      print in order even it is a parallel stream
        duplicated_p_stream.flatMapToDouble(i -> DoubleStream.of((double) i)).forEachOrdered(System.out::println);
    }

    /**
     * limit max min
     */
    @Test
    public void testF() {
        infinite_constant_stream.limit(5).forEach(System.out::println);

        Integer max = infinite_accumulated_stream.limit(5).max(Comparator.comparingInt(i -> i)).orElse(-1);
        System.out.println(max);

        Integer min = stream.min(Comparator.comparingInt(i -> i)).orElse(-1);
        System.out.println(min);
    }

    /**
     * map peek skip sorted
     */
    @Test
    public void testG() {
        stream.map(i -> i + 1)
                .peek(System.out::println)
                .skip(3)
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);
    }

    /**
     * reduce toArray
     */
    @Test
    public void testH() {
//        1 + 2 + 3 + 4 + 5
        Integer sum = stream.reduce(Integer::sum).orElse(-1);
        System.out.println(sum);

//        identity for accumulator
//        100 + 1 + 1 + 1 + 1 + 1
        sum = infinite_constant_stream.limit(5).reduce(100, Integer::sum);
        System.out.println(sum);

//        combiner only used for parallel stream, identity for combiner
//        101 + 102 + 103 + 104 + 105
        sum = p_stream.reduce(100, Integer::sum, Integer::sum);
        System.out.println(sum);

        System.out.println(Arrays.toString(duplicated_stream.toArray()));
    }
}