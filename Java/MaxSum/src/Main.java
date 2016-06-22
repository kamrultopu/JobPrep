import java.util.Arrays;
import java.util.Scanner;


class MaxSum{
	int[][] input;
	int[][] sumArray;
	int nDimension;
	int[][] tempArray;
	public void Begin(){
		int maxSum; 
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			maxSum = Integer.MIN_VALUE;
			nDimension = sc.nextInt();
			input = new int[nDimension][nDimension];
			sumArray = new int[nDimension][nDimension];
			tempArray = new int[nDimension][nDimension];
			for( int i = 0 ; i < nDimension ; i++){
				for( int j = 0 ; j < nDimension;j++){
					//System.out.println("i: " +i + "j"+j);
					input[i][j] = sc.nextInt();
				}
			}
			//System.out.println("hi");
			/*for( int i = 0 ; i < nDimension; i++)
				System.out.println(Arrays.toString(input[i]));
			System.out.println("start");*/
			for( int i = 0 ; i < nDimension ; i++){
				tempArray[i] = Arrays.copyOf(input[i], nDimension);
			}
			for( int i = 0 ; i < nDimension ; i++){
				for( int j = 0 ; j < nDimension ; j++){
					input[j] = Arrays.copyOf(tempArray[j], nDimension);
				}
				//System.out.println("int : " + i);
				for( int j = 0 ; j < i; j++){
					for( int k = 0; k < nDimension;k++){
						input[k][j]= 0;
					}
				}
				for( int j = 0 ; j < nDimension ; j++){
					sumArray[j] = Arrays.copyOf(input[j], nDimension);
				}
				int temp = CalculateSum(i,1);
				if( temp > maxSum){
					maxSum = temp;
				}
				for( int j = 0 ; j < nDimension ; j++){
					input[j] = Arrays.copyOf(tempArray[j], nDimension);
				}
				for( int j = 0 ; j < i; j++){
					for( int k = 0; k < nDimension;k++){
						input[j][k]= 0;
					}
				}
				for( int j = 0 ; j < nDimension ; j++){
					sumArray[j] = Arrays.copyOf(input[j], nDimension);
				}
				temp = CalculateSum(i,2);
				if( temp > maxSum){
					maxSum = temp;
				}
			}
			System.out.println(maxSum);
		}
	}
	public int CalculateSum(int startPos, int orientation){
		int max = Integer.MIN_VALUE;
		for( int i = 1 ; i < nDimension ; i++){
			sumArray[0][i] = sumArray[0][i-1] + sumArray[0][i];
			sumArray[i][0] = sumArray[i-1][0] + sumArray[i][0];
		}
		for( int i = 1 ; i < nDimension; i++){
			for( int j = 1; j< nDimension;j++){
				sumArray[i][j] += (sumArray[i-1][j] + sumArray[i][j-1] - sumArray[i-1][j-1]); 
			}
		}
		if( orientation == 1){
			for( int i = 0 ; i < nDimension; i++){
				for( int j = startPos ; j< nDimension;j++){
					if( sumArray[i][j] > max){
						max = sumArray[i][j];
					}
				}
			}
		}
		else{
			for( int i = startPos ; i < nDimension; i++){
				for( int j = 0 ; j< nDimension;j++){
					if( sumArray[i][j] > max){
						max = sumArray[i][j];
					}
				}
			}
		}
		
		return max;
	}
}

class Main {

	public static void main(String[] args) {
		MaxSum mS = new MaxSum();
		mS.Begin();
	}

}
