package sth.core;

import java.util.Comparator;

import sth.core.exception.BadEntryException;

/**
 * TODO
 *
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public abstract class Person implements Notifiable, Comparable<Person>, java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152206L;

	/** Perosn ID number */
	private int _id;
	
	/** Person phone number */
	private int _phoneNum;
	
	/** Person name */
	private String _name;
	
	/** Notifications from disciplines */
	private String _messages;

	/** Comparator for sorting persons by their names */
	private static Comparator<Person> _compByName = new Comparator<Person>() {

		@Override
		public int compare(Person p1, Person p2) {
			return p1._name.compareTo(p2._name);
		}
	};

	/**
	 * Creates a new person.
	 *
	 * @param id       - Perosn ID number
	 * @param phoneNum - Person phone number
	 * @param name     - Person name
	 */
	Person(int id, int phoneNum, String name) {
		_id = id;
		_phoneNum = phoneNum;
		_name = name;
		_messages = "";
	}

	/**
	 * TODO
	 *
	 * @param context - String representing information about the person
	 * @param school  - School from which the objects represented in the context will be created
	 *
	 * @throws BadEntryException When the expected format of the input file is wrong
	 */
	void parseContext(String context, School school) throws BadEntryException {
		throw new BadEntryException("Should not have extra context: " + context);
	}

	/**
	 * Gets the pending notifications of the person.
	 *
	 * @return All unseen notifications
	 */
	String showNotifications() {
		String messages = _messages;
		_messages = "";
		return messages;
	}

	/**
	 * Gets this persons name.
	 *
	 * @return Person name
	 */
	String getName() {
		return _name;
	}

	/**
	 * Gets this persons id.
	 *
	 * @return Person ID number
	 */
	int getId() {
		return _id;
	}

	/**
	 * Gets this persons phone number.
	 *
	 * @return Person phone number
	 */
	int getPhoneNum() {
		return _phoneNum;
	}

	/**
	 * Gets a persons comparator, to compare persons by name.
	 *
	 * @return A way to compare Persons by name
	 */
	static Comparator<Person> getComparatorByName() {
		return _compByName;
	}

	/**
	 * Sets the persons phone number.
	 *
	 * @param newPhoneNum - Persons new phone number
	 */
	void setPhoneNumber(int newPhoneNum) {
		_phoneNum = newPhoneNum;
	}

	@Override
	public void addNotification(String notification) {
		_messages += notification + "\n";
	}

	@Override
	public int compareTo(Person p) {
		return _id - p._id;
	}

	@Override
	public String toString() {
		return getType() + "|" + _id + "|" + _phoneNum + "|" + _name + "\n" + getInfo();
	}

	/**
	 * Gets the persons type <code>String</code>.
	 *
	 * @return The person type
	 */
	abstract String getType();

	/**
	 * Gets a persons information, formatted accordingly.
	 *
	 * @return The person info
	 */
	abstract String getInfo();

	@Override
	public boolean equals(Object obj) {
		return obj != null && 
			   obj instanceof Person &&
			   ((Person) obj)._id == _id;
	}

	@Override
	public int hashCode() {
		return _id;
	}
}
