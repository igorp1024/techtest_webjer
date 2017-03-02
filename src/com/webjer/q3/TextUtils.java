package com.webjer.q3;

/**
 * Various handy text processing utility methods class.
 */
public final class TextUtils {

    private final static char NEWLINE_CHAR = '\n';
    private final static char SPACE_CHAR = ' ';

    private TextUtils() {
    }

    /**
     * Warps text according to the following rules:
     * <ul>
     * <li>A word is a nonempty sequence of characters that contains no spaces and no newlines;
     * <li>Lines in the result string are separated by the newline character, <tt>'\n'</tt>;
     * <li>Words on each line are separated by spaces;
     * <li>Newlines in the {@param text} argument are preserved;
     * <li>Words in the result string are separated by either a single space or by one or more
     * newlines;
     * <li>Lines in the result string do not start or end with any spaces;
     * <li>The {@param text} may contain lines longer than {@param maxCharsPerLine}. Newlines
     * are added so that each line in the result string has at most {@param maxCharsPerLine}
     * characters (the words are fit as many as possible on a line (while keeping line length
     * at most {@param maxCharsPerLine} and satisfying the other requirements) before starting
     * a new line;
     * <li>Each word in the {@param text} with at most {@param maxCharsPerLine} characters
     * will not be broken up, each word with more than characters will be broken up so that
     * all of the other requirements are satisfied.
     * </ul>
     *
     * @param text            A text to wrap (should not contain any whitespace characters other
     *                        than spaces and newlines).
     * @param maxCharsPerLine amount of maximum characters per line.
     * @return reformatted text.
     */
    public static String wrapText(String text, int maxCharsPerLine) {
        StringBuilder sb = new StringBuilder(text.length());

        int seekPos = 0;
        while (eolNotReached(text, seekPos)) {

            int curLineCharsCount = 0;
            while (eolNotReached(text, seekPos) && curLineCharsCount < maxCharsPerLine) {

                // Restart line building on newline
                //----------------------------------
                if (text.charAt(seekPos) == NEWLINE_CHAR) {
                    seekPos++;
                    curLineCharsCount = 0;
                    sb.append(NEWLINE_CHAR);
                    continue;
                }

                // Skip single or many spaces (if any)
                //-------------------------------------
                boolean itIsNecessaryToAddSpaceChar = false;
                while (eolNotReached(text, seekPos) && text.charAt(seekPos) == SPACE_CHAR) {
                    seekPos++;
                    itIsNecessaryToAddSpaceChar = true;
                }

                // Copy single space
                //-------------------
                if (itIsNecessaryToAddSpaceChar && curLineCharsCount > 0) {

                    // Handle words break
                    //----------------------
                    // We've just added the space char, so this is word boundary. Now it is
                    // necessary to check if this word a non-breakable and should it go on the next
                    // line or will it just fit in the current one.
                    if (putNonbreakableOnNextLine(text,
                                                  maxCharsPerLine,
                                                  seekPos,
                                                  curLineCharsCount)) {
                        // Start a new line, because there's no space for non-breakable word
                        break;
                    }

                    // Handle (copy or ignore) trailing space(s)
                    //-------------------------------------------
                    if (
                        // This is not the last chat in the current line
                        curLineCharsCount < maxCharsPerLine - 1
                        //
                        && eolNotReached(text, seekPos)
                        // And the last char in the test is not a newline
                        && text.charAt(seekPos) != NEWLINE_CHAR
                        ) {

                        // Copy space char
                        sb.append(SPACE_CHAR);
                        curLineCharsCount++;

                    } else {
                        // Ignore the last space char in the line
                        break;
                    }
                }

                // Copy chars
                //------------
                if (eolNotReached(text, seekPos)
                    // There's a place for words in current line
                    && curLineCharsCount < maxCharsPerLine
                    ) {
                    sb.append(text.charAt(seekPos));
                    curLineCharsCount++;
                }
                seekPos++;
            }
            // Start a new line when current reached maxCharsPerLine chars (avoid double newlines)
            //-------------------------------------------------------------------------------------
            if (eolNotReached(text, seekPos)) {
                // If we on a word boundary which perfectly fit the line width let's ignore trailing
                // spaces followed by a newline char (if any)
                if (
                    // This is a perfect word fit on a word boundary
                    curLineCharsCount == maxCharsPerLine
                    // There's certainly at least one more symbol left
                    && seekPos < text.length() - 1
                    ) {

                    // Ignore the trailing spaces
                    while (text.charAt(seekPos) == SPACE_CHAR) {
                        if (weAreAtEol(text, seekPos)) {
                            break;
                        }
                        seekPos++;
                    }
                }
                if (text.charAt(seekPos) != NEWLINE_CHAR) {
                    sb.append(NEWLINE_CHAR);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Checks whether the {@param seekPos} is NOT pointing at the last char of the {@param text}.
     *
     * @param text    source <tt>String</tt> to check.
     * @param seekPos position within {@param text}.
     * @return true if not pointing, false otherwise.
     */
    private static boolean eolNotReached(String text, int seekPos) {
        return seekPos < text.length();
    }

    /**
     * Checks whether the {@param seekPos} IS pointing at the last char of the {@param text}.
     *
     * @param text    source <tt>String</tt> to check.
     * @param seekPos position within {@param text}.
     * @return true if pointing, false otherwise.
     */
    private static boolean weAreAtEol(String text, int seekPos) {
        return !eolNotReached(text, seekPos);
    }

    /**
     * <p>Check if the current word is non-breakable (depends on {@param maxCharsPerLine}) and if
     * the current word fits into current line (depends on {@param maxCharsPerLine} and {@param
     * curLineCharsCount}). Current word is the word starting from {@param seekPos} and consists of
     * chars which are still unprocessed (look ahead is performed to evaluate it's length).
     *
     * <p>Extracted into a separate method for the readability sake.
     *
     * @return true if current word is non-breakable and it should be broken (won't fit in the
     * string size limit), false otherwise.
     */
    private static boolean putNonbreakableOnNextLine(String text,
                                                     int maxCharsPerLine,
                                                     int seekPos,
                                                     int curLineCharsCount) {
        // It is necessary to look ahead for current word length
        int lookAheadPos = seekPos;
        while (eolNotReached(text, lookAheadPos) && text.charAt(lookAheadPos) != ' ') {
            lookAheadPos++;
        }
        // If current word can fit into line (it's length is less or equal
        // to maxCharsPerLine), we should start it from new line, otherwise it will
        // be broken
        int currentWordLength = lookAheadPos - seekPos;
        int symbolsLeftInCurLine = maxCharsPerLine - curLineCharsCount;
        boolean wordIsNonBreakable = currentWordLength <= maxCharsPerLine;
        boolean wordWontFitInTheCurrentLine = symbolsLeftInCurLine < currentWordLength;

        return (wordIsNonBreakable && wordWontFitInTheCurrentLine);
    }
}
