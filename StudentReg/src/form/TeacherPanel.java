package form;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import model.Room;
import model.Schedule;
import model.Teacher;
import service.ScheduleService;
import service.StaffService;
import service.TeacherService;
import shared.utils.IDkeyGenerator;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JSeparator;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TeacherPanel extends JPanel {
	private JTextField txtName;
	private JTextField txtNRC;
	private JTextField txtPhno;
	private JTextField txtSearch;
	JTextArea txtAddress;
	TeacherService teacherservice;
	ScheduleService scheduleService;
	
	JComboBox comboPost;
	JPanel savebtnpanel, editbtnpanel, lbleditpanel, lbladdpanel;
	private Teacher teacher;
	private JTable tblTeacher;
	JButton btneditCancel;
	private List<Teacher> originalTeacherList = new ArrayList<>();
	private List<Teacher> teacherlist = new ArrayList<>();
	private DefaultTableModel dtm = new DefaultTableModel();

	/**
	 * Create the panel.
	 */

	public void resertTeacherData() {
		txtName.setText(null);
		txtAddress.setText(null);
		txtNRC.setText(null);
		txtPhno.setText(null);
		comboPost.setSelectedIndex(0);

	}

	public void resetPanel() {
		savebtnpanel.setVisible(false);
		editbtnpanel.setVisible(false);
		lbladdpanel.setVisible(false);
		lbleditpanel.setVisible(false);
	}

	public void intializeDepency() {
		// TODO Auto-generated method stub
		this.teacherservice = new TeacherService();
		this.scheduleService=new ScheduleService();

	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Address");
		dtm.addColumn("NRC");
		dtm.addColumn("Phone");
		dtm.addColumn("POst");

		this.tblTeacher.setModel(dtm);
	}

	private void loadAllTeacher(Optional<List<Teacher>> optionalTeacher) {
		this.dtm = (DefaultTableModel) this.tblTeacher.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		originalTeacherList = this.teacherservice.findAllTeacher();
		teacherlist = optionalTeacher.orElseGet(() -> originalTeacherList);

		teacherlist.forEach(e -> {
			Object[] row = new Object[6];
			row[0] = IDkeyGenerator.idToString("TE-",e.getTeacher_no());
			row[1] = e.getTeacherName();
			row[2] = e.getTeacherAddress();
			row[3] = e.getTeacherNRC();
			row[4] = e.getTeacherPhno();
			row[5] = e.getTeacherPost();
			dtm.addRow(row);
		});

		this.tblTeacher.setModel(dtm);
	}

	private void loadPostForComboBox() {
		this.comboPost.addItem("- Select -");
		this.comboPost.addItem("Instructor");
		this.comboPost.addItem("Lecture");
		this.comboPost.addItem("Demo");

	}

	public boolean isDuplicate(String NRC, String phno, List<Teacher> teacherlist) {
		boolean nrcDup = false, phnoDup = false;
		for (Teacher t : teacherlist) {

			nrcDup = t.getTeacherNRC().equals(NRC);
			phnoDup = t.getTeacherPhno().equals(phno);
			if (nrcDup || phnoDup) {
				return true;
			}
		}

		return false;

	}

	public TeacherPanel() {
		intializeDepency();
		setBackground(new Color(173, 216, 230));
		setLayout(null);
		setBounds(0, 0, 1300, 700);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(456, 92, 788, 505);
		add(scrollPane);

		tblTeacher = new JTable();
		tblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(tblTeacher);
		this.tblTeacher.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblTeacher.getSelectionModel().isSelectionEmpty()) {
				
				String id = String.valueOf(IDkeyGenerator.stringToID(tblTeacher.getValueAt(tblTeacher.getSelectedRow(), 0).toString()));
				teacher = teacherservice.findById(id);
				txtName.setText(teacher.getTeacherName());
				txtAddress.setText(teacher.getTeacherAddress());
				txtNRC.setText(teacher.getTeacherNRC());
				txtPhno.setText(teacher.getTeacherPhno());
				comboPost.setSelectedItem(teacher.getTeacherPost());
				resetPanel();
				editbtnpanel.setVisible(true);
				lbleditpanel.setVisible(true);

			}
		});

		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String keyword = txtSearch.getText();

				List<Teacher> searchList = new ArrayList<Teacher>();
				searchList = originalTeacherList.stream().filter(teacher -> {
					return teacher.getTeacherName().toLowerCase().contains(keyword.toLowerCase())
							|| teacher.getTeacherAddress().toLowerCase().contains(keyword.toLowerCase())
							|| teacher.getTeacherNRC().toLowerCase().contains(keyword.toLowerCase())
							|| teacher.getTeacherPhno().toLowerCase().contains(keyword.toLowerCase())
							|| teacher.getTeacherPost().toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("TE-",teacher.getTeacher_no()).toLowerCase().contains(keyword.toLowerCase());

				}).collect(Collectors.toList());

				loadAllTeacher(Optional.of(searchList));

			}

		});

		txtSearch.setColumns(10);
		txtSearch.setBounds(1075, 55, 166, 27);
		add(txtSearch);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(55, 92, 396, 505);
		add(panel_2);
		panel_2.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 79, 373, 337);
		panel_2.add(panel);
		panel.setLayout(null);

		txtAddress = new JTextArea();
		txtAddress.setBounds(116, 180, 230, 84);
		panel.add(txtAddress);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(SystemColor.desktop);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAddress.setBounds(40, 187, 75, 14);
		panel.add(lblAddress);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Post");
		lblNewLabel_1_1_1_1.setForeground(SystemColor.desktop);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_1.setBounds(40, 295, 75, 14);
		panel.add(lblNewLabel_1_1_1_1);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(116, 27, 230, 27);
		panel.add(txtName);

		txtNRC = new JTextField();
		txtNRC.setColumns(10);
		txtNRC.setBounds(116, 79, 230, 27);
		panel.add(txtNRC);

		txtPhno = new JTextField();
		txtPhno.setColumns(10);
		txtPhno.setBounds(116, 130, 230, 27);
		panel.add(txtPhno);

		JLabel lblNewLabel = new JLabel(" Name");
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(40, 32, 75, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1_1 = new JLabel("NRC");
		lblNewLabel_1_1.setForeground(SystemColor.desktop);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(43, 84, 67, 14);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("PhoneNumber");
		lblNewLabel_1_1_1.setForeground(SystemColor.desktop);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_1.setBounds(43, 136, 75, 14);
		panel.add(lblNewLabel_1_1_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(31, 65, 315, 19);
		panel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 117, 315, 8);
		panel.add(separator_1);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(28, 168, 318, 8);
		panel.add(separator_1_1);

		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(28, 275, 318, 8);
		panel.add(separator_1_1_1);

		comboPost = new JComboBox();
		comboPost.setBackground(Color.WHITE);
		comboPost.setBounds(116, 292, 230, 27);
		panel.add(comboPost);

		savebtnpanel = new JPanel();
		savebtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		savebtnpanel.setBounds(10, 427, 373, 69);
		panel_2.add(savebtnpanel);
		savebtnpanel.setLayout(null);

		JButton btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(TeacherPanel.class.getResource("/icon/Add.png")));
		btnAdd.setBounds(43, 15, 95, 38);
		savebtnpanel.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Teacher teacher = new Teacher();
				if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtNRC.getText().isEmpty()
						|| txtPhno.getText().isEmpty() || comboPost.getSelectedIndex()== 0) {
					JOptionPane.showMessageDialog(panel, "required field");
				} else {

					if (!txtName.getText().matches("[a-zA-Z ]+")) {
						JOptionPane.showMessageDialog(panel, "Your Teacher Name is something wrong");
						return;

					}
					if (isDuplicate(txtNRC.getText(), txtPhno.getText(), originalTeacherList)) {
						JOptionPane.showMessageDialog(panel, "Your NRC or Phone Number is existing number");
						return;

					} else {
						if (txtNRC.getText().matches("[0-9]+")) {

							JOptionPane.showMessageDialog(panel, "Your NRC number is invalid");

							return;
						}
						if (!txtPhno.getText().matches("[0-9]+") || txtPhno.getText().toString().length() < 5
								|| txtPhno.getText().length() > 15) {
							JOptionPane.showMessageDialog(panel, "Your phone number is invalid");
							return;

						}
					}

					if (txtAddress.getText().matches("[0-9]+")) {

						JOptionPane.showMessageDialog(panel, "Your address  something wrong");
						return;

					}

					teacher.setTeacherName(txtName.getText());
					teacher.setTeacherAddress(txtAddress.getText());
					teacher.setTeacherNRC(txtNRC.getText());
					teacher.setTeacherPhno(txtPhno.getText());
					teacher.setTeacherPost(comboPost.getSelectedItem().toString());
					teacherservice.createTeacher(teacher);
					resertTeacherData();
					loadAllTeacher(Optional.empty());
				}


			}
		});

		btnAdd.setForeground(Color.BLACK);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btneditCancel_1 = new JButton("Cancel");
		btneditCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resertTeacherData();
			}
		});
		btneditCancel_1.setIcon(new ImageIcon(TeacherPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btneditCancel_1.setForeground(Color.BLACK);
		btneditCancel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btneditCancel_1.setBounds(226, 15, 95, 38);
		savebtnpanel.add(btneditCancel_1);

		editbtnpanel = new JPanel();
		editbtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		editbtnpanel.setBounds(10, 427, 373, 69);
		panel_2.add(editbtnpanel);
		editbtnpanel.setLayout(null);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(20, 15, 95, 38);
		editbtnpanel.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtNRC.getText().isEmpty()
						|| txtPhno.getText().isEmpty() || comboPost.getSelectedIndex()== 0) 
					 {
							JOptionPane.showMessageDialog(panel, "Required field");
							return;
						}
				else {
				
					if (!txtName.getText().matches("[a-zA-Z ]+")) {
						JOptionPane.showMessageDialog(panel, "Your Teacher Name is something wrong");
						return;
					}
					if (txtNRC.getText().matches("[0-9]+")) {

						JOptionPane.showMessageDialog(panel, "Your NRC number is invalid");

						return;
					}
					if (!txtPhno.getText().matches("[0-9]+") || txtPhno.getText().toString().length() < 5
							|| txtPhno.getText().length() > 15) {
						JOptionPane.showMessageDialog(panel, "Your phone number is invalid");
						return;

					}

					if (txtAddress.getText().matches("[0-9]+")) {
						JOptionPane.showMessageDialog(panel, "Your address is something wrong");
						return;
					}
					
					List<Teacher> editTeacherList=new ArrayList<Teacher>();
					
					editTeacherList=originalTeacherList.stream()
							.filter(s -> s.getTeacher_no()!=teacher.getTeacher_no())  // filter teachers not contain selected teacher
							.collect(Collectors.toList());
					
					
					if (isDuplicate(txtNRC.getText(), txtPhno.getText(), editTeacherList)) {
						JOptionPane.showMessageDialog(panel, "Your NRC or Phone Number is existing number");
						return;

					}
					

					teacher.setTeacherName(txtName.getText());
					teacher.setTeacherNRC(txtNRC.getText());
					teacher.setTeacherPhno(txtPhno.getText());
					teacher.setTeacherAddress(txtAddress.getText());
					teacher.setTeacherPost(comboPost.getSelectedItem().toString());

					teacherservice.updateTeacher(String.valueOf(teacher.getTeacher_no()), teacher);
					teacher = null;
					
				} 

				loadAllTeacher(Optional.empty());
				resertTeacherData();
				btneditCancel.doClick();
				
				
				
			}
			
			

		});
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(138, 15, 95, 38);
		editbtnpanel.add(btnDelete);
		btnDelete.setIcon(new ImageIcon(TeacherPanel.class.getResource("/icon/Delete.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null, "Do you really want to Delete?", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

					teacherservice.deleteTeacher(String.valueOf(teacher.getTeacher_no()));
					
					 List<Schedule> deleteScheduleList = new ArrayList<>();
					 List<Schedule> originalScheduleList = new ArrayList<>();
					 
					 originalScheduleList=scheduleService.findAllSchedules(); // select all
					 
					 deleteScheduleList=originalScheduleList.stream()
								.filter(s -> s.getTeacher().getTeacher_no()==(teacher.getTeacher_no()))  // filter by deleted teacher id
								.collect(Collectors.toList());
					 
					deleteScheduleList.forEach(s->scheduleService.deleteSchedule(s.getSchedule_id())); // delete
					
					loadAllTeacher(Optional.empty());
					
					resertTeacherData();
					JOptionPane.showMessageDialog(null, "Succefully Delete");
					btneditCancel.doClick();

				}

			}
		});

		btnDelete.setForeground(Color.BLACK);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btneditCancel = new JButton("Cancel");
		btneditCancel.setIcon(new ImageIcon(TeacherPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btneditCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tblTeacher.getSelectionModel().clearSelection();
				resertTeacherData();
				resetPanel();
				savebtnpanel.setVisible(true);
				lbladdpanel.setVisible(true);
			}
		});
		btneditCancel.setForeground(Color.BLACK);
		btneditCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btneditCancel.setBounds(260, 15, 95, 38);
		editbtnpanel.add(btneditCancel);

		lbleditpanel = new JPanel();
		lbleditpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbleditpanel.setBounds(10, 11, 373, 57);
		panel_2.add(lbleditpanel);
		lbleditpanel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Edit Teacher Information");
		lblNewLabel_1.setBounds(59, 11, 285, 31);
		lbleditpanel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lbladdpanel = new JPanel();
		lbladdpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbladdpanel.setBounds(10, 11, 373, 57);
		panel_2.add(lbladdpanel);
		lbladdpanel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Create Teacher Information");
		lblNewLabel_2.setBounds(59, 11, 285, 31);
		lbladdpanel.add(lblNewLabel_2);
		lblNewLabel_2.setForeground(new Color(138, 43, 226));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblTeacherInformationList = new JLabel("Teacher information List");
		lblTeacherInformationList.setForeground(new Color(138, 43, 226));
		lblTeacherInformationList.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTeacherInformationList.setBounds(418, 11, 464, 33);
		add(lblTeacherInformationList);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.BLACK);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSearch.setBounds(1008, 61, 55, 14);
		add(lblSearch);
		setTableDesign();
		loadAllTeacher(Optional.empty());
		resetPanel();
		lbladdpanel.setVisible(true);
		savebtnpanel.setVisible(true);
		loadPostForComboBox();

	}
}
