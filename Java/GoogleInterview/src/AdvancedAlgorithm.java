
public class AdvancedAlgorithm {
	static int alphSize = 256;
	static int hval= 1;
	static int prime = 311;
	private static int getHash(String str){
		int size = str.length();
		
		int hashval = 0 ;
		
		System.out.println(str);
		for(int i =0 ; i < size;i++){
			hashval = (hashval * alphSize + (int)str.charAt(i))%prime;
			//System.out.println(hashval);
		}
		return hashval;
	}
	public static int RabinKarp(String str, String pattern){
		int result = -1;
		int strSize = str.length();
		int patSize = pattern.length();
		for( int i = 0 ; i < patSize - 1 ; i++){
			hval = (hval * alphSize) % prime;
		}
		System.out.println(hval);
		int patHashVal = getHash(pattern);
		System.out.println(patHashVal);
		int strHashVal = getHash(str.substring(0,patSize));
		for(int i = 0 ; i <= strSize - patSize; i++){
			//System.out.println("pat: " + patHashVal + ", str : " + strHashVal);
			if( strHashVal == patHashVal){
				boolean flag = true;
				for (int j = 0; j < patSize; j++)
	            {
	                if (str.charAt(i+j) != pattern.charAt(j)){
	                	flag = false;
	                	break;
	                } 
	            }
				if(flag){
					result = i;
					return result;
				}
					
			}
			if( i < strSize - patSize){
				System.out.println(strHashVal);
				strHashVal = (alphSize*(strHashVal - (hval *str.charAt(i)) + str.charAt(i+patSize) ))% prime;
				strHashVal = Math.abs(strHashVal);
				System.out.println(strHashVal);
				System.out.println("hello" + (int) hval *str.charAt(i));
				System.out.println("hi "+(int)str.charAt(i+patSize));
			}
		}
		return result;
	}


}
