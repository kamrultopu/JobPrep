package learnJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import learnJava.CaesarCipher;

public class CaesarBreaker {
	int[] counts = new int[26];
	StringBuilder fileString = new StringBuilder();
	public void countLetters(String filename) throws IOException{
		File fp = new File(filename);
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine())!= null){
			fileString.append(line);
			for( int i = 0 ; i < line.length();i++){
				if(Character.isLetter(line.charAt(i))){
					int index = Character.toLowerCase(line.charAt(i)) - 97;
					//System.out.println(line.charAt(i));
					counts[index]++;
				}
			}
		}
	}
	
	public int maxIndex(){
		int max = -1;
		int indexMax = -1;
		for( int i = 0 ; i < counts.length ; i++){
			if( counts[i] > max){
				indexMax = i;
				max = counts[i];
			}
		}
		return indexMax;
	}

	public String decrypt(String filename) throws IOException{
		countLetters(filename);
		int maxIndex = maxIndex();
		System.out.println(maxIndex);
		int shift;
		if( maxIndex < 5 ){
			shift = 5-maxIndex;
		}
		else{
			shift = 26 - (maxIndex - 5);
		}
		int key = 26 - shift + 1;
		CaesarCipher cc = new CaesarCipher();
		System.out.println(key);
		return cc.decrypt(fileString.toString(), key);
	}
	
	public String halfOfString(String message, int start){
		StringBuilder sb = new StringBuilder();
		for( int i = start ; i < message.length(); i+=2){
			sb.append(message.charAt(i));
		}
		return sb.toString();
	}
	
}
