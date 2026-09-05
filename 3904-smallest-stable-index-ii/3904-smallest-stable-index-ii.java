class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        
        // 1. Create an array to store the prefix maximums
        // prefMax[i] will hold the maximum value in nums[0...i]
        int[] prefMax = new int[n];
        prefMax[0] = nums[0];
        for (int i = 1; i < n; i++) {
            // The max up to current index is either the previous max or the current number
            prefMax[i] = Math.max(prefMax[i - 1], nums[i]);
        }
        
        // 2. Create an array to store the suffix minimums
        // suffMin[i] will hold the minimum value in nums[i...n-1]
        int[] suffMin = new int[n];
        suffMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            // The min from current index to end is either the next min or the current number
            suffMin[i] = Math.min(suffMin[i + 1], nums[i]);
        }
        
        // 3. Iterate through each index to find the first stable one
        for (int i = 0; i < n; i++) {
            // Calculate instability score using our precomputed values
            // Score = (Max of nums[0..i]) - (Min of nums[i..n-1])
            long instabilityScore = (long) prefMax[i] - suffMin[i];
            
            // Check if this index is stable (score <= k)
            if (instabilityScore <= k) {
                return i; // Found the smallest stable index, return immediately
            }
        }
        
        // 4. If we checked all indices and none were stable, return -1
        return -1;
    }
}