package com.webjer.q2;

/**
 * Simple tests runner for {@link StateUtils}.
 */
public class StateUtilsRunner {

    public static void main(String[] args) {

        System.out.println("\nHTML markup.\n-----------------------------");
        System.out.println(StateUtils.createStateSelectList());

        System.out.println("\nNames to codes.\n-----------------------------");
        performParseSelectedState("alaska");
        performParseSelectedState("  Delaware ");
        performParseSelectedState("  teXas");

        System.out.println("\nCodes to names.\n-----------------------------");
        performDisplayStateFullName("AK");
        performDisplayStateFullName(" CA");
        performDisplayStateFullName("IN  ");
        performDisplayStateFullName("  mD  ");
        performDisplayStateFullName("  ne  ");
    }

    private static void performParseSelectedState(String code) {
        System.out.println(
            String.format("%s ->  %s",
                          code,
                          StateUtils.parseSelectedState(code)
            )
        );
    }

    private static void performDisplayStateFullName(String code) {
        System.out.println(
            String.format("%s ->  %s",
                          code,
                          StateUtils.displayStateFullName(code)
            )
        );
    }

}
