/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.services.Services;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class App {

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try {
                hvan.qlkh.views.App.main(args);
                Services.getInstance().getUser();
                Services.getInstance().get();
            } catch (Exception ex) {
            }
        });
    }
}
