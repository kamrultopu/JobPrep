package Problem;

import java.util.HashMap;

public class LeetCodeSolution {
	public int lengthOfLongestSubstring(String s) {
        int start = 0 ;
        int end = 0;
        int length = 0;
        int maxSize = -1;
        System.out.println(s);
        HashMap<Character,Integer> hmap = new HashMap<Character,Integer>();
        for( int i = 0 ; i < s.length();i++){
        	
        	if( hmap.containsKey(s.charAt(i)) && (hmap.get(s.charAt(i)).intValue() >= start)){
        		end = i - 1;
        		length = end - start + 1;
        		if( length > maxSize){
        			maxSize = length;
        		}
        		System.out.println(hmap.get(s.charAt(i)).intValue());
        		start = hmap.get(s.charAt(i)).intValue() + 1;
        		System.out.println("start: " + start + "char: "+ s.charAt(start) +  "end"+end+"max " +maxSize);
        		hmap.put(s.charAt(i), i);
        	}
        	else{
        		hmap.put(s.charAt(i), i);
        	}
        }
        end = s.length()-1;
        length = end-start + 1;
        if( length > maxSize){
			maxSize = length;
		}
        //System.out.println("end: "+ end);
        return maxSize;
    }

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int size1 = nums1.length;
        int size2 = nums2.length;
        double median = 0;
        double med1 = getMedian(nums1);
        double  med2 = getMedian(nums2);
        int medPos1 = size1/2;
        int medPos2 = size2/2;
        System.out.println(med1 + " " + med2);
        if( med1 == med2){
        	median = med1;
        }
        else if (med1 > med2){
        	int shiftedCount = 1 ; 
        	int toShift = 0;
        	if( size2%2==0)
        		shiftedCount++;
        	for( int i = size2/2;i < size2; i++){
        		if( nums2[i] > med1){
        			break;
        		}
        		shiftedCount++;
        		medPos2++;
        	}
        	System.out.println("midpos2: " + medPos2 + " , shiftedCount: " + shiftedCount);
        	toShift = shiftedCount/2;
        	System.out.println("to shift: " + toShift);
        	boolean shiftFlag = false;
        	if( shiftedCount %2 != 0 )
        		shiftFlag = true;
        	//System.out.println(shiftFlag);
        	int flag = 0;
        	medPos2--;
        	medPos1--;
        	for( int i = 0 ; i < toShift ; i++){
        		//System.out.println(" nums2: " + nums2[medPos2] + " nums1: " + nums1[medPos1]);
        		if( nums2[medPos2] > nums1[medPos1]){
        			medPos2--;
        			flag = 2;
        		}
        		else{
        			medPos1--;
        			flag = 1;
        		}
        	}
        	//System.out.println(flag);
        	if(flag == 1){
        		median = nums1[medPos1+1];
        	}
        	else{
        		median = nums2[medPos2+1];
        	}
        	System.out.println(median);
        	if( shiftFlag){
        		if( nums2[medPos2] > nums1[medPos1]){
        			median = (median + nums2[medPos2])/2;
        		}
        		else{
        			median = (median + nums1[medPos1])/2;
        		}
        	}
        }
        else{
        	int shiftedCount = 1 ; 
        	int toShift = 0;
        	if( size1%2==0)
        		shiftedCount++;
        	for( int i = size1/2;i < size1; i++){
        		if( nums1[i] > med2){
        			break;
        		}
        		shiftedCount++;
        		medPos1++;
        	}
        	System.out.println("midpos2: " + medPos1 + " , shiftedCount: " + shiftedCount);
        	toShift = shiftedCount/2;
        	System.out.println("to shift: " + toShift);
        	boolean shiftFlag = false;
        	if( shiftedCount %2 != 0 )
        		shiftFlag = true;
        	//System.out.println(shiftFlag);
        	int flag = 0;
        	medPos2--;
        	medPos1--;
        	for( int i = 0 ; i < toShift ; i++){
        		//System.out.println(" nums2: " + nums2[medPos2] + " nums1: " + nums1[medPos1]);
        		if( nums2[medPos2] > nums1[medPos1]){
        			medPos2--;
        			flag = 2;
        		}
        		else{
        			medPos1--;
        			flag = 1;
        		}
        	}
        	//System.out.println(flag);
        	if(flag == 1){
        		median = nums1[medPos1+1];
        	}
        	else{
        		median = nums2[medPos2+1];
        	}
        	System.out.println(median);
        	if( shiftFlag){
        		if( nums2[medPos2] > nums1[medPos1]){
        			median = (median + nums2[medPos2])/2;
        		}
        		else{
        			median = (median + nums1[medPos1])/2;
        		}
        	}
        }
        return median;
	}
	
	public static double getMedian(int[] list){
		int size = list.length;
		int mid = size/2;
		int median = 0 ;
		if(size%2==0){
			median = (list[mid-1] + list[mid])/2;
		}
		else{
			median = list[mid];
		}
		return median;
	}
	
}
