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

    private Map<String,Discipline> _disciplines;

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


    boolean becomeRepresentative() {
        if (_course.addRepresentative(this)) {
            _representative = true;
            return true;
        }
        return false;
    }

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

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
               obj instanceof Student &&
               ((Student)obj).getId() == getId();
    }

    @Override
    String getType() {
        return _representative ? "DELEGADO" : "ALUNO";
    }

    // Needs changes for Map
    @Override
    String getInfo() {
        String info = "";

        List<Discipline> disciplines = new ArrayList<>(_disciplines.values());
        Collections.sort(disciplines);

        for (Discipline d : disciplines)
            info += "* " + _course.getName() + " - " + d.getName() + "\n";

        return info;
    }

    Discipline getDiscipline(String disName) throws NoSuchDisciplineIdException {
        if(!_disciplines.containsKey(disName))
            throw new NoSuchDisciplineIdException(disName);
        return _disciplines.get(disName);
    }

    void submitProject(String disName, String projName, String submission) throws NoSuchDisciplineIdException, NoSuchProjectIdException, ClosedProjectException {
        getDiscipline(disName).getProject(projName).submit(this,submission);
    }

    void answerSurvey(String disName, String projName, int time, String comment) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, NoSubmissionsMadeException, InvalidSurveyOperationException {
        getDiscipline(disName).getProject(projName).answerSurvey(getId(), time, comment);
    }

    void createSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateAssociatedSurveyException {
        getDiscipline(disName).getProject(projName).addSurvey();
    }

    void cancelSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, NonEmptyAssociatedSurveyException, InvalidSurveyOperationException {
        getDiscipline(disName).getProject(projName).getSurvey().cancel();
    }

    void openSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        Discipline discipline = getDiscipline(disName);
        discipline.getProject(projName).getSurvey().open();
        discipline.getNotifier().sendAllMessage("");

    }

    void closeSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        getDiscipline(disName).getProject(projName).getSurvey().close();
    }

    void finalizeSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        Discipline discipline = getDiscipline(disName);
        discipline.getProject(projName).getSurvey().finish();
        discipline.getNotifier().sendAllMessage("");
    }

    String showSurveyResults(String disName) throws NoSuchDisciplineIdException {
        return getDiscipline(disName).showSurveys(this);

    }

    void enableNotifications(String disName) throws NoSuchDisciplineIdException {
        getDiscipline(disName).giveNotifications(this);
    }
    
    void disableNotifications(String disName) throws NoSuchDisciplineIdException {
        getDiscipline(disName).stopNotifications(this);
    }

    @Override
    public String showSurveyResults(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException {
        return disName + " - " + projName + " " + getDiscipline(disName).getProject(projName).getSurvey().showResults(this);
    }

    @Override
    public String showServey(Survey survey) {
        return null;
    }

}
