import java.util.*;

public class StudentResultManagementDemo {
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1;
    
    static class Student {
        int id;
        String name;
        String rollNumber;
        int subject1, subject2, subject3;
        int total;
        float percentage;
        
        Student(String name, String rollNumber, int s1, int s2, int s3) {
            this.id = nextId++;
            this.name = name;
            this.rollNumber = rollNumber;
            this.subject1 = s1;
            this.subject2 = s2;
            this.subject3 = s3;
            this.total = s1 + s2 + s3;
            this.percentage = total / 3.0f;
        }
    }
    
    public static void main(String[] args) {
        // Add sample data
        students.add(new Student("John Doe", "CS001", 85, 90, 78));
        students.add(new Student("Jane Smith", "CS002", 92, 88, 95));
        
        System.out.println("Connected to in-memory database successfully!");
        
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: searchStudent(); break;
                case 4: 
                    System.out.println("Thank you for using Student Result Management System!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    private static void showMenu() {
        System.out.println("\n=== STUDENT RESULT MANAGEMENT SYSTEM ===");
        System.out.println("1. Add Student Record");
        System.out.println("2. View All Student Records");
        System.out.println("3. Search Student by Roll Number");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void addStudent() {
        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter roll number: ");
            String rollNumber = scanner.nextLine();
            
            System.out.print("Enter marks for Subject 1: ");
            int subject1 = scanner.nextInt();
            
            System.out.print("Enter marks for Subject 2: ");
            int subject2 = scanner.nextInt();
            
            System.out.print("Enter marks for Subject 3: ");
            int subject3 = scanner.nextInt();
            
            Student student = new Student(name, rollNumber, subject1, subject2, subject3);
            students.add(student);
            
            System.out.println("Student record added successfully!");
            System.out.println("Total Marks: " + student.total + "/300");
            System.out.println("Percentage: " + String.format("%.2f", student.percentage) + "%");
            
        } catch (Exception e) {
            System.out.println("Invalid input! Please try again.");
            scanner.nextLine();
        }
    }
    
    private static void viewAllStudents() {
        System.out.println("\n=== ALL STUDENT RECORDS ===");
        System.out.println("+---------+------------------+-------------+------+------+------+-------+-----------+");
        System.out.println("| ID      | Name             | Roll No     | Sub1 | Sub2 | Sub3 | Total | Percentage|");
        System.out.println("+---------+------------------+-------------+------+------+------+-------+-----------+");
        
        if (students.isEmpty()) {
            System.out.println("|                           No records found                                        |");
        } else {
            for (Student s : students) {
                System.out.printf("| %-7d | %-16s | %-11s | %-4d | %-4d | %-4d | %-5d | %-9.2f |\n",
                    s.id, s.name, s.rollNumber, s.subject1, s.subject2, s.subject3, s.total, s.percentage);
            }
        }
        
        System.out.println("+---------+------------------+-------------+------+------+------+-------+-----------+");
    }
    
    private static void searchStudent() {
        System.out.print("Enter roll number to search: ");
        String rollNumber = scanner.nextLine();
        
        Student found = null;
        for (Student s : students) {
            if (s.rollNumber.equals(rollNumber)) {
                found = s;
                break;
            }
        }
        
        if (found != null) {
            System.out.println("\n=== STUDENT FOUND ===");
            System.out.println("ID: " + found.id);
            System.out.println("Name: " + found.name);
            System.out.println("Roll Number: " + found.rollNumber);
            System.out.println("Subject 1: " + found.subject1);
            System.out.println("Subject 2: " + found.subject2);
            System.out.println("Subject 3: " + found.subject3);
            System.out.println("Total Marks: " + found.total + "/300");
            System.out.println("Percentage: " + String.format("%.2f", found.percentage) + "%");
            
            String grade;
            if (found.percentage >= 90) grade = "A+";
            else if (found.percentage >= 80) grade = "A";
            else if (found.percentage >= 70) grade = "B";
            else if (found.percentage >= 60) grade = "C";
            else if (found.percentage >= 50) grade = "D";
            else grade = "F";
            
            System.out.println("Grade: " + grade);
        } else {
            System.out.println("No student found with roll number: " + rollNumber);
        }
    }
}