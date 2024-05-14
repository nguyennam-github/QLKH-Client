/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.utils;

import hvan.qlkh.models.Product;
import hvan.qlkh.models.User;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class ClassTableModel {

    public DefaultTableModel setProductsTableModel(List<Product> products, String[] columns) {
        Object[][] obj = new Object[products.size()][7];
        int num = products.size();
        Product product;
        @SuppressWarnings("deprecation")
        Locale vn = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(vn);
        for (int i = 0; i < num; i++) {
            product = products.get(i);
            obj[i][0] = product.getId();
            obj[i][1] = product.getName();
            obj[i][2] = product.getCategory();
            obj[i][3] = product.getQuantity();
            obj[i][4] = currencyFormat.format(product.getPrice()).trim();
            String date = new SimpleDateFormat("dd/MM/yyyy").format(product.getExpDate());
            obj[i][5] = date;
            obj[i][6] = product.getManafacturer();
        }
        return new DefaultTableModel(obj, columns);
    }
    
    public DefaultTableModel setUsersTableModel(List<User> users, String[] columns) {
        Object[][] obj = new Object[users.size()][5];
        int num = users.size();
        User user;
        for (int i = 0; i < num; i++) {
            user = users.get(i);
            obj[i][0] = i + 1;
            obj[i][1] = user.getUsername();
            String date = new SimpleDateFormat("dd/MM/yyyy").format(user.getRegister());
            obj[i][2] = date;
            obj[i][3] = user.isRead() ? "x" : "";
            obj[i][4] = user.isWrite() ? "x" : "";
        }
        return new DefaultTableModel(obj, columns);
    }

}
