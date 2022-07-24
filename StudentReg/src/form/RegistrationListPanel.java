package form;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import model.Registration;
import model.Schedule;
import service.RegistrationService;
import service.ScheduleService;
import shared.utils.DateUtils;
import shared.utils.IDkeyGenerator;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class RegistrationListPanel extends JPanel {
	private JTable registratinTable;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<Registration> originalRegistrationList = new ArrayList<>(); // for table data
	private List<Registration> searchRegistrationList = new ArrayList<>();
	private List<Schedule> ScheduleList = new ArrayList<>();
	JPanel searchTextFieldPanel;
	RegistrationService registrationService;
	ScheduleService scheduleService;
	JComboBox<String> searchDataCombo;
	JComboBox<String> searchByCombo;
	Registration selectedRegistration;
	private JTextField txtSearch;
	JPanel searchDataComboPanel;

	/**
	 * Create the panel.
	 */

	public void resetSearchDataPanel() {
		searchDataComboPanel.setVisible(false);
		searchTextFieldPanel.setVisible(false);
	}

	private void setTableDesign() {

		dtm.addColumn("Registration-ID");
		dtm.addColumn("Student Name");
		dtm.addColumn("Schedule-ID");
		dtm.addColumn("Teacher Name");
		dtm.addColumn("Course");
		dtm.addColumn("Discount");
		dtm.addColumn("Date");

		this.registratinTable.setModel(dtm);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i <= 6; i++) {
			if (i == 1 || i == 3 || i == 4)
				continue;
			registratinTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // Table Data to center

		}

		registratinTable.getTableHeader() // Column heading two line
				.setPreferredSize(new Dimension(registratinTable.getColumnModel().getTotalColumnWidth(), 32));
		registratinTable.getColumnModel().getColumn(2).setHeaderValue("<html>Schedule<br><center> ID</center>");
		registratinTable.getColumnModel().getColumn(0).setHeaderValue("<html>Registration<br><center> ID</center>");

		registratinTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// set width manual
		registratinTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		registratinTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		registratinTable.getColumnModel().getColumn(2).setPreferredWidth(60);
		registratinTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		registratinTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		registratinTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		registratinTable.getColumnModel().getColumn(6).setPreferredWidth(100);

		registratinTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

	}

	public void loadAllRegistrations(Optional<List<Registration>> optionalRegistration) {
		this.dtm = (DefaultTableModel) this.registratinTable.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		originalRegistrationList = registrationService.findAllRegistrations();
		List<Registration> registrationList = optionalRegistration.orElseGet(() -> originalRegistrationList);

		registrationList.forEach(s -> {
			Object[] row = new Object[7];
			row[0] = IDkeyGenerator.idToString("RG-", s.getReg_no());
			row[1] = s.getStudent().getStdName();
			row[2] = IDkeyGenerator.idToString("SC-", s.getSchedule().getSchedule_id());
			row[3] = s.getSchedule().getTeacher().getTeacherName();
			row[4] = s.getSchedule().getCourse().getCourseName();
			row[5] = s.getDiscount().getDiscountName();
			row[6] = s.getDate();

			dtm.addRow(row);
		});

		this.registratinTable.setModel(dtm);
	}

	public RegistrationListPanel() {
		setLayout(null);
		setBackground(new Color(173, 216, 230));
		setBounds(0, 0, 1300, 700);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(76, 108, 1161, 434);
		add(scrollPane);

		registratinTable = new JTable();
		registratinTable.setRowHeight(20);
		registratinTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.registratinTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {

			if (!e.getValueIsAdjusting()) {
				if (!registratinTable.getSelectionModel().isSelectionEmpty()) {

					String registrationid = String.valueOf(IDkeyGenerator
							.stringToID(registratinTable.getValueAt(registratinTable.getSelectedRow(), 0).toString()));

					selectedRegistration = originalRegistrationList.stream()
							.filter(s -> String.valueOf(s.getReg_no()).equals(registrationid)).findFirst().get();

					RegistrationDetailForm rg = new RegistrationDetailForm(selectedRegistration);
					rg.setVisible(true);

				}
			}

		});

		scrollPane.setViewportView(registratinTable);

		JLabel lblNewLabel_2 = new JLabel("Search By");
		lblNewLabel_2.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		lblNewLabel_2.setBounds(789, 71, 107, 22);
		add(lblNewLabel_2);

		searchByCombo = new JComboBox<String>();
		searchByCombo.addItem("- Select -");
		searchByCombo.addItem("Registration ID");
		searchByCombo.addItem("Schedule ID");
		searchByCombo.addItem("All Field");

		searchByCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				searchDataCombo.removeAllItems();
				searchDataCombo.addItem("- Select -");
				resetSearchDataPanel();

				if (searchByCombo.getSelectedItem().equals("Registration ID")) {
					originalRegistrationList
							.forEach(s -> searchDataCombo.addItem(IDkeyGenerator.idToString("RG-", s.getReg_no())));
					loadAllRegistrations(Optional.of(originalRegistrationList));
					searchDataComboPanel.setVisible(true);
					// list.sort(Comparator.comparing(AnObject::getAttr));
				} else if (searchByCombo.getSelectedItem().equals("Schedule ID")) {
					ScheduleList.forEach(
							r -> searchDataCombo.addItem(IDkeyGenerator.idToString("SC-", r.getSchedule_id())));
					loadAllRegistrations(Optional.of(originalRegistrationList));
					searchDataComboPanel.setVisible(true);

				} else if (searchByCombo.getSelectedItem().equals("All Field")) {

					searchTextFieldPanel.setVisible(true);
				}

				searchDataCombo.setEnabled(true);

			}
		});
		searchByCombo.setBounds(917, 70, 138, 28);

		add(searchByCombo);

		JLabel lblRegistrationList = new JLabel("Registration List");
		lblRegistrationList.setForeground(new Color(138, 43, 226));
		lblRegistrationList.setFont(new Font("Myanmar Text", Font.BOLD, 32));
		lblRegistrationList.setBounds(524, 16, 251, 45);
		add(lblRegistrationList);

		searchDataComboPanel = new JPanel();
		searchDataComboPanel.setBackground(new Color(173, 216, 230));
		searchDataComboPanel.setBounds(1065, 60, 172, 45);

		add(searchDataComboPanel);
		searchDataComboPanel.setLayout(null);

		searchDataCombo = new JComboBox<String>();

		AutoCompleteDecorator.decorate(searchDataCombo);
		searchDataCombo.setBounds(12, 10, 146, 28);
		searchDataComboPanel.add(searchDataCombo);
		searchDataCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (searchByCombo.getSelectedItem().equals("Registration ID") && searchDataCombo.getSelectedIndex() != 0
						&& searchDataCombo.getSelectedIndex() != -1) {

					loadAllRegistrations(Optional.of(originalRegistrationList.stream()
							.filter(s -> String.valueOf(s.getReg_no()).equals(String.valueOf(IDkeyGenerator.stringToID(searchDataCombo.getSelectedItem().toString()))))
							.collect(Collectors.toList())));

				} else if (searchByCombo.getSelectedItem().equals("Schedule ID")
						&& searchDataCombo.getSelectedIndex() != 0 && searchDataCombo.getSelectedIndex() != -1) {
					loadAllRegistrations(Optional.of(originalRegistrationList.stream()
							.filter(s -> String.valueOf(s.getSchedule().getSchedule_id()).equals(String.valueOf(IDkeyGenerator.stringToID(searchDataCombo.getSelectedItem().toString()))))
							.collect(Collectors.toList())));

				}

			}
		});
		searchDataCombo.setEnabled(false);

		searchTextFieldPanel = new JPanel();
		searchTextFieldPanel.setBounds(1065, 60, 172, 45);
		searchTextFieldPanel.setBackground(new Color(173, 216, 230));
		add(searchTextFieldPanel);
		searchTextFieldPanel.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.setBounds(12, 10, 146, 27);

		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String keyword = txtSearch.getText();

				List<Registration> searchList = new ArrayList<Registration>();
				searchList = originalRegistrationList.stream().filter(registration -> {
					return registration.getStudent().getStdName().toLowerCase().contains(keyword.toLowerCase())
							|| registration.getSchedule().getTeacher().getTeacherName().toLowerCase()
									.contains(keyword.toLowerCase())
							|| registration.getSchedule().getCourse().getCourseName().toLowerCase()
									.contains(keyword.toLowerCase())
							|| registration.getDiscount().getDiscountName().toLowerCase()
									.contains(keyword.toLowerCase())
							|| registration.getDate().toString().toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("SC-", registration.getSchedule().getSchedule_id())
									.toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("RG-", registration.getReg_no()).toLowerCase()
									.contains(keyword.toLowerCase());

				}).collect(Collectors.toList());

				loadAllRegistrations(Optional.of(searchList));

			}

		});

		searchTextFieldPanel.add(txtSearch);
		txtSearch.setColumns(10);

		registrationService = new RegistrationService();
		scheduleService = new ScheduleService();
		setTableDesign();
		resetSearchDataPanel();
		loadAllRegistrations(Optional.empty());
		ScheduleList = scheduleService.findAllSchedules();
	}
}
