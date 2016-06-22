
public class Palindrome {
	private String targetStr;
	private boolean IsPalindrome;
	Palindrome(String str){
		targetStr = str;
		IsPalindrome = CheckPalindrome();
	}
	private boolean CheckPalindrome(){
		return isPalindrome(targetStr);
	}
	public boolean isPalindrome(){
		return IsPalindrome;
	}
	public String getString(){
		return targetStr;
	}
	public static boolean isPalindrome(String str){
		boolean flag = true;
		int size = str.length();
		int i = 0;
		int j = size -1;
		//System.out.println(size);
		while(i<j){
			while( i < j && !Character.isLetterOrDigit(str.charAt(i)))
				i++;
			while( j > i && !Character.isLetterOrDigit(str.charAt(j)))
				j--;
			if( Character.toUpperCase(str.charAt(i)) != Character.toUpperCase(str.charAt(j))){
				//System.out.println("i: " +i + ", j: "+j);
				flag = false;
				break;
			}
			i++;
			j--;
		}
		return flag;
	}
}
