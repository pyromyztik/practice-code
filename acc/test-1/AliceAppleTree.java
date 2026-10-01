
public class AliceAppleTree {
    public static void main(String[] args) {
        int apples = Integer.parseInt(args[0]);

        int sum = 0, count = 0;
        while(sum < apples) {
            count++;
            sum += (12 * count*count);
        }
        System.out.print(8 * count);
    }
}
