public class MajorityElement {
    public static int find(int[] values) {
        int candidate = 0;
        int count = 0;

        for (int value: values) {
            if (count == 0) candidate = value;
            count += value == candidate? 1 : -1;
        }
        count = 0;
        for (int value: values) 
            if (value == candidate) count++;

        if (count <= values.length / 2) 
            throw new IllegalArgumentException("No majority element");
        return candidate;
    }

    public static void main(String[] args) {
        int[] values = new int[args.length];
        for (int index = 0; index < args.length; index++) 
            values[index] = Integer.parseInt(args[index]);
        System.out.println(find(values));
    }
}