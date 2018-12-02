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

	public void cancel(Survey survey) throws NonEmptyAssociatedSurveyException, InvalidSurveyOperationException;

	public void open(Survey survey) throws InvalidSurveyOperationException;

	public void close(Survey survey) throws InvalidSurveyOperationException;

	public void finish(Survey survey) throws InvalidSurveyOperationException;

	public void answer(Survey survey, int id, int time, String comment) throws InvalidSurveyOperationException;

	public String showResults(Survey survey, SurveyAccess person);
}