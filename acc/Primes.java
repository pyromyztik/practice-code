class Primes {
    static int numberOfCoprimesTill(int limit) {
        int result = limit;

        for(int p = 2; p*p <= limit; p++) {
            if(limit % p != 0) continue; // prime divisors

            while(limit % p == 0)        // remove prime factor
                limit /= p;
            result -= result / p;        // remove multiples
        }
        if(limit > 1) result -= result / limit;
        return result;
    }

    static void simpleSieve(int limit) {
        boolean[] primes = new boolean[limit + 1];
        for(int i = 2; i < primes.length; i++) 
            primes[i] = true;               // initialize all true

        for(int p = 2; p*p <= limit; p++) {
            if(primes[p] != true) continue; // find a prime number

            for(int i = p*p; i <= limit; i += p)
                primes[i] = false;          // sieve out multiples
        }        
        for(int i = 0; i <= limit; i++)     // print all primes 
            if(primes[i]) System.out.print(i + " ");
        System.out.println();
    }

    static void segmentedSieve(int lower, int higher) {
        boolean[] primes = new boolean[higher - lower];
        for(int i = 0; i < higher - lower; i++) 
            primes[i] = true;

        for(int p = 2; p*p <= higher; p++) {
            int smallestMultiple = (lower / p) * p;
            if(smallestMultiple < lower)
                smallestMultiple += p;

            for(int i = smallestMultiple; i < higher; i += p)
                primes[i - lower] = false;
        }        
        for(int i = lower; i < higher; i++)      // print all primes 
            if(primes[i - lower]) System.out.print(i + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Number of coprimes till 100: "
            + numberOfCoprimesTill(100));
        System.out.println("Simple Sieve: ");
            simpleSieve(50);
        System.out.println("Segmented Sieve: ");
            segmentedSieve(2, 50);
    }
}
