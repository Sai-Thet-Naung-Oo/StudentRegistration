package form;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Dashboard {

	public JFrame frame;
	StudentPanel studentpanel;
	SchedulePanel schedulepanel;
	public static StaffPanel staffpanel;
	DiscountPanel discountpanel;
	CoursePanel coursePanel;
	TeacherPanel teacherpanel;
	RegistrationPanel registrationpanel;
	RegistrationListPanel registrationlistpanel;
	TransferPanel transferpanel;
	JMenu staffMenu;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public Dashboard() {

		initialize();
		resetview();
	}

	public void resetview() {
		studentpanel.setVisible(false);
		schedulepanel.setVisible(false);
		teacherpanel.setVisible(false);
		staffpanel.setVisible(false);
		coursePanel.setVisible(false);
		discountpanel.setVisible(false);
		registrationpanel.setVisible(false);
		registrationlistpanel.setVisible(false);
		transferpanel.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
	    frame.setDefaultCloseOperation(0);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(20, 20, 1300, 700);
		frame.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setAlignmentY(0.5f);
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Teacher Management");
		mnNewMenu.setBorder(null);
		mnNewMenu.setFont(new Font("Tahoma", Font.BOLD, 16));
		mnNewMenu.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Teacher management.png")));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Teacher");
		mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		mntmNewMenuItem.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Teacher management.png")));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetview();
				teacherpanel.btneditCancel.doClick();
				teacherpanel.setVisible(true);

			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		JMenu mnNewMenu_1 = new JMenu("Student Management");
		mnNewMenu_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		mnNewMenu_1.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Studnet management.png")));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Student");
		mntmNewMenuItem_2.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Studnet management.png")));
		mntmNewMenuItem_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetview();
				studentpanel.btneditCancel.doClick();
				studentpanel.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		JMenu mnNewMenu_2 = new JMenu("Course");
		mnNewMenu_2.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Course  management.png")));
		mnNewMenu_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Course");
		mntmNewMenuItem_5.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Course  management.png")));
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetview();
				coursePanel.btneditCancel.doClick();
				coursePanel.setVisible(true);
			}
		});
		mntmNewMenuItem_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		mnNewMenu_2.add(mntmNewMenuItem_5);

		staffMenu = new JMenu("Staff Management");
		staffMenu.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/User list.png")));
		staffMenu.setFont(new Font("Tahoma", Font.BOLD, 16));
		menuBar.add(staffMenu);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Staff");
		mntmNewMenuItem_6.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/User list.png")));
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetview();
				staffpanel.btneditCancel.doClick();
				staffpanel.setVisible(true);
			}
		});
		mntmNewMenuItem_6.setBackground(Color.CYAN);
		mntmNewMenuItem_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		staffMenu.add(mntmNewMenuItem_6);

		JMenu mnNewMenu_4 = new JMenu("  Schedule");
		mnNewMenu_4.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Class introduction.png")));
		mnNewMenu_4.setFont(new Font("Tahoma", Font.BOLD, 16));
		menuBar.add(mnNewMenu_4);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Schedule List");
		mntmNewMenuItem_8.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Class list.png")));
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetview();

				schedulepanel.loadAllSchedules(Optional.empty());
				schedulepanel.loadTeacherForComboBox();
				schedulepanel.loadCourseForComboBox();
				schedulepanel.resetFormData();
				schedulepanel.setVisible(true);
			}
		});
		mntmNewMenuItem_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		mnNewMenu_4.add(mntmNewMenuItem_8);

		JMenu mnNewMenu_5 = new JMenu("Registration");
		mnNewMenu_5.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Class introduction.png")));
		mnNewMenu_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		menuBar.add(mnNewMenu_5);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Registration Form");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				resetview();
				registrationpanel.loadStudentNumberForComboBox();
				registrationpanel.loadScheduleIDForComboBox();
				registrationpanel.loadDiscountForComboBox();

				registrationpanel.resetCourseInformationForm();
				registrationpanel.resetRegistrationForm();

				registrationpanel.resetStudentInformationForm();

				registrationpanel.setVisible(true);

			}
		});
		mntmNewMenuItem_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		mnNewMenu_5.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Registration List");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				resetview();
				registrationlistpanel.loadAllRegistrations(Optional.empty());
				registrationlistpanel.setVisible(true);

			}
		});
		mntmNewMenuItem_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		mnNewMenu_5.add(mntmNewMenuItem_4);

		JMenu mnNewMenu_5_1 = new JMenu("Discount");
		mnNewMenu_5_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		menuBar.add(mnNewMenu_5_1);

		JMenuItem mntmNewMenuItem_9_1 = new JMenuItem("Discount");
		mntmNewMenuItem_9_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetview();
				discountpanel.btneditCanel.doClick();
				discountpanel.setVisible(true);

			}
		});
		mntmNewMenuItem_9_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		mnNewMenu_5_1.add(mntmNewMenuItem_9_1);

		JMenu mnNewMenu_4_1 = new JMenu("Transfer");

		mnNewMenu_4_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		menuBar.add(mnNewMenu_4_1);

		JMenuItem mntmNewMenuItem_11 = new JMenuItem("Transfer Form");
		mntmNewMenuItem_11.setFont(new Font("Tahoma", Font.BOLD, 14));
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				resetview();

				transferpanel.dataRefresh();

				transferpanel.setVisible(true);

			}
		});
		mnNewMenu_4_1.add(mntmNewMenuItem_11);

		JMenu Logout = new JMenu("Logout");
		Logout.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Exit.png")));
		Logout.setFont(new Font("Tahoma", Font.BOLD, 16));
		menuBar.add(Logout);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Logout");
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				createForm cform = new createForm();
				cform.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		mntmNewMenuItem_1.setIcon(new ImageIcon(Dashboard.class.getResource("/icon/Exit.png")));
		Logout.add(mntmNewMenuItem_1);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Dashboard.class.getResource("/image/kmdextrasmall.png")));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 0, 165, 81);
		frame.getContentPane().add(lblNewLabel_1);
		
		
		studentpanel = new StudentPanel();
		studentpanel.setBounds(0, 0, 1300, 682);
		frame.getContentPane().add(studentpanel);

		staffpanel = new StaffPanel();
		staffpanel.setBounds(0, 0, 1284, 635);
		frame.getContentPane().add(staffpanel);

		coursePanel = new CoursePanel();
		coursePanel.setBounds(0, 0, 1300, 635);
		frame.getContentPane().add(coursePanel);

		teacherpanel = new TeacherPanel();
		teacherpanel.setBounds(0, 0, 1284, 635);
		frame.getContentPane().add(teacherpanel);

		schedulepanel = new SchedulePanel();
		schedulepanel.setBounds(0, 0, 1284, 635);
		frame.getContentPane().add(schedulepanel);

		discountpanel = new DiscountPanel();
		discountpanel.setBounds(0, 0, 1284, 635);
		frame.getContentPane().add(discountpanel);
		
		registrationpanel = new RegistrationPanel();
		registrationpanel.setBounds(0, 0, 1284, 635);
		
		frame.getContentPane().add(registrationpanel);

		registrationlistpanel = new RegistrationListPanel();
		registrationlistpanel.setBounds(0, 0, 1284, 635);
		
		frame.getContentPane().add(registrationlistpanel);

		transferpanel = new TransferPanel();
		transferpanel.setBounds(0, 0, 1284, 635);
		frame.getContentPane().add(transferpanel);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Dashboard.class.getResource("/image/kmd.png")));
		lblNewLabel.setBounds(119, 59, 1046, 516);
		frame.getContentPane().add(lblNewLabel);
		
		


	}
}
