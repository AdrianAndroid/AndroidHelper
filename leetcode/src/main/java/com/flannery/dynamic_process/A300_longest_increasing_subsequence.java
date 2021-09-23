package com.flannery.dynamic_process;

public class A300_longest_increasing_subsequence {

    public static void main(String[] args) {
        //new Solution().lengthOfLIS(new int[]{10,9,2,5,3,7,101,18]);
        System.out.println(new Solution().lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
    }

    static class Solution {
        public int lengthOfLIS2(int[] nums) {
            int len = 1, n = nums.length;
            if (n == 0) {
                return 0;
            }
            int[] d = new int[n + 1];
            d[len] = nums[0];
            for (int i = 1; i < n; ++i) {
                if (nums[i] > d[len]) {
                    d[++len] = nums[i];
                } else {
                    int l = 1, r = len, pos = 0; // 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                    while (l <= r) {
                        int mid = (l + r) >> 1;
                        if (d[mid] < nums[i]) {
                            pos = mid;
                            l = mid + 1;
                        } else {
                            r = mid - 1;
                        }
                    }
                    d[pos + 1] = nums[i];
                }
            }
            return len;
        }

        public int lengthOfLIS(int[] nums) {
            if (nums.length == 0) return 0;
            // 定义
            int dp[] = new int[nums.length]; //存储结果
            dp[0] = 1;
            int result = 1; //结果,只要有一个值
            for (int i = 1; i < nums.length; i++) {
                dp[i] = 1;
                for (int j = 0; j < i; j++) { // 遍历前边的数
                    if (nums[i] > nums[j])
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                result = Math.max(result, dp[i]);
            }
            return result;
        }
    }

}
