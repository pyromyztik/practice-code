public class EuclidAlgorithm {
    public static long gcd(long first, long second) {
        first = Math.abs(first);
        second = Math.abs(second);
        while (second != 0) {
            long remainder = first % second;
            first = second;
            second = remainder;
        }
        return first;
    }

    public static void main(String[] args) {
        long first = Long.parseLong(args[0]);
        long second = Long.parseLong(args[1]);
        System.out.println(gcd(first, second));
    }
}