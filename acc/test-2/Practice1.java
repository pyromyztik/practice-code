import java.util.*;

public class Practice1 {
    static long Karatsubas(long x, long y) {
        if(x <= 10 || y <= 10)
            return x * y;

        int n = Math.max(Long.toString(x).length(), 
                         Long.toString(y).length());
        int half = (n + 1) / 2;

        long a = x / (long) Math.pow(10, half);
        long b = x % (long) Math.pow(10, half);        
        long c = y / (long) Math.pow(10, half);
        long d = y % (long) Math.pow(10, half);

        long ac = Karatsubas(a, c);
        long bd = Karatsubas(b, d);
        long adbc = Karatsubas(a + b, c + d) - ac - bd;

        return ac * (long)Math.pow(10, half * 2) 
           + adbc * (long)Math.pow(10, half) + bd;
    }

    static long booths(int multiplicand, int multiplier) {
        long A = 0;
        long shQ = multiplicand;
        int Qminus1 = 0;
        long shM = multiplier;

        for(int bit = 0; bit < Integer.SIZE; bit++) {
            int currentBit = (int) (shM & 1);

            if(currentBit == 0 && Qminus1 == 1)
                A += shQ;
            else if(currentBit == 1 && Qminus1 == 0)
                A -= shQ;

            shQ <<= 1;
            shM >>= 1;
            Qminus1 = currentBit;
        }
        return A;
    }

    static int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a % b);
    }

    static String Lexico1stPalinStr(String input) {
        int[] frequencies = new int[Character.MAX_VALUE + 1];
        for(char character: input.toCharArray()) 
            frequencies[character]++;

        int oddCount = 0; String middleChar = "";
        for(int i = 0; i < frequencies.length; i++)
            if((frequencies[i] & 1) == 1) {
                oddCount++;
                middleChar = String.valueOf((char) i);
            }
        if(oddCount >= 2) throw new IllegalArgumentException("Can't have more than one odd-count character (Palindrome impossible)");

        StringBuilder firstHalf = new StringBuilder("");
        for(int i = 0; i < frequencies.length; i++) {
            for(int count = 0; count < frequencies[i] / 2; count++)
                firstHalf.append((char) i);
        }
        return firstHalf + middleChar + firstHalf.reverse();
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter 2 integers: ");
        int a = input.nextInt();
        int b = input.nextInt();

        System.out.println("Booth's Product: " 
                    + booths(a, b));
        System.out.println("Karatsuba's Product: " 
                    + Karatsubas(a, b));
        System.out.println("Greatest Common Divisor: " 
                    + gcd(a, b));

        System.out.println("Enter a string: ");
        String palInput = input.next();        
        System.out.println("LexicographicallyFirstPalindromicString: " 
                    + Lexico1stPalinStr(palInput)); 

        input.close();
    }
}