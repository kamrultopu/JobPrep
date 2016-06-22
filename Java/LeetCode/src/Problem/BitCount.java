package Problem;

import java.util.HashMap;

public class BitCount {
	public int[] getBitCount(int n){
		HashMap<Integer,Integer> hmap = new HashMap<Integer,Integer>();
		hmap.put(0, 0);
		hmap.put(1, 1);
		for( int i = 2; i <= n ; ){
			//System.out.println(Math.log(i));
			int logVal = (int) (Math.log(i)/Math.log(2));
			int total = (int) Math.pow(2, logVal);
			int kIndex = (int)Math.pow(2, logVal-1);
			//System.out.println(i + " total:" + total + " logVal:" + logVal);
			System.out.println("kIndex: " + kIndex);
			for( int j = 0 ; j < total/2 && i <= n;j++,i++){
				System.out.println("i: " + i + " kIndex: " + kIndex);
				hmap.put(i, hmap.get(kIndex++));
				System.out.println(hmap.toString());
			}
			kIndex = (int)Math.pow(2, logVal-1);
			for( int j = 0 ; j < total/2 && i <= n;j++,i++){
				System.out.println("i: " + i + " kIndex: " + kIndex);
				hmap.put(i, 1+ hmap.get(kIndex++));
				System.out.println(hmap.toString());
			}
		}
		
		int[] resultArray = new int[n+1];
		System.out.println("final" + hmap.toString());
		for( int i = 0 ; i <= n ;i++){
			resultArray[i] = hmap.get(i);
		}
		return resultArray;
	}
}
