package form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Registration;
import shared.utils.IDkeyGenerator;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Font;

public class RegistrationDetailForm extends JFrame {
	JLabel date;
	private JPanel contentPane;
	private JTextField txtStudentName;
	private JTextField txtStudentNRC;
	private JTextField txtStudentAddress;
	private JTextField txtStudentPhoneNumber;
	private JTextField txtRegistrationNumber;
	private JTextField txtStaffName;
	private JTextField txtDiscountType;
	private JTextField txtTotalAmount;
	private JTextField txtTeacherName;
	private JTextField txtCourseName;
	private JTextField txtRoom;
	private JTextField txtSection;
	private JTextField txtStartDate;
	private JTextField txtEndDate;
	private JTextField txtTotalPC;
	private JTextField txtRegistrationCount;
	private JTextField txtStudentNumber;
	private JTextField txtScheduleID;



	

	public RegistrationDetailForm(Registration registration) {
		initialize();

		txtStudentName.setText(registration.getStudent().getStdName());
		txtStudentNRC.setText(registration.getStudent().getStdNRC());
		txtStudentAddress.setText(registration.getStudent().getStdAddress());
		txtStudentPhoneNumber.setText(registration.getStudent().getStdPhno());
		txtRegistrationNumber.setText(IDkeyGenerator.idToString("RG-", registration.getReg_no()));
		txtStaffName.setText(registration.getStaff().getStaffName());
		txtDiscountType.setText(registration.getDiscount().getDiscountName());
		txtTotalAmount.setText(String.valueOf(registration.getTotalamount()));
		txtTeacherName.setText(registration.getSchedule().getTeacher().getTeacherName());
		txtCourseName.setText(registration.getSchedule().getCourse().getCourseName());
		txtRoom.setText(registration.getSchedule().getRoom().getRoom_name());
		txtSection.setText(registration.getSchedule().getSection().getSection_DayTime());
		txtStartDate.setText(registration.getSchedule().getStart_date().toString());
		txtEndDate.setText(registration.getSchedule().getEnd_date().toString());
		txtTotalPC.setText(String.valueOf(registration.getSchedule().getRoom().getRoom_pc()));
		txtRegistrationCount.setText(String.valueOf(registration.getSchedule().getRegristration_count()));
		date.setText(registration.getDate().toString());
		txtStudentNumber.setText(IDkeyGenerator.idToString("ST-", registration.getStudent().getStdNo()));
		txtScheduleID.setText(IDkeyGenerator.idToString("SC-", registration.getSchedule().getSchedule_id()));
	}

	public RegistrationDetailForm() {
		initialize();
	}

