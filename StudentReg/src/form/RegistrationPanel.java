package form;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import com.toedter.calendar.JDateChooser;

import model.Course;
import model.Discount;
import model.Registration;
import model.Schedule;
import model.Staff;
import model.Student;
import service.DiscountService;
import service.RegistrationService;
import service.ScheduleService;
import service.StudentService;
import shared.utils.CurrentUserHolder;
import shared.utils.DateUtils;
import shared.utils.IDkeyGenerator;

import javax.imageio.spi.RegisterableService;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.beans.PropertyChangeEvent;

public class RegistrationPanel extends JPanel {
	private JTextField txtRegID;
	private JTextField txtStudentName;
	private JTextField txtStudentNRC;
	private JTextField txtStudentAddress;
	private JTextField txtStudentPhone;
	private JTextField txtTeacherName;
	private JTextField txtCourseName;
	private JTextField txtRoom;
	private JTextField txtSection;
	private JTextField txtStartDate;
	private JTextField txtEndDate;
	private JTextField txtCoursePrice;
	private JTextField txtTotalAmount;
	private JTextField txtTotalPC;
	private JTextField txtREGCount;

	// service
	StudentService studentService;
	ScheduleService scheduleService;
	DiscountService discountService;
	RegistrationService registrationService;

	JComboBox<String> studentNum_combo;
	JComboBox<String> scheduleID_combo;
	JComboBox<String> discount_combo;

	List<Student> studentList = new ArrayList<Student>();
	List<Schedule> scheduleList = new ArrayList<Schedule>();
	List<Discount> discountList = new ArrayList<Discount>();
	List<Registration> registrationList = new ArrayList<Registration>();
	Discount selectedDiscount;
	Schedule selectedSchedule;
	Student selectedStudent;
	
	JLabel txtOldNewStudent;

	/**
	 * Create the panel.
	 */

	public void resetStudentInformationForm() {
		txtStudentName.setText(null);
		txtStudentNRC.setText(null);
		txtStudentAddress.setText(null);
		txtStudentPhone.setText(null);
		txtOldNewStudent.setText(null);

	}

	public void resetCourseInformationForm() {

		txtTeacherName.setText(null);
		txtCourseName.setText(null);
		txtRoom.setText(null);
		txtStartDate.setText(null);
		txtTotalPC.setText(null);
		txtREGCount.setText(null);
		txtSection.setText(null);
		txtEndDate.setText(null);
		txtCoursePrice.setText(null);

	}

	public void resetRegistrationForm() {
		studentNum_combo.setSelectedIndex(0);
		scheduleID_combo.setSelectedIndex(0);
		txtCoursePrice.setText(null);
		discount_combo.setEnabled(false);
		txtTotalAmount.setText(null);

	}

	public void loadStudentNumberForComboBox() {
		studentNum_combo.removeAllItems();

		studentNum_combo.addItem("- Select -");
		studentList = this.studentService.findAllStudent();
		studentList.forEach(s -> this.studentNum_combo.addItem(IDkeyGenerator.idToString("ST-", s.getStdNo())));

	}

	public void loadDiscountForComboBox() {
		discount_combo.removeAllItems();

		discount_combo.addItem("- Select -");
		discountList = this.discountService.findAllDiscount();
		discountList.forEach(s -> this.discount_combo.addItem(String.valueOf(s.getDiscountName())));

	}

	public void loadScheduleIDForComboBox() {
		scheduleID_combo.removeAllItems();
		scheduleID_combo.addItem("- Select -");

		LocalDate today = DateUtils.asLocalDate(new Date());
		scheduleList = scheduleService.findAllSchedules().stream().filter(s -> {
			return today.isBefore(s.getStart_date()) || today.isEqual(s.getStart_date()); // Before Schedule Start Date
		}).collect(Collectors.toList());

		scheduleList.forEach(s -> this.scheduleID_combo.addItem(IDkeyGenerator.idToString("SC-", s.getSchedule_id())));

	}

