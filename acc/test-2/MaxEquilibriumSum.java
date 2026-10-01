public class MaxEquilibriumSum {
    public static long maximumSum(int[] values) {
        long total = 0;
        for (int value: values) total += value;

        long leftSum = 0;
        long best = Long.MIN_VALUE;
        for (int value: values) {
            total -= value;
            if (leftSum == total) 
                best = Math.max(best, leftSum);
            leftSum += value;
        }
        return (best == Long.MIN_VALUE)? 0 : best;
    }

    public static void main(String[] args) {
        int[] values = new int[args.length];
        for (int index = 0; index < args.length; index++) 
            values[index] = Integer.parseInt(args[index]);
        System.out.println(maximumSum(values));
    }
}