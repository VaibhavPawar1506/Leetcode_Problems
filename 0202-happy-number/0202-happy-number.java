class Solution {
    public int SumOfDigits(int n){
        int sum = 0;
        while(n!=0){
            int rem = n%10;
            sum = sum + (rem * rem);
            n/=10;
        }
        return sum;
    }
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;
        while(fast!=1){
            slow=SumOfDigits(slow);
            fast=SumOfDigits(SumOfDigits(fast));

            if(fast == 1){
                return true;
            }
            if(slow == fast){
                return false;
            }
        }
        return true;
    }
}