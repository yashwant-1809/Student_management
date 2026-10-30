public class Course {
    private String courseName;
    private String department;

    public Course(String courseName, String department) {
        this.courseName = courseName;
        this.department = department;
    }

    public String getCourseName() { return courseName; }
    public String getDepartment() { return department; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return courseName + " (" + department + ")";
    }
}
