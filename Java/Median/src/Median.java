import java.util.Collections;
import java.util.PriorityQueue;


public class Median<T> {
	PriorityQueue<T> maxQ = new PriorityQueue<T>();
	PriorityQueue<T> minQ = new PriorityQueue<T>(1,Collections.reverseOrder());
	int count;
	Object[] median =  new Object[2];
	Median(){
		count = 0 ;
	}
	public void insert(T obj){
		
		if( count%2 == 0 ){
			if(median[0]!= null && (Integer)obj > (Integer)median[0]){
				maxQ.add(obj);
				T temp = maxQ.poll();
				minQ.add(temp);
			}
			else{
				minQ.add(obj);
			}
			count++;
		}
		else{
			if((Integer)obj > (Integer)median[0]){
				maxQ.add(obj);
			}
			else{
				minQ.add(obj);
				T temp = minQ.poll();
				maxQ.add(temp);
			}
			count++;
		}
		SetMedian();
	}
	private void SetMedian(){
		//System.out.println(count);
		if(count%2==0){
			median[0] = minQ.peek();
			median[1] = maxQ.peek();
		}
		else{
			median[0] = minQ.peek();
			median[1] = minQ.peek();
		}
		//System.out.println(median[0] + " " + median[1]);
	}
}
