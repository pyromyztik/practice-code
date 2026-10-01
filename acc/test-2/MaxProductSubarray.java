public class MaxProductSubarray {
    static long maximumProduct(int[] values) {
        if(values.length == 0)
            throw new IllegalArgumentException("Array cannot be empty");

        long maximumEndingHere = values[0];
        long minimumEndingHere = values[0];
        long result = values[0];

        for(int index = 1; index < values.length; index++) {
            long value = values[index];
            long product1 = value * maximumEndingHere;
            long product2 = value * minimumEndingHere;

            maximumEndingHere = Math.max(value,
                                Math.max(product1, product2));
            minimumEndingHere = Math.min(value,
                                Math.min(product1, product2));
            result = Math.max(result, maximumEndingHere);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] values = new int[args.length];
        for (int index = 0; index < args.length; index++) 
            values[index] = Integer.parseInt(args[index]);
        System.out.println(maximumProduct(values));
    }
}