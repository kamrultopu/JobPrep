
public class MyString {
	public static int strStr(String haystack, String needle){
		int result = -1;
		int needleSize = needle.length();
		int haystackSize = haystack.length();
		if( haystackSize < needleSize)
			return result;
		
		for( int i = 0 ; i < haystackSize ;i++){
			result = i;
			if( haystack.charAt(i) == needle.charAt(0)){
				//System.out.println(i);
				boolean flag = true;
				int nextPos = i + 1;
				for( int j = 0; j < needleSize;j++){
					if( haystack.charAt(i) != needle.charAt(j)){
						//System.out.println("j: " + j);
						flag = false;
						result = -1;
						break;
					}
					if( i < haystackSize && haystack.charAt(i) == needle.charAt(0)){
						nextPos = i;
					}
					i++;
				}
				if( flag )
					break;
				//System.out.println("next: " + nextPos);
				i = nextPos;
			}
			else {
				result = -1;
			}
		}
		
		return result;
	}
}
