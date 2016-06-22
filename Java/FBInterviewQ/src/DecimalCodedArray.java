import java.util.ArrayList;
import java.util.Arrays;


public class DecimalCodedArray {
	public int[] PlusOne(int[] nums){
		int size = nums.length;
		int carry = 0;
		for( int i = size - 1 ; i>=0;i--){
			nums[i] = nums[i] + carry;
			if( i == size - 1 )
				nums[i] += 1;
			carry = nums[i]/10;
			nums[i] = nums[i]%10;
		}
		if( carry != 0 ){
			int[] newArray = new int[size + 1];
			newArray[0] = 1;
			return newArray;
		}
		return nums;
	}
	public int[] PlusOne(int[] nums,int base){
		int size = nums.length;
		int carry = 0;
		for( int i = size - 1 ; i>=0;i--){
			nums[i] = nums[i] + carry;
			if( i == size - 1 )
				nums[i] += 1;
			carry = nums[i]/base;
			nums[i] = nums[i]%base;
		}
		if( carry != 0 ){
			int[] newArray = new int[size + 1];
			newArray[0] = 1;
			return newArray;
		}
		return nums;
	}
	
	public int[] Plus(int[] nums, int add, int base){
		int size = nums.length;
		int carry = 0;
		for( int i = size - 1 ; i>=0;i--){
			nums[i] = nums[i] + carry + add%base;
			add = add/base;
			carry = nums[i]/base;
			nums[i] = nums[i]%base;
		}
		//System.out.println(Arrays.toString(nums) + " add: " + add + ", carry: " + carry);
		
		if( add != 0 || carry != 0){
			add += carry;
			int temp = add;
			ArrayList<Integer> arrList = new ArrayList<Integer>();
			while(temp!=0){
				arrList.add(temp%base);
				temp = temp/base;
			}
			int[] newArray = new int[size+arrList.size()];
			int i=0;
			int j = arrList.size() - 1;
			while(add!=0){
				newArray[i] = arrList.get(j--);
				add = add/10;
				i++;
			}
			//System.out.println(Arrays.toString(newArray));
			for( j = 0 ; j < size ; j++,i++){
				newArray[i] = nums[j];
			}
			return newArray;
		}
		
		return nums;
	}
	
}
