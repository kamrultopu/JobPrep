
public class HexaDecimalNumber {
	public static String DecimalToHexa(int n){
		return Integer.toHexString(n);
	}
	public static int HexaToInt(String str){
		return Integer.parseInt(str, 16);
	}
	/*
	 * parameter str is hexa character splitted by ' ' for each character
	 * return is the message in human readable code
	 */
	public static String HexaMessage(String str){
		StringBuilder msg = new StringBuilder();
		String[] list = str.split(" ");
		int size = list.length;
		for( int i = 0 ; i < size ; i++){
			//System.out.println(list[i]);
			msg.append((char)HexaToInt(list[i]));
		}
		//System.out.println(msg);
		return msg.toString();	
	}
	/*
	 * parameter str is dotted IP 
	 * return value is a hexa form of IP splitted by ' ' for each octet
	 */
	public static String HexaIPfromDotted(String str){
		StringBuilder sb = new StringBuilder();
		String[] list = str.split("\\.");
		int size = list.length;
		System.out.println("str: " + str + " size: " + size );
		for( int i = 0 ; i < size ; i++){
			list[i] = list[i].trim();
			sb.append(DecimalToHexa(Integer.parseInt(list[i])));
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/*
	 * parameter str is a hexa form of IP splitted by ' ' for each octet
	 * returned is a dotted IP address
	 */
	public static String DottedIPfromHexa(String str){
		StringBuilder sb = new StringBuilder();
		String[] list = str.split(" ");
		int size = list.length;
		for( int i = 0 ; i < size; i++){
			//System.out.println(list[i]);
			sb.append(Integer.toString(HexaToInt(list[i])));
			sb.append(".");
		}
		return sb.toString();
	}
}
