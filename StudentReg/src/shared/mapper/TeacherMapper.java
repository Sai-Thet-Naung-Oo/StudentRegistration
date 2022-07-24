package shared.mapper;

import java.sql.ResultSet;

import model.Teacher;



public class TeacherMapper {

	
	public Teacher mapToTeacher(Teacher teacher, ResultSet rs) {
        try {
        	teacher.setTeacher_no(rs.getInt("teacher_no"));
        	teacher.setTeacherName(rs.getString("teacher_name"));
        	teacher.setTeacherAddress(rs.getString("teacher_address"));
        	teacher.setTeacherNRC(rs.getString("teacher_NRC"));
        	teacher.setTeacherPhno(rs.getString("teacher_phone"));
        	teacher.setTeacherPost(rs.getString("teacher_post"));
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    return teacher;
	}
}
