package storage;

import java.util.ArrayList;
import java.util.HashMap;

import clientAPI.Client;
import secCloudAPI.Data;
import secCloudAPI.ProvRecord;

public class CloudStorage {
	HashMap<ProvRecord,Data> dataWithProvenance = new HashMap<ProvRecord,Data>();
	HashMap<Data,Client> dataWithClient = new HashMap<Data,Client>();
	ArrayList<ProvRecord> provCollection = new ArrayList<ProvRecord>();
	ArrayList<Data> dataCollection = new ArrayList<Data>();
	public CloudStorage() {
		// TODO Auto-generated constructor stub
	}
	public boolean StoreData(Data data, ProvRecord provRecord, Client client){
		return true;
	}

}
