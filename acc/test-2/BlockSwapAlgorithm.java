import java.util.Arrays;

public class BlockSwapAlgorithm {
    public static void rotateLeft(int[] values, int distance) {
        if (values.length == 0) return;
        distance = ((distance % values.length) + values.length) % values.length;
        blockSwap(values, 0, distance, values.length);
    }

    private static void blockSwap(int[] values, int start, 
                                        int distance, int length) {
        if (distance == 0 || distance == length) return;
        if (distance == length - distance) {
            swapBlocks(values, start, start + distance, distance);
        } else if (distance < length - distance) {
            swapBlocks(values, start, start + length - distance, distance);
            blockSwap(values, start, 
                            distance, length - distance);
        } else {
            swapBlocks(values, start, start + distance, length - distance);
            blockSwap(values, start + length - distance, 
                        2 * distance - length, distance);
        }
    }

    private static void swapBlocks(int[] values, int first, 
                                         int second, int length) {
        for (int index = 0; index < length; index++) {
            int temporary = values[first + index];
            values[first + index] = values[second + index];
            values[second + index] = temporary;
        }
    }

    public static void main(String[] args) {
        int distance = Integer.parseInt(args[0]);
        int[] values = new int[args.length - 1];
        for (int index = 0; index < values.length; index++) {
            values[index] = Integer.parseInt(args[index + 1]);
        }
        rotateLeft(values, distance);
        System.out.println(Arrays.toString(values));
    }
}