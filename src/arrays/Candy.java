package arrays;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 6/25/2021  7:53 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/candy/
 *
 * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 *
 * You are giving candies to these children subjected to the following requirements:
 *
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
 * Return the minimum number of candies you need to have to distribute the candies to the children.
 *
 *
 *
 * Example 1:
 *
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 * Example 2:
 *
 * Input: ratings = [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 * The third child gets 1 candy because it satisfies the above two conditions.
 *
 *
 * Constraints:
 *
 * n == ratings.length
 * 1 <= n <= 2 * 104
 * 0 <= ratings[i] <= 2 * 104
 */
public class Candy {

  public static void main(String[] args) {
    Candy o = new Candy();
    System.out.println(o.candy_best(new int[]{1,2,3,1,0}));
    System.out.println(o.candy_best(new int[]{29,51,87,87,72,12}));
    System.out.println(o.candy(new int[]{1,2,87,87,87,2,1}));
    System.out.println(o.candy(new int[]{1,2,2}));
    System.out.println(o.candy(new int[]{1,0,2}));
  }

  public static int candy_best(int[] ratings) {
    int n = ratings.length;

    // минимум: первый получает одну конфету
    int candies = 1;

    // длина текущего подъёма
    int up = 1;

    // длина текущего спуска
    int down = 0;

    // длина последнего завершённого подъёма
    int peak = 1;

    for (int i = 1; i < n; i++) {

      if (ratings[i] > ratings[i - 1]) {
        // пошли вверх
        up++;
        peak = up;
        down = 0;

        candies += up;


      } else if (ratings[i] < ratings[i - 1]) {
        // пошли вниз
        down++;
        up = 1;

        candies += down;

        // если спуск стал длиннее пика – даём бонус вершине
        if (down >= peak) candies++;

      } else {
        // плато – всё обнуляется
        up = 1;
        down = 0;
        peak = 1;

        candies += 1;
      }
    }

    return candies;
  }
  public int candy(int[] ratings) {
    int[] candies = new int[ratings.length];
    Arrays.fill(candies, 1);
    for (int i = 1; i < ratings.length; i++) {
      if (ratings[i] > ratings[i - 1]) {
        candies[i] = candies[i - 1] + 1;
      }
    }
    int sum = candies[ratings.length - 1];
    for (int i = ratings.length - 2; i >= 0; i--) {
      if (ratings[i] > ratings[i + 1]) {
        candies[i] = Math.max(candies[i], candies[i + 1] + 1);
      }
      sum += candies[i];
    }
    return sum;
  }

  public int candy_brute_force(int[] ratings) {
    int[] candies = new int[ratings.length];
    Arrays.fill(candies, 1);
    boolean hasChanged = true;
    while (hasChanged) {
      hasChanged = false;
      for (int i = 0; i < ratings.length; i++) {
        if (i != ratings.length - 1 && ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
          candies[i] = candies[i + 1] + 1;
          hasChanged = true;
        }
        if (i > 0 && ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]) {
          candies[i] = candies[i - 1] + 1;
          hasChanged = true;
        }
      }
    }
    int sum = 0;
    for (int candy : candies) {
      sum += candy;
    }
    return sum;
  }
}
