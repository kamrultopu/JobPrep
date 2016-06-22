package Problem;

public class ZigZagConversion {
	public String convert(String s, int numRows) {
		System.out.println(s);
        StringBuffer[] sbList = new StringBuffer[numRows];
        for( int i = 0 ; i < numRows ;i++){
        	sbList[i] = new StringBuffer();
        }
        
        boolean isDecreasing = false;
        int revIndex = 0;
        for( int k = 0 ; k < s.length();){
        	if(!isDecreasing){
        		for( int i = 0 ; i < numRows && k < s.length() ; i++){
        			sbList[i].append(s.charAt(k++));
        		}
        		isDecreasing = true;
        		revIndex = numRows - 2;
        	}
        	else{
        		if(revIndex > 0){
        			sbList[revIndex--].append(s.charAt(k++));
        		}
        		else{
        			isDecreasing = false;
        		}
        	}
        }
        StringBuilder sbResult = new StringBuilder();
        for( int i = 0 ; i < numRows; i++){
        	System.out.println(sbList[i].toString());
        	sbResult.append(sbList[i]);
        }
        return sbResult.toString();
    }
}
