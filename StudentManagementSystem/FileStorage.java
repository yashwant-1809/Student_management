import java.io.*;
import java.util.ArrayList;

public class FileStorage {
    private final String fileName;

    public FileStorage(String fileName) {
        this.fileName = fileName;
    }

    public synchronized void save(ArrayList<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                String type = s instanceof GraduateStudent ? "G" : "R";
                String topic = s instanceof GraduateStudent ? ((GraduateStudent) s).getResearchTopic() : "";
                writer.write(s.getRollNumber() + "|" + clean(s.getName()) + "|" + s.getAge() + "|" +
                        clean(s.getEmail()) + "|" + clean(s.getCourse().getCourseName()) + "|" +
                        clean(s.getCourse().getDepartment()) + "|" + s.getMarks() + "|" + type + "|" + clean(topic));
                writer.newLine();
            }
        }
    }

    public synchronized ArrayList<Student> load() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return students;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] p = line.split("\\|", -1);
                    if (p.length != 9) continue;
                    int roll = Integer.parseInt(p[0]);
                    String name = p[1];
                    int age = Integer.parseInt(p[2]);
                    String email = p[3];
                    Course course = new Course(p[4], p[5]);
                    double marks = Double.parseDouble(p[6]);
                    if (p[7].equals("G")) {
                        students.add(new GraduateStudent(roll, name, age, email, course, marks, p[8]));
                    } else {
                        students.add(new Student(roll, name, age, email, course, marks));
                    }
                } catch (NumberFormatException ignored) {
                    System.out.println("Skipping invalid record in storage file.");
                }
            }
        }
        return students;
    }

    private String clean(String value) {
        return value.replace('|', '/').replace('\n', ' ').replace('\r', ' ');
    }
}
