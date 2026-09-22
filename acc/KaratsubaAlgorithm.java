import java.math.BigInteger;

public class KaratsubaAlgorithm {
    public static BigInteger multiply(BigInteger first, BigInteger second) {
        boolean negative = first.signum() != second.signum();
        BigInteger result = multiplyPositive(first.abs(), second.abs());
        return negative ? result.negate() : result;
    }

    private static BigInteger multiplyPositive(BigInteger first, 
                                               BigInteger second) {
        if (first.signum() == 0 || second.signum() == 0) return BigInteger.ZERO;
        if (first.bitLength() <= 32 || second.bitLength() <= 32) {
            return first.multiply(second);
        }
        int split = Math.max(first.bitLength(), second.bitLength()) / 2;
        BigInteger highFirst = first.shiftRight(split);
        BigInteger lowFirst = first.subtract(highFirst.shiftLeft(split));
        BigInteger highSecond = second.shiftRight(split);
        BigInteger lowSecond = second.subtract(highSecond.shiftLeft(split));

        BigInteger lowProduct = multiplyPositive(lowFirst, lowSecond);
        BigInteger highProduct = multiplyPositive(highFirst, highSecond);
        BigInteger middleProduct = multiplyPositive(lowFirst.add(highFirst),
                lowSecond.add(highSecond)).subtract(lowProduct).subtract(highProduct);

        return highProduct.shiftLeft(2 * split)
                .add(middleProduct.shiftLeft(split))
                .add(lowProduct);
    }

    public static void main(String[] args) {
        BigInteger first = new BigInteger(args[0]);
        BigInteger second = new BigInteger(args[1]);
        System.out.println(multiply(first, second));
    }
}