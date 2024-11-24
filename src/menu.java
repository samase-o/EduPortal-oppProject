package pl.project;
import java.util.Scanner;
import java.util.*;
public class menu {    
    static String type;
    Scanner input = new Scanner(System.in);
    User newUser = new User();
   
    public menu() {}

void LogAndReg(){
   System.out.println("hello in the courses program");
   System.out.println("Enter 1 to login,  2 to Register");
   
   int x = 0;
   boolean validInput = false;
   
   while (!validInput) {
       try {
           x = input.nextInt();
           if(x!= 1&& x!=2) {
               throw new Exception();
           }
           validInput = true;
       } catch (Exception e) {
           System.out.println("Invalid input. Please enter a number.");
           input.next(); // clear the invalid input from the scanner
       }
   }
   
    //login
    if (x == 1)
    {
        System.out.print("Enter your email: ");
        String email = input.next();
        System.out.print("Enter your password: ");
        String pass = input.next();
        type = newUser.login(email, pass);
        this.afterLog();
    }

     //Register
    if (x == 2)
    {
        User newUser = new User();
        newUser.register();
    }
}

void afterLog(){
        if (type.equals("Admin")){
            Admin myAdmin = new Admin();
            myAdmin.manageUsers();
        }
        
        else if (type.equals("Student")){            
                    Student student = new Student();
        

                    int choice = -1;
                    do {
                        try {
                            System.out.println("\nStudent Menu:");
                        System.out.println("1. See All Courses");
                        System.out.println("2. See Notification");
                        System.out.println("3. See Grades for Specific Course");
                        System.out.println("4. Submit Feedback");
                        System.out.println("5. Update Profile");
                        System.out.println("0. Exit");
                        System.out.print("Enter your choice: ");
                            choice = input.nextInt();
                    input.nextLine(); // Consume the newline character
                    switch (choice) {
                        case 1:
                            student.seeAllCourses();
                            break;
                        case 2:
                            student.displayCoursesNearTOStart();
                            student.displayCoursesNearTOEnd();
                        case 3:
                            student.seeGradesForSpecificCourse();
                            break;
                        case 4:
                            student.submitFeedback();
                            break;
                        case 5:
                            student.updateProfile(newUser);
                            break;
                        case 0:
                            System.out.println("Exiting the program. Goodbye!");
                            input.close();
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                            choice = input.nextInt();
                    }

                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            input.next(); // clear the invalid input from the scanner
                        }
                    } while (!(choice == 0));
                    if (choice==0)
                        logout();

                }        
        else if (type.equals("instructor")){
            System.out.println("Hello! You can now update grades for your students.");

        try {
            boolean continueChoosing = true;
            input.nextLine();
            while (continueChoosing) {
                System.out.println("Enter the file path (name of the course) (type 'exit' to finish):");
                String filePath = input.nextLine();

                if (filePath.equalsIgnoreCase("exit")) {
                    continueChoosing = false;
                    logout();
                }

                if (filePath == null || filePath.trim().isEmpty()) {
                    System.out.println("File path cannot be null or empty.");
                    continue;
                }
                FileHandling file = new FileHandling(filePath);
                if (!file.checkFileExistence()) {
                    System.out.println("File not found");
                    continue;
                }

                GradeUpdater updater = new GradeUpdater(filePath);
                updater.updateGrades(); // Use the existing method
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            input.close();
        }

        // Access the static attribute after updating grades (if needed)
        String attribute = GradeUpdater.getStaticAttribute();
        System.out.println("Static Attribute Value: " + attribute);
    }
        
        
     }
void logout(){
            newUser.logout();
}

}
