package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import config.DBConfig;
import model.Student;
import model.Teacher;
import shared.mapper.TeacherMapper;

public class TeacherService {
	
	private final DBConfig dbconfig;
	private final TeacherMapper teachermapper;
	public  TeacherService() {
		this.dbconfig = new DBConfig();
		this.teachermapper = new TeacherMapper();
		
	}
	public void createTeacher(Teacher teacher) {
		try {
			PreparedStatement ps=this.dbconfig.getConnection()
					.prepareStatement("INSERT INTO teacher (teacher_name,teacher_address,teacher_NRC,teacher_phone,teacher_post,status) VALUES (?,?,?,?,?,?);");
			ps.setString(1, teacher.getTeacherName());
			ps.setString(2, teacher.getTeacherAddress());
			ps.setString(3, teacher.getTeacherNRC());
			ps.setString(4, teacher.getTeacherPhno());
			ps.setString(5, teacher.getTeacherPost());
			ps.setInt(6, 1);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e) {
			if(e instanceof SQLException) {
				e.printStackTrace();
			}
		}
		
	}
	   public List<Teacher> findAllTeacher() {
	        List<Teacher> teacherlist = new ArrayList<>();
	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM teacher WHERE status=1";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	Teacher teacher =new Teacher();
	            	teacherlist.add(this.teachermapper.mapToTeacher(teacher, rs));	
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return teacherlist;
	    }
	   
	   public Teacher findById(String id) {
	       Teacher teacher=new Teacher();

	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM teacher WHERE teacher_no = " + id + ";";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	
	            	this.teachermapper.mapToTeacher(teacher, rs);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return teacher;
	    }
	   
	   public void deleteTeacher(String id) {
		  	  try {
		  	        PreparedStatement ps = this.dbconfig.getConnection()
		  	                .prepareStatement("UPDATE teacher SET  status=? WHERE teacher_no=?");

		  	        ps.setInt(1, 0);
		  	        ps.setString(2, id);

		  	        ps.executeUpdate();
		  	        ps.close();
		  	    } catch (Exception e) {

		  	    	e.printStackTrace();
		  	    }
		  }
	   public void updateTeacher(String id, Teacher teacher) {
		    try {
		        PreparedStatement ps = this.dbconfig.getConnection()
		                .prepareStatement("UPDATE teacher SET teacher_name=?,teacher_address=?,teacher_NRC=?,teacher_phone=?,teacher_post=? WHERE teacher_no=?");

		        ps.setString(1,teacher.getTeacherName());
		        ps.setString(2, teacher.getTeacherAddress());
		        ps.setString(3,teacher.getTeacherNRC());
		        ps.setString(4, teacher.getTeacherPhno());
		        ps.setString(5, teacher.getTeacherPost());
		        ps.setString(6, id);

		        ps.executeUpdate();
		        ps.close();
		    } catch (Exception e) {

		    	e.printStackTrace();
		    }
		}

}
