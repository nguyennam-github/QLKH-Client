/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hvan.qlkh.views;

import hvan.qlkh.chart.ModelChart;
import hvan.qlkh.chart.PieChart;
import hvan.qlkh.controllers.Controller;
import hvan.qlkh.models.Product;
import hvan.qlkh.utils.BigDecimalConverter;
import java.awt.Color;
import hvan.qlkh.chart.ModelPieChart;
import hvan.qlkh.controllers.MainController;
import hvan.qlkh.dao.UserDAO;
import hvan.qlkh.models.User;
import hvan.qlkh.services.ProductService;
import hvan.qlkh.services.UserServices;
//import hvan.qlkh.socket.ProductServices;
import hvan.qlkh.utils.Panel;
import hvan.qlkh.utils.ScrollBar;
import hvan.qlkh.utils.Table;
import hvan.qlkh.utils.WrapLayout;
import hvan.qlkh.utils.Comparator;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBException;
/**
 *
 * @author PC
 */
public class Main extends javax.swing.JPanel implements ListSelectionListener{

    /**
     * Creates new form Main
     */

    private static final int PAGES_HOME = 0;
    private static final int PAGES_PRODUCTS = 1;
    private static final int PAGES_STATISTIC = 2;
    private static final int PAGES_AUTHORIZATION = 3;
    private static Main instance;
    private transient Controller appController;
    private transient Controller adminController;
    private Table productsTable = new Table();
    private Table usersTable = new Table();
    private static List<ProductTemplate> productTemplates = new ArrayList<>();
    private static List<ProductTemplate> sortTemplates = new ArrayList<>();
    private static int accessPage;
    private JPanel p = new JPanel();
    private boolean idCheck = true;
    private boolean nameCheck = true;
    private boolean quantityCheck = true;
    private boolean priceCheck = true;
    private boolean dateCheck = true;
    private boolean manafacturerCheck = true;
    private boolean productSearchModify = false;
    private Thread thread;
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private int id;

    public Controller getAppController(){
        return appController;
    }
    
    public Table getProductsTable(){
        return productsTable;
    }
    
    public void initToolbar(){
        Toolbar__DescriptionScroll.setBorder(new LineBorder(new Color(0, 0, 0, 200), 1));
        Toolbar__DescriptionScroll.setVerticalScrollBar(new ScrollBar());
        Toolbar__DescriptionScroll.getVerticalScrollBar().setBackground(Color.WHITE);
        Toolbar__DescriptionScroll.getViewport().setBackground(Color.WHITE);
        Toolbar__ButtonEdit.setEnabled(false);
        Toolbar__ButtonDelete.setEnabled(false);
        Toolbar__ExpiryInput.getJCalendar().setPreferredSize(new Dimension(365, 300));
        Toolbar__ExpiryInput.getJCalendar().getDayChooser().getDayPanel().setBackground(Color.white);
        Toolbar__ExpiryInput.getJCalendar().getDayChooser().getDayPanel().setLayout(new GridLayout(7, 7, -6, 0));
        Toolbar__ExpiryInput.getJCalendar().getDayChooser().getDayPanel().getComponents();
        Toolbar__CategoryInput.setBackground(Color.white);
        Toolbar__SortInput.setBackground(Color.white);
        Toolbar__SearchInput.setBackground(Color.white);
    }

    public void resetToolbar(boolean state){
        Toolbar__IDInput.setText("");
        Toolbar__NameInput.setText("");
        Toolbar__CategoryInput.setSelectedIndex(0);
        Toolbar__QuantityInput.setText("");
        Toolbar__PriceInput.setText("");
        Toolbar__ExpiryInput.setDate(null);
        Toolbar__ManafacturerInput.setText("");
        Toolbar__ThumbnailInput.setText("");
        Toolbar__DescriptionInput.setText("");
        Toolbar__Alert.setText("");
        Toolbar__ButtonAdd.setEnabled(state);
        Toolbar__ButtonEdit.setEnabled(false);
        Toolbar__ButtonDelete.setEnabled(false);
        Toolbar__ButtonReset.setEnabled(state);
        ProductService.setSelectedProduct(null);
        Toolbar.repaint();
        Toolbar.revalidate();
    }

