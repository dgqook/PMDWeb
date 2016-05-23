package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SoftwareInfoVO implements Serializable{
	private String ownSer= "";
	private String swName= "";
	private String swVendor= "";
	private String swFile= "";
	private String swVersion= "";
	private String swVendorKr= "";
	private String userId= "";
	private String pcName= "";
	private String pcIp= "";
	private String pcOs= "";
	private String updateDate= "";
	private String ownQuantity= "";
	private String ownExpDate= "";
	private String originalQuantity= "";
	private String copyQuantity= "";
	private String stockQuantity= "";
	private String usingQuantity= "";
	private String param1= "";
	
	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public SoftwareInfoVO() {
		super();
	}

	public String getOwnSer() {
		return ownSer;
	}

	public void setOwnSer(String ownSer) {
		this.ownSer = ownSer;
	}

	public SoftwareInfoVO(String swName, String swVendor, String swFile, String swVersion, String swVendorKr) {
		super();
		this.swName = swName;
		this.swVendor = swVendor;
		this.swFile = swFile;
		this.swVersion = swVersion;
		this.swVendorKr = swVendorKr;
	}

	public SoftwareInfoVO(String swName, String swVendor, String swFile, String swVersion, String userId, String pcName,
			String pcIp, String pcOs, String updateDate) {
		super();
		this.swName = swName;
		this.swVendor = swVendor;
		this.swFile = swFile;
		this.swVersion = swVersion;
		this.userId = userId;
		this.pcName = pcName;
		this.pcIp = pcIp;
		this.pcOs = pcOs;
		this.updateDate = updateDate;
	}
	
	public SoftwareInfoVO(String swName, String userId, String pcName, String pcIp, String pcOs, String updateDate) {
		super();
		this.swName = swName;
		this.userId = userId;
		this.pcName = pcName;
		this.pcIp = pcIp;
		this.pcOs = pcOs;
		this.updateDate = updateDate;
	}
	
	public SoftwareInfoVO(String swName, String swFile, String userId, String pcName, String pcIp, String pcOs, String updateDate) {
		super();
		this.swName = swName;
		this.swFile = swFile;
		this.userId = userId;
		this.pcName = pcName;
		this.pcIp = pcIp;
		this.pcOs = pcOs;
		this.updateDate = updateDate;
	}

	

	public String getSwName() {
		return swName;
	}
	public void setSwName(String swName) {
		this.swName = swName;
	}
	public String getSwVendor() {
		return swVendor;
	}
	public void setSwVendor(String swVendor) {
		this.swVendor = swVendor;
	}
	public String getSwFile() {
		return swFile;
	}
	public void setSwFile(String swFile) {
		this.swFile = swFile;
	}
	public String getSwVersion() {
		return swVersion;
	}
	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}
	public String getSwVendorKr() {
		return swVendorKr;
	}
	public void setSwVendorKr(String swVendorKr) {
		this.swVendorKr = swVendorKr;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getOwnQuantity() {
		return ownQuantity;
	}
	public void setOwnQuantity(String ownQuantity) {
		this.ownQuantity = ownQuantity;
	}
	public String getOwnExpDate() {
		return ownExpDate;
	}
	public void setOwnExpDate(String ownExpDate) {
		this.ownExpDate = ownExpDate;
	}
	public String getOriginalQuantity() {
		return originalQuantity;
	}
	public void setOriginalQuantity(String originalQuantity) {
		this.originalQuantity = originalQuantity;
	}
	public String getCopyQuantity() {
		return copyQuantity;
	}
	public void setCopyQuantity(String copyQuantity) {
		this.copyQuantity = copyQuantity;
	}
	public String getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getUsingQuantity() {
		return usingQuantity;
	}
	public void setUsingQuantity(String usingQuantity) {
		this.usingQuantity = usingQuantity;
	}
	@Override
	public String toString() {
		return "SoftwareInfoVO [swName=" + swName + ", swVendor=" + swVendor + ", swFile=" + swFile + ", swVersion="
				+ swVersion + ", swVendorKr=" + swVendorKr + ", userId=" + userId + ", pcName=" + pcName + ", pcIp="
				+ pcIp + ", pcOs=" + pcOs + ", updateDate=" + updateDate + ", ownQuantity=" + ownQuantity
				+ ", ownExpDate=" + ownExpDate + ", originalQuantity=" + originalQuantity + ", copyQuantity="
				+ copyQuantity + ", stockQuantity=" + stockQuantity + ", usingQuantity=" + usingQuantity + "]";
	}
	
	
}
