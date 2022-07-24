package form;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import model.Student;
import model.Teacher;
import service.StudentService;
import shared.utils.IDkeyGenerator;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
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
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

public class StudentPanel extends JPanel {
	private StudentService studentservice;
	private Student student;
	private List<Student> originalStudentList = new ArrayList<>();
	private DefaultTableModel dtm = new DefaultTableModel();
	protected Component panel;
	private JTextField txtName;
	private JTextField txtPhno;
	private JTextField txtNRC;
	private JTextArea txtAddress;
	private JTable tblStudent;
	private List<Student> studentlist = new ArrayList<>();
	JPanel savebtnpanel, editbtnpanel;
	JPanel lbladdpanel, lbleditpanel;
	private JTextField txtSearch;
	JButton btneditCancel;

	/**
	 * Create the panel.
	 */
	public boolean isDuplicate(String NRC, String phno, List<Student> studentlist) {
		boolean nrcDup = false, phnoDup = false;
		for (Student s : studentlist) {
			nrcDup = s.getStdNRC().equals(NRC);
			phnoDup = s.getStdPhno().equals(phno);
			if (nrcDup || phnoDup) {
				return true;
			}
		}

		return false;

	}

	private void intializeDepency() {
		// TODO Auto-generated method stub
		this.studentservice = new StudentService();

	}

	public void resetStudentData() {
		txtName.setText(null);
		txtAddress.setText(null);
		txtNRC.setText(null);
		txtPhno.setText(null);
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Address");
		dtm.addColumn("NRC");
		dtm.addColumn("Phone");
		this.tblStudent.setModel(dtm);
	}

	public void resetPanel() {
		savebtnpanel.setVisible(false);
		editbtnpanel.setVisible(false);
		lbladdpanel.setVisible(false);
		lbleditpanel.setVisible(false);
	}

	private void loadAllStudent(Optional<List<Student>> optionalStudent) {
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.originalStudentList = this.studentservice.findAllStudent();
		studentlist = optionalStudent.orElseGet(() -> originalStudentList);

		studentlist.forEach(e -> {
			Object[] row = new Object[5];

			row[0] = IDkeyGenerator.idToString("ST-", e.getStdNo());
			row[1] = e.getStdName();
			row[2] = e.getStdAddress();
			row[3] = e.getStdNRC();
			row[4] = e.getStdPhno();
			dtm.addRow(row);
		});
	}

