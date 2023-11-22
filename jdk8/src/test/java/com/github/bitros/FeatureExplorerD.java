package com.github.bitros;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FeatureExplorerD {

    /**
     * Optional
     */
    @Test
    public void testA() {
        Optional<Integer> optional = Optional.of(new Random().nextInt(100));
//        Optional<Integer> optional = Optional.empty();

//        get
        System.out.println(optional.get());

//        orElse orElseGet orElseThrow
        System.out.println(optional.orElse(-1));
        System.out.println(optional.orElseGet(() -> new Random().nextInt()));
        System.out.println(optional.orElseThrow(NoSuchElementException::new));

//      isPresent ifPresent
        optional.ifPresent(System.out::println);

//        filter map
        optional = optional.filter(i -> i > 50).map(i -> i * i);
        optional.ifPresent(System.out::println);

//        flatMap needs Optional result
        optional = optional.flatMap(i -> Optional.of(i * 2));
        optional.ifPresent(System.out::println);
    }

    /**
     * @see java.time.LocalDate
     * @see java.time.LocalTime
     * @see java.time.LocalDateTime
     * @see java.time.OffsetTime
     * @see java.time.OffsetDateTime
     * @see java.time.ZonedDateTime
     */
    @Test
    public void testB() {
        LocalDateTime date_time = LocalDateTime.now();
        System.out.println(date_time);
        date_time = LocalDateTime.of(2022, 10, 10, 11, 11, 11);
        System.out.println(date_time);

        ZonedDateTime zone_date_time = ZonedDateTime.now();
        System.out.println(zone_date_time);
        zone_date_time = ZonedDateTime.of(date_time, ZoneId.systemDefault());
        System.out.println(zone_date_time);

//        -18 +18
        OffsetDateTime offset_date_time = OffsetDateTime.now();
        System.out.println(offset_date_time);
//        set offset as 3 minutes, +00:03
        offset_date_time = OffsetDateTime.of(date_time, ZoneOffset.ofTotalSeconds(180));
        System.out.println(offset_date_time);
    }

    /**
     * @see java.time.Clock
     * @see java.time.Instant
     * @see java.time.Duration
     * @see java.time.Period
     * @see java.time.format.DateTimeFormatter
     */
    @Test
    public void testC() {
        Clock clock = Clock.fixed(Clock.systemDefaultZone().instant(), Clock.systemDefaultZone().getZone());
        System.out.println(clock.instant());

//        tick every 10 seconds show 10 20 30 ...
        clock = Clock.tick(Clock.systemDefaultZone(), Duration.ofSeconds(10));
        System.out.println(clock.instant());

//        current instant + offset, current: 12:00:13, offset clock: 12:00:23
        clock = Clock.offset(Clock.systemDefaultZone(), Duration.ofSeconds(10));
        System.out.println(clock.instant());

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSSSSS")));
    }

    /**
     * @see java.util.Base64
     */
    @Test
    public void testD() {
        String encoded = Base64.getEncoder().encodeToString("hello".getBytes());
        System.out.println(encoded);
        byte[] decoded = Base64.getDecoder().decode("aGVsbG8=");
        System.out.println(new String(decoded));


        encoded = Base64.getUrlEncoder().encodeToString("https://github.com/".getBytes());
        System.out.println(encoded);
        decoded = Base64.getUrlDecoder().decode("aHR0cHM6Ly9naXRodWIuY29tLw==");
        System.out.println(new String(decoded));

        encoded = Base64.getMimeEncoder().encodeToString("Hello \nWorld!".getBytes());
        System.out.println(encoded);
        decoded = Base64.getMimeDecoder().decode("SGVsbG8gCldvcmxkIQ==");
        System.out.println(new String(decoded));
    }

    /**
     * @see java.util.StringJoiner
     */
    @Test
    public void testE() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add("1");
        sj.add("2");
        sj.add("3");
        sj.add("4");
        System.out.println(sj);
        StringJoiner sj2 = new StringJoiner(":", "{", "}");
        sj2.add("5");
        sj2.add("6");
//        merge means join all strings with : first, ignore prefix and suffix of sj2.
        sj.merge(sj2);
//        [1,2,3,4,5:6]
        System.out.println(sj);

        String joined = String.join(",", "1", "2", "3");
        System.out.println(joined);
    }

    /**
     * Array parallel
     */
    @Test
    public void testF() {
        int[] array = new int[]{2, 1, 0, 3};

//        [0, 1, 2, 3]
        Arrays.parallelSort(array);
        System.out.println(Arrays.toString(array));

//        [0, 1, 3, 6]
        Arrays.parallelPrefix(array, Integer::sum);
        System.out.println(Arrays.toString(array));

//        [1, 2, 4, 7]
        Arrays.parallelSetAll(array, index -> array[index] += 1);
        System.out.println(Arrays.toString(array));
    }
}