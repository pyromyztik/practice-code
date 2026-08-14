import java.io.*;
import java.util.*;

public class scratchPad {
    public static void main(String[] args) throws IOException {
        try (Scanner in = new Scanner(System.in)) {
            int a = 3, b = 4;
            System.out.println( a + b );
            System.out.println( "3" + "4" );
            System.out.println( "" + a + b );
            System.out.println( 3 + 4 + a + " " + b + a );
            System.out.println( "Result: " + a + b );
            System.out.println( "Result: " + ( a + b ) );
            System.out.println(args[0] + args[1]);

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in)); 
            String str1 = reader.readLine();
            String str2 = System.console().readLine(); 
            System.out.println(str1 + " " + str2);

            in.next();
        }
    }
}