package sth.core;

import java.util.Comparator;

public abstract class Person implements Comparable<Person> {

	private String _name;
	private int _id;
	private int _teleNum;

	// Might turn to method or declare instead at sort call outside Person
	private Comparator<Person> _compByName = new Comparator<Person>() {

		@Override
		public int compare(Person p1, Person p2) {
			return p1._name.compareTo(p2._name);
		}
	};

	public Person(String name, int id, int teleNum) {
		_name = name;
		_id = id;
		_teleNum = teleNum;
	}

	public String getName() {
		return _name;
	}

	public int getId() {
		return _id;
	}

	public int getTeleNum() {
		return _teleNum;
	}

	public Comparator<Person> getComparatorByName() {
		return _compByName;
	}

	@Override
	public String toString() {
		return "|" + _id + "|" + _teleNum + "|" + _name;
	}

	@Override
	public int compareTo(Person p) {
		return _id - p._id;
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
