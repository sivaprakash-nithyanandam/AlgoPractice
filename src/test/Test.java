package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author - Sivaprakash Nithyanandam Timestamp - 5/21/2022  11:27 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

public class Test {

    public static void main(String[] args) throws JsonProcessingException {

        Test2 test2 = new Test2();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        System.out.println(mapper.writeValueAsString(test2.getStartDateTime()));

        System.out.println(test2.getStartDateTime());

//        System.out.println(fullJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16));
//        System.out.println(fullJustify(new String[]{"What","must","be","acknowledgment","shall","be"}, 16));
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {

        int i = 0;
        int n = words.length;

        List<String> res = new ArrayList<>();
        StringBuilder currStr = new StringBuilder();
        int currLen = 0;
        int start = i;

        while (i < n) {
            if (currLen + words[i].length() <= maxWidth) {
                currLen += words[i].length() + 1;
                i++;
                if (i < n) {
                    continue;
                }
            }
            int spaces = maxWidth - (currLen - (i - start));
            int dividend = i - start - 1 > 0 ? i - start - 1 : 1;
            int extraSpaces = spaces/dividend;
            int leftSpaces = spaces % dividend;
            if (i >= n) {
                extraSpaces = 1;
                leftSpaces = 0;
            }

            while (start < i-1) {
                currStr.append(words[start]);
                int j = 0;
                while (j < extraSpaces) {
                    currStr.append(" ");
                    j++;
                }

                if (leftSpaces > 0) {
                    currStr.append(" ");
                    leftSpaces--;
                }
                start++;
            }

            currStr.append(words[start]);

            int k = maxWidth - currStr.length();
            while (k > 0) {
                currStr.append(" ");
                k--;
            }
            res.add(currStr.toString());
            currLen = 0;
            currStr.delete(0, currStr.length());
            start = i;
        }
        return res;
    }
}