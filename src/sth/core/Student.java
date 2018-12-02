package sth.core;

import sth.core.exception.*;

import java.util.*;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Student extends Person implements SurveyAccess, java.io.Serializable {

    /** Serial number for serialization */
    private static final long serialVersionUID = 201811152207L;

    /** Maximum number of disciplines per Student */
    private static final int MAX_NUM_DISCIPLINES = 6;

    private Map<String, Discipline> _disciplines;

    /** Student course */
    private Course _course;
    
    /** true if the Student is a representative, false otherwise */
    private boolean _representative;

    /**
     * Creates a new student without a set course.
     * 
     * @param id             - Student ID number
     * @param phoneNum       - Student phone number
     * @param name           - Student name
     * @param representative - true if representative, false otherwise
     */
	Student(int id, int phoneNum, String name, boolean representative) {
        this(id, phoneNum, name, representative, null);
    }

    /**
     * Creates a new student.
     * 
     * @param id             - Student ID number
     * @param phoneNum       - Student phone number
     * @param name           - Student name
     * @param representative - true if representative, false otherwise
     * @param course         - Student course
     */
    Student(int id, int phoneNum, String name, boolean representative, Course course) {
        super(id, phoneNum, name);

        _disciplines = new HashMap<>();
        _representative = representative;
        _course = course;
    }

    @Override
    void parseContext(String context, School school) throws BadEntryException {
        String components[] =  context.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + context);

        if (_course == null) { 
            addCourse(school.parseCourse(components[0]));
            _course.addStudent(this);
        }

        Discipline discipline = _course.parseDiscipline(components[1]);
        addDiscipline(discipline);
    }

    /**
     * Adds a course to the student, representing the course the student is enrolled in.
     * 
     * @param course - Course to be added
     * @return true if the course was set, false if the student already had a course
     */
    boolean addCourse(Course course) {
        if (_course != null)
            return false;

        _course = course;
        return true;
    }

    /**
     * Adds a discipline to the student, representing a discipline the student is now taking.
     * 
     * @param discipline - Discipline to be added
     * @return true if the discipline was added, false otherwise
     */
    boolean addDiscipline(Discipline discipline) {

        if (maxNumDisciplines() || _disciplines.containsValue(discipline) || discipline.getCourse() != _course)
            return false;

        _disciplines.put(discipline.getName(), discipline);
        discipline.enrollStudent(this);

        return true;

    }

    /**
     * Tells if the student is already enrolled in the max number of disciplines.
     *
     * @return true if the student is taking max disciplines, false otherwise
     */
    boolean maxNumDisciplines() {
        return getNumDisciplines() == MAX_NUM_DISCIPLINES;
    }

    /**
     * Gets the number of enrolled disciplines.
     * 
     * @return The number of diciplines the student is enrolled in
     */
    int getNumDisciplines() {
        return _disciplines.size();
    }

    /**
     * Turns a student into a student representative.
     *
     * @return true if the student became a representative, false otherwise
     */
    boolean becomeRepresentative() {
        if (_course.addRepresentative(this)) {
            _representative = true;
            return true;
        }
        return false;
    }

    /**
     * Turns a student into a normal student.
     * 
     * @return true if the Student became normal, false if the student was not a representative
     */
    boolean unbecomeRepresentative() {
        _representative = false;
        return _course.removeRepresentative(this);
    }

    /**
     * Tells if the student is a representative. 
     *
     * @return true if the Student is a representative, false otherwise
     */
    boolean isRepresentative() {
        return _representative;
    }

    /**
     * Gets a specific discipline from the ones the student is enrolled in, given its name.
     * 
     * @param disName - Name ID of the discipline
     *
     * @return The discipline whose name was passed in
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the students disciplines
     */
    Discipline getDiscipline(String disName) throws NoSuchDisciplineIdException {
        if (!_disciplines.containsKey(disName))
            throw new NoSuchDisciplineIdException(disName);
        return _disciplines.get(disName);
    }

    /**
     * Submites to a given project from a given discipline.
     * The submission is represent by a <code>String</code> passed in.
     * 
     * @param disName    - Name ID of the discipline
     * @param projName   - Name ID of the project
     * @param submission - Concrete submission to the project, represented by a <code>String</code>
     * 
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines the student is enrolled in
     * @throws NoSuchProjectIdException When the passed project ID is not found in the chosen disciline
     * @throws ClosedProjectException When the project to submit to has already closed
     */
    void submitProject(String disName, String projName, String submission) throws NoSuchDisciplineIdException, NoSuchProjectIdException, ClosedProjectException {
        getDiscipline(disName).getProject(projName).submit(this, submission);
    }

    /**
     * Answers the survey of a given project from a given discipline.
     * The answer is composed of the time that took to finish the project and a comment on said project.
     * 
     * @param disName  - Name ID of the discipline
     * @param projName - Name ID of the project
     * @param time     - Time that took to fill the survey
     * @param comment  - Comment on the project
     * 
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines the student is enrolled
     * @throws NoSuchProjectIdException When the passed project ID is not found in the chosen disciline
     * @throws NoAssociatedSurveyException When the chosen project does not have a survey associated with it
     * @throws NoSubmissionsMadeException When the current user did not submit to the project
     * @throws InvalidSurveyOperationException When the survey is not opened
     */
    void answerSurvey(String disName, String projName, int time, String comment) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, NoSubmissionsMadeException, InvalidSurveyOperationException {
        getDiscipline(disName).getProject(projName).answerSurvey(getId(), time, comment);
    }

    @Override
    public String showSurveyResults(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException {
        return disName + " - " + projName + " " + getDiscipline(disName).getProject(projName).getSurvey().showResults(this);
    }

    @Override
    public String showServey(Survey survey) {
        return " * Número de respostas: " + survey.getNumAnswers()
             + " * Tempo médio (horas): " + survey.getAverageTime();
    }

    /**
     * Creates a new survey for a given project of a given discipline.
     * 
     * @param disName  - Name ID of the discipline
     * @param projName - Name ID of the project
     *
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the course the student is part of
     * @throws NoSuchProjectIdException When the passed project ID is not found in the chosen disciline
     * @throws DuplicateAssociatedSurveyException When the chosen project already has a survey associated with it
     */
    void createSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateAssociatedSurveyException {
        _course.getDiscipline(disName).getProject(projName).addSurvey();
    }

    /**
     * Cancels an existing survey in a given project of a given discipline.
     * 
     * @param disName  - Name ID of the discipline
     * @param projName - Name ID of the project
     *
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the course the student is part of
     * @throws NoSuchProjectIdException When the passed project ID is not found in the chosen disciline
     * @throws NoAssociatedSurveyException When the chosen project does not have a survey associated with it
     * @throws NonEmptyAssociatedSurveyException When the survey being canceled has already received answers
     * @throws InvalidSurveyOperationException When the survey being canceled has already been finalized
     */
    void cancelSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, NonEmptyAssociatedSurveyException, InvalidSurveyOperationException {
        _course.getDiscipline(disName).getProject(projName).getSurvey().cancel();
    }

    /**
     * Opens an existing survey in a given project of a given discipline.
     * All entities with enabled notifications to the passed dicipline will receive a message if the survey opened.
     * 
     * @param disName  - Name ID of the discipline
     * @param projName - Name ID of the project
     *
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the course the student is part of
     * @throws NoSuchProjectIdException When the passed project ID is not found in the chosen disciline
     * @throws NoAssociatedSurveyException When the chosen project does not have a survey associated with it
     * @throws InvalidSurveyOperationException When the survey being opened is already finalized or opened
     */
    void openSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        Discipline discipline = _course.getDiscipline(disName);

        discipline.getProject(projName).getSurvey().open();
        discipline.getNotifier().sendAllMessage("Pode preencher o inquérito do projeto " + projName + " da disciplina " + disName);
    }

    /**
     * Closes an existing survey in a given porject of a given discipline.
     * 
     * @param disName  - Name ID of the discipline
     * @param projName - Name ID of the project
     *
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the course the student is part of
     * @throws NoSuchProjectIdException When the passed project ID is not found in the chosen disciline
     * @throws NoAssociatedSurveyException When the chosen project does not have a survey associated with it
     * @throws InvalidSurveyOperationException When the survey being closed is already finalized or not yet opened
     */
    void closeSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        _course.getDiscipline(disName).getProject(projName).getSurvey().close();
    }

    /**
     * Finalizes an existing survey in the given porject of a given discipline.
     * All entities with enabled notifications to the passed dicipline will receive a message if the survey finalizes.
     * 
     * @param disName  - Name ID of the discipline
     * @param projName - Name ID of the project
     *
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the course the student is part of
     * @throws NoSuchProjectIdException When the passed project ID is not found in the chosen disciline
     * @throws NoAssociatedSurveyException When the chosen project does not have a survey associated with it
     * @throws InvalidSurveyOperationException When the survey being finalized is not closed
     */
    void finalizeSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        Discipline discipline = _course.getDiscipline(disName);

        discipline.getProject(projName).getSurvey().finish();
        discipline.getNotifier().sendAllMessage("Resultados do inquérito do projeto " + projName + " da disciplina " + disName);
    }

    /**
     * Gets a formatted <code>String</code> containing the information of all surveys in a given discipline.
     *
     * @param disName - Name ID of the discipline
     * @return The info of the surveys to be visualized
     *
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the course the student is part of
     */
    String showSurveyResults(String disName) throws NoSuchDisciplineIdException {
        return _course.getDiscipline(disName).showSurveys(this);
    }

    /**
     * Enables the passed discipline to send notification to the student every time a survey opes or finalizes.
     *
     * @param disName - Name ID of the discipline
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines the student is enrolled
     */
    void enableNotifications(String disName) throws NoSuchDisciplineIdException {
        getDiscipline(disName).giveNotifications(this);
    }
    
    /**
     * Disables the passed discipline to send notification to the student every time a survey opes or finalizes.
     *
     * @param disName - Name ID of the discipline
     * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines the student is enrolled
     */
    void disableNotifications(String disName) throws NoSuchDisciplineIdException {
        getDiscipline(disName).stopNotifications(this);
    }

    @Override
    String getType() {
        return _representative ? "DELEGADO" : "ALUNO";
    }

    @Override
    String getInfo() {
        String info = "";

        List<Discipline> disciplines = new ArrayList<>(_disciplines.values());
        Collections.sort(disciplines);

        for (Discipline d : disciplines)
            info += "* " + _course.getName() + " - " + d.getName() + "\n";

        return info;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
               obj instanceof Student &&
               ((Student)obj).getId() == getId();
    }
}
