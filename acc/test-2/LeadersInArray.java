import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeadersInArray {
    public static List<Integer> leaders(int[] values) {
        List<Integer> result = new ArrayList<>();
        int greatest = Integer.MIN_VALUE;
        for (int index = values.length - 1; index >= 0; index--) 
            if (values[index] >= greatest) {
                result.add(values[index]);
                greatest = values[index];
            }
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        int[] values = new int[args.length];
        for (int index = 0; index < args.length; index++) 
            values[index] = Integer.parseInt(args[index]);
        System.out.println(leaders(values));
    }
}