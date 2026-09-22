public class BoothsAlgorithm {
    public static long multiply(int multiplicand, int multiplier) {
        long accumulator = 0;
        long shiftedMultiplicand = multiplicand;
        int previousBit = 0;
        long shiftedMultiplier = multiplier;

        for (int bit = 0; bit < Integer.SIZE; bit++) {
            int currentBit = (int) (shiftedMultiplier & 1);
            
            if (currentBit == 0 && previousBit == 1) 
                accumulator += shiftedMultiplicand;
            else if (currentBit == 1 && previousBit == 0) 
                accumulator -= shiftedMultiplicand;

            previousBit = currentBit;
            shiftedMultiplier >>= 1;
            shiftedMultiplicand <<= 1;
        }
        return accumulator;
    }

    public static void main(String[] args) {
        int multiplicand = Integer.parseInt(args[0]);
        int multiplier = Integer.parseInt(args[1]);
        System.out.println(multiply(multiplicand, multiplier));
    }
}