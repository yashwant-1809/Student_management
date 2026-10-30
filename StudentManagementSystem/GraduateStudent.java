public class GraduateStudent extends Student {
    private String researchTopic;

    public GraduateStudent(int rollNumber, String name, int age, String email,
                           Course course, double marks, String researchTopic) {
        super(rollNumber, name, age, email, course, marks);
        this.researchTopic = researchTopic;
    }

    public String getResearchTopic() { return researchTopic; }
    public void setResearchTopic(String researchTopic) { this.researchTopic = researchTopic; }

    @Override
    public String getStudentType() { return "Graduate"; }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Research topic: " + researchTopic);
    }
}
