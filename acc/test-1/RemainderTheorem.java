import java.util.Scanner;

public class RemainderTheorem {
    // Brute Force - finds the smallest positive integer x such that:
    // x % divisors[i] == remainders[i] for all i.
    public static int findMinX(int[] divisors, int[] remainders, int size) {
        int x = 1;

        while(true) {
            int i;
            for(i = 0; i < size; i++) 
                if(x % divisors[i] != remainders[i]) 
                    break;

            // If all conditions are satisfied
            if(i == size) return x;
            x++;
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of conditions: ");
        int size = scanner.nextInt();

        int[] divisors = new int[size];
        int[] remainders = new int[size];

        System.out.println("Enter divisors (moduli):");
        for(int i = 0; i < size; i++) 
            divisors[i] = scanner.nextInt();

        System.out.println("Enter remainders:");
        for(int i = 0; i < size; i++) 
            remainders[i] = scanner.nextInt();

        int result = findMinX(divisors, remainders, size);
        System.out.println("Smallest x: " + result);

        scanner.close();
    }
}