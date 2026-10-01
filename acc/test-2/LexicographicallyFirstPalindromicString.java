public class LexicographicallyFirstPalindromicString {
    public static String makePalindrome(String value) {
        int[] frequencies = new int[Character.MAX_VALUE + 1];
        for (char character : value.toCharArray()) 
            frequencies[character]++;

        int oddCharacters = 0;
        for (int frequency : frequencies) 
            if ((frequency & 1) == 1) oddCharacters++;
        if (oddCharacters > 1) 
            throw new IllegalArgumentException("No palindromic permutation exists");

        StringBuilder firstHalf = new StringBuilder();
        String middle = "";
        for (int character = 0; character < frequencies.length; character++) {
            int frequency = frequencies[character];
            if ((frequency & 1) == 1) 
                middle = String.valueOf((char) character);
            for (int copy = 0; copy < frequency / 2; copy++) 
                firstHalf.append((char) character);
        }
        return firstHalf + middle + firstHalf.reverse();
    }

    public static void main(String[] args) {
        System.out.println(makePalindrome(args[0]));
    }
}