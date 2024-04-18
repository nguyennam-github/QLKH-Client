/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.controllers;

import com.toedter.calendar.JDateChooser;
import hvan.qlkh.services.Services;
import hvan.qlkh.utils.TextField;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class UserController {

    private static UserController instance;
    private static JPanel toolbar;
    private static JPanel navbar;
    private static Map<String, Integer> toolbarMap = new HashMap<>();
    private static Map<String, Integer> navbarMap = new HashMap<>();

    public static UserController getInstance(){
        if (instance == null){
            instance = new UserController();
        }
        return instance;
    }

    private UserController() {
    }

    private void setComponentsMap(){
        for (int i = 0; i < toolbar.getComponentCount(); i++) {
            toolbarMap.put(toolbar.getComponent(i).getName(), i);
        }
        for (int i = 0; i < navbar.getComponentCount(); i++) {
            navbarMap.put(navbar.getComponent(i).getName(), i);
        }
    }

    public void initMain(){

        try {

            setComponentsMap();
            navbar.getComponent(navbarMap.get("Navbar__ButtonAuthorization")).setVisible(false);

            if (Services.getInstance().getCurrentUser().getUsername().equals("admin")){
                navbar.getComponent(navbarMap.get("Navbar__ButtonAuthorization")).setVisible(true);
            }

            if (!Services.getInstance().getCurrentUser().isWrite()) {
                for (int i = 0; i < toolbar.getComponentCount(); i++) {
                    if (toolbar.getComponent(i).getClass().equals(TextField.class)){
                        ((TextField)toolbar.getComponent(i)).setEditable(false);
                        ((TextField)toolbar.getComponent(i)).setEnabled(false);
                    }
                    if (toolbar.getComponent(i).getClass().equals(JDateChooser.class)){
                        ((JDateChooser) toolbar.getComponent(i)).setEnabled(false);
                    }
                    if (toolbar.getComponent(i).getClass().equals(JComboBox.class)){
                        ((JComboBox) toolbar.getComponent(i)).setEnabled(false);
                    }
                    if (toolbar.getComponent(i).getClass().equals(JScrollPane.class)){
                        ((JScrollPane)toolbar.getComponent(i)).getViewport().getView().setEnabled(false);
                    }
                    if (toolbar.getComponent(i).getName() != null) {
                        if (toolbar.getComponent(i).getName().equals("Toolbar__ButtonFileChooser")) {
                            toolbar.getComponent(i).setEnabled(false);
                        }
                        if (toolbar.getComponent(i).getName().toLowerCase().contains("search") ||
                                toolbar.getComponent(i).getName().toLowerCase().contains("sort")) {
                            toolbar.getComponent(i).setEnabled(true);
                            if (toolbar.getComponent(i).getClass().equals(TextField.class)){
                                ((TextField)toolbar.getComponent(i)).setEditable(true);
                            }
                        }
                    }
                }
            }
            else{
                for (int i = 0; i < toolbar.getComponentCount(); i++) {
                    if (toolbar.getComponent(i).getClass().equals(TextField.class)){
                        ((TextField)toolbar.getComponent(i)).setEditable(true);
                        ((TextField)toolbar.getComponent(i)).setEnabled(true);
                    }
                    if (toolbar.getComponent(i).getClass().equals(JDateChooser.class)){
                        ((JDateChooser) toolbar.getComponent(i)).setEnabled(true);
                    }
                    if (toolbar.getComponent(i).getClass().equals(JComboBox.class)){
                        ((JComboBox) toolbar.getComponent(i)).setEnabled(true);
                        ((JComboBox) toolbar.getComponent(i)).setBackground(Color.white);
                    }
                    if (toolbar.getComponent(i).getClass().equals(JScrollPane.class)){
                        ((JScrollPane)toolbar.getComponent(i)).getViewport().getView().setEnabled(true);
                    }
                    if (toolbar.getComponent(i).getName() != null &&  (toolbar.getComponent(i).getName().equals("Toolbar__ButtonFileChooser"))) {
                        toolbar.getComponent(i).setEnabled(true);
                    }
                }
            }
        } catch (IOException ex) {
        }
    }

    public static void setComponents(JPanel toolBar, JPanel navBar) {

        UserController.toolbar = toolBar;
        UserController.navbar = navBar;

    }

}
