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

    /** List of Observers */
	Set<Notifiable> _people;
	
	/**
	 * Creates a new notification mechanism.
	 */
	public Notification() {
		_people = new HashSet<>();
	}
	
	/**
	 * 
	 * @param person - New observer to notify
	 */
	public void attachPerson(Notifiable person) {
		_people.add(person);
	}
	
	/**
	 * 
	 * @param person - Observer to stop notifying
	 */
	public void disattachPerson(Notifiable person) {
		_people.remove(person);
	}
	
	/**
	 * 
	 * @param notification - Notification being sent to all observers
	 */
	public void sendAllMessage(String notification) {
		for (Notifiable p : _people)
			p.addNotification(notification);
	}
}
