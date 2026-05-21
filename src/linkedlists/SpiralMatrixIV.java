package linkedlists;

/**
 * You have two numbers represented by a linked list, where each node contains a single
 * digit. The digits are stored in reverse order, such that the 1's digit is at the head
 * of the list. Write a function that adds the two numbers and returns the sum as a
 * linked list.
 * Example
 * Input: (7 -> 1 -> 6) + (5 -> 9 -> 2). That is, 617 + 295
 * Output: 2 -> 1 -> 9. That is, 912.
 *
 * Follow Up:
 * Suppose the digits are stored in forward order. Repeat the above problem.
 * Example
 * Input: (6 -> 1 -> 7) + (2 -> 9 -> 5). That is, 617 + 295
 * Output: 9 -> 1 -> 2. That is, 912.
 */

import java.util.Arrays;

/**
 * For #2 problem, its bit tricky. Take only matching lenghts of two lists and add.
 * Once thats complete, take remaining
 * numbers from big list and add it.
 */
public class SpiralMatrixIV {

    static class Node {
        int val;
        Node next;

        Node(int data) {
            this.val = data;
        }
    }
    public static void main(String[] args) {
        Node list1 = new Node(3);
        list1.next = new Node(0);
        list1.next.next = new Node(2);
        list1.next.next.next = new Node(6);
        list1.next.next.next.next = new Node(8);
        list1.next.next.next.next.next = new Node(1);
        list1.next.next.next.next.next.next = new Node(7);
        list1.next.next.next.next.next.next.next = new Node(9);
        list1.next.next.next.next.next.next.next.next= new Node(4);
        list1.next.next.next.next.next.next.next.next.next = new Node(2);
        list1.next.next.next.next.next.next.next.next.next.next = new Node(3);
        list1.next.next.next.next.next.next.next.next.next.next.next = new Node(4);

        SpiralMatrixIV o = new SpiralMatrixIV();
        o.spiralMatrix_clean_faster(10,1, list1);
        o.spiralMatrix_1(3,4, list1);

    }

    public int[][] spiralMatrix_1(int m, int n, Node head) {
        this.curr = head;

        int[][] res = new int[m][n];

        int r = 0;

        while (r <= m/2) {
            int i = r;
            int j = r;

            while (j < n-r) {
                res[i][j] = getVal();
                j++;
            }
            j--;
            i++;

            while (i < m-r && j <= n-1-r) {
                res[i][j] = getVal();
                i++;
            }
            i--;
            j--;

            while (j >= r && i > r) {
                res[i][j] = getVal();
                j--;
            }
            j++;
            i--;

            while (i > r && j < n-1-r) {
                res[i][j] = getVal();
                i--;
            }
            r++;
        }
        return res;
    }

    private int getVal() {
        if (curr == null) {
            return -1;
        }
        int val = curr.val;
        curr = curr.next;
        return val;
    }

    Node curr;

    public int[][] spiralMatrix_clean_faster(int rows, int columns, Node head) {
        int[][] matrix = new int[rows][];
        for (int i = 0; i < rows; i++) {
            matrix[i] = new int [columns];
            Arrays.fill(matrix[i], -1);
        }

        int topRow = 0, bottomRow = rows - 1, leftColumn = 0, rightColumn = columns - 1;
        while (head != null) {

            for (int col = leftColumn; col <= rightColumn && head != null; col++) {
                matrix[topRow][col] = head.val;
                head = head.next;
            }
            topRow++;


            for (int row = topRow; row <= bottomRow && head != null; row++) {
                matrix[row][rightColumn] = head.val;
                head = head.next;
            }
            rightColumn--;


            for (int col = rightColumn; col >= leftColumn && head != null; col--) {
                matrix[bottomRow][col] = head.val;
                head = head.next;
            }
            bottomRow--;


            for (int row = bottomRow; row >= topRow && head != null; row--) {
                matrix[row][leftColumn] = head.val;
                head = head.next;
            }
            leftColumn++;
        }

        return matrix;
    }
}
