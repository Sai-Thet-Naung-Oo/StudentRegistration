package model;

public abstract class StaffInfo {

	int StaffNo;
	String StaffName;
	String StaffAddress;
	String StaffNRC;
	String StdaffPhno;
	
	
	public StaffInfo() {
		super();
	}
	public StaffInfo(int staffNo, String staffName, String staffAddress, String staffNRC, String stdaffPhno) {
		super();
		StaffNo = staffNo;
		StaffName = staffName;
		StaffAddress = staffAddress;
		StaffNRC = staffNRC;
		StdaffPhno = stdaffPhno;
	}
	public int getStaffNo() {
		return StaffNo;
	}
	public void setStaffNo(int staffNo) {
		StaffNo = staffNo;
	}
	public String getStaffName() {
		return StaffName;
	}
	public void setStaffName(String staffName) {
		StaffName = staffName;
	}
	public String getStaffAddress() {
		return StaffAddress;
	}
	public void setStaffAddress(String staffAddress) {
		StaffAddress = staffAddress;
	}
	public String getStaffNRC() {
		return StaffNRC;
	}
	public void setStaffNRC(String staffNRC) {
		StaffNRC = staffNRC;
	}
	public String getStdaffPhno() {
		return StdaffPhno;
	}
	public void setStdaffPhno(String stdaffPhno) {
		StdaffPhno = stdaffPhno;
	}
	
	
	
	
	
}
