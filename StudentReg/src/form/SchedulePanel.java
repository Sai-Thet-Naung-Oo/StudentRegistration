package form;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import model.Course;
import model.Room;
import model.Schedule;
import model.Section;
import model.Teacher;
import service.CourseService;
import service.RoomService;
import service.ScheduleService;
import service.SectionService;
import service.TeacherService;
import shared.utils.DateRangeValidator;
import shared.utils.DateUtils;
import shared.utils.IDkeyGenerator;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SchedulePanel extends JPanel {

	// ui
	JTable tableSchedule;
	JComboBox<String> teacher_combo;
	JComboBox<String> course_combo;
	JComboBox<String> section_combo;
	JComboBox<String> room_combo;

	JComboBox<String> searchByCombo;
	JComboBox<String> searchDataCombo;

	JPanel searchDataComboPanel;

	JDateChooser start_DateChooser;
	JDateChooser end_DateChooser;
	JScrollPane scrollPane;

	// service
	TeacherService teacherservice;
	CourseService courseservice;
	SectionService sectionservice;
	RoomService roomservice;
	ScheduleService scheduleService;
	JPanel editpanel, savepanel;

	// List
	private List<Teacher> teacherList = new ArrayList<>(); // all teacher
	private List<Course> courseList = new ArrayList<>();
	private List<Section> sectionList = new ArrayList<>();
	private List<Room> roomList = new ArrayList<>();

	private List<Schedule> teacherscehduleList = new ArrayList<>(); // schedule list by teacher id
	private List<Schedule> roomscehduleList = new ArrayList<>(); // schedule list by room id
	private List<Schedule> originalScheduleList = new ArrayList<>(); // for table data
	private List<Schedule> searchScheduleList = new ArrayList<>();
	// variable
	LocalDate start_Date, end_Date;
	private DefaultTableModel dtm = new DefaultTableModel();
	Schedule selectedSchedule;

	JPanel createHeading, editHeading;
	JButton editButton, deleteButton, editCancelButton;
	JButton saveButton, saveCancelButton;
	private JTextField txtSearch;
	private JPanel searchTextFieldPanel;
	LocalDate today = DateUtils.asLocalDate(new Date());

	/**
	 * Create the panel.
	 */
	public void autoDelete() { // auto delete end date
		List<Schedule> deleteScheduleList = new ArrayList<>();
		List<Schedule> originalScheduleList = new ArrayList<>();

		originalScheduleList = scheduleService.findAllSchedules(); // select all

		deleteScheduleList = originalScheduleList.stream()
				.filter(s -> today.isAfter(s.getEnd_date())) // filter end
																										// date to
																										// delete
				.collect(Collectors.toList());

		deleteScheduleList.forEach(s -> scheduleService.deleteSchedule(s.getSchedule_id())); // delete

	}

	public void resetSearchDataPanel() {
		searchDataComboPanel.setVisible(false);
		searchTextFieldPanel.setVisible(false);
	}

	public void resetButtonPanel() {
		savepanel.setVisible(false);
		createHeading.setVisible(false);

		editpanel.setVisible(false); // show edit panel
		editHeading.setVisible(false); // show edit heading
	}

	public void resetFormData() {
		teacher_combo.setSelectedIndex(0);
		room_combo.setSelectedIndex(0);
		section_combo.setSelectedIndex(0);
		course_combo.setSelectedIndex(0);
		start_DateChooser.setCalendar(null);
		end_DateChooser.setCalendar(null);
	}

	public SchedulePanel() {

		intialize();// load view

		teacherservice = new TeacherService(); // declare service;
		courseservice = new CourseService();
		sectionservice = new SectionService();
		roomservice = new RoomService();
		scheduleService = new ScheduleService();

		loadTeacherForComboBox(); // load data to combo
		loadCourseForComboBox();
		loadSectionForComboBox();
		loadRoomForComboBox();
		resetSearchDataPanel();
		// table
		setTableDesign();
		autoDelete();
		this.loadAllSchedules(Optional.empty());

	}

	private void setTableDesign() {

		dtm.addColumn("Schedule-ID");
		dtm.addColumn("Teacher Name");
		dtm.addColumn("Course");
		dtm.addColumn("Section");
		dtm.addColumn("Room");
		dtm.addColumn("Start-Date");
		dtm.addColumn("End-Date");
		dtm.addColumn("total");
		dtm.addColumn("reg");

		this.tableSchedule.setModel(dtm);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i <= 8; i++) {
			if (i == 1 || i == 2)
				continue;
			tableSchedule.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // Table Data to center

		}

		tableSchedule.getTableHeader() // Column heading two line
				.setPreferredSize(new Dimension(tableSchedule.getColumnModel().getTotalColumnWidth(), 32));
		tableSchedule.getColumnModel().getColumn(0).setHeaderValue("<html>Schedule<br><center> ID</center>");
		tableSchedule.getColumnModel().getColumn(7).setHeaderValue("<html>Total<br><center>PC</center>");
		tableSchedule.getColumnModel().getColumn(8).setHeaderValue("<html>REG<br><center>Count</center>");

		tableSchedule.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// set width manual
		tableSchedule.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableSchedule.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableSchedule.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableSchedule.getColumnModel().getColumn(3).setPreferredWidth(120);
		tableSchedule.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableSchedule.getColumnModel().getColumn(5).setPreferredWidth(100);
		tableSchedule.getColumnModel().getColumn(6).setPreferredWidth(100);
		tableSchedule.getColumnModel().getColumn(7).setPreferredWidth(50);
		tableSchedule.getColumnModel().getColumn(8).setPreferredWidth(50);
		tableSchedule.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

	}

	public void loadAllSchedules(Optional<List<Schedule>> optionalSchedules) {
		this.dtm = (DefaultTableModel) this.tableSchedule.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		originalScheduleList = scheduleService.findAllSchedules();
		List<Schedule> scheduleList = optionalSchedules.orElseGet(() -> originalScheduleList);

		scheduleList.forEach(s -> {
			Object[] row = new Object[9];
			row[0] = IDkeyGenerator.idToString("SC-", s.getSchedule_id());
			row[1] = s.getTeacher().getTeacherName();
			row[2] = s.getCourse().getCourseName();
			row[3] = s.getSection().getSection_DayTime();
			row[4] = s.getRoom().getRoom_name();
			row[5] = s.getStart_date();
			row[6] = s.getEnd_date();
			row[7] = s.getRoom().getRoom_pc();
			row[8] = s.getRegristration_count();

			dtm.addRow(row);
		});

		this.tableSchedule.setModel(dtm);
	}

	public void loadTeacherForComboBox() {
		teacher_combo.removeAllItems();

		this.teacher_combo.addItem("- Select -");
		teacherList = this.teacherservice.findAllTeacher();
		teacherList.forEach(s -> this.teacher_combo.addItem(s.getTeacherName()));
	}

	public void loadCourseForComboBox() {

		course_combo.removeAllItems();
		this.course_combo.addItem("- Select -");
		courseList = this.courseservice.findAllCourse();
		courseList.forEach(s -> this.course_combo.addItem(s.getCourseName()));
	}

	private void loadRoomForComboBox() {
		this.room_combo.addItem("- Select -");
		roomList = this.roomservice.findAllRoom();
		roomList.forEach(s -> this.room_combo.addItem(s.getRoom_name()));
	}

	private void loadSectionForComboBox() {
		this.section_combo.addItem("- Select -");
		sectionList = this.sectionservice.findAllSection();
		sectionList.forEach(s -> this.section_combo.addItem(s.getSection_DayTime()));
	}

	private void intialize() {
		setBackground(new Color(173, 216, 230));
		setLayout(null);
		setBounds(0, 0, 1300, 700);

		JLabel lblNewLabel = new JLabel("Schedule Management");
		lblNewLabel.setForeground(new Color(138, 43, 226));

		lblNewLabel.setFont(new Font("Myanmar Text", Font.BOLD, 32));
		lblNewLabel.setBounds(446, 10, 381, 45);

		add(lblNewLabel);

		JPanel panel = new JPanel();

		panel.setBounds(33, 90, 321, 487);
		add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();

		panel_1.setBorder(null);
		panel_1.setBounds(20, 51, 284, 340);
		panel.add(panel_1);
		panel_1.setLayout(null);

		end_DateChooser = new JDateChooser();
		end_DateChooser.setBounds(107, 282, 167, 24);

		end_DateChooser.setMinSelectableDate(DateUtils.asDate(DateUtils.asLocalDate(new Date()).plusDays(1)));
		JTextFieldDateEditor editor1 = (JTextFieldDateEditor) end_DateChooser.getDateEditor();
		editor1.setEditable(false);

		panel_1.add(end_DateChooser);

		JLabel lblNewLabel_1_4_1 = new JLabel("End Date");
		lblNewLabel_1_4_1.setBounds(10, 289, 98, 17);
		panel_1.add(lblNewLabel_1_4_1);
		lblNewLabel_1_4_1.setForeground(Color.BLACK);

		start_DateChooser = new JDateChooser();
		start_DateChooser.setBounds(107, 227, 167, 24);
		start_DateChooser.setMinSelectableDate(new Date());
		JTextFieldDateEditor editor = (JTextFieldDateEditor) start_DateChooser.getDateEditor();
		editor.setEditable(false);

		panel_1.add(start_DateChooser);

		JLabel lblNewLabel_1_4 = new JLabel("Start Date");
		lblNewLabel_1_4.setBounds(10, 234, 98, 17);
		panel_1.add(lblNewLabel_1_4);
		lblNewLabel_1_4.setForeground(Color.BLACK);

		section_combo = new JComboBox();
		section_combo.setBounds(107, 173, 167, 30);
		section_combo.setMaximumRowCount(5);
		panel_1.add(section_combo);

		JLabel lblNewLabel_1_2 = new JLabel("Section");
		lblNewLabel_1_2.setBounds(10, 180, 98, 17);
		panel_1.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setForeground(Color.BLACK);

		course_combo = new JComboBox();
		course_combo.setBounds(107, 119, 167, 30);
		AutoCompleteDecorator.decorate(course_combo);
		panel_1.add(course_combo);

		JLabel lblNewLabel_1_1 = new JLabel("Course");
		lblNewLabel_1_1.setBounds(10, 126, 98, 17);
		panel_1.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setForeground(Color.BLACK);

		room_combo = new JComboBox();
		room_combo.setBounds(107, 65, 167, 30);
		panel_1.add(room_combo);

		JLabel lblNewLabel_1_3 = new JLabel("Room");
		lblNewLabel_1_3.setBounds(10, 72, 87, 17);
		panel_1.add(lblNewLabel_1_3);
		lblNewLabel_1_3.setForeground(Color.BLACK);

		teacher_combo = new JComboBox<String>();
		teacher_combo.setBounds(107, 11, 167, 30);
		AutoCompleteDecorator.decorate(teacher_combo);
		panel_1.add(teacher_combo);

		JLabel lblNewLabel_1 = new JLabel("Teacher Name");
		lblNewLabel_1.setBounds(10, 18, 98, 17);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(0, 0, 0));

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 52, 264, 2);
		panel_1.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 106, 264, 2);
		panel_1.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 160, 264, 2);
		panel_1.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 214, 264, 2);
		panel_1.add(separator_3);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 262, 264, 2);
		panel_1.add(separator_4);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(10, 320, 264, 2);
		panel_1.add(separator_5);

		savepanel = new JPanel();
		savepanel.setBorder(null);
		savepanel.setBounds(20, 410, 284, 55);
		panel.add(savepanel);
		savepanel.setLayout(null);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (teacher_combo.getSelectedIndex() == 0 || course_combo.getSelectedIndex() == 0
						|| section_combo.getSelectedIndex() == 0 || room_combo.getSelectedIndex() == 0
						|| start_DateChooser.getDate() == null || end_DateChooser.getDate() == null) {

					JOptionPane.showMessageDialog(null, "required all field");
					return;
				}

				start_Date = DateUtils.asLocalDate(start_DateChooser.getDate());
				end_Date = DateUtils.asLocalDate(end_DateChooser.getDate());

				LocalDate nowdate = DateUtils.asLocalDate(new Date());

				if (nowdate.isAfter(start_Date)) { // check Start Date not later current date
					JOptionPane.showMessageDialog(null, "Invaild Start Date,Please choose again!!Start Date ");
					return;
				}

				if (end_Date.isBefore(start_Date)) { // check End
					JOptionPane.showMessageDialog(null, "Invaild End Date,Please choose again");
					return;
				}

				// get data from form
				Optional<Teacher> selectedTeacher = teacherList.stream()
						.filter(s -> s.getTeacherName().equals(teacher_combo.getSelectedItem())).findFirst(); // get
				Optional<Course> selectedCourse = courseList.stream()
						.filter(s -> s.getCourseName().equals(course_combo.getSelectedItem())).findFirst(); // g
				Optional<Section> selectedSection = sectionList.stream()
						.filter(s -> s.getSection_DayTime().equals(section_combo.getSelectedItem())).findFirst(); // g
				Optional<Room> selectedRoom = roomList.stream()
						.filter(s -> s.getRoom_name().equals(room_combo.getSelectedItem())).findFirst(); // g


				teacherscehduleList = originalScheduleList.stream()
						.filter(s -> s.getTeacher().getTeacherName().equals(selectedTeacher.get().getTeacherName()))
						.collect(Collectors.toList());

				roomscehduleList = originalScheduleList.stream()
						.filter(s -> s.getRoom().getRoom_name().equals(selectedRoom.get().getRoom_name()))
						.collect(Collectors.toList());

				

				boolean teacherbusy = false, roombusy = false;

				teacherbusy = scheduleService.isTeacherBusy(start_Date, end_Date,
						selectedSection.get().getSection_DayTime(), teacherscehduleList);

				roombusy = scheduleService.isRoomBusy(start_Date, selectedSection.get().getSection_DayTime(),
						roomscehduleList);

				if (!teacherbusy && !roombusy) {

					if (selectedTeacher.isPresent() && selectedCourse.isPresent() && selectedSection.isPresent()
							&& selectedRoom.isPresent()) { // create schedule

						Schedule schedule = new Schedule();

						schedule.setCourse(selectedCourse.get());
						schedule.setTeacher(selectedTeacher.get());
						schedule.setRoom(selectedRoom.get());
						schedule.setSection(selectedSection.get());
						schedule.setStart_date(start_Date);
						schedule.setEnd_date(end_Date);

						scheduleService.createSchedule(schedule);

						JOptionPane.showMessageDialog(null, "Succefully Save");
						loadAllSchedules(Optional.empty());
						saveCancelButton.doClick();

					}

				}

			}
		});
		saveButton.setBounds(28, 11, 91, 35);
		savepanel.add(saveButton);

		saveCancelButton = new JButton("Cancel");
		saveCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				resetFormData();
			}
		});
		saveCancelButton.setBounds(169, 11, 91, 35);
		savepanel.add(saveCancelButton);

		createHeading = new JPanel();
		createHeading.setBounds(20, 11, 284, 29);
		panel.add(createHeading);
		createHeading.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Create Schedule");
		lblNewLabel_3.setForeground(new Color(138, 43, 226));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(78, 0, 145, 29);
		createHeading.add(lblNewLabel_3);

		editpanel = new JPanel();
		editpanel.setBounds(20, 410, 284, 55);

		panel.add(editpanel);
		editpanel.setLayout(null);

		savepanel.setVisible(true);
		editpanel.setVisible(false);

		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (teacher_combo.getSelectedIndex() == 0 || course_combo.getSelectedIndex() == 0
						|| section_combo.getSelectedIndex() == 0 || room_combo.getSelectedIndex() == 0
						|| start_DateChooser.getDate() == null || end_DateChooser.getDate() == null) {

					JOptionPane.showMessageDialog(null, "required all field");
					return;
				}

				start_Date = DateUtils.asLocalDate(start_DateChooser.getDate());
				end_Date = DateUtils.asLocalDate(end_DateChooser.getDate());

				LocalDate nowdate = DateUtils.asLocalDate(new Date());

				if (nowdate.isAfter(start_Date)) { // check Start Date not later current date
					JOptionPane.showMessageDialog(null, "Invaild Start Date,Please choose again!!Start Date ");
					return;
				}

				if (end_Date.isBefore(start_Date)) { // check End
					JOptionPane.showMessageDialog(null, "Invaild End Date,Please choose again");
					return;
				}

				// get data from form
				Optional<Teacher> selectedTeacher = teacherList.stream()
						.filter(s -> s.getTeacherName().equals(teacher_combo.getSelectedItem())).findFirst(); // get
				Optional<Course> selectedCourse = courseList.stream()
						.filter(s -> s.getCourseName().equals(course_combo.getSelectedItem())).findFirst(); // g
				Optional<Section> selectedSection = sectionList.stream()
						.filter(s -> s.getSection_DayTime().equals(section_combo.getSelectedItem())).findFirst(); // g
				Optional<Room> selectedRoom = roomList.stream()
						.filter(s -> s.getRoom_name().equals(room_combo.getSelectedItem())).findFirst(); // g

				List<Schedule> editScheduleList = new ArrayList<Schedule>();

				editScheduleList = originalScheduleList.stream()
						.filter(s -> s.getSchedule_id() != selectedSchedule.getSchedule_id())
						.collect(Collectors.toList());

				teacherscehduleList = editScheduleList.stream()
						.filter(s -> s.getTeacher().getTeacherName().equals(selectedTeacher.get().getTeacherName()))
						.collect(Collectors.toList());

				roomscehduleList = editScheduleList.stream()
						.filter(s -> s.getRoom().getRoom_name().equals(selectedRoom.get().getRoom_name()))
						.collect(Collectors.toList());

				boolean teacherbusy = false, roombusy = false;

				teacherbusy = scheduleService.isTeacherBusy(start_Date, end_Date,
						selectedSection.get().getSection_DayTime(), teacherscehduleList);

				roombusy = scheduleService.isRoomBusy(start_Date, selectedSection.get().getSection_DayTime(),
						roomscehduleList);

				if (!teacherbusy && !roombusy) {

					if (selectedTeacher.isPresent() && selectedCourse.isPresent() && selectedSection.isPresent()
							&& selectedRoom.isPresent()) { // update schedule

						Schedule schedule = new Schedule();

						schedule.setCourse(selectedCourse.get());
						schedule.setTeacher(selectedTeacher.get());
						schedule.setRoom(selectedRoom.get());
						schedule.setSection(selectedSection.get());
						schedule.setStart_date(start_Date);
						schedule.setEnd_date(end_Date);

						scheduleService.updateSchedule(selectedSchedule.getSchedule_id(), schedule);

						JOptionPane.showMessageDialog(null, "Succefully Update");

						loadAllSchedules(Optional.empty());
						editCancelButton.doClick();

					}

				}

			}
		});
		editpanel.setLayout(null);
		editButton.setBounds(20, 11, 73, 32);
		editpanel.add(editButton);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null, "Do you really want to Delete?", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

					scheduleService.deleteSchedule(selectedSchedule.getSchedule_id());
					JOptionPane.showMessageDialog(null, "Succefully Delete");
					loadAllSchedules(Optional.empty());
					editCancelButton.doClick();
				}

			}
		});
		deleteButton.setBounds(103, 11, 79, 32);
		editpanel.add(deleteButton);

		editCancelButton = new JButton("Cancel");
		editCancelButton.setBounds(192, 11, 79, 32);

		editpanel.add(editCancelButton);

		editHeading = new JPanel();
		editHeading.setBounds(20, 11, 284, 29);

		panel.add(editHeading);
		editHeading.setLayout(null);

		JLabel lblNewLabel_3_1 = new JLabel("Edit Schedule");
		lblNewLabel_3_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3_1.setBounds(78, 0, 145, 29);
		editHeading.add(lblNewLabel_3_1);
		editCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tableSchedule.getSelectionModel().clearSelection();
				resetFormData();

				resetButtonPanel();
				savepanel.setVisible(true);
				createHeading.setVisible(true);

			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(364, 90, 891, 487);

		tableSchedule = new JTable();
		tableSchedule.setRowHeight(20);
		tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	
		scrollPane.setViewportView(tableSchedule);

		this.tableSchedule.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {

			if (!tableSchedule.getSelectionModel().isSelectionEmpty()) {

				String scheduleid = String.valueOf(IDkeyGenerator
						.stringToID(tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 0).toString()));

				selectedSchedule = originalScheduleList.stream()
						.filter(s -> String.valueOf(s.getSchedule_id()).equals(scheduleid)).findFirst().get();

				teacher_combo.setSelectedItem(selectedSchedule.getTeacher().getTeacherName());
				room_combo.setSelectedItem(selectedSchedule.getRoom().getRoom_name());
				course_combo.setSelectedItem(selectedSchedule.getCourse().getCourseName());
				section_combo.setSelectedItem(selectedSchedule.getSection().getSection_DayTime());

				start_DateChooser.setDate(DateUtils.asDate(selectedSchedule.getStart_date()));
				end_DateChooser.setDate(DateUtils.asDate(selectedSchedule.getEnd_date()));

				resetButtonPanel();
				editHeading.setVisible(true);
				editpanel.setVisible(true);

			}

		});

		add(scrollPane);

		JLabel lblNewLabel_2 = new JLabel("Search By");
		lblNewLabel_2.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		lblNewLabel_2.setBounds(786, 56, 99, 22);
		add(lblNewLabel_2);

		searchByCombo = new JComboBox<String>();

		searchByCombo.addItem("- Select -");
		searchByCombo.addItem("Teacher Name");
		searchByCombo.addItem("Room");
		searchByCombo.addItem("All Field");

		searchByCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				searchDataCombo.removeAllItems();
				searchDataCombo.addItem("- Select -");
				resetSearchDataPanel();
				if (searchByCombo.getSelectedItem().equals("Teacher Name")) {
					teacherList.forEach(s -> searchDataCombo.addItem(s.getTeacherName()));
					loadAllSchedules(Optional.of(originalScheduleList));

					searchDataComboPanel.setVisible(true);

				} else if (searchByCombo.getSelectedItem().equals("Room")) {
					roomList.forEach(r -> searchDataCombo.addItem(r.getRoom_name()));
					loadAllSchedules(Optional.of(originalScheduleList));

					searchDataComboPanel.setVisible(true);
				} else if (searchByCombo.getSelectedItem().equals("All Field")) {

					searchTextFieldPanel.setVisible(true);
				}

				searchDataCombo.setEnabled(true);

			}
		});

		searchByCombo.setBounds(884, 55, 133, 28);
		add(searchByCombo);

		searchDataComboPanel = new JPanel();
		searchDataComboPanel.setBackground(new Color(173, 216, 230));
		searchDataComboPanel.setBounds(1029, 44, 220, 45);

		add(searchDataComboPanel);
		searchDataComboPanel.setLayout(null);

		searchDataCombo = new JComboBox<String>();
		searchDataCombo.setBounds(12, 10, 194, 28);
		searchDataComboPanel.add(searchDataCombo);
		AutoCompleteDecorator.decorate(searchDataCombo);

		searchDataCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (searchByCombo.getSelectedItem().equals("Teacher Name") && searchDataCombo.getSelectedIndex() != 0) {

					loadAllSchedules(Optional.of(originalScheduleList.stream()
							.filter(s -> s.getTeacher().getTeacherName().equals(searchDataCombo.getSelectedItem()))
							.collect(Collectors.toList())));

				} else if (searchByCombo.getSelectedItem().equals("Room") && searchDataCombo.getSelectedIndex() != 0) {
					loadAllSchedules(Optional.of(originalScheduleList.stream()
							.filter(s -> s.getRoom().getRoom_name().equals(searchDataCombo.getSelectedItem()))
							.collect(Collectors.toList())));

				}

			}
		});
		searchDataCombo.setEnabled(false);

		searchTextFieldPanel = new JPanel();
		searchTextFieldPanel.setBackground(new Color(173, 216, 230));
		searchTextFieldPanel.setBounds(1029, 44, 220, 45);
		add(searchTextFieldPanel);
		searchTextFieldPanel.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.setBounds(12, 10, 166, 27);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String keyword = txtSearch.getText();

				List<Schedule> searchList = new ArrayList<Schedule>();
				searchList = originalScheduleList.stream().filter(schedule -> {
					return schedule.getTeacher().getTeacherName().toLowerCase().contains(keyword.toLowerCase())
							|| schedule.getCourse().getCourseName().toLowerCase().contains(keyword.toLowerCase())
							|| schedule.getSection().getSection_DayTime().toLowerCase().contains(keyword.toLowerCase())
							|| schedule.getRoom().getRoom_name().toLowerCase().contains(keyword.toLowerCase())
							|| schedule.getStart_date().toString().toLowerCase().contains(keyword.toLowerCase())
							|| schedule.getEnd_date().toString().toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("SC-", schedule.getSchedule_id()).toLowerCase()
									.contains(keyword.toLowerCase());

				}).collect(Collectors.toList());

				loadAllSchedules(Optional.of(searchList));

			}

		});

		searchTextFieldPanel.add(txtSearch);
		txtSearch.setColumns(10);

	}
}
