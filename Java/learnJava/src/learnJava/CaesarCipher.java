package learnJava;

/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    public String encrypt(String message, int key){
        StringBuilder sb = new StringBuilder(message);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = alphabet.substring(key) + alphabet; 
        for(int i = 0 ; i < message.length() ; i++){
        	char ch = message.charAt(i);
        	boolean flag = false;
        	if( Character.isLowerCase(ch)){
        		ch = Character.toUpperCase(ch);
        		flag = true;
        	}
            int index = alphabet.indexOf(ch);
            if( index >= 0 )
            {
            	char shifCH = shiftedAlphabet.charAt(index);
            	if(flag ){
            		shifCH = Character.toLowerCase(shifCH);
            	}
            	sb.setCharAt(i, shifCH);
            }
        }
        return sb.toString();
    }

    public void testCaesar(){
    	
    	
    }
    
    public String encryptTwoKeys(String message, int key1, int key2){
    	StringBuilder sb = new StringBuilder(message);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet1 = alphabet.substring(key1) + alphabet; 
        String shiftedAlphabet2 = alphabet.substring(key2) + alphabet; 
        for(int i = 0 ; i < message.length() ; i++){
        	char ch = message.charAt(i);
        	boolean flag = false;
        	if( Character.isLowerCase(ch)){
        		ch = Character.toUpperCase(ch);
        		flag = true;
        	}
            int index = alphabet.indexOf(ch);
            if( index >= 0 )
            {
            	char shifCH;
            	if( i %2 == 0 ){
            		shifCH = shiftedAlphabet1.charAt(index);
            	}
            	else {
            		shifCH = shiftedAlphabet2.charAt(index);
            	}
            	if(flag ){
            		shifCH = Character.toLowerCase(shifCH);
            	}
            	sb.setCharAt(i, shifCH);
            }
        }
        return sb.toString();
    }

    public String decrypt(String message, int key){
    	String decryptMessage = encrypt(message, 26-key);
    	return decryptMessage;
    }
    
    public String decryptTwo(String message, int key1 , int key2){
    	StringBuilder sb1 = new StringBuilder(encrypt(message,26-key1));
    	StringBuilder sb2 = new StringBuilder(encrypt(message,26-key2));
    	for( int i = 1 ; i < message.length(); i+=2){
    		sb1.setCharAt(i, sb2.charAt(i));
    	}
    	return sb1.toString();
    }
    
    public void eyeBallDecrypt(String message){
    	for( int i = 0 ; i <= 26 ; i++ ){
			System.out.println(decrypt(message, i));
		}
    }
    
    public void eyeBallDecryptTwo(String message){
    	for( int i = 0 ; i <= 26 ; i++ ){
    		for( int j = 0 ; j <= 26 ; j++ ){
    			String dString = decryptTwo(message, i, j);
    			if(dString.contains(" and ") || dString.contains(" is ")){
    				System.out.println(decryptTwo(message, i, j));
    			}
    			//System.out.println(decryptTwo(message, i, j));
    		}
			
		}
    }
    
}
