class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        // Step 1: Cyclic Sort
        // Place each number in its correct position if possible
        for (int i = 0; i < n; i++) {
            // While the current number is in the valid range [1, n]
            // AND it is NOT already in its correct position (index = value - 1)
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                // Swap nums[i] with the number at its target position
                int correctIndex = nums[i] - 1;
                // Swap
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }

        // Step 2: Find the first missing positive
        // Iterate through the array. If index i does not contain i+1, then i+1 is missing.
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        // Step 3: If all positions 1..n are filled, the answer is n + 1
        return n + 1;
    }
}