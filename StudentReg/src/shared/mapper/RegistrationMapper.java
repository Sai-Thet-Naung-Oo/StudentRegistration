package shared.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Course;
import model.Discount;
import model.Registration;
import model.Room;
import model.Schedule;
import model.Section;
import model.Staff;
import model.Student;
import model.Teacher;

public class RegistrationMapper {

	public Registration mapToSchedule(Registration registration, ResultSet rs) {
		try {

			Student student = new Student();
			student.setStdNo(rs.getInt("student_no"));
			student.setStdName(rs.getString("student_name"));
			student.setStdAddress(rs.getString("student_address"));
			student.setStdNRC(rs.getString("student_NRC"));
			student.setStdPhno(rs.getString("student_phone"));

			Staff staff = new Staff();
			staff.setStaffNo(rs.getInt("staff_no"));
			staff.setStaffName(rs.getString("staff_name"));
			staff.setStaffNRC(rs.getString("staff_address"));
			staff.setStdaffPhno(rs.getString("staff_phone"));

			Schedule schedule = new Schedule();
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

			Discount discount = new Discount();
			discount.setDiscount_id(rs.getInt("discount_id"));
			discount.setDiscountName(rs.getString("discount_name"));
			discount.setRate(rs.getInt("rate"));

			registration.setReg_no(rs.getInt("reg_no"));
			registration.setStudent(student);
			registration.setStaff(staff);
			registration.setSchedule(schedule);
			registration.setDiscount(discount);
			registration.setDate(rs.getDate("date").toLocalDate());
			registration.setTotalamount(rs.getInt("totalamount"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return registration;

	}

}
