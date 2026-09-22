public class LongestSequenceOfOnes {
    public static int longestAfterOneFlip(int value) {
        int best = 0;
        int current = 0;
        int previous = 0;

        for (int bit = 0; bit < Integer.SIZE; bit++) {
            if ((value & 1) == 1) 
                current++;
            else {
                previous = current;
                current = 0;
            }
            best = Math.max(best, previous + current + 1);
            value >>>= 1;
        }
        return Math.min(best, Integer.SIZE);
    }

    public static void main(String[] args) {
        int value = Integer.parseInt(args[0]);
        System.out.println(longestAfterOneFlip(value));
    }
}