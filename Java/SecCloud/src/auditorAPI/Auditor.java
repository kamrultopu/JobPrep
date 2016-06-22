package auditorAPI;

import serverAPI.Server;

public class Auditor {
	private Server server;
	public Auditor() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean verify(){
		if( server.serverBAS.verify(server) ){
			return true;
		}
		else{
			return false;
		}
		
	}
	public void setServer(Server server) {
		this.server = server;
	}
}
