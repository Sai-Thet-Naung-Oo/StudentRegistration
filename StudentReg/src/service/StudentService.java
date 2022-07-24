package service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import config.DBConfig;
import model.Student;
import shared.mapper.StudentMapper;

public class StudentService {
	
	private final DBConfig dbconfig;
	private final StudentMapper studentmapper;
	
	public  StudentService() {
		this.dbconfig=new DBConfig();
		this.studentmapper = new StudentMapper();
		
	}
	public void createStudent(Student student) {
		try {
			PreparedStatement ps=this.dbconfig.getConnection()
					.prepareStatement("INSERT INTO student (student_name,student_address,student_NRC,student_phone,status) VALUES (?,?,?,?,?);");
			ps.setString(1, student.getStdName());
			ps.setString(2, student.getStdAddress());
			ps.setString(3, student.getStdNRC());
			ps.setString(4, student.getStdPhno());
			ps.setInt(5, 1);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e) {
			if(e instanceof SQLException) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	   public List<Student> findAllStudent() {
	        List<Student> studentlist = new ArrayList<>();
	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM student WHERE status=1";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	Student student=new Student();
	            	studentlist.add(this.studentmapper.mapToStudent(student, rs));	
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return studentlist;
	    }

	    public Student findById(String id) {
	       Student student=new Student();

	        try (Statement st = this.dbconfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM student WHERE student_no = " + id + ";";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	this.studentmapper.mapToStudent(student, rs);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return student;
	    }
	    public void updateStudent(String id, Student student) {
		    try {
		        PreparedStatement ps = this.dbconfig.getConnection()
		                .prepareStatement("UPDATE student SET student_name=?,student_address=?,student_NRC=?,student_phone=? WHERE student_no=?");

		        ps.setString(1, student.getStdName());
		        ps.setString(2, student.getStdAddress());
		        ps.setString(3,student.getStdNRC());
		        ps.setString(4, student.getStdPhno());
		        ps.setString(5, id);

		        ps.executeUpdate();
		        ps.close();
		    } catch (Exception e) {

		    	e.printStackTrace();
		    }
		}
	    
	    public void deleteStudent(String id) {
	  	  try {
	  	        PreparedStatement ps = this.dbconfig.getConnection()
	  	                .prepareStatement("UPDATE student SET status=?  WHERE student_no=?");

	  	        ps.setInt(1, 0);
	  	        ps.setString(2, id);

	  	        ps.executeUpdate();
	  	        ps.close(); 
	  	    } catch (Exception e) {

	  	    	e.printStackTrace();
	  	    }
	  }

}
