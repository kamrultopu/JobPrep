package secCloudAPI;

public class Data {
	private String data;
	public Data() {
		// TODO Auto-generated constructor stub
		StringBuilder sb = new StringBuilder("");
		data = sb.toString();
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public byte[] getByte(){
		return data.getBytes();
	}
}
