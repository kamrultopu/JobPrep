/**
 * 
 */
package learnJava;

import java.io.Serializable;
import java.util.Arrays;
import java.util.RandomAccess;

/**
 * @author misty
 *
 */
public class MyArray<T extends Comparable<T>> implements Serializable, RandomAccess, Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T[] list;
	private int length;
	private int total;
	
	@SuppressWarnings("unchecked")
	MyArray(){
		list = (T[]) new Comparable[10];
		length = 10;
		total = 0 ;
	}
	
	@SuppressWarnings("unchecked")
	MyArray(int n){
		list = (T[])new Object[n];
		length = n;
		total = 0 ;
	}
	
	public boolean update(int n, T val){
		boolean rtVal = false;
		if( n < list.length ){
			rtVal = true;
			list[n] = val;
		}
		return rtVal;
	}
	
	
	public boolean insert(int n, T val){
		
		boolean rtVal = false;
		if( n < total){
			for( int i = total ; i > n; i-- ){
				list[i] = list[i-1];
			}
			list[n]=val;
			total++;
			rtVal = true;
		}
		return rtVal;
	}
	
	public boolean push(T val){
		boolean rtVal = false;
		if( total < length){
			list[total] = val;
			total++;
			rtVal = true;
		}
		return rtVal;
	}
	
	public void Traverse(){
		for( int i = 0 ; i < total; i++ ){
			System.out.println(list[i]);
		}
	}
	
	public T get(int n){
		if( n < length )
			return list[n];
		else 
			return null;
	}
	
	public int HasValue(T val){
		int retVal = -1;
		for( int i = 0 ; i < total ; i++){
			if( val == list[i]){
				retVal = i;
				break;
			}
		}
		return retVal;
	}
	
	public boolean delete(int n){
		boolean rtVal = false;
		if( n < length ){
			for( int i = n ; i < total; i++ ){
				list[i] = list[i+1];
			}
			rtVal = true;
			total--;
		}
		return rtVal;
	}
	
	public void Sort(){
		for( int i = 0 ; i < total; i++){
			for(int j = i+1; j < total ; j++){
				if( list[j].compareTo(list[i]) < 0 ){
					T temp = list[i];
					list[i] = list[j];
					list[j] = temp;
				}
			}
		}
	}

	public static<T extends Comparable<T>> MyArray<T> Merge(MyArray<T> arr1, MyArray<T> arr2){
		MyArray<T> temp = new MyArray<T>(arr1.length+arr2.length);
		for( int i = 0 ; i < arr1.length ; i++){
			temp.push(arr1.get(i));
		}
		for( int i = 0 ; i < arr2.length; i++){
			temp.push(arr2.get(i));
		}
		return temp;
	}
	
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
