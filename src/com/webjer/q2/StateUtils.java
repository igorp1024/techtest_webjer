package com.webjer.q2;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Convenience class for manipulation US states data (name, codes and other things).
 */
public final class StateUtils {

    private final static Map<String, String> statesByAbbreviation;
    private final static Map<String, String> statesByName;
    private final static String selectHtmlMarkup;

    static {
        // Generate reference map
        Map<String, String> map = new TreeMap<>();
        map.put("AL", "Alabama");
        map.put("AK", "Alaska");
        map.put("AZ", "Arizona");
        map.put("AR", "Arkansas");
        map.put("CA", "California");
        map.put("CO", "Colorado");
        map.put("CT", "Connecticut");
        map.put("DE", "Delaware");
        map.put("FL", "Florida");
        map.put("GA", "Georgia");
        map.put("HI", "Hawaii");
        map.put("ID", "Idaho");
        map.put("IL", "Illinois");
        map.put("IN", "Indiana");
        map.put("IA", "Iowa");
        map.put("KS", "Kansas");
        map.put("KY", "Kentucky");
        map.put("LA", "Louisiana");
        map.put("ME", "Maine");
        map.put("MD", "Maryland");
        map.put("MA", "Massachusetts");
        map.put("MI", "Michigan");
        map.put("MN", "Minnesota");
        map.put("MS", "Mississippi");
        map.put("MO", "Missouri");
        map.put("MT", "Montana");
        map.put("NE", "Nebraska");
        map.put("NV", "Nevada");
        map.put("NH", "New Hampshire");
        map.put("NJ", "New Jersey");
        map.put("NM", "New Mexico");
        map.put("NY", "New York");
        map.put("NC", "North Carolina");
        map.put("ND", "North Dakota");
        map.put("OH", "Ohio");
        map.put("OK", "Oklahoma");
        map.put("OR", "Oregon");
        map.put("PA", "Pennsylvania");
        map.put("RI", "Rhode Island");
        map.put("SC", "South Carolina");
        map.put("SD", "South Dakota");
        map.put("TN", "Tennessee");
        map.put("TX", "Texas");
        map.put("UT", "Utah");
        map.put("VT", "Vermont");
        map.put("VA", "Virginia");
        map.put("WA", "Washington");
        map.put("WV", "West Virginia");
        map.put("WI", "Wisconsin");
        map.put("WY", "Wyoming");

        // Generate states maps (direct and reverse)
        statesByAbbreviation = Collections.unmodifiableMap(map);
        statesByName = Collections.unmodifiableMap(
            map.entrySet().stream()
                // Reverse the map
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))
        );

        // Generate markup for states select html control
        selectHtmlMarkup =
            map.entrySet().stream()
                .map(
                    entry -> String.format(
                        "<option value=\"%s\">%s</option>", entry.getKey(), entry.getValue()
                    )
                ).collect(Collectors.joining("\n", "<select name=\"state\">\n", "</select>\n")
            );

        // The following commented solution with StringBuilder is faster (10-14 times in my tests),
        // but consumes more memory (≈3.5 times more in my tests).
        // Despite Streams API solution is more time consuming, it will not be that noticeable
        // on 50 records and it is done only once on JVM start since it is static class initializer,
        // but the code clarity is much higher than with StringBuilder.

