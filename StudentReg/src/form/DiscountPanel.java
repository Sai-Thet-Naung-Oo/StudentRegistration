package form;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Course;
import model.Discount;
import model.Teacher;
import service.CourseService;
import service.DiscountService;
import shared.utils.IDkeyGenerator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.ImageIcon;

public class DiscountPanel extends JPanel {
	private JTextField txtRate;
	private Discount discount;
	JButton btneditCanel;
	private DefaultTableModel dtm = new DefaultTableModel();
	private DiscountService discountservice;
	private JTable tbldiscount;
	private List<Discount> filtereddiscount = new ArrayList<>();
	private List<Discount> discountlist = new ArrayList<>();
	private Optional<Discount> selectdiscount;
	private JTextField txtDiscountName;
	private JTextField txtSearch;

	JPanel editbtnpanel, savebtnpanel, lblsavepanel, lbleditpanel;

	/**
	 * Create the panel.
	 */
	public void resertDiscountData() {
		txtDiscountName.setText("");
		txtRate.setText("");

	}

	void intializeDepency() {
		// TODO Auto-generated method stub
		discountservice = new DiscountService();

	}

	public boolean isduplicate(String dName, List<Discount> discountslist) {
		boolean isname = false;
		for (Discount d : discountslist) {
			isname = d.getDiscountName().equals(dName);
			if (isname) {
				return true;
			}
		}

		return false;
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Rate");

		this.tbldiscount.setModel(dtm);
	}

	public void resertPanel() {
		savebtnpanel.setVisible(false);
		editbtnpanel.setVisible(false);
		lblsavepanel.setVisible(false);
		lbleditpanel.setVisible(false);
	}

	private void loadAllDiscount(Optional<List<Discount>> optionaldiscount) {

		this.dtm = (DefaultTableModel) this.tbldiscount.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.filtereddiscount = this.discountservice.findAllDiscount();
		discountlist = optionaldiscount.orElseGet(() -> filtereddiscount);

		discountlist.forEach(e -> {
			Object[] row = new Object[3];

			row[0] = IDkeyGenerator.idToString("DS-", e.getDiscount_id());
			;
			row[1] = e.getDiscountName();
			row[2] = e.getRate();

			dtm.addRow(row);
		});

		this.tbldiscount.setModel(dtm);
	}

