
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    public string encrypt(String message, int key){
        StringBuilder sb = new StrinbBuilder(message);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = alphabet.substring(key) + alphabet; 
        for(int i = 0 ; i < message.length ; i++){
            int index = alphabet.indexOf(message.charAt(i));
            sb = shiftedAlphabet.charAt(index) + 
        }
        return sb;
    }
}
