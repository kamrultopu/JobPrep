
public class MyHashMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "my name is kanrul";
		System.out.println(str.hashCode());
		HashMap test = new HashMap();
		test.put(5, 5);
		test.put(10, 10);
		test.put(133, 133);
		test.put(5, 25);
		System.out.println(test.get(10));
		System.out.println(test.get(5));
		
	}

}

class HashMap{
	private static final int MaxSize = 128;
	HashEntry[] table;
	HashMap(){
		table = new HashEntry[MaxSize];
	}
	private int HashFunction(int value){
		int key = -1;
		key = value%MaxSize;
		return key;
	}
	public boolean put(int key, int val){
		int hash = HashFunction(key);
		int start = hash;
		while(table[hash]!=null && table[hash].getKey()!=key){
			hash = (hash+1)%128;
			if(hash == start){
				return false;
			}
		}
		table[hash] = new HashEntry(key,val);
		//System.out.println("hi"+key+val+hash);
		return true;
	}
	public int get(int key){
		int val =-1;
		int hash = HashFunction(key);
		int start = hash;
		if(table[hash]!=null && table[hash].getKey()!=key){
			hash = (hash+1)%128;
			if(start == hash){
				return val;
			}
		}
		if(table[hash]!=null)
			val = table[hash].getVal();
		return val;
	}
}

class HashEntry{
	private int key;
	private int value;
	HashEntry(int key,int val){
		this.key = key;
		this.value = val;
	}
	public int getKey(){
		return key;
	}
	public int getVal(){
		return value;
	}
}