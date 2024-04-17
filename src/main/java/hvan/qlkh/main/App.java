/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.services.Services;
import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;

/**
 *
 * @author PC
 */
public class App {

    public static void main(String[] args) throws IOException, JAXBException, ClassNotFoundException{
        SwingUtilities.invokeLater(() -> {
            hvan.qlkh.views.App.main(args);
            try {
                Services.getInstance().getUser();
                Services.getInstance().get();
            } catch (IOException | JAXBException ex) {
            }
        });
    }
}
