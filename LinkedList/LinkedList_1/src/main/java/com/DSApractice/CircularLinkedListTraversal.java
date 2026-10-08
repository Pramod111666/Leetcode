package com.DSApractice;

public class CircularLinkedListTraversal {

    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static class Solution {
        public void printCircularLinkedList(ListNode head) {
            if (head == null) {
                return;
            }

            ListNode curr = head;
            StringBuilder sb = new StringBuilder();

            // Use a do-while loop so we process the head node first
            // and stop when we loop completely back to the head.
            do {
                sb.append(curr.val).append(" ");
                curr = curr.next;
            } while (curr != head);

            // Print the resulting space-separated values
            System.out.println(sb.toString().trim());
        }
    }

    // Main method with test cases
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test Case 1: 1 -> 7 -> 8 -> 10 (points back to 1)
        ListNode head1 = new ListNode(1);
        ListNode node7 = new ListNode(7);
        ListNode node8 = new ListNode(8);
        ListNode node10 = new ListNode(10);

        head1.next = node7;
        node7.next = node8;
        node8.next = node10;
        node10.next = head1; // Completes the circle

        System.out.print("Test 1 Output: ");
        sol.printCircularLinkedList(head1);
        // Expected Output: 1 7 8 10

        // Test Case 2: 2 -> 5 -> 7 -> 8 -> 10 (points back to 2)
        ListNode head2 = new ListNode(2);
        ListNode n5 = new ListNode(5);
        ListNode n7 = new ListNode(7);
        ListNode n8 = new ListNode(8);
        ListNode n10 = new ListNode(10);

        head2.next = n5;
        n5.next = n7;
        n7.next = n8;
        n8.next = n10;
        n10.next = head2; // Completes the circle

        System.out.print("Test 2 Output: ");
        sol.printCircularLinkedList(head2);
        // Expected Output: 2 5 7 8 10

        // Test Case 3: Single node circular list (42 -> 42)
        ListNode head3 = new ListNode(42);
        head3.next = head3;

        System.out.print("Test 3 Output: ");
        sol.printCircularLinkedList(head3);
        // Expected Output: 42
    }
}
