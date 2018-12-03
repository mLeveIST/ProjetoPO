package sth.core;

import sth.core.exception.NoAssociatedSurveyException;
import sth.core.exception.NonEmptyAssociatedSurveyException;
import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public interface SurveyState {

	/**
	 * Cancels the passed survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param survey - Survey to cancel
	 * @param notifier - Entity that will notify observers if the survey is opened
	 *
	 * @throws NonEmptyAssociatedSurveyException When the survey to cancel is opened and has received answers
	 * @throws InvalidSurveyOperationException When the survey is in a finished state, canceling is impossible
	 */
	public void cancel(Survey survey, Notification notifier) throws NonEmptyAssociatedSurveyException, InvalidSurveyOperationException;

	/**
	 * Opens the passed survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param survey   - Survey to open
	 * @param notifier - Entity that will notify observers if the survey is opened
	 *
	 * @throws InvalidSurveyOperationException When the survey is in a finished state, opening is impossible
	 */
	public void open(Survey survey, Notification notifier) throws InvalidSurveyOperationException;

	/**
	 * Closes the passed survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param survey - Survey to close
	 *
	 * @throws InvalidSurveyOperationException When the survey is in a created or finished state, closing is impossible
	 */
	public void close(Survey survey) throws InvalidSurveyOperationException;

	/**
	 * Finalizes the passed survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param survey   - Survey to finalize
	 * @param notifier - Entity that will notify observers if the survey is finalized
	 *
	 * @throws InvalidSurveyOperationException When the survey is in a created or opened state, finalizing is impossible
	 */
	public void finish(Survey survey, Notification notifier) throws InvalidSurveyOperationException;

	/**
	 * Adds an answer to the passed survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param survey  - Survey to answer
	 * @param id 	  - ID of the student answering the survey
	 * @param time 	  - Time the student took to finish the project
	 * @param comment - Comment on the project
	 *
	 * @throws InvalidSurveyOperationException When the survey is not in an opened state, answering is impossible
	 */
	public void answer(Survey survey, int id, int time, String comment) throws InvalidSurveyOperationException;

	/**
	 * Gets the results of the passed survey.
	 * The actions of this method depend on the surveys state.
	 * 
	 * @param survey - Survey whose results are to be sent
	 * @param person - The person that is asking for the survey results
	 * @return The results of the survey
	 */
	public String showResults(Survey survey, SurveyShowable shower);
}