package MathAndLogic;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 6/26/2021  12:49 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.HashMap;

/**
 * https://leetcode.com/problems/encode-and-decode-tinyurl/
 *
 * Note: This is a companion problem to the System Design problem: Design TinyURL.
 * TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl and it returns a short URL such as http://tinyurl.com/4e9iAk. Design a class to encode a URL and decode a tiny URL.
 *
 * There is no restriction on how your encode/decode algorithm should work. You just need to ensure that a URL can be encoded to a tiny URL and the tiny URL can be decoded to the original URL.
 *
 * Implement the Solution class:
 *
 * Solution() Initializes the object of the system.
 * String encode(String longUrl) Returns a tiny URL for the given longUrl.
 * String decode(String shortUrl) Returns the original long URL for the given shortUrl. It is guaranteed that the given shortUrl was encoded by the same object.
 *
 *
 * Example 1:
 *
 * Input: url = "https://leetcode.com/problems/design-tinyurl"
 * Output: "https://leetcode.com/problems/design-tinyurl"
 *
 * Explanation:
 * Solution obj = new Solution();
 * string tiny = obj.encode(url); // returns the encoded tiny url.
 * string ans = obj.decode(tiny); // returns the original url after deconding it.
 *
 *
 * Constraints:
 *
 * 1 <= url.length <= 104
 * url is guranteed to be a valid URL.
 */
public class EncodeandDecodeTinyURL {

  public static void main(String[] args) {
    EncodeandDecodeTinyURL o = new EncodeandDecodeTinyURL();
    String shortUrl;
    shortUrl = o.encode("https://leetcode.com/problems/design-tinyurl");
    System.out.println(shortUrl);
    System.out.println(o.decode(shortUrl));

    shortUrl = o.encode("https://leetcode.com/problems/design-tinyurl1");
    System.out.println(shortUrl);
    System.out.println(o.decode(shortUrl));

    shortUrl = o.encode("https://leetcode.com/problems/design-tinyurl2");
    System.out.println(shortUrl);
    System.out.println(o.decode(shortUrl));
  }

  String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  HashMap<String, String> map = new HashMap<>();
  int count = 1;

  public String getString() {
    int c = count;
    StringBuilder sb = new StringBuilder();
    while (c > 0) {
      c--;
      sb.append(chars.charAt(c % 62));
      c /= 62;
    }
    return sb.toString();
  }

  public String encode(String longUrl) {
    String key = getString();
    map.put(key, longUrl);
    count++;
    return "http://tinyurl.com/" + key;
  }

  public String decode(String shortUrl) {
    return map.get(shortUrl.replace("http://tinyurl.com/", ""));
  }

}
