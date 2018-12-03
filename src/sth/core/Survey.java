package sth.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sth.core.exception.NoAssociatedSurveyException;
import sth.core.exception.NonEmptyAssociatedSurveyException;
import sth.core.exception.InvalidSurveyOperationException;

/**
 * Class that represents a survey of a specific project.
 * A survey can be in diferent states, represented by a <code>SurveyState</code> type.
 * The possible states are: Created, Opened, Closed and Finalized.
 * More states and behaviors can be added/changed easily whitout altering this class.
 * A survey also stores, anonymously, the answers of the students that submitted to the associated project.
 * @see Project
 * @see SurveyState
 * @see Answer
 *
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Survey implements java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152211L;

    /** Project that has this survey */
    private Project _project;

    /** State of the survey that decides certain methods actions */
    private SurveyState _state;

    /** Set of student IDs that anonymously stores the students who already filled this survey */
    private Set<Integer> _ids;

    /** List of answers made to the survey */
    private List<Answer> _answers;

	/**
	 * Creates a new survey given the project it belongs to.
	 *
	 * @param project - Project that has this survey
	 */
    Survey(Project project) {
    	_project = project;
    	_state = new Created();
    	_ids = new HashSet<>();
    	_answers = new ArrayList<>();
    }

    /**
	 * Cancels the survey.
	 * The actions of this method depend on the surveys state.
	 *
	 * @param notifier - Entity that will notify observers if the survey is opened
	 *
	 * @throws NonEmptyAssociatedSurveyException When the survey to cancel is opened and has received answers
	 * @throws InvalidSurveyOperationException When the survey is in a finished state, canceling is impossible
	 */
	void cancel(Notification notifier) throws NonEmptyAssociatedSurveyException, InvalidSurveyOperationException {
    	_state.cancel(this, notifier);
    }

    /**
	 * Opens the survey.
	 * The actions of this method depend on the surveys state.
	 *
	 * @param notifier - Entity that will notify observers if the survey is opened
	 *
	 * @throws InvalidSurveyOperationException When the survey is in a finished state, opening is impossible
	 */
	void open(Notification notifier) throws InvalidSurveyOperationException {
		_state.open(this, notifier);
	}

	/**
	 * Closes the survey.
	 * The actions of this method depend on the surveys state.
	 *
	 * @throws InvalidSurveyOperationException When the survey is in a created or finished state, closing is impossible
	 */
	void close() throws InvalidSurveyOperationException {
		_state.close(this);
	}

	/**
	 * Finalizes the survey.
	 * The actions of this method depend on the surveys state.
	 *
	 * @param notifier - Entity that will notify observers if the survey is finalized
	 * 
	 * @throws InvalidSurveyOperationException When the survey is in a created or opened state, finalizing is impossible
	 */
	void finish(Notification notifier) throws InvalidSurveyOperationException {
		_state.finish(this, notifier);
	}

	/**
	 * Adds an answer to the survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param id 	  - ID of the student answering the survey
	 * @param time 	  - Time the student took to finish the project
	 * @param comment - Comment on the project
	 *
	 * @throws InvalidSurveyOperationException When the survey is not in an opened state, answering is impossible
	 */
	void answer(int id, int time, String comment) throws NoAssociatedSurveyException, InvalidSurveyOperationException {
		_state.answer(this, id, time, comment);
	}

	/**
	 * Gets the results of the answers to the survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param person - The person that is asking for the survey results
	 * @return The results of the survey
	 */
	String showResults(SurveyShowable shower) {
		return _state.showResults(this, shower);
	}

	/**
	 * Adds an answer to the survey.
	 *
	 * @param id 	  - ID of the student answering the survey
	 * @param time 	  - Time the student took to finish the project
	 * @param comment - Comment on the project
	 * @return true if the answer was successfully added, false if the student had already answered
	 */
	boolean addAnswer(int id, int time, String comment) {
		if (_ids.add(id)) {
    		_answers.add(new Answer(time,comment));
    		return true;
		}
		return false;
	}

	/**
	 * Tells if the survey has been answered.
	 *
	 * @return true if the survey as at least one answer, false otherwise
	 */
	boolean hasAnswers() {
		return getNumAnswers() > 0;
	}

	/**
	 * Gets the number of answers to the survey.
	 *
	 * @return Number of answers to the survey
	 */
	int getNumAnswers() {
		return _answers.size();
	}

	/**
	 * Changes the current state of the survey to a new one.
	 *
	 * @param state - New state to put the survey on
	 */
	void setState(SurveyState state) {
    	_state = state;
	}

	SurveyState getState() {
		return null;
		// TODO
	}

	/**
	 * Gets the project that has this survey associated.
	 *
	 * @return The survey project
	 */
	Project getProject() {
    	return _project;
	}

	/**
	 * Gets the minimum time the project took to be finished.
	 *
	 * @return The minimum time the project was solved in 
	 */
	int getMinTime() {
		int min = hasAnswers() ? _answers.get(0).getTime() : 0;
		
		for (Answer a : _answers)
			if (a.getTime() < min)
				min = a.getTime();

		return min;
	}

	/**
	 * Gets the average time the project took to be finished.
	 *
	 * @return The average time the project was solved in
	 */
	int getAverageTime() {
		int sum = 0;
		
		for (Answer a : _answers)
			sum += a.getTime();
		
		return sum / getNumAnswers();
	}

	/**
	 * Gets the maximum time the project took to be finished.
	 *
	 * @return The maximum time the project was solved in
	 */
	int getMaxTime() {
		int max = 0;
		
		for (Answer a : _answers)
			if (a.getTime() > max)
				max = a.getTime();

		return max;
	}

	/**
 	 * Class that represents an answer to the outer survey.
 	 * An answer is composed of a comment to the associated project
 	 * and the time that took to finish said project.
 	 *
 	 * @author Miguel Levezinho,  No 90756
 	 * @author Rafael Figueiredo, No 90770
 	 * @version 2.0
 	 */
	private class Answer implements java.io.Serializable {

		/** Serial number for serialization */
    	private static final long serialVersionUID = 201812030257L;

		/** Time that took to finish the project */
		private int _time;

		/** Comment to the submited project */
		private String _comment;

		/**
		 * Creates a new answer to the survey, given a time a comment on the project.
		 *
		 * @param time    - Time that took to finish the project
		 * @param comment - Comment to the submited project
		 */
		Answer(int time, String comment) {
			_comment = comment;
			_time = time;
		}

		/**
		 * Gets the time that took to finish the project
		 * 
		 * @return The time that took to finish the project
		 */
		int getTime() {
			return _time;
		}

		/**
		 * Gets the comment to the submited project
		 * 
		 * @return The comment to the submited project
		 */
		String getComment() {
			return _comment;
		}
	}
}