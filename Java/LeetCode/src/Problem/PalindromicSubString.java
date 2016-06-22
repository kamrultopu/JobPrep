package Problem;

import java.util.Arrays;

public class PalindromicSubString {
	int[][] isPalindrome;
	public String longestPalindrome(String s) {
        int size = s.length();
        char[] charArray = new char[2*size];
        for( int i = 0,k=0 ; i < size; i++,k+=2 ){
        	charArray[k]= s.charAt(i);
        	charArray[k+1]='#';
        }
        int max = 0;
        int mid = 0;
        for( int i = 0 ; i < 2 * size - 1 ; i++){
        	int temp = expand(charArray,i,0);
        	if( temp > max){
        		mid = i;
        		max = temp;
        	}
        }
        int start = mid - max;
        int end = mid + max;
        start = (start+1)/2;
        end /= 2;
        return s.substring(start, end+1);
    }	
	public String longestPalindromeNaive(String s){
		int size = s.length();
		isPalindrome = new int[size][size];
		for( int i = 0 ; i < size; i++){
			Arrays.fill(isPalindrome[i], -1);
			isPalindrome[i][i] = 1;
			
		}
		for( int i = 0 ; i < size - 1 ; i++){
			if( s.charAt(i) == s.charAt(i+1)){
				isPalindrome[i][i+1] = 1;
			}
		}
		for( int i = 0 ; i < size ; i++){
			for( int j = i+1 ; j < size;j++){
				isPalindrome[i][j] = FillFlag(s,i,j);
			}
		}
		for( int i = 0 ; i < size; i++){
			System.out.println(Arrays.toString(isPalindrome[i]));
		}

		int start = 0;
		int end = 0;
		for( int i = size - 1 ; i >= 0 ; i-- ){
			for( int j = size -1 ; j >= i ; j--){
				if(isPalindrome[i][j]== 1){
					start = i;
					end = j;
					break;
				}
			}
		}
		return s.substring(start, end+1);
	}
	private int expand(char[] str, int mid, int length){
		if( (mid - length) < 0 || (mid+length) >= str.length){
			return length - 1;
		}
		if( mid <=0 )
			return 0;
		if( str[mid-length] == str[mid+length]){
			return expand(str,mid,length+1);
		}
		else{
			return length - 1;
		}
	}
	
	private int FillFlag(String s, int start, int end){
		if( isPalindrome[start][end] != -1){
			return isPalindrome[start][end];
		}
		if( end < start ) {
			return 0;
		}
		if ( end == start){
			return 1;
		}
		if( end == start+1){
			if( s.charAt(start) == s.charAt(end)){
				return 1;
			}
			else{
				return 0;
			}
		}
		else{
			if( s.charAt(start) == s.charAt(end)){
				return FillFlag(s,start+1,end-1);
			}
			else{
				return 0;
			}
		}
	}
}
