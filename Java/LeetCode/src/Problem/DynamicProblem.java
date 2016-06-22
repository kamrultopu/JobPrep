package Problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DynamicProblem {
	public int DP(int n){
		int[] dp = new int[n+1];
		int i;
		dp[1] = 0;  // trivial case
		for( i = 2 ; i <= n ; i ++ )
		{
			dp[i] = 1 + dp[i-1];
			if(i%2==0) dp[i] = Math.min(dp[i] , 1+ dp[i/2] );
			if(i%3==0) dp[i] = Math.min( dp[i] , 1+ dp[i/3] );
		}
		return dp[n];
	}

	public ArrayList<ArrayList<Integer>> minCoinSum(ArrayList<Integer> list, int target){
		
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		HashMap<Integer,ArrayList<ArrayList<Integer>>> hmap = new HashMap<Integer,ArrayList<ArrayList<Integer>>>();
		ArrayList<Integer> tempZero = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rZero = new ArrayList<ArrayList<Integer>>();
		rZero.add(tempZero);
		hmap.put(0, rZero);
		for( int i = 1 ; i <= target ; i++ ){
			for( int j = 0 ; j < list.size();j++){
				int currentCoin = list.get(j);
				int requiredCoin = i - currentCoin;
				ArrayList<ArrayList<Integer>> temp;
				if( currentCoin <= i ){
					if( hmap.containsKey(requiredCoin)){
						if( hmap.containsKey(i)){
							temp = hmap.get(i);
						}
						else{
							temp = new ArrayList<ArrayList<Integer>>();
						}
						
						int count = hmap.get(requiredCoin).size();
						for( int k = 0 ; k < count; k++){
							ArrayList<Integer> tempResult = new ArrayList<Integer>();
							int cc = hmap.get(requiredCoin).get(k).size();
							for(int l = 0 ; l< cc; l++){
								tempResult.add(hmap.get(requiredCoin).get(k).get(l));
							}
							tempResult.add(currentCoin);
							temp.add(tempResult);
						}
						//System.out.println(temp.toString());
						hmap.put(i, temp);
					}
				}
			}
		}
		
		return hmap.get(target);
	}
}
