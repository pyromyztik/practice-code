public class ToggleSwitch {
    public static void main(String[] args) {        
        int switches = Integer.parseInt(args[0]);
        boolean[] switchStates = new boolean[switches + 1];

        for(int i = 1; i <= switches; i++) 
            for(int j = i; j <= switches; j += i) 
                switchStates[j] = !switchStates[j];

        int open = 0, closed = 0;
        for(int i = 1; i <= switches; i++) {
            if(switchStates[i]) open++;               
            else                closed++;
        }
        System.out.println("Open: " + open 
                       + "\nClosed: "+ closed);
    }
}
