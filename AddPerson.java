package jeremytrieu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AddPerson extends JDialog implements ActionListener, Serializable {

	private Information infor;
	private MainFrame mainFrame;

	public AddPerson(MainFrame mainFrame) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setTitle("Add C");

		this.mainFrame = mainFrame;
		infor = new Information(mainFrame.getData().getListGroup());
		add(createMainPanel());

		pack();
		setLocationRelativeTo(null);
	}
ontact
	// create main panel contains all control
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		// add information panel
		panel.add(infor, BorderLayout.CENTER);
		// add button panel
		panel.add(createButtonPanel(), BorderLayout.PAGE_END);
		return panel;
	}

	// create button panel with three button
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(createButton("Add"));
		panel.add(createButton("Re-enter"));
		panel.add(createButton("Delete"));
		return panel;
	}

	// create a button
	private JButton createButton(String btnName) {
		JButton btn = new JButton(btnName);
		btn.addActionListener(this);
		return btn;
	}

	// Action

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Delete") {
			cancel();
		}
		if (e.getActionCommand() == "Re-enter") {
			resetData();
		}

		if (e.getActionCommand() == "Add") {
			addPerson();
		}
	}

	private void cancel() {
		clearInput();
		setVisible(false);
	}

	private void resetData() {
		clearInput();
	}

	private void addPerson() {
		Person p = infor.getInfor();
		if (p != null) {
			clearInput();
			setVisible(false);
			mainFrame.getData().getListPerson().add(p);
			mainFrame.updateData();
		}
	}

	private void clearInput() {
		infor.getTfName().setText("");
		infor.getTfPhone().setText("");
		infor.getTfAddres().setText("");
		infor.getTfName().requestFocus();
	}

	public void display(boolean visible) {
		loadInfor();
		setVisible(visible);
	}

	private void loadInfor() {
		infor.setListGroup(mainFrame.getData().getListGroup());
		infor.loadListGroup();
	}
}
