import java.io.IOException;
import java.util.ArrayList;

public class StudentManagementSystem implements StudentOperations {
    private final ArrayList<Student> students;
    private final FileStorage storage;

    public StudentManagementSystem(String fileName) {
        students = new ArrayList<>();
        storage = new FileStorage(fileName);
    }

    @Override
    public synchronized void addStudent(Student student) throws StudentNotFoundException {
        for (Student s : students) {
            if (s.getRollNumber() == student.getRollNumber()) {
                throw new IllegalArgumentException("That roll number already exists.");
            }
        }
        students.add(student);
        System.out.println("Student added successfully.");
    }

    @Override
    public synchronized void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        for (Student student : students) {
            System.out.println("------------------------------");
            student.displayDetails();
        }
    }

    @Override
    public synchronized Student searchStudent(int rollNumber) throws StudentNotFoundException {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) return student;
        }
        throw new StudentNotFoundException("No student found with roll number " + rollNumber);
    }

    @Override
    public synchronized void updateStudent(int rollNumber, String newEmail, double newMarks)
            throws StudentNotFoundException {
        Student student = searchStudent(rollNumber);
        student.setEmail(newEmail);
        student.setMarks(newMarks);
        System.out.println("Student updated successfully.");
    }

    @Override
    public synchronized void deleteStudent(int rollNumber) throws StudentNotFoundException {
        Student student = searchStudent(rollNumber);
        students.remove(student);
        System.out.println("Student deleted successfully.");
    }

    public synchronized void saveToFile() throws IOException {
        storage.save(new ArrayList<>(students));
    }

    public synchronized void loadFromFile() throws IOException {
        students.clear();
        students.addAll(storage.load());
    }

    public synchronized ArrayList<Student> getStudentsSnapshot() {
        return new ArrayList<>(students);
    }
}
