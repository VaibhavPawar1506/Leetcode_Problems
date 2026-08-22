class Solution {
    public int[] sortedSquares(int[] nums) {
        int res [] = new int[nums.length];
        for(int i = 0; i<nums.length; i++){
            if(nums[i]==0){
                res[i]=nums[i];
            }
            res[i]=nums[i]*nums[i];
        }
        Arrays.sort(res);
        return res;
    }
}