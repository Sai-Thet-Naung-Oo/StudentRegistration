package form;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import model.Staff;
import model.StaffInfo;
import model.Student;
import model.Teacher;
import service.StaffService;
import shared.utils.IDkeyGenerator;

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
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class StaffPanel extends JPanel {
	private JTextField txtName;
	private JTextField txtPhno;
	private JTextField txtNRC;
	private JTable tblStaff;
	private JTextArea txtAddress;
	private JTextField txtSearch;
	JButton btneditCancel;
	JPanel editbtnpanel;
	JPanel savebtnpanel;
	StaffService staffservice;
	JPanel lbladdpanel;
	JPanel lbleditpanel;
	JComboBox cboRole;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Staff staff;
	List<Staff> originalStaffList = new ArrayList<>();
	private List<Staff> stafflist = new ArrayList<>();

	/**
	 * Create the panel.
	 */

	public void resertStaffData() {
		txtName.setText(null);
		txtAddress.setText(null);
		txtPhno.setText(null);
		txtNRC.setText(null);
		cboRole.setSelectedIndex(0);
	}

	public boolean isDuplicate(String NRC, String phno, List<Staff> stafflist) {

		boolean nrcDup = false, phnoDup = false;
		for (Staff s : stafflist) {
			nrcDup = s.getStaffNRC().equals(NRC);
			phnoDup = s.getStdaffPhno().equals(phno);

			if (nrcDup || phnoDup) {
				return true;
			}
		}
		return false;

	}

	public void resertPanel() {
		savebtnpanel.setVisible(false);
		editbtnpanel.setVisible(false);
		lbladdpanel.setVisible(false);
		lbleditpanel.setVisible(false);
	}

	public void intializeDepency() {
		// TODO Auto-generated method stub
		staffservice = new StaffService();

	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Address");
		dtm.addColumn("NRC");
		dtm.addColumn("Phone");
		dtm.addColumn("Role");
		this.tblStaff.setModel(dtm);
	}

	public void loadAllStaff(Optional<List<Staff>> optionalStaff) {

		this.dtm = (DefaultTableModel) this.tblStaff.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		originalStaffList = this.staffservice.findAllStaff();
		stafflist = optionalStaff.orElseGet(() -> originalStaffList);

		stafflist.forEach(e -> {
			Object[] row = new Object[6];

			row[0] = IDkeyGenerator.idToString("SF-", e.getStaffNo());
			row[1] = e.getStaffName();
			row[2] = e.getStaffAddress();
			row[3] = e.getStaffNRC();
			row[4] = e.getStdaffPhno();
			row[5] = e.getRole();

			dtm.addRow(row);
		});

		this.tblStaff.setModel(dtm);
	}

	public StaffPanel() {
		intializeDepency();
		setBackground(new Color(173, 216, 230));
		setBounds(0, 0, 1300, 700);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Staff Information List");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblNewLabel.setForeground(new Color(138, 43, 226));
		lblNewLabel.setBounds(453, 28, 367, 25);
		add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(485, 122, 697, 479);
		add(scrollPane);

		tblStaff = new JTable();
		tblStaff.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(tblStaff);
		this.tblStaff.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblStaff.getSelectionModel().isSelectionEmpty()) {

				String id = String.valueOf(
						IDkeyGenerator.stringToID(tblStaff.getValueAt(tblStaff.getSelectedRow(), 0).toString()));

				staff = staffservice.findById(id);

				txtName.setText(staff.getStaffName());
				txtAddress.setText(staff.getStaffAddress());
				txtNRC.setText(staff.getStaffNRC());
				txtPhno.setText(staff.getStdaffPhno());
				cboRole.setSelectedItem(staff.getRole());
				resertPanel();
				editbtnpanel.setVisible(true);
				lbleditpanel.setVisible(true);

			}
		});

		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBounds(999, 84, 183, 28);

		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String keyword = txtSearch.getText();

				List<Staff> searchList = new ArrayList<Staff>();
				searchList = originalStaffList.stream().filter(staff -> {
					return staff.getStaffName().toLowerCase().contains(keyword.toLowerCase())
							|| staff.getStaffAddress().toLowerCase().contains(keyword.toLowerCase())
							|| staff.getStaffNRC().toLowerCase().contains(keyword.toLowerCase())
							|| staff.getStdaffPhno().toLowerCase().contains(keyword.toLowerCase())
							|| staff.getRole().toString().toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("SF-", staff.getStaffNo()).toLowerCase()
									.contains(keyword.toLowerCase());

				}).collect(Collectors.toList());

				loadAllStaff(Optional.of(searchList));

			}

		});

		add(txtSearch);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(98, 122, 377, 479);
		add(panel_2);
		panel_2.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(21, 60, 336, 310);
		panel_2.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(24, 29, 61, 14);
		panel.add(lblNewLabel_1);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(95, 24, 215, 27);
		panel.add(txtName);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAddress.setBounds(24, 79, 61, 14);
		panel.add(lblAddress);

		JLabel lblNewLabel_1_1 = new JLabel("NRC");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(24, 170, 61, 14);
		panel.add(lblNewLabel_1_1);

		txtPhno = new JTextField();
		txtPhno.setColumns(10);
		txtPhno.setBounds(95, 223, 215, 27);
		panel.add(txtPhno);

		JLabel lblNewLabel_1_1_1 = new JLabel("Phone Number");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_1.setBounds(24, 223, 76, 14);
		panel.add(lblNewLabel_1_1_1);

		txtNRC = new JTextField();
		txtNRC.setColumns(10);
		txtNRC.setBounds(95, 167, 215, 27);
		panel.add(txtNRC);

		JSeparator separator = new JSeparator();
		separator.setBounds(24, 62, 286, 2);
		panel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(24, 154, 286, 14);
		panel.add(separator_1);

		txtAddress = new JTextArea();
		txtAddress.setBounds(95, 75, 215, 68);
		panel.add(txtAddress);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(24, 210, 286, 14);
		panel.add(separator_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Role");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_1_1.setBounds(24, 273, 76, 14);
		panel.add(lblNewLabel_1_1_1_1);

		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(24, 261, 286, 14);
		panel.add(separator_1_1_1);

		cboRole = new JComboBox();
		cboRole.addItem("-select-");
		cboRole.addItem("ADMIN");
		cboRole.addItem("STAFF");
		cboRole.setBounds(94, 273, 216, 26);
		panel.add(cboRole);

		savebtnpanel = new JPanel();
		savebtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		savebtnpanel.setBounds(21, 381, 334, 87);
		panel_2.add(savebtnpanel);
		savebtnpanel.setLayout(null);

		JButton btnAdd = new JButton("Save");
		btnAdd.setIcon(new ImageIcon(StaffPanel.class.getResource("/icon/Add.png")));
		btnAdd.setBounds(34, 23, 95, 36);
		savebtnpanel.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Staff staff = new Staff();
				if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhno.getText().isEmpty()
						|| txtNRC.getText().isEmpty() || cboRole.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(panel, "required field");
				} else {

					if (!txtName.getText().matches("[a-zA-Z ]+")) {
						JOptionPane.showMessageDialog(panel, "Your Student Name is something wrong");
						return;

					} else {
						if (txtNRC.getText().matches("[0-9]+")) {
							JOptionPane.showMessageDialog(panel, "Your NRC is invalid");
							return;
						}

						if (!txtPhno.getText().matches("[0-9]+") || txtPhno.getText().toString().length() < 4
								|| txtPhno.getText().length() > 15) {
							JOptionPane.showMessageDialog(panel, "Your phone number is invalid");
							return;

						}
					}

					if (txtAddress.getText().matches("[0-9]+")) {

						JOptionPane.showMessageDialog(panel, "Your address  something wrong");
						return;

					}

					if (isDuplicate(txtNRC.getText().trim(), txtPhno.getText().trim(), originalStaffList)) {
						JOptionPane.showMessageDialog(panel, "Your NRC or Phone Number is existing number");
						return;

					}

					staff.setStaffName(txtName.getText());
					staff.setStaffAddress(txtAddress.getText());
					staff.setStaffNRC(txtNRC.getText());
					staff.setStdaffPhno(txtPhno.getText());
					staff.setRole(cboRole.getSelectedItem().toString());
					staffservice.createStaff(staff);
					resertStaffData();
					loadAllStaff(Optional.empty());

				}

			}
		});
		btnAdd.setForeground(new Color(0, 0, 0));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnSaveCancel = new JButton("Cancel");
		btnSaveCancel.setIcon(new ImageIcon(StaffPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btnSaveCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				resertStaffData();
			}
		});
		btnSaveCancel.setBounds(195, 23, 95, 36);
		savebtnpanel.add(btnSaveCancel);
		btnSaveCancel.setForeground(Color.BLACK);
		btnSaveCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		editbtnpanel = new JPanel();
		editbtnpanel.setBounds(21, 381, 334, 87);
		panel_2.add(editbtnpanel);
		editbtnpanel.setLayout(null);
		editbtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		btneditCancel = new JButton("Cancel");
		btneditCancel.setIcon(new ImageIcon(StaffPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btneditCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tblStaff.getSelectionModel().clearSelection();
				resertStaffData();
				resertPanel();
				savebtnpanel.setVisible(true);
				lbladdpanel.setVisible(true);

			}
		});
		btneditCancel.setForeground(Color.BLACK);
		btneditCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btneditCancel.setBounds(222, 20, 95, 36);
		editbtnpanel.add(btneditCancel);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(117, 20, 90, 36);
		editbtnpanel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null, "Do you really want to Delete?", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

					staffservice.deleteStaff(String.valueOf(staff.getStaffNo()));
					resertStaffData();
					loadAllStaff(Optional.empty());
					JOptionPane.showMessageDialog(null, "Succefully Delete");
					btneditCancel.doClick();

				}
			}
		});
		btnDelete.setIcon(new ImageIcon(StaffPanel.class.getResource("/icon/Delete.png")));

		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 13));

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhno.getText().isEmpty()
						|| txtNRC.getText().isEmpty() || cboRole.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(panel, "required field");
				} else {

					if (!txtName.getText().matches("[a-zA-Z ]+")) {
						JOptionPane.showMessageDialog(panel, "Your Teacher Name is something wrong");
						return;
					}
					if (txtNRC.getText().matches("[0-9]+")) {
						JOptionPane.showMessageDialog(panel, "Your NRC is invalid");
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

					List<Staff> editStaffList = new ArrayList<Staff>();

					editStaffList = originalStaffList.stream()
							.filter(s -> s.getStaffNo() != staff.getStaffNo())  // filter staffs not contain selected staff
							.collect(Collectors.toList());
					
				 

					if (isDuplicate(txtNRC.getText().trim(), txtPhno.getText().trim(), editStaffList)) {
						JOptionPane.showMessageDialog(panel, "Your NRC or Phone Number is existing number");
						return;

					}

					staff.setStaffName(txtName.getText());
					staff.setStaffAddress(txtAddress.getText());
					staff.setStaffNRC(txtNRC.getText());
					staff.setStdaffPhno(txtPhno.getText());
					staff.setRole(cboRole.getSelectedItem().toString());
					staffservice.updateStaff(String.valueOf(staff.getStaffNo()), staff);
					loadAllStaff(Optional.empty());
					btneditCancel.doClick();
					staff = null;
				}

			}
		});
		btnEdit.setBounds(10, 20, 90, 36);
		editbtnpanel.add(btnEdit);
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblNewLabel_2 = new JLabel("Create Account?");
		lblNewLabel_2.setBounds(117, 73, 116, 14);
		editbtnpanel.add(lblNewLabel_2);
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (null != staff) {

					createForm cform = new createForm(staff);
					cform.frame.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null, "Choose Employee");
				}

			}
		});

		lbleditpanel = new JPanel();
		lbleditpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbleditpanel.setBounds(23, 11, 336, 42);
		panel_2.add(lbleditpanel);
		lbleditpanel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Edit Staff Information");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(62, 0, 264, 42);
		lblNewLabel_4.setForeground(new Color(138, 43, 226));
		lbleditpanel.add(lblNewLabel_4);

		lbladdpanel = new JPanel();
		lbladdpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbladdpanel.setBounds(23, 11, 336, 42);
		panel_2.add(lbladdpanel);
		lbladdpanel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Add Staff Infomation");
		lblNewLabel_3.setBounds(62, 0, 264, 42);
		lbladdpanel.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3.setForeground(new Color(138, 43, 226));

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.BLACK);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSearch.setBounds(936, 91, 55, 14);
		add(lblSearch);
		lbladdpanel.setVisible(true);
		setTableDesign();
		loadAllStaff(Optional.empty());
		resertPanel();
		savebtnpanel.setVisible(true);
		lbladdpanel.setVisible(true);

	}
}
