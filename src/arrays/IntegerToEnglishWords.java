package arrays;
/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

/**
 *
 */
public class IntegerToEnglishWords {

  public static void main(String[] args) {
    IntegerToEnglishWords o = new IntegerToEnglishWords();
    System.out.println(o.numberToWords_v1(1234567));
    System.out.println(o.numberToWords_faster(100001));
  }

  private static final String[] LESS_THAN_TWENTY = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
  private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
  private static final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

  public String numberToWords_v1(int num) {
    if (num == 0) {
      return "Zero";
    }

    int i = 0;
    String words = "";

    while (num > 0) {
      if (num % 1000 != 0) {
        words = helper_v1(num % 1000) + THOUSANDS[i] + " " + words;
      }
      num /= 1000;
      i++;
    }

    return words.trim();
  }

  private String helper_v1(int num) {
    if (num == 0) {
      return "";
    }
    else if (num < 20) {
      return LESS_THAN_TWENTY[num] + " ";
    }
    else if (num < 100) {
      return TENS[num / 10] + " " + helper_v1(num % 10);
    }
    else {
      return LESS_THAN_TWENTY[num / 100] + " Hundred " + helper_v1(num % 100);
    }
  }

  String[] tens = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
  String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven",
      "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

  public String numberToWords_faster(int num) {

    if (num == 0)
      return "Zero";

    return helper(num).trim();
  }

  private String helper(int num) {
    StringBuilder sb=new StringBuilder();
    if (num >= 1000000000) {
      sb.append(helper(num / 1000000000)).append(" Billion ").append( helper(num % 1000000000));
    } else if  (num >= 1000000) {
      sb.append(helper(num / 1000000)).append(" Million ").append( helper(num % 1000000));
    }else if (num >= 1000) {
      sb.append(helper(num / 1000)).append(" Thousand ").append( helper(num % 1000));
    }else if (num >= 100) {
      sb.append(helper(num / 100)).append(" Hundred ").append( helper(num % 100));
    }else if (num >= 20) {
      sb.append(tens[num / 10]).append(" ").append( helper(num % 10));
    } else {
      sb.append(ones[num]);
    }
    return sb.toString().trim();

  }

  public String one(int num) {
    switch (num) {
      case 1:
        return "One";
      case 2:
        return "Two";
      case 3:
        return "Three";
      case 4:
        return "Four";
      case 5:
        return "Five";
      case 6:
        return "Six";
      case 7:
        return "Seven";
      case 8:
        return "Eight";
      case 9:
        return "Nine";
    }
    return "";
  }

  public String twoLessThan20(int num) {
    switch (num) {
      case 10:
        return "Ten";
      case 11:
        return "Eleven";
      case 12:
        return "Twelve";
      case 13:
        return "Thirteen";
      case 14:
        return "Fourteen";
      case 15:
        return "Fifteen";
      case 16:
        return "Sixteen";
      case 17:
        return "Seventeen";
      case 18:
        return "Eighteen";
      case 19:
        return "Nineteen";
    }
    return "";
  }

  public String ten(int num) {
    switch (num) {
      case 2:
        return "Twenty";
      case 3:
        return "Thirty";
      case 4:
        return "Forty";
      case 5:
        return "Fifty";
      case 6:
        return "Sixty";
      case 7:
        return "Seventy";
      case 8:
        return "Eighty";
      case 9:
        return "Ninety";
    }
    return "";
  }

  public String two(int num) {
    if (num == 0) {
      return "";
    } else if (num < 10) {
      return one(num);
    } else if (num < 20) {
      return twoLessThan20(num);
    } else {
      int tenner = num / 10;
      int rest = num - tenner * 10;
      if (rest != 0) {
        return ten(tenner) + " " + one(rest);
      } else {
        return ten(tenner);
      }
    }
  }

  public String three(int num) {
    int hundred = num / 100;
    int rest = num - hundred * 100;
    String res = "";
    if (hundred * rest != 0) {
      res = one(hundred) + " Hundred " + two(rest);
    } else if ((hundred == 0) && (rest != 0)) {
      res = two(rest);
    } else if ((hundred != 0) && (rest == 0)) {
      res = one(hundred) + " Hundred";
    }
    return res;
  }

  public String numberToWords(int num) {
    if (num == 0) {
      return "Zero";
    }

    int billion = num / 1000000000;
    int million = (num - billion * 1000000000) / 1000000;
    int thousand = (num - billion * 1000000000 - million * 1000000) / 1000;
    int rest = num - billion * 1000000000 - million * 1000000 - thousand * 1000;

    String result = "";
    if (billion != 0) {
      result = three(billion) + " Billion";
    }
    if (million != 0) {
      if (!result.isEmpty()) {
        result += " ";
      }
      result += three(million) + " Million";
    }
    if (thousand != 0) {
      if (!result.isEmpty()) {
        result += " ";
      }
      result += three(thousand) + " Thousand";
    }
    if (rest != 0) {
      if (!result.isEmpty()) {
        result += " ";
      }
      result += three(rest);
    }
    return result;
  }

}
