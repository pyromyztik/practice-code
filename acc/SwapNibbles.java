public class SwapNibbles {
    public static int swap(int value) {
        value &= 0xFF;
        return ((value & 0x0F) << 4) | ((value & 0xF0) >>> 4);
    }

    public static void main(String[] args) {
        int value = Integer.parseInt(args[0]);
        System.out.println(swap(value));
    }
}