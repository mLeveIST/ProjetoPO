package sth.core;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Notification implements java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152212L;

    /** Name of the discipline that has this notification entity */
    private String _disciplineName;

    /** List of Observers */
	Set<Notifiable> _people;
	
	/**
	 * Creates a new notification central.
	 */
	Notification(String disName) {
		_disciplineName = disName;
		_people = new HashSet<>();
	}
	
	/**
	 * Adds a new entity to the list of observers to notify.
	 *
	 * @param person - New observer to notify
	 */
	void attachPerson(Notifiable person) {
		_people.add(person);
	}
	
	/**
	 * Removes an entity from the list of observers to notify.
	 *
	 * @param person - Observer to stop notifying
	 */
	void disattachPerson(Notifiable person) {
		_people.remove(person);
	}
	
	/**
	 * Sends a notification to all observers.
	 *
	 * @param notification - Notification being sent to all observers
	 */
	void sendAllMessage(String notification) {
		for (Notifiable p : _people)
			p.addNotification(notification);
	}

	/**
	 * Gets the name of the associated discipline.
	 *
	 * @return Discipline name
	 */
	String getDisName() {
		return _disciplineName;
	}
}
