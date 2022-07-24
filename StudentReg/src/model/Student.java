package model;

public class Student {
	int StdNo;
	String StdName;
	String StdAddress;
	String StdNRC;
	String StdPhno;
	
	public Student() {
		super();
	}
	public Student(int stdNo, String stdName, String stdAddress, String stdNRC, String stdPhno) {
		super();
		StdNo = stdNo;
		StdName = stdName;
		StdAddress = stdAddress;
		StdNRC = stdNRC;
		StdPhno = stdPhno;
	}
	public int getStdNo() {
		return StdNo;
	}
	public void setStdNo(int stdNo) {
		StdNo = stdNo;
	}
	public String getStdName() {
		return StdName;
	}
	public void setStdName(String stdName) {
		StdName = stdName;
	}
	public String getStdAddress() {
		return StdAddress;
	}
	public void setStdAddress(String stdAddress) {
		StdAddress = stdAddress;
	}
	public String getStdNRC() {
		return StdNRC;
	}
	public void setStdNRC(String stdNRC) {
		StdNRC = stdNRC;
	}
	public String getStdPhno() {
		return StdPhno;
	}
	public void setStdPhno(String stdPhno) {
		StdPhno = stdPhno;
	}
	
	
	
	

}