//        StringBuilder selectHtmlMarkupSB = new StringBuilder();
//        selectHtmlMarkupSB.append("<select name=\"state\">\n");
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            selectHtmlMarkupSB.append("<option value=\"");
//            selectHtmlMarkupSB.append(entry.getKey());
//            selectHtmlMarkupSB.append("\">");
//            selectHtmlMarkupSB.append(entry.getValue());
//            selectHtmlMarkupSB.append("</option>\n");
//        }
//        selectHtmlMarkupSB.append("</select>\n");
//        selectHtmlMarkup = selectHtmlMarkupSB.toString();
    }

    private StateUtils() {
    }

    /**
     * Generates an HTML markup for <tt>&lt;select&gt;</tt> control containing US states.
     *
     * @return html markup as <tt>String</tt>.
     */
    public static String createStateSelectList() {
        return selectHtmlMarkup;
/*
        The implemented solution is better than the original one because:

         * this implementation doesn't waste a heap due to not concatenating strings via '+'
            operator which puts to garbage all intermediate strings;

         * original implementation uses state name as form value passed through form submit which
            is overhead, this implementation uses state code instead;

         * this implementation doesn't generate the markup each time it is needed, but uses cached
            pre-built copy;

         * if there any typo in referential data (state code or name) it is easy to fix it in one
            place affecting other pieces of code where it also was copied to;

         * the implemented solution is much easier to read.

        // ---------------------------------
        return
            "<select name=\"state\">\n"
            + "<option value=\"Alabama\">Alabama</option>\n"
            + "<option value=\"Alaska\">Alaska</option>\n"
            // more states here
            + "</select>\n"
            ;
        // ---------------------------------
*/
    }

    /**
     * Parses the state from an HTML form submission, converting it to the two-letter abbreviation.
     *
     * @param stateName state name as <tt>String</tt> (case independent).
     * @return state code as <tt>String</tt>.
     */
    public static String parseSelectedState(String stateName) {
        if (stateName == null) {
            throw new IllegalArgumentException("State name can't be null.");
        }
        stateName = stateName.trim();
        String refinedStateName =
            stateName.substring(0, 1).toUpperCase() + stateName.substring(1).toLowerCase();

        String stateCode = statesByName.get(refinedStateName);
        if (stateCode == null) {
            throw new IllegalArgumentException("Wrong state name specified.");
        }
        return stateCode;
/*
        The implemented solution is better than the original one because:

         * hash map value access is constant (within same bucket, of course, so let's say
            "rather constant") and faster than string comparison. But this will be noticeable only
            under heavy load;

         * current implementation treats the state name as string which might have been entered
            manually, so it strips spaces, respects the case. So "AlAsKa" or "  aLASKa    "
            will also result into "AK" code;

         * this implementation handles invalid state name like "Whatever" or null value;

         * if there any typo in referential data (state code or name) it is easy to fix it in one
            place affecting other pieces of code where it also was copied to;

         * the implemented solution is much easier to read.

        // ---------------------------------
        if (s.equals("Alabama")) {
            return "AL";
        }
        if (s.equals("Alaska")) {
            return "AK";
        }
        // more states here
        // ---------------------------------
*/
    }

    /**
     * Parses the two-letter code of the state and returns the full name of the state.
     *
     * @param stateCode state abbreviation (case independent).
     * @return state name as <tt>String</tt>.
     */
    public static String displayStateFullName(String stateCode) {
        if (stateCode == null) {
            throw new IllegalArgumentException("State abbreviation can't be null.");
        }
        String refinedStateCode = stateCode.trim().toUpperCase();
        String stateName = statesByAbbreviation.get(refinedStateCode);
        if (stateName == null) {
            throw new IllegalArgumentException("Wrong state abbreviation specified.");
        }
        return stateName;
/*
         The implemented approach is better because:

         * accessing hash map value takes constant time and in worst case should be faster than
          comparing strings. Will be noticeable under heavy load;

         * current implementation treats the state code as string which might have been entered
            manually, so it strips spaces, respects the case. So "AK" or "  ak    "
            will also result into "Alaska" name;

         * this implementation handles invalid codes like "ZZzzzZZ" or null value;

         * if there any typo in referential data (state code or name) it is easy to fix it in one
            place affecting other pieces of code where it also was copied to;

         * the implemented solution is much easier to read.

        // ---------------------------------
        if (abbr.equals("AL")) {
            return "Alabama";
        }
        if (abbr.equals("AK")) {
            return "Alaska";
        }
        // more states here
        // ---------------------------------
*/
    }
}