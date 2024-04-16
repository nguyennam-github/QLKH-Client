/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.controllers;

import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class SignUpController {

    private static SignUpController instance;

    private SignUpController() {
    }

    public static synchronized SignUpController getInstance(){
        if(instance == null){
            instance = new SignUpController();
        }
        return instance;
    }

    public void setSignIn(JPanel container, JPanel Sign_in){
        container.add(Sign_in);
    }

}