	public void initialize() {

		studentService = new StudentService();
		scheduleService = new ScheduleService();
		discountService = new DiscountService();
		registrationService = new RegistrationService();

		setBackground(new Color(173, 216, 230));
		setBounds(0, 0, 1300, 700);
		setLayout(null);

		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setBounds(536, 36, 227, 45);
		lblRegistration.setForeground(new Color(138, 43, 226));
		lblRegistration.setFont(new Font("Myanmar Text", Font.BOLD, 32));
		add(lblRegistration);

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBounds(686, 136, 571, 319);
		add(panel_2_1);

		JLabel lblCourseInformation = new JLabel("Course Information");
		lblCourseInformation.setForeground(new Color(138, 43, 226));
		lblCourseInformation.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblCourseInformation.setBounds(194, 10, 183, 29);
		panel_2_1.add(lblCourseInformation);

		JPanel panel_4_5 = new JPanel();
		panel_4_5.setLayout(null);
		panel_4_5.setBounds(12, 57, 258, 52);
		panel_2_1.add(panel_4_5);

		JLabel lblNewLabel_1_4_5 = new JLabel("Teacher Name");
		lblNewLabel_1_4_5.setForeground(Color.BLACK);
		lblNewLabel_1_4_5.setBounds(0, 13, 98, 17);
		panel_4_5.add(lblNewLabel_1_4_5);

		txtTeacherName = new JTextField();
		txtTeacherName.setEditable(false);
		txtTeacherName.setColumns(10);
		txtTeacherName.setBounds(98, 6, 160, 31);
		panel_4_5.add(txtTeacherName);

		JSeparator separator_4_5 = new JSeparator();
		separator_4_5.setBounds(-6, 50, 264, 2);
		panel_4_5.add(separator_4_5);

		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setLayout(null);
		panel_4_1_1.setBounds(12, 119, 258, 52);
		panel_2_1.add(panel_4_1_1);

		JLabel lblNewLabel_1_4_1_1 = new JLabel("Course Name");
		lblNewLabel_1_4_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_1_1.setBounds(0, 13, 98, 17);
		panel_4_1_1.add(lblNewLabel_1_4_1_1);

		txtCourseName = new JTextField();
		txtCourseName.setEditable(false);
		txtCourseName.setColumns(10);
		txtCourseName.setBounds(98, 6, 160, 31);
		panel_4_1_1.add(txtCourseName);

		JSeparator separator_4_1_1 = new JSeparator();
		separator_4_1_1.setBounds(-6, 50, 264, 2);
		panel_4_1_1.add(separator_4_1_1);

		JPanel panel_4_2_1 = new JPanel();
		panel_4_2_1.setLayout(null);
		panel_4_2_1.setBounds(12, 181, 258, 52);
		panel_2_1.add(panel_4_2_1);

		JLabel lblNewLabel_1_4_2_1 = new JLabel("Room");
		lblNewLabel_1_4_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_2_1.setBounds(0, 13, 98, 17);
		panel_4_2_1.add(lblNewLabel_1_4_2_1);

		txtRoom = new JTextField();
		txtRoom.setEditable(false);
		txtRoom.setColumns(10);
		txtRoom.setBounds(98, 6, 160, 31);
		panel_4_2_1.add(txtRoom);

		JSeparator separator_4_2_1 = new JSeparator();
		separator_4_2_1.setBounds(-6, 50, 264, 2);
		panel_4_2_1.add(separator_4_2_1);

		JPanel panel_4_3_1 = new JPanel();
		panel_4_3_1.setLayout(null);
		panel_4_3_1.setBounds(295, 181, 258, 52);
		panel_2_1.add(panel_4_3_1);

		JLabel lblNewLabel_1_4_3_1 = new JLabel("Section");
		lblNewLabel_1_4_3_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_3_1.setBounds(0, 13, 98, 17);
		panel_4_3_1.add(lblNewLabel_1_4_3_1);

		txtSection = new JTextField();
		txtSection.setEditable(false);
		txtSection.setColumns(10);
		txtSection.setBounds(98, 6, 160, 31);
		panel_4_3_1.add(txtSection);

		JSeparator separator_4_3_1 = new JSeparator();
		separator_4_3_1.setBounds(-6, 50, 264, 2);
		panel_4_3_1.add(separator_4_3_1);

		JPanel panel_4_4_2 = new JPanel();
		panel_4_4_2.setLayout(null);
		panel_4_4_2.setBounds(12, 243, 258, 52);
		panel_2_1.add(panel_4_4_2);

		JLabel lblNewLabel_1_4_4_2 = new JLabel("Start Date");
		lblNewLabel_1_4_4_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_2.setBounds(0, 13, 98, 17);
		panel_4_4_2.add(lblNewLabel_1_4_4_2);

		txtStartDate = new JTextField();
		txtStartDate.setEditable(false);
		txtStartDate.setColumns(10);
		txtStartDate.setBounds(98, 6, 160, 31);
		panel_4_4_2.add(txtStartDate);

		JSeparator separator_4_4_2 = new JSeparator();
		separator_4_4_2.setBounds(-6, 50, 264, 2);
		panel_4_4_2.add(separator_4_4_2);

		JPanel panel_4_4_1_1 = new JPanel();
		panel_4_4_1_1.setLayout(null);
		panel_4_4_1_1.setBounds(295, 243, 258, 52);
		panel_2_1.add(panel_4_4_1_1);

		JLabel lblNewLabel_1_4_4_1_1 = new JLabel("End Date");
		lblNewLabel_1_4_4_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_1_1.setBounds(0, 13, 98, 17);
		panel_4_4_1_1.add(lblNewLabel_1_4_4_1_1);

		txtEndDate = new JTextField();
		txtEndDate.setEditable(false);
		txtEndDate.setColumns(10);
		txtEndDate.setBounds(98, 6, 160, 31);
		panel_4_4_1_1.add(txtEndDate);

		JSeparator separator_4_4_1_1 = new JSeparator();
		separator_4_4_1_1.setBounds(-6, 50, 264, 2);
		panel_4_4_1_1.add(separator_4_4_1_1);

		JPanel panel_4_5_1 = new JPanel();
		panel_4_5_1.setLayout(null);
		panel_4_5_1.setBounds(295, 57, 258, 52);
		panel_2_1.add(panel_4_5_1);

		JLabel lblNewLabel_1_4_5_1 = new JLabel("Total PC");
		lblNewLabel_1_4_5_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_1.setBounds(0, 13, 98, 17);
		panel_4_5_1.add(lblNewLabel_1_4_5_1);

		txtTotalPC = new JTextField();
		txtTotalPC.setEditable(false);
		txtTotalPC.setColumns(10);
		txtTotalPC.setBounds(98, 6, 160, 31);
		panel_4_5_1.add(txtTotalPC);

		JSeparator separator_4_5_1 = new JSeparator();
		separator_4_5_1.setBounds(-6, 50, 264, 2);
		panel_4_5_1.add(separator_4_5_1);

		JPanel panel_4_5_2 = new JPanel();
		panel_4_5_2.setLayout(null);
		panel_4_5_2.setBounds(295, 119, 258, 52);
		panel_2_1.add(panel_4_5_2);

		JLabel lblNewLabel_1_4_5_2 = new JLabel("REG Count");
		lblNewLabel_1_4_5_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_2.setBounds(0, 13, 98, 24);
		panel_4_5_2.add(lblNewLabel_1_4_5_2);

		txtREGCount = new JTextField();
		txtREGCount.setEditable(false);
		txtREGCount.setColumns(10);
		txtREGCount.setBounds(98, 6, 160, 31);
		panel_4_5_2.add(txtREGCount);

		JSeparator separator_4_5_2 = new JSeparator();
		separator_4_5_2.setBounds(-6, 50, 264, 2);
		panel_4_5_2.add(separator_4_5_2);

		JPanel panel = new JPanel();
		panel.setBounds(42, 136, 305, 456);
		add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 63, 282, 388);
		panel.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setBorder(null);