	public DiscountPanel() {
		intializeDepency();
		setBackground(new Color(173, 216, 230));
		setBounds(0, 0, 1300, 700);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(498, 163, 672, 360);
		add(scrollPane);

		tbldiscount = new JTable();
		scrollPane.setViewportView(tbldiscount);
		this.tbldiscount.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {

			if (!tbldiscount.getSelectionModel().isSelectionEmpty()) {

				String id = String.valueOf(
						IDkeyGenerator.stringToID(tbldiscount.getValueAt(tbldiscount.getSelectedRow(), 0).toString()));

				discount = discountservice.findById(id);

				txtDiscountName.setText(discount.getDiscountName());
				txtRate.setText(String.valueOf(discount.getRate()));
				resertPanel();
				editbtnpanel.setVisible(true);
				lbleditpanel.setVisible(true);

			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(119, 163, 369, 360);
		add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(22, 64, 330, 200);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Discount Name");
		lblNewLabel.setBounds(20, 47, 99, 14);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtRate = new JTextField();
		txtRate.setBounds(109, 126, 201, 31);
		panel_1.add(txtRate);
		txtRate.setColumns(10);

		JLabel lblRate = new JLabel("Rate to %");
		lblRate.setBounds(20, 133, 84, 14);
		panel_1.add(lblRate);
		lblRate.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtDiscountName = new JTextField();
		txtDiscountName.setColumns(10);
		txtDiscountName.setBounds(109, 45, 201, 31);
		panel_1.add(txtDiscountName);

		savebtnpanel = new JPanel();
		savebtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		savebtnpanel.setBounds(22, 275, 330, 74);
		panel.add(savebtnpanel);
		savebtnpanel.setLayout(null);

		JButton btnSave = new JButton("Add");
		btnSave.setIcon(new ImageIcon(DiscountPanel.class.getResource("/icon/Add.png")));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtDiscountName.getText().isEmpty() || txtRate.getText().isEmpty()) {
					JOptionPane.showMessageDialog(panel, "required field");
				} else {

					if (isduplicate(txtDiscountName.getText(), discountlist)) {
						JOptionPane.showMessageDialog(panel, "Your Discount Name is Duplicate");
						return;
					} else {
						if (!txtDiscountName.getText().matches("[a-zA-Z0-9% ]+")) {
							JOptionPane.showMessageDialog(panel, "Your Discount Name  is wrong!!");
							return;
						}
					}
					if (!txtRate.getText().matches("[0-9]+")) {
						JOptionPane.showMessageDialog(panel, "Your Discount rate is wrong!!");
						return;
					}
					Discount discount = new Discount();
					discount.setDiscountName(txtDiscountName.getText());
					discount.setRate(Integer.parseInt(txtRate.getText()));
					discountservice.createDiscount(discount);
					resertDiscountData();
					loadAllDiscount(Optional.empty());

				}

			}
		});
		btnSave.setBounds(28, 20, 95, 36);
		savebtnpanel.add(btnSave);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnaddCancel = new JButton("Cancel");
		btnaddCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resertDiscountData();
			}
		});
		btnaddCancel.setIcon(new ImageIcon(DiscountPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btnaddCancel.setBounds(211, 20, 95, 36);
		savebtnpanel.add(btnaddCancel);
		btnaddCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblsavepanel = new JPanel();
		lblsavepanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblsavepanel.setBounds(22, 11, 330, 42);
		panel.add(lblsavepanel);
		lblsavepanel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Add Discount Infomation");
		lblNewLabel_3.setBounds(45, 11, 246, 23);
		lblsavepanel.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(new Color(138, 43, 226));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lbleditpanel = new JPanel();
		lbleditpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbleditpanel.setLayout(null);
		lbleditpanel.setBounds(22, 11, 330, 42);
		panel.add(lbleditpanel);

		JLabel lblNewLabel_3_1 = new JLabel("Edit Discount Infomation");
		lblNewLabel_3_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3_1.setBounds(45, 11, 246, 23);
		lbleditpanel.add(lblNewLabel_3_1);

		editbtnpanel = new JPanel();
		editbtnpanel.setLayout(null);
		editbtnpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		editbtnpanel.setBounds(22, 275, 330, 74);
		panel.add(editbtnpanel);

		btneditCanel = new JButton("Cancel");
		btneditCanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbldiscount.getSelectionModel().clearSelection();
				resertPanel();
				resertDiscountData();
				savebtnpanel.setVisible(true);
				lblsavepanel.setVisible(true);

			}
		});
		btneditCanel.setIcon(new ImageIcon(DiscountPanel.class.getResource("/icon/AddTeacher_Reset.png")));
		btneditCanel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btneditCanel.setBounds(222, 20, 98, 36);
		editbtnpanel.add(btneditCanel);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(10, 20, 90, 36);
		editbtnpanel.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtDiscountName.getText().isEmpty() || txtRate.getText().isEmpty()) {
					JOptionPane.showMessageDialog(panel, "required field");
				} else {
					if (!txtDiscountName.getText().matches("[a-zA-Z0-9% ]+")) {
						JOptionPane.showMessageDialog(panel, "Your Discount Name is wrong");
						return;
					}
					if (!txtRate.getText().matches("[0-9]+")) {
						JOptionPane.showMessageDialog(panel, "Your Discount rate is wrong!!");
						return;
					}
					
					
					
					List<Discount> editDiscountList=new ArrayList<Discount>();
					
					editDiscountList=discountlist.stream()
							.filter(s -> s.getDiscount_id()!=discount.getDiscount_id())  // filter teachers not contain selected teacher
							.collect(Collectors.toList());
					
					
					if (isduplicate(txtDiscountName.getText(), editDiscountList)) {
						JOptionPane.showMessageDialog(panel, "Your Discount Name is Duplicate");
						return;
					}
					
					discount.setDiscountName(txtDiscountName.getText());
					discount.setRate(Integer.parseInt(txtRate.getText()));
					discountservice.updateDiscount(String.valueOf(discount.getDiscount_id()), discount);
					
					btneditCanel.doClick();
					loadAllDiscount(Optional.empty());
					discount = null;
				}
			}
		});

		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(114, 20, 95, 36);
		editbtnpanel.add(btnDelete);
		btnDelete.setIcon(new ImageIcon(DiscountPanel.class.getResource("/icon/Delete.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null, "Do you really want to Delete?", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

					discountservice.deleteDiscount(String.valueOf(discount.getDiscount_id()));
					resertDiscountData();
					loadAllDiscount(Optional.empty());
					JOptionPane.showMessageDialog(null, "Succefully Delete");
					btneditCanel.doClick();

				}

			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblNewLabel_1 = new JLabel("Discount Infomation");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 31));
		lblNewLabel_1.setBounds(491, 46, 433, 27);
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		add(lblNewLabel_1);

		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBounds(972, 124, 198, 29);

		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String keyword = txtSearch.getText();

				List<Discount> searchList = new ArrayList<Discount>();
				searchList = filtereddiscount.stream().filter(discount -> {
					return discount.getDiscountName().toLowerCase().contains(keyword.toLowerCase())
							|| String.valueOf(discount.getRate()).toLowerCase().contains(keyword.toLowerCase())
							|| IDkeyGenerator.idToString("DS-", discount.getDiscount_id()).toLowerCase()
									.contains(keyword.toLowerCase());

				}).collect(Collectors.toList());

				loadAllDiscount(Optional.of(searchList));

			}

		});

		add(txtSearch);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.BLACK);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSearch.setBounds(915, 132, 55, 14);
		add(lblSearch);
		setTableDesign();
		loadAllDiscount(Optional.empty());
		resertPanel();
		savebtnpanel.setVisible(true);
		lblsavepanel.setVisible(true);
	}
}
