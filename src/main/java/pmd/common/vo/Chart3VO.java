package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Chart3VO implements Serializable {
	private String pcName= "";
	private String pcIp= "";
	private String pcOs= "";
	private String swName= "";
	private String updateDate= "";
	
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public String getPcIp() {
		return pcIp;
	}
	public void setPcIp(String pcIp) {
		this.pcIp = pcIp;
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
	@Override
	public String toString() {
		return "Chart3VO [pcName=" + pcName + ", pcIp=" + pcIp + ", pcOs=" + pcOs + ", swName=" + swName
				+ ", updateDate=" + updateDate + "]";
	}
	
	
}
