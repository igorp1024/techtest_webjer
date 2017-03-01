package com.webjer;

/**
 * Naive execution time/memory measuring class.
 */
public class PerformanceGauge {

    private Runnable callback;

    public PerformanceGauge(Runnable callback) {
        this.callback = callback;
    }

    /**
     * Measure and return execution time.
     *
     * @return time in millis.
     */
    public SampledValues measure() {
        long memFreeBefore = Runtime.getRuntime().freeMemory();
        long memTotalBefore = Runtime.getRuntime().totalMemory();
        long startedAt = System.currentTimeMillis();
        callback.run();
        long totalTimeMs = System.currentTimeMillis() - startedAt;
        long totalMemBytes =
            (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
            - (memTotalBefore - memFreeBefore);
        return new SampledValues(totalTimeMs, totalMemBytes);
    }

    /**
     * Measure and print execution time results.
     */
    public void measureAndPrint(String message) {
        SampledValues values = measure();
        System.out.println(
            String.format("%s in %d ms, (%d Mb)",
                          message, values.getConsumedMillis(), values.getConsumedBytes() / 1048576L)
        );
    }

    public void measureAndPrint() {
        measureAndPrint("Completed");
    }

    private class SampledValues {
        private long consumedMillis;
        private long consumedBytes;

        public SampledValues(long consumedMillis, long consumedBytes) {
            this.consumedMillis = consumedMillis;
            this.consumedBytes = consumedBytes;
        }

        public long getConsumedMillis() {
            return consumedMillis;
        }

        public long getConsumedBytes() {
            return consumedBytes;
        }
    }
}
