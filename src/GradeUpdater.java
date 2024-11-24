/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.project;

import java.io.*;
import java.util.*;



   
import java.io.*;
import java.util.*;

public class GradeUpdater extends FileHandling implements GradeUpdatable{

    private static String staticAttribute;
    private String filePath;

  
    public GradeUpdater(String filePath) {
       super(filePath);;
    }
@Override
    public void updateGrades() {
        try {
              String fileContent = readFile();
            Map<String, Integer> grades = new LinkedHashMap<>();

            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    int grade = parts[1].trim().equals("No grade available") ? -1 : Integer.parseInt(parts[1].trim());
                    System.out.println(name + " - " + (grade != -1 ? grade : "No grade available"));
                    grades.put(name, grade);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
            reader.close();

            Scanner scanner = new Scanner(System.in);

            // Modify grades in memory
            while (true) {
                System.out.println("\nEnter the name of the student to update the grade (type 'exit' to finish):");
                String studentName = scanner.nextLine().trim();

                if (studentName.equalsIgnoreCase("exit")) {
                    break;
                }

                if (!grades.containsKey(studentName)) {
                    System.out.println("Student name not found. Please enter an existing name.");
                    continue;
                }

                System.out.println("Enter the new grade for " + studentName + ":");
                int newGrade = Integer.parseInt(scanner.nextLine().trim());

                // Update the grade for the specific student
                grades.put(studentName, newGrade);
            }

           
             writeFile(""); 
            for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                appendFile(entry.getKey() + " - " + (entry.getValue() != -1 ? entry.getValue() : "No grade available"));
            }
           

           
            System.out.println("\nUpdated IDs, Names, and Grades:");
            for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                System.out.println(entry.getKey() + " - " + (entry.getValue() != -1 ? entry.getValue() : "No grade available"));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
     public static void updateGradesStatic(String filePath) {
        GradeUpdater updater = new GradeUpdater(filePath);
        updater.updateGrades();
    }

  
    public static String getStaticAttribute() {
        return staticAttribute;
    }

  public void updateGrades(String fileName) {
        try {
            File file = new File(fileName); // Use a different file name parameter
            Map<String, Integer> grades = new LinkedHashMap<>();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    int grade = parts[1].trim().equals("No grade available") ? -1 : Integer.parseInt(parts[1].trim());
                    System.out.println(name + " - " + (grade != -1 ? grade : "No grade available"));
                    grades.put(name, grade);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
            reader.close();

            Scanner scanner = new Scanner(System.in);

           
            while (true) {
                System.out.println("\nEnter the name of the student to update the grade (type 'exit' to finish):");
                String studentName = scanner.nextLine().trim();

                if (studentName.equalsIgnoreCase("exit")) {
                    break;
                }

                if (!grades.containsKey(studentName)) {
                    System.out.println("Student name not found. Please enter an existing name.");
                    continue;
                }

                System.out.println("Enter the new grade for " + studentName + ":");
                int newGrade = Integer.parseInt(scanner.nextLine().trim());

                // Update the grade for the specific student
                grades.put(studentName, newGrade);
            }

            FileWriter writer = new FileWriter(file, false);
            for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                writer.write(entry.getKey() + " - " + (entry.getValue() != -1 ? entry.getValue() : "No grade available") + "\n");
            }
            writer.close();

            System.out.println("\nUpdated IDs, Names, and Grades:");
            for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                System.out.println(entry.getKey() + " - " + (entry.getValue() != -1 ? entry.getValue() : "No grade available"));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

}  