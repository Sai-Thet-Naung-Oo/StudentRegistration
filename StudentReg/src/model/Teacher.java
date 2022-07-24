package model;

public class Teacher {
	int teacher_no;
	String teacherName;
	String teacherAddress;
	String teacherNRC;
	String teacherPhno;
	String teacherPost;
	

	public Teacher() {
		super();
	}
	public Teacher(int teacher_no, String teacherName, String teacherAddress, String teacherNRC, String teacherPhno,
			String teacherPost) {
		super();
		this.teacher_no = teacher_no;
		this.teacherName = teacherName;
		this.teacherAddress = teacherAddress;
		this.teacherNRC = teacherNRC;
		this.teacherPhno = teacherPhno;
		this.teacherPost = teacherPost;
	}
	public int getTeacher_no() {
		return teacher_no;
	}
	public void setTeacher_no(int teacher_no) {
		this.teacher_no = teacher_no;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherAddress() {
		return teacherAddress;
	}
	public void setTeacherAddress(String teacherAddress) {
		this.teacherAddress = teacherAddress;
	}
	public String getTeacherNRC() {
		return teacherNRC;
	}
	public void setTeacherNRC(String teacherNRC) {
		this.teacherNRC = teacherNRC;
	}
	public String getTeacherPhno() {
		return teacherPhno;
	}
	public void setTeacherPhno(String teacherPhno) {
		this.teacherPhno = teacherPhno;
	}
	public String getTeacherPost() {
		return teacherPost;
	}
	public void setTeacherPost(String teacherPost) {
		this.teacherPost = teacherPost;
	}
	
		
	
	

}
