/**
 *  This package is for demonstration of Seccloud 
 */
package secCloudAPI;

import it.unisa.dia.gas.jpbc.Element;

import java.util.HashMap;
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
		Server server = new Server();
		Client.setServer(server);
		HashMap<Integer, Client> hmapUser = new HashMap<Integer, Client>();
		HashMap<ProvRecord,Client> hmapRecord = new HashMap<ProvRecord,Client>();
		boolean bFinish = false;
		Scanner sc = new Scanner(System.in);
		
		while(!bFinish){
			System.out.println(" insert Data? 1. Yes : 2. No : ");
			
			int chOption = Integer.parseInt(sc.nextLine());
			Client currentClient;
			if(chOption == 2){
				bFinish = true;
				continue;
			}
			System.out.println("Client ID (if first time user input 0 : ");
			
			int clID =  Integer.parseInt(sc.nextLine()); 
			if(hmapUser.containsKey(clID)){
				currentClient = hmapUser.get(clID);
			} 
			else{
				currentClient = new Client();
				clID = currentClient.getUserID();
				hmapUser.put(clID, currentClient);
			}
			Data currentData = currentClient.CreateData();
			ProvRecord currentProvRecord = server.put(currentData);
			Element currentSign = currentClient.getSign(currentData);
			if(server.put(currentProvRecord,currentSign,currentClient)){
				hmapRecord.put(currentProvRecord, currentClient);
			}
			
		}
		sc.close();
		Auditor auditor = new Auditor();
		auditor.setServer(server);
		if(auditor.verify()){
			System.out.println("Provenance Record verified");
		}
		else{
			System.out.println("Provenance Record not Verified");
		}
	}
}
