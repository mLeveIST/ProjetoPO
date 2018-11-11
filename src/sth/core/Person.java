package sth.core;

import java.util.Comparator;

public abstract class Person implements Comparable<Person> {

	private int _id;
	private int _phoneNum;
	private String _name;

	// Might turn to method or declare instead at Collection.sort call outside Person
	private Comparator<Person> _compByName = new Comparator<Person>() {

		@Override
		public int compare(Person p1, Person p2) {
			return p1._name.compareTo(p2._name);
		}
	};

	Person(int id, int phoneNum, String name) {
		_id = id;
		_phoneNum = phoneNum;
		_name = name;
	}

	String getName() {
		return _name;
	}

	int getId() {
		return _id;
	}

	int getPhoneNum() {
		return _phoneNum;
	}

	Comparator<Person> getComparatorByName() {
		return _compByName;
	}

	void setPhoneNum(int newPhoneNum) {
		_phoneNum = newPhoneNum;
	}

	abstract void addContext(Course course, Discipline discipline);

	@Override
	public int compareTo(Person p) {
		return _id - p._id;
	}

	@Override
	public String toString() {
		return "|" + _id + "|" + _phoneNum + "|" + _name;
	}

	// ******* IF A SET OF PERSONS IS USED **********

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Person && _id == ((Person) o)._id;
	}

	@Override
	public int hashCode() {
		return _id;
	}
}
