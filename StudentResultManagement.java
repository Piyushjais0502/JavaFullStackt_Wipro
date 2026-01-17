import java.sql.*;
import java.util.Scanner;

public class StudentResultManagement {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database successfully!");
            
            // Main menu loop
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        searchStudent();
                        break;
                    case 4:
                        System.out.println("Thank you for using Student Result Management System!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                scanner.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
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
            
            // Calculate total and percentage
            int total = subject1 + subject2 + subject3;
            float percentage = (total / 3.0f);
            
            String sql = "INSERT INTO student_results (name, roll_number, subject1, subject2, subject3, total, percentage) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, rollNumber);
            stmt.setInt(3, subject1);
            stmt.setInt(4, subject2);
            stmt.setInt(5, subject3);
            stmt.setInt(6, total);
            stmt.setFloat(7, percentage);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student record added successfully!");
                System.out.println("Total Marks: " + total + "/300");
                System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
            }
            
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input! Please try again.");
            scanner.nextLine(); // clear invalid input
        }
    }
    
    private static void viewAllStudents() {
        try {
            String sql = "SELECT * FROM student_results ORDER BY id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("\n=== ALL STUDENT RECORDS ===");
            System.out.println("+---------+------------------+-------------+------+------+------+-------+-----------+");
            System.out.println("| ID      | Name             | Roll No     | Sub1 | Sub2 | Sub3 | Total | Percentage|");
            System.out.println("+---------+------------------+-------------+------+------+------+-------+-----------+");
            
            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                System.out.printf("| %-7d | %-16s | %-11s | %-4d | %-4d | %-4d | %-5d | %-9.2f |\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("roll_number"),
                    rs.getInt("subject1"),
                    rs.getInt("subject2"),
                    rs.getInt("subject3"),
                    rs.getInt("total"),
                    rs.getFloat("percentage"));
            }
            
            if (!hasRecords) {
                System.out.println("|                           No records found                                        |");
            }
            
            System.out.println("+---------+------------------+-------------+------+------+------+-------+-----------+");
            
        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }
    
    private static void searchStudent() {
        try {
            System.out.print("Enter roll number to search: ");
            String rollNumber = scanner.nextLine();
            
            String sql = "SELECT * FROM student_results WHERE roll_number = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\n=== STUDENT FOUND ===");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Roll Number: " + rs.getString("roll_number"));
                System.out.println("Subject 1: " + rs.getInt("subject1"));
                System.out.println("Subject 2: " + rs.getInt("subject2"));
                System.out.println("Subject 3: " + rs.getInt("subject3"));
                System.out.println("Total Marks: " + rs.getInt("total") + "/300");
                System.out.println("Percentage: " + String.format("%.2f", rs.getFloat("percentage")) + "%");
                
                // Grade calculation
                float percentage = rs.getFloat("percentage");
                String grade;
                if (percentage >= 90) grade = "A+";
                else if (percentage >= 80) grade = "A";
                else if (percentage >= 70) grade = "B";
                else if (percentage >= 60) grade = "C";
                else if (percentage >= 50) grade = "D";
                else grade = "F";
                
                System.out.println("Grade: " + grade);
            } else {
                System.out.println("No student found with roll number: " + rollNumber);
            }
            
        } catch (SQLException e) {
            System.out.println("Error searching student: " + e.getMessage());
        }
    }
}