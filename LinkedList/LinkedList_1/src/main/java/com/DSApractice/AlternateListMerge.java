package com.DSApractice;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class AlternateListMerge {

    /**
     * Inserts nodes of head2 alternately into head1.
     *
     * @param head1 The head of the first linked list.
     * @param head2 The head of the second linked list.
     * @return The new head of the remaining nodes of head2.
     */
    public static Node mergeLists(Node head1, Node head2) {
        if (head1 == null) return head2;
        if (head2 == null) return null;

        Node curr1 = head1;
        Node curr2 = head2;

        while (curr1 != null && curr2 != null) {
            // Save the next pointers of both lists
            Node next1 = curr1.next;
            Node next2 = curr2.next;

            // Insert curr2 into head1 between curr1 and next1
            curr1.next = curr2;
            curr2.next = next1;

            // Move pointers forward for the next iteration
            curr1 = next1;
            curr2 = next2;
        }

        // curr2 now points to the remaining nodes of head2 (if any)
        return curr2;
    }

    // Helper method to print the linked list
    public static void printList(Node head) {
        if (head == null) {
            System.out.println("<empty>");
            return;
        }
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + (curr.next != null ? " -> " : ""));
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // --- Test Case 1 ---
        System.out.println("--- Test Case 1 ---");
        Node head1 = new Node(10);
        head1.next = new Node(9);

        Node head2 = new Node(6);
        head2.next = new Node(1);
        head2.next.next = new Node(2);
        head2.next.next.next = new Node(3);
        head2.next.next.next.next = new Node(4);
        head2.next.next.next.next.next = new Node(5);

        System.out.print("head1 before: ");
        printList(head1);
        System.out.print("head2 before: ");
        printList(head2);

        head2 = mergeLists(head1, head2);

        System.out.print("head1 after:  ");
        printList(head1);
        System.out.print("head2 after:  ");
        printList(head2);

        // --- Test Case 2 ---
        System.out.println("\n--- Test Case 2 ---");
        Node h1 = new Node(1);
        h1.next = new Node(2);
        h1.next.next = new Node(3);

        Node h2 = new Node(4);
        h2.next = new Node(5);
        h2.next.next = new Node(6);

        System.out.print("head1 before: ");
        printList(h1);
        System.out.print("head2 before: ");
        printList(h2);

        h2 = mergeLists(h1, h2);

        System.out.print("head1 after:  ");
        printList(h1);
        System.out.print("head2 after:  ");
        printList(h2);
    }
}