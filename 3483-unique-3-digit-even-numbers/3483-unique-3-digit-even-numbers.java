class Solution {
    public int totalNumbers(int[] digits) {
        int[] freq = new int[10];
        for (int d : digits) {
            freq[d]++;
        }

        int count = 0;

        // Iterate over all 3-digit even numbers
        for (int num = 100; num < 1000; num += 2) {
            int[] tempFreq = freq.clone();
            int n = num;
            boolean valid = true;

            for (int i = 0; i < 3; i++) {
                int d = n % 10;
                n /= 10;
                if (tempFreq[d] > 0) {
                    tempFreq[d]--;
                } else {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                count++;
            }
        }

        return count;
    }
}