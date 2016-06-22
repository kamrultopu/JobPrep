import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * Definition for singly-linked list.*/
 class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
 }
 

public class LeetCodeSolution {
	public static final boolean DEV_MODE = true;
	
	
	 public String removeDuplicateLetters(String s) {
        int size = s.length();
        //System.out.println(s);
        if( size == 0 )
            return "";
        HashMap<Character,Integer> hm = new HashMap<Character,Integer>();
        for( int i = 0 ; i < size;i++){
            char ch = s.charAt(i);
            if( !hm.containsKey(ch) ){
                hm.put(ch,0);
            }
        }
        
        int totaldistinct = hm.size();
        for(Character ch : hm.keySet()){
            HashSet<Character> hs = new HashSet<Character>();
            int count = 0;
            hs.add(ch);
            int firstPos = s.indexOf(Character.toString(ch));
            //System.out.println(firstPos);
            for( int i = firstPos+1;i < size;i++ ){
                if( !hs.contains(s.charAt(i)) ){
                	//System.out.println(ch + "   " + s.charAt(i));
                    count++;
                    hs.add(s.charAt(i));
                }
            }
            hm.put(ch,count);
            //System.out.println(hm.toString());
        }
        
        char ch = ' ';
        int max = -1;
        for(Character c: hm.keySet()){
            if( hm.get(c) > max ){
                max = hm.get(c);
                ch = c;
            }
        }
        //System.out.println(ch);
        StringBuilder sb = new StringBuilder();
        sb.append(ch);
        int pos = s.indexOf(ch);
        //System.out.println("before recursion: " + sb.toString());
        sb.append(removeDuplicateLetters(s.substring(pos+1)));
        //System.out.println("after recursion: " + sb.toString());
        return sb.toString();
	}
	
	public int climbStairs(int n) {
        HashMap<Integer,Integer> hmap = new HashMap<Integer,Integer>();
        hmap.put(1, 1);
        hmap.put(2, 2);
        for( int i = 3; i<=n ; i++){
        	hmap.put(i, hmap.get(i-1)+hmap.get(i-2));
        }
        return hmap.get(n);
    }
	 public boolean isValid(String s) {
	        int size = s.length();
	        if( size == 0 ){
	            return true;
	        }
	        HashMap<Character,Character> hmap = new HashMap<Character, Character>();
	        hmap.put(')','(');
	        hmap.put('}','{');
	        hmap.put(']','[');
	        Stack<Character> stack = new Stack<Character>();
	        boolean result = true;
	        for( int i = 0 ; i < size;i++){
	            char ch = s.charAt(i);
	            if( ch == '(' || ch == '{' || ch == '['){
	                stack.push(ch);
	            }
	            else{
	            	System.out.println(stack.peek());
	            	System.out.println(hmap.get(ch));
	                if( !stack.isEmpty() && stack.peek()== hmap.get(ch)){
	                    stack.pop();
	                }
	                else{
	                    result = false;
	                }
	            }
	        }
	        System.out.println(stack.toString());
	        if(!stack.isEmpty()){
	            result = false;
	        }
	        return result;
	    }
	
	public boolean IsAdditive(String str){
		boolean result = true;
		int size = str.length();
		ArrayList<Integer> nums = new ArrayList<Integer>();
		int tempSize = 1;
		int currentPos = 0;
		boolean validThree = false;
		for(  ; tempSize < size/2; tempSize++){
			int firstVal = Integer.parseInt(str.substring(0, tempSize));
			System.out.println(firstVal);
			for( int second = 1; second <= size-2*tempSize;second++){
				int secondVal = Integer.parseInt(str.substring(tempSize,tempSize+second));
				int thirdVal = firstVal + secondVal ;
				String target = Integer.toString(thirdVal);
				int targetsize = target.length();
				if((tempSize + second + targetsize) > size ){
					continue;
				}
				
				String check =  str.substring(tempSize+second, tempSize+second+targetsize);
				System.out.println(check);
				if( check.equals(target)){
					nums.add(firstVal);
					nums.add(secondVal);
					nums.add(thirdVal);
					currentPos = tempSize+second+targetsize+1;	
					validThree = true;
					break;
				}	
			} 
			//System.out.println(validThree);
			if( validThree )
				break;
		}
		
		if( nums.size() != 0 ){
			//System.out.println("entered");
			int i = currentPos - 1;
			int lastNumber = 2;
			while( i < size ){
				int prev = nums.get(lastNumber);
				int prevprev = nums.get(lastNumber-1);
				int target = prev + prevprev;
				String targetString = Integer.toString(target);
				int targetsize = targetString.length();
				//System.out.println(targetsize);
				//System.out.println(currentPos);
				String check = str.substring(i,i+targetsize);
				//System.out.println("check : "+ check + " targetString " + target);
				if(  ! check.equals(targetString)){
					result = false;
					break;
				} 
				else{
					nums.add(target);
				}
				i+=targetsize;
				lastNumber++;
			}
		}
		else {
			result = false;
		}
		return result;
	}

	
	
