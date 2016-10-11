package pmd.common.vo;

public class ContactInfoVO {
	private String name= "";
	private String pnum= "";
	private String unum= "";
	private String pkey= "";
	private String enabledTime= "";
	private String messageReq= "";
	
	
	public ContactInfoVO() {
		super();
	}
	public ContactInfoVO(String name, String pnum, String unum, String pkey) {
		super();
		this.name = name;
		this.pnum = pnum;
		this.unum = unum;
		this.pkey = pkey;
	}
	public ContactInfoVO(String name, String pnum, String unum, String pkey, String messageReq) {
		super();
		this.name = name;
		this.pnum = pnum;
		this.unum = unum;
		this.pkey = pkey;
		this.messageReq= messageReq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getUnum() {
		return unum;
	}
	public void setUnum(String unum) {
		this.unum = unum;
	}
	public String getPkey() {
		return pkey;
	}
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	public String getEnabledTime() {
		return enabledTime;
	}
	public void setEnabledTime(String enabledTime) {
		this.enabledTime = enabledTime;
	}
	public String getMessageReq() {
		return messageReq;
	}
	public void setMessageReq(String messageReq) {
		this.messageReq = messageReq;
	}
	
	
}

// VO -> ServiceImpl -> Service -> Controller (기능 3개 필요: 조회/등록/삭제)
