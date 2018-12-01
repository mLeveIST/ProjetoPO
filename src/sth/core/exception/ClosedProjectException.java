package sth.core.exception;

/** Exception thrown when the requested project does not exist. */
public class ClosedProjectException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 565656565678L;

  /** Project id. */
  private String _projectName;

  /**
   * @param id
   */
  public ClosedProjectException(String id) {
    _projectName = id;
  }

  /** @return id */
  public String getId() {
    return _projectName;
  }

}