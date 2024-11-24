/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.project;

import java.util.Scanner;

/**
 *
 * @author Rasha
 */
public interface AdminInterface {

    public void addStudent();

    public void addInstructor();

    public void updateUser(Scanner scanner);

    public void deleteUser(Scanner scanner);

    public void showAllUsers();
}
