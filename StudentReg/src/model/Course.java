package model;

public class Course {

	int course_id;
	String courseName;
     int Price;
	
	public Course() {
		super();
	}
	public Course(int course_id, String courseName, int price) {
		super();
		this.course_id = course_id;
		this.courseName = courseName;
		Price = price;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	
}
