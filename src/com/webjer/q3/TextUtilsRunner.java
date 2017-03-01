package com.webjer.q3;

/**
 * {@link TextUtils} runner class.
 */
public class TextUtilsRunner {

    public static void main(String[] args) {
        performSimpleTest(
            "         its a__    very  important__  a text_text___    the   "
            + "defghijklmnopq zxcvbnmasdfghjklw\n"
            + "thi sis    very  LARGE_TEXT THE large_text____\n"
            + "123 456    7890     A     \n",
            12);
        performSimpleTest(
            "Hello\nI'm the    text     A     b",
            12);
        performSimpleTest(
            "    The the   ab    text_text    text1text2   \n\n    long_and_bo\n\n"
            + "ring_nonsence_here__     the_end   ",
            10);
    }

    private static void performSimpleTest(String text, int maxCharsPerLine) {
        System.out.println("\n=[ " + maxCharsPerLine + " chars ]=============================\n"
                           + text
                           + "\n"
                           + "-[ result ]-------------------------------\n"
                           + TextUtils.wrapText(text, maxCharsPerLine)
                           + "\n==========================================\n");
    }

}
