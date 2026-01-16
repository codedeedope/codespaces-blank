public class Crazytriangle {
    
    public static void lowerHalf(int n) {
        String star = "*";
        String space = " ";
        for(int i = 0; i <= n; ++i) {
            String stars = star.repeat(i);
            String spaces = space.repeat(2*(n-i)+3);
            System.out.println(stars+spaces+stars);
        }
    }

    public static void upperHalf(int n ) {
        for (int i =n ;i>0 ;--i ) {
            for (int j =0 ;j<i ;++j ) {
                System.out.print("*");
            }
            for (int j = 1; j <= 2 * (n - i) + 3; j++) {
                System.out.print(" ");
            }
             for (int j =0 ;j<i ;++j ) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int n = 5;
        upperHalf(n);
        lowerHalf(n);
    }
}
