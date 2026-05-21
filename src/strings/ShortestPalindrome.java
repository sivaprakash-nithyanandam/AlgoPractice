package strings;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 19,2024 - 10:45 PM
 */

public class ShortestPalindrome {

    public static void main(String[] args) {
        ShortestPalindrome o = new ShortestPalindrome();
        System.out.println(o.shortestPalindrome_kmp("aacecaaa"));
        System.out.println(o.shortestPalindrome_rolling_hash("abcddcbaxya"));
        System.out.println(o.shortestPalindrome_rolling_hash("abcd"));
        System.out.println(o.shortestPalindrome_rolling_hash("adcba"));
    }

    public String shortestPalindrome_rolling_hash(String s) {
        long hashBase = 29;
        long modValue = (long) 1e9 + 7;
        long forwardHash = 0, reverseHash = 0, powerValue = 1;
        int palindromeEndIndex = -1;

        // Calculate rolling hashes and find the longest palindromic prefix
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);

            // Update forward hash
            forwardHash = (forwardHash * hashBase + (currentChar - 'a' + 1)) %
                    modValue;

            // Update reverse hash
            reverseHash = (reverseHash + (currentChar - 'a' + 1) * powerValue) %
                    modValue;
            powerValue = (powerValue * hashBase) % modValue;

            // If forward and reverse hashes match, update palindrome end index
            if (forwardHash == reverseHash) {
                palindromeEndIndex = i;
            }
        }

        // Find the remaining suffix after the longest palindromic prefix
        String suffix = s.substring(palindromeEndIndex + 1);
        // Reverse the remaining suffix
        StringBuilder reversedSuffix = new StringBuilder(suffix).reverse();

        // Prepend the reversed suffix to the original string and return the result
        return reversedSuffix.append(s).toString();
    }

    public String shortestPalindrome_kmp(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb = sb.reverse();

        int[] lps = new int[sb.length() * 2 + 1];
        int len = helper(lps, s + "#" + sb);

        sb = new StringBuilder(s.substring(len));
        sb = sb.reverse();
        return sb.append(s).toString();
    }

    private int helper(int[] lps, String str) {
        int j = 1;
        int i = 0;

        while (j < str.length()) {
            if (str.charAt(j) == str.charAt(i)) {
                i++;
                lps[j] = i;
                j++;
            } else {
                if (i == 0) {
                    j++;
                } else {
                    i = lps[i-1];
                }
            }
        }
        return lps[str.length()-1];
    }

    public String shortestPalindrome_two_pointer(String s) {
        int length = s.length();
        if (length == 0) {
            return s;
        }

        // Find the longest palindromic prefix
        int left = 0;
        for (int right = length - 1; right >= 0; right--) {
            if (s.charAt(right) == s.charAt(left)) {
                left++;
            }
        }

        // If the whole string is a palindrome, return the original string
        if (left == length) {
            return s;
        }

        // Extract the suffix that is not part of the palindromic prefix
        String nonPalindromeSuffix = s.substring(left);
        StringBuilder reverseSuffix = new StringBuilder(
                nonPalindromeSuffix
        ).reverse();

        // Form the shortest palindrome by prepending the reversed suffix
        return reverseSuffix
                .append(shortestPalindrome_two_pointer(s.substring(0, left)))
                .append(nonPalindromeSuffix)
                .toString();
    }

    public String shortestPalindrome_bruteforce(String s) {
        int length = s.length();
        String reversedString = new StringBuilder(s).reverse().toString();

        // Iterate through the string to find the longest palindromic prefix
        for (int i = 0; i < length; i++) {
            if (s.substring(0, length - i).equals(reversedString.substring(i))) {
                return new StringBuilder(reversedString.substring(0, i))
                        .append(s)
                        .toString();
            }
        }
        return "";
    }
}
