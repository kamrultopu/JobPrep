import java.util.HashMap;

import it.unisa.dia.gas.jpbc.Element;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.digests.SHA256Digest;

import engines.BASSigner;
import BAS.BAS;




public class MultiSignature {

	public static void main(String[] args) {
		int totalUser = 2;
		int totalMessage;
		String[] messages = {"Hello 1", "Hello 2", "Hello 3" , "Hello 4"};
		totalMessage = messages.length;
		//System.out.println(totalMessage);
		BAS bs = new BAS(totalUser,totalMessage);
		bs.keyPair = new AsymmetricCipherKeyPair[totalUser];
		CipherParameters[] privKeys = new CipherParameters[totalUser];	
		CipherParameters[] pubKeys = new CipherParameters[totalUser];
		
		HashMap<Integer,Integer> hmap = new HashMap<Integer,Integer>();
		int[] map = {0,1,0,0};
		for( int i = 0 ; i < totalMessage ; i++ ){
			hmap.put(i, map[i]);
		}
		
		for(int i = 0 ; i < totalUser ; i++){
			AsymmetricCipherKeyPair temp = bs.keyGen(bs.parameters);
			bs.keyPair[i] = temp;
			privKeys[i] = temp.getPrivate();
			pubKeys[i] = temp.getPublic();
		}
		
		/*byte[][] bt = new byte[totalMessage][256];
		for(int i = 0 ; i < totalMessage ; i++){
			int userIndex = hmap.get(i);
			//System.out.println(userIndex);
			bt[i] = bs.sign(messages[i],privKeys[userIndex]);
		}
		messages[2] = "kamrul";
		for(int i = 0 ; i < totalMessage ; i++){
			int userIndex = hmap.get(i);
			if(bs.verify(bt[i], messages[i], pubKeys[userIndex])){
				System.out.println("signature verified");
			}
			else {
				System.out.println("signature wrong");
			}
		}*/
		
		byte[] aggreSign = null;
		
		aggreSign = bs.aggreSign(messages, privKeys, hmap);
		
		
		String[] temp = {"Hello 1", "Hello 2", "Hello 3" , "Hello 4"};
		//hmap.put(3, 1);
		if(bs.aggreVerify(aggreSign, temp, pubKeys, hmap)){
			System.out.println("verified");
		}
		else {
			System.out.println("signature not verified");
		}
		
		
	}

}
