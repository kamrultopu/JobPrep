package serverAPI;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import clientAPI.Client;
import secCloudAPI.Data;
import secCloudAPI.ProvRecord;
import storage.CloudStorage;
import BAS.BAS;

public class Server {
	public BAS serverBAS;
	public Element currentSig;
	public String currentSigString;
	private String foldername;
	public HashMap<Data,Client> datamapCollection = new HashMap<Data,Client>();
	public ArrayList<Data> dataCollection = new ArrayList<Data>();
	ArrayList<ProvRecord> provCollection = new ArrayList<ProvRecord>();
	HashMap<ProvRecord,Data> prRecordQueue = new HashMap<ProvRecord,Data>();
	private CloudStorage cloudStorage = new CloudStorage();
	public Server() {
		// TODO Auto-generated constructor stub
		serverBAS = new BAS();
		currentSig = null;
		foldername = "./resource/Server/";
	}
	
	public ProvRecord put(Data data){
		ProvRecord prRecord = new ProvRecord();
		prRecord.CreatePR(data);
		prRecordQueue.put(prRecord, data);
		return prRecord;
	}
	
	public boolean put(ProvRecord provRecord, Element newSign, Client client){
		Data data;
		if( prRecordQueue.containsKey(provRecord) ){
			data = prRecordQueue.get(provRecord);
			if(cloudStorage.StoreData(data, provRecord,client)){
				provCollection.add(provRecord);
				dataCollection.add(data);
				datamapCollection.put(data, client);
				currentSig = serverBAS.aggreSign(currentSig, newSign);
				currentSigString = Base64.encodeBytes(currentSig.toBytes());
				return true;
			}
		}
		return false;
	}
	
	public boolean saveClientPublicKey(int userID, byte[] pubkey){
		String filename = foldername + "User_pub_Key_User_ID_" + Integer.toString(userID);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
			fos.write(pubkey);
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
