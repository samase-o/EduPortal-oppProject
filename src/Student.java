/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
//package student;

/**
 *
 * @author WESO
 */
    
package pl.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import com.mycompany.coursesmanagementsystem.Interfaces.StudentInterface;

public class Student extends User implements StudentInterface {
    private final List<String> courses;
    private final List<String> grades;

//    private static final String COURSES_FILE = "courses.txt";
//    private static final String FEEDBACK_FILE = "feedback.txt";
//    private static final String GRADES_FILE = "grades.txt";

    public Student() {
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

  

    public List<String> getCourses() {
        return courses;
    }

    public List<String> getGrades() {
        return grades;
    }

    @Override
public void updateProfile(User newUser) {
    try {
        // Use the student ID from the parent class
        int studentIdToUpdate = newUser.getId();

        FileHandling fileHandling = new FileHandling("student.txt");
        String content = fileHandling.readFile();
        String[] studentsData = content.split("\n");
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < studentsData.length; i++) {
            String[] studentInfo = studentsData[i].split(",");
            String currentStudentId = studentInfo[0].trim();

            if (Integer.parseInt(currentStudentId) == studentIdToUpdate) {
                System.out.println("Enter new name (or press Enter to skip): ");
                String newName = scanner.nextLine();

                System.out.println("Enter new email (or press Enter to skip): ");
                String newEmail = scanner.nextLine();

                System.out.println("Enter new password (or press Enter to skip): ");
                String newPassword = scanner.nextLine();

                // Update the information
                studentInfo[1] = newName.isEmpty() ? studentInfo[1] : newName;
                studentInfo[2] = newEmail.isEmpty() ? studentInfo[2] : newEmail;
                studentInfo[3] = newPassword.isEmpty() ? studentInfo[3] : newPassword;

                // Join the updated information back to the array
                studentsData[i] = String.join(",", studentInfo);
                
                // Join all students' data back to a single string
                String updatedContent = String.join("\n", studentsData);

                // Write the updated content back to the file
                fileHandling.writeFile(updatedContent);

                System.out.println("Profile updated successfully.");
                return; // Exit the loop if the student is found and updated
            }
        }

        System.out.println("Student with ID " + studentIdToUpdate + " not found.");
    } catch (Exception e) {
        System.out.println("Error updating profile: " + e.getMessage());
    }
}


    
    @Override
    public void seeAllCourses() {
        
    int courseIndex = 1;

    System.out.println("--------------------------------------------------");
    System.out.printf("%-10s %-20s %-20s %-10s %-10s %-10s %-15s %-15s %-15s\n",
            "CourseID", "CourseName", "Instructor", "Room", "Branch", "Days", "Price", "Start Date", "End Date");

    while (true) {
        FileHandling courseFile = new FileHandling("C" + courseIndex + ".txt");

        boolean coursesExist = courseFile.checkFileExistence();

        if (!coursesExist) {
            break;
        }

        System.out.printf("%-10s %-20s %-20s %-10s %-10s %-10s %-15s %-15s %-15s ",
                courseIndex, courseFile.readLine(2), courseFile.readLine(3),
                courseFile.readLine(4), courseFile.readLine(5),
                courseFile.readLine(6), courseFile.readLine(7),
                courseFile.readLine(8), courseFile.readLine(9));
        System.out.println();

        courseIndex++;
    }
    }

    @Override
    public void seeGradesForSpecificCourse() {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.println("Enter the name of the file:");
        String fileName = scanner.nextLine();

        System.out.println("Enter your student name:");
        String studentName = scanner.nextLine();

        FileHandling fileHandling = new FileHandling(fileName + ".txt");
        String fileData = fileHandling.readFile();

        if (!fileData.isEmpty()) {
            System.out.println("Student Information for " + studentName + " in " + fileName + ":");

            String[] studentInfo = fileData.split("\n");
            for (String info : studentInfo) {
                String[] parts = info.split(" - ");
                if (parts.length >= 2 && parts[0].equals(studentName)) {
                    System.out.println("Student Name: " + parts[0]);
                    System.out.println("Grade: " + parts[1]);
                    return;
                }
            }

            System.out.println("Student not found in the specified file.");
        } else {
            System.out.println("File not found or empty.");
        }
    } catch (Exception e) {
        System.out.println("Error reading student information: " + e.getMessage());
        }
    }
    
    @Override
    public void submitFeedback() {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.print("Enter the course name for feedback: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter your name: ");
        String studentName = scanner.nextLine();

        String feedbackFilePath = "feedback.txt";

        try {
            FileHandling fileHandling = new FileHandling(feedbackFilePath);
            String existingFeedback = fileHandling.readFile();
            String newFeedback = courseName + "," + studentName + ",";

            System.out.print("Enter your feedback: ");
            String feedbackText = scanner.nextLine();
            newFeedback += feedbackText;

            String updatedFeedback = existingFeedback.isEmpty() ? newFeedback : existingFeedback + "\n" + newFeedback;
            fileHandling.writeFile(updatedFeedback);

            System.out.println("Feedback submitted successfully.");
        } catch (Exception e) {
            System.out.println("Error saving feedback to file: " + e.getMessage());
            }
        }
    }


}



