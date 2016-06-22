import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
	public static void main(String[] args){
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list1.add(3);
		list1.add(11);
		list1.add(17);
		list1.add(25);
		list1.add(58);
		list1.add(73);
		list2.add(6);
		list2.add(18);
		list2.add(40);
		list2.add(47);
		System.out.println(new Interval().MeregeInterval(list1, list2));
	}
}
