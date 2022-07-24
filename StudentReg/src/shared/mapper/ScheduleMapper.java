package shared.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Course;
import model.Room;
import model.Schedule;
import model.Section;
import model.Teacher;

public class ScheduleMapper {

	public Schedule mapToSchedule(Schedule schedule, ResultSet rs) {
		try {
			schedule.setSchedule_id(rs.getInt("schedule_id"));

			Room room = new Room();
			room.setRoom_id(rs.getInt("room_id"));
			room.setRoom_name(rs.getString("room_name"));
			room.setRoom_pc(rs.getInt("room_pc"));

			Teacher teacher = new Teacher();
			teacher.setTeacher_no(rs.getInt("teacher_no"));
			teacher.setTeacherName(rs.getString("teacher_name"));
			teacher.setTeacherAddress(rs.getString("teacher_address"));
			teacher.setTeacherPhno(rs.getString("teacher_phone"));
			teacher.setTeacherPost(rs.getString("teacher_post"));

			Course course = new Course();
			course.setCourse_id(rs.getInt("course_id"));
			course.setCourseName(rs.getString("course_name"));
			course.setPrice(rs.getInt("course_price"));

			Section section = new Section();
			section.setSection_id(rs.getInt("section_id"));
			section.setSection_DayTime(rs.getString("section_DayTime"));

			
			schedule.setCourse(course);
			schedule.setRoom(room);
			schedule.setSection(section);
			schedule.setTeacher(teacher);
			schedule.setStart_date(rs.getDate("start_date").toLocalDate());
			schedule.setEnd_date(rs.getDate("end_date").toLocalDate());
			schedule.setRegristration_count(rs.getInt("regristration_count"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return schedule;

	}

}
