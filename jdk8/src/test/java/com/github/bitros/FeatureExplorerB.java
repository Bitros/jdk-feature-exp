package com.github.bitros;


import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.function.*;

/**
 * We defined many functional interfaces in FeatureExplorerA, they are simple and generic.
 * So JDK provides built-in interfaces.
 * <ul>
 *  <li>Consumer    8</li>
 *  <li>Function    17</li>
 *  <li>Operator    8</li>
 *  <li>Predicate   5</li>
 *  <li>Supplier    5</li>
 * </ul>
 *
 * @see java.util.function
 */
public class FeatureExplorerB {
    /**
     * Consumer
     * Accept one argument and return nothing.
     */
    @Test
    public void testA() {
        Consumer<Integer> c = System.out::println;
//        print 1 only
        c.accept(1);

//        print 1 and 10
        c = c.andThen(i -> System.out.println(i * 10));
        c.accept(1);

//      chain call with generic Consumer, print 1 10 100
        ((Consumer<Integer>) System.out::println)
                .andThen(i -> System.out.println(i * 10))
                .andThen(i -> System.out.println(i * 100))
                .accept(1);

//      chain call with IntConsumer, print 1 10 100
        ((IntConsumer) System.out::println)
                .andThen(i -> System.out.println(i * 10))
                .andThen(i -> System.out.println(i * 100))
                .accept(1);
    }

    /**
     * Function
     * Accept and return a result
     */
    @Test
    public void testB() {
//        andThen runs after
//        print 2
        Function<String, Integer> f = Integer::valueOf;
        f = f.andThen(i -> i + 1);
        Integer result = f.apply("1");
        System.out.println(result);

//        compose runs before
//        print 3
        result = ((Function<Integer, Integer>) i -> i + 1)
                .compose(o -> Integer.valueOf((String) o))
                .apply("2");
        System.out.println(result);

//       a special function identity
        System.out.println(Function.identity().apply("s"));
    }

    /**
     * Operator
     * - UnaryOperator
     * - BinaryOperator
     * Sub of Function with similar concept
     * accept and return same types
     */
    @Test
    public void testC() {
//        print 2
        UnaryOperator<Integer> uo = i -> i + 1;
        Integer result = uo.apply(1);
        System.out.println(result);

//        print 3
        BinaryOperator<Integer> bo = Integer::sum;
//        BinaryOperator<Integer> bo = (i1, i2) -> i1 + i2;
        result = bo.apply(1, 2);
        System.out.println(result);
    }

    /**
     * Predicate
     * accept and return a boolean value.
     */
    @Test
    public void testD() {
//        print true
        Predicate<Integer> p = i -> i > 0;
        boolean result = p.test(1);
        System.out.println(result);
//        print false
        Predicate<Integer> np = p.negate();
        result = np.test(1);
        System.out.println(result);
//        print false
        result = ((Predicate<Integer>) i -> i > 0).and(i -> i % 2 == 0).test(3);
        System.out.println(result);
//        print true
        result = ((IntPredicate) i -> i > 0).or(i -> i % 2 == 0).test(3);
        System.out.println(result);
    }

    /**
     * Supplier
     * produce from nothing
     */
    @Test
    public void testE() {
//        always produce 0
        Supplier<Integer> s = () -> 0;
        Integer result = s.get();
        System.out.println(result);

//        always produce now
        Supplier<Date> ds = Date::new;
        Date now = ds.get();
        System.out.println(now);
    }

    /**
     * obj method references
     */
    @Test
    public void testF() {
        Consumer<String> f = System.out::println;
        f.accept("Hello");
    }

    /**
     * static method references
     */
    @Test
    public void testG() {
        Function<String, Integer> f = Integer::valueOf;
        Integer applied = f.apply("123");
        System.out.println(applied);
    }

    /**
     * constructor references Class::new
     */
    @Test
    public void testH() {
//        point to public String(char[] value)
        Function<char[], String> f = String::new;
        String applied = f.apply(new char[]{'H', 'e', 'l', 'l', 'o'});
        System.out.println(applied);
    }

    /**
     * array constructor references String[]::new
     */
    @Test
    public void testI() {
//        equal to new String[10]
        Function<Integer, String[]> f = String[]::new;
        String[] applied = f.apply(10);
        System.out.println(Arrays.toString(applied));
        System.out.println(applied.length);
    }
}
