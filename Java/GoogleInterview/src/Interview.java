import java.util.Arrays;


public class Interview {
	
	public static void main(String[] args) {
		int[] test = { 5,7,24,8,13,33};
		CheckSome cs = new CheckSome();
		cs.BuildMaxHeap(test,test.length);
		System.out.println(Arrays.toString(test));
	
	}
	

}
