package form;

import java.awt.EventQueue;

import javax.swing.JFrame;

import model.Staff;
import model.StaffInfo;
import service.StaffService;
import shared.utils.CurrentUserHolder;
import service.AuthService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class createForm {

	public JFrame frame;
	public Staff staff;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JPanel panel_1;
	private JLabel lblNewLabel_1;
	StaffService staffservice;
	AuthService authservice;
	private JPasswordField txtPassword;
	private JTextField txtusername;
	List<Staff> stafflist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createForm window = new createForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean isduplicate(String username, List<Staff> stafflist) {
		boolean isname = false;
		for (Staff s : stafflist) {
			isname = s.getUsername().equals(username);
			if (isname) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Create the application.
	 */
	public createForm() {
		initialize();
		initializeDependency();
	}

	private void initializeDependency() {
		this.staffservice = new StaffService();
		this.authservice = new AuthService();
	}

	public createForm(Staff staff) {
		// TODO Auto-generated constructor stub
		this.staff = staff;
		initialize();
		initializeDependency();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 496, 333);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBounds(83, 11, 327, 49);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		lblNewLabel_1 = new JLabel(staff != null ? "Staff:" + staff.getStaffName() : "Login Form");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(105, 11, 175, 27);
		panel_1.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(83, 74, 327, 191);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(49, 13, 62, 14);
		panel.add(lblNewLabel);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(49, 73, 62, 14);
		panel.add(lblPassword);

		btnLogin = new JButton(staff != null ? "CreateAccount" : "Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (null != staff) { // create account
					staff.setUsername(txtusername.getText());
					staff.setPassword(String.valueOf(txtPassword.getPassword()));

					if (!staff.getUsername().isBlank() && !staff.getPassword().isBlank()) {

						stafflist = staffservice.findAllStaff();
						List<Staff> usernameStaffList = stafflist.stream().filter(s -> {
							return s.getUsername() != null && s.getStaffNo() != staff.getStaffNo();

						}).collect(Collectors.toList());

						if (isduplicate(txtusername.getText(), usernameStaffList)) {
							txtusername.setText(null);
							txtPassword.setText(null);
							JOptionPane.showMessageDialog(null, "Your user name is already exist");
							return;
						}

						staffservice.updateStaff(String.valueOf(staff.getStaffNo()), staff);

						Dashboard.staffpanel.btneditCancel.doClick();
						Dashboard.staffpanel.loadAllStaff(Optional.empty());
						frame.dispose();

					} else {
						JOptionPane.showMessageDialog(null, "Fill required fields");
					}
				} else { // login
					String username = txtusername.getText();
					String password = String.valueOf(txtPassword.getPassword());

					if (!username.isBlank() && !password.isBlank()) {
						String loggedInUserId = authservice.login(username, password);

						if (!loggedInUserId.isBlank()) {
							JOptionPane.showMessageDialog(null, "Successfully Login");
							Dashboard dash = new Dashboard();
							dash.frame.setVisible(true);

							staff = staffservice.findById(loggedInUserId);
							if (staff.getRole().toString().toUpperCase().equals("STAFF")) {

								dash.staffMenu.setEnabled(false);
							}

							CurrentUserHolder.setLoggedInUser(staff); // keep staff
							frame.setVisible(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Enter required Fields");
					}
				}

			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogin.setBounds(100, 140, 126, 29);
		panel.add(btnLogin);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(49, 98, 229, 29);
		panel.add(txtPassword);

		txtusername = new JTextField();
		txtusername.setBounds(47, 38, 231, 29);
		panel.add(txtusername);
		txtusername.setColumns(10);

	}
}
