package sth.core.exception;

/**
 * Exception thrown when the project to create already exists.
 */
public class DuplicateProjectIdException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151343L;

  /** Discipline name. */
  private String _discipline;

  /** Project name. */
  private String _project;
  
  /**
   * @param discipline 
   * @param project 
   */
  public DuplicateProjectIdException(String discipline, String project) {
    _discipline = discipline;
    _project = project;
  }

  /**
   * @return discipline name
   */
  public String getDiscipline() {
    return _discipline;
  }

  /**
   * @return project name
   */
  public String getProject() {
    return _project;
  }
}
