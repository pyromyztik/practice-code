
public class BoothsAlgorithm {
    public static long multiply(int multiplicand, int multiplier) {
        long A = 0;
        long shQ = multiplicand;
        int Qminus1 = 0;
        long shM = multiplier;

        for(int bit = 0; bit < Integer.SIZE; bit++) {
            int currentBit = (int) (shM & 1);
            
            if (currentBit == 0 && Qminus1 == 1) 
                A += shQ;
            else if (currentBit == 1 && Qminus1 == 0) 
                A -= shQ;

            Qminus1 = currentBit;
            shM >>= 1;
            shQ <<= 1;
        }
        return A;
    }

    public static void main(String[] args) {
        int multiplicand = Integer.parseInt(args[0]);
        int multiplier = Integer.parseInt(args[1]);
        System.out.println(multiply(multiplicand, multiplier));
    }
}