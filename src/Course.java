/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.project;

/**
 *
 * @author HP
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Course implements CourseInterface{

    Scanner input= new Scanner(System.in);
    private String name;
    private String instructor;
    private String room;
    private String branch;
    private double price;
    private Date startDate ;
    private int  days;
    private Date endDate;
    private final long millisecondsToAdd =  24 * 60 * 60 * 1000;
    private final   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public Course() {
    }

    
    @Override
public void addCourse() {
        Scanner input = new Scanner(System.in);
        int id = generateNextId();


        System.out.println("Enter name Course:");
        name = input.next();
        FileHandling fileCurs = new FileHandling( "C"+ id + ".txt");
        fileCurs.createFile();
        fileCurs.appendFile(String.valueOf(id));
        fileCurs.appendFile(name);


        System.out.println("Enter instructor Course:");
        instructor = input.next();
        
        fileCurs.appendFile(instructor);

        System.out.println("Enter room Course:");
        room = input.next();
        fileCurs.appendFile(room);

        System.out.println("Enter branch Course:");
        branch = input.next();
        fileCurs.appendFile(branch);

        while (true) {
            System.out.println("Enter days Course:");
            if (input.hasNextInt()) {
                days = input.nextInt();
                fileCurs.appendFile(String.valueOf(days));
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer for the days.");
                input.next(); // Consume the invalid input
            }
        }
        while (true) {
            System.out.println("Enter price Course:");
            if (input.hasNextDouble()) {
                price = input.nextDouble();
                fileCurs.appendFile(String.valueOf(price));
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid double value for the price.");
                input.next(); // Consume the invalid input
            }
        }

        this.startDate = getStartDate();
        endDate = new Date(this.startDate.getTime() + (millisecondsToAdd  * (long) days)   );
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fileCurs.appendFile(dateFormat.format(this.startDate));
        fileCurs.appendFile(dateFormat.format(this.endDate));
    }    
    private boolean isIdExists(int id) {
        FileHandling fileCou = new FileHandling("C" + id + ".txt");
        return fileCou.checkFileExistence();
    }
 
    

    @Override
    public void updateCourse() {
        String courseName;
        FileHandling fileCou;

        // Repeat until a valid course name is entered
        do {
            System.out.println("Enter course name:");
            courseName = input.next();

            // Find the course file by name
            fileCou = findCourseFileByName(courseName);

            if (fileCou == null || !fileCou.checkFileExistence()) {
                System.out.println("Course not found. Please enter a valid course name.");
            }

        } while (fileCou == null || !fileCou.checkFileExistence());

        int choice;
        while (true) {
            System.out.println("What do you want to update\n1-Course Name\n2-instructor\n3-room\n4-branch\n5-price\n6-Course days\n7-Start Date\n8-exit");
            choice = input.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("current name : "+fileCou.readLine(2));
                    System.out.println("Enter new name:");
                    String newName = input.next();
                    fileCou.deleteLine(2);
                    fileCou.appendLine(2, newName);
                }
                case 2 -> {
                    System.out.println("current instructor : "+fileCou.readLine(3));
                    System.out.println("Enter new instructor:");
                    String newInstructor = input.next();
                    fileCou.deleteLine(3);
                    fileCou.appendLine(3, newInstructor);
                }
                case 3 -> {
                    System.out.println("current room : "+fileCou.readLine(4));
                    System.out.println("Enter new room:");
                    String newRoom = input.next();
                    fileCou.deleteLine(4);
                    fileCou.appendLine(4, newRoom);
                }
                case 4 -> {
                    System.out.println("current branch : "+fileCou.readLine(5));
                    System.out.println("Enter new branch:");
                    String newBranch = input.next();
                    fileCou.deleteLine(5);
                    fileCou.appendLine(5, newBranch);
                }
                case 5 -> {
                    System.out.println("current price : "+fileCou.readLine(7));
                    System.out.println("Enter new price:");
                    String newPrice = input.next();
                    fileCou.deleteLine(7);
                    fileCou.appendLine(7, newPrice);
                }
                case 6 -> {
                    try {
                        System.out.println("Current days: " + fileCou.readLine(6));
                        System.out.println("Enter new number of course days:");
                        int newDays = input.nextInt();

                        String startDateString = fileCou.readLine(8);
                        if (startDateString != null && !startDateString.isEmpty()) {
                            Date startDate = dateFormat.parse(startDateString);

                            long millisecondsToAdd = 24 * 60 * 60 * 1000L;
                            Date endDate = new Date(startDate.getTime() + (millisecondsToAdd * (long) newDays));

                            fileCou.updateLine(6, String.valueOf(newDays));
                            fileCou.updateLine(9, dateFormat.format(endDate));
                        } else {
                            System.out.println("Error: No date available on line 8.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Error converting string to date:" + e.getMessage());
                    }
                }
                case 7 -> {
                    try {
                        System.out.println("Current start date: " + fileCou.readLine(8));
                        System.out.println("Enter new start date (yyyy-MM-dd):");
                        String newStartDateString = input.next();

                        Date newStartDate = dateFormat.parse(newStartDateString);

                        int currentDays = Integer.parseInt(fileCou.readLine(6));
                        Date newEndDate = new Date(newStartDate.getTime() + (millisecondsToAdd * (long) currentDays));

                        fileCou.updateLine(8, dateFormat.format(newStartDate));
                        fileCou.updateLine(9, dateFormat.format(newEndDate));
                    } catch (ParseException e) {
                        System.out.println("Error converting string to date: " + e.getMessage());
                    }
                }
                case 8 -> {
                    return;
                }
                default -> System.out.println("Invalid number");
            }
        }
    }





    @Override
    public void deleteCourse()
    {
        while (true) {
            int id,choice;
            System.out.println("Enter id : ");
            id = input.nextInt();
            FileHandling fileCou = new FileHandling("C" + id + ".txt");
            if (fileCou.checkFileExistence()) {
                System.out.println("1-toDelete\n2-exit");
                choice=input.nextInt();
                switch (choice)
                {
                    case 1:
                        System.out.println("Delete Course"+id);
                        fileCou.deleteFile();
                    case 2:
                        return;
                }
            }
            else {
                System.out.println("Not Found");
                return;
            }
        }

    }





    @Override
    public void showAllInstructor() {
        System.out.println("------------------- Instructor------------------");
        FileHandling file = new FileHandling("instructor.txt");
        String fileContent = file.readFile();
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] values = line.split(",");
            System.out.println(values[2]);
        }
    }
   
    

    @Override
    public void showAllStudents() {
        System.out.println("------------------- Students------------------");
        FileHandling file = new FileHandling("student.txt");
        String fileContent = file.readFile();
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] values = line.split(",");
            System.out.println(values[1]);
        }
    }






    @Override
    public void displayCoursesNearTOStart() {
        int i = 1;
        while (true) {
            FileHandling fileCou = new FileHandling("C" + i + ".txt");
            if (fileCou.checkFileExistence()) {
                String nameCourse = fileCou.readLine(2);
                String startDate = fileCou.readLine(8);

                if (startDate != null && !startDate.isEmpty()) {
                    String near = checkStartDate(startDate);
                    if (near.equals("near")) {
                        System.out.println("send");
                        FileHandling fileStar = new FileHandling("Start.txt");
                        fileStar.createFile();
                        fileStar.appendFile("Course " + nameCourse + " is near to start in: " + startDate);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
            i++;
        }
    }

    
    
    
    private String checkStartDate(String startDate) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start;
        try {
            start = LocalDate.parse(startDate, formatter);
        } catch (Exception e) {

            return "not near";
        }

        if (start.isAfter(today) && start.minusDays(5).isBefore(today)) {
            return "near";
        } else {
            return "not near";
        }
    }






    @Override
    public void displayCoursesNearTOEnd(){
        int i = 1;
        while (true) {
            FileHandling fileCou = new FileHandling("C" + i + ".txt");
            if (fileCou.checkFileExistence()) {
                String nameCourse = fileCou.readLine(2);
                String endDate = fileCou.readLine(9);

                if (endDate != null && !endDate.isEmpty()) {
                    String near = checkStartDate(endDate);
                    if (near.equals("near")) {
                        System.out.println("send");
                        FileHandling fileStar = new FileHandling("Start.txt");
                        fileStar.createFile();
                        fileStar.appendFile("Course " + nameCourse + " is near to end in: " + endDate);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            else {
                break;
            }
            i++;
        }
    }


    
    
    private Date getStartDate() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter start date (yyyy-MM-dd): ");
        String inputDate = input.next();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return dateFormat.parse(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    
    

    @Override
    public void showDetailsAllCourses() {
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
    
    

    private String readLine(String filename, int lineNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int currentLine = 1;

            while ((line = reader.readLine()) != null) {
                if (currentLine == lineNumber) {
                    return line;
                }
                currentLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






    private void showStudentsAndGradesInTable(int customerId) {
        FileHandling fileStudents = new FileHandling("Student" + customerId + ".txt");
        if (fileStudents.checkFileExistence()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileStudents.getPath()))) {
                String studentLine;
                int currentLine = 1;

                while ((studentLine = reader.readLine()) != null) {
                    if (currentLine == customerId) {
                        String[] parts = studentLine.split("-");

                        if (parts.length == 2) {
                            String studentName = parts[0].trim();
                            String studentGrade = parts[1].trim(); 

                           
                            System.out.printf("%-25s %-25s\n", studentName, studentGrade);
                        } else {
                            System.out.println("The line does not have a valid split.");
                        }
                        break;
                    }
                    currentLine++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found.");
        }
    }


 
    
    private int generateNextId() {
        int id = 1;

        while (true) {
            FileHandling fileCou = new FileHandling("C" + id + ".txt");
            if (!fileCou.checkFileExistence()) {
                break;
            }
            id++;
        }

        return id;
    }

    private FileHandling findCourseFileByName(String courseName) {
        int i = 1;
        while (true) {
            FileHandling fileCou = new FileHandling("C" + i + ".txt");
            if (fileCou.checkFileExistence()) {
                String currentCourseName = fileCou.readLine(2);
                if (currentCourseName.equals(courseName)) {
                    return fileCou;
                }
            } else {
                break;
            }
            i++;
        }
        return null;
}

}