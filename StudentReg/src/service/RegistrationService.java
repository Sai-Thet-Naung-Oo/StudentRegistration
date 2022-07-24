package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import model.Registration;
import model.Schedule;
import shared.mapper.RegistrationMapper;

public class RegistrationService {
	private final DBConfig dbConfig;
	RegistrationMapper registrationMapper;

	public RegistrationService() {
		// TODO Auto-generated constructor stub
		dbConfig = new DBConfig();
		registrationMapper = new RegistrationMapper();
	}

	public void createRegistration(Registration registration) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"INSERT INTO registration (student_no, schedule_id, staff_no, discount_id, date,totalamount) "
							+ "VALUES (?, ?, ?, ?, ?,?);");

			ps.setInt(1, registration.getStudent().getStdNo());
			ps.setInt(2, registration.getSchedule().getSchedule_id());
			ps.setInt(3, registration.getStaff().getStaffNo());
			ps.setInt(4, registration.getDiscount().getDiscount_id());
			ps.setDate(5, java.sql.Date.valueOf(registration.getDate()));
			ps.setInt(6, registration.getTotalamount());

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Already Exists");
			e.printStackTrace();
		}
	}

	public int getLatestRegistrationId() {

		int id = 0;
		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "SELECT reg_no FROM registration ORDER BY reg_no DESC";

			ResultSet rs = st.executeQuery(query);
			if(rs.next()) 
			id = rs.getInt("reg_no");
			else 
			id=0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	public List<Registration> findAllRegistrations() {

		List<Registration> registrationList = new ArrayList<>();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "SELECT * FROM registration\n" + "INNER JOIN student\n"
					+ "ON student.student_no = registration.student_no\n" + "INNER JOIN schedule\n"
					+ "ON schedule.schedule_id = registration.schedule_id\n" + "INNER JOIN staff\n"
					+ "ON staff.staff_no = registration.staff_no\n" + "INNER JOIN discount\n"
					+ "ON discount.discount_id = registration.discount_id\n" + "INNER JOIN course\n"
					+ "ON course.course_id = schedule.course_id\n" + "INNER JOIN room\n"
					+ "ON room.room_id = schedule.room_id\n" + "INNER JOIN section\n"
					+ "ON section.section_id = schedule.section_id\n" + "INNER JOIN teacher\n"
					+ "ON teacher.teacher_no = schedule.teacher_no\n" + "order by reg_no;";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Registration registration = new Registration();
				registrationList.add(this.registrationMapper.mapToSchedule(registration, rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return registrationList;
	}

	public void updateRegistration(int id, Registration registration) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"UPDATE registration SET student_no=?,schedule_id=?, staff_no=?, discount_id=?, date=?,totalamount=? WHERE reg_no = ?;");

		
			ps.setInt(1, registration.getStudent().getStdNo());
			ps.setInt(2, registration.getSchedule().getSchedule_id());
			ps.setInt(3, registration.getStaff().getStaffNo());
			ps.setInt(4, registration.getDiscount().getDiscount_id());
			ps.setDate(5, java.sql.Date.valueOf(registration.getDate()));
			ps.setInt(6, registration.getTotalamount());
			ps.setInt(7, id);
			

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