    public void initSearchField(){
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Mã số sản phẩm")){
            Toolbar__SearchStringInput.setVisible(true);
            Toolbar__SearchMinInput.setText("");
            Toolbar__SearchMinInput.setVisible(false);
            Toolbar__SearchMaxInput.setText("");
            Toolbar__SearchMaxInput.setVisible(false);
            Toolbar__Decorate.setVisible(false);
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Tên sản phẩm")){
            Toolbar__SearchStringInput.setVisible(true);
            Toolbar__SearchMinInput.setText("");
            Toolbar__SearchMinInput.setVisible(false);
            Toolbar__SearchMaxInput.setText("");
            Toolbar__SearchMaxInput.setVisible(false);
            Toolbar__Decorate.setVisible(false);
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Danh mục")){
            Toolbar__SearchStringInput.setVisible(true);
            Toolbar__SearchMinInput.setText("");
            Toolbar__SearchMinInput.setVisible(false);
            Toolbar__SearchMaxInput.setText("");
            Toolbar__SearchMaxInput.setVisible(false);
            Toolbar__Decorate.setVisible(false);
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Nhà sản xuất")){
            Toolbar__SearchStringInput.setVisible(true);
            Toolbar__SearchMinInput.setText("");
            Toolbar__SearchMinInput.setVisible(false);
            Toolbar__SearchMaxInput.setText("");
            Toolbar__SearchMaxInput.setVisible(false);
            Toolbar__Decorate.setVisible(false);
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Đơn giá")){
            Toolbar__SearchStringInput.setText("");
            Toolbar__SearchStringInput.setVisible(false);
            Toolbar__SearchMinInput.setVisible(true);
            Toolbar__SearchMaxInput.setVisible(true);
            Toolbar__Decorate.setVisible(true);
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Hạn sử dụng")){
            Toolbar__SearchStringInput.setText("");
            Toolbar__SearchStringInput.setVisible(false);
            Toolbar__SearchMinInput.setVisible(true);
            Toolbar__SearchMaxInput.setVisible(true);
            Toolbar__Decorate.setVisible(true);
        }
    }

    private void resetSearchField(boolean state){
        Toolbar__SearchStringInput.setText("");
        Toolbar__SearchMinInput.setText("");
        Toolbar__SearchMaxInput.setText("");
        Toolbar__SortInput.setSelectedIndex(0);
        Toolbar__SearchInput.setSelectedIndex(0);
        Toolbar__ButtonSort.setEnabled(state);
        Toolbar__ButtonSearch.setEnabled(state);
        Toolbar__ButtonResetSearch.setEnabled(state);
    }

    public void initWarehouse(){
        Products__Main.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        Products__Scroll.setBorder(null);
        Products__Scroll.setVerticalScrollBar(new ScrollBar());
        Products__Scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        Products__Scroll.getViewport().setBackground(Color.WHITE);
        Products__Scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
    }
    
    public void setProductTemplates(List<Product> products){
        Products__Main.removeAll();
        productTemplates.clear();
        products.forEach(product -> {
            ProductTemplate temp = new ProductTemplate(product);
            productTemplates.add(temp);
            Products__Main.add(temp);
        });
        Products__Main.repaint();
        Products__Main.revalidate();
    }
    
    private void resetWarehouse(){
        productTemplates.forEach(pt ->  pt.setVisible(true));
    }

    public void initSort(){
        Sort__Main.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        Sort__Scroll.setBorder(null);
        Sort__Scroll.setVerticalScrollBar(new ScrollBar());
        Sort__Scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        Sort__Scroll.getViewport().setBackground(Color.WHITE);
        Sort__Scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        try {
            ProductService.getInstance().sort(new Comparator.IDComparator()).forEach(product -> {
                ProductTemplate temp = new ProductTemplate(product);
                sortTemplates.add(temp);
                Sort__Main.add(temp);
            });
        } catch (IOException e) {
        }
    }

    private void setBarChart(String lengend, List<ModelChart> modelCharts){
        Statistic__BarChart.addLegend(lengend, new Color(245, 189, 135));
        modelCharts.forEach(mc -> Statistic__BarChart.addData(mc));
    }

    private void initBarChart(){
        Statistic__BarChart.clearData();
        if (Statistic__QuantityStatistic.getSelectedItem().toString().equals("Hạn sử dụng")){
            Map<Integer, Integer> expStatistic = new HashMap<>();
            try {
                ProductService.getInstance().getProducts().forEach(product -> {
                    int key = product.getExpDate().getYear() + 1900;
                    if (expStatistic.containsKey(key)){
                        expStatistic.replace(key, expStatistic.get(key) + product.getQuantity());
                    }
                    else{
                        expStatistic.put(key, product.getQuantity());
                    }
                });
            } catch (IOException e) {
            }
            
            List<ModelChart> modelCharts = new ArrayList<>();
            for (Entry<Integer, Integer> entry : expStatistic.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                modelCharts.add(new ModelChart(key + "", new double[]{value}));
            }
            setBarChart("Hạn sử dụng", modelCharts);
            Statistic__BarChart.repaint();
            Statistic__BarChart.revalidate();
        }
        if (Statistic__QuantityStatistic.getSelectedItem().toString().equals("Danh mục")){
            Map<String, Integer> categoryStatistic = new HashMap<>();
            try {
                ProductService.getInstance().getProducts().forEach(product -> {
                    String key = product.getCategory();
                    if (categoryStatistic.containsKey(key)){
                        categoryStatistic.replace(key, categoryStatistic.get(key) + product.getQuantity());
                    }
                    else{
                        categoryStatistic.put(key, product.getQuantity());
                    }
                });
            } catch (IOException ex ){
            }
            List<ModelChart> modelCharts = new ArrayList<>();
            for (Entry<String, Integer> entry : categoryStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                modelCharts.add(new ModelChart(key, new double[]{value}));
            }
            setBarChart("Danh mục", modelCharts);
            Statistic__BarChart.repaint();
            Statistic__BarChart.revalidate();
        }
        if (Statistic__QuantityStatistic.getSelectedItem().toString().equals("Nhà sản xuất")){
            Map<String, Integer> manafacturerStatistic = new HashMap<>();
            try {
                ProductService.getInstance().getProducts().forEach(product -> {
                    String key = product.getManafacturer();
                    if (manafacturerStatistic.containsKey(key)){
                        manafacturerStatistic.replace(key, manafacturerStatistic.get(key) + product.getQuantity());
                    }
                    else{
                        manafacturerStatistic.put(key, product.getQuantity());
                    }
                });
            } catch (IOException e) {
            }
            List<ModelChart> modelCharts = new ArrayList<>();
            for (Entry<String, Integer> entry : manafacturerStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                modelCharts.add(new ModelChart(key, new double[]{value}));
            }
            setBarChart("Nhà sản xuất", modelCharts);
            Statistic__BarChart.repaint();
            Statistic__BarChart.revalidate();
        }
    }
    private static final Color [] CHART_COLOR = {new Color(23, 126, 238),
                                                new Color(47, 157, 64),
                                                new Color(221, 65, 65),
                                                new Color(196, 151, 58),
                                                new Color(204, 93, 232),
                                                new Color(32, 201, 151),
                                                new Color(245, 83, 118),
                                                new Color(235, 121, 0)};
    private static final String [] PRICE = {"dưới 1tr",
                                            "1tr - 5tr",
                                            "5tr - 10tr",
                                            "10tr - 20tr",
                                            "trên 20tr"};
    private void initPieChart(){
        Statistic__PieChart.clearData();
        Statistic__PieChart.setChartType(PieChart.PeiChartType.DONUT_CHART);
        if (Statistic__PercentStatistic.getSelectedItem().toString().equals("Danh mục")) {
            Map<String, Integer> categoryStatistic = new HashMap<>();
            for (int i = 0; i < Toolbar__CategoryInput.getItemCount(); i++) {
                String key = Toolbar__CategoryInput.getItemAt(i);
                int value = 0;
                try {
                    value = ProductService.getInstance().filterByCategory(key).size();
                } catch (IOException  e) {
                }
                categoryStatistic.put(key, value);
            }
            int ci = 0;
            for (Entry<String, Integer> entry : categoryStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                Statistic__PieChart.addData(new ModelPieChart(key, value, CHART_COLOR[ci]));
                ci++;
            }
            Statistic__PieChart.repaint();
            Statistic__PieChart.revalidate();
        }
        if (Statistic__PercentStatistic.getSelectedItem().toString().equals("Đơn giá")) {
            Map<String, Integer> priceStatistic = new HashMap<>();
            try {
                priceStatistic.put(PRICE[0], ProductService.getInstance().filterByPrice(new BigDecimal("0"), new BigDecimal("1000000")).size());
                priceStatistic.put(PRICE[1], ProductService.getInstance().filterByPrice(new BigDecimal("1000000"), new BigDecimal("5000000")).size());
                priceStatistic.put(PRICE[2], ProductService.getInstance().filterByPrice(new BigDecimal("5000000"), new BigDecimal("10000000")).size());
                priceStatistic.put(PRICE[3], ProductService.getInstance().filterByPrice(new BigDecimal("10000000"), new BigDecimal("20000000")).size());
                priceStatistic.put(PRICE[4], ProductService.getInstance().filterByPrice(new BigDecimal("20000000"), new BigDecimal("-12345")).size());
            } catch (IOException e) {
            }
            int ci = 0;
            for (Entry<String, Integer> entry : priceStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                Statistic__PieChart.addData(new ModelPieChart(key, value, CHART_COLOR[ci]));
                ci++;
            }
            Statistic__PieChart.repaint();
            Statistic__PieChart.revalidate();
        }
    }

    private void initOveral(){
        int totalProducts = 0;
        try {
            totalProducts = ProductService.getInstance().getProducts().size();
        } catch (IOException e) {
        }
        int totalQuantity = 0;
        int totalCategory = Toolbar__CategoryInput.getItemCount();
        Set<String> manafacturer = new HashSet<>();
        try {
            for (Product product: ProductService.getInstance().getProducts()){
                totalQuantity += product.getQuantity();
                manafacturer.add(product.getManafacturer());
            }
        } catch (IOException  e) {
        }
        int totalManafaturer = manafacturer.size();
        Statistic__TotalProductsStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">" + totalProducts +"</div></html>");
        Statistic__TotalQuantityStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">" + totalQuantity +"</div></html>");
        Statistic__CategoryStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">" + totalCategory +"</div></html>");
        Statistic__ManafacturerStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">" + totalManafaturer +"</div></html>");
    }

    public void initStatistic(){
        Statistic__QuantityStatistic.setSelectedIndex(0);
        Statistic__PercentStatistic.setSelectedIndex(0);
        Statistic__QuantityStatistic.setBackground(Color.WHITE);
        Statistic__PercentStatistic.setBackground(Color.WHITE); 
        initOveral();
        initBarChart();
        initPieChart();
    }

    private void resetUserEdit(){
        Information__UsernameInput.setText("");
        Information__RegisterInput.setText("");
        Information__WriteInput.setSelected(false);
        Information__ReadInput.setSelected(false);
        Information__ButtonEdit.setEnabled(false);
        Information__ButtonDelete.setEnabled(false);
        usersTable.clearSelection();
    }

    private void resetUserFilter(){
        Filter__UsernameInput.setText("");
        Filter__DateMin.setText("");
        Filter__DateMax.setText("");
        Filter__WriteInput.setSelected(false);
        Filter__ReadInput.setSelected(false);
        usersTable.clearSelection();
    }

    private void setSort(List<Product> products){
        Sort__Main.removeAll();
        products.forEach(product -> Sort__Main.add(new ProductTemplate(product)));
        Sort__Main.validate();
        Sort__Main.repaint();
        CardLayout c = (CardLayout) Main__Pages.getLayout();
        c.show(Main__Pages, "Pages__Sort");
        this.repaint();
        this.revalidate();
    }
    

    private boolean isUserModify(){
        boolean temp = false;
        if (UserServices.getInstance().getSelectedUser() != null){
            User user = UserServices.getInstance().getSelectedUser();
            temp = !(Information__ReadInput.isSelected() == user.isRead() &&
                    Information__WriteInput.isSelected() == user.isWrite());
        }
        return temp;
    }

    private boolean isModify(){
        boolean temp = false;
        try {
            if (ProductService.getInstance().getSelectedProduct() != null){
                Product product = ProductService.getInstance().getSelectedProduct();
                if (Toolbar__ExpiryInput.getDate() != null){
                    temp = !(Toolbar__IDInput.getText().equals(product.getId())&&
                            Toolbar__NameInput.getText().equals(product.getName())&&
                            ((String)Toolbar__CategoryInput.getSelectedItem()).equals(product.getCategory())&&
                            (Integer.parseInt(Toolbar__QuantityInput.getText()) == product.getQuantity())&&
                            new BigDecimal(Toolbar__PriceInput.getText()).equals(product.getPrice())&&
                            Toolbar__ExpiryInput.getDate().equals(product.getExpDate())&&
                            Toolbar__ManafacturerInput.getText().equals(product.getManafacturer())&&
                            Toolbar__ThumbnailInput.getText().equals(product.getThumbnail())&&
                            Toolbar__DescriptionInput.getText().equals(product.getDescription()));
                }
                else{
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Hạn sử dụng sản phẩm không hợp lệ!</div></html>");
                }
        }
        } catch (IOException | NumberFormatException  e) {
        }
        return temp;
    }

    private void filter(List<Product> products){
            productTemplates.forEach(pt -> {
                if (products.contains(pt.getProduct())){
                    pt.setVisible(true);
                }
                else{
                    pt.setVisible(false);
                }
            });
    }
    
    private Main(){
        initComponents();
        accessPage = PAGES_HOME;
        Navbar__ButtonAuthorization.setName("Navbar__ButtonAuthorization");
        Toolbar__ButtonFileChooser.setName("Toolbar__ButtonFileChooser");
        Toolbar__SearchStringInput.setName("Toolbar__SearchStringInput");
        Toolbar__SearchInput.setName("Toolbar__SearchInput");
        Toolbar__SearchMinInput.setName("Toolbar__SearchMinInput");
        Toolbar__SearchMaxInput.setName("Toolbar__SearchMaxInput");
        Toolbar__SortInput.setName("Toolbar__SortInput");
        p.setBackground(new Color(246,251,249,0));
        initToolbar();
        MainController.getInstance();
        MainController.setComponents(Toolbar, Navbar);
        initSearchField();
        initWarehouse();
        Statistic__Scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        Statistic__Scroll.setBorder(new LineBorder(new Color(246,251,249), 1));
        Statistic__Scroll.setVerticalScrollBar(new ScrollBar());
        Statistic__Scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        Statistic__Scroll.getViewport().setBackground(Color.WHITE);
        Statistic__Scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        Statistic__QuantityStatistic.setBackground(Color.WHITE);
        Statistic__PercentStatistic.setBackground(Color.WHITE); 
        this.appController = new Controller(Home__Table);
        this.adminController = new Controller(jPanel1);
        addProductSelectionListener(new ProductSelectionListener());
        addUserSelectionListener(new UserSelectionListener());
    }

    public static synchronized Main getInstance(){
        if(instance == null){
            instance = new Main();
        }
        return instance;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Toolbar = new javax.swing.JPanel();
        Toolbar__NameTitle = new javax.swing.JLabel();
        Toolbar__NameInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__CategoryTitle = new javax.swing.JLabel();
        Toolbar__CategoryInput = new javax.swing.JComboBox<>();
        Toolbar__QuantityTitle = new javax.swing.JLabel();
        Toolbar__QuantityInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__PriceTitle = new javax.swing.JLabel();
        Toolbar__PriceInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__ExpiryTitle = new javax.swing.JLabel();
        Toolbar__ExpiryInput = new com.toedter.calendar.JDateChooser();
        Toolbar__ManafacturerTitle = new javax.swing.JLabel();
        Toolbar__ManafacturerInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__ButtonAdd = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__ButtonEdit = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__ButtonDelete = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__Title = new javax.swing.JLabel();
        Toolbar__ButtonReset = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__SortTitle = new javax.swing.JLabel();
        Toolbar__SortInput = new javax.swing.JComboBox<>();
        Toolbar__ButtonSort = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__SearchTitle = new javax.swing.JLabel();
        Toolbar__SearchInput = new javax.swing.JComboBox<>();
        Toolbar__SearchStringInput = new hvan.qlkh.utils.TextField(0, new Color(0, 0, 0, 200));
        Toolbar__ButtonSearch = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__SeparatorBottom = new javax.swing.JPanel();
        Toolbar__SeparatorTop = new javax.swing.JPanel();
        Toolbar__SearchMinInput = new hvan.qlkh.utils.TextField(0, new Color(0, 0, 0, 200));
        Toolbar__SearchMaxInput = new hvan.qlkh.utils.TextField(0, new Color(0, 0, 0, 200));
        Toolbar__Decorate = new javax.swing.JLabel();
        Toolbar__Alert = new javax.swing.JLabel();
        Toolbar__ButtonFileChooser = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__ThumbnailInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__ThumbnailTitle = new javax.swing.JLabel();
        Toolbar__DescriptionTitle = new javax.swing.JLabel();
        Toolbar__DescriptionScroll = new javax.swing.JScrollPane();
        Toolbar__DescriptionInput = new javax.swing.JTextPane();
        Toolbar__IDTitle = new javax.swing.JLabel();
        Toolbar__IDInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__ButtonResetSearch = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Main = new javax.swing.JPanel();
        Main__Pages = new Panel(40);
        Pages__Home = new Panel(40);
        Home__Table = new Panel(40);
        Pages__Products = new Panel(40);
        Products__Scroll = new javax.swing.JScrollPane();
        Products__Main = new javax.swing.JPanel();
        Pages__Statistic = new Panel(40);
        Statistic__Scroll = new javax.swing.JScrollPane();
        Statistic__Main = new javax.swing.JPanel();
        Statistic__BarChart = new hvan.qlkh.chart.Chart();
        Statistic__PieChart = new hvan.qlkh.chart.PieChart();
        Statistic__Menu = new hvan.qlkh.utils.Panel(40);
        Statistic__QuantityStatistic = new javax.swing.JComboBox<>();
        Statistic__QuantityStatisticTitle = new javax.swing.JLabel();
        Statistic__PercentStatisticTitle = new javax.swing.JLabel();
        Statistic__PercentStatistic = new javax.swing.JComboBox<>();
        Statistic__ButtonPercent = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Statistic__ButtonPercent1 = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Statistic__Category = new hvan.qlkh.utils.Panel(40);
        Statistic__CategoryThumb = new javax.swing.JLabel();
        Statistic__CategoryTitle = new javax.swing.JLabel();
        Statistic__CategoryStatistic = new javax.swing.JLabel();
        Statistic__Manafacturer = new hvan.qlkh.utils.Panel(40);
        Statistic__ManafacturerThumb = new javax.swing.JLabel();
        Statistic__ManafacturerTitle = new javax.swing.JLabel();
        Statistic__ManafacturerStatistic = new javax.swing.JLabel();
        Statistic__TotalProducts = new hvan.qlkh.utils.Panel(40);
        Statistic__TotalProductsThumb = new javax.swing.JLabel();
        Statistic__TotalProductsTitle = new javax.swing.JLabel();
        Statistic__TotalProductsStatistic = new javax.swing.JLabel();
        Statistic__TotalQuanity = new hvan.qlkh.utils.Panel(40);
        Statistic__TotalQuantityThumb = new javax.swing.JLabel();
        Statistic__TotalQuantityTitle = new javax.swing.JLabel();
        Statistic__TotalQuantityStatistic = new javax.swing.JLabel();
        Pages__Sort = new Panel(40);
        Sort__Scroll = new javax.swing.JScrollPane();
        Sort__Main = new javax.swing.JPanel();
        Pages__Authorization = new Panel(40);
        jPanel1 = new Panel(40);
        jPanel2 = new Panel(40);
        Information__UsernameInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__NameTitle1 = new javax.swing.JLabel();
        Toolbar__NameTitle2 = new javax.swing.JLabel();
        Information__RegisterInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__NameTitle3 = new javax.swing.JLabel();
        Information__ReadInput = new javax.swing.JCheckBox();
        Toolbar__NameTitle4 = new javax.swing.JLabel();
        Information__WriteInput = new javax.swing.JCheckBox();
        Information__ButtonDelete = new hvan.qlkh.utils.Button(35, new Color(255, 86, 86), new Color(195, 52, 52), new Color(149, 39, 39));
        Information__ButtonEdit = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Information__ButtonReset = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Toolbar__NameTitle11 = new javax.swing.JLabel();
        jPanel3 = new Panel(40);
        Toolbar__NameTitle5 = new javax.swing.JLabel();
        Toolbar__NameTitle6 = new javax.swing.JLabel();
        Filter__ButtonFilter = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Filter__UsernameInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Filter__DateMin = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Filter__DateMax = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        Toolbar__NameTitle7 = new javax.swing.JLabel();
        Toolbar__NameTitle8 = new javax.swing.JLabel();
        Filter__ReadInput = new javax.swing.JCheckBox();
        Toolbar__NameTitle9 = new javax.swing.JLabel();
        Filter__WriteInput = new javax.swing.JCheckBox();
        Toolbar__NameTitle10 = new javax.swing.JLabel();
        Navbar__ButtonAuthorization3 = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Navbar = new hvan.qlkh.utils.Panel(40);
        Navbar__ButtonHome = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Navbar__ButtonProducts = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Navbar__ButtonStatistic = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        Navbar__ButtonSignOut = new hvan.qlkh.utils.Button(35, new Color(255, 86, 86), new Color(195, 52, 52), new Color(149, 39, 39));
        Navbar__ButtonAuthorization = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));

        setBackground(new Color(76, 175, 79, 200));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        Toolbar.setBackground(new java.awt.Color(244, 244, 244));
        Toolbar.setPreferredSize(new java.awt.Dimension(380, 800));

        Toolbar__NameTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tên sản phẩm</div></html>");
        Toolbar__NameTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__NameInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__NameInput.setMinimumSize(new java.awt.Dimension(26, 26));
        Toolbar__NameInput.setPreferredSize(new java.awt.Dimension(230, 35));
        Toolbar__NameInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                Toolbar__NameInputCaretUpdate(evt);
            }
        });

        Toolbar__CategoryTitle.setText("<html><div style=\"width: 60px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Danh mục</div></html>");
        Toolbar__CategoryTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__CategoryInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__CategoryInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Điện thoại", "Máy tính", "Thực Phẩm", "Mỹ Phẩm", "Đồ Chơi", "Thời trang Nam", "Thời trang Nữ" }));
        Toolbar__CategoryInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Toolbar__CategoryInput.setPreferredSize(new java.awt.Dimension(230, 35));

        Toolbar__QuantityTitle.setText("<html><div style=\"width: 60px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 56px; word-wrap: break-word\">Số lượng</div></html>");
        Toolbar__QuantityTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__QuantityInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__QuantityInput.setPreferredSize(new java.awt.Dimension(230, 35));
        Toolbar__QuantityInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                Toolbar__QuantityInputCaretUpdate(evt);
            }
        });

        Toolbar__PriceTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá</div></html>");
        Toolbar__PriceTitle.setMinimumSize(new java.awt.Dimension(105, 25));
        Toolbar__PriceTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__PriceInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__PriceInput.setPreferredSize(new java.awt.Dimension(230, 35));
        Toolbar__PriceInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                Toolbar__PriceInputCaretUpdate(evt);
            }
        });

        Toolbar__ExpiryTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Hạn sử dụng</div></html>");
        Toolbar__ExpiryTitle.setMinimumSize(new java.awt.Dimension(105, 20));
        Toolbar__ExpiryTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__ExpiryInput.setBackground(new java.awt.Color(255, 255, 255));
        Toolbar__ExpiryInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Toolbar__ExpiryInput.setOpaque(false);
        Toolbar__ExpiryInput.setPreferredSize(new java.awt.Dimension(230, 35));

        Toolbar__ManafacturerTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Nhà sản xuất</div></html>");
        Toolbar__ManafacturerTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__ManafacturerInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__ManafacturerInput.setPreferredSize(new java.awt.Dimension(230, 35));
        Toolbar__ManafacturerInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                Toolbar__ManafacturerInputCaretUpdate(evt);
            }
        });

        Toolbar__ButtonAdd.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonAdd.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Thêm</div></html>");
        Toolbar__ButtonAdd.setBorder(null);
        Toolbar__ButtonAdd.setBorderPainted(false);
        Toolbar__ButtonAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonAddMouseClicked(evt);
            }
        });

        Toolbar__ButtonEdit.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonEdit.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Sửa</div></html>");
        Toolbar__ButtonEdit.setBorder(null);
        Toolbar__ButtonEdit.setBorderPainted(false);
        Toolbar__ButtonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonEditMouseClicked(evt);
            }
        });

        Toolbar__ButtonDelete.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonDelete.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Xóa</div></html>");
        Toolbar__ButtonDelete.setBorder(null);
        Toolbar__ButtonDelete.setBorderPainted(false);
        Toolbar__ButtonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonDeleteMouseClicked(evt);
            }
        });

        Toolbar__Title.setText("<html><div style=\"width: 290px; text-align: center; color: rgba(76, 175, 79, 0.8) ; font-size: 20px; font-family: Karla; font-weight: 500; line-height: 20px; word-wrap: break-word\">Quản lý kho hàng</div></html>");
        Toolbar__Title.setPreferredSize(new java.awt.Dimension(380, 35));

        Toolbar__ButtonReset.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonReset.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        Toolbar__ButtonReset.setBorder(null);
        Toolbar__ButtonReset.setBorderPainted(false);
        Toolbar__ButtonReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonResetMouseClicked(evt);
            }
        });

        Toolbar__SortTitle.setText("<html><div style=\"width: 85px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Sắp xếp theo:</div></html>");
        Toolbar__SortTitle.setPreferredSize(new java.awt.Dimension(340, 30));

        Toolbar__SortInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        Toolbar__SortInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định", "Mã số sản phẩm", "Tên sản phẩm", "Số lượng tăng", "Số lượng giảm", "Đơn giá tăng", "Đơn giá giảm", "Hạn sử dụng" }));
        Toolbar__SortInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Toolbar__SortInput.setPreferredSize(new java.awt.Dimension(230, 35));

        Toolbar__ButtonSort.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonSort.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Áp dụng</div></html>");
        Toolbar__ButtonSort.setBorder(null);
        Toolbar__ButtonSort.setBorderPainted(false);
        Toolbar__ButtonSort.setPreferredSize(new java.awt.Dimension(100, 35));
        Toolbar__ButtonSort.setRolloverEnabled(false);
        Toolbar__ButtonSort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonSortMouseClicked(evt);
            }
        });

        Toolbar__SearchTitle.setText("<html><div style=\"width: 85px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tìm kiếm theo:</div></html>");
        Toolbar__SearchTitle.setPreferredSize(new java.awt.Dimension(100, 35));

        Toolbar__SearchInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        Toolbar__SearchInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã số sản phẩm", "Tên sản phẩm", "Danh mục", "Đơn giá", "Hạn sử dụng", "Nhà sản xuất" }));
        Toolbar__SearchInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Toolbar__SearchInput.setPreferredSize(new java.awt.Dimension(230, 35));
        Toolbar__SearchInput.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Toolbar__SearchInputItemStateChanged(evt);
            }
        });

        Toolbar__SearchStringInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        Toolbar__SearchStringInput.setPreferredSize(new java.awt.Dimension(340, 35));
        Toolbar__SearchStringInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Toolbar__SearchStringInputFocusGained(evt);
            }
        });
        Toolbar__SearchStringInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Toolbar__SearchStringInputKeyPressed(evt);
            }
        });

        Toolbar__ButtonSearch.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonSearch.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Tìm kiếm</div></html>");
        Toolbar__ButtonSearch.setBorder(null);
        Toolbar__ButtonSearch.setBorderPainted(false);
        Toolbar__ButtonSearch.setPreferredSize(new java.awt.Dimension(100, 35));
        Toolbar__ButtonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonSearchMouseClicked(evt);
            }
        });

        Toolbar__SeparatorBottom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Toolbar__SeparatorBottom.setPreferredSize(new java.awt.Dimension(380, 1));

        javax.swing.GroupLayout Toolbar__SeparatorBottomLayout = new javax.swing.GroupLayout(Toolbar__SeparatorBottom);
        Toolbar__SeparatorBottom.setLayout(Toolbar__SeparatorBottomLayout);
        Toolbar__SeparatorBottomLayout.setHorizontalGroup(
            Toolbar__SeparatorBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Toolbar__SeparatorBottomLayout.setVerticalGroup(
            Toolbar__SeparatorBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Toolbar__SeparatorTop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Toolbar__SeparatorTop.setPreferredSize(new java.awt.Dimension(380, 1));

        javax.swing.GroupLayout Toolbar__SeparatorTopLayout = new javax.swing.GroupLayout(Toolbar__SeparatorTop);
        Toolbar__SeparatorTop.setLayout(Toolbar__SeparatorTopLayout);
        Toolbar__SeparatorTopLayout.setHorizontalGroup(
            Toolbar__SeparatorTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Toolbar__SeparatorTopLayout.setVerticalGroup(
            Toolbar__SeparatorTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Toolbar__SearchMinInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        Toolbar__SearchMinInput.setPreferredSize(new java.awt.Dimension(135, 35));
        Toolbar__SearchMinInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Toolbar__SearchMinInputFocusGained(evt);
            }
        });
        Toolbar__SearchMinInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Toolbar__SearchMinInputKeyPressed(evt);
            }
        });

        Toolbar__SearchMaxInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        Toolbar__SearchMaxInput.setPreferredSize(new java.awt.Dimension(135, 35));
        Toolbar__SearchMaxInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Toolbar__SearchMaxInputFocusGained(evt);
            }
        });
        Toolbar__SearchMaxInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Toolbar__SearchMaxInputKeyPressed(evt);
            }
        });

        Toolbar__Decorate.setText("<html><div style=\"width: 85px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 12px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">đến</div></html>");
        Toolbar__Decorate.setPreferredSize(new java.awt.Dimension(30, 35));

        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 354px; color: red; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");

        Toolbar__ButtonFileChooser.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonFileChooser.setText("<html><div style=\"text-align: center; color:white; font-size: 10px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Chọn</div></html>");
        Toolbar__ButtonFileChooser.setBorder(null);
        Toolbar__ButtonFileChooser.setBorderPainted(false);
        Toolbar__ButtonFileChooser.setPreferredSize(new java.awt.Dimension(55, 35));
        Toolbar__ButtonFileChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonFileChooserMouseClicked(evt);
            }
        });

        Toolbar__ThumbnailInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__ThumbnailInput.setPreferredSize(new java.awt.Dimension(170, 35));

        Toolbar__ThumbnailTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Ảnh minh họa</div></html>");
        Toolbar__ThumbnailTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__DescriptionTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Mô tả</div></html>");
        Toolbar__DescriptionTitle.setPreferredSize(new java.awt.Dimension(340, 25));

        Toolbar__DescriptionScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Toolbar__DescriptionInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__DescriptionScroll.setViewportView(Toolbar__DescriptionInput);

        Toolbar__IDTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Mã số</div></html>");
        Toolbar__IDTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        Toolbar__IDInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Toolbar__IDInput.setPreferredSize(new java.awt.Dimension(230, 35));
        Toolbar__IDInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                Toolbar__IDInputCaretUpdate(evt);
            }
        });

        Toolbar__ButtonResetSearch.setBackground(new Color(76, 175, 79, 200));
        Toolbar__ButtonResetSearch.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        Toolbar__ButtonResetSearch.setBorder(null);
        Toolbar__ButtonResetSearch.setBorderPainted(false);
        Toolbar__ButtonResetSearch.setPreferredSize(new java.awt.Dimension(100, 35));
        Toolbar__ButtonResetSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Toolbar__ButtonResetSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ToolbarLayout = new javax.swing.GroupLayout(Toolbar);
        Toolbar.setLayout(ToolbarLayout);
        ToolbarLayout.setHorizontalGroup(
            ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToolbarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ToolbarLayout.createSequentialGroup()
                        .addComponent(Toolbar__DescriptionTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(ToolbarLayout.createSequentialGroup()
                        .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Toolbar__SearchStringInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(ToolbarLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(Toolbar__ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(Toolbar__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(Toolbar__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(Toolbar__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(Toolbar__SortTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(ToolbarLayout.createSequentialGroup()
                                    .addComponent(Toolbar__SortInput, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(Toolbar__ButtonSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ToolbarLayout.createSequentialGroup()
                                            .addComponent(Toolbar__ThumbnailTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(5, 5, 5)
                                            .addComponent(Toolbar__ThumbnailInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(70, 70, 70))
                                        .addComponent(Toolbar__DescriptionScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ToolbarLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(Toolbar__Alert, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(ToolbarLayout.createSequentialGroup()
                                            .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(Toolbar__ExpiryTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(5, 5, 5)
                                            .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(Toolbar__ExpiryInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__ManafacturerInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(ToolbarLayout.createSequentialGroup()
                                            .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(Toolbar__CategoryTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__NameTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__PriceTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__QuantityTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__IDTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(5, 5, 5)
                                            .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(Toolbar__CategoryInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__QuantityInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__IDInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__NameInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Toolbar__PriceInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(ToolbarLayout.createSequentialGroup()
                                            .addComponent(Toolbar__SearchTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(Toolbar__SearchInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(ToolbarLayout.createSequentialGroup()
                                            .addComponent(Toolbar__SearchMinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(20, 20, 20)
                                            .addComponent(Toolbar__Decorate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(20, 20, 20)
                                            .addComponent(Toolbar__SearchMaxInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(Toolbar__ButtonFileChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(10, 10, 10))))
            .addGroup(ToolbarLayout.createSequentialGroup()
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Toolbar__SeparatorBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__SeparatorTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ToolbarLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(Toolbar__ButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Toolbar__ButtonResetSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Toolbar__Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ToolbarLayout.setVerticalGroup(
            ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToolbarLayout.createSequentialGroup()
                .addComponent(Toolbar__Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(Toolbar__SeparatorTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__IDTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__IDInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__NameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__NameTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__CategoryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__CategoryInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Toolbar__QuantityTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__QuantityInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__PriceTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__PriceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Toolbar__ExpiryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ExpiryInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Toolbar__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ManafacturerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__ThumbnailInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ThumbnailTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ButtonFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(Toolbar__DescriptionTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(Toolbar__DescriptionScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Toolbar__Alert, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(Toolbar__SeparatorBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Toolbar__SortTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__ButtonSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__SortInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__SearchTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__SearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(Toolbar__SearchStringInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__SearchMinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__SearchMaxInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__Decorate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(ToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__ButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Toolbar__ButtonResetSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        Toolbar__IDInput.getAccessibleContext().setAccessibleName("Toolbar__IDInput");

        Main.setBackground(new Color(76, 175, 79, 200));
        Main.setPreferredSize(new java.awt.Dimension(800, 800));

        Main__Pages.setBackground(new java.awt.Color(246, 251, 249));
        Main__Pages.setOpaque(false);
        Main__Pages.setPreferredSize(new java.awt.Dimension(770, 675));
        Main__Pages.setLayout(new java.awt.CardLayout());

        Pages__Home.setBackground(new java.awt.Color(246, 251, 249));
        Pages__Home.setPreferredSize(new java.awt.Dimension(770, 690));

        Home__Table.setBackground(new java.awt.Color(246, 251, 249));
        Home__Table.setPreferredSize(new java.awt.Dimension(755, 640));
        Home__Table.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout Pages__HomeLayout = new javax.swing.GroupLayout(Pages__Home);
        Pages__Home.setLayout(Pages__HomeLayout);
        Pages__HomeLayout.setHorizontalGroup(
            Pages__HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__HomeLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Home__Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        Pages__HomeLayout.setVerticalGroup(
            Pages__HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pages__HomeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Home__Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        Main__Pages.add(Pages__Home, "Pages__Home");

        Pages__Products.setBackground(new java.awt.Color(246, 251, 249));
        Pages__Products.setPreferredSize(new java.awt.Dimension(770, 690));

        Products__Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Products__Main.setBackground(new java.awt.Color(246, 251, 249));
        Products__Main.setLayout(new java.awt.GridLayout(5, 0));
        Products__Scroll.setViewportView(Products__Main);

        javax.swing.GroupLayout Pages__ProductsLayout = new javax.swing.GroupLayout(Pages__Products);
        Pages__Products.setLayout(Pages__ProductsLayout);
        Pages__ProductsLayout.setHorizontalGroup(
            Pages__ProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__ProductsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Products__Scroll)
                .addGap(15, 15, 15))
        );
        Pages__ProductsLayout.setVerticalGroup(
            Pages__ProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__ProductsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Products__Scroll)
                .addGap(15, 15, 15))
        );

        Main__Pages.add(Pages__Products, "Pages__Products");

        Pages__Statistic.setBackground(new java.awt.Color(246, 251, 249));
        Pages__Statistic.setPreferredSize(new java.awt.Dimension(770, 690));

        Statistic__Scroll.setBackground(new java.awt.Color(246, 251, 249));
        Statistic__Scroll.setBorder(null);
        Statistic__Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Statistic__Scroll.setPreferredSize(new java.awt.Dimension(730, 690));

        Statistic__Main.setBackground(new java.awt.Color(246, 251, 249));
        Statistic__Main.setPreferredSize(new java.awt.Dimension(715, 650));

        Statistic__BarChart.setPreferredSize(new java.awt.Dimension(600, 320));

        Statistic__PieChart.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        Statistic__PieChart.setPreferredSize(new java.awt.Dimension(325, 325));

        Statistic__Menu.setBackground(new java.awt.Color(255, 255, 255));
        Statistic__Menu.setPreferredSize(new java.awt.Dimension(390, 200));

        Statistic__QuantityStatistic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Danh mục", "Nhà sản xuất", "Hạn sử dụng" }));
        Statistic__QuantityStatistic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Statistic__QuantityStatistic.setPreferredSize(new java.awt.Dimension(225, 35));

        Statistic__QuantityStatisticTitle.setText("<html><div style=\"width: 300px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Thống kê số lượng sản phẩm theo:</div></html>");
        Statistic__QuantityStatisticTitle.setPreferredSize(new java.awt.Dimension(240, 30));

        Statistic__PercentStatisticTitle.setText("<html><div style=\"width: 300px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Thống kê tỉ lệ sản phẩm theo:</div></html>");
        Statistic__PercentStatisticTitle.setPreferredSize(new java.awt.Dimension(240, 30));

        Statistic__PercentStatistic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Danh mục", "Đơn giá" }));
        Statistic__PercentStatistic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Statistic__PercentStatistic.setPreferredSize(new java.awt.Dimension(225, 35));

        Statistic__ButtonPercent.setBackground(new Color(76, 175, 79, 200));
        Statistic__ButtonPercent.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Áp dụng</div></html>");
        Statistic__ButtonPercent.setPreferredSize(new java.awt.Dimension(100, 35));
        Statistic__ButtonPercent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Statistic__ButtonPercentMouseClicked(evt);
            }
        });

        Statistic__ButtonPercent1.setBackground(new Color(76, 175, 79, 200));
        Statistic__ButtonPercent1.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Áp dụng</div></html>");
        Statistic__ButtonPercent1.setPreferredSize(new java.awt.Dimension(100, 35));
        Statistic__ButtonPercent1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Statistic__ButtonPercent1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Statistic__MenuLayout = new javax.swing.GroupLayout(Statistic__Menu);
        Statistic__Menu.setLayout(Statistic__MenuLayout);
        Statistic__MenuLayout.setHorizontalGroup(
            Statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__MenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Statistic__MenuLayout.createSequentialGroup()
                        .addComponent(Statistic__QuantityStatistic, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(Statistic__ButtonPercent1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Statistic__MenuLayout.createSequentialGroup()
                        .addGroup(Statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Statistic__PercentStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Statistic__QuantityStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Statistic__MenuLayout.createSequentialGroup()
                        .addComponent(Statistic__PercentStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(Statistic__ButtonPercent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        Statistic__MenuLayout.setVerticalGroup(
            Statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__MenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Statistic__QuantityStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(Statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Statistic__ButtonPercent1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Statistic__QuantityStatistic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(Statistic__PercentStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(Statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Statistic__ButtonPercent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Statistic__PercentStatistic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        Statistic__Category.setBackground(new java.awt.Color(255, 255, 255));
        Statistic__Category.setPreferredSize(new java.awt.Dimension(100, 140));

        Statistic__CategoryThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-opened-folder-80.png"))); // NOI18N

        Statistic__CategoryTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Tổng số danh mục:</div></html>");

        Statistic__CategoryStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">8</div></html>");

        javax.swing.GroupLayout Statistic__CategoryLayout = new javax.swing.GroupLayout(Statistic__Category);
        Statistic__Category.setLayout(Statistic__CategoryLayout);
        Statistic__CategoryLayout.setHorizontalGroup(
            Statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__CategoryLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(Statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Statistic__CategoryThumb)
                    .addGroup(Statistic__CategoryLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(Statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Statistic__CategoryStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Statistic__CategoryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        Statistic__CategoryLayout.setVerticalGroup(
            Statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__CategoryLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Statistic__CategoryThumb)
                .addGap(5, 5, 5)
                .addComponent(Statistic__CategoryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(Statistic__CategoryStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Statistic__Manafacturer.setBackground(new java.awt.Color(255, 255, 255));
        Statistic__Manafacturer.setPreferredSize(new java.awt.Dimension(100, 140));

        Statistic__ManafacturerThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-manufacture-80.png"))); // NOI18N

        Statistic__ManafacturerTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Tổng số nhà sản xuất:</div></html>");

        Statistic__ManafacturerStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">12</div></html>");

        javax.swing.GroupLayout Statistic__ManafacturerLayout = new javax.swing.GroupLayout(Statistic__Manafacturer);
        Statistic__Manafacturer.setLayout(Statistic__ManafacturerLayout);
        Statistic__ManafacturerLayout.setHorizontalGroup(
            Statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Statistic__ManafacturerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(Statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Statistic__ManafacturerThumb)
                    .addGroup(Statistic__ManafacturerLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(Statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Statistic__ManafacturerStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Statistic__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        Statistic__ManafacturerLayout.setVerticalGroup(
            Statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__ManafacturerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Statistic__ManafacturerThumb)
                .addGap(5, 5, 5)
                .addComponent(Statistic__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(Statistic__ManafacturerStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Statistic__TotalProducts.setBackground(new java.awt.Color(255, 255, 255));
        Statistic__TotalProducts.setPreferredSize(new java.awt.Dimension(185, 100));

        Statistic__TotalProductsThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-product-80.png"))); // NOI18N

        Statistic__TotalProductsTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Tổng số sản phẩm:</div></html>");

        Statistic__TotalProductsStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">35</div></html>");

        javax.swing.GroupLayout Statistic__TotalProductsLayout = new javax.swing.GroupLayout(Statistic__TotalProducts);
        Statistic__TotalProducts.setLayout(Statistic__TotalProductsLayout);
        Statistic__TotalProductsLayout.setHorizontalGroup(
            Statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__TotalProductsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Statistic__TotalProductsThumb)
                .addGroup(Statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Statistic__TotalProductsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(Statistic__TotalProductsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Statistic__TotalProductsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Statistic__TotalProductsStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        Statistic__TotalProductsLayout.setVerticalGroup(
            Statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__TotalProductsLayout.createSequentialGroup()
                .addGroup(Statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Statistic__TotalProductsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(Statistic__TotalProductsThumb))
                    .addGroup(Statistic__TotalProductsLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(Statistic__TotalProductsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Statistic__TotalProductsStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        Statistic__TotalQuanity.setBackground(new java.awt.Color(255, 255, 255));
        Statistic__TotalQuanity.setPreferredSize(new java.awt.Dimension(185, 100));

        Statistic__TotalQuantityThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-bill-80.png"))); // NOI18N

        Statistic__TotalQuantityTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Số lượng sản phẩm:</div></html>");

        Statistic__TotalQuantityStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">1285</div></html>");

        javax.swing.GroupLayout Statistic__TotalQuanityLayout = new javax.swing.GroupLayout(Statistic__TotalQuanity);
        Statistic__TotalQuanity.setLayout(Statistic__TotalQuanityLayout);
        Statistic__TotalQuanityLayout.setHorizontalGroup(
            Statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__TotalQuanityLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Statistic__TotalQuantityThumb)
                .addGap(12, 12, 12)
                .addGroup(Statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Statistic__TotalQuantityTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Statistic__TotalQuantityStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        Statistic__TotalQuanityLayout.setVerticalGroup(
            Statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Statistic__TotalQuanityLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(Statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Statistic__TotalQuantityThumb)
                    .addGroup(Statistic__TotalQuanityLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(Statistic__TotalQuantityTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Statistic__TotalQuantityStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout Statistic__MainLayout = new javax.swing.GroupLayout(Statistic__Main);
        Statistic__Main.setLayout(Statistic__MainLayout);
        Statistic__MainLayout.setHorizontalGroup(
            Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__MainLayout.createSequentialGroup()
                .addGroup(Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Statistic__MainLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(Statistic__PieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(Statistic__MainLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(Statistic__Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Statistic__MainLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Statistic__TotalProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(Statistic__TotalQuanity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(Statistic__MainLayout.createSequentialGroup()
                        .addComponent(Statistic__BarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addGroup(Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Statistic__Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Statistic__Manafacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );
        Statistic__MainLayout.setVerticalGroup(
            Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistic__MainLayout.createSequentialGroup()
                .addGroup(Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Statistic__BarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Statistic__MainLayout.createSequentialGroup()
                        .addComponent(Statistic__Category, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addComponent(Statistic__Manafacturer, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
                .addGroup(Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Statistic__MainLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(Statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Statistic__TotalProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Statistic__TotalQuanity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(Statistic__Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(Statistic__MainLayout.createSequentialGroup()
                        .addComponent(Statistic__PieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(5, 5, 5))))
        );

        Statistic__Scroll.setViewportView(Statistic__Main);

        javax.swing.GroupLayout Pages__StatisticLayout = new javax.swing.GroupLayout(Pages__Statistic);
        Pages__Statistic.setLayout(Pages__StatisticLayout);
        Pages__StatisticLayout.setHorizontalGroup(
            Pages__StatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__StatisticLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Statistic__Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        Pages__StatisticLayout.setVerticalGroup(
            Pages__StatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__StatisticLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Statistic__Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        Main__Pages.add(Pages__Statistic, "Pages__Statistic");

        Pages__Sort.setBackground(new java.awt.Color(246, 251, 249));
        Pages__Sort.setPreferredSize(new java.awt.Dimension(770, 690));

        Sort__Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Sort__Main.setBackground(new java.awt.Color(246, 251, 249));
        Sort__Main.setLayout(new java.awt.GridLayout(5, 0));
        Sort__Scroll.setViewportView(Sort__Main);

        javax.swing.GroupLayout Pages__SortLayout = new javax.swing.GroupLayout(Pages__Sort);
        Pages__Sort.setLayout(Pages__SortLayout);
        Pages__SortLayout.setHorizontalGroup(
            Pages__SortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__SortLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Sort__Scroll)
                .addGap(15, 15, 15))
        );
        Pages__SortLayout.setVerticalGroup(
            Pages__SortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__SortLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Sort__Scroll)
                .addGap(15, 15, 15))
        );

        Main__Pages.add(Pages__Sort, "Pages__Sort");

        Pages__Authorization.setBackground(new java.awt.Color(246, 251, 249));
        Pages__Authorization.setPreferredSize(new java.awt.Dimension(770, 690));

        jPanel1.setBackground(new java.awt.Color(246, 251, 249));
        jPanel1.setPreferredSize(new java.awt.Dimension(480, 650));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(244, 244, 244));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 300));

        Information__UsernameInput.setEditable(false);
        Information__UsernameInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Information__UsernameInput.setPreferredSize(new java.awt.Dimension(150, 35));

        Toolbar__NameTitle1.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tên tài khoản:</div></html>");
        Toolbar__NameTitle1.setPreferredSize(new java.awt.Dimension(100, 35));

        Toolbar__NameTitle2.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Ngày tạo:</div></html>");
        Toolbar__NameTitle2.setPreferredSize(new java.awt.Dimension(80, 35));

        Information__RegisterInput.setEditable(false);
        Information__RegisterInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Information__RegisterInput.setPreferredSize(new java.awt.Dimension(150, 35));

        Toolbar__NameTitle3.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Cho phép đọc:</div></html>");
        Toolbar__NameTitle3.setPreferredSize(new java.awt.Dimension(80, 35));

        Information__ReadInput.setBackground(new java.awt.Color(255, 255, 255));
        Information__ReadInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Information__ReadInput.setPreferredSize(new java.awt.Dimension(20, 35));
        Information__ReadInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Information__ReadInputStateChanged(evt);
            }
        });

        Toolbar__NameTitle4.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Cho phép ghi:</div></html>");
        Toolbar__NameTitle4.setPreferredSize(new java.awt.Dimension(80, 35));

        Information__WriteInput.setBackground(new java.awt.Color(255, 255, 255));
        Information__WriteInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Information__WriteInput.setPreferredSize(new java.awt.Dimension(20, 35));
        Information__WriteInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Information__WriteInputStateChanged(evt);
            }
        });

        Information__ButtonDelete.setBackground(new java.awt.Color(255, 86, 86));
        Information__ButtonDelete.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Xóa</div></html>");
        Information__ButtonDelete.setBorder(null);
        Information__ButtonDelete.setBorderPainted(false);
        Information__ButtonDelete.setPreferredSize(new java.awt.Dimension(70, 35));
        Information__ButtonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Information__ButtonDeleteMouseClicked(evt);
            }
        });

        Information__ButtonEdit.setBackground(new Color(76, 175, 79, 200));
        Information__ButtonEdit.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Sửa</div></html>");
        Information__ButtonEdit.setBorder(null);
        Information__ButtonEdit.setBorderPainted(false);
        Information__ButtonEdit.setPreferredSize(new java.awt.Dimension(70, 35));
        Information__ButtonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Information__ButtonEditMouseClicked(evt);
            }
        });

        Information__ButtonReset.setBackground(new Color(76, 175, 79, 200));
        Information__ButtonReset.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        Information__ButtonReset.setBorder(null);
        Information__ButtonReset.setBorderPainted(false);
        Information__ButtonReset.setPreferredSize(new java.awt.Dimension(70, 35));
        Information__ButtonReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Information__ButtonResetMouseClicked(evt);
            }
        });

        Toolbar__NameTitle11.setText("<html><div style=\"width: 125px; text-align: center; color:rgba(76, 175, 79, 0.8); font-size: 13px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Thông tin tài khoản</div></html>");
        Toolbar__NameTitle11.setPreferredSize(new java.awt.Dimension(160, 35));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(Information__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Information__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Information__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Toolbar__NameTitle4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Information__WriteInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Toolbar__NameTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Information__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Toolbar__NameTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Toolbar__NameTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Information__RegisterInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Information__UsernameInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(Toolbar__NameTitle11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Toolbar__NameTitle11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__NameTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Information__UsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__NameTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Information__RegisterInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__NameTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Information__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Toolbar__NameTitle4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Information__WriteInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Information__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Information__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Information__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel3.setBackground(new java.awt.Color(244, 244, 244));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 255));

        Toolbar__NameTitle5.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tên tài khoản:</div></html>");
        Toolbar__NameTitle5.setPreferredSize(new java.awt.Dimension(100, 35));

        Toolbar__NameTitle6.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Ngày tạo:</div></html>");
        Toolbar__NameTitle6.setPreferredSize(new java.awt.Dimension(100, 35));

        Filter__ButtonFilter.setBackground(new Color(76, 175, 79, 200));
        Filter__ButtonFilter.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Lọc</div></html>");
        Filter__ButtonFilter.setBorder(null);
        Filter__ButtonFilter.setBorderPainted(false);
        Filter__ButtonFilter.setPreferredSize(new java.awt.Dimension(70, 35));
        Filter__ButtonFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Filter__ButtonFilterMouseClicked(evt);
            }
        });

        Filter__UsernameInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Filter__UsernameInput.setPreferredSize(new java.awt.Dimension(150, 35));
        Filter__UsernameInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Filter__UsernameInputFocusGained(evt);
            }
        });
        Filter__UsernameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Filter__UsernameInputKeyPressed(evt);
            }
        });

        Filter__DateMin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Filter__DateMin.setPreferredSize(new java.awt.Dimension(100, 35));
        Filter__DateMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Filter__DateMinFocusGained(evt);
            }
        });
        Filter__DateMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Filter__DateMinKeyPressed(evt);
            }
        });

        Filter__DateMax.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Filter__DateMax.setPreferredSize(new java.awt.Dimension(100, 35));
        Filter__DateMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Filter__DateMaxFocusGained(evt);
            }
        });
        Filter__DateMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Filter__DateMaxKeyPressed(evt);
            }
        });

        Toolbar__NameTitle7.setText("<html><div style=\"width: 30px; text-align: center; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">đến</div></html>");
        Toolbar__NameTitle7.setPreferredSize(new java.awt.Dimension(40, 35));

        Toolbar__NameTitle8.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Được phép đọc:</div></html>");
        Toolbar__NameTitle8.setMinimumSize(new java.awt.Dimension(105, 35));
        Toolbar__NameTitle8.setPreferredSize(new java.awt.Dimension(90, 35));

        Filter__ReadInput.setBackground(new java.awt.Color(255, 255, 255));
        Filter__ReadInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Filter__ReadInput.setPreferredSize(new java.awt.Dimension(20, 35));
        Filter__ReadInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Filter__ReadInputStateChanged(evt);
            }
        });
        Filter__ReadInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Filter__ReadInputKeyPressed(evt);
            }
        });

        Toolbar__NameTitle9.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Được phép ghi:</div></html>");
        Toolbar__NameTitle9.setPreferredSize(new java.awt.Dimension(105, 35));

        Filter__WriteInput.setBackground(new java.awt.Color(255, 255, 255));
        Filter__WriteInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Filter__WriteInput.setPreferredSize(new java.awt.Dimension(20, 35));
        Filter__WriteInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Filter__WriteInputStateChanged(evt);
            }
        });
        Filter__WriteInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Filter__WriteInputKeyPressed(evt);
            }
        });

        Toolbar__NameTitle10.setText("<html><div style=\"width: 90px; text-align: center; color:rgba(76, 175, 79, 0.8); font-size: 13px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Lọc tài khoản</div></html>");
        Toolbar__NameTitle10.setPreferredSize(new java.awt.Dimension(120, 35));

        Navbar__ButtonAuthorization3.setBackground(new Color(76, 175, 79, 200));
        Navbar__ButtonAuthorization3.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        Navbar__ButtonAuthorization3.setBorder(null);
        Navbar__ButtonAuthorization3.setBorderPainted(false);
        Navbar__ButtonAuthorization3.setPreferredSize(new java.awt.Dimension(70, 35));
        Navbar__ButtonAuthorization3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Navbar__ButtonAuthorization3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Filter__ButtonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(Navbar__ButtonAuthorization3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Toolbar__NameTitle6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Toolbar__NameTitle5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Filter__UsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Toolbar__NameTitle8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Toolbar__NameTitle9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Filter__WriteInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Filter__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Filter__DateMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Toolbar__NameTitle7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Filter__DateMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(Toolbar__NameTitle10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Toolbar__NameTitle10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toolbar__NameTitle5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Filter__UsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Toolbar__NameTitle6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Filter__DateMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Toolbar__NameTitle7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Filter__DateMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(Toolbar__NameTitle8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Filter__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Toolbar__NameTitle9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Filter__WriteInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Filter__ButtonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Navbar__ButtonAuthorization3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout Pages__AuthorizationLayout = new javax.swing.GroupLayout(Pages__Authorization);
        Pages__Authorization.setLayout(Pages__AuthorizationLayout);
        Pages__AuthorizationLayout.setHorizontalGroup(
            Pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__AuthorizationLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addGroup(Pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Pages__AuthorizationLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Pages__AuthorizationLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );
        Pages__AuthorizationLayout.setVerticalGroup(
            Pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pages__AuthorizationLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Pages__AuthorizationLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        Main__Pages.add(Pages__Authorization, "Pages__Authorization");

        Navbar.setBackground(new java.awt.Color(246, 251, 249));
        Navbar.setPreferredSize(new java.awt.Dimension(770, 70));

        Navbar__ButtonHome.setBackground(new Color(76, 175, 79, 200));
        Navbar__ButtonHome.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Trang chủ</div></html>");
        Navbar__ButtonHome.setBorder(null);
        Navbar__ButtonHome.setBorderPainted(false);
        Navbar__ButtonHome.setPreferredSize(new java.awt.Dimension(110, 35));
        Navbar__ButtonHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Navbar__ButtonHomeMouseClicked(evt);
            }
        });

        Navbar__ButtonProducts.setBackground(new Color(76, 175, 79, 200));
        Navbar__ButtonProducts.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Kho hàng</div></html>");
        Navbar__ButtonProducts.setBorder(null);
        Navbar__ButtonProducts.setBorderPainted(false);
        Navbar__ButtonProducts.setPreferredSize(new java.awt.Dimension(110, 35));
        Navbar__ButtonProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Navbar__ButtonProductsMouseClicked(evt);
            }
        });

        Navbar__ButtonStatistic.setBackground(new Color(76, 175, 79, 200));
        Navbar__ButtonStatistic.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Thống kê</div></html>");
        Navbar__ButtonStatistic.setBorder(null);
        Navbar__ButtonStatistic.setBorderPainted(false);
        Navbar__ButtonStatistic.setPreferredSize(new java.awt.Dimension(110, 35));
        Navbar__ButtonStatistic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Navbar__ButtonStatisticMouseClicked(evt);
            }
        });

        Navbar__ButtonSignOut.setBackground(new java.awt.Color(255, 86, 86));
        Navbar__ButtonSignOut.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Đăng xuất</div></html>");
        Navbar__ButtonSignOut.setBorder(null);
        Navbar__ButtonSignOut.setBorderPainted(false);
        Navbar__ButtonSignOut.setPreferredSize(new java.awt.Dimension(110, 35));
        Navbar__ButtonSignOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Navbar__ButtonSignOutMouseClicked(evt);
            }
        });

        Navbar__ButtonAuthorization.setBackground(new Color(76, 175, 79, 200));
        Navbar__ButtonAuthorization.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Tài khoản</div></html>");
        Navbar__ButtonAuthorization.setBorder(null);
        Navbar__ButtonAuthorization.setBorderPainted(false);
        Navbar__ButtonAuthorization.setPreferredSize(new java.awt.Dimension(110, 35));
        Navbar__ButtonAuthorization.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Navbar__ButtonAuthorizationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout NavbarLayout = new javax.swing.GroupLayout(Navbar);
        Navbar.setLayout(NavbarLayout);
        NavbarLayout.setHorizontalGroup(
            NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavbarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(Navbar__ButtonHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(Navbar__ButtonProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(Navbar__ButtonStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(Navbar__ButtonAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Navbar__ButtonSignOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        NavbarLayout.setVerticalGroup(
            NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavbarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Navbar__ButtonHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Navbar__ButtonProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Navbar__ButtonStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Navbar__ButtonSignOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Navbar__ButtonAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        Navbar__ButtonAuthorization.getAccessibleContext().setAccessibleName("Navbar__ButtonAuthorization");

        javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
        Main.setLayout(MainLayout);
        MainLayout.setHorizontalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Main__Pages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Navbar, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        MainLayout.setVerticalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Navbar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(Main__Pages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
            .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void showMessage(String message, boolean type) {
        if (type){
            JOptionPane.showMessageDialog(null, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Toolbar__ButtonAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonAddMouseClicked
        if (UserServices.getInstance().getCurrent().isWrite()){
            if (Toolbar__ButtonAdd.isEnabled()){
                if (!idCheck){
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Mã số sản phảm này đã tồn tại!</div></html>");
                }
                else{
                    if (!nameCheck){
                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Sản phẩm này đã tồn tại!</div></html>");
                    }
                    else{
                        if (!quantityCheck){
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không hợp lệ!</div></html>");
                        }
                        else {
                            if (!priceCheck){
                                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không hợp lệ!</div></html>");
                            }
                        }
                    }
                }
                if (Toolbar__IDInput.getText().equals("")){
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Mã số sản phẩm không được để trống!</div></html>");
                }
                else{
                    if (Toolbar__NameInput.getText().equals("")){
                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Tên sản phẩm không được để trống!</div></html>");  
                    }
                    else{
                        if (Toolbar__QuantityInput.getText().equals("")){
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không được để trống!</div></html>");
                        }
                        else{
                            if(Toolbar__PriceInput.getText().equals("")){
                                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không được để trống!</div></html>");
                            }
                            else{
                                if (Toolbar__ExpiryInput.getDate() == null){
                                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Hạn sử dụng sản phẩm không hợp lệ!</div></html>");
                                }
                                else{
                                    if (Toolbar__ManafacturerInput.getText().equals("")){
                                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Nhà sản xuất không được để trống!</div></html>");
                                    }
                                    else{
                                        if (idCheck == nameCheck == quantityCheck == priceCheck == manafacturerCheck == true){
                                            try {
                                                Product temp = new Product(Toolbar__IDInput.getText(), Toolbar__NameInput.getText(), (String) Toolbar__CategoryInput.getSelectedItem(), Integer.parseInt(Toolbar__QuantityInput.getText()), new BigDecimal(Toolbar__PriceInput.getText()), Toolbar__ExpiryInput.getDate(), Toolbar__ManafacturerInput.getText());
                                                if(!Toolbar__ThumbnailInput.getText().equals("") || Toolbar__ThumbnailInput.getText() == null){
                                                    temp.setThumbnail(Toolbar__ThumbnailInput.getText());
                                                }
                                                if (!Toolbar__DescriptionInput.getText().equals("") || Toolbar__DescriptionInput.getText() == null){
                                                    temp.setDescription(Toolbar__DescriptionInput.getText());
                                                }
                                                ProductService.getInstance().create(temp);
                                                showMessage("Thêm mới sản phẩm thành công!", true);
                                            } catch (Exception e) {
                                            }
                                            
//                                            if(!Toolbar__ThumbnailInput.getText().equals("") || Toolbar__ThumbnailInput.getText() == null){
//                                                temp.setThumbnail(Toolbar__ThumbnailInput.getText());
//                                            }
//                                            if (!Toolbar__DescriptionInput.getText().equals("") || Toolbar__DescriptionInput.getText() == null){
//                                                temp.setDescription(Toolbar__DescriptionInput.getText());
//                                            }
//                                            ProductDAO.getInstance().create(temp);
//                                            appController.setProductsTable(productsTable, ProductService.getInstance().get());
//                                            resetToolbar(true);
//                                            productTemplates.add(new ProductTemplate(temp));
//                                            Products__Main.add(productTemplates.getLast());
//                                            initStatistic();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            resetToolbar(true);
            showMessage("Bạn chưa được cho phép để thực hiện hành động này", false);
        }
    }//GEN-LAST:event_Toolbar__ButtonAddMouseClicked

    private void Toolbar__NameInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_Toolbar__NameInputCaretUpdate
        if (UserServices.getInstance().getCurrent().isWrite()) {
            if (Toolbar__NameInput.getText().equals("")){
                nameCheck = false;
                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Tên sản phẩm không được để trống!</div></html>"); 
            }
            else{
                try {
                    if (ProductService.getInstance().getSelectedProduct() != null){
                        if (ProductService.getInstance().findByName(Toolbar__NameInput.getText()) == null ||
                        ProductService.getInstance().getSelectedProduct().getName().equals(Toolbar__NameInput.getText())){
                            nameCheck = true;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");
                        }
                        else{
                            nameCheck = false;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Sản phẩm này đã tồn tại!</div></html>");
                        }
                    }
                    else{
                        if (ProductService.getInstance().findByName(Toolbar__NameInput.getText()) == null){
                            nameCheck = true;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");
                        }
                        else{
                            nameCheck = false;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Sản phẩm này đã tồn tại!</div></html>");
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_Toolbar__NameInputCaretUpdate

    private void Toolbar__QuantityInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_Toolbar__QuantityInputCaretUpdate
        if (UserServices.getInstance().getCurrent().isWrite()){
            if (Toolbar__QuantityInput.getText().equals("")) {
                quantityCheck = false;
                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không được để trống!</div></html>");
            }
            else{
                try {
                    int quantity = Integer.parseInt(Toolbar__QuantityInput.getText());
                    if (quantity >= 0){
                        quantityCheck = true;
                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");    
                    }
                    else{
                    quantityCheck = false;
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không hợp lệ!</div></html>");
                    }
                } catch (Exception e) {
                    quantityCheck = false;
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không hợp lệ!</div></html>");
                }
            }
        }
    }//GEN-LAST:event_Toolbar__QuantityInputCaretUpdate

    private void Toolbar__PriceInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_Toolbar__PriceInputCaretUpdate
        if (UserServices.getInstance().getCurrent().isWrite()){
            if (Toolbar__PriceInput.getText().equals("")) {
                priceCheck = false;
                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không được để trống!</div></html>");
            }
            else{
                try {
                    BigDecimal price = new BigDecimal(Toolbar__PriceInput.getText());
                    if (price.compareTo(BigDecimal.ZERO) >= 0){
                        priceCheck = true;
                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");    
                    }
                    else{
                        priceCheck = false;
                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không hợp lệ!</div></html>");
                    }
                } catch (Exception e) {
                    priceCheck = false;
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không hợp lệ!</div></html>");
                }
            }
        }
    }//GEN-LAST:event_Toolbar__PriceInputCaretUpdate

    private void Toolbar__ButtonFileChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonFileChooserMouseClicked
        if (Toolbar__ButtonFileChooser.isEnabled()){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("src/main/resources/images"));
            int res = fileChooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION){
                Toolbar__ThumbnailInput.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }//GEN-LAST:event_Toolbar__ButtonFileChooserMouseClicked

    private void Toolbar__IDInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_Toolbar__IDInputCaretUpdate
        if (UserServices.getInstance().getCurrent().isWrite()){
            if (Toolbar__IDInput.getText().equals("")) {
                idCheck = false;
                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Mã số sản phẩm không được để trống!</div></html>");
            }
            else{
                try {
                    if (ProductService.getInstance().getSelectedProduct() != null){
                        if (ProductService.getInstance().findById(Toolbar__IDInput.getText()) == null ||
                        ProductService.getInstance().getSelectedProduct().getId().equals(Toolbar__IDInput.getText())){
                            idCheck = true;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");
                        }
                        else{
                            idCheck = false;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Mã số sản phảm này đã tồn tại!</div></html>");
                        }
                    }
                    else{
                        if (ProductService.getInstance().findById(Toolbar__IDInput.getText()) == null){
                            idCheck = true;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");
                        }
                        else{
                            idCheck = false;
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Mã số sản phảm này đã tồn tại!</div></html>");
                        }
                    }
                } catch (IOException e) {
                }
            }
        }
    }//GEN-LAST:event_Toolbar__IDInputCaretUpdate

    private void Navbar__ButtonHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Navbar__ButtonHomeMouseClicked
        accessPage = PAGES_HOME;
        try {
            appController.setProductsTable(productsTable, ProductService.getInstance().getProducts());
        } catch (IOException  e) {
        }
        Toolbar__SortInput.setSelectedIndex(0);
        Toolbar__SearchInput.setSelectedIndex(0);
        resetToolbar(true);
        resetSearchField(true);
        CardLayout c = (CardLayout) Main__Pages.getLayout();
        c.show(Main__Pages, "Pages__Home");
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_Navbar__ButtonHomeMouseClicked

    private void Navbar__ButtonProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Navbar__ButtonProductsMouseClicked
        accessPage = PAGES_PRODUCTS;
        if (productSearchModify){
            resetWarehouse();
        }
        resetToolbar(true);
        resetSearchField(true);
        Toolbar__SortInput.setSelectedIndex(0);
        Toolbar__SearchInput.setSelectedIndex(0);
        CardLayout c = (CardLayout) Main__Pages.getLayout();
        c.show(Main__Pages, "Pages__Products");
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_Navbar__ButtonProductsMouseClicked

    private void Navbar__ButtonStatisticMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Navbar__ButtonStatisticMouseClicked
        accessPage = PAGES_STATISTIC;
        resetToolbar(true);
        resetSearchField(true);
        Toolbar__SortInput.setSelectedIndex(0);
        Toolbar__SearchInput.setSelectedIndex(0);
        initStatistic();
        CardLayout c = (CardLayout) Main__Pages.getLayout();
        c.show(Main__Pages, "Pages__Statistic");
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_Navbar__ButtonStatisticMouseClicked

    private void Navbar__ButtonSignOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Navbar__ButtonSignOutMouseClicked
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn đăng xuất tài khoản này?", "Cảnh báo",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        switch (response) {
            case JOptionPane.NO_OPTION: break;
            case JOptionPane.YES_OPTION: {
                CardLayout AppCardLayout = (CardLayout) Main__Pages.getParent().getParent().getParent().getLayout();
                AppCardLayout.show(Main__Pages.getParent().getParent().getParent(), "SignIn");
                CardLayout MainCardLayout = (CardLayout) Main__Pages.getLayout();
                MainCardLayout.show(Main__Pages, "Pages__Home");
            try {
                appController.setProductsTable(productsTable, ProductService.getInstance().getProducts());
            } catch (IOException e) {
            } 
                resetWarehouse();
                Products__Scroll.getVerticalScrollBar().setValue(0);
                initStatistic();
                resetToolbar(true);
                resetSearchField(true);
                UserServices.getInstance().setCurrent(null);
                accessPage = PAGES_HOME;
                this.repaint();
                this.revalidate();
            }
            case JOptionPane.CLOSED_OPTION: break;
            default: {
            }
        }
    }//GEN-LAST:event_Navbar__ButtonSignOutMouseClicked

    private void Toolbar__ManafacturerInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_Toolbar__ManafacturerInputCaretUpdate
        if (UserServices.getInstance().getCurrent().isWrite()){
            if (Toolbar__ManafacturerInput.getText().equals("")){
                manafacturerCheck = false;
                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Nhà sản xuất không được để trống!</div></html>");
            }
            else{
                manafacturerCheck = true;
                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");
            }
        }
    }//GEN-LAST:event_Toolbar__ManafacturerInputCaretUpdate

    private void Toolbar__ButtonResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonResetMouseClicked
        if (Toolbar__ButtonReset.isEnabled()){
            try {
                resetToolbar(true);
                Toolbar__ButtonAdd.setEnabled(true);
                Toolbar__ButtonEdit.setEnabled(false);
                Toolbar__ButtonDelete.setEnabled(false);
                productsTable.clearSelection();
                ProductService.getInstance().setSelectedProduct(null);
            } catch (IOException e) {
            } 
        }
    }//GEN-LAST:event_Toolbar__ButtonResetMouseClicked

    private void Toolbar__ButtonEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonEditMouseClicked
        if (UserServices.getInstance().getCurrent().isWrite()){
            if (Toolbar__ButtonEdit.isEnabled() && isModify()){
                if (!idCheck){
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Mã số sản phảm này đã tồn tại!</div></html>");
                }
                else{
                    if (!nameCheck){
                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Sản phẩm này đã tồn tại!</div></html>");
                    }
                    else{
                        if (!quantityCheck){
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không hợp lệ!</div></html>");
                        }
                        else {
                            if (!priceCheck){
                                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không hợp lệ!</div></html>");
                            }
                        }
                    }
                }
                if (Toolbar__IDInput.getText().equals("")){
                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Mã số sản phẩm không được để trống!</div></html>");
                }
                else{
                    if (Toolbar__NameInput.getText().equals("")){
                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Tên sản phẩm không được để trống!</div></html>");  
                    }
                    else{
                        if (Toolbar__QuantityInput.getText().equals("")){
                            Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không được để trống!</div></html>");
                        }
                        else{
                            if(Toolbar__PriceInput.getText().equals("")){
                                Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không được để trống!</div></html>");
                            }
                            else{
                                if (Toolbar__ExpiryInput.getDate() == null){
                                    dateCheck = false;
                                    Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Hạn sử dụng sản phẩm không hợp lệ!</div></html>");
                                }
                                else{
                                    if (Toolbar__ManafacturerInput.getText().equals("")){
                                        Toolbar__Alert.setText("<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Nhà sản xuất không được để trống!</div></html>");
                                    }
                                    else{
                                        if (idCheck&&nameCheck&&quantityCheck&&priceCheck&&dateCheck&&manafacturerCheck){
                                            JDialog.setDefaultLookAndFeelDecorated(true);
                                            int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa thông tin sản phẩm này?", "Cảnh báo",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                            switch (response) {
                                                case JOptionPane.NO_OPTION: break; 
                                                case JOptionPane.YES_OPTION: {
                                                    try {
                                                        String id  = Toolbar__IDInput.getText();
                                                        String name = Toolbar__NameInput.getText();
                                                        String category = (String) Toolbar__CategoryInput.getSelectedItem(); 
                                                        int quantity = Integer.parseInt(Toolbar__QuantityInput.getText());
                                                        BigDecimal price = new BigDecimal(Toolbar__PriceInput.getText());
                                                        Date expDate = Toolbar__ExpiryInput.getDate();
                                                        String manafacturer = Toolbar__ManafacturerInput.getText();
                                                        String thumbnail = Toolbar__ThumbnailInput.getText();
                                                        String description = Toolbar__DescriptionInput.getText();
                                                        Product product = new Product(id, name, category, quantity, price, expDate, manafacturer, thumbnail, description);
                                                        System.out.println(ProductService.getInstance().getSelectedProduct().getId());
                                                        int index = ProductService.getInstance().getProducts().indexOf(ProductService.getInstance().findById(ProductService.getInstance().getSelectedProduct().getId()));                                                     
                                                        ProductService.getInstance().update(ProductService.getInstance().getSelectedProduct().getId(), product);
                                                        showMessage("Sửa thông tin sản phẩm thành công!", true);
//                                                        Products__Main.remove(index);
//                                                        ProductTemplate temp = new ProductTemplate(product);
//                                                        productTemplates.add(index, temp);
//                                                        Products__Main.add(temp, index);
                                                    } catch (IOException | NumberFormatException  e) {
                                                    }
                                                }
                                                case JOptionPane.CLOSED_OPTION: break;
                                                default: {
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            showMessage("Bạn chưa được cho phép để thực hiện hành động này", false);
        }
    }//GEN-LAST:event_Toolbar__ButtonEditMouseClicked

    private void Toolbar__ButtonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonDeleteMouseClicked
        if (UserServices.getInstance().getCurrent().isWrite()){
            if (Toolbar__ButtonDelete.isEnabled()){
                JDialog.setDefaultLookAndFeelDecorated(true);
                int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa sản phẩm này?", "Cảnh báo",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                switch (response) {
                    case JOptionPane.NO_OPTION: break;
                    case JOptionPane.YES_OPTION: {
                        try {
                            Toolbar__ButtonAdd.setEnabled(true);
//                            productTemplates.remove(ProductServices.getInstance().getProducts().indexOf(ProductServices.getInstance().getSelectedProduct()));
//                            Products__Main.remove(ProductServices.getInstance().getProducts().indexOf(ProductServices.getInstance().getSelectedProduct()));
                            ProductService.getInstance().delete(ProductService.getInstance().getSelectedProduct().getId());
//                            appController.setProductsTable(productsTable, ProductServices.getInstance().getProducts());
                            productsTable.clearSelection();
                        } catch (IOException e) {
                        }
                    }
                    case JOptionPane.CLOSED_OPTION: break;
                    default: {
                    }
                }
            }
        }
        else{
            showMessage("Bạn chưa được cho phép để thực hiện hành động này", false);
        }
    }//GEN-LAST:event_Toolbar__ButtonDeleteMouseClicked

    private void Toolbar__ButtonSortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonSortMouseClicked
        if (Toolbar__ButtonSort.isEnabled()){
            try {
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Mặc định")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().getProducts());
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        CardLayout c = (CardLayout) Main__Pages.getLayout();
                        c.show(Main__Pages, "Pages__Products");
                    }
                }
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Mã số sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().sort(new Comparator.IDComparator()));   
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(ProductService.getInstance().sort(new Comparator.IDComparator()));
                    }
                }
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Tên sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().sort(new Comparator.NameComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(ProductService.getInstance().sort(new Comparator.NameComparator()));
                    }
                }
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Số lượng tăng")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().sort(new Comparator.QuantityUpComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(ProductService.getInstance().sort(new Comparator.QuantityUpComparator()));
                    }
                }
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Số lượng giảm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().sort(new Comparator.QuantityDownComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(ProductService.getInstance().sort(new Comparator.QuantityDownComparator()));
                    }
                }
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Đơn giá tăng")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().sort(new Comparator.PriceUpComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(ProductService.getInstance().sort(new Comparator.PriceUpComparator()));
                    }
                }
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Đơn giá giảm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().sort(new Comparator.PriceDownComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(ProductService.getInstance().sort(new Comparator.PriceDownComparator()));
                    }
                }
                if (Toolbar__SortInput.getSelectedItem().toString().equals("Hạn sử dụng")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().sort(new Comparator.ExpiryComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(ProductService.getInstance().sort(new Comparator.ExpiryComparator()));
                    }
                }
            } catch (IOException e) {
            }
        }
    }//GEN-LAST:event_Toolbar__ButtonSortMouseClicked

    private void Toolbar__SearchInputItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Toolbar__SearchInputItemStateChanged
        initSearchField();
        Toolbar__SearchStringInput.setText("");
        Toolbar__SearchMaxInput.setText("");
        Toolbar__SearchMinInput.setText("");
    }//GEN-LAST:event_Toolbar__SearchInputItemStateChanged

    private void Toolbar__ButtonSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonSearchMouseClicked
        try {
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Mã số sản phẩm")){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Tên sản phẩm")){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Danh mục")){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Nhà sản xuất")){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Đơn giá")){
            try {
                BigDecimal minPrice = new BigDecimal(Toolbar__SearchMinInput.getText());
                BigDecimal maxPrice = new BigDecimal(Toolbar__SearchMaxInput.getText());
                if (accessPage == PAGES_HOME){
                    appController.setProductsTable(productsTable, ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                }
                if (accessPage == PAGES_PRODUCTS){
                    filter(ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                    productSearchModify = true;
                }
            } catch (IOException e) {
                showMessage("Đơn giá sản phẩm không hợp lệ", false);
            }
        }
        if (Toolbar__SearchInput.getSelectedItem().toString().equals("Hạn sử dụng")){
            try {
                Date minDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMinInput.getText());
                Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMaxInput.getText());
                if (accessPage == PAGES_HOME){
                    appController.setProductsTable(productsTable, ProductService.getInstance().filterByExpiry(minDate, maxDate));
                }
                if (accessPage == PAGES_PRODUCTS){
                    filter(ProductService.getInstance().filterByExpiry(minDate, maxDate));
                    productSearchModify = true;
                }
            } catch (IOException | ParseException  e) {
                showMessage("Hạn sử dụng sản phẩm không hợp lệ", false);
            }
        }
        } catch (IOException e) {
        }
        initSearchField();
    }//GEN-LAST:event_Toolbar__ButtonSearchMouseClicked

    private void Statistic__ButtonPercentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Statistic__ButtonPercentMouseClicked
        initPieChart();
    }//GEN-LAST:event_Statistic__ButtonPercentMouseClicked

    private void Toolbar__ButtonResetSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Toolbar__ButtonResetSearchMouseClicked
        if (accessPage == PAGES_HOME){
            try {
                appController.setProductsTable(productsTable, ProductService.getInstance().getProducts());
            } catch (IOException  e) {
            }
            resetSearchField(true);
        }
        else{
            resetWarehouse();
            Toolbar__SearchStringInput.setText("");
            Toolbar__SearchMinInput.setText("");
            Toolbar__SearchMaxInput.setText("");
        }
    }//GEN-LAST:event_Toolbar__ButtonResetSearchMouseClicked

    private void Toolbar__SearchStringInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Toolbar__SearchStringInputFocusGained
        Toolbar__SearchStringInput.setText("");
    }//GEN-LAST:event_Toolbar__SearchStringInputFocusGained

    private void Toolbar__SearchMinInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Toolbar__SearchMinInputFocusGained
        Toolbar__SearchMinInput.setText("");
    }//GEN-LAST:event_Toolbar__SearchMinInputFocusGained

    private void Toolbar__SearchMaxInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Toolbar__SearchMaxInputFocusGained
        Toolbar__SearchMaxInput.setText("");
    }//GEN-LAST:event_Toolbar__SearchMaxInputFocusGained

    private void Statistic__ButtonPercent1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Statistic__ButtonPercent1MouseClicked
        initBarChart();
    }//GEN-LAST:event_Statistic__ButtonPercent1MouseClicked

    private void Navbar__ButtonAuthorizationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Navbar__ButtonAuthorizationMouseClicked
        accessPage = PAGES_AUTHORIZATION;
        resetToolbar(true);
        CardLayout c = (CardLayout) Main__Pages.getLayout();
        c.show(Main__Pages, "Pages__Authorization");
        usersTable.setBackground(Color.WHITE);
        adminController.setUsersTable(usersTable, UserServices.getInstance().getUsers());
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_Navbar__ButtonAuthorizationMouseClicked

    private void Information__ButtonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Information__ButtonDeleteMouseClicked
        if (Information__ButtonDelete.isEnabled()){
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa tài khoản này?", "Cảnh báo",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            switch (response) {
                case JOptionPane.NO_OPTION: break;
                case JOptionPane.YES_OPTION: {
                    UserDAO.getInstance().delete(UserServices.getInstance().getSelectedUser().getUsername());
                    adminController.setUsersTable(usersTable, UserServices.getInstance().getUsers());
                    UserServices.getInstance().setSelectedUser(null);
                    this.repaint();
                    this.revalidate();
                    showMessage("Bạn đã xóa thành công tài khoản", true);
                }
                case JOptionPane.CLOSED_OPTION: break;
                default: {
                }
            }
        }
    }//GEN-LAST:event_Information__ButtonDeleteMouseClicked

    private void Information__ButtonEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Information__ButtonEditMouseClicked
        if (Information__ButtonEdit.isEnabled() && isUserModify()){
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa quyền cho tài khoản này?", "Cảnh báo",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            switch (response) {
                case JOptionPane.NO_OPTION: break;
                case JOptionPane.YES_OPTION: {
                    UserDAO.getInstance().update(UserServices.getInstance().getSelectedUser(), Information__ReadInput.isSelected(), Information__WriteInput.isSelected());
                    adminController.setUsersTable(usersTable, UserServices.getInstance().getUsers());
                    this.repaint();
                    this.revalidate();
                    resetUserEdit();
                    showMessage("Bạn đã chỉnh sửa thành công quyền cho tài khoản", true);
                }
                case JOptionPane.CLOSED_OPTION: break;
                default: {
                }
            }
        }
    }//GEN-LAST:event_Information__ButtonEditMouseClicked

    private void Filter__ButtonFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Filter__ButtonFilterMouseClicked
        String username = Filter__UsernameInput.getText();
        Date dateMin = null;
        Date dateMax = null;
        if (!Filter__DateMin.getText().equals("") && !Filter__DateMax.getText().equals("")){
            try {
                dateMin = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMin.getText());
                dateMax = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMax.getText());
            } catch (Exception e) {
                showMessage("Ngày tạo tài khoản không hợp lệ", false);
            } 
        }
        adminController.setUsersTable(usersTable, UserServices.getInstance().filter(username, dateMin, dateMax, Filter__ReadInput.isSelected(), Filter__WriteInput.isSelected()));
        resetUserEdit();
    }//GEN-LAST:event_Filter__ButtonFilterMouseClicked

    private void Navbar__ButtonAuthorization3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Navbar__ButtonAuthorization3MouseClicked
        adminController.setUsersTable(usersTable, UserServices.getInstance().getUsers());
        resetUserFilter();
    }//GEN-LAST:event_Navbar__ButtonAuthorization3MouseClicked

    private void Information__ButtonResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Information__ButtonResetMouseClicked
        resetUserEdit();
    }//GEN-LAST:event_Information__ButtonResetMouseClicked

    private void Information__ReadInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Information__ReadInputStateChanged
        if (Information__WriteInput.isSelected()){
            Information__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_Information__ReadInputStateChanged

    private void Information__WriteInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Information__WriteInputStateChanged
        if (Information__WriteInput.isSelected()){
            Information__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_Information__WriteInputStateChanged

    private void Filter__ReadInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Filter__ReadInputStateChanged
        if (Filter__WriteInput.isSelected()){
            Filter__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_Filter__ReadInputStateChanged

    private void Filter__WriteInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Filter__WriteInputStateChanged
        if (Filter__WriteInput.isSelected()){
            Filter__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_Filter__WriteInputStateChanged

    private void Filter__UsernameInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Filter__UsernameInputFocusGained
        Filter__UsernameInput.setText("");
        Filter__WriteInput.setSelected(false);
        Filter__ReadInput.setSelected(false);
    }//GEN-LAST:event_Filter__UsernameInputFocusGained

    private void Filter__DateMinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Filter__DateMinFocusGained
        Filter__DateMin.setText("");
        Filter__WriteInput.setSelected(false);
        Filter__ReadInput.setSelected(false);
    }//GEN-LAST:event_Filter__DateMinFocusGained

    private void Filter__DateMaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Filter__DateMaxFocusGained
        Filter__DateMax.setText("");
        Filter__WriteInput.setSelected(false);
        Filter__ReadInput.setSelected(false);
    }//GEN-LAST:event_Filter__DateMaxFocusGained

    private void Toolbar__SearchStringInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Toolbar__SearchStringInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Mã số sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Tên sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Danh mục")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Nhà sản xuất")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Đơn giá")){
                    try {
                        BigDecimal minPrice = new BigDecimal(Toolbar__SearchMinInput.getText());
                        BigDecimal maxPrice = new BigDecimal(Toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                            productSearchModify = true;
                        }
                    } catch (IOException e) {
                        showMessage("Đơn giá sản phẩm không hợp lệ", false);
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Hạn sử dụng")){
                    try {
                        Date minDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMinInput.getText());
                        Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, ProductService.getInstance().filterByExpiry(minDate, maxDate));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(ProductService.getInstance().filterByExpiry(minDate, maxDate));
                            productSearchModify = true;
                        }
                    } catch (IOException | ParseException  e) {
                        showMessage("Hạn sử dụng sản phẩm không hợp lệ", false);
                    }
                }
            } catch (IOException e) {
            }
        initSearchField();  
        }
    }//GEN-LAST:event_Toolbar__SearchStringInputKeyPressed

    private void Toolbar__SearchMinInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Toolbar__SearchMinInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Mã số sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Tên sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Danh mục")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Nhà sản xuất")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Đơn giá")){
                    try {
                        BigDecimal minPrice = new BigDecimal(Toolbar__SearchMinInput.getText());
                        BigDecimal maxPrice = new BigDecimal(Toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                            productSearchModify = true;
                        }
                    } catch (IOException e) {
                        showMessage("Đơn giá sản phẩm không hợp lệ", false);
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Hạn sử dụng")){
                    try {
                        Date minDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMinInput.getText());
                        Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, ProductService.getInstance().filterByExpiry(minDate, maxDate));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(ProductService.getInstance().filterByExpiry(minDate, maxDate));
                            productSearchModify = true;
                        }
                    } catch (IOException | ParseException  e) {
                        showMessage("Hạn sử dụng sản phẩm không hợp lệ", false);
                    }
                }
            } catch (IOException e) {
            }
        initSearchField();  
        }
    }//GEN-LAST:event_Toolbar__SearchMinInputKeyPressed

    private void Toolbar__SearchMaxInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Toolbar__SearchMaxInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Mã số sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterById(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Tên sản phẩm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByName(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Danh mục")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByCategory(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Nhà sản xuất")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(ProductService.getInstance().filterByManafacturer(Toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Đơn giá")){
                    try {
                        BigDecimal minPrice = new BigDecimal(Toolbar__SearchMinInput.getText());
                        BigDecimal maxPrice = new BigDecimal(Toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(ProductService.getInstance().filterByPrice(minPrice, maxPrice));
                            productSearchModify = true;
                        }
                    } catch (IOException e) {
                        showMessage("Đơn giá sản phẩm không hợp lệ", false);
                    }
                }
                if (Toolbar__SearchInput.getSelectedItem().toString().equals("Hạn sử dụng")){
                    try {
                        Date minDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMinInput.getText());
                        Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse(Toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, ProductService.getInstance().filterByExpiry(minDate, maxDate));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(ProductService.getInstance().filterByExpiry(minDate, maxDate));
                            productSearchModify = true;
                        }
                    } catch (IOException | ParseException  e) {
                        showMessage("Hạn sử dụng sản phẩm không hợp lệ", false);
                    }
                }
            } catch (IOException e) {
            }
        initSearchField();  
        }
    }//GEN-LAST:event_Toolbar__SearchMaxInputKeyPressed

    private void Filter__UsernameInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Filter__UsernameInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = Filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!Filter__DateMin.getText().equals("") && !Filter__DateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMin.getText());
                    dateMax = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMax.getText());
                } catch (Exception e) {
                    showMessage("Ngày tạo tài khoản không hợp lệ", false);
                } 
            }
            adminController.setUsersTable(usersTable, UserServices.getInstance().filter(username, dateMin, dateMax, Filter__ReadInput.isSelected(), Filter__WriteInput.isSelected()));
            resetUserEdit();
        }
    }//GEN-LAST:event_Filter__UsernameInputKeyPressed

    private void Filter__DateMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Filter__DateMinKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = Filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!Filter__DateMin.getText().equals("") && !Filter__DateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMin.getText());
                    dateMax = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMax.getText());
                } catch (Exception e) {
                    showMessage("Ngày tạo tài khoản không hợp lệ", false);
                } 
            }
            adminController.setUsersTable(usersTable, UserServices.getInstance().filter(username, dateMin, dateMax, Filter__ReadInput.isSelected(), Filter__WriteInput.isSelected()));
            resetUserEdit();
        }
    }//GEN-LAST:event_Filter__DateMinKeyPressed

    private void Filter__DateMaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Filter__DateMaxKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = Filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!Filter__DateMin.getText().equals("") && !Filter__DateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMin.getText());
                    dateMax = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMax.getText());
                } catch (Exception e) {
                    showMessage("Ngày tạo tài khoản không hợp lệ", false);
                } 
            }
            adminController.setUsersTable(usersTable, UserServices.getInstance().filter(username, dateMin, dateMax, Filter__ReadInput.isSelected(), Filter__WriteInput.isSelected()));
            resetUserEdit();
        }
    }//GEN-LAST:event_Filter__DateMaxKeyPressed

    private void Filter__ReadInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Filter__ReadInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = Filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!Filter__DateMin.getText().equals("") && !Filter__DateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMin.getText());
                    dateMax = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMax.getText());
                } catch (Exception e) {
                    showMessage("Ngày tạo tài khoản không hợp lệ", false);
                } 
            }
            adminController.setUsersTable(usersTable, UserServices.getInstance().filter(username, dateMin, dateMax, Filter__ReadInput.isSelected(), Filter__WriteInput.isSelected()));
            resetUserEdit();
        }
    }//GEN-LAST:event_Filter__ReadInputKeyPressed

    private void Filter__WriteInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Filter__WriteInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = Filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!Filter__DateMin.getText().equals("") && !Filter__DateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMin.getText());
                    dateMax = new SimpleDateFormat("dd/MM/yyyy").parse(Filter__DateMax.getText());
                } catch (Exception e) {
                    showMessage("Ngày tạo tài khoản không hợp lệ", false);
                } 
            }
            adminController.setUsersTable(usersTable, UserServices.getInstance().filter(username, dateMin, dateMax, Filter__ReadInput.isSelected(), Filter__WriteInput.isSelected()));
            resetUserEdit();
        }
    }//GEN-LAST:event_Filter__WriteInputKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Filter__ButtonFilter;
    private javax.swing.JTextField Filter__DateMax;
    private javax.swing.JTextField Filter__DateMin;
    private javax.swing.JCheckBox Filter__ReadInput;
    private javax.swing.JTextField Filter__UsernameInput;
    private javax.swing.JCheckBox Filter__WriteInput;
    private javax.swing.JPanel Home__Table;
    private javax.swing.JButton Information__ButtonDelete;
    private javax.swing.JButton Information__ButtonEdit;
    private javax.swing.JButton Information__ButtonReset;
    private javax.swing.JCheckBox Information__ReadInput;
    private javax.swing.JTextField Information__RegisterInput;
    private javax.swing.JTextField Information__UsernameInput;
    private javax.swing.JCheckBox Information__WriteInput;
    private javax.swing.JPanel Main;
    private javax.swing.JPanel Main__Pages;
    private javax.swing.JPanel Navbar;
    private javax.swing.JButton Navbar__ButtonAuthorization;
    private javax.swing.JButton Navbar__ButtonAuthorization3;
    private javax.swing.JButton Navbar__ButtonHome;
    private javax.swing.JButton Navbar__ButtonProducts;
    private javax.swing.JButton Navbar__ButtonSignOut;
    private javax.swing.JButton Navbar__ButtonStatistic;
    private javax.swing.JPanel Pages__Authorization;
    private javax.swing.JPanel Pages__Home;
    private javax.swing.JPanel Pages__Products;
    private javax.swing.JPanel Pages__Sort;
    private javax.swing.JPanel Pages__Statistic;
    private javax.swing.JPanel Products__Main;
    private javax.swing.JScrollPane Products__Scroll;
    private javax.swing.JPanel Sort__Main;
    private javax.swing.JScrollPane Sort__Scroll;
    private hvan.qlkh.chart.Chart Statistic__BarChart;
    private javax.swing.JButton Statistic__ButtonPercent;
    private javax.swing.JButton Statistic__ButtonPercent1;
    private javax.swing.JPanel Statistic__Category;
    private javax.swing.JLabel Statistic__CategoryStatistic;
    private javax.swing.JLabel Statistic__CategoryThumb;
    private javax.swing.JLabel Statistic__CategoryTitle;
    private javax.swing.JPanel Statistic__Main;
    private javax.swing.JPanel Statistic__Manafacturer;
    private javax.swing.JLabel Statistic__ManafacturerStatistic;
    private javax.swing.JLabel Statistic__ManafacturerThumb;
    private javax.swing.JLabel Statistic__ManafacturerTitle;
    private javax.swing.JPanel Statistic__Menu;
    private javax.swing.JComboBox<String> Statistic__PercentStatistic;
    private javax.swing.JLabel Statistic__PercentStatisticTitle;
    private hvan.qlkh.chart.PieChart Statistic__PieChart;
    private javax.swing.JComboBox<String> Statistic__QuantityStatistic;
    private javax.swing.JLabel Statistic__QuantityStatisticTitle;
    private javax.swing.JScrollPane Statistic__Scroll;
    private javax.swing.JPanel Statistic__TotalProducts;
    private javax.swing.JLabel Statistic__TotalProductsStatistic;
    private javax.swing.JLabel Statistic__TotalProductsThumb;
    private javax.swing.JLabel Statistic__TotalProductsTitle;
    private javax.swing.JPanel Statistic__TotalQuanity;
    private javax.swing.JLabel Statistic__TotalQuantityStatistic;
    private javax.swing.JLabel Statistic__TotalQuantityThumb;
    private javax.swing.JLabel Statistic__TotalQuantityTitle;
    private javax.swing.JPanel Toolbar;
    private javax.swing.JLabel Toolbar__Alert;
    private javax.swing.JButton Toolbar__ButtonAdd;
    private javax.swing.JButton Toolbar__ButtonDelete;
    private javax.swing.JButton Toolbar__ButtonEdit;
    private javax.swing.JButton Toolbar__ButtonFileChooser;
    private javax.swing.JButton Toolbar__ButtonReset;
    private javax.swing.JButton Toolbar__ButtonResetSearch;
    private javax.swing.JButton Toolbar__ButtonSearch;
    private javax.swing.JButton Toolbar__ButtonSort;
    private javax.swing.JComboBox<String> Toolbar__CategoryInput;
    private javax.swing.JLabel Toolbar__CategoryTitle;
    private javax.swing.JLabel Toolbar__Decorate;
    private javax.swing.JTextPane Toolbar__DescriptionInput;
    private javax.swing.JScrollPane Toolbar__DescriptionScroll;
    private javax.swing.JLabel Toolbar__DescriptionTitle;
    private com.toedter.calendar.JDateChooser Toolbar__ExpiryInput;
    private javax.swing.JLabel Toolbar__ExpiryTitle;
    private javax.swing.JTextField Toolbar__IDInput;
    private javax.swing.JLabel Toolbar__IDTitle;
    private javax.swing.JTextField Toolbar__ManafacturerInput;
    private javax.swing.JLabel Toolbar__ManafacturerTitle;
    private javax.swing.JTextField Toolbar__NameInput;
    private javax.swing.JLabel Toolbar__NameTitle;
    private javax.swing.JLabel Toolbar__NameTitle1;
    private javax.swing.JLabel Toolbar__NameTitle10;
    private javax.swing.JLabel Toolbar__NameTitle11;
    private javax.swing.JLabel Toolbar__NameTitle2;
    private javax.swing.JLabel Toolbar__NameTitle3;
    private javax.swing.JLabel Toolbar__NameTitle4;
    private javax.swing.JLabel Toolbar__NameTitle5;
    private javax.swing.JLabel Toolbar__NameTitle6;
    private javax.swing.JLabel Toolbar__NameTitle7;
    private javax.swing.JLabel Toolbar__NameTitle8;
    private javax.swing.JLabel Toolbar__NameTitle9;
    private javax.swing.JTextField Toolbar__PriceInput;
    private javax.swing.JLabel Toolbar__PriceTitle;
    private javax.swing.JTextField Toolbar__QuantityInput;
    private javax.swing.JLabel Toolbar__QuantityTitle;
    private javax.swing.JComboBox<String> Toolbar__SearchInput;
    private javax.swing.JTextField Toolbar__SearchMaxInput;
    private javax.swing.JTextField Toolbar__SearchMinInput;
    private javax.swing.JTextField Toolbar__SearchStringInput;
    private javax.swing.JLabel Toolbar__SearchTitle;
    private javax.swing.JPanel Toolbar__SeparatorBottom;
    private javax.swing.JPanel Toolbar__SeparatorTop;
    private javax.swing.JComboBox<String> Toolbar__SortInput;
    private javax.swing.JLabel Toolbar__SortTitle;
    private javax.swing.JTextField Toolbar__ThumbnailInput;
    private javax.swing.JLabel Toolbar__ThumbnailTitle;
    private javax.swing.JLabel Toolbar__Title;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    public void setProductFromSelected() throws ParseException{
        int row = productsTable.getSelectedRow();
        if (row >= 0) {
            Toolbar__IDInput.setText(productsTable.getModel().getValueAt(row, 0).toString());
            Toolbar__NameInput.setText(productsTable.getModel().getValueAt(row, 1).toString());
            Toolbar__CategoryInput.setSelectedItem(productsTable.getModel().getValueAt(row, 2).toString());
            Toolbar__QuantityInput.setText(productsTable.getModel().getValueAt(row, 3).toString());
            Toolbar__PriceInput.setText(BigDecimalConverter.currencyParse(productsTable.getModel().getValueAt(row, 4).toString()).toString());
            Toolbar__ExpiryInput.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(productsTable.getModel().getValueAt(row, 5).toString()));
            Toolbar__ManafacturerInput.setText(productsTable.getModel().getValueAt(row, 6).toString());
            try {
                if (ProductService.getInstance().getSelectedProduct() != null){
                    if (ProductService.getInstance().getSelectedProduct().getThumbnail() != null){
                        Toolbar__ThumbnailInput.setText(ProductService.getInstance().getSelectedProduct().getThumbnail());
                    }
                    if (ProductService.getInstance().getSelectedProduct().getDescription()!= null){
                        Toolbar__DescriptionInput.setText(ProductService.getInstance().getSelectedProduct().getDescription());
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    public void setUserFromSelected(){
        int row = usersTable.getSelectedRow();
        if (row >= 0) {
            Information__UsernameInput.setText(usersTable.getModel().getValueAt(row, 1).toString());
            Information__RegisterInput.setText(usersTable.getModel().getValueAt(row, 2).toString());
            Information__ReadInput.setSelected(("x".equals(usersTable.getModel().getValueAt(row, 3).toString())));
            Information__WriteInput.setSelected("x".equals(usersTable.getModel().getValueAt(row, 4).toString()));
            if (usersTable.getModel().getValueAt(row, 1).toString().equals("admin")){
                Information__ReadInput.setEnabled(false);
                Information__WriteInput.setEnabled(false);
                Information__ButtonEdit.setEnabled(false);
                Information__ButtonDelete.setEnabled(false);
            }
            else{
                Information__ReadInput.setEnabled(true);
                Information__WriteInput.setEnabled(true);
                Information__ButtonEdit.setEnabled(true);
                Information__ButtonDelete.setEnabled(true);
            }
        }
    }

    public void addProductSelectionListener(ListSelectionListener listener) {
        productsTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addUserSelectionListener(ListSelectionListener listener) {
        usersTable.getSelectionModel().addListSelectionListener(listener);
    }

    class ProductSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent evt) {
            try {
                setProductFromSelected();
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            int row = productsTable.getSelectedRow();
            if (row >= 0) {
                try {
                    ProductService.getInstance();
                    ProductService.setSelectedProduct(ProductService.getInstance().findById(productsTable.getModel().getValueAt(row, 0).toString()));
                } catch (IOException e) {
                }
            }
            Toolbar__ButtonAdd.setEnabled(false);
            Toolbar__ButtonEdit.setEnabled(true);
            Toolbar__ButtonDelete.setEnabled(true);
        }
    }

    class UserSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            setUserFromSelected();
            int row = usersTable.getSelectedRow();
            if (row >= 0) {
                UserServices.getInstance();
                UserServices.getInstance().setSelectedUser(UserServices.getInstance().findByName(usersTable.getModel().getValueAt(row, 1).toString()));
            }
        }
    }
}
