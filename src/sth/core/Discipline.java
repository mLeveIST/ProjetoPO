package sth.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import sth.core.exception.DuplicateProjectIdException;
import sth.core.exception.NoAssociatedSurveyException;
import sth.core.exception.NoSuchProjectIdException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Discipline implements SurveyShowable, Comparable<Discipline>, java.io.Serializable {

    /** Serial number for serialization */
    private static final long serialVersionUID = 201811152205L;

    /** Maximum number of students a discipline ca have */
    private static final int MAX_STUDENTS_DISCIPLINE = 200;

    /** Discipline name */
    private String _name;
    
    /** Course the Discipline is part of */
    private Course _course;
    
    /** Collection of Students enrolled in the discipline */
    private Set<Student> _students;
    
    /** Colection of teachers that give the discipline */
    private Set<Teacher> _teachers;
    
    /** Discipline projects */
    private Map<String, Project> _projects;

    /** Notifies all interested entities of an opened or finalized survey */
    private Notification _notifier;

    /**
     * Creates a new Discipline. 
     *
     * @param name   - Discipline name
     * @param course - Discipline course
     */
    Discipline(String name, Course course) {
        _name = name;
        _course = course;

        _students = new TreeSet<>();
        _teachers = new HashSet<>();
        _projects = new HashMap<>();
        
        _notifier = new Notification(name);
    }

    /**
     * TODO condition in if can be its own method, maxStudentsReached
     * TODO replace > with == (noob)
     *
     * Adds a new student to the discipline.
     *
     * @param student - Student to enroll in the disscipline
     * @return true if the student was added, false if it already was enrolled, or the discipline cap was reached
     */
    boolean enrollStudent(Student student) {
        if (_students.size() > MAX_STUDENTS_DISCIPLINE)
            return false;

        return _students.add(student);
    }

    /**
     * Adds a new teacher to the discipline.
     *
     * @param teacher - Teacher to add
     * @return true if the teacher was added, false if the passed teacher already existed
     */
    boolean addTeacher(Teacher teacher) {
        return _teachers.add(teacher);
    }
    
    /**
     * Creates a new project in this discipline.
     * 
     * @param projName - Name of the project to create
     *
     * @throws DuplicateProjectIdException When the passed project ID is already in use in the discipline
     */
    void createProject(String projName) throws DuplicateProjectIdException {
        if (_projects.containsKey(projName))
            throw new DuplicateProjectIdException(_name, projName);

        _projects.put(projName, new Project(projName));
    }

    /**
     * Gets a formatted <code>String</code> containing the information of all the students in the discipline. 
     *
     * @return The students info
     */
    String showStudents() {
        String info = "";

        for (Student student : _students) {
            info += student.toString();
        }

        return info;
    }

    /**
     * Gets a formatted <code>String</code> containing the information of a survey in a given project in a given discipline.
     *
     * @param disName  - Name ID of the discipline
     * @param projName - Name ID of the project
     * @return The info of the survey to be visualized
     */
    String showSurveyResults() {
        String info = "";

        List<Project> projects = new ArrayList<>(_projects.values());
        Collections.sort(projects);

        for (Project p : projects) {
            System.out.println(p.hasSurvey());
            try {
                info += showSurveyAnswers(_name, p.getName()) + p.getSurvey().showResults(this) + "\n";
            } catch (NoAssociatedSurveyException nase) {
                continue;
            }
        }
        return info;
    }

    @Override
    public String showSurveyAnswers(String disName, String projName) {
        return disName + " - " + projName + " ";
    }

    @Override
    public String showAnswers(Survey survey) {
        return survey.getNumAnswers() + "- respostas - " 
             + survey.getAverageTime() + " horas";
    }
    
    /**
     * Gets a project associated with the discipline.
     *
     * @param projName - Name of the project to fetch
     * @return The found project
     *
     * @throws NoSuchProjectIdException When the passed project name does not exist in the discipline
     */
    Project getProject(String projName) throws NoSuchProjectIdException {
        if (!_projects.containsKey(projName))
            throw new NoSuchProjectIdException(projName);

        return _projects.get(projName);
    }

    /**
     * Gets the name of the discipline. 
     *
     * @return Discipline name
     */
    String getName() {
        return _name;
    }

    /**
     * Gets the name of the discipline course. 
     *
     * @return Discipline course
     */
    Course getCourse() {
        return _course;
    }

    /**
     * Gets the entity that will notify observers of a survey state change to opened or finished.
     *
     * @return The notifier
     */
    Notification getNotifier() {
        return _notifier;
    }

    /**
     * Adds a new entity to the list of observers to notify when a survey state changes to opened or finished.
     *
     * @param person - The person that wants to be notified
     */
    void giveNotifications(Person person) {
        _notifier.attachPerson(person);
    }
    
    /**
     * Removes an entity from the list of observers to notify when a survey state changes to opened or finished.
     *
     * @param person - The person that wants to be notified
     */
    void stopNotifications(Person person) {
        _notifier.disattachPerson(person);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
               obj instanceof Discipline &&
               ((Discipline) obj)._name.equals(_name) &&
               ((Discipline) obj)._course.equals(_course);
    }

    @Override
    public int compareTo(Discipline d) {
        int equal = _course.compareTo(d._course);

        return equal == 0 ? _name.compareTo(d._name) : equal;
    }
}
