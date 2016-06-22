package Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TwoSum {
	public int[] NaivegetResult(int[] nums, int target){
		int[] result = new int[2];
		Arrays.fill(result, -1);
		int size = nums.length;
		for( int i = 0 ; i < size ; i++ ){
			for( int j = i+1;j < size; j++){
				if( nums[i] + nums[j] == target ){
					result[0] = i;
					result[1] = j;
				}
			}
		}
		return result;
	}
	public int[] GetResult(int[] nums, int target) {
		int[] result = new int[2];
		Arrays.fill(result, -1);
        HashMap<Integer,ArrayList<Integer>> hmap = new HashMap<Integer,ArrayList<Integer>>();
        for( int i =0 ; i < nums.length;i++){
        	if( hmap.containsKey(nums[i])){
        		ArrayList<Integer> tempList = hmap.get(nums[i]);
        		tempList.add(i);
        		//hmap.put(nums[i], tempList);
        	}
        	else{
        		ArrayList<Integer> tempList = new ArrayList<Integer>();
        		tempList.add(i);
        		hmap.put(nums[i], tempList);
        	}
        }
        
        for( int i = 0 ; i < nums.length;i++){
        	int seek = target - nums[i];
        	if( seek == nums[i]){
        		if( (hmap.get(seek)).size()>=2){
        			result[0]=(hmap.get(seek)).get(0) ;
            		result[1]=(hmap.get(seek)).get(1) ;
        		}
        	}
        	else {
        		if( hmap.containsKey(seek)){
            		result[0]=i+1;
            		result[1]=(hmap.get(seek)).get(0) + 1;
            		break;
            	}
        	}
        }
        
        return result;
    }
	public List<List<Integer>> threeSum(int[] nums) {
        int size = nums.length;
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        
        for( int i = 0 ; i < size ; i++){
        	int target = nums[i] * -1;
        	int[] result = GetResult(nums,target);
        	if( result[0] != -1 ){
        		ArrayList<Integer> arraList = new ArrayList<Integer>();
        		arraList.add(nums[i]);
        		arraList.add(nums[result[0]]);
        		arraList.add(nums[result[1]]);
        	}		
        }
        return list;
    }
}

/*

public int[] GetResult(int[] nums, int target) {
		int[] result = new int[2];
		Arrays.fill(result, -1);
        HashMap<Integer,ArrayList<Integer>> hmap = new HashMap<Integer,ArrayList<Integer>>();
        for( int i =0 ; i < nums.length;i++){
        	if( hmap.containsKey(nums[i])){
        		ArrayList<Integer> tempList = hmap.get(nums[i]);
        		tempList.add(i);
        		//hmap.put(nums[i], tempList);
        	}
        	else{
        		ArrayList<Integer> tempList = new ArrayList<Integer>();
        		tempList.add(i);
        		hmap.put(nums[i], tempList);
        	}
        }
        
        for( int i = 0 ; i < nums.length;i++){
        	int seek = target - nums[i];
        	if( seek == nums[i]){
        		if( (hmap.get(seek)).size()>=2){
        			result[0]=(hmap.get(seek)).get(0) + 1;
            		result[1]=(hmap.get(seek)).get(1) + 1;
        		}
        	}
        	else {
        		if( hmap.containsKey(seek)){
            		result[0]=i+1;
            		result[1]=(hmap.get(seek)).get(0) + 1;
            		break;
            	}
        	}
        }
        
        return result;
    }



*/