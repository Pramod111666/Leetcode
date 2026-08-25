package com.DSApractice;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class DeleteNodeLinkedList {

    /**
     * APPROACH 1: BRUTE FORCE (Shifting all values)
     * Time Complexity: O(N) where N is the number of nodes after the given node.
     * Space Complexity: O(1) as we are only using a few pointers.
     */
    public static void deleteNodeBruteForce(ListNode node) {
        // Step 1: Loop until we reach the second-to-last node.
        // We do this because we need to shift values leftwards one by one.
        while (node.next != null) {

            // Step 2: Overwrite the current node's value with the next node's value.
            node.val = node.next.val;

            // Step 3: Check if the next node is the last node in the list.
            if (node.next.next == null) {
                // Step 4: If it is the last node, cut it off from the list.
                node.next = null;
                // Exit the loop since we have successfully deleted the duplicate last node.
                break;
            }

            // Step 5: Move to the next node to continue shifting values.
            node = node.next;
        }
    }

    /**
     * APPROACH 2: OPTIMAL (O(1) Trick)
     * Time Complexity: O(1) - Constant time, as we only perform two operations regardless of list size.
     * Space Complexity: O(1) - Constant space, no extra memory used.
     */
    public static void deleteNodeOptimal(ListNode node) {
        // Step 1: Instead of deleting the current node (which we can't do without the head),
        // we copy the value of the IMMEDIATE NEXT node into our CURRENT node.
        node.val = node.next.val;

        // Step 2: Now that our current node holds the data we want to keep,
        // we bypass (skip over) the immediate next node by pointing our current
        // node's 'next' pointer to the node AFTER the next one.
        node.next = node.next.next;

        // The original next node is now disconnected from the list and will be garbage collected.
    }


    // --- UTILITY METHODS FOR TESTING ---

    // Helper method to print the linked list
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }


    // --- MAIN METHOD ---
    public static void main(String[] args) {
        System.out.println("--- Testing Optimal Solution ---");
        // Create the linked list: 4 -> 5 -> 1 -> 9
        ListNode head1 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node1 = new ListNode(1);
        ListNode node9 = new ListNode(9);

        head1.next = node5;
        node5.next = node1;
        node1.next = node9;

        System.out.print("Original List: ");
        printList(head1);

        // We want to delete node with value 5
        System.out.println("Deleting node with value 5...");
        deleteNodeOptimal(node5);

        System.out.print("List after deletion: ");
        printList(head1);

        System.out.println("\n--- Testing Brute Force Solution ---");
        // Create another linked list: 4 -> 5 -> 1 -> 9
        ListNode head2 = new ListNode(4);
        ListNode node5_2 = new ListNode(5);
        ListNode node1_2 = new ListNode(1);
        ListNode node9_2 = new ListNode(9);

        head2.next = node5_2;
        node5_2.next = node1_2;
        node1_2.next = node9_2;

        System.out.print("Original List: ");
        printList(head2);

        // We want to delete node with value 1
        System.out.println("Deleting node with value 1...");
        deleteNodeBruteForce(node1_2);

        System.out.print("List after deletion: ");
        printList(head2);
    }
}