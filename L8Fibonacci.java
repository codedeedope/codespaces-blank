
import java.util.HashMap;

public class L8Fibonacci {

    private static HashMap<Integer, Integer> memo = new HashMap<>();

    static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static int fibonacciIterative(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int a = 0, b = 1, sum;
        for (int i = 2; i <= n; i++) {
            sum = a + b;
            a = b;
            b = sum; // reuse previous computation
        }
        return b;
    }

    public static int fibonacciMemo(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        if (memo.containsKey(n)) {
            return memo.get(n); // Return stored value
        }
        int result = fibonacciMemo(n - 1) + fibonacciMemo(n - 2);
        memo.put(n, result); // Store result for future calls

        return result;
    }

    public static void main(String[] args) {
        int num = 40;
        for (int i = 0; i < 10; ++i) {
            var i11 = System.nanoTime();
            var fib1 = fibonacci(num);
            var i12 = System.nanoTime();
            var i21 = System.nanoTime();
            var fib2 = fibonacciIterative(num);
            var i22 = System.nanoTime();
            var i31 = System.nanoTime();
            var fib3 = fibonacciMemo(num);
            var i32 = System.nanoTime();
            System.out.print(String.format("%s, %s, %s", i12 - i11, i22 - i21, i32 - i31));
            System.out.println();
        }
    }
}
