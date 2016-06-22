import java.util.Arrays;
import java.util.Random;

/**
 * 
 */

/**
 * @author mki
 *
 */
public class MyArray {
	int arraySize ;
	int[] list;
	MyArray(){
		arraySize = 100;
		list = new int[arraySize];
	}
	MyArray(int n){
		arraySize = n;
		list = new int[arraySize];
	}
	public void RandomList(int bound){
		Random rd = new Random();
		for(int i = 0; i< arraySize; i++){
			list[i] = rd.nextInt(bound);
		}
	}
	public void listAssign(int[] glist){
		for(int i = 0 ; i < arraySize; i++){
			list[i] = glist[i];
		}
	}
	public void Traverse(){
		for(int i:list){
			System.out.print(i+" ");
		}
		System.out.println("\n");
	}
	public void Sort(){
		Arrays.sort(list);
	}
	public int Search(int target){
		int[] tempList = Arrays.copyOf(list, arraySize);
		Arrays.sort(tempList);
		int pos = Arrays.binarySearch(tempList, target);
		return pos;
	}
	public boolean insert(int target, int pos){
		if(pos < 0 || pos > arraySize){
			return false;
		}
		int[] temporaryList = new int[arraySize];
		
		temporaryList = Arrays.copyOf(list, arraySize);
		arraySize++;
		list = null;
		list = new int[arraySize];
		int k = 0;
		for(int i:temporaryList){
			list[k] = i;
			k++;
		}
		//System.out.println("ArraySize: " + arraySize + " List Size: " + list.length);
		for(int i = arraySize - 1; i> pos ; i--){
			list[i] = list[i-1];
		}
		list[pos] = target;
		return true;
	}
	public boolean delete(int target){
		int pos = Search(target);
		if(pos < 0){
			return false;
		}
		else{
			if( pos == arraySize - 1){
				list[pos] = 0;
			}
			else{
				for(int i = pos;i<arraySize-1;i++){
					list[i] = list[i+1];
				}
			}
		}
		return true;
	}
	public static MyArray merge(MyArray obj1, MyArray obj2){
		int size1 = obj1.arraySize;
		int size2 = obj2.arraySize;
		int size = size1+size2;
		MyArray newArray = new MyArray(size);
		int pos = 0;
		for(int i:obj1.list){
			newArray.list[pos] = i;
			pos++;
		}
		for(int i: obj2.list){
			newArray.list[pos]=i;
			pos++;
		}
		return newArray;
	}
}
