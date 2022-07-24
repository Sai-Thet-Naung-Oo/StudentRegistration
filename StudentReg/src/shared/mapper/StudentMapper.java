package shared.mapper;

import java.sql.ResultSet;

import model.Student;



public class StudentMapper {
	
	public Student mapToStudent(Student student, ResultSet rs) {
        try {
        	student.setStdNo(rs.getInt("student_no")); 
        	student.setStdName(rs.getString("student_name"));
        	student.setStdAddress(rs.getString("student_address"));
        	student.setStdNRC(rs.getString("student_NRC"));
        	student.setStdPhno(rs.getString("student_phone"));
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    return student;
	}
}
