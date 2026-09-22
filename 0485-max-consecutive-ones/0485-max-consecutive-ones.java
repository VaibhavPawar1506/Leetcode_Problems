class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int max_count = 0;
        int current_count = 0;
        for(int i = 0; i<nums.length; i++){
            if(nums[i] == 1){
                current_count++;
            }
            else{
                max_count = Math.max(max_count, current_count);
                current_count = 0;
            }
        }
        return Math.max(max_count, current_count);
    }
}