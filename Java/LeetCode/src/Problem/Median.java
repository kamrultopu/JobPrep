package Problem;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Median {
	PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(10,Collections.reverseOrder());
	double currentMedian = 0.0;
	public double getMedian(int[] nums){
		Arrays.sort(nums);
		System.out.println(Arrays.toString(nums));
		int size = nums.length;
		double med = 0;
		if( size%2 == 0){
			med = (nums[size/2-1] + nums[size/2])/2.0; 
		}
		else{
			med = nums[size/2];
		}
		return med;
	}
	public double getMedian(int num){
		System.out.println(num);
		int size1 = minHeap.size();
		int size2 = maxHeap.size();
		if(num < currentMedian){
			System.out.println("in max");
			if ( size1 < size2){
				minHeap.add(maxHeap.poll());
			}
			maxHeap.add(num);
			System.out.println(maxHeap.toString());
			System.out.println(minHeap.toString());
		}
		else{
			if ( size2 < size1){
				maxHeap.add(minHeap.poll());
			}
			minHeap.add(num);
			System.out.println(maxHeap.toString());
			System.out.println(minHeap.toString());
		}
		SetCurrentMedian();
		return currentMedian;
	}
	private void SetCurrentMedian() {
		int size1 = minHeap.size();
		int size2 = maxHeap.size();
		if( size1 == size2){
			currentMedian = (maxHeap.peek() + minHeap.peek())/2.0;
		}
		else if( size1 > size2){
			currentMedian =  minHeap.peek();
		}
		else{
			currentMedian = maxHeap.peek() ;
		}
		
	}

}
/*
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
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
*/