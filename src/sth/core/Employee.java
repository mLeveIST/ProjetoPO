package sth.core;

import java.util.List;
import java.util.ArrayList;

public class Employee extends Person implements java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152209L;

	//private List<Course> _courses;

	Employee(int id, int phoneNum, String name) {
		super(id, phoneNum, name);
		//_courses = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "FUNCIONÁRIO" + super.toString();
	}
}
