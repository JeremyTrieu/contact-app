package jeremytrieu;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
	private ArrayList<Person> listPerson = new ArrayList<Person>();
	private ArrayList<String> listGroup;

	public Data() {
	}

	public ArrayList<Person> getListPerson() {
		return listPerson;
	}

	public void setListPerson(ArrayList<Person> listPerson) {
		this.listPerson = listPerson;
	}

	public ArrayList<String> getListGroup() {
		listGroup = new ArrayList<String>();
		for (int i = 0; i < listPerson.size(); i++) {
			boolean check = true;
			for (int j = i - 1; j >= 0; j--) {
				if (listPerson.get(i).getGroup()
						.equals(listPerson.get(j).getGroup())) {
					check = false;
					break;
				}
			}
			if (check) {
				listGroup.add(listPerson.get(i).getGroup());
			}
		}
		return listGroup;
	}

	public void setListGroup(ArrayList<String> listGroup) {
		this.listGroup = listGroup;
	}
}
