package com.github.bitros;


import org.junit.Test;

/**
 * <ul>
 *  <li> Lambda Expressions
 *  <li> <a href="https://wiki.jvmlangsummit.com/images/7/71/2011_Goetz_Extension.pdf">Virtual Extension Methods</a>
 * </ul>
 */
public class FeatureExplorerA {
    /**
     * lambda with functional interface
     */
    @Test
    public void testA() {
        FIA fia = i -> System.out.println(i);
        fia.print(1);

        fia = (int i) -> System.out.println(i);
        fia.print(1);

        fia = (i) -> System.out.println(i);
        fia.print(1);
    }


    /**
     * lambda with multiple parameters
     */
    @Test
    public void testB() {
        FIB fib = (i, s) -> {
            System.out.println(i);
            System.out.println(s);
        };
        fib.printTwo(1, "s");

        fib = (int i, String s) -> {
            System.out.println(i);
            System.out.println(s);
        };
        fib.printTwo(1, "s");
    }

    /**
     * lambda with return values
     */
    @Test
    public void testC() {
        FIC fic = i -> i == 10;
        System.out.println(fic.judge(1));

        fic = (int i) -> {
            return i == 10;
        };
        System.out.println(fic.judge(11));

        fic = (i) -> {
            return i == 10;
        };
        System.out.println(fic.judge(10));
    }

    /**
     * lambda and Virtual Extension Methods
     */
    @Test
    public void testD() {
//        lambda cannot call default method in interface
//        Calculable calc = i -> calculateDouble(i);

//        override and call
        Calculable calc = new Calculable() {
            @Override
            public int calculate(int i) {
                return calculateDouble(i) * calculateTriple(i);
            }

            /**
             * @return quadruple
             */
            @Override
            public int calculateDouble(int i) {
                return 2 * Calculable.super.calculateDouble(i);
            }
        };
//        quadruple * triple and get 1200
        System.out.println(calc.calculate(10));
    }

    /**
     * interface static method
     */
    @Test
    public void testE() {
        System.out.println(Calculable.square(10));
    }
}

@FunctionalInterface
interface FIA {
    void print(int i);
}

@FunctionalInterface
interface FIB {
    void printTwo(int i, String s);
}

@FunctionalInterface
interface FIC {
    boolean judge(int i);
}

/**
 * multiple Virtual Extension Methods for backward compatibility
 */
interface Calculable {

    int calculate(int i);

    default int calculateDouble(int i) {
        return i * 2;
    }

    default int calculateTriple(int i) {
        return i * 3;
    }

    static int square(int i) {
        return i * i;
    }
}