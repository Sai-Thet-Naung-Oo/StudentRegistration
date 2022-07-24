package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import config.DBConfig;
import model.Course;
import model.StaffInfo;
import shared.mapper.CourseMapper;

public class CourseService {
	private DBConfig dbconfig;
	private CourseMapper coursemapper;
	
	public CourseService() {
		this.dbconfig=new DBConfig();
		this.coursemapper=new CourseMapper();
	}
	
	public void createCourse(Course course) {
		try {
			PreparedStatement ps=this.dbconfig.getConnection()
					.prepareStatement("INSERT INTO course (course_name,course_price,status) VALUES (?,?,?);");
			ps.setString(1, course.getCourseName());
			ps.setInt(2,course.getPrice());
			ps.setInt(3,1);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e) {
			if(e instanceof SQLException) {
				e.printStackTrace();
			}
		}

}
	
	 public List<Course> findAllCourse() {
	        List<Course> courselist = new ArrayList<>();
	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM course WHERE status=1";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	Course course=new Course();
	            	courselist.add(this.coursemapper.mapToCourse(course, rs));	
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return courselist;
	    }

	    public Course   findById(String id) {
	    	 Course course=new Course();
	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM course WHERE course_id = " + id + ";";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	
	            	this.coursemapper.mapToCourse(course, rs);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        
			return course;
	    }
	    
	    public void updateCourse(String id, Course course) {
		    try {
		        PreparedStatement ps = this.dbconfig.getConnection()
		                .prepareStatement("UPDATE course SET course_name=?,course_price=? WHERE course_id=?");

		        ps.setString(1, course.getCourseName());
		        ps.setInt(2, course.getPrice());
		       
		        ps.setString(3, id);

		        ps.executeUpdate();
		        ps.close();
		    } catch (Exception e) {

		    	e.printStackTrace();
		    }
		}
	    
	    public void deleteCourse(String id) {
	  	  try {
	  	        PreparedStatement ps = this.dbconfig.getConnection()
	  	                .prepareStatement("UPDATE course SET status=? WHERE course_id=?");

	  	        ps.setInt(1,0);
	  	        ps.setString(2, id);

	  	        ps.executeUpdate();
	  	        ps.close();
	  	    } catch (Exception e) {

	  	    	e.printStackTrace();
	  	    }
	  }

}