	public void initialize() {
		setFont(new Font("Dialog", Font.BOLD, 22));
		setTitle("Registration Detail");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(160, 100, 1000, 600);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(173, 216, 230));
		panel_3.setLayout(null);
		panel_3.setBounds(53, 186, 313, 365);
		contentPane.add(panel_3);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(173, 216, 230));
		panel_2.setLayout(null);
		panel_2.setBounds(12, 49, 282, 306);
		panel_3.add(panel_2);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(12, 58, 258, 52);
		panel_2.add(panel_4);

		JLabel lblNewLabel_1_4 = new JLabel("Student Name");
		lblNewLabel_1_4.setForeground(Color.BLACK);
		lblNewLabel_1_4.setBounds(0, 13, 98, 17);
		panel_4.add(lblNewLabel_1_4);

		txtStudentName = new JTextField();
		txtStudentName.setText((String) null);
		txtStudentName.setEditable(false);
		txtStudentName.setColumns(10);
		txtStudentName.setBounds(98, 6, 160, 31);
		panel_4.add(txtStudentName);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(-6, 50, 264, 2);
		panel_4.add(separator_4);

		JPanel panel_4_1 = new JPanel();
		panel_4_1.setLayout(null);
		panel_4_1.setBounds(12, 120, 258, 52);
		panel_2.add(panel_4_1);

		JLabel lblNewLabel_1_4_1 = new JLabel("Student NRC");
		lblNewLabel_1_4_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_1.setBounds(0, 13, 98, 17);
		panel_4_1.add(lblNewLabel_1_4_1);

		txtStudentNRC = new JTextField();
		txtStudentNRC.setText((String) null);
		txtStudentNRC.setEditable(false);
		txtStudentNRC.setColumns(10);
		txtStudentNRC.setBounds(98, 6, 160, 31);
		panel_4_1.add(txtStudentNRC);

		JSeparator separator_4_1 = new JSeparator();
		separator_4_1.setBounds(-6, 50, 264, 2);
		panel_4_1.add(separator_4_1);

		JPanel panel_4_2 = new JPanel();
		panel_4_2.setLayout(null);
		panel_4_2.setBounds(12, 182, 258, 52);
		panel_2.add(panel_4_2);

		JLabel lblNewLabel_1_4_2 = new JLabel("Student Address");
		lblNewLabel_1_4_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_2.setBounds(0, 13, 98, 17);
		panel_4_2.add(lblNewLabel_1_4_2);

		txtStudentAddress = new JTextField();
		txtStudentAddress.setText((String) null);
		txtStudentAddress.setEditable(false);
		txtStudentAddress.setColumns(10);
		txtStudentAddress.setBounds(98, 6, 160, 31);
		panel_4_2.add(txtStudentAddress);

		JSeparator separator_4_2 = new JSeparator();
		separator_4_2.setBounds(-6, 50, 264, 2);
		panel_4_2.add(separator_4_2);

		JPanel panel_4_3 = new JPanel();
		panel_4_3.setLayout(null);
		panel_4_3.setBounds(12, 244, 258, 52);
		panel_2.add(panel_4_3);

		JLabel lblNewLabel_1_4_3 = new JLabel("Student Phone");
		lblNewLabel_1_4_3.setForeground(Color.BLACK);
		lblNewLabel_1_4_3.setBounds(0, 13, 98, 17);
		panel_4_3.add(lblNewLabel_1_4_3);

		txtStudentPhoneNumber = new JTextField();
		txtStudentPhoneNumber.setText((String) null);
		txtStudentPhoneNumber.setEditable(false);
		txtStudentPhoneNumber.setColumns(10);
		txtStudentPhoneNumber.setBounds(98, 6, 160, 31);
		panel_4_3.add(txtStudentPhoneNumber);

		JSeparator separator_4_3 = new JSeparator();
		separator_4_3.setBounds(-6, 50, 264, 2);
		panel_4_3.add(separator_4_3);

		JPanel panel_4_6 = new JPanel();
		panel_4_6.setLayout(null);
		panel_4_6.setBounds(12, 0, 258, 52);
		panel_2.add(panel_4_6);

		JLabel lblNewLabel_1_4_7 = new JLabel("Student Number");
		lblNewLabel_1_4_7.setForeground(Color.BLACK);
		lblNewLabel_1_4_7.setBounds(0, 13, 98, 17);
		panel_4_6.add(lblNewLabel_1_4_7);

		txtStudentNumber = new JTextField();
		txtStudentNumber.setText((String) null);
		txtStudentNumber.setEditable(false);
		txtStudentNumber.setColumns(10);
		txtStudentNumber.setBounds(98, 6, 160, 31);
		panel_4_6.add(txtStudentNumber);

		JSeparator separator_4_6 = new JSeparator();
		separator_4_6.setBounds(-6, 50, 264, 2);
		panel_4_6.add(separator_4_6);

		JLabel lblStudentInformation = new JLabel("Student Information");
		lblStudentInformation.setForeground(new Color(138, 43, 226));
		lblStudentInformation.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblStudentInformation.setBounds(59, 10, 177, 29);
		panel_3.add(lblStudentInformation);

		JPanel panel_4_4 = new JPanel();
		panel_4_4.setLayout(null);
		panel_4_4.setBounds(53, 62, 313, 52);
		contentPane.add(panel_4_4);

		txtRegistrationNumber = new JTextField();
		txtRegistrationNumber.setText((String) null);
		txtRegistrationNumber.setEditable(false);
		txtRegistrationNumber.setColumns(10);
		txtRegistrationNumber.setBounds(141, 9, 160, 31);
		panel_4_4.add(txtRegistrationNumber);

		JSeparator separator_4_4 = new JSeparator();
		separator_4_4.setBounds(-6, 50, 307, 2);
		panel_4_4.add(separator_4_4);

		JLabel lblNewLabel_1_4_4 = new JLabel("Registration Number");
		lblNewLabel_1_4_4.setBounds(12, 16, 129, 17);
		panel_4_4.add(lblNewLabel_1_4_4);
		lblNewLabel_1_4_4.setForeground(Color.BLACK);

		JPanel panel_4_4_1 = new JPanel();
		panel_4_4_1.setLayout(null);
		panel_4_4_1.setBounds(53, 124, 313, 52);
		contentPane.add(panel_4_4_1);

		txtStaffName = new JTextField();
		txtStaffName.setText((String) null);
		txtStaffName.setEditable(false);
		txtStaffName.setColumns(10);
		txtStaffName.setBounds(141, 9, 160, 31);
		panel_4_4_1.add(txtStaffName);

		JSeparator separator_4_4_1 = new JSeparator();
		separator_4_4_1.setBounds(-6, 50, 307, 2);
		panel_4_4_1.add(separator_4_4_1);

		JLabel lblNewLabel_1_4_6 = new JLabel("Staff Name");
		lblNewLabel_1_4_6.setBounds(12, 16, 98, 17);
		panel_4_4_1.add(lblNewLabel_1_4_6);
		lblNewLabel_1_4_6.setForeground(Color.BLACK);

		JPanel panel_4_4_1_3 = new JPanel();
		panel_4_4_1_3.setLayout(null);
		panel_4_4_1_3.setBounds(383, 62, 313, 52);
		contentPane.add(panel_4_4_1_3);

		txtDiscountType = new JTextField();
		txtDiscountType.setText((String) null);
		txtDiscountType.setEditable(false);
		txtDiscountType.setColumns(10);
		txtDiscountType.setBounds(141, 9, 160, 31);
		panel_4_4_1_3.add(txtDiscountType);

		JSeparator separator_4_4_1_3 = new JSeparator();
		separator_4_4_1_3.setBounds(-6, 50, 307, 2);
		panel_4_4_1_3.add(separator_4_4_1_3);

		JLabel lblNewLabel_1_4_4_1_3 = new JLabel("Discount Type");
		lblNewLabel_1_4_4_1_3.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_1_3.setBounds(12, 16, 115, 17);
		panel_4_4_1_3.add(lblNewLabel_1_4_4_1_3);

		JPanel panel_4_4_1_4 = new JPanel();
		panel_4_4_1_4.setLayout(null);
		panel_4_4_1_4.setBounds(383, 124, 313, 52);
		contentPane.add(panel_4_4_1_4);

		txtTotalAmount = new JTextField();
		txtTotalAmount.setText((String) null);
		txtTotalAmount.setEditable(false);
		txtTotalAmount.setColumns(10);
		txtTotalAmount.setBounds(141, 9, 160, 31);
		panel_4_4_1_4.add(txtTotalAmount);

		JSeparator separator_4_4_1_4 = new JSeparator();
		separator_4_4_1_4.setBounds(-6, 50, 307, 2);
		panel_4_4_1_4.add(separator_4_4_1_4);

		JLabel lblNewLabel_1_4_4_1_4 = new JLabel("Total Amount");
		lblNewLabel_1_4_4_1_4.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_1_4.setBounds(12, 16, 115, 17);
		panel_4_4_1_4.add(lblNewLabel_1_4_4_1_4);

		date = new JLabel("New label");
		date.setFont(new Font("MS PMincho", Font.BOLD, 22));
		date.setBounds(53, 10, 223, 44);
		contentPane.add(date);

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(new Color(173, 216, 230));
		panel_2_1.setLayout(null);
		panel_2_1.setBounds(378, 186, 571, 365);
		contentPane.add(panel_2_1);

		JLabel lblCourseInformation = new JLabel("Course Information");
		lblCourseInformation.setForeground(new Color(138, 43, 226));
		lblCourseInformation.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblCourseInformation.setBounds(194, 10, 183, 29);
		panel_2_1.add(lblCourseInformation);

		JPanel panel_4_5 = new JPanel();
		panel_4_5.setLayout(null);
		panel_4_5.setBounds(12, 106, 258, 52);
		panel_2_1.add(panel_4_5);

		JLabel lblNewLabel_1_4_5 = new JLabel("Teacher Name");
		lblNewLabel_1_4_5.setForeground(Color.BLACK);
		lblNewLabel_1_4_5.setBounds(0, 13, 98, 17);
		panel_4_5.add(lblNewLabel_1_4_5);

		txtTeacherName = new JTextField();
		txtTeacherName.setText((String) null);
		txtTeacherName.setEditable(false);
		txtTeacherName.setColumns(10);
		txtTeacherName.setBounds(98, 6, 160, 31);
		panel_4_5.add(txtTeacherName);

		JSeparator separator_4_5 = new JSeparator();
		separator_4_5.setBounds(-6, 50, 264, 2);
		panel_4_5.add(separator_4_5);

		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setLayout(null);
		panel_4_1_1.setBounds(12, 168, 258, 52);
		panel_2_1.add(panel_4_1_1);

		JLabel lblNewLabel_1_4_1_1 = new JLabel("Course Name");
		lblNewLabel_1_4_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_1_1.setBounds(0, 13, 98, 17);
		panel_4_1_1.add(lblNewLabel_1_4_1_1);

		txtCourseName = new JTextField();
		txtCourseName.setText((String) null);
		txtCourseName.setEditable(false);
		txtCourseName.setColumns(10);
		txtCourseName.setBounds(98, 6, 160, 31);
		panel_4_1_1.add(txtCourseName);

		JSeparator separator_4_1_1 = new JSeparator();
		separator_4_1_1.setBounds(-6, 50, 264, 2);
		panel_4_1_1.add(separator_4_1_1);

		JPanel panel_4_2_1 = new JPanel();
		panel_4_2_1.setLayout(null);
		panel_4_2_1.setBounds(12, 230, 258, 52);
		panel_2_1.add(panel_4_2_1);

		JLabel lblNewLabel_1_4_2_1 = new JLabel("Room");
		lblNewLabel_1_4_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_2_1.setBounds(0, 13, 98, 17);
		panel_4_2_1.add(lblNewLabel_1_4_2_1);

		txtRoom = new JTextField();
		txtRoom.setText((String) null);
		txtRoom.setEditable(false);
		txtRoom.setColumns(10);
		txtRoom.setBounds(98, 6, 160, 31);
		panel_4_2_1.add(txtRoom);

		JSeparator separator_4_2_1 = new JSeparator();
		separator_4_2_1.setBounds(-6, 50, 264, 2);
		panel_4_2_1.add(separator_4_2_1);

		JPanel panel_4_3_1 = new JPanel();
		panel_4_3_1.setLayout(null);
		panel_4_3_1.setBounds(295, 230, 258, 52);
		panel_2_1.add(panel_4_3_1);

		JLabel lblNewLabel_1_4_3_1 = new JLabel("Section");
		lblNewLabel_1_4_3_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_3_1.setBounds(0, 13, 98, 17);
		panel_4_3_1.add(lblNewLabel_1_4_3_1);

		txtSection = new JTextField();
		txtSection.setText((String) null);
		txtSection.setEditable(false);
		txtSection.setColumns(10);
		txtSection.setBounds(98, 6, 160, 31);
		panel_4_3_1.add(txtSection);

		JSeparator separator_4_3_1 = new JSeparator();
		separator_4_3_1.setBounds(-6, 50, 264, 2);
		panel_4_3_1.add(separator_4_3_1);

		JPanel panel_4_4_2 = new JPanel();
		panel_4_4_2.setLayout(null);
		panel_4_4_2.setBounds(12, 292, 258, 52);
		panel_2_1.add(panel_4_4_2);

		JLabel lblNewLabel_1_4_4_2 = new JLabel("Start Date");
		lblNewLabel_1_4_4_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_2.setBounds(0, 13, 98, 17);
		panel_4_4_2.add(lblNewLabel_1_4_4_2);

		txtStartDate = new JTextField();
		txtStartDate.setText((String) null);
		txtStartDate.setEditable(false);
		txtStartDate.setColumns(10);
		txtStartDate.setBounds(98, 6, 160, 31);
		panel_4_4_2.add(txtStartDate);

		JSeparator separator_4_4_2 = new JSeparator();
		separator_4_4_2.setBounds(-6, 50, 264, 2);
		panel_4_4_2.add(separator_4_4_2);

		JPanel panel_4_4_1_1 = new JPanel();
		panel_4_4_1_1.setLayout(null);
		panel_4_4_1_1.setBounds(295, 292, 258, 52);
		panel_2_1.add(panel_4_4_1_1);

		JLabel lblNewLabel_1_4_4_1_1 = new JLabel("End Date");
		lblNewLabel_1_4_4_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_1_1.setBounds(0, 13, 98, 17);
		panel_4_4_1_1.add(lblNewLabel_1_4_4_1_1);

		txtEndDate = new JTextField();
		txtEndDate.setText((String) null);
		txtEndDate.setEditable(false);
		txtEndDate.setColumns(10);
		txtEndDate.setBounds(98, 6, 160, 31);
		panel_4_4_1_1.add(txtEndDate);

		JSeparator separator_4_4_1_1 = new JSeparator();
		separator_4_4_1_1.setBounds(-6, 50, 264, 2);
		panel_4_4_1_1.add(separator_4_4_1_1);

		JPanel panel_4_5_1 = new JPanel();
		panel_4_5_1.setLayout(null);
		panel_4_5_1.setBounds(295, 106, 258, 52);
		panel_2_1.add(panel_4_5_1);

		JLabel lblNewLabel_1_4_5_1 = new JLabel("Total PC");
		lblNewLabel_1_4_5_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_1.setBounds(0, 13, 98, 17);
		panel_4_5_1.add(lblNewLabel_1_4_5_1);

		txtTotalPC = new JTextField();
		txtTotalPC.setText((String) null);
		txtTotalPC.setEditable(false);
		txtTotalPC.setColumns(10);
		txtTotalPC.setBounds(98, 6, 160, 31);
		panel_4_5_1.add(txtTotalPC);

		JSeparator separator_4_5_1 = new JSeparator();
		separator_4_5_1.setBounds(-6, 50, 264, 2);
		panel_4_5_1.add(separator_4_5_1);

		JPanel panel_4_5_2 = new JPanel();
		panel_4_5_2.setLayout(null);
		panel_4_5_2.setBounds(295, 168, 258, 52);
		panel_2_1.add(panel_4_5_2);

		JLabel lblNewLabel_1_4_5_2 = new JLabel("REG Count");
		lblNewLabel_1_4_5_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_2.setBounds(0, 13, 98, 24);
		panel_4_5_2.add(lblNewLabel_1_4_5_2);

		txtRegistrationCount = new JTextField();
		txtRegistrationCount.setText((String) null);
		txtRegistrationCount.setEditable(false);
		txtRegistrationCount.setColumns(10);
		txtRegistrationCount.setBounds(98, 6, 160, 31);
		panel_4_5_2.add(txtRegistrationCount);

		JSeparator separator_4_5_2 = new JSeparator();
		separator_4_5_2.setBounds(-6, 50, 264, 2);
		panel_4_5_2.add(separator_4_5_2);

		JPanel panel_4_5_3 = new JPanel();
		panel_4_5_3.setLayout(null);
		panel_4_5_3.setBounds(12, 49, 258, 52);
		panel_2_1.add(panel_4_5_3);

		JLabel lblNewLabel_1_4_5_3 = new JLabel("Schedule ID");
		lblNewLabel_1_4_5_3.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_3.setBounds(0, 13, 98, 17);
		panel_4_5_3.add(lblNewLabel_1_4_5_3);

		txtScheduleID = new JTextField();
		txtScheduleID.setText((String) null);
		txtScheduleID.setEditable(false);
		txtScheduleID.setColumns(10);
		txtScheduleID.setBounds(98, 6, 160, 31);
		panel_4_5_3.add(txtScheduleID);

		JSeparator separator_4_5_3 = new JSeparator();
		separator_4_5_3.setBounds(-6, 50, 264, 2);
		panel_4_5_3.add(separator_4_5_3);
	}
}
