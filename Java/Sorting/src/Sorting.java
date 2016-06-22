import java.util.Arrays;


public class Sorting {
	int activeSort = 0;
	public void DoActiveSort(int[] array){
		switch(activeSort){
		case 1:
			BubbleSort(array);
			break;
		case 2:
			InsertionSort(array);
			break;
		case 3:
			ShellSort(array);
			break;
		case 4:
			SelectionSort(array);
			break;
		case 5:
			MergeSort(array);
			break;
		case 6:
			QuickSort(array);
			break;
		case 7:
			HeapSort(array);
			break;
		case 8:
			CocktailSort(array);
			break;
		case 9:
			CoutingSort(array);
			break;
		}
	}
	public void swap(int[] array, int a, int b){
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	
	public void CoutingSort(int[] array){
		int range = 40;
		int[] countingArray = new int[range];
		//System.out.println(Arrays.toString(countingArray));
		int size = array.length;
		for( int i = 0 ; i < size;i++ ){
			countingArray[array[i]]++;
		}
		for( int i = 1 ; i < range-1;i++){
			countingArray[i]+=countingArray[i-1];
		}
		//System.out.println(Arrays.toString(countingArray));
		int[] temp = new int[size];
		for( int i = size-1 ; i >=0 ;i--){
			temp[--countingArray[array[i]]]= array[i];
			//countingArray[array[i]]--;
			//System.out.println(Arrays.toString(temp));
		}
		for( int i = 0 ; i < size;i++){
			array[i] = temp[i];
		}
	}
	
	public void QuickSort(int[] array){
		System.out.println("In QuickSort");
		int size = array.length;
		DoQuickSort(array,0,size-1);
	}
	private void DoQuickSort(int[] array, int low, int high){
		if( low < high){
			int partition = GetPartition(array, low, high);
			DoQuickSort(array,low,partition-1);
			DoQuickSort(array,partition+1,high);
		}
	}
	private int GetPartition(int[] array, int low, int high){
		int pivot = array[high];
		int left = low;
		int right = high -1;
		while( left < right){
			while( left < right && array[left] < pivot)
				left++;
			while( right > left && array[right] > pivot){
				right--;
			}
			swap(array,left,right);
		}
		swap(array,right,high);
		return right;
	}
	
	public void BubbleSort(int[] array){
		System.out.println("In BubbleSort");
		int size = array.length;
		boolean swapped = false;
		for( int i = 0 ; i < size - 1 ; i++ ){
			swapped = false;
			for( int j = 0 ; j< size - 1 - i; j++){
				if( array[j] > array[j+1]){
					swap(array,j,j+1);
					swapped = true;
				}
			}
			if(!swapped){
				break;
			}
		}
	}

	public void InsertionSort(int[] array){
		System.out.println("In Insertion");
		int size = array.length;
		for( int i = 1; i < size - 1; i++){
			int key = array[i];
			int j = i-1;
			while( j >=0 && array[j] > key){
				array[j+1] = array[j];
				j--;
			}
			array[++j] = key;
		}
	}

	public void CocktailSort(int[] array){
		System.out.println("In Cocktail Sort");
		int size = array.length;
		int leftPos = 0 ; 
		int rightPos = size - 1;
		boolean swapped = false;
		while( leftPos < rightPos){
			swapped = false;
			for ( int i = leftPos; i < rightPos;i++){
				for( int j = leftPos; j< rightPos - i; j++){
					if( array[j] > array[j+1]){
						swap(array,j,j+1);
						swapped = true;
					}
				}
				if( !swapped )
					break;
			}
			rightPos--;
			for( int i = rightPos; i>=leftPos;i--){
				swapped = false;
				for( int j = rightPos; i>= leftPos -i;i--){
					if( array[j] < array [j-1]){
						swap(array,j,j-1);
						swapped = true;
					}
				}
				if(!swapped)
					break;
			}
			leftPos++;
		}
	}

	public void ShellSort(int[] array){
		System.out.println("In Shell Sort");
		int size = array.length;
		//boolean swapped = false;
		for( int gap = size/2; gap > 0 ; gap/=2){
			for( int i = gap ; i <size ; i++ ){
				int key = array[i];
				int j = i-gap;
				while( j>= 0 && array[j] > key){
					swap(array,j,j+gap);
					j-=gap;
				}
			}
			//System.out.println("tracing + gap: "+ gap + Arrays.toString(array));
		}
	}

	public void SelectionSort(int[] array){
		System.out.println("In Selection Sort");
		int size = array.length;
		for( int i = 0 ; i < size-1 ; i++){
			int targetPos = i;
			int min = array[targetPos];
			for( int j = i; j <size;j++ ){
				if( array[j] < min){
					targetPos = j; 
					min = array[j];
				}
					
			}
			if( targetPos != i)
				swap(array,i,targetPos);
		}
	}
	
	public void HeapSort(int[] array){
		int size = array.length;
		int heapsize = size - 1;
		BuildMaxHip(array,heapsize);
		//System.out.println(Arrays.toString(array));
		for( int i = heapsize;i>0;i--){
			swap( array, 0, i);
			heapsize--;
			Heapify(array,0,heapsize);
			//System.out.println("after i: " + i + Arrays.toString(array));
		}
	}

	public void MergeSort(int[] array){
		int size = array.length;
		int[] temp = array.clone();
		TopDownSplit(array,temp,0,size-1);
	}
	
	private void TopDownSplit(int[] array, int[] temp, int iBegin, int iEnd){
		//System.out.println("begin: " + iBegin  + " end: " + iEnd);
		if( iEnd - iBegin > 0){
			int iMid = iBegin + (iEnd - iBegin)/2;
			TopDownSplit(array,temp,iBegin,iMid);
			TopDownSplit(array,temp,iMid+1,iEnd);
			BottomUpMerge(array,temp,iBegin,iMid,iEnd);
			
		}
	}
	
	private void CopyFromTemp(int[] array, int iBegin, int iEnd, int[] temp){
		for( int i = iBegin;i<=iEnd;i++){
			temp[i] = array[i];
		}
	}
	private void BottomUpMerge(int[] array, int[] temp, int iBegin, int iMid, int iEnd){
		int left = iBegin;
		int right = iMid+1;
		
		CopyFromTemp(array,iBegin,iEnd,temp);
		//System.out.println("ibegin: " + iBegin + " mid: "+ iMid + " end: "+ iEnd + " left: " + left + " right:" + right);
		for( int i = iBegin;i<=iEnd;i++){
			//System.out.println(Arrays.toString(temp));
			//System.out.println("i: " + i + " left: " + left + " right : " + right + " temp[l]: " + temp[left] + " r:"+temp[right]);
			if( left > iMid){
				array[i] = temp[right];
				right++;
			}
			else if( right > iEnd){
				array[i] = temp[left];
				left++;
			}
			else {
				if( temp[left] < temp[right]){
					array[i]= temp[left];
					left++;
				}
				else{
					array[i] = temp[right]; 
					//System.out.println("right: "+ right + " te"+ temp[right]);
					right++;
				}
			}	
			//System.out.println("i: " + i + " array[i]: "+ array[i]);
		}
		
	}
	
	private void BuildMaxHip(int[] array, int heapsize){
		for( int i = heapsize/2;i>=0;i--){
			Heapify(array,i,heapsize);
		}
	}
	private void Heapify(int[] array,int target, int heapsize){
		int left = 2 * target + 1;
		int right = 2 * target + 2;
		int largest = target;
		//System.out.println("heapsize: " + heapsize + " left: " + left + " right "+ right);
		if( left <= heapsize && array[left] > array[target]){
			largest = left;
		}
		if( right <= heapsize && array[right] > array[largest] ){
			largest = right;
		}
		//System.out.println("largest: " + largest + " target: " + target);
		if( largest != target){
			swap(array, largest, target);
			Heapify(array,largest,heapsize);
		}
	}
	
}
