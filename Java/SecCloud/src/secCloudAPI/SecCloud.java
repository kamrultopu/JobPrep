/**
 *  This package is for demonstration of Seccloud 
 */
package secCloudAPI;

import it.unisa.dia.gas.jpbc.Element;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import auditorAPI.Auditor;
import serverAPI.Server;
import clientAPI.Client;


/**
 * @author MKI
 *
 */
public class SecCloud {

	/**
	 * 
	 */
	public SecCloud() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int userNo = 3;
		int recordCount = 500;
		Random rnd = new Random();
		for( int ru = 1; ru < 8 ; ru++){
			
			for( int rc = 500; rc < 4000 ; rc+=500){
				Server server = new Server();
				Client.setServer(server);
				HashMap<Integer, Client> hmapUser = new HashMap<Integer, Client>();
				HashMap<ProvRecord,Client> hmapRecord = new HashMap<ProvRecord,Client>();
				boolean bFinish = false;
				//Scanner sc = new Scanner(System.in);
				
				hmapUser.clear();
				Client.clientNo = 1000;
				for(int i = 0 ; i < ru; i++){
					Client currentClient = new Client();
					int clID = currentClient.getUserID();
					hmapUser.put(clID, currentClient);
				}
				
				for(int i = 0 ; i < rc; i++ ){
					int currentUser = rnd.nextInt(ru) + 1000;
					//System.out.println(ru);
					//System.out.println(hmapUser.keySet().toString());
					
					Client currentClient = hmapUser.get(1000);
					//System.out.println(currentClient);
					Data currentData = currentClient.CreateData();
					ProvRecord currentProvRecord = server.put(currentData);
					Element currentSign = currentClient.getSign(currentData);
					if(server.put(currentProvRecord,currentSign,currentClient)){
						hmapRecord.put(currentProvRecord, currentClient);
					}
				}
				
				Auditor auditor = new Auditor();
				auditor.setServer(server);
				long tn = System.currentTimeMillis();
				if(auditor.verify()){
					System.out.println("Provenance Record verified");
				}
				else{
					System.out.println("Provenance Record not Verified");
				}
				System.out.println("User count: "+ ru + " record #: " + rc + " time = " + (System.currentTimeMillis() - tn));
			}
		}
		
		
		
		
	}
}