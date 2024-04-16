/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.controllers;

import com.toedter.calendar.JDateChooser;
import hvan.qlkh.services.UserServices;
import hvan.qlkh.utils.TextField;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author PC
 */
public class MainController {
    
    private static MainController instance;
    private static JPanel Toolbar;
    private static JPanel Navbar;
    private static Map<String, Integer> ToolbarMap = new HashMap<>();
    private static Map<String, Integer> NavbarMap = new HashMap<>();
    
    public static MainController getInstance(){
        if (instance == null){
            instance = new MainController();
        }
        return instance;
    }

    private MainController() {
    }
    
    private void setComponetsMap(){
        for (int i = 0; i < Toolbar.getComponentCount(); i++) {
            ToolbarMap.put(Toolbar.getComponent(i).getName(), i);
        }
        for (int i = 0; i < Navbar.getComponentCount(); i++) {
            NavbarMap.put(Navbar.getComponent(i).getName(), i);
        }
    }
    
    public void initMain(){
        setComponetsMap();
        if (UserServices.getInstance().getCurrent().getUsername().equals("admin")){
            Navbar.getComponent(NavbarMap.get("Navbar__ButtonAuthorization")).setVisible(true);
        }
        else{
            Navbar.getComponent(NavbarMap.get("Navbar__ButtonAuthorization")).setVisible(false);
        }

        if (!UserServices.getInstance().getCurrent().isWrite()) {
            for (int i = 0; i < Toolbar.getComponentCount(); i++) {
                if (Toolbar.getComponent(i).getClass().equals(TextField.class)){
                    ((TextField)Toolbar.getComponent(i)).setEditable(false);
                    ((TextField)Toolbar.getComponent(i)).setEnabled(false);
                }
                if (Toolbar.getComponent(i).getClass().equals(JDateChooser.class)){
                    ((JDateChooser) Toolbar.getComponent(i)).setEnabled(false);
                }
                if (Toolbar.getComponent(i).getClass().equals(JComboBox.class)){
                    ((JComboBox) Toolbar.getComponent(i)).setEnabled(false);
                }
                if (Toolbar.getComponent(i).getClass().equals(JScrollPane.class)){
                    ((JScrollPane)Toolbar.getComponent(i)).getViewport().getView().setEnabled(false);
                }
                if (Toolbar.getComponent(i).getName() != null) {
                    if (Toolbar.getComponent(i).getName().equals("Toolbar__ButtonFileChooser")) {
                        Toolbar.getComponent(i).setEnabled(false);
                    }
                    if (Toolbar.getComponent(i).getName().toLowerCase().contains("search") ||
                        Toolbar.getComponent(i).getName().toLowerCase().contains("sort")) {
                        Toolbar.getComponent(i).setEnabled(true);
                        if (Toolbar.getComponent(i).getClass().equals(TextField.class)){
                            ((TextField)Toolbar.getComponent(i)).setEditable(true);
                        }
                    }
                }
            }
        }
        else{
            for (int i = 0; i < Toolbar.getComponentCount(); i++) {
                if (Toolbar.getComponent(i).getClass().equals(TextField.class)){
                    ((TextField)Toolbar.getComponent(i)).setEditable(true);
                    ((TextField)Toolbar.getComponent(i)).setEnabled(true);
                }
                if (Toolbar.getComponent(i).getClass().equals(JDateChooser.class)){
                    ((JDateChooser) Toolbar.getComponent(i)).setEnabled(true);
                }
                if (Toolbar.getComponent(i).getClass().equals(JComboBox.class)){
                    ((JComboBox) Toolbar.getComponent(i)).setEnabled(true);
                    ((JComboBox) Toolbar.getComponent(i)).setBackground(Color.white);
                }
                if (Toolbar.getComponent(i).getClass().equals(JScrollPane.class)){
                    ((JScrollPane)Toolbar.getComponent(i)).getViewport().getView().setEnabled(true);
                }
                if (Toolbar.getComponent(i).getName() != null) {
                    if (Toolbar.getComponent(i).getName().equals("Toolbar__ButtonFileChooser")) {
                        Toolbar.getComponent(i).setEnabled(true);
                    }
                }
            }
        }
    }

    public static void setComponents(JPanel ToolBar, JPanel NavBar) {
        MainController.Toolbar = ToolBar;
        MainController.Navbar = NavBar;
    }
    
}
