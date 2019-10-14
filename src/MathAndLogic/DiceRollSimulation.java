package MathAndLogic;

/**
 * A die simulator generates a random number from 1 to 6 for each roll. You introduced a constraint to the generator such that it cannot roll the number i more than rollMax[i] (1-indexed) consecutive times.
 *
 * Given an array of integers rollMax and an integer n, return the number of distinct sequences that can be obtained with exact n rolls.
 *
 * Two sequences are considered different if at least one element differs from each other.
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 2, rollMax = [1,1,2,2,2,3]
 * Output: 34
 * Explanation: There will be 2 rolls of die, if there are no constraints on the die,
 * there are 6 * 6 = 36 possible combinations. In this case, looking at rollMax array,
 * the numbers 1 and 2 appear at most once consecutively,
 * therefore sequences (1,1) and (2,2) cannot occur, so the final answer is 36-2 = 34.
 * Example 2:
 *
 * Input: n = 2, rollMax = [1,1,1,1,1,1]
 * Output: 30
 * Example 3:
 *
 * Input: n = 3, rollMax = [1,1,1,2,2,3]
 * Output: 181
 *
 *
 * Constraints:
 *
 * 1 <= n <= 5000
 * rollMax.length == 6
 * 1 <= rollMax[i] <= 15
 */
//TODO Revisit
public class DiceRollSimulation {
    public static void main(String[] args) {
        DiceRollSimulation o = new DiceRollSimulation();
        System.out.println(o.dieSimulator(3, new int[]{1,1,2,2,2,3}));
    }

    public int dieSimulator(int n, int[] rollMax) {
        int mod = 1000000007;
        long[][] dp = new long[6][16];
        for(int i = 0;i < 6;i++){
            dp[i][1] = 1;
        }
        for(int i = 2;i <= n;i++){
            long[][] ndp = new long[6][16];
            for(int j = 0;j < 6;j++){
                for(int k = 0;k < 16;k++){
                    for(int l = 0;l < 6;l++){
                        if(j == l){
                            if(k+1 <= rollMax[l]){
                                ndp[l][k+1] += dp[j][k];
                                ndp[l][k+1] %= mod;
                            }
                        }else{
                            ndp[l][1] += dp[j][k];
                            ndp[l][1] %= mod;
                        }
                    }
                }
            }
            dp = ndp;
        }

        long ret = 0;
        for(int i = 0;i < 6;i++){
            for(int j = 0;j < 16;j++) {
                ret += dp[i][j];
            }
        }
        return (int)(ret%mod);
    }
}