		scheduleID_combo = new JComboBox();
		scheduleID_combo.setMaximumRowCount(6);
		AutoCompleteDecorator.decorate(scheduleID_combo);

		scheduleID_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (scheduleID_combo.getSelectedIndex() != 0 && scheduleID_combo.getSelectedIndex() != -1) {

					resetCourseInformationForm();
					selectedSchedule = scheduleList.stream()
							.filter(s -> s.getSchedule_id() == IDkeyGenerator
									.stringToID(scheduleID_combo.getSelectedItem().toString()))
							.findFirst().orElse(null);

					txtTeacherName.setText(selectedSchedule.getTeacher().getTeacherName());
					txtCourseName.setText(selectedSchedule.getCourse().getCourseName());
					txtRoom.setText(selectedSchedule.getRoom().getRoom_name());
					txtStartDate.setText(selectedSchedule.getStart_date().toString());
					txtTotalPC.setText(String.valueOf(selectedSchedule.getRoom().getRoom_pc()));
					txtREGCount.setText(String.valueOf(selectedSchedule.getRegristration_count()));
					txtSection.setText(selectedSchedule.getSection().getSection_DayTime());
					txtEndDate.setText(selectedSchedule.getEnd_date().toString());
					txtCoursePrice.setText(String.valueOf(selectedSchedule.getCourse().getPrice()));
					discount_combo.setEnabled(true);
					discount_combo.setSelectedIndex(0);
					txtTotalAmount.setText(null);
				}

			}
		});
		scheduleID_combo.setBounds(120, 118, 154, 30);
		panel_1.add(scheduleID_combo);

		JLabel lblNewLabel_1_1 = new JLabel("Schedule ID");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setBounds(10, 125, 98, 17);
		panel_1.add(lblNewLabel_1_1);

		studentNum_combo = new JComboBox<String>();
		studentNum_combo.setMaximumRowCount(6);
		studentNum_combo.setBounds(121, 65, 153, 30);
		AutoCompleteDecorator.decorate(studentNum_combo);
		panel_1.add(studentNum_combo);

		studentNum_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (studentNum_combo.getSelectedIndex() != 0 && studentNum_combo.getSelectedIndex() != -1) {

					resetStudentInformationForm();

					selectedStudent = studentList.stream()
							.filter(s -> s.getStdNo() == IDkeyGenerator
									.stringToID(studentNum_combo.getSelectedItem().toString()))
							.findFirst().orElse(null);
				
					txtStudentName.setText(selectedStudent.getStdName());
					txtStudentNRC.setText(selectedStudent.getStdNRC());
					txtStudentAddress.setText(selectedStudent.getStdAddress());
					txtStudentPhone.setText(selectedStudent.getStdPhno());
					
					for (Registration registration : registrationList) {
						if(registration.getStudent().getStdNo()==selectedStudent.getStdNo())
						{
							txtOldNewStudent.setText("Old Student");
							return;
						}
						else 
							txtOldNewStudent.setText("New Student");
						
						
					}
					

				}

			}
		});

		JLabel lblNewLabel_1_3 = new JLabel("Student Number");
		lblNewLabel_1_3.setForeground(Color.BLACK);
		lblNewLabel_1_3.setBounds(10, 72, 105, 17);
		panel_1.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1 = new JLabel("Registration ID");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBounds(10, 18, 98, 17);
		panel_1.add(lblNewLabel_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 52, 264, 2);
		panel_1.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 106, 264, 2);
		panel_1.add(separator_1);

		txtRegID = new JTextField();
		txtRegID.setEditable(false);
		txtRegID.setBounds(119, 10, 153, 31);

		panel_1.add(txtRegID);
		txtRegID.setColumns(10);

		discount_combo = new JComboBox();
		discount_combo.setEnabled(false);
		discount_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (discount_combo.getSelectedIndex() != 0 && discount_combo.getSelectedIndex() != -1) {

					int coursePrice = Integer.parseInt(txtCoursePrice.getText());

					selectedDiscount = discountList.stream()
							.filter(s -> s.getDiscountName().equals(discount_combo.getSelectedItem())).findFirst()
							.orElse(null);
					coursePrice -= (coursePrice) * selectedDiscount.getRate() / 100;
					txtTotalAmount.setText(String.valueOf(coursePrice));

				}

			}
		});
		discount_combo.setBounds(120, 218, 154, 30);
		panel_1.add(discount_combo);
		discount_combo.setMaximumRowCount(5);

		JLabel lblNewLabel_1_2 = new JLabel("Discount");
		lblNewLabel_1_2.setBounds(10, 225, 98, 17);
		panel_1.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setForeground(Color.BLACK);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (studentNum_combo.getSelectedIndex() == 0 || scheduleID_combo.getSelectedIndex() == 0
						|| discount_combo.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Required All Field.");
					return;
				}

				if (selectedSchedule.getRoom().getRoom_pc() > selectedSchedule.getRegristration_count()) {
					Registration registration = new Registration();

				
					registration.setStudent(selectedStudent);
					registration.setSchedule(selectedSchedule);
					registration.setDiscount(selectedDiscount);
					registration.setStaff(CurrentUserHolder.getCurrentUser());
					registration.setDate(DateUtils.asLocalDate(new Date()));
					registration.setTotalamount(Integer.parseInt(txtTotalAmount.getText()));

					registrationService.createRegistration(registration);

					JOptionPane.showMessageDialog(null, "Succefully Register");

					selectedSchedule.setRegristration_count(selectedSchedule.getRegristration_count() + 1);

					scheduleService.updateSchedule(selectedSchedule.getSchedule_id(), selectedSchedule);
					dataRefresh();

				} else {
					JOptionPane.showMessageDialog(null, "Can't Register,Course is Full...");
				}

			}
		});
		btnNewButton.setBounds(6, 335, 268, 38);
		panel_1.add(btnNewButton);
		btnNewButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 158, 264, 2);
		panel_1.add(separator_2);

		JLabel lblNewLabel_1_1_1 = new JLabel("Course Price");
		lblNewLabel_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1.setBounds(10, 176, 98, 17);
		panel_1.add(lblNewLabel_1_1_1);

		txtCoursePrice = new JTextField();

		txtCoursePrice.setEditable(false);
		txtCoursePrice.setColumns(10);
		txtCoursePrice.setBounds(120, 169, 153, 31);
		panel_1.add(txtCoursePrice);

		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setBounds(10, 208, 264, 2);
		panel_1.add(separator_2_1);

		JSeparator separator_2_2 = new JSeparator();
		separator_2_2.setBounds(10, 258, 264, 2);
		panel_1.add(separator_2_2);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Total Amount");
		lblNewLabel_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1_1.setBounds(10, 277, 98, 17);
		panel_1.add(lblNewLabel_1_1_1_1);

		txtTotalAmount = new JTextField();
		txtTotalAmount.setEditable(false);
		txtTotalAmount.setColumns(10);
		txtTotalAmount.setBounds(119, 270, 153, 31);
		panel_1.add(txtTotalAmount);

		JSeparator separator_2_2_1 = new JSeparator();
		separator_2_2_1.setBounds(10, 314, 264, 2);
		panel_1.add(separator_2_2_1);

		JLabel lblR = new JLabel("Registration Form");
		lblR.setForeground(new Color(138, 43, 226));
		lblR.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblR.setBounds(73, 10, 159, 29);
		panel.add(lblR);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(369, 136, 305, 319);
		add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 53, 282, 258);
		panel_3.add(panel_2);
		panel_2.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(12, 10, 258, 52);
		panel_2.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblNewLabel_1_4 = new JLabel("Student Name");
		lblNewLabel_1_4.setForeground(Color.BLACK);
		lblNewLabel_1_4.setBounds(0, 13, 98, 17);
		panel_4.add(lblNewLabel_1_4);

		txtStudentName = new JTextField();
		txtStudentName.setEditable(false);
		txtStudentName.setColumns(10);
		txtStudentName.setBounds(98, 6, 160, 31);
		panel_4.add(txtStudentName);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(-6, 50, 264, 2);
		panel_4.add(separator_4);

		JPanel panel_4_1 = new JPanel();
		panel_4_1.setLayout(null);
		panel_4_1.setBounds(12, 72, 258, 52);
		panel_2.add(panel_4_1);

		JLabel lblNewLabel_1_4_1 = new JLabel("Student NRC");
		lblNewLabel_1_4_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_1.setBounds(0, 13, 98, 17);
		panel_4_1.add(lblNewLabel_1_4_1);

		txtStudentNRC = new JTextField();
		txtStudentNRC.setEditable(false);
		txtStudentNRC.setColumns(10);
		txtStudentNRC.setBounds(98, 6, 160, 31);
		panel_4_1.add(txtStudentNRC);

		JSeparator separator_4_1 = new JSeparator();
		separator_4_1.setBounds(-6, 50, 264, 2);
		panel_4_1.add(separator_4_1);

		JPanel panel_4_2 = new JPanel();
		panel_4_2.setLayout(null);
		panel_4_2.setBounds(12, 134, 258, 52);
		panel_2.add(panel_4_2);

		JLabel lblNewLabel_1_4_2 = new JLabel("Student Address");
		lblNewLabel_1_4_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_2.setBounds(0, 13, 98, 17);
		panel_4_2.add(lblNewLabel_1_4_2);

		txtStudentAddress = new JTextField();
		txtStudentAddress.setEditable(false);
		txtStudentAddress.setColumns(10);
		txtStudentAddress.setBounds(98, 6, 160, 31);
		panel_4_2.add(txtStudentAddress);

		JSeparator separator_4_2 = new JSeparator();
		separator_4_2.setBounds(-6, 50, 264, 2);
		panel_4_2.add(separator_4_2);

		JPanel panel_4_3 = new JPanel();
		panel_4_3.setLayout(null);
		panel_4_3.setBounds(12, 196, 258, 52);
		panel_2.add(panel_4_3);

		JLabel lblNewLabel_1_4_3 = new JLabel("Student Phone");
		lblNewLabel_1_4_3.setForeground(Color.BLACK);
		lblNewLabel_1_4_3.setBounds(0, 13, 98, 17);
		panel_4_3.add(lblNewLabel_1_4_3);

		txtStudentPhone = new JTextField();
		txtStudentPhone.setEditable(false);
		txtStudentPhone.setColumns(10);
		txtStudentPhone.setBounds(98, 6, 160, 31);
		panel_4_3.add(txtStudentPhone);

		JSeparator separator_4_3 = new JSeparator();
		separator_4_3.setBounds(-6, 50, 264, 2);
		panel_4_3.add(separator_4_3);

		JLabel lblStudentInformation = new JLabel("Student Information");
		lblStudentInformation.setForeground(new Color(138, 43, 226));
		lblStudentInformation.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblStudentInformation.setBounds(59, 10, 177, 29);
		panel_3.add(lblStudentInformation);
		
		txtOldNewStudent = new JLabel("1");
		txtOldNewStudent.setForeground(Color.BLACK);
		txtOldNewStudent.setBounds(12, 32, 117, 17);
		panel_3.add(txtOldNewStudent);

		JLabel txtTodayDate = new JLabel("Today DATE ");
		txtTodayDate.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		txtTodayDate.setBounds(156, 96, 113, 30);
		txtTodayDate.setText(DateUtils.asLocalDate(new Date()).toString());
		add(txtTodayDate);

		JLabel lblNewLabel = new JLabel("Today Date");
		lblNewLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		lblNewLabel.setBounds(42, 102, 102, 19);
		add(lblNewLabel);

	}

	public RegistrationPanel() {
		initialize();

		dataRefresh();

	}

	public void dataRefresh() {

		loadStudentNumberForComboBox(); // select from database,data need to refresh
		loadScheduleIDForComboBox();
		loadDiscountForComboBox();
		resetCourseInformationForm();
		resetStudentInformationForm();
		resetRegistrationForm();
		txtRegID.setText(IDkeyGenerator.idToString("RG-", registrationService.getLatestRegistrationId() + 1));
		registrationList = registrationService.findAllRegistrations();
		
	}
}
