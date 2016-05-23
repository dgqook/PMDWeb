package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExpiryManageVO implements Serializable {
	private String userId= "";
	private String userCoName= "";
	private String userName= "";
	private String userTel= "";
	private String userHp= "";
	private String swName= "";
	private String ownQuantity= "";
	private String ownExpDate= "";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserCoName() {
		return userCoName;
	}
	public void setUserCoName(String userCoName) {
		this.userCoName = userCoName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserHp() {
		return userHp;
	}
	public void setUserHp(String userHp) {
		this.userHp = userHp;
	}
	public String getSwName() {
		return swName;
	}
	public void setSwName(String swName) {
		this.swName = swName;
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
	
	
}
