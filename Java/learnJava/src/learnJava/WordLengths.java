package learnJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class WordLengths {
	//int[] counts;
	WordLengths(){
		//counts = new int[25];
	}
	public void countWorldLengths(String filename, int[] counts) throws IOException{
		File fp= new File(filename);
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);
		String line;
		Arrays.fill(counts, 0);
		while((line = br.readLine()) != null){
			//System.out.println(line);
			String[] strList = line.split(" ");
			for( int i = 0 ; i < strList.length; i++){
				int wordlength = strList[i].length();
				if( wordlength <=1)
					continue;
				//System.out.println(strList[i]);
				//System.out.println(wordlength);
				if( !Character.isLetter(strList[i].charAt(wordlength-1))){
					wordlength--;
				}
				if( !Character.isLetter(strList[i].charAt(0))){
					wordlength--;
				}
				counts[wordlength]++;
			}
		}
		br.close();
	}

	public int indexOfMax(int[] counts){
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
}
