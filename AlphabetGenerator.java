package piwords;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlphabetGenerator {
    /**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * 
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
     * 
     * This method should do the following to generate an alphabet:
     *   1. Count the occurrence of each character a-z in trainingData.
     *   2. Compute the probability of each character a-z by taking
     *      (occurrence / total_num_characters).
     *   3. The output generated in step (2) is a PDF of the characters in the
     *      training set. Convert this PDF into a CDF for each character.
     *   4. Multiply the CDF value of each character by the base we are
     *      converting into.
     *   5. For each index 0 <= i < base,
     *      output[i] = (the first character whose CDF * base is > i)
     * 
     * A concrete example:
     * 	 0. Input = {"aaaaa..." (302 "a"s), "bbbbb..." (500 "b"s),
     *               "ccccc..." (198 "c"s)}, base = 93
     *   1. Count(a) = 302, Count(b) = 500, Count(c) = 198
     *   2. Pr(a) = 302 / 1000 = .302, Pr(b) = 500 / 1000 = .5,
     *      Pr(c) = 198 / 1000 = .198
     *   3. CDF(a) = .302, CDF(b) = .802, CDF(c) = 1
     *   4. CDF(a) * base = 28.086, CDF(b) * base = 74.586, CDF(c) * base = 93
     *   5. Output = {"a", "a", ... (29 As, indexes 0-28),
     *                "b", "b", ... (46 Bs, indexes 29-74),
     *                "c", "c", ... (18 Cs, indexes 75-92)}
     * 
     * The letters should occur in lexicographically ascending order in the
     * returned array.
     *   - {"a", "b", "c", "c", "d"} is a valid output.
     *   - {"b", "c", "c", "d", "a"} is not.
     *   
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * 
     * If base < 0, return null.
     * 
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     * 
     * @param base A numeric base to get an alphabet for.
     * @param trainingData The training data from which to generate frequency
     *                     counts. This array is not mutated.
     * @return A char[] that maps every digit of the base to a char that the
     *         digit should be translated into.
     */

//    public static char[] generateFrequencyAlphabet(int base,
//                                                   String[] trainingData) {
//        if (base < 0) {
//            return null;
//        }
//
//        if (base == 0) {
//            char[] emptyRes = {};
//            return emptyRes;
//        }
//
//        Map<Character, Integer> charMap = new HashMap<>();
//
//        // Generate a map of characters with counts as values
//        for (String x : trainingData) {
//            char[] charArray = x.toCharArray();
//            for (int j = 0; j < charArray.length; j++) {
//                char character = charArray[j];
//                //System.out.println("CHAR ARRAY" + charArray);
//                // Ignore anything that is not a-z
//                if (Character.isLetter(character)) {
//                    char lowerChar = Character.toLowerCase(character);
//                    if (charMap.containsKey(lowerChar)) {
//                        charMap.put(lowerChar, charMap.get(lowerChar) + 1);
//                    } else {
//                        charMap.put(lowerChar, 1);
//                    }
//                }
//            }
//        }
//
//        // Collect the keys and calculate the probabilities
//        Character[] keys = charMap.keySet().toArray(new Character[0]);
//        System.out.println("Map:" + charMap.toString());
//        Arrays.sort(keys);
//        System.out.println("Found keys" + Arrays.toString(keys));
//        // Truncate if the letters are more than the base
//        if (base < keys.length) {
//            System.out.println("Truncating keys");
//            keys = Arrays.copyOf(keys, base);
//            System.out.println("New keys: " + Arrays.toString(keys));
//        }
//
//        int total = 0;
//        for (char i : keys) {
//            total += charMap.get(i);
//        }
//        System.out.println("Total letters:" + total);
//
//        Map<Character, Float> cumProbs = new HashMap<Character, Float>();
//        float prior = 0f;
//        for (char i : keys) {
//            float prob = (float) charMap.get(i)/total;
//            cumProbs.put(i, prob + prior);
//            prior = prior + prob;
//        }
//        System.out.println("Cumulative Probs:" + cumProbs);
//
//
//        // Write the output array
//        char[] res = new char[base];
//        int j = 0;
//        for (char i : keys) {
//            int limit = Math.round(cumProbs.get(i) * base)-1;
//            System.out.println("Putting letter: " + i + " with limit " + limit);
//            while ((j <= limit) && (j < base)) {
//                res[j] = i;
//                j++;
//            }
//        }
//        System.out.println("Final result: " + Arrays.toString(res));
//        return res;
//
//    }

    public static char[] generateFrequencyAlphabet(int base, String[] trainingData) {

        if (base < 0) {
            return null;
        }

        if (base == 0) {
            return new char[0];
        }

        char[] allChar = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
                'n','o','p','q','r','s','t','u','v','w','x','y','z'};

        Map<Character, Integer> charMap = new HashMap<>();
        // Generates a new map with counts as values
        for (String x : trainingData) {
            char[] charArray = x.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                char character = charArray[j];
                //Ignore everything that is not a-z
                if(Character.isLetter(character)) {
                    char lowerChar = Character.toLowerCase(character);
                    if (charMap.containsKey(character)) {
                        charMap.put(lowerChar, charMap.get(lowerChar) + 1);
                    } else {
                        charMap.put(lowerChar, 1);
                    }
                }
            }
        }

        for (char x : allChar) {
            if (!charMap.containsKey(x)) {
                charMap.put(x, 0);
            }
        }

        // Collect keys, calculate probablities
        Character[] keys = charMap.keySet().toArray(new Character[0]);
        System.out.println("Map" + charMap.toString());
        Arrays.sort(keys);
        System.out.println("The keys are" + Arrays.toString(keys));
        // Truncate if letters are more than base
        //if (base < keys.length) {
        //    System.out.println("Truncating keys");
        //    keys = Arrays.copyOf(keys, base);
        //    System.out.println("New keys are" + Arrays.toString(keys));
        //}
        int totalNumChar = 0;
        for (int i : charMap.values()) {
            totalNumChar += i;
        }
        //System.out.println(totalNumChar);
        Map<Character, Double> PDF = new HashMap<>();
        for (char i : keys) {
            //System.out.println(charMap.get(i));
            PDF.put(i, ((double)charMap.get(i) / (double)totalNumChar));
        }
        System.out.println("PDF" + PDF.toString());

        Map<Character, Double> CDF = new HashMap<>();
        //System.out.print("CDF" + CDF.toString());
        double carry = 0;
        for (char i : keys) {
            if (PDF.get(i) + carry <= 1) {
                CDF.put(i, PDF.get(i) + carry);
            } else {
                CDF.put(i, 1.0);
            }
            carry += PDF.get(i);

        }
        System.out.println("CDF" + CDF.toString());

        // Write the output array
        char[] output = new char[base];
        for (int i = 0; i < output.length; i++) {
            for (char x : keys) {
                System.out.println("Letter = " + x + ", index = " + i + ", CDF*base = " + CDF.get(x) * base);
                if (CDF.get(x) * base > i) {
                    output[i] = x;
                    break;
                }
            }
        }
        //lionell noob
        return output;
    }

    public static void main(String[] args) {
        String[] trainingData = {"u","y"};
        System.out.println(generateFrequencyAlphabet(4, trainingData));
    }

}
