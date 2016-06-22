package prov.engine.crypto;

import java.util.List;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;


public class RSAAccumulator {

	/**
	 * @param args
	
	 */
	
	private BigInteger N;
	private BigInteger publicKeyX;
	private BigInteger accumulator;
	private ArrayList<Integer> hashSet;
	
	public RSAAccumulator(BigInteger N, BigInteger publicKeyX)
	{
		this.publicKeyX= publicKeyX;
		this.N = N;
		hashSet = new ArrayList<Integer>();
	}
	public RSAAccumulator(int bits)
	{
		BigInteger P = new BigInteger(bits, new Random());
		BigInteger Q = new BigInteger(bits, new Random());
		BigInteger two = new BigInteger("2");
		N = (P.multiply(two).add(BigInteger.ONE)).multiply(Q.multiply(two).add(BigInteger.ONE));
		
		publicKeyX = new BigInteger(bits, new Random());
		
		hashSet = new ArrayList<Integer>();
		
		System.out.println("N:" + N + " X:" + publicKeyX);
	}
	
	public void insertElement(String element)
	{
		int hash = getIntHash(element);
		if(hashSet.size() == 0)
		{
			accumulator = (publicKeyX.pow(hash)).mod(N);
			hashSet.add(hash);
		}
		else{
			insertElement(element, accumulator);
		}
	}
	
	public void insertElement(String element, BigInteger currentAccumulator)
	{
		int hash = getIntHash(element);
		accumulator = (currentAccumulator.pow(hash)).mod(N);
		hashSet.add(hash);
	}
	
	public BigInteger getIdentity(String element)
	{
		BigInteger x = publicKeyX;
		int elementHash = getIntHash(element);
		for(Integer hash : hashSet)
		{
			if(elementHash != hash)
			{
				x = (x.pow(hash)).mod(N);
			}
		}
		return x;
	}
	
	public BigInteger getIdentity(String element, ArrayList<String> dataSet)
	{
		hashSet = new ArrayList<Integer>();
		for(String data : dataSet)
		{
			hashSet.add(getIntHash(data));
		}
		return getIdentity(element);
	}
	
	public void sethashSet(List<String> dataSet)
	{
		hashSet = new ArrayList<Integer>();
		for(String data : dataSet)
		{
			hashSet.add(getIntHash(data));
		}
	}
	
	public List<Integer> getHashSet(){
		return hashSet;
	}
	
	public boolean checkExistency(String element)
	{
		BigInteger identity = getIdentity(element);
		return checkExistency(element, identity);
	}
	public boolean checkExistency(String element,BigInteger identity)
	{
		return checkExistency(element, identity, accumulator);
	}
	public boolean checkExistency(String element,BigInteger identity, BigInteger acc)
	{
		int hash = getIntHash(element);
		return identity.pow(hash).mod(N).equals(acc);
	}
	
	
	public BigInteger getN() {
		return N;
	}

	public void setN(BigInteger n) {
		N = n;
	}

	public BigInteger getPublicKeyX() {
		return publicKeyX;
	}

	public void setPublicKeyX(BigInteger publicKeyX) {
		this.publicKeyX = publicKeyX;
	}

	public BigInteger getAccumulator() {
		return accumulator;
	}


	private int getIntHash(String message) {
		MessageDigest md;
		//StringBuffer sb = new StringBuffer();
		int hash = 0;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(message.getBytes());
			byte byteData[] = md.digest();
			
			ByteBuffer buf = ByteBuffer.wrap(byteData);
			//hash = buf.getInt();
			for (int i = 0; i < byteData.length; i++) {
				hash+= (byteData[i] & 0xff);
			}
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hash;
	}
	
	
}