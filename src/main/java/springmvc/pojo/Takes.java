package springmvc.pojo;


public class Takes {

  private String id;
  private String course_id;
  private String sec_id;
  private String semester;
  private int year;
  private String grade;


  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.id = id;
  }


  public String getCourse_id() {
    return course_id;
  }

  public void setCourse_id(String course_id) {
    this.course_id = course_id;
  }

  public String getSec_id() {
    return sec_id;
  }

  public void setSec_id(String sec_id) {
    this.sec_id = sec_id;
  }

  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }


  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }


  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

}
