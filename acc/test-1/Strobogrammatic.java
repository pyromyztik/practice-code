import java.util.*;

class Strobogrammatic {
    static boolean isStrobogrammatic(String input) {
        Map<Character, Character> flip = new HashMap<>();
        flip.put('6', '9');
        flip.put('9', '6');
        flip.put('8', '8');
        flip.put('1', '1');
        flip.put('0', '0');

        int i = 0, j = input.length() - 1;
        while(i <= j) {
            if(!flip.containsKey(input.charAt(i))) 
                return false;
            if(flip.get(input.charAt(i)) != input.charAt(j))
                return false;
            i++; j--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.print(isStrobogrammatic(args[0]));
    }
}
