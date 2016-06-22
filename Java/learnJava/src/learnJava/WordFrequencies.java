package learnJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class WordFrequencies {
	HashMap<String,Integer> myList;
	HashMap<String,Integer> characterList;
	File fp;
	WordFrequencies(String filename){
		myList = new HashMap<String,Integer>();
		characterList = new HashMap<String,Integer>();
		fp = new File(filename);
	}
	public int NoOfUnique(){
		return myList.size();
	}
	public void findUnique() throws IOException{
		myList.clear();
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line=br.readLine())!= null){
			String[] list = line.split(" ");
			for( int i = 0 ; i < list.length; i++){
				
				
				if(list[i].length() < 1 )
					continue;
				if( !Character.isLetter(list[i].charAt(0))){
					list[i] = list[i].substring(1);
					//System.out.println(list[i]);
				}
				boolean flag = false;
				if( list[i].length() >= 2){
					if( Character.isUpperCase(list[i].charAt(1))){
						System.out.println(list[i]);
						flag = true;
					}
					if( !Character.isLetter(list[i].charAt(list[i].length()-1))){
						list[i] = list[i].substring(0,list[i].length()-2);
					}
				}
				if(list[i].length()<1)
					continue;
				list[i]=list[i].toLowerCase();
				if( flag ){
					if(characterList.containsKey(list[i])){
						characterList.put(list[i], characterList.get(list[i])+1);
					}
					else {
						characterList.put(list[i], 1);
					}
				}
				if(myList.containsKey(list[i])){
					int val = myList.get(list[i]) + 1;
					myList.put(list[i], val);
				}
				else {
					myList.put(list[i], 1);
				}
			}
		}
	}
	
	public int findTotalCharacter(){
		return characterList.size();
	}
	
	public String findMostUsedCharacter(){
		int max = -1;
		String maxCount = null;
		for( String key : characterList.keySet()){
			int count = characterList.get(key);
			if(count > max){
				max = count;
				maxCount = key;
			}
		}
		return maxCount;
	}

	public int getCountCharacter(String str){
		return characterList.get(str);
	}
	
	public String findStringOfMax(){
		int max = -1;
		String maxCount = null;
		for( String key : myList.keySet()){
			int count = myList.get(key);
			if(count > max){
				max = count;
				maxCount = key;
			}
		}
		return maxCount;
	}
	
	public void ShowUniqueWord(){
		for( String key : myList.keySet()){
			int count = myList.get(key);
			System.out.println("word: " + key + ", count: " + count);
		}
	}
	
	public int getCount(String str){
		int result = 0;
		if( myList.containsKey(str)){
			result = myList.get(str);
		}
		return result;
	}
}