	public int coinChange(int[] coins, int amount) {
        HashMap<Integer,Integer> hmap = new HashMap<Integer,Integer>();
        hmap.put(0, 0);
        for( int i = 1 ; i <=amount; i++ ){
        	for( int j = 0; j< coins.length;j++){
        		int coin = coins[j];
        		//System.out.println("coin: " + coin);
        		int req = -1;
        		int need = i - coin;
        		while(need>=0){
        			
        			//System.out.println("hi : " + need + " i: "+ i + " coin: "+ coin);
        			if( hmap.containsKey(need)){
            			req = hmap.get(need)+1;
            			//System.out.println("req" + req);
            			break;
            		}
        			else{
        				if( need >=0 )
        					req++;
        			}
        			//System.out.println("req: " + req);
        			need = need - coin;
        		}
        		
        		//System.out.println("req" + req);
        		if( hmap.containsKey(i) && hmap.get(i)>0){
        			
        				
        			hmap.put(i, Math.min(req, hmap.get(i)));
        		}
        		else{
        			//System.out.println("i: " + i + "req: " + req);
        			hmap.put(i, req);
        		}
        		//System.out.println(hmap.toString());
        	}
        }
        int res = hmap.get(amount);
        if( res == 0 && amount !=0)
        	res = -1;
        return res;
    }
	
	
	
	public void moveZeroes(int[] nums) {
	       
	}
	
	public void maxHeap(int[] nums){
		int size = nums.length;
		int k = size/2;
		for( int i = size/2;i>=0;i--){
			
			if(nums[k]==0 && size < (2*k+1)){
				
			}
			Heapify(nums,i);
		}
	}
	public void Heapify(int[] nums, int pos){
		int size = nums.length;
		int left = 2 * pos + 1;
		int right = 2 * pos + 2;
		int largest = pos;
		if( left < size && nums[left] > nums[pos]){
			largest = left;
		}
		if( right < size && nums[right] > nums[largest]){
			largest = right;
		}
		if( largest != pos){
			int temp = nums[largest];
			nums[largest] = nums[pos];
			nums[pos] = temp;
			Heapify(nums,pos);
		}
	}
	
