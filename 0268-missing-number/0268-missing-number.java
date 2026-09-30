class Solution {
    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int exp = n * (n + 1) / 2;
        int num = 0;
        for(int k : nums){
            num += k;
        }
        return exp - num;
    }
}