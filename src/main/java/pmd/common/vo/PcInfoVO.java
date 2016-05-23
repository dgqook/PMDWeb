package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PcInfoVO implements Serializable {
	private String pcName= "";
	private String pcOs= "";
	private String swName= "";
	private String updateDate= "";
	private String param1= "";
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public String getPcOs() {
		return pcOs;
	}
	public void setPcOs(String pcOs) {
		this.pcOs = pcOs;
	}
	public String getSwName() {
		return swName;
	}
	public void setSwName(String swName) {
		this.swName = swName;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	@Override
	public String toString() {
		return "PcInfoVO [pcName=" + pcName + ", pcOs=" + pcOs + ", swName=" + swName + ", updateDate=" + updateDate
				+ ", param1=" + param1 + "]";
	}
	
}
