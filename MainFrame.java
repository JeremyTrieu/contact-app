package jeremytrieu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame implements ActionListener, KeyListener,
		Serializable {

	public static final int width = 600;
	public static final int height = 400;
	public static final int tableWidth = width - 10;
	public static final int tableHeight = height - 130;
	private final Color colorDefault = Color.white, colorNotFound = Color.pink;

	private String[] listSearch = { "Name", "Phone", "Group", "Address" };
	private String[] titleItem = listSearch;
	private JTable table;
	private JTextField tfSearch;
	private JLabel lbStatus;
	private JComboBox<String> cbSearchType;
	private int typeSearch = 0;

	private Data data = new Data();
	private ArrayList<Person> dataSearch = new ArrayList<Person>();

	private AddPerson addPerson;
	private EditPerson editPerson;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public MainFrame() {
		// create JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setTitle("Contact Management");
		setResizable(false);
		// add content
		setJMenuBar(createJMenuBar());
		add(createMainPanel());

		read();
		updateTable(this.data.getListPerson());

		// display
		setLocationRelativeTo(null);
	}

	// create menuBar
	private JMenuBar createJMenuBar() {
		JMenuBar mb = new JMenuBar();
		String[] contacts = { "Exit" };
		mb.add(createJMenu("Contact", contacts, KeyEvent.VK_D));
		String[] help = { "Instruction", "", "Intro" };
		mb.add(createJMenu("instruction", help, KeyEvent.VK_H));
		return mb;
	}

	// create menus
	private JMenu createJMenu(String menuName, String itemName[], int key) {
		JMenu m = new JMenu(menuName);
		m.addActionListener(this);
		m.setMnemonic(key);

		for (int i = 0; i < itemName.length; i++) {
			if (itemName[i].equals("")) {
				m.add(new JSeparator());
			} else {
				m.add(createJMenuItem(itemName[i]));
			}
		}

		return m;
	}

	// create menu item
	private JMenuItem createJMenuItem(String itName) {
		JMenuItem mi = new JMenuItem(itName);
		mi.addActionListener(this);
		return mi;
	}

	// create main panel
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createSearchPanel(), BorderLayout.PAGE_START);
		mainPanel.add(createContactsPanel(), BorderLayout.CENTER);
		mainPanel.add(createStatusPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}

	// create search panel
	private JPanel createSearchPanel() {
		JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
		searchPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
		tfSearch = createJTextField();
		searchPanel.add(tfSearch, BorderLayout.CENTER);
		cbSearchType = createListSearch();
		searchPanel.add(cbSearchType, BorderLayout.EAST);
		return searchPanel;
	}

	// create comboBox to choose type search
	private JComboBox<String> createListSearch() {
		JComboBox<String> cb = new JComboBox<String>(listSearch);
		cb.addActionListener(this);
		return cb;
	}

	private JTextField createJTextField() {
		JTextField tf = new JTextField(20);
		tf.addKeyListener(this);
		return tf;
	}

	private JPanel createContactsPanel() {
		JPanel panel = new JPanel();
		panel.add(createTabelPanel());
		return panel;
	}

	private JPanel createTabelPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		table = createTable();
		loadData(table);
		JScrollPane scronllPanel = new JScrollPane(table);
		scronllPanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		panel.add(scronllPanel, BorderLayout.CENTER);
		return panel;
	}

	private JTable createTable() {
		JTable table = new JTable();
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return table;
	}

	private void loadData(JTable table) {
		String data[][] = null;
		// don't edit table
		DefaultTableModel tableModel = new DefaultTableModel(data, titleItem) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(tableModel);
	}

	private JPanel createStatusPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		lbStatus = new JLabel();
		searchStatus(0, "", "");
		panel.add(lbStatus, BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		return panel;
	}

	// create button panel
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(createButton("Add"));
		buttonPanel.add(createButton("Edit"));
		buttonPanel.add(createButton("Delete"));
		return buttonPanel;
	}

	// create a button
	private JButton createButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == "Add") {
			themDanhBa();
			return;
		}
		if (command == "Edit") {
			suaDanhBa();
			return;
		}
		if (command == "Delete") {
			delete();
			return;
		}
		if (e.getSource() == cbSearchType) {
			resetSearch();
			return;
		}
		if (command == "Exit") {
			System.exit(0);
		}
		if (command == "Instruction") {
			showHelp();
			return;
		}
		if (command == "Intro") {
			showAbout();
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		updateTable(search(typeSearch));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public void updateData() {
		Collections.sort(data.getListPerson(), Person.PersonNameComparator);
		write();
		if (dataSearch.size() > 0) {
			updateTable(search(typeSearch));
		} else {
			updateTable(this.data.getListPerson());
		}
	}

	private int findIndexOfData() {
		int index = table.getSelectedRow();
		if (dataSearch.size() > 0) {
			for (int i = 0; i < data.getListPerson().size(); i++) {
				if (dataSearch.get(index) == data.getListPerson().get(i)) {
					System.out.println("index of data: " + i);
					return i;
				}
			}
		}
		return index;
	}

	private void themDanhBa() {
		if (addPerson == null) {
			addPerson = new AddPerson(this);
		}
		addPerson.display(true);
		tfSearch.requestFocus();
	}

	private void suaDanhBa() {
		System.out.println(table.getSelectedRow());
		int index = findIndexOfData();
		if (index >= 0) {
			if (editPerson == null) {
				editPerson = new EditPerson(this);
			}
			editPerson.setIndexRow(index);
			editPerson.display(true);
		} else {
			JOptionPane.showMessageDialog(null, "Choose 1 contact to edit!");
		}
		tfSearch.requestFocus();
	}

	private void delete() {
		int index = findIndexOfData();
		if (index >= 0) {
			int select = JOptionPane.showOptionDialog(null,
					"Are you sure about deleting this contact? "
							+ data.getListPerson().get(index).getName() + " ?",
					"Delete", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (select == 0) {
				data.getListPerson().remove(index);
				updateData();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Choose 1 contact to edit!");
		}
		tfSearch.requestFocus();
	}

	private void updateTable(ArrayList<Person> list) {
		String data[][] = convertData(list);
		DefaultTableModel tableModel = new DefaultTableModel(data, titleItem) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(tableModel);
		// DefaultTableCellRenderer centerRenderer = new
		// DefaultTableCellRenderer();
		// centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		// for (int x = 0; x < titleItem.length; x++) {
		// table.getColumnModel().getColumn(x)
		// .setCellRenderer(centerRenderer);
		// }

	}

	private String[][] convertData(ArrayList<Person> list) {
		int size = list.size();
		String data[][] = new String[size][titleItem.length];
		for (int i = 0; i < size; i++) {
			Person person = list.get(i);
			data[i][0] = person.getName();
			data[i][1] = person.getPhone();
			data[i][2] = person.getGroup();
			data[i][3] = person.getAddres();
		}
		return data;
	}

	private ArrayList<Person> search(int typeSearch) {
		int size = data.getListPerson().size();
		dataSearch.clear();
		String textFind = tfSearch.getText().trim().toLowerCase();
		if (textFind.length() == 0) {
			searchStatus(0, "", "");
			return this.data.getListPerson();
		}
		String textType = "";
		for (int i = 0; i < size; i++) {
			Person p = data.getListPerson().get(i);
			String text = "";
			if (typeSearch == 0) {
				text = p.getName();
				textType = " contact with right name \"";
			} else if (typeSearch == 1) {
				text = p.getPhone();
				textType = " contact with right phone number \"";
			} else if (typeSearch == 2) {
				text = p.getGroup();
				textType = " contact with right group \"";
			} else if (typeSearch == 3) {
				text = p.getAddres();
				textType = " contact with right address \"";
			}
			text = text.trim().toLowerCase();
			if (text.indexOf(textFind) >= 0) {
				dataSearch.add(p);
			}
		}
		searchStatus(dataSearch.size(), textType + textFind + "\".", textFind);
		return dataSearch;
	}

	private void searchStatus(int count, String status, String textFind) {
		tfSearch.setBackground(colorDefault);
		if (textFind.length() == 0) {
			lbStatus.setText("Enter information !");
		} else if (count > 0 && textFind.length() > 0) {
			lbStatus.setText("Found " + count + status);
		} else if (count == 0 && textFind.length() > 0) {
			tfSearch.setBackground(colorNotFound);
			lbStatus.setText("Not Found" + status);
		}
	}

	private void resetSearch() {
		typeSearch = cbSearchType.getSelectedIndex();
		tfSearch.setText("");
		tfSearch.requestFocus();
		searchStatus(0, "", "");
		updateData();
	}

	public void write() { // ghi theo Object
		try {
			FileOutputStream f = new FileOutputStream("data");
			ObjectOutputStream oStream = new ObjectOutputStream(f);
			oStream.writeObject(data);
			oStream.close();
		} catch (IOException e) {
			System.out.println("Error Write file");
		}
	}

	public void read() {
		Data data = null;
		try {
			File file = new File("data");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileInputStream is = new FileInputStream(file);
			ObjectInputStream inStream = new ObjectInputStream(is);
			data = (Data) inStream.readObject();
			this.data = data;
			inStream.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		} catch (IOException e) {
			System.out.println("Error Read file");
		}
	}

	private void showHelp() {
		new HelpAndAbout(0, "Instruction").setVisible(true);
	}

	private void showAbout() {
		new HelpAndAbout(1, "Intro").setVisible(true);
	}
}
