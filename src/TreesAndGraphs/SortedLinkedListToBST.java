package TreesAndGraphs;

/**
 * Given a sorted linked list, convert into binary search tree.
 */
public class SortedLinkedListToBST {

    private static LLNode head;

    static class LLNode {
        private int data;
        private LLNode prev;
        private LLNode next;

        public LLNode(int data) {
            this.data = data;
        }
    }

    static class TNode {
        private int data;
        private TNode left;
        private TNode right;

        public TNode(int data) {
            this.data = data;
        }
    }

    private static void push(int data) {
        LLNode llNode = new LLNode(data);
        llNode.next = head;
        if (head != null) {
            head.prev = llNode;
        }
        head = llNode;
    }

    private static void printLinkedList() {
        LLNode llNode = head;
        while (llNode != null) {
            System.out.print(llNode.data + ",");
            llNode = llNode.next;
        }
    }

    private static TNode convertLLToBst(int n) {
        if (n == 0) {
            return null;
        }
        TNode left = convertLLToBst(n/2);
        TNode root = new TNode(head.data);
        root.left = left;
        head = head.next;
        root.right = convertLLToBst(n - (n/2) - 1);
        return root;
    }

    private static int getLLSize() {
        int count = 0;
        LLNode llNode = head;
        while (llNode != null) {
            count++;
            llNode = llNode.next;
        }
        return count;
    }

    public static void main(String[] args) {
        push(7);
        push(6);
        push(5);
        push(4);
        push(3);
        push(2);
        push(1);

        System.out.println("Given linked list:");
        printLinkedList();
        System.out.println("");

        int llSize = getLLSize();
        TNode tNode = convertLLToBst(llSize);
        printTreeNodes(tNode);

        ConvertBinarySearchTreetoSortedDoublyLinkedList o = new ConvertBinarySearchTreetoSortedDoublyLinkedList();
        TNode dllNode = o.treeToDoublyList(tNode);
        System.out.println("");
        System.out.println("Final linked list:");
        printDoublyLinkedList(dllNode);
    }

    private static void printDoublyLinkedList(TNode tNode) {
        System.out.print(tNode.data + ",");
        TNode curr = tNode.right;
        while (curr!= null && curr != tNode) {
            System.out.print(curr.data + ",");
            curr = curr.right;
        }
    }

    private static void printTreeNodes(TNode tNode) {
        if (tNode != null) {
            System.out.print(tNode.data + ",");
            printTreeNodes(tNode.left);
            printTreeNodes(tNode.right);
        }
    }

    static class ConvertBinarySearchTreetoSortedDoublyLinkedList {

        TNode newHead = null;
        TNode newTail = null;

        public TNode treeToDoublyList(TNode tNode) {
            dfs(tNode);
            if (newHead == null) {
                return tNode;
            }
            newHead.left = newTail;
            newTail.right = newHead;

            return newHead;
        }

        private void dfs(TNode node) {
            if (node == null) {
                return;
            }
            dfs(node.left);
            if (newHead == null) {
                newHead = node;
            } else {
                node.left = newTail;
                newTail.right = node;
            }

            newTail = node;

            dfs(node.right);
        }
    }
}
