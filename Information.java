package jeremytrieu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Information extends JPanel implements KeyListener, ActionListener,
		Serializable {

	private String lbStringName = "Name", lbStringPhone = "Phone",
			lbStringGroup = "Group", lbStringAddres = "Address";
	private JTextField tfName, tfPhone, tfAddres;
	private JComboBox<String> cbGroup;
	// private Data data = new Data();
	private int padding = 10;
	private ArrayList<String> listGroup;

	public ArrayList<String> getListGroup() {
		return listGroup;
	}

	public void setListGroup(ArrayList<String> listGroup) {
		this.listGroup = listGroup;
	}

	public JTextField getTfName() {
		return tfName;
	}

	public void setTfName(JTextField tfName) {
		this.tfName = tfName;
	}

	public JTextField getTfPhone() {
		return tfPhone;
	}

	public void setTfPhone(JTextField tfPhone) {
		this.tfPhone = tfPhone;
	}

	public JTextField getTfAddres() {
		return tfAddres;
	}

	public void setTfAddres(JTextField tfAddres) {
		this.tfAddres = tfAddres;
	}

	public JComboBox<String> getCbGroup() {
		return cbGroup;
	}

	public void setCbGroup(JComboBox<String> cbGroup) {
		this.cbGroup = cbGroup;
	}

	public Information(ArrayList<String> listGroup) {
		this.listGroup = listGroup;
		setLayout(new BorderLayout());
		setBorder(new TitledBorder("Information"));
		add(createPanelLabel(), BorderLayout.WEST);
		add(createPanelTextField(), BorderLayout.CENTER);
	}

	// return a Person
	public Person getInfor() {
		if (checkInfor(tfName) && checkInfor(tfPhone) && checkInfor(tfAddres)
				&& checkInfor(cbGroup)) {
			Person p = new Person(tfName.getText().trim().toString(), tfPhone
					.getText().trim().toString(), cbGroup.getSelectedItem()
					.toString(), tfAddres.getText().trim().toString());
			System.out.println(p.toString());
			return p;
		} else {
			return null;
		}
	}

	// create labels
	private JPanel createPanelLabel() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
		panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
		panel.add(createJLabel(lbStringName));
		panel.add(createJLabel(lbStringPhone));
		panel.add(createJLabel(lbStringAddres));
		panel.add(createJLabel(lbStringGroup));
		return panel;
	}

	// create textField to input information
	private JPanel createPanelTextField() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
		panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
		tfName = createJTextFiel();
		tfPhone = createJTextFiel();
		String[] list = new String[listGroup.size()];
		list = listGroup.toArray(list);
		cbGroup = new JComboBox<String>(list);
		tfAddres = createJTextFiel();

		JPanel groupPanel = new JPanel(new BorderLayout(5, 5));
		groupPanel.add(cbGroup, BorderLayout.CENTER);
		groupPanel.add(createJButton("New Group"), BorderLayout.EAST);

		panel.add(tfName);
		panel.add(tfPhone);
		panel.add(tfAddres);
		panel.add(groupPanel);
		return panel;
	}

	// create a JTextField
	private JTextField createJTextFiel() {
		JTextField tf = new JTextField(20);
		tf.addKeyListener(this);
		return tf;
	}

	private JButton createJButton(String btnName) {
		JButton btn = new JButton(btnName);
		btn.addActionListener(this);
		btn.setMargin(new Insets(5, 2, 5, 2));
		return btn;
	}

	// check information of JTextFields
	private boolean checkInfor(JTextField tf) {
		// haven't data
		if (tf.getText().trim().equals("")) {
			tf.requestFocus();
			System.out.println(tf.getName() + " haven't data");
			return false;
		}
		// check phone information
		if (tf.equals(tfPhone)) {
			try {
				Double.parseDouble(tf.getText());
			} catch (Exception e) {
				System.out.println(tf.getName() + " data fails");
				JOptionPane.showMessageDialog(null, "Invalid phone number",
						"Enter phone number", JOptionPane.OK_OPTION);
				tf.requestFocus();
				return false;
			}
		}
		return true;
	}

	private boolean checkInfor(JComboBox<String> cb) {
		if (listGroup.size() == 0) {
			cb.requestFocus();
			return false;
		}
		return true;
	}

	// create a JLabel
	private JLabel createJLabel(String name) {
		JLabel label = new JLabel(name);
		return label;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "New Group") {
			String groupName = JOptionPane.showInputDialog("Enter new group");
			if (groupName != null && groupName.trim().length() > 0) {
				addGroup(groupName);
			}
		}
	}

	public void loadListGroup() {
		String[] list = new String[listGroup.size()];
		list = listGroup.toArray(list);
		cbGroup.setModel(new DefaultComboBoxModel<String>(list));
	}

	// add group
	private void addGroup(String groupName) {
		int index = indexGroupName(groupName);
		if (index < 0) {
			listGroup.add(groupName);
			Collections.sort(listGroup);
			index = indexGroupName(groupName);
		}
		loadListGroup();
		cbGroup.setSelectedIndex(index);
	}

	public int indexGroupName(String groupName) {
		for (int i = 0; i < listGroup.size(); i++) {
			if (listGroup.get(i).equals(groupName)) {
				return i;
			}
		}
		return -1;
	}
}
