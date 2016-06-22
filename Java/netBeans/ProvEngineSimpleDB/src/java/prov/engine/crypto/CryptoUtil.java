/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.crypto;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;

/**
 *
 * @author Shams
 */
public class CryptoUtil {
    
    private static byte[] rsaEncrypt(byte[] data, PublicKey pubKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] cipherData = cipher.doFinal(data);
			return cipherData;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	private static byte[] rsaDecrypt(byte[] cipherData, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] plainData = cipher.doFinal(cipherData);
			return plainData;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private static byte[] rsaEncryptLongMessage(byte[] plainText, PublicKey pubKey)
	{
		List<byte[]> cipherByteList = new ArrayList<byte[]>();
		int maxSize = 245;
		int n = plainText.length/maxSize;
		int totalBytes = 0;
		int totalCyperSize = 0;
		for(int i=0; i< n; i++)
		{
			byte[] subPlain = getSubArray(plainText, i*maxSize, maxSize);
			byte[] subCipher = rsaEncrypt(subPlain, pubKey);
			cipherByteList.add(subCipher);
			totalBytes+=maxSize;
			totalCyperSize+= subCipher.length;
		}
		byte[] subPlain = getSubArray(plainText, totalBytes, (plainText.length - totalBytes));
		byte[] subCipher = rsaEncrypt(subPlain, pubKey);
		totalCyperSize+= subCipher.length;
		cipherByteList.add(subCipher);
		
		byte[] result = new byte[totalCyperSize];
		int offset=0;
		for(byte[] cb : cipherByteList)
		{
			int i=0;
			for(; i< cb.length; i++)
			{
				result[offset+i] = cb[i];
			}
			offset += i;
		}
		return result;
	}
	
	private static String rsaDecryptLongMessage(byte[] cipherText, PrivateKey priKey)
	{
		StringBuilder sb = new StringBuilder();
		int maxSize = 256;
		int n = cipherText.length/maxSize;
		for(int i=0; i< n; i++)
		{
			byte[] subCipher = getSubArray(cipherText, i*maxSize, maxSize);
			byte[] subPlain = rsaDecrypt(subCipher, priKey);
			sb.append(new String(subPlain));
		}
		return sb.toString();
	}
	private static byte[] getSubArray(byte[] array, int offset, int size) {
		byte[] subArray = new byte[size];
		for (int i = 0; (i < size) && (i + offset < array.length); i++) {
			subArray[i] = array[offset + i];
		}
		return subArray;
	}
	
	private static byte[] rsaSignMessage(byte[] data, PrivateKey prvKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, prvKey);
			byte[] cipherData = cipher.doFinal(data);
			return cipherData;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	private static byte[] rsaDecryptSignature(byte[] cipherData, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] plainData = cipher.doFinal(cipherData);
			return plainData;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
        private static byte[] rsaSignLongMessage(byte[] plainText, PrivateKey priKey)
	{
		List<byte[]> cipherByteList = new ArrayList<byte[]>();
		int maxSize = 245;
		int n = plainText.length/maxSize;
		int totalBytes = 0;
		int totalCyperSize = 0;
		for(int i=0; i< n; i++)
		{
			byte[] subPlain = getSubArray(plainText, i*maxSize, maxSize);
			byte[] subCipher = rsaSignMessage(subPlain, priKey);
			cipherByteList.add(subCipher);
			totalBytes+=maxSize;
			totalCyperSize+= subCipher.length;
		}
		byte[] subPlain = getSubArray(plainText, totalBytes, (plainText.length - totalBytes));
		byte[] subCipher = rsaSignMessage(subPlain, priKey);
		totalCyperSize+= subCipher.length;
		cipherByteList.add(subCipher);
		
		byte[] result = new byte[totalCyperSize];
		int offset=0;
		for(byte[] cb : cipherByteList)
		{
			int i=0;
			for(; i< cb.length; i++)
			{
				result[offset+i] = cb[i];
			}
			offset += i;
		}
		return result;
	}
	
	private static String rsaDecryptLongSignature(byte[] cipherText, PublicKey pubKey)
	{
		StringBuilder sb = new StringBuilder();
		int maxSize = 256;
		int n = cipherText.length/maxSize;
		for(int i=0; i< n; i++)
		{
			byte[] subCipher = getSubArray(cipherText, i*maxSize, maxSize);
			byte[] subPlain = rsaDecryptSignature(subCipher, pubKey);
			sb.append(new String(subPlain));
		}
		return sb.toString();
	}
	public static String getHash(String message) {
		return getHash(message,"SHA-256");	
	}
	
	public static String getHash(String message, String hashAlg) {
		MessageDigest md;
		StringBuffer sb = new StringBuffer();
		try {
			md = MessageDigest.getInstance(hashAlg);
			md.update(message.getBytes());
			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			
			//System.out.println("Hex format : " + sb.toString());

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	public static String decryptSignature(String message, PublicKey pubKey)
	{
		try {
			return new String(rsaDecryptSignature(Base64.decode(message), pubKey));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public static String getCipherMessage(String plainMessage, PublicKey publicKey)
	{
		byte[] plainText = plainMessage.getBytes();
		byte[] cipher = rsaEncrypt(plainText, publicKey);
		return Base64.encodeBytes(cipher);
	}
	
	public static String getLongCipherMessage(String plainMessage, PublicKey publicKey)
	{
		byte[] plainText = plainMessage.getBytes();
		byte[] cipher = rsaEncryptLongMessage(plainText, publicKey);
		return Base64.encodeBytes(cipher);
	}
	
	
        public static String decipherMessage(String encryptedMessage, PrivateKey privateKey)
	{
		
		byte[] cipherText;
		try {
			cipherText = Base64.decode(encryptedMessage);
			return new String(rsaDecrypt(cipherText, privateKey));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	
	public static String decryptLongSignature(String message, PublicKey pubKey) throws IOException
	{
		try {
			return new String(rsaDecryptLongSignature(Base64.decode(message), pubKey));
		} catch (IOException e) {
                    e.printStackTrace();
                    throw e;
		}
	}
	
	public static String decipherLongMessage(String encryptedMessage, PrivateKey priKey) throws KeyNotFoundException, IOException{
		//Log.d(tag, "receivedEncryptedMessage: " + encryptedMessage);
		try {
			byte[] cipherText;
			cipherText = Base64.decode(encryptedMessage);
			return new String(rsaDecryptLongMessage(cipherText, priKey));
		} catch (IOException e) {
			throw e;
		}

	}
}