	public int bulbSwitch(int n) {
        BitSet bs = new BitSet(n);
        //System.out.println(bs.toString());
        bs.flip(0, n);
        //System.out.println(bs.toString());
        for( int i = 2 ; i <= n ; i++){
        	int temp = i;
        	int k = 2;
        	while( temp <= n){
        		bs.flip(temp-1);
        		temp = temp*k;
        		k++;
        	}
        	//System.out.println(bs.toString());
        }
        return bs.cardinality();
    }
	
	
	public int[] twoSum(int[] nums,int target){
		int size = nums.length;
		int[] result ={-1,-1};
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		for( int i = 0 ; i < size; i++){
			ArrayList<Integer> tempList;
			if( map.containsKey(nums[i])){
				tempList = map.get(nums[i]);
				tempList.add(i+1);
			}
			else{
				tempList = new ArrayList<Integer>();
				tempList.add(i+1);
				map.put(nums[i], tempList);
			}
		}
		if(DEV_MODE){
			for( Integer i:map.keySet()){
				System.out.println(" key: " + i + ", Arrays: "+ map.get(i).toString());
			}
		}
		for( int i = 0; i < size; i++){
			int needVal = target - nums[i];
			if( map.containsKey(needVal)){
				ArrayList<Integer> tempList = map.get(needVal);
				if( tempList.get(0) == (i+1) && tempList.size()<2){
					continue;
				}
				result[0] = i + 1 ;
				result[1] = tempList.get(tempList.size()-1);
				break;
			}
		}
		return result;
	}
	public int[] sortedTwoSum(int[] nums, int target){
		int[] result = {-1,-1};
		int size = nums.length;
		for( int i = 0 ; i< size ; i++){
			int needVal = target - nums[i];
			int pos = Searching.BinarySearch(nums, needVal);
			if( pos > -1 && pos != i){
				result[0] = i+1;
				result[1] = pos + 1;
				break;
			}
		}
		return result;
	}
	public int[] twoPointerTwoSum(int[] nums, int target){
		int[] result = {-1,-1};
		int size = nums.length;
		int hi = size - 1; 
		int lo = 0;
		while(lo < hi){
			int sum = nums[lo] + nums[hi];
			if( sum == target){
				result[0] = lo + 1;
				result[1] = hi + 1;
				break;
			}
			else if( sum > target ){
				hi--;
			}
			else {
				lo++;
			}
		}
		return result;
	}
	
	
	public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null || head.next.next == null)
            return head;
        ListNode oddHead = head;
        ListNode evenHead = head.next;
        ListNode rootOdd = oddHead;
        ListNode rootEven = evenHead;
        ListNode currentNode = head.next.next;
        int k = 1;
        while(currentNode != null ){
        	//System.out.println("current " + currentNode.val + " k: "+ k);
            if( k%2 == 0){
            	evenHead.next = currentNode;
            }
            else{
            	oddHead.next = currentNode;
            }
            currentNode = currentNode.next;
            if( k%2 == 0){
            	evenHead = evenHead.next;
                
            }
            else{
            	oddHead = oddHead.next;
                //System.out.println("even" + evenHead.val);
            }
            k++;
        }
        //System.out.println("odd: " + oddHead.val);
        //System.out.println("even: " + evenHead.val);
        oddHead.next = rootEven;
        evenHead.next = null;
        return rootOdd;
    }

	public int hammingWeight(int n) {
        int hammingCount = 0;
        for( int i = 0 ; i < 32 ;i++){
            if( ((1<<i) & n) != 0){
                hammingCount++;
            }
        }
        return hammingCount;
    }
	
	public int rangeBitwiseAnd(int m, int n) {
		 if( m == n)
	            return m;
		 int result = 0;
		 while((m>0)&&(n>0)){
			 int a = Integer.highestOneBit(m);
		     int b = Integer.highestOneBit(n);
		     if( a!=b){
		    	 return result;
		     }
		     result+=a;
		     m = m-a;
		     n= n-a;
		 }
		 return result;
    }
	
	public int reverseBits(int n) {
        int res = 0;
        for( int i = 0 ; i < 32; i++){
            res = (res << 1) + (n&1);
            n = n>>1;
            System.out.println(res + " n: " + n);
        }
        return res;
    }
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size1 = nums1.length;
        int size2 = nums2.length;
        double median = 0;
        double med1 = getMedian(nums1);
        double  med2 = getMedian(nums2);
        if( med1 == med2){
        	median = med1;
        }
        else if (med1 > med2){
        	int j, k;
        	j = size1/2 - 1;
        	k = size2/2 - 1;
        	int shift = 0 ; 
        	int inbalanceCount = 0;
        	for( int i = size2/2;i < size2; i++){
        		if( nums2[i] > med1){
        			break;
        		}
        		inbalanceCount++;
        		k++;
        	}
        	shift = inbalanceCount/2;
        	int flag = 1;
        	for( int i = 0 ; i < shift ; i++){
        		if( nums2[k] > nums1[j]){
        			k--;
        			flag = 2;
        		}
        		else{
        			j--;
        			flag = 1;
        		}
        	}
        	if(flag == 1){
        		median = nums1[j];
        	}
        	else{
        		median = nums2[k];
        	}
        }
        else{
        	
        }
        return median;
    }
	private double getMedian(int[] nums){
		int size = nums.length;
		int mid = size/2;
		int res = 0;
		if( size%2 == 0 ){
			res = (nums[mid]+nums[mid+1])/2;
		}
		else {
			res = nums[mid];
		}
		return res;
	}
	
	public static boolean validateNumber(String str){
		str = str.trim();
		int size = str.length();
		if( size == 0)
		    return false;
		boolean result = true;
		boolean expoFlag = true;
		boolean initFlag = true;
		boolean pointFlag = false;
		boolean isBase = false;
		boolean isPower = false;
		for( int i = 0 ; i < size ; i++){
			//System.out.println("i" + str.charAt(i));
			if( !Character.isDigit(str.charAt(i) )){
				if ( str.charAt(i) == 'e' || str.charAt(i) == 'E'){
					if( expoFlag || (i == size-1) || !isBase) {
						result = false;
						break;
					}
					else {
						expoFlag = true;
						initFlag = true; 
						isBase = false;
					}
				}
				else if ( str.charAt(i) == '.' ){
					//System.out.println("i");
				    if( pointFlag || (size == 1)|| (i==size-1 && expoFlag && !isBase && !isPower)){
				        if( i < size-1){
				        	
				        }
				    	result = false;
				        break;
				    }
				    else{
				        pointFlag = true;
				    }
				}
				else if( !initFlag  ){
					result = false;
					break;
				}
				else if ( str.charAt(i) == '+' || str.charAt(i) == '-'){
					initFlag = false;
				} 
				else {
					result = false;
					break;
				}
			}
			else {
				isBase = true;
				if(pointFlag){
					isPower = true;
				}
			}   
			initFlag = false; 
			if(i == 0)
				expoFlag = false;
		}
		return result;
	}
	public static int lengthoflongestsubstring(String s){
		HashMap<Character,Integer> hmap = new HashMap<Character,Integer>();
		int size = s.length();
		int longest = 0;
		int start = 0;
		int end = 0;
		int length = 0;
		//System.out.println(size);
		for( int i = 0 ; i < size ; i++){
			char ch = s.charAt(i);
			int pos = -1;
			if(hmap.containsKey(ch ))
				pos = hmap.get(ch);
			if(pos >= start){
				end = i - 1;
				length = end - start + 1;
				if( length > longest)
					longest = length;
				start = hmap.get(ch) + 1;
				hmap.put(ch, i);
				//System.out.println(hmap.toString());
			}
			else{
				hmap.put(ch, i);
			}
			if( i == size-1 ){
				end = i;
				//System.out.println(hmap.toString() + "start : " + start + "end "+ end);
				length = end - start + 1;
				
				if( length > longest)
					longest = length;
			}
		}
		return longest;
	}

	public static int LongestSubStringWithTwodisChar(String str){
		int size = str.length();
		int longest = -1;
		int start = 0;
		int newStart = -1;
		int end = 0;
		int disChar = 0;
		for(int i = 1 ; i < size; i++){
			if(str.charAt(i) == str.charAt(i-1))
				continue;
			if( newStart >=0 && str.charAt(newStart) != str.charAt(i) ){
				longest = Math.max(i-start, longest);
				start = newStart + 1;
			}
			newStart = i-1;
		}
		return longest>(size-start)?longest:(size-start);
	}
	public static int addDigits(int num) {
		int sum = 0;
		while( num > 0 ){
			sum = sum + num%10;
			num = num/10;
	    }
		System.out.println(sum);
		if( sum > 9 ){
			sum = addDigits(sum);
	    }
		return sum;
	}
	
	static HashSet<Integer> hs = new HashSet<Integer>();
    public static int sumSquareDigit(int n){
		int sum = 0;
		while( n > 0 ){
			int temp = n % 10;
			sum = sum + temp* temp;
			n = n/10;
	    }
		return sum;
	}
	
	public static boolean isHappy(int n) {
        while(!hs.contains(n)){
        	if( n == 1){
        		return true;
        	}
        	else{
        		hs.add(n);
        		n = sumSquareDigit(n);
        		return isHappy(n);
        	}
        }
        return false;
    }
	
	public static int strStr(String haystack, String needle){
		KMP kp = new KMP();
		//kp.initialize(needle);
		return kp.KMPSearch(haystack, needle);
		
	}
	
	public static int atoi(String str){
		str = str.trim();
		int size = str.length();
		int retVal = 0 ;
		if( size == 0)
			return retVal;
		boolean flag = false;
		if(str.charAt(0)=='-')
			flag = true;
		//int flagPrev = Integer.MAX_VALUE;
		
		for( int i = 0 ; i < size;i++){
			if( !Character.isDigit(str.charAt(i)) ){
				if( i==0 && (str.charAt(i) == '+' || str.charAt(i) == '-'))
					continue;
				break; 
			}
			int charVal = str.charAt(i) - 48;
			int temp = 10 * retVal + charVal;
			//System.out.println(temp);
			if( retVal >214748363){
				if( (retVal == 214748364) && charVal < 8 ){
					retVal = temp;
				}
				else {
					if(flag)
						retVal = Integer.MIN_VALUE;
					else
						retVal = Integer.MAX_VALUE;
					flag = false;
				}
				
				break;
			}
			else{
				retVal = temp;
			}
		}
		if(flag) {
			retVal = retVal * -1;
		}
		return retVal;
	}
}
