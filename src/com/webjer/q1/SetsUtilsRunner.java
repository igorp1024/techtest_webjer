package com.webjer.q1;

import com.webjer.PerformanceGauge;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Simple tests runner for {@link SetsUtils}.
 */
public class SetsUtilsRunner {

    public static void main(String[] args) {

        performSimpleTest(
            new String[][]{
                {"a", "b", "c"}
            }
        );

        performSimpleTest(
            new String[][]{
                {"a", null, null, null, null, "b", "c"},
                {"a", null, "b", "c"}
            }
        );

        performSimpleTest(
            new String[][]{
                {"a", "b", "c"},
                {"a", "c", "b"},
                {"c", "c", "a", "b"},
                {"b", "a", "c", "c", "a", "b", "c"}
            }
        );

        performSimpleTest(
            new String[][]{
                {"a", "b", "c"},
                {"a", "c", "b"},
                {"c", "c", "a", "b"},
                {"b", "a"}
            }
        );

        performSimpleTest(
            new String[][]{
                {"a", "b"},
                {"b", "b", "a"},
                {"b", "a"}
            }
        );

        performSimpleTest(
            new String[][]{
                {"a", "b"},
                {"a"},
                {"b"}
            }
        );

        performHeavyTest();
    }

    private static void performSimpleTest(String[][] sets) {
        System.out.println(
            String.format("%b \t->  %s",
                          SetsUtils.allStringSetsIdentical(sets),
                          Arrays.deepToString(sets)
            )
        );
    }

    private static void performHeavyTest() {
        // Let's check the execution time
        System.out.print("\nNow generating quite bit of source data for heavy load test. "
                         + "This will take some time, please be patient... ");
        final int RANGE = 10_000_000;
        // Array containing duplicates values (even numbers from 0 to RANGE)
        String[]
            sampleArray =
            IntStream.range(0, RANGE)
                .mapToObj(i -> Integer.toString((i % 2 == 0) ? i : i - 1))
                .toArray(String[]::new);

        // Make several copies of same array
        String[][] sets = {
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length),
            Arrays.copyOf(sampleArray, sampleArray.length)
        };
        System.out.println("Done. Running the test... ");

        new PerformanceGauge(
            () -> SetsUtils.allStringSetsIdentical(sets)
        ).measureAndPrint();
    }
}
