package Problem;

import java.util.Arrays;

public class Number {
	public int countPrimes(int n) {
		//System.out.println("n: " + n);
        int[] isPrime = new int[n+1];
        Arrays.fill(isPrime,-1);
        if( n >= 2 )
            isPrime[2] = 1;
        if( n >= 3 )
            isPrime[3] = 1;
        for( int i = 2; i*i  < n || i < 10; i++){
            System.out.println(i);
            if(isPrime[i] != 0 ){
                for( int j = i*i; j < n; j+=i){
                    int index = j;
                    isPrime[index] = 0;
                }
            }
            if( isPrime[i] != 0 )
                isPrime[i] = 1;
            System.out.println(Arrays.toString(isPrime));
        }
        int count = 0;
        for( int i = 2 ; i < n; i++){
            if ( isPrime[i] == 1){
                count++;
            }
        }
        //System.out.println(Arrays.toString(isPrime));
        return count;
    }
}
