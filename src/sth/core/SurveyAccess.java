package sth.core;

import sth.core.exception.NoAssociatedSurveyException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

public interface SurveyAccess {

    public String showSurveyResults(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException;

    public String showServey(Survey survey);
}
