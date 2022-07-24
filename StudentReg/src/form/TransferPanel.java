package form;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import model.Discount;
import model.Registration;
import model.Schedule;
import model.Staff;
import model.Student;
import service.RegistrationService;
import service.ScheduleService;
import shared.utils.DateUtils;
import shared.utils.IDkeyGenerator;

import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;

public class TransferPanel extends JPanel {
	private JTextField txtCurrentTeacherName;
	private JTextField txtCurrentCourseName;
	private JTextField txtCurrentRoom;
	private JTextField txtCurrentSection;
	private JTextField txtCurrentStartDate;
	private JTextField txtCurrentEndDate;
	private JTextField txtCurrentTotalPC;
	private JTextField txtCurrentRegCount;
	private JTextField txtNewTeacherName;
	private JTextField txtNewCourseName;
	private JTextField txtNewRoom;
	private JTextField txtNewSection;
	private JTextField txtNewStartDate;
	private JTextField txtNewEndDate;
	private JTextField txtNewTotalPC;
	private JTextField txtNewRegCount;
	private JTextField txtStudentName;
	private JTextField txtStudentNRC;
	private JTextField txtStudentAddress;
	private JTextField txtStudentPhone;
	private JTextField txtStudentNumber;
	private JTextField txtPaidAmount;
	private JTextField txtNewCoursePrice;
	private JTextField txtNeedToPay;
	JLabel CurrentCourseID;
	JLabel NewCourseID;

	JComboBox<String> Registration_combo;
	JComboBox<String> transferSchedule_combo;

	RegistrationService registrationService;
	ScheduleService scheduleService;

	List<Registration> registrationList = new ArrayList<Registration>();
	List<Schedule> scheduleList = new ArrayList<Schedule>();

	Registration selectedRegistration;
	Schedule selectedSchedule;
	JButton btnTransfer;
	LocalDate today = DateUtils.asLocalDate(new Date());

	/**
	 * Create the panel.
	 */

	public void loadRegistrationForComboBox() {
		Registration_combo.removeAllItems();

		Registration_combo.addItem("- Select -");
		registrationList = this.registrationService.findAllRegistrations();

		registrationList.forEach(s -> this.Registration_combo.addItem(IDkeyGenerator.idToString("RG-", s.getReg_no())));

	}

	public void dataRefresh() {

		loadRegistrationForComboBox();

		resetStudentInformation();
		resetCurrentCourseView();
		resetNewCourseView();
		resetTransferForm();

	}

	public void resetStudentInformation() {
		txtStudentNumber.setText(null);
		txtStudentName.setText(null);
		txtStudentNRC.setText(null);
		txtStudentAddress.setText(null);
		txtStudentPhone.setText(null);
	}

	public void resetCurrentCourseView() {
		txtCurrentTeacherName.setText(null);
		txtCurrentCourseName.setText(null);
		txtCurrentRoom.setText(null);
		txtCurrentStartDate.setText(null);
		txtCurrentEndDate.setText(null);
		txtCurrentTotalPC.setText(null);
		txtCurrentRegCount.setText(null);
		txtCurrentSection.setText(null);

		CurrentCourseID.setText(null);

	}

	public void resetTransferForm() {

		btnTransfer.setEnabled(false);
		transferSchedule_combo.setEnabled(false);
		txtPaidAmount.setText(null);
		txtNewCoursePrice.setText(null);
		txtNeedToPay.setText(null);

	}

	public void resetNewCourseView() {

		txtNewTeacherName.setText(null);
		txtNewCourseName.setText(null);
		txtNewRoom.setText(null);
		txtNewStartDate.setText(null);
		txtNewEndDate.setText(null);
		txtNewTotalPC.setText(null);
		txtNewRegCount.setText(null);
		txtNewSection.setText(null);
		txtNewCoursePrice.setText(null);
		NewCourseID.setText(null);

	}

