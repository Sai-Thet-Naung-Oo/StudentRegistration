package form;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import model.Course;
import model.Schedule;
import model.StaffInfo;
import model.Student;
import model.Teacher;
import service.CourseService;
import service.ScheduleService;
import service.StaffService;
import shared.utils.IDkeyGenerator;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;

public class CoursePanel extends JPanel {

	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtSearch;
	JButton btnSave;
	private JTable tblCourse;
	private Course course;
	JButton btneditCancel;
	private CourseService courseservice;
	ScheduleService scheduleService;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<Course> filteredcourselist = new ArrayList<>();
	private List<Course> courselist = new ArrayList<>();
	JPanel lbladdpanel, lbleditpanel, editbtnpanel, savebtnpanel;

	/**
	 * Create the panel.
	 */
	public void resertCourseData() {
		txtName.setText("");
		txtPrice.setText("");

	}

	public void resertPanel() {
		savebtnpanel.setVisible(false);
		editbtnpanel.setVisible(false);
		lbladdpanel.setVisible(false);
		lbleditpanel.setVisible(false);
	}

	public boolean isduplicate(String cName, List<Course> courselist) {
		boolean isname = false;
		for (Course c : courselist) {
			isname = c.getCourseName().equals(cName);
			if (isname) {
				return true;
			}
		}
		

		return false;
	}

	public void intializeDepency() {
		// TODO Auto-generated method stub
		courseservice = new CourseService();
		scheduleService=new ScheduleService();

	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Price");

		this.tblCourse.setModel(dtm);
	}

	private void loadAllCourse(Optional<List<Course>> optionalcourse) {

		this.dtm = (DefaultTableModel) this.tblCourse.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.filteredcourselist = this.courseservice.findAllCourse();
		courselist = optionalcourse.orElseGet(() -> filteredcourselist);

		courselist.forEach(e -> {
			Object[] row = new Object[3];
			
			row[0] = IDkeyGenerator.idToString("CO-",e.getCourse_id());
			row[1] = e.getCourseName();
			row[2] = e.getPrice();

			dtm.addRow(row);
		});

		this.tblCourse.setModel(dtm);
	}

	public CoursePanel() {
		intializeDepency();
		setBackground(new Color(173, 216, 230));
		setBounds(0, 0, 1300, 700);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(445, 163, 736, 381);
		add(scrollPane);

		tblCourse = new JTable();
		tblCourse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(tblCourse);
		this.tblCourse.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {

			if (!tblCourse.getSelectionModel().isSelectionEmpty()) {

				String id = String.valueOf(IDkeyGenerator.stringToID(tblCourse.getValueAt(tblCourse.getSelectedRow(), 0).toString()));
				
				course = courseservice.findById(id);

				txtName.setText(course.getCourseName());
				txtPrice.setText(String.valueOf(course.getPrice()));
				resertPanel();
				editbtnpanel.setVisible(true);
				lbleditpanel.setVisible(true);

			}
		});

		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBounds(983, 124, 198, 29);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String keyword = txtSearch.getText();

