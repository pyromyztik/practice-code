
public class BinaryPalindrome {
    public static boolean isBinaryPalindrome(int x) {
        int reversed = 0;
        int original = x;

        while (x > 0) {
            reversed <<= 1;
            reversed |= (x & 1);
            x >>= 1;
        }
        return reversed == original;
    }
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);

        if(isBinaryPalindrome(x)) 
            System.out.println(x + " (" + Integer.toBinaryString(x) 
                                 + ") is a binary palindrome.");
        else
            System.out.println(x + " (" + Integer.toBinaryString(x) 
                                 + ") is NOT a binary palindrome.");
    }
}