public class MaxProductSubarray {
    public static long maximumProduct(int[] values) {
        if (values.length == 0) 
            throw new IllegalArgumentException("Array must not be empty");
        long maximumEndingHere = values[0];
        long minimumEndingHere = values[0];
        long result = values[0];

        for (int index = 1; index < values.length; index++) {
            long value = values[index];
            long productOne = value * maximumEndingHere;
            long productTwo = value * minimumEndingHere;
            maximumEndingHere = Math.max(value, 
                                Math.max(productOne, productTwo));
            minimumEndingHere = Math.min(value, 
                                Math.min(productOne, productTwo));
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