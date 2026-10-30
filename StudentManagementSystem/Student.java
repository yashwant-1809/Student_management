public class Student extends Person {
    private int rollNumber;
    private String email;
    private Course course;
    private double marks;

    public Student(int rollNumber, String name, int age, String email, Course course, double marks) {
        super(name, age);
        this.rollNumber = rollNumber;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    public int getRollNumber() { return rollNumber; }
    public String getEmail() { return email; }
    public Course getCourse() { return course; }
    public double getMarks() { return marks; }
    public void setEmail(String email) { this.email = email; }
    public void setCourse(Course course) { this.course = course; }
    public void setMarks(double marks) { this.marks = marks; }
    public String getStudentType() { return "Regular"; }

    @Override
    public void displayDetails() {
        System.out.println("Type: " + getStudentType());
        System.out.println("Roll number: " + rollNumber);
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Email: " + email);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
    }
}
