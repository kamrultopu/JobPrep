import java.util.Arrays;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {30,6,2,1,8,12,35,22};
		Sorting srt = new Sorting();
		srt.activeSort = 9;
		System.out.println(Arrays.toString(nums));
		srt.DoActiveSort(nums);
		System.out.println(Arrays.toString(nums));
	}

}
