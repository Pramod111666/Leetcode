package com.DSApractice;

import java.util.Stack;

// Definition for singly-linked list.
class ListNode {
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

public class ReverseLinkedList {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Using Stack)
    // ==========================================
    // Logic:
    // - Traverse the linked list and push all node values onto an explicit Stack.
    // - Traverse the linked list a second time and pop values from the Stack to overwrite node values.
    // - Because a stack operates as Last-In-First-Out (LIFO), this reverses the order of elements.
    //
    // Time Complexity: O(2 * N) = O(N) - Two full passes over the list of length N.
    // Space Complexity: O(N) - Extra space needed to store N element values in the Stack.
    public static ListNode reverseListBruteForce(ListNode head) {
        if (head == null) return null;

        Stack<Integer> stack = new Stack<>();
        ListNode temp = head;

        // Step 1: Pass 1 - Push all node values into the stack
        while (temp != null) {
            stack.push(temp.val);
            temp = temp.next;
        }

        temp = head;
        // Step 2: Pass 2 - Pop values from the stack and overwrite node values
        while (temp != null) {
            temp.val = stack.pop();
            temp = temp.next;
        }

        return head;
    }

    // ==========================================
    // 2. BETTER APPROACH (Recursive Pointer Reversal)
    // ==========================================
    // Logic:
    // - Recursively reach the end of the list to find the new head.
    // - On the return pass of recursion, make the next node point back to the current node (`head.next.next = head`).
    // - Break the original forward link (`head.next = null`) to avoid cycles.
    //
    // Time Complexity: O(N) - Visits every node once during the recursive calls.
    // Space Complexity: O(N) - Stack space used by the system call stack due to N recursive frames.
    public static ListNode reverseListRecursive(ListNode head) {
        // Step 1: Base Case - empty list or reached the last node
        if (head == null || head.next == null) {
            return head;
        }

        // Step 2: Recursively reverse the rest of the list
        ListNode newHead = reverseListRecursive(head.next);

        // Step 3: Change pointers - make the next node point back to current node
        head.next.next = head;

        // Step 4: Disconnect current forward pointer to avoid circular links
        head.next = null;

        // Step 5: Return the newly established head node
        return newHead;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (Iterative Three-Pointer Reversal)
    // ==========================================
    // Logic:
    // - Use three pointers: `prev` (tracks previous node), `curr` (tracks current node), and `next` (temporarily stores next node).
    // - Loop through the list, flip `curr.next` to point to `prev`, then move `prev` and `curr` one step forward.
    //
    // Time Complexity: O(N) - Single pass through the linked list.
    // Time Complexity: O(N) - Single pass through the linked list.
    // Space Complexity: O(1) - Modifies pointers in-place using constant auxiliary variables.
    public static ListNode reverseListOptimal(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        // Step 1: Traverse the linked list until curr reaches the end (null)
        while (curr != null) {
            // Step 2: Store next node before breaking the forward connection
            ListNode nextTemp = curr.next;

            // Step 3: Reverse the current node's pointer direction
            curr.next = prev;

            // Step 4: Advance prev and curr pointers one step forward
            prev = curr;
            curr = nextTemp;
        }

        // Step 5: prev points to the new head of the reversed list
        return prev;
    }

    // ==========================================
    // HELPER METHODS & MAIN METHOD
    // ==========================================

    // Utility to create a linked list from an array
    private static ListNode createList(int[] values) {
        if (values.length == 0) return null;
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        return head;
    }

    // Utility to print a linked list
    private static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        ListNode temp = head;
        while (temp != null) {
            sb.append(temp.val);
            if (temp.next != null) sb.append(", ");
            temp = temp.next;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int[] input = {1, 2, 3, 4, 5};

        System.out.println("--- 206. Reverse Linked List ---\n");

        // Test 1: Brute Force
        ListNode head1 = createList(input);
        System.out.print("Original List      : ");
        printList(head1);
        ListNode result1 = reverseListBruteForce(head1);
        System.out.print("Brute Force Result : ");
        printList(result1);
        System.out.println("----------------------------------------------");

        // Test 2: Recursive (Better)
        ListNode head2 = createList(input);
        System.out.print("Original List      : ");
        printList(head2);
        ListNode result2 = reverseListRecursive(head2);
        System.out.print("Recursive Result   : ");
        printList(result2);
        System.out.println("----------------------------------------------");

        // Test 3: Optimal (Iterative In-Place)
        ListNode head3 = createList(input);
        System.out.print("Original List      : ");
        printList(head3);
        ListNode result3 = reverseListOptimal(head3);
        System.out.print("Optimal Result     : ");
        printList(result3);
        System.out.println("----------------------------------------------");
    }
}