package sth.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sth.core.exception.NoAssociatedSurveyException;
import sth.core.exception.NonEmptyAssociatedSurveyException;
import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Survey implements java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152211L;

    private Project _project;
    private SurveyState _state;
    private Set<Integer> _ids;
    private List<Answer> _answers;

    Survey(Project project) {
    	_project = project;
    	_state = new Created();
    }

    Project getProject(){
    	return _project;
	}

	void cancel() throws NonEmptyAssociatedSurveyException, InvalidSurveyOperationException {
    	_state.cancel(this);
    }

	void open() throws InvalidSurveyOperationException {
		_state.open(this);
	}

	void close() throws InvalidSurveyOperationException {
		_state.close(this);
	}

	void finish() throws InvalidSurveyOperationException {
		_state.finish(this);
	}

	void answer(int id, int time, String comment) throws NoAssociatedSurveyException, InvalidSurveyOperationException {
		_state.answer(this, id, time, comment);
	}

	void addId(int id){
    	_ids.add(id);
	}

	void addAnswer(int time, String comment){
    	_answers.add(new Answer(time,comment));
	}

	String showResults(SurveyAccess person) {
		return _state.showResults(this, person);
	}

	SurveyState getState() {
		return null;
		// TODO
	}

	void setState(SurveyState state){
    	_state = state;
	}

	boolean hasAnswers() {
		return false;
		// TODO
	}

	int getNumAnswers() {
		return 0;
		// TODO
	}

	int getMinTime() {
		return 0;
		// TODO
	}

	int getAverageTime() {
		return 0;
		// TODO
	}

	int getMaxTime() {
		return 0;
		// TODO
	}

	private class Answer {

		private int _time;
		private String _comment;

		Answer(int time, String comment) {
			// TODO
		}

		int getTime() {
			return 0;
			// TODO
		}

		String getComment() {
			return null;
			// TODO
		}
	}
}