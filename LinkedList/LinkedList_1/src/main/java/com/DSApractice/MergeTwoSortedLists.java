package com.DSApractice;

public class MergeTwoSortedLists {

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
    // 1. BRUTE FORCE APPROACH (Extract to Array, Sort, & Rebuild)
    // ==========================================
    // Logic:
    // Step 1: Traverse both list1 and list2 to copy all node values into a dynamic array (ArrayList).
    // Step 2: Sort the array in non-decreasing order using built-in sorting.
    // Step 3: Iterate through the sorted array and construct a brand-new linked list.
    //
    // Time Complexity: O((N + M) log(N + M)) - Extracting elements takes O(N + M), sorting takes O((N + M) log(N + M)), rebuilding takes O(N + M).
    // Space Complexity: O(N + M) - Extra space for the dynamic array and creating brand new nodes for the output list.
    public static ListNode mergeTwoListsBruteForce(ListNode list1, ListNode list2) {
        java.util.List<Integer> values = new java.util.ArrayList<>();

        // Step 1: Collect values from list1
        ListNode temp = list1;
        while (temp != null) {
            values.add(temp.val);
            temp = temp.next;
        }

        // Collect values from list2
        temp = list2;
        while (temp != null) {
            values.add(temp.val);
            temp = temp.next;
        }

        // If both lists were empty, return null
        if (values.isEmpty()) {
            return null;
        }

        // Step 2: Sort all collected values
        java.util.Collections.sort(values);

        // Step 3: Rebuild a new linked list from sorted values
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }

        return dummy.next;
    }

    // ==========================================
    // 2. BETTER APPROACH (Recursive Splicing)
    // ==========================================
    // Logic:
    // Step 1: Base Cases: If list1 is null, return list2; if list2 is null, return list1.
    // Step 2: Compare the heads of list1 and list2.
    // Step 3: Whichever node has the smaller value becomes the head of the merged list,
    //        and its 'next' pointer is assigned to the result of recursively merging the rest of the lists.
    //
    // Time Complexity: O(N + M) - Each recursive call processes one node until both lists are traversed.
    // Space Complexity: O(N + M) - Call stack memory overhead due to recursion (depth of call stack is N + M).
    public static ListNode mergeTwoListsBetter(ListNode list1, ListNode list2) {
        // Step 1: Base cases
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        // Step 2 & 3: Recursive selection of smaller element
        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsBetter(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsBetter(list1, list2.next);
            return list2;
        }
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (Iterative Two-Pointer In-Place Merging)
    // ==========================================
    // Logic:
    // Step 1: Create a dummy node to act as the head anchor and a 'tail' pointer initialized to dummy.
    // Step 2: Loop while both list1 and list2 are not null:
    //        - Compare list1.val and list2.val.
    //        - Attach the node with the smaller value to tail.next and advance that list's pointer.
    //        - Move tail pointer forward (tail = tail.next).
    // Step 3: After the loop, if one list still has remaining nodes, directly attach it to tail.next.
    // Step 4: Return dummy.next as the head of the merged list.
    //
    // Time Complexity: O(N + M) - Traverses both lists once in a single pass.
    // Space Complexity: O(1) - Rearranges existing node pointers in-place without extra memory or call stack overhead.
    public static ListNode mergeTwoListsOptimal(ListNode list1, ListNode list2) {
        // Step 1: Initialize dummy node and pointer tail
        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;

        // Step 2: Compare and splice nodes iteratively
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        // Step 3: Attach remaining non-empty list directly
        if (list1 != null) {
            tail.next = list1;
        } else if (list2 != null) {
            tail.next = list2;
        }

        // Step 4: Return merged head
        return dummy.next;
    }

    // Helper method to print linked list elements
    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        ListNode temp = head;
        while (temp != null) {
            System.out.print(temp.val + (temp.next != null ? ", " : ""));
            temp = temp.next;
        }
        System.out.println("]");
    }

    // Helper method to create linked list from array
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
        // Example 1: list1 = [1,2,4], list2 = [1,3,4]
        int[] arr1 = {1, 2, 4};
        int[] arr2 = {1, 3, 4};

        System.out.println("--- Test Case 1: [1,2,4] & [1,3,4] ---");

        // Testing Brute Force
        ListNode l1 = createList(arr1);
        ListNode l2 = createList(arr2);
        ListNode resBrute = mergeTwoListsBruteForce(l1, l2);
        System.out.print("Brute Force Result: ");
        printList(resBrute);

        // Testing Better (Recursive)
        l1 = createList(arr1);
        l2 = createList(arr2);
        ListNode resBetter = mergeTwoListsBetter(l1, l2);
        System.out.print("Better Approach Result: ");
        printList(resBetter);

        // Testing Optimal (Iterative)
        l1 = createList(arr1);
        l2 = createList(arr2);
        ListNode resOptimal = mergeTwoListsOptimal(l1, l2);
        System.out.print("Optimal Approach Result: ");
        printList(resOptimal);

        System.out.println();

        // Example 2: Both lists empty
        System.out.println("--- Test Case 2: [] & [] ---");
        ListNode empty1 = createList(new int[]{});
        ListNode empty2 = createList(new int[]{});
        ListNode resEmpty = mergeTwoListsOptimal(empty1, empty2);
        System.out.print("Optimal Approach Result: ");
        printList(resEmpty);

        System.out.println();

        // Example 3: One list empty list1 = [], list2 = [0]
        System.out.println("--- Test Case 3: [] & [0] ---");
        ListNode listEmpty = createList(new int[]{});
        ListNode listZero = createList(new int[]{0});
        ListNode resSingle = mergeTwoListsOptimal(listEmpty, listZero);
        System.out.print("Optimal Approach Result: ");
        printList(resSingle);
    }
}