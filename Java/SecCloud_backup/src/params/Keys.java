package params;

import it.unisa.dia.gas.jpbc.Element;

public class Keys {
	private Element pk;
	private Element sk;
	private BASParameters parameters;
	public Element getPk() {
		return pk;
	}
	public Element getSk() {
		return sk;
	}
	public void setSk(Element sk) {
		this.sk = sk;
	}
	public BASParameters getParameters() {
		return parameters;
	}
	public void setParameters(BASParameters parameters) {
		this.parameters = parameters;
	}
	public Keys(Element e1, Element e2, BASParameters param) {
		// TODO Auto-generated constructor stub
		pk = e1;
		sk = e2;
		parameters = param;
	}

}
