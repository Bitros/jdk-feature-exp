package com.github.bitros;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;


public class FeatureExplorerE {
    /**
     * repeatable annotations test
     */
    @Test
    @Entrance(id = 1, value = "west")
    @Entrance(id = 2, value = "east")
    @Entrance(id = 3, value = "north")
    @Entrance(id = 4, value = "south")
    public void testA() {
        try {
            Method testA = FeatureExplorerE.class.getMethod("testA");
            Annotation[] annotations = testA.getAnnotations();
//            length 2
            System.out.println(annotations.length);
//            @Test, @Entrances including 4 @Entrance
            System.out.println(Arrays.toString(annotations));


            Entrances entrances = testA.getAnnotation(Entrances.class);
            System.out.println(entrances);

//            cannot get @Entrance
            Entrance entrance = testA.getAnnotation(Entrance.class);
            System.out.println(entrance);
//            can
            Entrance[] entrance_array = testA.getAnnotationsByType(Entrance.class);
            System.out.println(Arrays.toString(entrance_array));

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * repeatable annotations test
     * in the old way
     */
    @Test
    @Entrances({@Entrance(id = 1, value = "west"), @Entrance(id = 2, value = "east"), @Entrance(id = 3, value = "north"), @Entrance(id = 4, value = "south")})
    public void testB() {
        try {
            Method testB = FeatureExplorerE.class.getMethod("testB");
            Annotation[] annotations = testB.getAnnotations();
//            length 2
            System.out.println(annotations.length);
//            @Test, @Entrances including 4 @Entrance
            System.out.println(Arrays.toString(annotations));


            Entrances entrances = testB.getAnnotation(Entrances.class);
            System.out.println(entrances);

//            cannot get @Entrance
            Entrance entrance = testB.getAnnotation(Entrance.class);
            System.out.println(entrance);
//            can
            Entrance[] entrance_array = testB.getAnnotationsByType(Entrance.class);
            System.out.println(Arrays.toString(entrance_array));

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * parameter reflection
     */
    @Test
    public void testC() {
//        print int a, int b instead of int arg0 int arg1
        Arrays.stream(FeatureExplorerE.class.getDeclaredMethods())
                .filter(method -> method.getName().equals("testSum"))
                .findAny()
                .ifPresent(method -> Arrays.stream(method.getParameters())
                        .forEach(System.out::println));
    }

    private int testSum(int a, int b) {
        return a + b;
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Entrances {
    Entrance[] value();
}

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Entrances.class)
@interface Entrance {
    String value();

    int id();
}