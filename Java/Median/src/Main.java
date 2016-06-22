
public class Main {
	public static void main(String[] args){
		
	}
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size1 = nums1.length;
        int size2 = nums2.length;
        boolean cur1 = true;
        boolean cur2 = true;
        int med1;
        int med2;
        if(size1 != 0 ){
        	if(size1%2==0){
        		med1 = (nums1[size1/2-1]+nums1[size1/2])/2;
        		cur1 = false;
        	}
        	else{
        		med1 = nums1[size1/2];
        		cur1 = true;
        	}
        }
        else{
        	med1 = Integer.MIN_VALUE;
        }
        if(size2 != 0 ){
        	if(size2%2==0){
        		med2 = (nums2[size2/2-1]+nums2[size2/2])/2;
        		cur2 = false;
        	}
        	else{
        		med2 = nums2[size2/2];
        		cur2 = true;
        	}
        }
        else{
        	med2 = Integer.MIN_VALUE;
        }
        if( size1 == 0){
        	return med2;
        }
        if(size2 == 0){
        	return med1;
        }
        if( med1 == med2){
        	return (double)med1;
        }
        
        if(med1>med2){
        	return findMedianSortedArrays(nums2,nums1);
        }
        double Med = nums1[size1/2];
        int midPos = size1/2;
        
        int startPos1;
        int startPos2;
        if(cur2){
        	startPos2 = size2/2;
        }
        else{
        	startPos2 = size2/2 - 1;
        }
        
        return Med;
    }
}
