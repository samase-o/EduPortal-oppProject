/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.project;

/**
 *
 * @author barakt
 */
public interface UserInterface {
    public String login(String email, String password);
    public void register();
    public void logout();
}