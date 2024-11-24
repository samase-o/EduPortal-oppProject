/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.project;

import java.util.Scanner;


/**
 *
 * @author WESO
 */
public interface StudentInterface {
    
    void updateProfile(User newUser);

    void seeAllCourses();

    void seeGradesForSpecificCourse();

    void submitFeedback();

}
