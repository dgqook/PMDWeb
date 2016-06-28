package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInfoVO implements Serializable {
	private String userSer= "";
	private String userId= "";
	private String userPw= "";
	private String userPmss= "";
	private String userName= "";
	private String userEmail= "";
	private String userCoName= "";
	private String userCoZip= "";
	private String userCoAddrSys= "";
	private String userCoAddr= "";
	private String userTel= "";
	private String userHp= "";
	private String userRegDate= "";
	private String userRecentConn= "";
	private String userExpiryDate= "";
	private String userQuestion= "";
	private String userAnswer= "";
	
	public String getUserSer() {
		return userSer;
	}
	public void setUserSer(String userSer) {
		this.userSer = userSer;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserPmss() {
		return userPmss;
	}
	public void setUserPmss(String userPmss) {
		this.userPmss = userPmss;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserCoName() {
		return userCoName;
	}
	public void setUserCoName(String userCoName) {
		this.userCoName = userCoName;
	}
	public String getUserCoZip() {
		return userCoZip;
	}
	public void setUserCoZip(String userCoZip) {
		this.userCoZip = userCoZip;
	}
	public String getUserCoAddrSys() {
		return userCoAddrSys;
	}
	public void setUserCoAddrSys(String userCoAddrSys) {
		this.userCoAddrSys = userCoAddrSys;
	}
	public String getUserCoAddr() {
		return userCoAddr;
	}
	public void setUserCoAddr(String userCoAddr) {
		this.userCoAddr = userCoAddr;
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
	public String getUserRegDate() {
		return userRegDate;
	}
	public void setUserRegDate(String userRegDate) {
		this.userRegDate = userRegDate;
	}
	public String getUserRecentConn() {
		return userRecentConn;
	}
	public void setUserRecentConn(String userRecentConn) {
		this.userRecentConn = userRecentConn;
	}
	public String getUserExpiryDate() {
		return userExpiryDate;
	}
	public void setUserExpiryDate(String userExpiryDate) {
		this.userExpiryDate = userExpiryDate;
	}
	public String getUserQuestion() {
		return userQuestion;
	}
	public void setUserQuestion(String userQuestion) {
		this.userQuestion = userQuestion;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	@Override
	public String toString() {
		return "UserInfoVO [userId=" + userId + ", userPmss=" + userPmss + ", userName=" + userName + ", userEmail="
				+ userEmail + ", userCoName=" + userCoName + "]";
	}
	
	
}
