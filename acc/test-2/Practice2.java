import java.util.*;

public class Practice2 {
    static int longestFlipBitSeq(long sequence) {
        int longest = 1;
        int curr = 0, prev = 0;

        for(int i = 0; i < Long.SIZE; i++) {
            if((sequence & 1) == 1)
                curr++;
            else {
                prev = ((sequence & 2) == 0)? 0 : curr; 
                curr = 0;
            }
            longest = Math.max(longest, curr + prev + 1);
            sequence >>>= 1;
        }
        return Math.min(longest, Long.SIZE);
    }

    static int swapNibbles(int input) {
        input &= 0xff; // taking lowest 8 bits alone
        return ((input & 0x0f) << 4) | ((input & 0xf0) >>> 4);
    }

    static int leaders(int[] array) {
        int leaders = 0, max = Integer.MIN_VALUE;

        for(int i = array.length - 1; i >= 0; i--) 
            if(array[i] > max) {
                leaders++;
                max = array[i];
            }
        return leaders;
    }

    static int majorityElement(int[] array) {
        int candidate = array[0], votes = 0;

        for(int element: array) {            
            if(votes == 0) 
                candidate = element;

            if(element == candidate)
                votes++;
            else 
                votes--;
        }
        int count = 0;
        for(int element: array) 
            if(element == candidate) count++;

        return (count > array.length / 2)? candidate : 0;
    }

    static int maxEquillibriumSum(int[] array) {
        long right = 0;
        for(int element: array)
            right += element;

        long max = Long.MIN_VALUE, left = 0;
        for(int i = 0; i < array.length; i++) {
            right -= array[i];

            if(left == right) // doesn't include centre
                max = Math.max(max, left);

            left += array[i];
        }
        return (int) ((max == Long.MIN_VALUE)? 0 : max);
    }    

    static long maxProductSubarray(int[] array) {
        long maxValHere = array[0];
        long minValHere = array[0];
        long result = array[0];

        for(int i = 1; i < array.length; i++) {
            int value = array[i];
            long product1 = value * maxValHere;
            long product2 = value * minValHere;

            maxValHere = Math.max(value,
                         Math.max(product1, product2));
            minValHere = Math.min(value,
                         Math.min(product1, product2));
            result = Math.max(result, maxValHere);
        }
        return result;
    }

    // static void rotationBlockSwap() {}

    static int maxHourglass(int[][] matrix) {
        if(matrix.length < 3 || matrix[0].length < 3)
            return 0;

        int max = Integer.MIN_VALUE;
        for(int i = 0; i <= matrix.length - 3; i++) 
            for(int j = 0; j <= matrix[0].length - 3; j++) {
                int sum = matrix[i][j] + matrix[i][j + 1] + matrix[i][j + 2] +
                          matrix[i + 1][j + 1] + 
                          matrix[i + 2][j] + matrix[i + 2][j + 1] + matrix[i + 2][j + 2];
                max = Math.max(max, sum);
            }
        return max;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter a number: ");
        long num = in.nextLong();
        System.out.println("longestFlipBitSeq: "
                + longestFlipBitSeq(num));
        System.out.println("swapNibbles: "
                + swapNibbles((int) num));


        System.out.println("\nEnter array size: ");
        int size = in.nextInt();
        int[] array = new int[size];

        System.out.println("Enter array values: ");
        for(int i = 0; i < size; i++)
            array[i] = in.nextInt();
        
        System.out.println("leaders: "
                + leaders(array));
        System.out.println("majorityElement: "
                + majorityElement(array));
        System.out.println("maxEquillibriumSum: "
                + maxEquillibriumSum(array));
        System.out.println("maxProductSubarray: "
                + maxProductSubarray(array));


        System.out.println("\nEnter matrix size: ");
        int r = in.nextInt(), c = in.nextInt();
        int[][] matrix = new int[r][c];

        System.out.println("Enter array values: ");
        for(int i = 0; i < r * c; i++)
            matrix[i / c][i % c] = in.nextInt();

        System.out.println("maxHourglass: "
                + maxHourglass(matrix));

        in.close();
    }
}
