package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Chart1VO implements Serializable{
	private String swName= "";
	private int ownQuantity= 0;
	private int copyQuantity= 0;
	private int stockQuantity= 0;
	public String getSwName() {
		return swName;
	}
	public void setSwName(String swName) {
		this.swName = swName;
	}
	public int getOwnQuantity() {
		return ownQuantity;
	}
	public void setOwnQuantity(int ownQuantity) {
		this.ownQuantity = ownQuantity;
	}
	public int getCopyQuantity() {
		return copyQuantity;
	}
	public void setCopyQuantity(int copyQuantity) {
		this.copyQuantity = copyQuantity;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	@Override
	public String toString() {
		return "Chart1VO [swName=" + swName + ", ownQuantity=" + ownQuantity + ", copyQuantity=" + copyQuantity
				+ ", stockQuantity=" + stockQuantity + "]";
	}
	
	
}
