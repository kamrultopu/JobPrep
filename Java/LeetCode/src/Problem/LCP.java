package Problem;

public class LCP {
	public String longestCommonPrefix(String[] strs) {
		int size = strs.length;
        if( size == 0 ){
            return "";
        }
        if( size == 1){
            return strs[0];
        }
        System.out.println(size);
        StringBuilder sb = new StringBuilder();
        for( int i = 0;i < strs[0].length() ; i++){
        	boolean isBreak = false;
        	for( int j = 1 ; j < size  ; j++){
        		System.out.println("i: " + i + " j: " + j + " lenght" + strs[j].length());
        		if( strs[j].length() <= i  || strs[0].charAt(i) != strs[j].charAt(i)){
        			//System.out.println("i: " + i + " j: " + j);
        			System.out.println("break;");
        			isBreak = true;
        			break;
        		}
        		System.out.println("j : " + j);
        	}
        	if( isBreak ){
        		break;
        	}
        	else{
        	    sb.append(strs[0].charAt(i));
        	}
        }
        return sb.toString();
    }
}
