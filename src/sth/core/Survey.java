package sth.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    	// TODO
    }

	void cancel() throws NonEmptyAssociatedSurveyException, InvalidSurveyOperationException {
		// TODO
	}

	void open() throws InvalidSurveyOperationException {
		// TODO
	}

	void close() throws InvalidSurveyOperationException {
		// TODO
	}

	void finish() throws InvalidSurveyOperationException {
		// TODO
	}

	void answer(int id, int time, String comment) throws InvalidSurveyOperationException {
		// TODO
	}

	void showResults() {
		// TODO
	}

	SurveyState getState() {
		return null;
		// TODO
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