import java.util.Scanner;
import java.util.TreeSet;

class myString implements Comparable{
	String str;
	myString(String s){
		str = s;
	}
	@Override
	public int compareTo(Object arg0) {
		int size1 = str.length();
		String toCheck = ((myString)arg0).str;
		int size2 = toCheck.length();
		int i = 0 ; 
		while(i < size1 && i < size2){
			Character ch1 = str.charAt(i);
			Character ch2 = toCheck.charAt(i);
			if(ch1!=ch2){
				if(Character.toLowerCase(ch1) == Character.toLowerCase(ch2) ){
					return ch1-ch2;
				}
				else{
					return Character.toLowerCase(ch1) - Character.toLowerCase(ch2);
				}
			}
			i++;
		}
		
		return size1 - size2;
	}
	@Override
	public String toString() {
		return  str ;
	}
	
}

class Anagram{
	String strTarget;
	TreeSet<myString> anagramList = new TreeSet<myString>();
	Anagram(String str){
		strTarget = str;
		StringBuilder sb = new StringBuilder(str);
		createAnagram(sb,0,str.length()-1);
	}
	private void createAnagram(StringBuilder str, int lo, int hi) {
		if(lo == hi){
			anagramList.add(new myString(str.toString()));
		}
		else{
			for( int i = lo;i<=hi;i++){
				swap(str,lo,i);
				createAnagram(str,lo+1,hi);
				swap(str,lo,i);
			}
		}
		
	}
	private void swap(StringBuilder str, int lo, int i) {
		char temp = str.charAt(lo);
		str.setCharAt(lo, str.charAt(i));
		str.setCharAt(i,temp);
	}
	public void Show(){
		for(myString str:anagramList){
			System.out.println(str);
		}
	}
	@Override
	public String toString() {
		return "Anagram [strTarget=" + strTarget + "]";
	}
}

class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.nextLine();
		for( int i = 0 ; i < n;i++){
			String str = sc.nextLine();
			Anagram temp = new Anagram(str);
			temp.Show();
		}
	}

}
