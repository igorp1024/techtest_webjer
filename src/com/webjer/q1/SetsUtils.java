package com.webjer.q1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class SetsUtils {

    private SetsUtils() {
    }

    /**
     * Checks passed string sets for being identical.
     * @param sets array of arrays containing strings.
     * @return true if each array contains same set of string items disregarding duplicates.
     */
    public static boolean allStringSetsIdentical(String[][] sets) {

        if (sets == null) {
            throw new IllegalArgumentException("Non-null \"sets\" value should be passed.");
        } else if (sets.length == 0) {
            throw new IllegalArgumentException("\"Sets\" should contain at least one \"set\".");
        } else if (sets.length == 1) {
            return true;
        }

        // Do the identity check for two or more sets only

        // Compose set objects
        String[] firstArray = sets[0];
        Set<String> masterSet = new HashSet<>(firstArray.length);
        Collections.addAll(masterSet, firstArray);

        for (int i = 1; i < sets.length; i++) {
            String[] sampleArray = sets[i];
            Set<String> sampleSet = new HashSet<>(sampleArray.length);
            // First, check if *all* items are from first set
            for (String nextSampleItem : sampleArray) {
                if (!masterSet.contains(nextSampleItem)) {
                    return false;
                }
                sampleSet.add(nextSampleItem);
            }
            // Next, check the size of current set refined from duplicates
            if (sampleSet.size() != masterSet.size()) {
                return false;
            }
        }

        return true;
    }
}
