/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
/**
 * Definition for singly-linked list.
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        // Base Case: If the array is null or empty, there's nothing to merge.
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Call the helper function to start the Divide and Conquer process
        return helper(lists, 0, lists.length - 1);
    }

    // Helper function: Recursively divides the array and merges the results
    private ListNode helper(ListNode[] lists, int left, int right) {
        // Base Case 1: If left index equals right index, we have only one list left.
        // We just return that list (it's already sorted).
        if (left == right) {
            return lists[left];
        }

        // Divide: Find the middle point
        int mid = left + (right - left) / 2;

        // Conquer: Recursively merge the left half and the right half
        ListNode leftPart = helper(lists, left, mid);
        ListNode rightPart = helper(lists, mid + 1, right);

        // Combine: Merge the two sorted halves into one
        return mergeTwoLists(leftPart, rightPart);
    }

    // Standard function to merge two sorted linked lists into one
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // Create a dummy node to act as the start of our new list.
        // This makes it easier to handle the head of the list without special cases.
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Traverse both lists as long as both are not null
        while (l1 != null && l2 != null) {
            // Compare values and attach the smaller node to our result list
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next; // Move the pointer of the list we just picked
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            // Move our result pointer forward
            current = current.next;
        }

        // If one list is exhausted, attach the remaining nodes of the other list
        // (Since the remaining list is already sorted, we can just link the rest)
        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }

        // The actual head of our merged list is the node after dummy
        return dummy.next;
    }
}