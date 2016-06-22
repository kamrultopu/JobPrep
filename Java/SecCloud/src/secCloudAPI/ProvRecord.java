package secCloudAPI;

public class ProvRecord {
	byte[] provRecord;
	public ProvRecord() {
		// TODO Auto-generated constructor stub
	}

	public void CreatePR(Data data) {
		// TODO Auto-generated method stub
		provRecord = data.getByte();
	}

}
