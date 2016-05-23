package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Chart2VO  implements Serializable{
	private String swName= "";
	private int usingQuantity= 0;
	public String getSwName() {
		return swName;
	}
	public void setSwName(String swName) {
		this.swName = swName;
	}
	public int getUsingQuantity() {
		return usingQuantity;
	}
	public void setUsingQuantity(int usingQuantity) {
		this.usingQuantity = usingQuantity;
	}
	@Override
	public String toString() {
		return "Chart2VO [swName=" + swName + ", usingQuantity=" + usingQuantity + "]";
	}
	
}
