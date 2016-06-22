package auditorAPI;

import java.io.IOException;

import serverAPI.Server;

public class Auditor {
	private Server server;
	public Auditor() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean verify(){
		try {
			if( server.serverBAS.verify(server) ){
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public void setServer(Server server) {
		this.server = server;
	}
}
