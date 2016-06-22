package Problem;

import java.util.HashMap;
import java.util.HashSet;

public class longestSubString {
	public int lengthOfLongestSubstring(String s) {
        int retVal = LinearApproach(s);
		return retVal;
    }
	private int NaiveApproach(String s){
		int size = s.length();
		int retVal = 1;
		StringBuilder sb = new StringBuilder();
		
		for( int i = 0 ; i < size ; i++){
			int startPos = i;
			for( int j = 2; j < size - i  ; j++ ){
				int endPos = i+j;
				
				if(checkUnique(s,startPos,endPos)){
					
					if( j > retVal){
						retVal = j;
						
					}
				}
				else{
					break;
				}
			}
		}
		return retVal;
	}
	private boolean checkUnique(String str, int start, int end){
		HashSet<Character> hset = new HashSet<Character>();
		boolean isUnique = true;
		for( int i = start ; i < end ; i++){
			//System.out.println(i + " char : " + str.charAt(i));
			if( hset.contains(str.charAt(i))){
				isUnique = false;
				break;
			}
			else{
				hset.add(str.charAt(i));
			}
		}
		return isUnique;
	}

	private int LinearApproach(String s){
		int size = s.length();
		HashMap<Character,Integer> hmap = new HashMap<Character,Integer>();
		int max = -1;
		int startPos = 0;
		for(int i = 0 ; i < size;i++){
			char ch = s.charAt(i);
			if( hmap.containsKey(ch)){
				int prevPos = hmap.get(ch);
				if( prevPos >= startPos){
					int length = i - startPos;
					if( length > max ){
						max = length;
					}
					startPos = prevPos + 1;
				}
			}
			hmap.put(ch, i);
		}
		if( size - startPos > max ){
		    max = size - startPos;
		}
		return max;
	}
	
}
