
public class Assignment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "54 68 69 73 20 73 65 6E 74 65 6E 63 65 20 69 73 20 65 6E 63 6F 64 65 64 20 69 6E 20 48 65 78 61 44 65 63 69 6D 61 6C 20 63 68 61 72 61 63 74 65 72 73 2E";
		System.out.println(HexaDecimalNumber.HexaMessage(str));
		str = "8A 1A 1A 85";
		System.out.println(HexaDecimalNumber.DottedIPfromHexa(str));
		str = "41 58 59 72";
		System.out.println(HexaDecimalNumber.DottedIPfromHexa(str));
		str = "60 11 6A 5A";
		System.out.println(HexaDecimalNumber.DottedIPfromHexa(str));
	}
	
}
