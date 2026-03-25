import java.io.*;
import java.util.*;

public class StudentRecordProcessor {

    private final List<Student> students = new ArrayList<>();
    private double averageScore;
    private Student highestStudent;

    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("data/students.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {
                    String[] parts = line.split(",");

                    if (parts.length != 2) {
                        System.out.println("Invalid data: " + line);
                        continue;
                    }

                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Score must be between 0 and 100");
                    }

                    students.add(new Student(name, score));
                    System.out.println("Valid: " + line);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid data: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: data/students.txt");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {

        if (students.isEmpty()) {
            return;
        }

        int sum = 0;

        highestStudent = students.get(0);

        for (Student s : students) {

            sum += s.score;

            if (s.score > highestStudent.score) {
                highestStudent = s;
            }
        }

        averageScore = (double) sum / students.size();

        students.sort((a, b) -> Integer.compare(b.score, a.score));
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/report.txt"))) {

            writer.write("Average: " + averageScore);
            writer.newLine();

            writer.write("Highest: " + highestStudent.name + " - " + highestStudent.score);
            writer.newLine();
            writer.newLine();

            writer.write("Sorted Students:");
            writer.newLine();

            for (Student s : students) {
                writer.write(s.name + " - " + s.score);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing file");
        }
    }

    public static void main(String[] args) {

        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}


/* ================================
   Custom Exception
   ================================ */
class InvalidScoreException extends Exception {

    public InvalidScoreException(String message) {
        super(message);
    }
}


/* ================================
   Student Class
   ================================ */
class Student {

    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
}