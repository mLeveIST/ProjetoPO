package sth.core;

import java.util.HashMap;
import java.util.Map;

import sth.core.exception.NoSuchProjectIdException;

public class Project implements Comparable<Project> {

	private String _name;
	private String _description;
	private boolean _open;
	private Map<Integer, Submission> _submissions;
	private Survey _survey;

	Project(String name) {
		_name = name;
		_open = true;
	}

	String getName() {
		return _name;
	}

	String getDescription() {
		return _description;
	}

	boolean isOpen() {
		return _open;
	}

	void submit(Student student, String submission) {
		_submissions.put(student.getId(), new Submission(submission));
	}

	void close() {
		_open = false;
	}

	@Override
	public int compareTo(Project p) {
		return _name.compareTo(p._name);
	}

	private class Submission {

		private String _submission;

		Submission(String submission) {
			_submission = submission;
		}

		@Override
		public String toString() {
			return _submission;
		}
	}
}
