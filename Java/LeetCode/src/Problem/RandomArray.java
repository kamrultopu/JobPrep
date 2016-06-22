package Problem;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomArray {
	public static int[] getRandomArrayNoDuplicate(int n){
		int[] createdArray = new int[n];
		Set<Integer> mySet = new HashSet<Integer>();
		Random rnd = new Random(System.currentTimeMillis());
		for( int i = 0 ; i < n ; ){
			int temp = rnd.nextInt(10000);
			if(!mySet.contains(temp)){
				mySet.add(temp);
				createdArray[i]=temp;
				i++;
			}
		}
		return createdArray;
	}
	public static int[] getRandomArray(int n){
		int[] createdArray = new int[n];
		Random rnd = new Random(System.currentTimeMillis());
		for( int i = 0 ; i < n ; i++){
			createdArray[i] = rnd.nextInt(1000);
		}
		return createdArray;
	}
}
