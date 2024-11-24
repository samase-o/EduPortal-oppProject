package pl.project;

import java.io.*;
import java.util.*;
import pl.project.User;
import java.util.Scanner;
import static pl.project.User.nextId;
import static pl.project.User.writeNextId;
//import AdminInterface;

public class Admin extends User implements AdminInterface{
    private static final String ADMIN_FILE = "admins.txt";

    @Override
    public void addStudent() {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter student name: ");
        String name = scanner.nextLine();

        System.out.println("Enter student email: ");
        String email = scanner.nextLine();
        
        while (!isEmailUnique(email)) {
            System.out.println("Email already in use.");
            System.out.print("Enter another email:");
            email = scanner.nextLine();
        }

        System.out.println("Enter student password: ");
        String password = scanner.nextLine();
        // public Student(int par, String student_Name, String studentemailcom, String password, int par1) {
        int id = nextId++;
        try {
            FileHandling fileHandler = new FileHandling("student.txt");
            String content = id + "," + name + "," + email + "," + password;
            fileHandler.appendFile(content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        writeNextId(nextId);
        System.out.println("Student added successfully");

        }
    @Override
    public void addInstructor() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter instructor name: ");
        String name = scanner.nextLine();

        System.out.println("Enter instructor email: ");
        String email = scanner.nextLine();

        while (!isEmailUnique(email)) {
            System.out.println("Email already in use.");
            System.out.print("Enter another email:");
            email = scanner.nextLine();
        }

        System.out.println("Enter instructor password: ");
        String password = scanner.nextLine();
        // public Student(int par, String student_Name, String studentemailcom, String password, int par1) {
        int id = nextId++;
        try {
            FileHandling fileHandler = new FileHandling("instructor.txt");
            String content = id + "," + name + "," + email + "," + password;
            fileHandler.appendFile(content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        writeNextId(nextId);
        System.out.println("instructor added successfully");

    }

    @Override
    public void updateUser(Scanner scanner) {
    try {
        
        System.out.println("Enter User type: ");
        String TyprIdToUpdate = scanner.nextLine();

        System.out.println("Enter User ID: ");
        String studentIdToUpdate = scanner.nextLine();

        FileHandling fileHandling = new FileHandling(TyprIdToUpdate+ ".txt");
        String content = fileHandling.readFile();
        String[] studentsData = content.split("\n");

        for (int i = 0; i < studentsData.length; i++) {
            String[] studentInfo = studentsData[i].split(",");
            String currentStudentId = studentInfo[0].trim();

            if (currentStudentId.equals(studentIdToUpdate)) {
                System.out.println("Enter new name (or press Enter to skip): ");
                String newName = scanner.nextLine();

                System.out.println("Enter new email (or press Enter to skip): ");
                String newEmail = scanner.nextLine();

                System.out.println("Enter new password (or press Enter to skip): ");
                String newPassword = scanner.nextLine();

                studentInfo[1] = newName.isEmpty() ? studentInfo[1] : newName;
                studentInfo[2] = newEmail.isEmpty() ? studentInfo[2] : newEmail;
                studentInfo[3] = newPassword.isEmpty() ? studentInfo[3] : newPassword;

                studentsData[i] = String.join(",", studentInfo);
                
                String updatedContent = String.join("\n", studentsData);

                fileHandling.writeFile(updatedContent);

                System.out.println("Profile updated successfully.");
                return; 
            }
        }

        System.out.println("Student with ID " + studentIdToUpdate + " not found.");
    } catch (Exception e) {
        System.out.println("Error updating profile: " + e.getMessage());
        }
    }
   @Override
   public void deleteUser(Scanner scanner) {
    System.out.println("Enter user type: ");
    String userType = scanner.nextLine();

    System.out.println("Enter " + userType + " email: ");
    String userEmail = scanner.nextLine();

    String filePath = userType + ".txt";
    FileHandling fileHandling = new FileHandling(filePath);

    try {
        int lineNumber = 1;
        String line;
        boolean userDeleted = false;

        while ((line = fileHandling.readLine(lineNumber)) != null) {
            if (line.contains(userEmail)) {
                userDeleted = true;
                break;  // Exit the loop as soon as the email is found
            }
            lineNumber++;
        }

        if (userDeleted) {
            // Delete the line only if the user was found
            fileHandling.deleteLine(lineNumber);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found or could not be deleted.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}



    @Override
    public void showAllUsers() {
        
        System.out.println("All Instractor:");
        FileHandling fileHandling = new FileHandling("instructor.txt");
        String instractorData = fileHandling.readFile();
        System.out.println(instractorData);

        System.out.println("All Student:");
        FileHandling fileHandling2 = new FileHandling("Student.txt");
        String StudentData = fileHandling2.readFile();
        System.out.println(StudentData);

    
    }

    private int getUserChoice() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
        }

        return scanner.nextInt();
    }


//    private void saveUsersToFile(List<User> users) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
//            for (User user : users) {
//                if (user instanceof Student) {
//                    Student student = (Student) user;
//                    writer.write(student.getStudentId() + "," + student.getName() + "," + student.getEmail() + "," +
//                            student.getPassword() + "," + student.getType() + "\n");
//                } else if (user instanceof Instructor) {
//                    Instructor instructor = (Instructor) user;
//                    writer.write(instructor.getName() + "," + instructor.getEmail() + "," + instructor.getPassword() +
//                            "," + instructor.getType() + "\n");
//                } else {
//                    writer.write(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getType() + "\n");
//                }
//            }
//            System.out.println("Users information saved to file.");
//        } catch (IOException e) {
//            System.out.println("Error saving users information to file: " + e.getMessage());
//        }
//    }
//}
//
    
    public boolean isStudentFoundByID(String name) {
        try {
            File file = new File("student.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
            BufferedReader reader = new BufferedReader(new FileReader("student.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[1].equals(name)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 public void registerCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student name: ");
        String nameInput = scanner.nextLine();
        System.out.println("Enter course name: ");
        String courseName = scanner.nextLine();
        boolean isFound = isStudentFoundByID(nameInput);
         
        while(!isFound) {
            System.out.println("there is no student with that id!");
            System.out.println("Enter student name: ");
            nameInput = scanner.nextLine();
            isFound = isStudentFoundByID(nameInput);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader("student.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[1].equals(nameInput)) {
                    FileHandling fileHandler = new FileHandling(courseName+".txt");
                    String content = nameInput + " - " + "No grade available";
                    System.out.println(userData[1] + " - " + "No grade available");
                    fileHandler.appendFile(content);
                }
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
 }
 public void manageUsers() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Add Instructor");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Show All Students");
            System.out.println("6. Show All Instructors");
            System.out.println("7. Register course");
            System.out.println("8. Add course");
            System.out.println("9. Update course");
            System.out.println("10. Delet course");
            System.out.println("0. Logout");


            int choice = getUserChoice();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addInstructor();
                case 3 -> updateUser(new Scanner(System.in));
                case 4 -> deleteUser(new Scanner(System.in));
                case 5 -> showAllUsers();
                case 6 -> showAllInstructor();
                case 7 ->  registerCourse();
                case 8 ->  addCourse();
                case 9 ->  updateCourse();
                case 10 ->  deleteCourse();
                case 0 ->{
                    System.out.println("Logging out.");
                    menu Menu = new menu();
                    Menu.LogAndReg();
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }}