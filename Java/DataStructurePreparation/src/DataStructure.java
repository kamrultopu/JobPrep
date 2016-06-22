import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;




public class DataStructure {

	public static void main(String[] args) {
		int[] list1 = {1};
		int[] list2 = { 2,3};
		int[] mergeList = {1,2,3};
		Arrays.sort(mergeList);
		System.out.println(mergeList.length);
		System.out.println(Arrays.toString(list1));
		System.out.println(Arrays.toString(list2));
		System.out.println(Arrays.toString(mergeList));
		System.out.println(findMedianSortedArrays(list1,list2));
	}
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		PriorityQueue<Integer> maxQ = new PriorityQueue(5,Collections.reverseOrder());
		PriorityQueue<Integer> minQ = new PriorityQueue();
		int size1 = nums1.length;
		int size2 = nums2.length;
		
		if( size1 == 0 ){
		    int mid = size2/2;
		    if( size2%2==0){
		        return (nums2[mid-1]+nums2[mid])/2.0;
		    }
		    else{
		        return nums2[mid];
		    }
		}
		else if(size2 == 0 ){
		    int mid = size1/2;
		    if( size1%2==0){
		        return (nums1[mid-1]+nums1[mid])/2.0;
		    }
		    else{
		        return nums1[mid];
		    }
		}
		else if(size1 == 0 && size2==0){
		    return -1;
		}
		
		for( int i = 0 ; i < size1 ;i++){
			if( i <= size1/2){
				maxQ.add(nums1[i]);
			}
			else{
				minQ.add(nums1[i]);
			}
		}
		//System.out.println(maxQ.toString()+ " head: " + maxQ.peek());
		//System.out.println(minQ.toString()+ " head: " + minQ.peek());
		int count1 = maxQ.size();
		int count2 = minQ.size();
		//System.out.println("count1: " + count1 + " , count2 " + count2);
		for( int i = 0 ; i < size2;i++){
		    if( count1 == 0 ){
		        maxQ.add(nums2[i]);
		        count1++;
		        continue;
		    }
		    if( count2==0){
		        minQ.add(nums2[i]);
		        count2++;
		        continue;
		    }
			if( nums2[i]<maxQ.peek()){
				maxQ.add(nums2[i]);
				count1++;
			}
			else if( nums2[i] > minQ.peek()){
				minQ.add(nums2[i]);
				count2++;
			}
			else{
				if(count1 > count2){
					minQ.add(nums2[i]);
					count2++;
				}
				else{
					maxQ.add(nums2[i]);
					count1++;
				}
			}
			//System.out.println("count1: " + count1 + " , count2 " + count2);
			if( Math.abs(count1 - count2) > 1 ){
				//System.out.println("hi");
				if(count1 > count2){
					int temp = maxQ.remove();
					minQ.add(temp);
					count1--;
					count2++;
				}
				else{
					int temp = minQ.remove();
					maxQ.add(temp);
					count1++;
					count2--;
				}
				//System.out.println("count1: " + count1 + " , count2 " + count2);
			}
		}
		System.out.println(maxQ.toString()+ " head: " + maxQ.peek());
		System.out.println(minQ.toString()+ " head: " + minQ.peek());
		double median = 0;
		if(count1 == count2){
			median = (maxQ.poll() + minQ.poll())/2.0;
		}
		else if(count1 > count2){
				median = maxQ.poll();
			}
		else{
			median = minQ.poll();
		}
        return median;
    }
}
