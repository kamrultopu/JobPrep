package Problem;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    @Override
	public String toString() {
		return "ListNode [val=" + val + ", next=" + next + "]";
	}
}

public class LinkedList {
	public ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode ForeRunner = head;
        ListNode runner = head;
        ListNode prev = null;
        int kIndex = 0;
        if( head == null || (head.next == null && n == 1)){
        	return null;
        }
        while( kIndex < n){
        	ForeRunner = ForeRunner.next;
        	kIndex++;
        }
        while(ForeRunner != null){
            prev = runner;
            runner = runner.next;
            ForeRunner = ForeRunner.next;
            
        }
        System.out.println("prev: " + prev);
        if( prev != null){
            if( runner != null)
                prev.next = runner.next;
            else
                prev.next = null;
        }
        else{
            return head.next;
        }
        return head;
    }
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		int carry = 0 ;
		ListNode root,prev ;
		ListNode result = null;
		prev = null;
		while( l1 != null || l2!= null || carry != 0 ){
			int val = carry;
			if( l1!= null){
				val+=l1.val;
			}
			if( l2 != null){
				val+= l2.val;
			}
			
			carry = val / 10;
			val = val%10;
			root = new ListNode(val);
			if( result == null){
				result = root;
			}
			else {
				prev.next = root;
			}
			l1 = l1.next;
			l2 = l2.next;
			prev = root;
			root = root.next;
		}
		
		return result;
    }
	public ListNode reverseList(ListNode head) {
        ListNode root = head;
        ListNode prev = null;
        if( head == null || head.next == null){
            return head;
        }
        
        while(root!=null){
        	System.out.println(prev);
            ListNode temp = root.next;
            System.out.println(temp);
            root.next = prev;
            System.out.println(root);
            prev = root;
            root = temp;
        }
        
        return prev;
    }
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode temp = null;
        if( l1 == null ){
        	return l2;
        }
        else if ( l2 == null ){
        	return l1;
        }
        else{
        	if( l1.val > l2.val){
        		return mergeTwoLists(l2,l1);
        	}
        	ListNode prev = l1;
        	ListNode retNode = l1;
        	boolean isSwitch = false;
        	int current = 1;
        	while( l1 != null && l2 != null ){
        		//System.out.println(l1 + " " + l2);
        		if( l1.val <= l2.val){
        			//System.out.println("in l1<=l2");
        			if ( current == 2){
        				isSwitch = true;
        				current = 1;
        			}
        			else{
        				isSwitch = false;
        			}
        			if(isSwitch){
        				prev.next = l1;
        			}
        			prev = l1;
        			l1 = l1.next;
        		}
        		else{
        			//System.out.println("in l1>l2");
        			if( current == 1 ){
        				isSwitch = true;
        				current = 2;
        			}
        			else{
        				isSwitch = false;
        			}
        			if( isSwitch){
        				prev.next = l2;
        			}
        			prev = l2;
    				l2 = l2.next;
        		}
        	}
        	//System.out.println("current: " + current);
        	if( l1 != null && current == 2){
        		prev.next = l1;
        	}
        	if( l2!= null && current == 1){
        		
        		prev.next = l2;
        		System.out.println(prev);
        	}
        	//System.out.println(retNode + " : ");
        	return retNode;
        }
    }
	public ListNode sortList(ListNode head) {
        return null;
    }
}
