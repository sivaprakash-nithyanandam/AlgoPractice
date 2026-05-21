package MathAndLogic;

/**
 * Get all the prime numbers until a given number.
 */

/**
 * Sieve of Eratosthenes
 */
public class ListOfPrimes {

  public static void main(String[] args) {
    int[] arr = getPrimesUntilGiven(1000);
    for(int i = 0; i < arr.length; i++) {
      if (arr[i] == 0) {
        System.out.print(i + ",");
      }
    }
  }

  //TODO - FYI
  public static boolean isPrime(int n) {
    if (n <= 1) return false;
    if (n <= 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    for (int i = 5; i * i <= n; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0) return false;
    }
    return true;
  }

  private static int[] getPrimesUntilGiven(int max) {
    int[] flags = new int[max + 1];
    flags[0] = 1;
    flags[1] = 1;
    int prime = 2;
    while(prime < Math.sqrt(max)) {
      crossOff(flags, prime);
      prime = getNextPrime(flags, prime);
    }
    return flags;
  }

  private static int getNextPrime(int[] flags, int prime) {
    int next = prime + 1;
    while(flags[next] == 1) {
      next++;
    }
    return next;
  }

  private static void crossOff(int[] flags, int prime) {
    for(int i = prime * prime; i < flags.length; i += prime) {
      flags[i] = 1;
    }
  }
}
