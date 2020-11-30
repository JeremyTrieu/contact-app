package jeremytrieu;

import java.io.Serializable;
import java.util.Comparator;

public class Person implements Serializable {
	private String name, group, addres, phone, lastName;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAddres() {
		return addres;
	}

	public void setAddres(String addres) {
		this.addres = addres;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Person() {
	}

	public Person(String name, String phone, String group, String addres) {
		super();
		this.name = name;
		this.group = group;
		this.addres = addres;
		this.phone = phone;
		this.lastName = getLastNameOfName();
		System.out.println("last name is " + lastName);
	}

	private String getLastNameOfName() {
		String str = getName();
		int i = str.length() - 1;
		while (i > 0) {
			if (str.charAt(i) == ' ') {
				return str.substring(i + 1);
			}
			i--;
		}
		return str;
	}

	public String toString() {
		return name + ", " + phone + ", " + group + ", " + addres;
	}

	public static Comparator<Person> PersonNameComparator = new Comparator<Person>() {
		public int compare(Person p1, Person p2) {
			return p1.getLastName().compareTo(p2.getLastName());
		}
	};
}
