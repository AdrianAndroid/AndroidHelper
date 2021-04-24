package cn.enjoyedu.bio;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static double myPow(double x, int n) {
        // n是正数的时候 --
        // n是负数的时候 --
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }
        double pow = 1;
        while (n > 0) {
            // 基数的时候
            if (n % 2 == 1) pow *= x;
            x *= x;
            n >>= 1; // / 2 忽略了基数的情况
        }
        return pow;
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s == null) return list;
        char[] cp = p.toCharArray();
        Arrays.sort(cp);

        int len = p.length();
        loop:
        for (int i = 0; i < s.length() - len + 1; i++) {
            final char[] chs = s.substring(i, i + len).toCharArray();
            Arrays.sort(chs);
            for (int j = 0; j < len; j++) {
                if (chs[j] != cp[j]) continue loop;
            }
            list.add(i);
        }
        return list;
    }

    public static boolean lemonadeChange(int[] bills) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(5, 0);
        map.put(10, 0);
        map.put(20, 0);
        for (int i = 0; i < bills.length; i++) {
            int change = bills[i] - 5;//需要找零
            while (change > 0) {
                if (map.get(20) > 0 && change >= 20) {
                    change -= 20;
                    map.put(20, map.get(20) - 1);
                } else if (map.get(10) > 0 && change >= 10) {
                    change -= 10;
                    map.put(10, map.get(10) - 1);
                } else if (map.get(5) > 0 && change >= 5) {
                    change -= 5;
                    map.put(5, map.get(5) - 1);
                } else {
                    return false;
                }
            }
            map.put(bills[i], map.get(bills[i]) + 1);
        }
        final HashSet<Object> objects = new HashSet<>();
        objects.add("");

        return true;
    }

    /*
    [[".","8","7","6","5","4","3","2","1"]
    ,["2",".",".",".",".",".",".",".","."]
    ,["3",".",".",".",".",".",".",".","."]
    ,["4",".",".",".",".",".",".",".","."]
    ,["5",".",".",".",".",".",".",".","."]
    ,["6",".",".",".",".",".",".",".","."]
    ,["7",".",".",".",".",".",".",".","."]
    ,["8",".",".",".",".",".",".",".","."]
    ,["9",".",".",".",".",".",".",".","."]]
     */
    public boolean isValidSudoku(char[][] board) {
        int len = board.length;
        boolean[][] rows = new boolean[9][10];
        boolean[][] cols = new boolean[9][10];
        boolean[][] maix = new boolean[9][10];
        //全部遍历一次
        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++){
                Character ch = board[i][j];
                if(ch == '.') continue; //继续验证
                int index = ch - '0';
                if(rows[i][index]) return false; //已经出现过一次
                if(cols[i][index]) return false; //已经出现过一次
                final boolean[] m = maix[j / 3 + i / 3 * 3];
                if(m[index]) return false;

                rows[i][index] = true;
                cols[j][index] = true;
                m[index] = true;
            }
        }

        return true;
    }


    public static void main(String[] args) {

        boolean[][] rows = new boolean[9][10];
        boolean[][] cols = new boolean[9][10];
        boolean[][] maix = new boolean[9][10];
        //System.out.println(lemonadeChange(new int[]{5,5,5,10,20}));
        //System.out.println('9'-'0');
//        System.out.println(findAnagrams("cbaebabacd", "abc"));
        //System.out.println(findAnagrams("abab", "ab"));
        //System.out.println("cbaebabacd".substring(0, 3));
        // System.out.println(myPow(2.00000, -2147483648));


//        Map<Character, Character> map = new HashMap<>();
//        map.put('(',')');
//        map.put('{','}');
//        map.put('[',']');
//        System.out.println(map);
//        String s = "()";
//        final Character c = s.charAt(0);
//        Stack<Character> stack = new Stack<>();
//        stack.peek();
//
//        List<Integer> list;
//        final List<Integer> list = Arrays.asList(-1, 0, 1, 2, -1, -4);
//        final List<List<Integer>> l = Arrays.asList(list);
//        System.out.println(l);
//        for (List<Integer> list : threeSum(new int[]{-1, 0, 1, 2, -1, -4})) {
//            System.out.println(Arrays.toString(list.toArray()));
//        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); //使用l  r
        System.out.println(Arrays.toString(nums));
        int l, r; //左右指针
        //此时 -2 -1 0 1 2
        for (int i = 0; i < nums.length - 2; i++) {
            l = i + 1;
            r = nums.length - 1;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) { //正确结果
                    while (l < r && nums[l] == nums[l + 1]) l++;
                    while (l < r && nums[r - 1] == nums[r]) r--;
                    result.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    l++;
                    r--;
                } else if (sum > 0) {
                    r--;
                } else { // sum < 0
                    l++;
                }
            }
        }

        return result;
    }

    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        int[] nums = new int[2];

        int len = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            Character ch = s.charAt(i);
            if (map.containsKey(ch) && stack.peek() == map.get(ch)) {
                stack.pop(); //可以匹配上的话就消掉
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    void test() {
        final ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.newCondition();

        new AtomicInteger();
    }

    // lev 1
    class Food {
    }

    // lev 2
    class Fruit extends Food {
    }

    class Meat extends Food {
    }

    // lev 3
    class Apple extends Fruit {
    }

    class Banana extends Fruit {
    }

    class Pork extends Meat {
    }

    class Beef extends Meat {
    }

    // lev 4
    class RedApple extends Apple {
    }

    class GreenApple extends Apple {
    }

    class Plate<T> {
        private T item;

        public Plate(T t) {
            item = t;
        }

        public void set(T t) {
            item = t;
        }

        public T get() {
            return item;
        }
    }

    void test2() {
        Plate<? extends Fruit> p = new Plate<>(new Apple());
        //不能存入任何元素
//        p.set(new Fruit());
//        p.set(new Apple());
        //读取出来只能放在基类
        Fruit newFruit = p.get();
        Object f2 = p.get();
//        Apple f3 = p.get();

        Plate<? super Fruit> p2 = new Plate<>(new Fruit());
        p2.set(new Fruit());
        p2.set(new Apple());
        //读出曲磊
        final Object n1 = p2.get();
//        final Fruit n2 = p2.get();
//        final Apple n3 = p2.get();

    }
}