	public void initializeView() {
		setLayout(null);
		setBounds(0, 0, 1300, 700);
		setBackground(new Color(173, 216, 230));
		

		JLabel lblRegistrationList = new JLabel("Transfer");
		lblRegistrationList.setForeground(new Color(138, 43, 226));
		lblRegistrationList.setFont(new Font("Myanmar Text", Font.BOLD, 32));
		lblRegistrationList.setBounds(258, 22, 143, 45);
		add(lblRegistrationList);

		JPanel panel = new JPanel();

		panel.setBounds(26, 77, 313, 418);
		add(panel);
		panel.setLayout(null);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(12, 49, 289, 54);
		panel.add(panel_5);
		panel_5.setLayout(null);

		JLabel lblNewLabel_1_5 = new JLabel("Registration ID");
		lblNewLabel_1_5.setBounds(0, 17, 98, 17);
		panel_5.add(lblNewLabel_1_5);
		lblNewLabel_1_5.setForeground(Color.BLACK);

		Registration_combo = new JComboBox<String>();
		
		AutoCompleteDecorator.decorate(Registration_combo);
		Registration_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				resetStudentInformation();

				if (Registration_combo.getSelectedIndex() != 0 && Registration_combo.getSelectedIndex() != -1) {

					selectedRegistration = registrationList.stream()
							.filter(r -> r.getReg_no() == IDkeyGenerator
									.stringToID(Registration_combo.getSelectedItem().toString()))
							.findFirst().orElse(null);

					scheduleList = scheduleService.findAllSchedules().stream().filter(s -> {
						return s.getSchedule_id() != selectedRegistration.getSchedule().getSchedule_id()
								&& today.isBefore(s.getStart_date()); // Not Same Schedule id and Before Schedule Start
																		// Date

					}).collect(Collectors.toList());

					transferSchedule_combo.removeAllItems();
					transferSchedule_combo.addItem("- Select -");

					scheduleList.forEach(
							s -> transferSchedule_combo.addItem(IDkeyGenerator.idToString("SC-", s.getSchedule_id())));

					CurrentCourseID.setText(
							IDkeyGenerator.idToString("SC-", selectedRegistration.getSchedule().getSchedule_id()));

					txtStudentNumber
							.setText(IDkeyGenerator.idToString("ST-", selectedRegistration.getStudent().getStdNo()));
					txtStudentName.setText(selectedRegistration.getStudent().getStdName());
					txtStudentNRC.setText(selectedRegistration.getStudent().getStdNRC());
					txtStudentAddress.setText(selectedRegistration.getStudent().getStdAddress());
					txtStudentPhone.setText(selectedRegistration.getStudent().getStdPhno());

					txtCurrentTeacherName.setText(selectedRegistration.getSchedule().getTeacher().getTeacherName());
					txtCurrentCourseName.setText(selectedRegistration.getSchedule().getCourse().getCourseName());
					txtCurrentRoom.setText(selectedRegistration.getSchedule().getRoom().getRoom_name());
					txtCurrentStartDate.setText(selectedRegistration.getSchedule().getStart_date().toString());
					txtCurrentEndDate.setText(selectedRegistration.getSchedule().getEnd_date().toString());
					txtCurrentTotalPC
							.setText(String.valueOf(selectedRegistration.getSchedule().getRoom().getRoom_pc()));
					txtCurrentRegCount
							.setText(String.valueOf(selectedRegistration.getSchedule().getRegristration_count()));
					txtCurrentSection.setText(selectedRegistration.getSchedule().getSection().getSection_DayTime());
					txtPaidAmount.setText(String.valueOf(selectedRegistration.getTotalamount()));
					txtNewCoursePrice.setText(null);
					txtNeedToPay.setText(null);

					LocalDate startdate = selectedRegistration.getSchedule().getStart_date();

					resetNewCourseView();

					if (today.isBefore(startdate.minusDays(5))) {

						transferSchedule_combo.setEnabled(true);
						btnTransfer.setEnabled(true);
					} else if (today.isAfter(startdate)) {
						transferSchedule_combo.setEnabled(false);
						btnTransfer.setEnabled(false);
						JOptionPane.showMessageDialog(null, "Can't Transfer Class is on progress...");
					} else {

						transferSchedule_combo.setEnabled(false);
						btnTransfer.setEnabled(false);
						JOptionPane.showMessageDialog(null, "Can't Transfer class will start soon...");
					}


				}

			}
		});

		Registration_combo.setMaximumRowCount(6);
		Registration_combo.setBounds(125, 10, 152, 30);
		panel_5.add(Registration_combo);

		JPanel panel_5_1 = new JPanel();
		panel_5_1.setLayout(null);
		panel_5_1.setBounds(12, 177, 289, 54);
		panel.add(panel_5_1);

		JLabel lblNewLabel_1_5_1 = new JLabel("Transfer Schedule ID");
		lblNewLabel_1_5_1.setForeground(Color.BLACK);
		lblNewLabel_1_5_1.setBounds(0, 17, 122, 17);
		panel_5_1.add(lblNewLabel_1_5_1);

		transferSchedule_combo = new JComboBox<String>();

		transferSchedule_combo.setEnabled(false);
		transferSchedule_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (transferSchedule_combo.getSelectedIndex() != 0 && transferSchedule_combo.getSelectedIndex() != -1) {

					selectedSchedule = scheduleList.stream().filter(s -> s.getSchedule_id() == IDkeyGenerator
							.stringToID(transferSchedule_combo.getSelectedItem().toString())

					).findFirst().orElse(null);

					NewCourseID.setText(IDkeyGenerator.idToString("SC-", selectedSchedule.getSchedule_id()));

					txtNewTeacherName.setText(selectedSchedule.getTeacher().getTeacherName());
					txtNewCourseName.setText(selectedSchedule.getCourse().getCourseName());
					txtNewRoom.setText(selectedSchedule.getRoom().getRoom_name());
					txtNewStartDate.setText(selectedSchedule.getStart_date().toString());
					txtNewEndDate.setText(selectedSchedule.getEnd_date().toString());
					txtNewTotalPC.setText(String.valueOf(selectedSchedule.getRoom().getRoom_pc()));
					txtNewRegCount.setText(String.valueOf(selectedSchedule.getRegristration_count()));
					txtNewSection.setText(selectedSchedule.getSection().getSection_DayTime());
					txtNewCoursePrice.setText(String.valueOf(selectedSchedule.getCourse().getPrice()));

					int price = selectedSchedule.getCourse().getPrice() - Integer.parseInt(txtPaidAmount.getText());

					if (price >= 0)
						txtNeedToPay.setText(price + "");
					else
						txtNeedToPay.setText("NO Refund");

				}

			}
		});

		transferSchedule_combo.setMaximumRowCount(6);
		transferSchedule_combo.setBounds(124, 10, 153, 30);
		panel_5_1.add(transferSchedule_combo);

		btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (Registration_combo.getSelectedIndex() == 0 || transferSchedule_combo.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Required All Field.");
					return;
				}

				if (selectedSchedule.getRoom().getRoom_pc() > selectedSchedule.getRegristration_count()) {
					Registration registration = new Registration();

					Staff staff = new Staff(); // temporary Store Login STaff
					staff.setStaffNo(1);

					registration.setStudent(selectedRegistration.getStudent());
					registration.setSchedule(selectedSchedule);

				
					Discount discount = new Discount();
					discount.setDiscount_id(1);
					registration.setDiscount(discount);
					registration.setStaff(staff);
					registration.setDate(DateUtils.asLocalDate(new Date()));

					int newCoursePrice = Integer.parseInt(txtNewCoursePrice.getText());
					int paidAmount = Integer.parseInt(txtPaidAmount.getText());
					int totalAmount;
					if (newCoursePrice >= paidAmount)
						totalAmount = newCoursePrice;
					else
						totalAmount = paidAmount;

					registration.setTotalamount(totalAmount);

					registrationService.updateRegistration(selectedRegistration.getReg_no(), registration);

					JOptionPane.showMessageDialog(null, "Succefully Update");

					selectedSchedule.setRegristration_count(selectedSchedule.getRegristration_count() + 1); // increase
																											// registration
																											// count
					scheduleService.updateSchedule(selectedSchedule.getSchedule_id(), selectedSchedule);

					Schedule s = selectedRegistration.getSchedule();
					s.setRegristration_count(s.getRegristration_count() - 1); // decrease registration count
					scheduleService.updateSchedule(s.getSchedule_id(), s);

					dataRefresh();

				} else {
					JOptionPane.showMessageDialog(null, "Course is Full");
				}

			}
		});
		btnTransfer.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		btnTransfer.setBounds(12, 369, 289, 38);
		btnTransfer.setEnabled(false);
		panel.add(btnTransfer);

		JPanel panel_5_1_1 = new JPanel();
		panel_5_1_1.setLayout(null);
		panel_5_1_1.setBounds(12, 113, 289, 54);
		panel.add(panel_5_1_1);

		JLabel lblNewLabel_1_5_1_1 = new JLabel("Paid Amount");
		lblNewLabel_1_5_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_5_1_1.setBounds(0, 17, 106, 17);
		panel_5_1_1.add(lblNewLabel_1_5_1_1);

		txtPaidAmount = new JTextField();
		txtPaidAmount.setText((String) null);
		txtPaidAmount.setEditable(false);
		txtPaidAmount.setColumns(10);
		txtPaidAmount.setBounds(125, 10, 152, 31);
		panel_5_1_1.add(txtPaidAmount);

		JPanel panel_5_1_2 = new JPanel();
		panel_5_1_2.setLayout(null);
		panel_5_1_2.setBounds(12, 241, 289, 54);
		panel.add(panel_5_1_2);

		JLabel lblNewLabel_1_5_1_2 = new JLabel("New Course Price");
		lblNewLabel_1_5_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_5_1_2.setBounds(0, 17, 110, 17);
		panel_5_1_2.add(lblNewLabel_1_5_1_2);

		txtNewCoursePrice = new JTextField();
		txtNewCoursePrice.setText((String) null);
		txtNewCoursePrice.setEditable(false);
		txtNewCoursePrice.setColumns(10);
		txtNewCoursePrice.setBounds(120, 10, 152, 31);
		panel_5_1_2.add(txtNewCoursePrice);

		JPanel panel_5_1_2_1 = new JPanel();
		panel_5_1_2_1.setLayout(null);
		panel_5_1_2_1.setBounds(12, 305, 289, 54);
		panel.add(panel_5_1_2_1);

		JLabel lblNewLabel_1_5_1_2_1 = new JLabel("Need To Pay");
		lblNewLabel_1_5_1_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_5_1_2_1.setBounds(0, 17, 110, 17);
		panel_5_1_2_1.add(lblNewLabel_1_5_1_2_1);

		txtNeedToPay = new JTextField();
		txtNeedToPay.setText((String) null);
		txtNeedToPay.setEditable(false);
		txtNeedToPay.setColumns(10);
		txtNeedToPay.setBounds(125, 10, 152, 31);
		panel_5_1_2_1.add(txtNeedToPay);

		JLabel lblTransferForm = new JLabel("Transfer Form");
		lblTransferForm.setForeground(new Color(138, 43, 226));
		lblTransferForm.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblTransferForm.setBounds(89, 10, 135, 29);
		panel.add(lblTransferForm);

		JPanel panel_2_1_1 = new JPanel();

		panel_2_1_1.setLayout(null);
		panel_2_1_1.setBounds(676, 5, 571, 298);
		add(panel_2_1_1);

		JLabel lblCourse = new JLabel("Current Course");
		lblCourse.setForeground(new Color(138, 43, 226));
		lblCourse.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblCourse.setBounds(201, 10, 152, 29);
		panel_2_1_1.add(lblCourse);

		JPanel panel_4_5_4 = new JPanel();
		panel_4_5_4.setLayout(null);
		panel_4_5_4.setBounds(12, 49, 258, 52);
		panel_2_1_1.add(panel_4_5_4);

		JLabel lblNewLabel_1_4_5_4 = new JLabel("Teacher Name");
		lblNewLabel_1_4_5_4.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_4.setBounds(0, 13, 98, 17);
		panel_4_5_4.add(lblNewLabel_1_4_5_4);

		txtCurrentTeacherName = new JTextField();
		txtCurrentTeacherName.setText((String) null);
		txtCurrentTeacherName.setEditable(false);
		txtCurrentTeacherName.setColumns(10);
		txtCurrentTeacherName.setBounds(98, 6, 160, 31);
		panel_4_5_4.add(txtCurrentTeacherName);

		JSeparator separator_4_5_4 = new JSeparator();
		separator_4_5_4.setBounds(-6, 50, 264, 2);
		panel_4_5_4.add(separator_4_5_4);

		JPanel panel_4_1_1_1 = new JPanel();
		panel_4_1_1_1.setLayout(null);
		panel_4_1_1_1.setBounds(12, 111, 258, 52);
		panel_2_1_1.add(panel_4_1_1_1);

		JLabel lblNewLabel_1_4_1_1_1 = new JLabel("Course Name");
		lblNewLabel_1_4_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_1_1_1.setBounds(0, 13, 98, 17);
		panel_4_1_1_1.add(lblNewLabel_1_4_1_1_1);

		txtCurrentCourseName = new JTextField();
		txtCurrentCourseName.setText((String) null);
		txtCurrentCourseName.setEditable(false);
		txtCurrentCourseName.setColumns(10);
		txtCurrentCourseName.setBounds(98, 6, 160, 31);
		panel_4_1_1_1.add(txtCurrentCourseName);

		JSeparator separator_4_1_1_1 = new JSeparator();
		separator_4_1_1_1.setBounds(-6, 50, 264, 2);
		panel_4_1_1_1.add(separator_4_1_1_1);

		JPanel panel_4_2_1_1 = new JPanel();
		panel_4_2_1_1.setLayout(null);
		panel_4_2_1_1.setBounds(12, 173, 258, 52);
		panel_2_1_1.add(panel_4_2_1_1);

		JLabel lblNewLabel_1_4_2_1_1 = new JLabel("Room");
		lblNewLabel_1_4_2_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_2_1_1.setBounds(0, 13, 98, 17);
		panel_4_2_1_1.add(lblNewLabel_1_4_2_1_1);

		txtCurrentRoom = new JTextField();
		txtCurrentRoom.setText((String) null);
		txtCurrentRoom.setEditable(false);
		txtCurrentRoom.setColumns(10);
		txtCurrentRoom.setBounds(98, 6, 160, 31);
		panel_4_2_1_1.add(txtCurrentRoom);

		JSeparator separator_4_2_1_1 = new JSeparator();
		separator_4_2_1_1.setBounds(-6, 50, 264, 2);
		panel_4_2_1_1.add(separator_4_2_1_1);

		JPanel panel_4_3_1_1 = new JPanel();
		panel_4_3_1_1.setLayout(null);
		panel_4_3_1_1.setBounds(295, 173, 258, 52);
		panel_2_1_1.add(panel_4_3_1_1);

		JLabel lblNewLabel_1_4_3_1_1 = new JLabel("Section");
		lblNewLabel_1_4_3_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_3_1_1.setBounds(0, 13, 98, 17);
		panel_4_3_1_1.add(lblNewLabel_1_4_3_1_1);

		txtCurrentSection = new JTextField();
		txtCurrentSection.setText((String) null);
		txtCurrentSection.setEditable(false);
		txtCurrentSection.setColumns(10);
		txtCurrentSection.setBounds(98, 6, 160, 31);
		panel_4_3_1_1.add(txtCurrentSection);

		JSeparator separator_4_3_1_1 = new JSeparator();
		separator_4_3_1_1.setBounds(-6, 50, 264, 2);
		panel_4_3_1_1.add(separator_4_3_1_1);

		JPanel panel_4_4_2_1 = new JPanel();
		panel_4_4_2_1.setLayout(null);
		panel_4_4_2_1.setBounds(12, 235, 258, 52);
		panel_2_1_1.add(panel_4_4_2_1);

		JLabel lblNewLabel_1_4_4_2_1 = new JLabel("Start Date");
		lblNewLabel_1_4_4_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_2_1.setBounds(0, 13, 98, 17);
		panel_4_4_2_1.add(lblNewLabel_1_4_4_2_1);

		txtCurrentStartDate = new JTextField();
		txtCurrentStartDate.setText((String) null);
		txtCurrentStartDate.setEditable(false);
		txtCurrentStartDate.setColumns(10);
		txtCurrentStartDate.setBounds(98, 6, 160, 31);
		panel_4_4_2_1.add(txtCurrentStartDate);

		JSeparator separator_4_4_2_1 = new JSeparator();
		separator_4_4_2_1.setBounds(-6, 50, 264, 2);
		panel_4_4_2_1.add(separator_4_4_2_1);

		JPanel panel_4_4_1_1_1 = new JPanel();
		panel_4_4_1_1_1.setLayout(null);
		panel_4_4_1_1_1.setBounds(295, 235, 258, 52);
		panel_2_1_1.add(panel_4_4_1_1_1);

		JLabel lblNewLabel_1_4_4_1_1_1 = new JLabel("End Date");
		lblNewLabel_1_4_4_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_1_1_1.setBounds(0, 13, 98, 17);
		panel_4_4_1_1_1.add(lblNewLabel_1_4_4_1_1_1);

		txtCurrentEndDate = new JTextField();
		txtCurrentEndDate.setText((String) null);
		txtCurrentEndDate.setEditable(false);
		txtCurrentEndDate.setColumns(10);
		txtCurrentEndDate.setBounds(98, 6, 160, 31);
		panel_4_4_1_1_1.add(txtCurrentEndDate);

		JSeparator separator_4_4_1_1_1 = new JSeparator();
		separator_4_4_1_1_1.setBounds(-6, 50, 264, 2);
		panel_4_4_1_1_1.add(separator_4_4_1_1_1);

		JPanel panel_4_5_1_1 = new JPanel();
		panel_4_5_1_1.setLayout(null);
		panel_4_5_1_1.setBounds(295, 49, 258, 52);
		panel_2_1_1.add(panel_4_5_1_1);

		JLabel lblNewLabel_1_4_5_1_1 = new JLabel("Total PC");
		lblNewLabel_1_4_5_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_1_1.setBounds(0, 13, 98, 17);
		panel_4_5_1_1.add(lblNewLabel_1_4_5_1_1);

		txtCurrentTotalPC = new JTextField();
		txtCurrentTotalPC.setText((String) null);
		txtCurrentTotalPC.setEditable(false);
		txtCurrentTotalPC.setColumns(10);
		txtCurrentTotalPC.setBounds(98, 6, 160, 31);
		panel_4_5_1_1.add(txtCurrentTotalPC);

		JSeparator separator_4_5_1_1 = new JSeparator();
		separator_4_5_1_1.setBounds(-6, 50, 264, 2);
		panel_4_5_1_1.add(separator_4_5_1_1);

		JPanel panel_4_5_2_1 = new JPanel();
		panel_4_5_2_1.setLayout(null);
		panel_4_5_2_1.setBounds(295, 111, 258, 52);
		panel_2_1_1.add(panel_4_5_2_1);

		JLabel lblNewLabel_1_4_5_2_1 = new JLabel("REG Count");
		lblNewLabel_1_4_5_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_2_1.setBounds(0, 13, 98, 24);
		panel_4_5_2_1.add(lblNewLabel_1_4_5_2_1);

		txtCurrentRegCount = new JTextField();
		txtCurrentRegCount.setText((String) null);
		txtCurrentRegCount.setEditable(false);
		txtCurrentRegCount.setColumns(10);
		txtCurrentRegCount.setBounds(98, 6, 160, 31);
		panel_4_5_2_1.add(txtCurrentRegCount);

		JSeparator separator_4_5_2_1 = new JSeparator();
		separator_4_5_2_1.setBounds(-6, 50, 264, 2);
		panel_4_5_2_1.add(separator_4_5_2_1);

		CurrentCourseID = new JLabel("Teacher Name");
		CurrentCourseID.setForeground(Color.BLACK);
		CurrentCourseID.setBounds(12, 21, 105, 17);
		panel_2_1_1.add(CurrentCourseID);

		JPanel panel_2_1_2 = new JPanel();

		panel_2_1_2.setLayout(null);
		panel_2_1_2.setBounds(676, 313, 571, 298);
		add(panel_2_1_2);

		JLabel lblTransferCourse = new JLabel("New Course");
		lblTransferCourse.setForeground(new Color(138, 43, 226));
		lblTransferCourse.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		lblTransferCourse.setBounds(227, 10, 117, 29);
		panel_2_1_2.add(lblTransferCourse);

		JPanel panel_4_5_5 = new JPanel();
		panel_4_5_5.setLayout(null);
		panel_4_5_5.setBounds(12, 49, 258, 52);
		panel_2_1_2.add(panel_4_5_5);

		JLabel lblNewLabel_1_4_5_5 = new JLabel("Teacher Name");
		lblNewLabel_1_4_5_5.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_5.setBounds(0, 13, 98, 17);
		panel_4_5_5.add(lblNewLabel_1_4_5_5);

		txtNewTeacherName = new JTextField();
		txtNewTeacherName.setText((String) null);
		txtNewTeacherName.setEditable(false);
		txtNewTeacherName.setColumns(10);
		txtNewTeacherName.setBounds(98, 6, 160, 31);
		panel_4_5_5.add(txtNewTeacherName);

		JSeparator separator_4_5_5 = new JSeparator();
		separator_4_5_5.setBounds(-6, 50, 264, 2);
		panel_4_5_5.add(separator_4_5_5);

		JPanel panel_4_1_1_2 = new JPanel();
		panel_4_1_1_2.setLayout(null);
		panel_4_1_1_2.setBounds(12, 111, 258, 52);
		panel_2_1_2.add(panel_4_1_1_2);

		JLabel lblNewLabel_1_4_1_1_2 = new JLabel("Course Name");
		lblNewLabel_1_4_1_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_1_1_2.setBounds(0, 13, 98, 17);
		panel_4_1_1_2.add(lblNewLabel_1_4_1_1_2);

		txtNewCourseName = new JTextField();
		txtNewCourseName.setText((String) null);
		txtNewCourseName.setEditable(false);
		txtNewCourseName.setColumns(10);
		txtNewCourseName.setBounds(98, 6, 160, 31);
		panel_4_1_1_2.add(txtNewCourseName);

		JSeparator separator_4_1_1_2 = new JSeparator();
		separator_4_1_1_2.setBounds(-6, 50, 264, 2);
		panel_4_1_1_2.add(separator_4_1_1_2);

		JPanel panel_4_2_1_2 = new JPanel();
		panel_4_2_1_2.setLayout(null);
		panel_4_2_1_2.setBounds(12, 173, 258, 52);
		panel_2_1_2.add(panel_4_2_1_2);

		JLabel lblNewLabel_1_4_2_1_2 = new JLabel("Room");
		lblNewLabel_1_4_2_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_2_1_2.setBounds(0, 13, 98, 17);
		panel_4_2_1_2.add(lblNewLabel_1_4_2_1_2);

		txtNewRoom = new JTextField();
		txtNewRoom.setText((String) null);
		txtNewRoom.setEditable(false);
		txtNewRoom.setColumns(10);
		txtNewRoom.setBounds(98, 6, 160, 31);
		panel_4_2_1_2.add(txtNewRoom);

		JSeparator separator_4_2_1_2 = new JSeparator();
		separator_4_2_1_2.setBounds(-6, 50, 264, 2);
		panel_4_2_1_2.add(separator_4_2_1_2);

		JPanel panel_4_3_1_2 = new JPanel();
		panel_4_3_1_2.setLayout(null);
		panel_4_3_1_2.setBounds(295, 173, 258, 52);
		panel_2_1_2.add(panel_4_3_1_2);

		JLabel lblNewLabel_1_4_3_1_2 = new JLabel("Section");
		lblNewLabel_1_4_3_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_3_1_2.setBounds(0, 13, 98, 17);
		panel_4_3_1_2.add(lblNewLabel_1_4_3_1_2);

		txtNewSection = new JTextField();
		txtNewSection.setText((String) null);
		txtNewSection.setEditable(false);
		txtNewSection.setColumns(10);
		txtNewSection.setBounds(98, 6, 160, 31);
		panel_4_3_1_2.add(txtNewSection);

		JSeparator separator_4_3_1_2 = new JSeparator();
		separator_4_3_1_2.setBounds(-6, 50, 264, 2);
		panel_4_3_1_2.add(separator_4_3_1_2);

		JPanel panel_4_4_2_2 = new JPanel();
		panel_4_4_2_2.setLayout(null);
		panel_4_4_2_2.setBounds(12, 235, 258, 52);
		panel_2_1_2.add(panel_4_4_2_2);

		JLabel lblNewLabel_1_4_4_2_2 = new JLabel("Start Date");
		lblNewLabel_1_4_4_2_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_2_2.setBounds(0, 13, 98, 17);
		panel_4_4_2_2.add(lblNewLabel_1_4_4_2_2);

		txtNewStartDate = new JTextField();
		txtNewStartDate.setText((String) null);
		txtNewStartDate.setEditable(false);
		txtNewStartDate.setColumns(10);
		txtNewStartDate.setBounds(98, 6, 160, 31);
		panel_4_4_2_2.add(txtNewStartDate);

		JSeparator separator_4_4_2_2 = new JSeparator();
		separator_4_4_2_2.setBounds(-6, 50, 264, 2);
		panel_4_4_2_2.add(separator_4_4_2_2);

		JPanel panel_4_4_1_1_2 = new JPanel();
		panel_4_4_1_1_2.setLayout(null);
		panel_4_4_1_1_2.setBounds(295, 235, 258, 52);
		panel_2_1_2.add(panel_4_4_1_1_2);

		JLabel lblNewLabel_1_4_4_1_1_2 = new JLabel("End Date");
		lblNewLabel_1_4_4_1_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_4_1_1_2.setBounds(0, 13, 98, 17);
		panel_4_4_1_1_2.add(lblNewLabel_1_4_4_1_1_2);

		txtNewEndDate = new JTextField();
		txtNewEndDate.setText((String) null);
		txtNewEndDate.setEditable(false);
		txtNewEndDate.setColumns(10);
		txtNewEndDate.setBounds(98, 6, 160, 31);
		panel_4_4_1_1_2.add(txtNewEndDate);

		JSeparator separator_4_4_1_1_2 = new JSeparator();
		separator_4_4_1_1_2.setBounds(-6, 50, 264, 2);
		panel_4_4_1_1_2.add(separator_4_4_1_1_2);

		JPanel panel_4_5_1_2 = new JPanel();
		panel_4_5_1_2.setLayout(null);
		panel_4_5_1_2.setBounds(295, 49, 258, 52);
		panel_2_1_2.add(panel_4_5_1_2);

		JLabel lblNewLabel_1_4_5_1_2 = new JLabel("Total PC");
		lblNewLabel_1_4_5_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_1_2.setBounds(0, 13, 98, 17);
		panel_4_5_1_2.add(lblNewLabel_1_4_5_1_2);

		txtNewTotalPC = new JTextField();
		txtNewTotalPC.setText((String) null);
		txtNewTotalPC.setEditable(false);
		txtNewTotalPC.setColumns(10);
		txtNewTotalPC.setBounds(98, 6, 160, 31);
		panel_4_5_1_2.add(txtNewTotalPC);

		JSeparator separator_4_5_1_2 = new JSeparator();
		separator_4_5_1_2.setBounds(-6, 50, 264, 2);
		panel_4_5_1_2.add(separator_4_5_1_2);

		JPanel panel_4_5_2_2 = new JPanel();
		panel_4_5_2_2.setLayout(null);
		panel_4_5_2_2.setBounds(295, 111, 258, 52);
		panel_2_1_2.add(panel_4_5_2_2);

		JLabel lblNewLabel_1_4_5_2_2 = new JLabel("REG Count");
		lblNewLabel_1_4_5_2_2.setForeground(Color.BLACK);
		lblNewLabel_1_4_5_2_2.setBounds(0, 13, 98, 24);
		panel_4_5_2_2.add(lblNewLabel_1_4_5_2_2);

		txtNewRegCount = new JTextField();
		txtNewRegCount.setText((String) null);
		txtNewRegCount.setEditable(false);
		txtNewRegCount.setColumns(10);
		txtNewRegCount.setBounds(98, 6, 160, 31);
		panel_4_5_2_2.add(txtNewRegCount);

		JSeparator separator_4_5_2_2 = new JSeparator();
		separator_4_5_2_2.setBounds(-6, 50, 264, 2);
		panel_4_5_2_2.add(separator_4_5_2_2);

		NewCourseID = new JLabel("Teacher Name");
		NewCourseID.setForeground(Color.BLACK);
		NewCourseID.setBounds(12, 21, 105, 17);
		panel_2_1_2.add(NewCourseID);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);

		panel_3.setBounds(351, 77, 313, 365);
		add(panel_3);

		JPanel panel_2 = new JPanel();
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

		txtStudentPhone = new JTextField();
		txtStudentPhone.setText((String) null);
		txtStudentPhone.setEditable(false);
		txtStudentPhone.setColumns(10);
		txtStudentPhone.setBounds(98, 6, 160, 31);
		panel_4_3.add(txtStudentPhone);

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

		registrationService = new RegistrationService();
		scheduleService = new ScheduleService();
	}

	public TransferPanel() {
		initializeView();
		dataRefresh();

	}
}
