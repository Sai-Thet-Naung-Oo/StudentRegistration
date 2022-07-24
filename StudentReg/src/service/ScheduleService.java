package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;

import model.Schedule;
import shared.mapper.ScheduleMapper;
import shared.utils.DateRangeValidator;

public class ScheduleService {

	private final DBConfig dbConfig;
	ScheduleMapper scheduleMapper;

	public ScheduleService() {
		dbConfig = new DBConfig();
		scheduleMapper = new ScheduleMapper();

	}

	public void createSchedule(Schedule schedule) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"INSERT INTO schedule (room_id, teacher_no, course_id, section_id, start_date,end_date,regristration_count,status) "
							+ "VALUES (?, ?, ?, ?, ?,?,?,?);");

			ps.setInt(1, schedule.getRoom().getRoom_id());
			ps.setInt(2, schedule.getTeacher().getTeacher_no());
			ps.setInt(3, schedule.getCourse().getCourse_id());
			ps.setInt(4, schedule.getSection().getSection_id());
			ps.setDate(5, java.sql.Date.valueOf(schedule.getStart_date()));
			ps.setDate(6, java.sql.Date.valueOf(schedule.getEnd_date()));
			ps.setInt(7, 0);
			ps.setInt(8, 1);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Already Exists");
			e.printStackTrace();
		}
	}

	public void updateSchedule(int id, Schedule schedule) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"UPDATE schedule SET room_id=?, teacher_no=?, course_id=?, section_id=?, start_date=?,end_date=?,regristration_count=?  WHERE schedule_id = ?");

			ps.setInt(1, schedule.getRoom().getRoom_id());
			ps.setInt(2, schedule.getTeacher().getTeacher_no());
			ps.setInt(3, schedule.getCourse().getCourse_id());
			ps.setInt(4, schedule.getSection().getSection_id());
			ps.setDate(5, java.sql.Date.valueOf(schedule.getStart_date()));
			ps.setDate(6, java.sql.Date.valueOf(schedule.getEnd_date()));
			ps.setInt(7, schedule.getRegristration_count());
			ps.setInt(8, id);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSchedule(int id) {
		try {
			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("UPDATE schedule SET status=0 WHERE schedule_id=?");

			ps.setInt(1, id);

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Schedule findScheduleById(String scheduleId) {
		Schedule schedule = new Schedule();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "SELECT * FROM schedule\n" + "INNER JOIN course\n"
					+ "ON course.course_id = schedule.course_id\n" + "INNER JOIN room\n"
					+ "ON room.room_id = schedule.room_id\n" + "INNER JOIN section\n"
					+ "ON section.section_id = schedule.section_id\n" + "INNER JOIN teacher\n"
					+ "ON teacher.teacher_no = schedule.teacher_no\n" + "WHERE schedule.schedule_id = " + scheduleId
					+ ";";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				schedule = this.scheduleMapper.mapToSchedule(schedule, rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return schedule;
	}

	public List<Schedule> findAllSchedules() {

		List<Schedule> scheduleList = new ArrayList<>();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "SELECT * FROM schedule\n" + "INNER JOIN course\n"
					+ "ON course.course_id = schedule.course_id\n" + "INNER JOIN room\n"
					+ "ON room.room_id = schedule.room_id\n" + "INNER JOIN section\n"
					+ "ON section.section_id = schedule.section_id\n" + "INNER JOIN teacher\n"
					+ "ON teacher.teacher_no = schedule.teacher_no\n" + "WHERE schedule.status=1 \n"
					+ "order by schedule_id;";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Schedule schedule = new Schedule();
				scheduleList.add(this.scheduleMapper.mapToSchedule(schedule, rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return scheduleList;
	}

	public boolean isTeacherBusy(LocalDate starttest_date, LocalDate endtest_date, String test_section,
			List<Schedule> teacherscehduleList) {

		boolean isTeacherBusyThisDate, isTeacherBusyThisSection;

		for (Schedule s : teacherscehduleList) {

			isTeacherBusyThisDate = DateRangeValidator.isWithinRange(starttest_date, s.getStart_date(), s.getEnd_date())
					|| DateRangeValidator.isWithinRange(endtest_date, s.getStart_date(), s.getEnd_date());

			isTeacherBusyThisSection = s.getSection().getSection_DayTime().equals(test_section);

			if (isTeacherBusyThisDate && isTeacherBusyThisSection) { // check date and section

				JOptionPane.showMessageDialog(null, "Teacher is busy this date and this section.");

				return true;

			}

		}

		return false;
	}

	public boolean isRoomBusy(LocalDate test_date, String test_section, List<Schedule> roomscehduleList) {

		boolean isRoomBusyThisDate, isRoomBusyThisSection;

		for (Schedule s : roomscehduleList) {

			isRoomBusyThisDate = DateRangeValidator.isWithinRange(test_date, s.getStart_date(), s.getEnd_date());
			isRoomBusyThisSection = s.getSection().getSection_DayTime().equals(test_section);

			if (isRoomBusyThisDate && isRoomBusyThisSection) { // check date and section

				JOptionPane.showMessageDialog(null, "Room is busy this date and this section.");
				return true;

			}

		}

		return false;
	}

}
