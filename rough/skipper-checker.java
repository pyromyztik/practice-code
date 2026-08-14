import java.util.*;

class skipperChecker {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter att/tot: ");
        int att = in.nextInt(); 
        int tot = in.nextInt(); 
        System.out.println("Current %: " + (100 * ((double)att / tot)));

        while(true) {
            String choice;
            System.out.println("\nSkip/Go: "); 
            choice = in.next(); in.next();
            choice.toLowerCase(); 
            
            if(choice.equals("skip") || choice.equals("0")) {
                tot++;
                System.out.println("Current %: " + (100 * ((double)att / tot)));
            } else if(choice == "go" || choice == "1") {
                att++; tot++;
                System.out.println("Current %: " + (100 * ((double)att / tot)));
            } else if(choice == "q" || choice == "quit") {
                System.out.println("Quitting..."); break;
            } else 
                System.out.println("Invalid. Try again");
        }
        in.close();
    }
}