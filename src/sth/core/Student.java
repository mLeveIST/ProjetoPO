package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.ClosedProjectException;
import sth.core.exception.DuplicateAssociatedSurveyException;
import sth.core.exception.InvalidSurveyOperationException;
import sth.core.exception.NonEmptyAssociatedSurveyException;
import sth.core.exception.NoAssociatedSurveyException;
import sth.core.exception.NoSubmissionsMadeException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

import java.util.Set; // TODO change to HashMap
import java.util.TreeSet; // TODO change to Map

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Student extends Person implements java.io.Serializable {

    /** Serial number for serialization */
    private static final long serialVersionUID = 201811152207L;

    /** Maximum number of disciplines per Student */
    private static final int MAX_NUM_DISCIPLINES = 6;

    private Set<Discipline> _disciplines; // Change to Map<String, Discipline>
    private int _numDisciplines; // remove

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

        _disciplines = new TreeSet<>(); // Change to HashMap
        _numDisciplines = 0; // remove
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

        // Substitute with commented below
        if (_disciplines.size() == MAX_NUM_DISCIPLINES || _disciplines.contains(discipline))
            return false;

        _numDisciplines++;
        discipline.enrollStudent(this);
        return _disciplines.add(discipline);

        /* ****

        if (maxNumDisciplines() || _disciplines.containsValue(discipline) || discipline.getCourse() != _course)
            return false;

        _disciplines.put(discipline.getName(), discipline);
        discipline.enrollStudent(this);
        
        return true;

        **** */ 
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
    
    /*
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
    */

    // substitute
    boolean unmakeRepresentaive() {
        if (!isRepresentative())
            return false;

        _course.subNumRepresentatives();
        _representative = false;

        return true;
    }

    // substitute
    boolean makeRepresentaive() {
        if (isRepresentative() && !_course.addNumRepresentatives())
            return false;

        _representative = true;
        return true;
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

        for (Discipline d : _disciplines)
            info += "* " + _course.getName() + " - " + d.getName() + "\n";

        return info;
    }

    Discipline getDiscipline(String disName) throws NoSuchDisciplineIdException {
        // TODO
        return null;
    }

    void submitProject(String disName, String projName, String submission) throws NoSuchDisciplineIdException, NoSuchProjectIdException, ClosedProjectException {
        // TODO
    }

    void answerSurvey(String disName, String projName, int time, String comment) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, NoSubmissionsMadeException, InvalidSurveyOperationException {
        // TODO Entrega final
        // Chamar answerSurvey do projeto
    }

    // ************************************
    // ****** NÃO FAZER ESTE COMANDO ******
    // ************************************
    String showSurveyResults(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException {
        return null;
        // NOT TODO
    }

    void createSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateAssociatedSurveyException {
        // TODO Entrega final
    }

    void cancelSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, NonEmptyAssociatedSurveyException, InvalidSurveyOperationException {
        // TODO Entrega final
    }

    void openSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        // TODO Entrega final
        // Nao esquecer de notificar observers
    }

    void closeSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        // TODO Entrega final
    }

    void finalizeSurvey(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoAssociatedSurveyException, InvalidSurveyOperationException {
        // TODO Entrega final
        // Nao esquecer de notificar observers
    }

    String showSurveyResults(String disName) throws NoSuchDisciplineIdException {
        return null;
        // TODO
    }

    void enableNotifications(String disName) throws NoSuchDisciplineIdException {
        // TODO
    }
    
    void disableNotifications(String disName) throws NoSuchDisciplineIdException {
        // TODO
    }
}
