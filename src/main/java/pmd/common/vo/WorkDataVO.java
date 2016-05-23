package pmd.common.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WorkDataVO implements Serializable{
	private String type= "";
	private String date= "";
	private String company= "";
	private String owner= "";
	private String address= "";
	private String productname= "";
	private String version= "";
	private String number= "";
	private String license= "";
	private String seller= "";
	private String serial= "";
	private String count= "";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "WorkDataVO [type=" + type + ", date=" + date + ", company=" + company + ", owner=" + owner
				+ ", address=" + address + ", productname=" + productname + ", version=" + version + ", number="
				+ number + ", license=" + license + ", seller=" + seller + ", serial=" + serial + ", count=" + count
				+ "]";
	}
	
	
}
