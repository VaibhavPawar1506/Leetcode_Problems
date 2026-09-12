class Solution {

    static class Interval {
        int l;
        int r;
        int w;
        int idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long score;
        int[] indices;

        State(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        Interval[] arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        // Sort by starting point.
        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l) {
                return Integer.compare(a.l, b.l);
            }

            if (a.r != b.r) {
                return Integer.compare(a.r, b.r);
            }

            return Integer.compare(a.idx, b.idx);
        });

        // Store all starting positions for binary search.
        int[] starts = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = arr[i].l;
        }

        // next[i] = first interval whose start > arr[i].r
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {

            int low = i + 1;
            int high = n;

            while (low < high) {
                int mid = low + (high - low) / 2;

                if (starts[mid] > arr[i].r) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            next[i] = low;
        }

        /*
         * dp[i][k]:
         * best answer using intervals from i onward
         * with at most k intervals remaining.
         */
        State[][] dp = new State[n + 1][5];

        // Base cases.
        for (int k = 0; k <= 4; k++) {
            dp[n][k] = new State(0, new int[0]);
        }

        for (int i = n - 1; i >= 0; i--) {

            for (int k = 0; k <= 4; k++) {

                // Option 1: skip current interval.
                State skip = dp[i + 1][k];

                State best = skip;

                // Option 2: take current interval.
                if (k > 0) {

                    State after = dp[next[i]][k - 1];

                    long takeScore = arr[i].w + after.score;

                    int[] takeIndices =
                        new int[after.indices.length + 1];

                    takeIndices[0] = arr[i].idx;

                    System.arraycopy(
                        after.indices,
                        0,
                        takeIndices,
                        1,
                        after.indices.length
                    );

                    // Sort because lexicographical comparison
                    // must use original indices.
                    Arrays.sort(takeIndices);

                    State take =
                        new State(takeScore, takeIndices);

                    if (take.score > best.score) {
                        best = take;
                    } else if (take.score == best.score &&
                               lexicographicallySmaller(
                                   take.indices,
                                   best.indices)) {
                        best = take;
                    }
                }

                dp[i][k] = best;
            }
        }

        return dp[0][4].indices;
    }

    private boolean lexicographicallySmaller(
        int[] a,
        int[] b
    ) {

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {

            if (a[i] != b[i]) {
                return a[i] < b[i];
            }
        }

        // If one is a prefix of the other,
        // shorter array is lexicographically smaller.
        return a.length < b.length;
    }
}