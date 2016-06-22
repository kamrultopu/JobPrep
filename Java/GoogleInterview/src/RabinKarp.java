
public class RabinKarp {
	int prime;
	int hVal;
	int patSize;
	int alphaSize = 256;
	public void initialize(String str){
		patSize = str.length();
		hVal = 1;
		prime = 23;
		for(int i = 0 ; i < patSize - 1 ; i++){
			hVal = (hVal*alphaSize)%prime;
		}
		System.out.println(hVal);
	}
	public int getHash(String str){
		int size = str.length();
		int hash = 0;
		for(int i = 0 ; i < size;i++){
			hash = (hash*alphaSize + str.charAt(i))%prime;
		}
		return hash;
	}
	public int strStr(String haystack, String needle) {
		int index = -1;
        initialize(needle);
        int size = haystack.length();
        int patHash = getHash(needle);
        System.out.println("needle: " + patHash + " haysub: " + haystack.substring(0, patSize));
        int haysubHash = getHash(haystack.substring(0, patSize));
        for( int i = 0 ; i <= size - patSize;i++){
        	if( patHash == haysubHash){
        		if(traverse(haystack.substring(0, patSize),needle)){
        			index = i ;
        			break;
        		}
        	}
        	if ( i < size - patSize){
        		System.out.println(haysubHash);
        		haysubHash = (alphaSize * ( haysubHash - haystack.charAt(i)* hVal + haystack.charAt(i+patSize)) ) %prime;
        		System.out.println(haystack.charAt(i)* hVal);
        		System.out.println((int)haystack.charAt(i+patSize));
        		if( haysubHash < 0 ){
        			haysubHash = haysubHash + prime;
        		}
        	}
        }
        return index;
    }
	public boolean traverse(String str1, String str2){
		if(str1.length() != str2.length())
			return false;
		for( int i = 0 ; i < str1.length();i++){
			if( str1.charAt(i)!=str2.charAt(i))
				return false;
		}
		return true;
	}
}
