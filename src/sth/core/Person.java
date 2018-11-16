package sth.core;

import java.util.Comparator;

import sth.core.exception.BadEntryException;

public abstract class Person implements Comparable<Person>, java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152206L;

	private int _id;
	private int _phoneNum;
	private String _name;

	// Might turn to method or declare instead at Collection.sort call outside Person
	private static Comparator<Person> _compByName = new Comparator<Person>() {

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

	static Comparator<Person> getComparatorByName() {
		return _compByName;
	}

	void setPhoneNumber(int newPhoneNum) {
		_phoneNum = newPhoneNum;
	}

	void parseContext(String context, School school) throws BadEntryException {
		throw new BadEntryException("Should not have extra context: " + context);
	}

	@Override
	public int compareTo(Person p) {
		return _id - p._id;
	}

	@Override
	public String toString() {
		return "|" + _id + "|" + _phoneNum + "|" + _name + "\n";
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
