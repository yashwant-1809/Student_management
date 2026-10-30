public interface StudentOperations {
    void addStudent(Student student) throws StudentNotFoundException;
    void displayStudents();
    Student searchStudent(int rollNumber) throws StudentNotFoundException;
    void updateStudent(int rollNumber, String newEmail, double newMarks) throws StudentNotFoundException;
    void deleteStudent(int rollNumber) throws StudentNotFoundException;
}
