/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.controllers;

import hvan.qlkh.models.Product;
import hvan.qlkh.models.User;
import hvan.qlkh.utils.ClassTableModel;
import hvan.qlkh.utils.Table;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class HomeController {

    private JPanel pagesHome;
    private ClassTableModel classTableModel = null;
    private final String[] PRODUCTS_COLUMNS = {"Mã số", "Tên sản phẩm", "Danh mục",
        "Số lượng", "Đơn giá", "Ngày hết hạn", "Nhà sản xuất"};
    private final String[] USERS_COLUMNS = {"STT", "Tên tài khoản", "Ngày tạo", "Đọc", "Ghi"};

    public HomeController(JPanel pagesHome) {
        this.pagesHome = pagesHome;
        this.classTableModel = new ClassTableModel();
    }

    public void setProductsTable(Table table, List<Product> products) {
        DefaultTableModel model = classTableModel.setProductsTableModel(products, PRODUCTS_COLUMNS);
        table.setModel(model);
        table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 35));
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.setFont(new Font(pagesHome.getFont().getFontName(), Font.PLAIN, 15));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object o, boolean selected, boolean bln1, int i, int i1) {
                Component com = super.getTableCellRendererComponent(table, o, selected, bln1, i, i1);
                com.setBackground(new Color(246,251,249));
                setBorder(noFocusBorder);
                if (selected) {
                    com.setForeground(new Color(143, 148, 251));
                } else {
                    com.setForeground(new Color(102, 102, 102));
                }
                return com;
            }
        };
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        table.validate();
        table.repaint();
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        table.fixTable(scroll);
        pagesHome.removeAll();
        pagesHome.setLayout(new CardLayout());
        pagesHome.add(scroll);
        pagesHome.validate();
        pagesHome.repaint();
    }

    public void setUsersTable(Table table, List<User> users) {
        DefaultTableModel model = classTableModel.setUsersTableModel(users, USERS_COLUMNS);
        table.setModel(model);
        table.getTableHeader().setPreferredSize(new Dimension(730, 35));
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(3).setPreferredWidth(20);
        table.getColumnModel().getColumn(4).setPreferredWidth(20);
        table.setFont(new Font(pagesHome.getFont().getFontName(), Font.PLAIN, 15));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object o, boolean selected, boolean bln1, int i, int i1) {
                Component com = super.getTableCellRendererComponent(table, o, selected, bln1, i, i1);
                com.setBackground(new Color(246,251,249));
                setBorder(noFocusBorder);
                if (selected) {
                    com.setForeground(new Color(143, 148, 251));
                } else {
                    com.setForeground(new Color(102, 102, 102));
                }
                return com;
            }
        };
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.validate();
        table.repaint();
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        table.fixTable(scroll);
        pagesHome.removeAll();
        pagesHome.setLayout(new CardLayout());
        pagesHome.add(scroll);
        pagesHome.validate();
        pagesHome.repaint();
    }

}