	public StudentPanel() {
		intializeDepency();
		setBackground(new Color(173, 216, 230));
		// setBackground(new Color(250, 250, 250));
		setLayout(null);
		setBounds(0, 0, 1300, 700);

		JLabel lblNewLabel = new JLabel("Student information List");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblNewLabel.setBounds(487, 28, 464, 33);
		lblNewLabel.setForeground(new Color(138, 43, 226));
		add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(445, 127, 761, 453);
		add(scrollPane);

		tblStudent = new JTable();

		scrollPane.setViewportView(tblStudent);
		this.tblStudent.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblStudent.getSelectionModel().isSelectionEmpty()) {

				String id = String.valueOf(
						IDkeyGenerator.stringToID(tblStudent.getValueAt(tblStudent.getSelectedRow(), 0).toString()));

				student = studentservice.findById(id);
				txtName.setText(student.getStdName());
				txtAddress.setText(student.getStdAddress());
				txtNRC.setText(student.getStdNRC());
				txtPhno.setText(student.getStdPhno());
				resetPanel();
				editbtnpanel.setVisible(true);
				lbleditpanel.setVisible(true);

			}
		});

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(74, 127, 361, 453);
		add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 70, 343, 282);
		panel_3.add(panel_1);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setLayout(null);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(99, 24, 208, 27);
		panel_1.add(txtName);

		JLabel lblNewLabel_1_1_2 = new JLabel("NRC");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1_2.setBounds(30, 78, 51, 14);
		panel_1.add(lblNewLabel_1_1_2);

		JLabel lblNewLabel_1_2 = new JLabel("Name");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(30, 27, 67, 14);
		panel_1.add(lblNewLabel_1_2);

		txtAddress = new JTextArea();
		txtAddress.setBounds(99, 182, 208, 89);
		panel_1.add(txtAddress);

		txtPhno = new JTextField();
		txtPhno.setColumns(10);
		txtPhno.setBounds(99, 131, 208, 27);
		panel_1.add(txtPhno);

		JLabel lblAddress_1 = new JLabel("Address");
		lblAddress_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAddress_1.setBounds(30, 187, 61, 14);
		panel_1.add(lblAddress_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("PhoneNumber");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_1_1.setBounds(30, 137, 73, 14);
		panel_1.add(lblNewLabel_1_1_1_1);

		txtNRC = new JTextField();
		txtNRC.setColumns(10);
		txtNRC.setBounds(99, 78, 208, 27);
		panel_1.add(txtNRC);

		JSeparator separator = new JSeparator();
		separator.setBounds(26, 65, 281, 14);
		panel_1.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(26, 117, 281, 9);
		panel_1.add(separator_1);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(26, 169, 281, 9);
		panel_1.add(separator_1_1);

		savebtnpanel = new JPanel();
		savebtnpanel.setBounds(10, 363, 343, 80);
		panel_3.add(savebtnpanel);
		savebtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		savebtnpanel.setLayout(null);

		JButton btnAdd = new JButton("Add ");
		btnAdd.setIcon(new ImageIcon(StudentPanel.class.getResource("/icon/Add.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Student student = new Student();
				if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtNRC.getText().isEmpty()
						|| txtPhno.getText().isEmpty()) {
					JOptionPane.showMessageDialog(panel, "required field");
				} else {

					if (!txtName.getText().matches("[a-zA-Z ]+")) {
						JOptionPane.showMessageDialog(panel, "Your Student Name is something wrong");
						return;

					}
					if (isDuplicate(txtNRC.getText(), txtPhno.getText(), originalStudentList)) {
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

					student.setStdName(txtName.getText());
					student.setStdAddress(txtAddress.getText());
					student.setStdNRC(txtNRC.getText());
					student.setStdPhno(txtPhno.getText());
					studentservice.createStudent(student);
					loadAllStudent(Optional.empty());
					resetStudentData();
				}

			}

		});

		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnAdd.setBounds(41, 25, 100, 33);
		savebtnpanel.add(btnAdd);

		JButton btnsaveCancel = new JButton("Cancel");
		btnsaveCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				resetStudentData();
			}
		});
		btnsaveCancel.setIcon(new ImageIcon(StudentPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btnsaveCancel.setBounds(198, 25, 102, 33);
		savebtnpanel.add(btnsaveCancel);
		btnsaveCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lbladdpanel = new JPanel();
		lbladdpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbladdpanel.setBounds(12, 11, 341, 48);
		panel_3.add(lbladdpanel);
		lbladdpanel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Add Student Infomation");
		lblNewLabel_3.setForeground(new Color(138, 43, 226));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(60, 11, 253, 23);
		lbladdpanel.add(lblNewLabel_3);

		lbleditpanel = new JPanel();
		lbleditpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbleditpanel.setLayout(null);
		lbleditpanel.setBounds(12, 11, 341, 48);
		panel_3.add(lbleditpanel);

		JLabel lblNewLabel_3_1 = new JLabel("Edit Student Infomation");
		lblNewLabel_3_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3_1.setBounds(60, 11, 253, 23);
		lbleditpanel.add(lblNewLabel_3_1);

		editbtnpanel = new JPanel();
		editbtnpanel.setLayout(null);
		editbtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		editbtnpanel.setBounds(10, 363, 343, 80);
		panel_3.add(editbtnpanel);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(23, 24, 91, 34);
		editbtnpanel.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtNRC.getText().isEmpty()
						|| txtPhno.getText().isEmpty()) {

					JOptionPane.showMessageDialog(panel, "required field");

				} else {

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

					List<Student> editStudentList = new ArrayList<Student>();

					editStudentList = originalStudentList.stream()
							.filter(s -> s.getStdNo() != student.getStdNo())     // Filter edit student
							.collect(Collectors.toList());

					if (isDuplicate(txtNRC.getText(), txtPhno.getText(), editStudentList)) {
						JOptionPane.showMessageDialog(panel, "Your NRC or Phone Number is existing number");
						return;

					}

					student.setStdName(txtName.getText());
					student.setStdAddress(txtAddress.getText());
					student.setStdNRC(txtNRC.getText());
					student.setStdPhno(txtPhno.getText());
					studentservice.updateStudent(String.valueOf(student.getStdNo()), student);
					loadAllStudent(Optional.empty());
					btneditCancel.doClick();
					student = null;
				}

			}
		});

		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btneditCancel = new JButton("Cancel");
		btneditCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tblStudent.getSelectionModel().clearSelection();
				resetStudentData();
				resetPanel();
				savebtnpanel.setVisible(true);
				lbladdpanel.setVisible(true);
			}
		});
		btneditCancel.setIcon(new ImageIcon(StudentPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btneditCancel.setBounds(230, 24, 95, 34);
		editbtnpanel.add(btneditCancel);
		btneditCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnDelete = new JButton("Delete");
		btnDelete.setIcon(new ImageIcon(StudentPanel.class.getResource("/icon/Delete.png")));
		btnDelete.setBounds(122, 24, 93, 34);
		editbtnpanel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null, "Do you really want to Delete?", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

					studentservice.deleteStudent(String.valueOf(student.getStdNo()));
					resetStudentData();
					loadAllStudent(Optional.empty());
					JOptionPane.showMessageDialog(null, "Succefully Delete");
					btneditCancel.doClick();

				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBounds(1023, 85, 183, 28);

		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String keyword = txtSearch.getText();

				List<Student> searchList = new ArrayList<Student>();
				searchList = originalStudentList.stream().filter(student -> {
					return student.getStdName().toLowerCase().contains(keyword.toLowerCase())
							|| student.getStdAddress().toLowerCase().contains(keyword.toLowerCase())
							|| student.getStdNRC().toLowerCase().contains(keyword.toLowerCase())
							|| student.getStdPhno().toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("ST-", student.getStdNo()).toLowerCase()
									.contains(keyword.toLowerCase());

				}).collect(Collectors.toList());

				loadAllStudent(Optional.of(searchList));

			}

		});

		add(txtSearch);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.BLACK);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSearch.setBounds(963, 91, 55, 14);
		add(lblSearch);
		setTableDesign();
		loadAllStudent(Optional.empty());
		resetPanel();
		savebtnpanel.setVisible(true);
		lbladdpanel.setVisible(true);
	}
}
