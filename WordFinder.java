package piwords;

import java.util.HashMap;
import java.util.Map;

public class WordFinder {
    /**
     * Given a String (the haystack) and an array of Strings (the needles),
     * return a Map<String, Integer>, where keys in the map correspond to
     * elements of needles that were found as substrings of haystack, and the
     * value for each key is the lowest index of haystack at which that needle
     * was found. A needle that was not found in the haystack should not be
     * returned in the output map.
     * 
     * @param haystack The string to search into.
     * @param needles The array of strings to search for. This array is not
     *                mutated.
     * @return The list of needles that were found in the haystack.
     */
    public static Map<String, Integer> getSubstrings(String haystack,
                                                     String[] needles) { 

        HashMap<String, Integer> toReturn = new HashMap<>();
        for (String x : needles) {
            if (haystack.contains(x)) {
                int index = haystack.indexOf(x);
                toReturn.put(x, index);
            }
        }
        return toReturn;
    }

    public static void main(String[] args) {
        String haystack = "asdcrhs";
        String[] needles = new String[3];
        needles[0] = "a";
        needles[1] = "b";
        needles[2] = "c";
        for (String x : needles) {
            System.out.println(haystack.contains(x));
            System.out.println(haystack.indexOf(x));
        }
        System.out.println(getSubstrings(haystack, needles));

    }
}