				List<Course> searchList = new ArrayList<Course>();
				searchList = filteredcourselist.stream().filter(course -> {
					return course.getCourseName().toLowerCase().contains(keyword.toLowerCase())
							|| String.valueOf(course.getPrice()).toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("CO-",course.getCourse_id()).toLowerCase().contains(keyword.toLowerCase());

				}).collect(Collectors.toList());
				loadAllCourse(Optional.of(searchList));

			}

		});
		
		
		add(txtSearch);

		JLabel lblNewLabel_2 = new JLabel(" Course Inforamtion List");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblNewLabel_2.setForeground(new Color(138, 43, 226));
		lblNewLabel_2.setBounds(498, 33, 469, 29);
		add(lblNewLabel_2);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(94, 165, 341, 379);
		add(panel_2);
		panel_2.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 64, 323, 211);
		panel_2.add(panel);
		panel.setLayout(null);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(21, 101, 281, 9);
		panel.add(separator_1_1);

		txtName = new JTextField();
		txtName.setBounds(104, 37, 198, 29);
		panel.add(txtName);
		txtName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Course Name");
		lblNewLabel.setBounds(21, 43, 84, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblNewLabel_1 = new JLabel("Price");
		lblNewLabel_1.setBounds(20, 142, 45, 14);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtPrice = new JTextField();
		txtPrice.setBounds(104, 136, 198, 29);
		panel.add(txtPrice);
		txtPrice.setColumns(10);

		savebtnpanel = new JPanel();
		savebtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		savebtnpanel.setBounds(10, 286, 323, 81);
		panel_2.add(savebtnpanel);
		savebtnpanel.setLayout(null);
		btnSave = new JButton("Add");
		btnSave.setBounds(34, 26, 90, 36);
		savebtnpanel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Course course = new Course();
				if (txtName.getText().isEmpty() || txtPrice.getText().isEmpty()) {

					JOptionPane.showMessageDialog(panel, "required field");
				} else {
					if (isduplicate(txtName.getText(), courselist)) {
						JOptionPane.showMessageDialog(panel, "Your Course Name is existing Number");
						return;
					} else {
						if (txtName.getText().matches("[0-9]+")) {
							JOptionPane.showMessageDialog(panel, "Your Course Name is invalid");
							return;
						}
					}
					if (!txtPrice.getText().matches("[0-9]+")) {
						JOptionPane.showMessageDialog(panel, "Enter digit Number eg:1000");
						return;

					}
					course.setCourseName(txtName.getText());
					course.setPrice(Integer.parseInt(txtPrice.getText()));
					courseservice.createCourse(course);
					resertCourseData();
					loadAllCourse(Optional.empty());
					course = null;
				}

			}
		});
		btnSave.setIcon(new ImageIcon(CoursePanel.class.getResource("/icon/Add.png")));
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnaddCancel = new JButton("Cancel");
		btnaddCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resertCourseData();
			}
		});
		btnaddCancel.setIcon(new ImageIcon(CoursePanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btnaddCancel.setForeground(Color.BLACK);
		btnaddCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnaddCancel.setBounds(194, 27, 90, 36);
		savebtnpanel.add(btnaddCancel);

		lbladdpanel = new JPanel();
		lbladdpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbladdpanel.setBounds(10, 11, 323, 42);
		panel_2.add(lbladdpanel);
		lbladdpanel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Add Course Infomation");
		lblNewLabel_3.setForeground(new Color(138, 43, 226));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(47, 11, 246, 23);
		lbladdpanel.add(lblNewLabel_3);

		lbleditpanel = new JPanel();
		lbleditpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbleditpanel.setBounds(10, 11, 323, 42);
		panel_2.add(lbleditpanel);
		lbleditpanel.setLayout(null);

		JLabel lblNewLabel_3_1 = new JLabel("Edit Course Infomation");
		lblNewLabel_3_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3_1.setBounds(47, 11, 246, 23);
		lbleditpanel.add(lblNewLabel_3_1);

		editbtnpanel = new JPanel();
		editbtnpanel.setLayout(null);
		editbtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		editbtnpanel.setBounds(10, 286, 323, 81);
		panel_2.add(editbtnpanel);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(10, 20, 90, 36);
		editbtnpanel.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText().isEmpty() || txtPrice.getText().isEmpty()) {

					JOptionPane.showMessageDialog(panel, "required field");
				} else {
					if (!txtName.getText().matches("[a-zA-Z ]+")) {
						JOptionPane.showMessageDialog(panel, "Your Course Name is invalid");
						return;
					}

					if (!txtPrice.getText().matches("[0-9]+")) {
						JOptionPane.showMessageDialog(panel, "Enter digit Number eg:1000");
						return;
					}
					
					List<Course> editCourseList = new ArrayList<Course>();

					editCourseList = courselist.stream()
							.filter(s -> s.getCourse_id() != course.getCourse_id())     // Filter edit course
							.collect(Collectors.toList());
					
					
					if (isduplicate(txtName.getText(), editCourseList)) {
						JOptionPane.showMessageDialog(panel, "Your Course Name is existing Number");
						return;
					}
					
					
					course.setCourseName(txtName.getText());
					course.setPrice(Integer.parseInt(txtPrice.getText()));
					courseservice.updateCourse(String.valueOf(course.getCourse_id()), course);
					course = null;
					btneditCancel.doClick();
					loadAllCourse(Optional.empty());
				}
			}
		});
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(118, 20, 90, 36);
		editbtnpanel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null, "Do you really want to Delete?", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

					courseservice.deleteCourse(String.valueOf(course.getCourse_id()));
					
					 List<Schedule> deleteScheduleList = new ArrayList<>();
					 List<Schedule> originalScheduleList = new ArrayList<>();
					 
					 originalScheduleList=scheduleService.findAllSchedules(); // select all
					 
					 deleteScheduleList=originalScheduleList.stream()
								.filter(c -> c.getCourse().getCourse_id()==(course.getCourse_id()))  // filter  delete by course id
								.collect(Collectors.toList());
					 
					deleteScheduleList.forEach(s->scheduleService.deleteSchedule(s.getSchedule_id())); // delete
					
					
					
					loadAllCourse(Optional.empty());
					resertCourseData();
					JOptionPane.showMessageDialog(null, "Succefully Delete");
					btneditCancel.doClick();

				}
			}
		});
		btnDelete.setIcon(new ImageIcon(CoursePanel.class.getResource("/icon/Delete.png")));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 12));

		btneditCancel = new JButton("Cancel");
		btneditCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tblCourse.getSelectionModel().clearSelection();
				resertPanel();
				resertCourseData();
				savebtnpanel.setVisible(true);
				lbladdpanel.setVisible(true);

			}
		});
		btneditCancel.setIcon(new ImageIcon(CoursePanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btneditCancel.setForeground(Color.BLACK);
		btneditCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btneditCancel.setBounds(223, 20, 90, 36);
		editbtnpanel.add(btneditCancel);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.BLACK);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSearch.setBounds(916, 132, 55, 14);
		add(lblSearch);
		setTableDesign();
		loadAllCourse(Optional.empty());
		resertPanel();
		savebtnpanel.setVisible(true);
		lbladdpanel.setVisible(true);

	}
}
