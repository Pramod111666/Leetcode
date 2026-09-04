package com.DSApractice;

public class MiddleOfLinkedList {

    // Definition for singly-linked list node.
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // ==========================================
    // Logic:
    // Step 1: Traverse the linked list completely to store all nodes in an array/ArrayList.
    // Step 2: Use the size of the array to directly find the middle index (size / 2).
    // Step 3: Return the node present at that middle index in the array.
    //
    // Time Complexity: O(N) - Single pass through the linked list to populate the list.
    // Space Complexity: O(N) - Extra space required to store references to all N nodes in an ArrayList.
    public static ListNode findMiddleBruteForce(ListNode head) {
        if (head == null) return null;

        // Step 1: Store all nodes in a dynamic array
        java.util.List<ListNode> nodesList = new java.util.ArrayList<>();
        ListNode temp = head;
        while (temp != null) {
            nodesList.add(temp);
            temp = temp.next;
        }

        // Step 2 & 3: Find middle index (automatically rounds to second middle for even length) and return
        int middleIndex = nodesList.size() / 2;
        return nodesList.get(middleIndex);
    }

    // ==========================================
    // 2. BETTER APPROACH (Two-Pass Method)
    // ==========================================
    // Logic:
    // Step 1: Traverse the list to count the total number of nodes (N).
    // Step 2: Calculate the target middle index as N / 2.
    // Step 3: Reset pointer to head and traverse (N / 2) steps forward to reach the middle node.
    //
    // Time Complexity: O(N) - Requires two passes: N operations for counting + N/2 operations for reaching middle = O(1.5 N) ~ O(N).
    // Space Complexity: O(1) - Uses only integer variables for counting, no additional data structure.
    public static ListNode findMiddleBetter(ListNode head) {
        if (head == null) return null;

        // Step 1: Count total nodes
        int length = 0;
        ListNode temp = head;
        while (temp != null) {
            length++;
            temp = temp.next;
        }

        // Step 2: Calculate target steps to the middle
        int middleIndex = length / 2;

        // Step 3: Advance pointer to the middle node
        temp = head;
        for (int i = 0; i < middleIndex; i++) {
            temp = temp.next;
        }

        return temp;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (Tortoise and Hare Algorithm)
    // ==========================================
    // Logic:
    // Step 1: Initialize two pointers, 'slow' and 'fast', both pointing to the head.
    // Step 2: Move 'slow' by 1 step and 'fast' by 2 steps in each iteration.
    // Step 3: Continue until 'fast' reaches the end (null) or the last node ('fast.next' is null).
    // Step 4: When 'fast' reaches the end, 'slow' will be exactly at the middle node.
    //
    // Time Complexity: O(N) - Single pass through the list (fast pointer completes N/2 iterations).
    // Space Complexity: O(1) - Requires only two pointer variables.
    public static ListNode findMiddleOptimal(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // Move fast by 2 steps and slow by 1 step
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Slow pointer now points to the middle node
        return slow;
    }

    // Helper method to print the linked list from a given node onward
    public static void printListFromNode(ListNode node) {
        System.out.print("[");
        while (node != null) {
            System.out.print(node.val + (node.next != null ? ", " : ""));
            node = node.next;
        }
        System.out.println("]");
    }

    // Helper method to build a linked list from an array
    public static ListNode createList(int[] arr) {
        if (arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    // ==========================================
    // MAIN METHOD
    // ==========================================
    public static void main(String[] args) {
        // Test Case 1: Odd length list [1, 2, 3, 4, 5]
        int[] oddArr = {1, 2, 3, 4, 5};
        ListNode head1 = createList(oddArr);

        System.out.println("--- Test Case 1: Odd Length [1, 2, 3, 4, 5] ---");

        ListNode middleBrute1 = findMiddleBruteForce(head1);
        System.out.print("Brute Force Result: ");
        printListFromNode(middleBrute1);

        ListNode middleBetter1 = findMiddleBetter(head1);
        System.out.print("Better Solution Result: ");
        printListFromNode(middleBetter1);

        ListNode middleOptimal1 = findMiddleOptimal(head1);
        System.out.print("Optimal Solution Result: ");
        printListFromNode(middleOptimal1);

        System.out.println();

        // Test Case 2: Even length list [1, 2, 3, 4, 5, 6]
        int[] evenArr = {1, 2, 3, 4, 5, 6};
        ListNode head2 = createList(evenArr);

        System.out.println("--- Test Case 2: Even Length [1, 2, 3, 4, 5, 6] ---");

        ListNode middleBrute2 = findMiddleBruteForce(head2);
        System.out.print("Brute Force Result: ");
        printListFromNode(middleBrute2);

        ListNode middleBetter2 = findMiddleBetter(head2);
        System.out.print("Better Solution Result: ");
        printListFromNode(middleBetter2);

        ListNode middleOptimal2 = findMiddleOptimal(head2);
        System.out.print("Optimal Solution Result: ");
        printListFromNode(middleOptimal2);
    }
}