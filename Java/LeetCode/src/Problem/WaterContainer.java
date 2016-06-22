package Problem;

import java.util.TreeMap;

public class WaterContainer {
	public int NaivemaxArea(int[] height) {
        int size = height.length;
        int maxArea = 0 ; 
        for( int i = 0 ; i < size ; i++){
        	for( int j = i+1 ; j < size; j++ ){
        		int length = Math.min(height[i], height[j]);
        		int width = j - i;
        		int area = length * width;
        		if( maxArea < area){
        			maxArea = area;
        		}
        	}
        }
        return maxArea;
    }
	public int maxArea(int[] height) {
        int size = height.length;
        int hi = size - 1;
        int lo = 0;
        int maxArea = 0;
        while ( lo < hi){
        	int area = Math.min(height[hi], height[lo]) * ( hi - lo);
        	if( area > maxArea ){
        		maxArea = area;
        	}
        	if( height[lo] < height[hi]){
        		lo++;
        	}
        	else{
        		hi--;
        	}
        }
        return maxArea;
    }
}
