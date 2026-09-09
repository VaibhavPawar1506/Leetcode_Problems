class Solution {
    public long countCommas(long n) {
        long ans = 0;
        long lo = 1_000L;
        long k = 1;

        while (lo <= n) {
            long hi = Math.min(n, lo * 1000 - 1);
            ans += (hi - lo + 1) * k;
            lo *= 1_000;   // next group starts 3 more digits up
            k++;
        }
        return ans;
    }
}