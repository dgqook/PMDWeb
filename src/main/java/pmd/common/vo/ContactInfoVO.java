package pmd.common.vo;

public class ContactInfoVO {
	private String name= "";
	private String pnum= "";
	private String skey= "";
	private String akey= "";
	
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
	public String getSkey() {
		return skey;
	}
	public void setSkey(String skey) {
		this.skey = skey;
	}
	public String getAkey() {
		return akey;
	}
	public void setAkey(String akey) {
		this.akey = akey;
	}
}

// VO -> ServiceImpl -> Service -> Controller (기능 3개 필요: 조회/등록/삭제)
