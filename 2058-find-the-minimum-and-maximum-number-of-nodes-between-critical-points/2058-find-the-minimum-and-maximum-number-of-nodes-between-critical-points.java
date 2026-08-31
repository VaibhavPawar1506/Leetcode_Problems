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
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int firstCritIdx = -1;
        int lastCritIdx = -1;
        int minDist = Integer.MAX_VALUE;
        int index = 1; // Start at second node (index 1)

    ListNode prev = head;
    ListNode curr = head.next;

        while (curr.next != null) {
            // Check for critical point
            if ((curr.val > prev.val && curr.val > curr.next.val) || 
                (curr.val < prev.val && curr.val < curr.next.val)) {
                
                if (firstCritIdx == -1) {
                    firstCritIdx = index;
                } else {
                    // Calculate min distance between adjacent critical points
                    minDist = Math.min(minDist, index - lastCritIdx);
                }
                lastCritIdx = index;
            }
            
            // Move pointers
            prev = curr;
            curr = curr.next;
            index++;
        }

        if (firstCritIdx == -1 || firstCritIdx == lastCritIdx) {
            return new int[]{-1, -1};
        }

        int maxDist = lastCritIdx - firstCritIdx;
        return new int[]{minDist, maxDist};
            }
        }