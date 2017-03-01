## Q1
### Requirements
Write a Java method that takes an array of "sets" of String objects,
and determines whether _all_ sets in the array are equivalent.
Each "set" in the input array is represented as an array of String objects, in
no particular order, and possibly containing duplicates. Nevertheless, when
determining whether two of these "sets" are equivalent, you should disregard
order and duplicates. For example, the sets represented by these arrays are
all equivalent:
`
{"a", "b"}
{"b", "a"}
{"a", "b", "a"}
`

The signature for your method should be:
`public static boolean allStringSetsIdentical(String[ ][ ] sets)`

Examples of the method in operation:

 * `allStringSetsIdentical(new String[][] {{"a","b"},{"b","b","a"},{"b","a"}})` returns `true`
 * `allStringSetsIdentical(new String[][] {{"a","b"},{"a"},{"b"}})` returns `false`

### Solution demo
```bash
$ cd src 
$ javac com/webjer/q1/SetsUtilsRunner.java
$ java com/webjer/q1/SetsUtilsRunner

```

## Q2
### Requirements
``` java
//
// The following Java code is responsible for creating an HTML "SELECT"
// list of U.S. states, allowing a user to specify his or her state. This might
// be used, for instance, on a credit card transaction screen.
//
// Please rewrite this code to be "better". Submit your replacement code, and
// please also submit a few brief comments explaining why you think your code
// is better than the sample.
//
// (For brevity, this sample works for only 5 states. The real version would
// need to work for all 50 states. But it is fine if your rewrite shows only
// the 5 states here.)
//
public class StateUtils {
  //
  // Generates an HTML select list that can be used to select a specific
  // U.S. state.
  //
  public static String createStateSelectList()
  {
    return
      "<select name=\"state\">\n"
    + "<option value=\"Alabama\">Alabama</option>\n"
    + "<option value=\"Alaska\">Alaska</option>\n"
    + "<option value=\"Arizona\">Arizona</option>\n"
    + "<option value=\"Arkansas\">Arkansas</option>\n"
    + "<option value=\"California\">California</option>\n"
    // more states here
    + "</select>\n"
    ;
  }
  //
  // Parses the state from an HTML form submission, converting it to
  // the two-letter abbreviation.
  //
  public static String parseSelectedState(String s)
  {
    if (s.equals("Alabama"))     { return "AL"; }
    if (s.equals("Alaska"))      { return "AK"; }
    if (s.equals("Arizona"))     { return "AZ"; }
    if (s.equals("Arkansas"))    { return "AR"; }
    if (s.equals("California"))  { return "CA"; }
    // more states here
  }
  //
  // Displays the full name of the state specified by the two-letter code.
  //
  public static String displayStateFullName(String abbr) {
  {
    if (abbr.equals("AL")) { return "Alabama";    }
    if (abbr.equals("AK")) { return "Alaska";     }
    if (abbr.equals("AZ")) { return "Arizona";    }
    if (abbr.equals("AR")) { return "Arkansas";   }
    if (abbr.equals("CA")) { return "California"; }
    // more states here
  }
}
```
### Solution demo
```bash
$ cd src 
$ javac com/webjer/q2/StateUtilsRunner.java
$ java com/webjer/q2/StateUtilsRunner

```

## Q3
### Requirements
Write a Java method with the following method signature that takes a String and
returns a String formatted so that it satisfies the requirements below.  It may
need to insert newlines and/or delete spaces.

Method Signature:
`public static String wrapText(String text, int maxCharsPerLine)`

Definitions and Assumptions:

* A word is a nonempty sequence of characters that contains no spaces and no newlines.
* Lines in the return String are separated by the newline character, `'\n'`.
* Words on each line are separated by spaces. Assume that the String argument does
not contain any whitespace characters other than spaces and newlines.

Requirements:

1. Newlines in the String argument are preserved.
1. Words in the return String are separated by either a single space or by one or
more newlines.
1. Lines in the return String do not start or end with any spaces.
1. When constructing the return String from the String argument, each word in the
String argument with at most `maxCharsPerLine` characters should not be broken up.
Each word in the String argument with more than `maxCharsPerLine` characters should
be broken up so that all of the other requirements are satisfied.
1. The String argument may contain lines longer than `maxCharsPerLine`. Newlines
should be added so that each line in the return String has at most `maxCharsPerLine`
characters. To determine where newlines should be added, try to fit as many words
as possible on a line (while keeping line length at most `maxCharsPerLine` and
satisfying the other requirements) before starting a new line.
### Solution demo
```bash
$ cd src 
$ javac com/webjer/q3/TextUtilsRunner.java
$ java com/webjer/q3/TextUtilsRunner

```