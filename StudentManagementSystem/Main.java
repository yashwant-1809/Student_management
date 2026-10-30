import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem("students.txt");
        try {
            system.loadFromFile();
            System.out.println("Loaded " + system.getStudentsSnapshot().size() + " student record(s).");
        } catch (IOException e) {
            System.out.println("Could not load records: " + e.getMessage());
        }

        Thread autoSaveThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(30000);
                    system.saveToFile();
                    System.out.println("[Auto-save] Student records saved.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    System.out.println("[Auto-save error] " + e.getMessage());
                }
            }
        }, "Student-Auto-Save");
        autoSaveThread.setDaemon(true);
        autoSaveThread.start();

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1 -> addStudent(system, false);
                    case 2 -> addStudent(system, true);
                    case 3 -> system.displayStudents();
                    case 4 -> {
                        int roll = readInt("Enter roll number to search: ");
                        system.searchStudent(roll).displayDetails();
                    }
                    case 5 -> {
                        int roll = readInt("Enter roll number to update: ");
                        String email = readText("Enter new email: ");
                        double marks = readDouble("Enter new marks (0-100): ");
                        validateMarks(marks);
                        system.updateStudent(roll, email, marks);
                    }
                    case 6 -> system.deleteStudent(readInt("Enter roll number to delete: "));
                    case 7 -> {
                        system.saveToFile();
                        System.out.println("Records saved.");
                    }
                    case 8 -> {
                        system.loadFromFile();
                        System.out.println("Records loaded.");
                    }
                    case 0 -> {
                        system.saveToFile();
                        running = false;
                        System.out.println("Records saved. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please select from the menu.");
                }
            } catch (StudentNotFoundException | IOException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        autoSaveThread.interrupt();
        input.close();
    }

    private static void showMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add regular student");
        System.out.println("2. Add graduate student");
        System.out.println("3. Display all students");
        System.out.println("4. Search student");
        System.out.println("5. Update student email and marks");
        System.out.println("6. Delete student");
        System.out.println("7. Save records to file");
        System.out.println("8. Reload records from file");
        System.out.println("0. Save and exit");
    }

    private static void addStudent(StudentManagementSystem system, boolean graduate)
            throws StudentNotFoundException {
        int roll = readInt("Enter roll number: ");
        String name = readText("Enter name: ");
        int age = readInt("Enter age: ");
        if (age <= 0 || age > 120) throw new IllegalArgumentException("Age must be between 1 and 120.");
        String email = readText("Enter email: ");
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        String courseName = readText("Enter course name: ");
        String department = readText("Enter department: ");
        double marks = readDouble("Enter marks (0-100): ");
        validateMarks(marks);
        Course course = new Course(courseName, department);
        Student student;
        if (graduate) {
            String topic = readText("Enter research topic: ");
            student = new GraduateStudent(roll, name, age, email, course, marks, topic);
        } else {
            student = new Student(roll, name, age, email, course, marks);
        }
        system.addStudent(student);
    }

    private static void validateMarks(double marks) {
        if (marks < 0 || marks > 100) throw new IllegalArgumentException("Marks must be between 0 and 100.");
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        String value = input.nextLine().trim();
        if (value.isEmpty()) throw new IllegalArgumentException("This field cannot be empty.");
        return value;
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(input.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a whole number.");
        }
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(input.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number.");
        }
    }
}
