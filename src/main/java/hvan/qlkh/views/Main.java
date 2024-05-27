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
import hvan.qlkh.controllers.UserController;
import hvan.qlkh.models.User;
import hvan.qlkh.services.Services;
import hvan.qlkh.utils.Panel;
import hvan.qlkh.utils.ScrollBar;
import hvan.qlkh.utils.Table;
import hvan.qlkh.utils.WrapLayout;
import hvan.qlkh.utils.Comparator;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.imgscalr.Scalr;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public final class Main extends javax.swing.JPanel implements ListSelectionListener{

    private static final int PAGES_HOME = 0;
    private static final int PAGES_PRODUCTS = 1;
    private static final int PAGES_STATISTIC = 2;
    private static final int PAGES_AUTHORIZATION = 3;

    private static final String IMAGES_DIR = "images/";
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private static final String ID = "Mã số";
    private static final String NAME = "Tên sản phẩm";
    private static final String CATEGORY = "Danh mục";
    private static final String MANAFACTURER = "Nhà sản xuất";
    private static final String PRICE = "Đơn giá";
    private static final String EXPIRY = "Hạn sử dụng";

    private static final String CARD_HOME = "pages__Home";
    private static final String CARD_PRODUCTS = "pages__Products";
    private static final String CARD_STATISTIC = "pages__Statistic";
    private static final String CARD_AUTHORIZATION = "pages__Authorization";
    private static final String CARD_SORT = "pages__Sort";

    private static final String STATISTIC_HEADER = "<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">";
    private static final String STATISTIC_FOOTER = "</div></html>";
    private static final String NAME_EXIST_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Sản phẩm này đã tồn tại!</div></html>";
    private static final String QUANTITY_FORMAT_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không hợp lệ!</div></html>";
    private static final String PRICE_FORMAT_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không hợp lệ!</div></html>";
    private static final String NAME_BLANK_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Tên sản phẩm không được để trống!</div></html>";
    private static final String QUANTITY_BLANK_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Số lượng sản phẩm không được để trống!</div></html>";
    private static final String PRICE_BLANK_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá sản phẩm không được để trống!</div></html>";
    private static final String DATE_FORMAT_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Hạn sử dụng sản phẩm không hợp lệ!</div></html>";
    private static final String MANAFACTURER_BLANK_ERROR_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Nhà sản xuất không được để trống!</div></html>";
    private static final String NULL_MESSAGE = "<html><div style=\"text-align: center; width: 265px; color: red; font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>";

    private static final String TYPE_DIALOG_MESSAGE = "Cảnh báo";
    private static final String UNKNOWN_ERROR_DIALOG_MESSAGE = "Một lỗi không xác định đã xảy ra";
    private static final String PRICE_FORMAT_ERROR_DIALOG_MESSAGE = "Đơn giá sản phẩm không hợp lệ";
    private static final String AUTHORIZATION_ERROR_DIALOG_MESSAGE = "Bạn chưa được cho phép để thực hiện hành động này";
    private static final String EXPIRY_FORMAT_ERROR_DIALOG_MESSAGE = "Hạn sử dụng sản phẩm không hợp lệ";
    private static final String CREATE_FORMAT_ERROR_DIALOG_MESSAGE = "Ngày tạo tài khoản không hợp lệ";

    private static Main instance;

    private transient Controller appController;
    private transient Controller adminController;

    private Table productsTable = new Table();
    private Table usersTable = new Table();

    private static List<ProductTemplate> productTemplates = new ArrayList<>();

    private int accessPage;
    private JPanel p = new JPanel();
    private static final Color [] CHART_COLOR = {
                                                    new Color(221, 65, 65),
                                                    new Color(235, 121, 0),
                                                    new Color(245, 83, 118),
                                                    new Color(47, 157, 64),
                                                    new Color(180, 96, 96),
                                                    new Color(128, 72, 156),
                                                    new Color(149, 205, 65),
                                                    new Color(23, 126, 238),
                                                    new Color(255, 175, 69)
                                                };
    private static final String [] PRICE_RANGES = {
                                                    "dưới 1tr",
                                                    "1tr - 5tr",
                                                    "5tr - 10tr",
                                                    "10tr - 20tr",
                                                    "trên 20tr"
                                                };

    private boolean nameCheck = true;
    private boolean quantityCheck = true;
    private boolean priceCheck = true;
    private boolean dateCheck = true;
    private boolean manafacturerCheck = true;
    private boolean productSearchModify = false;

    public Controller getAppController(){
        return appController;
    }

    public Controller getAdminController(){
        return adminController;
    }

    public Table getProductsTable(){
        return productsTable;
    }

    public Table getUsersTable(){
        return usersTable;
    }

    public void initToolbar(){
        toolbar__DescriptionScroll.setBorder(new LineBorder(new Color(0, 0, 0, 200), 1));
        toolbar__DescriptionScroll.setVerticalScrollBar(new ScrollBar());
        toolbar__DescriptionScroll.getVerticalScrollBar().setBackground(Color.WHITE);
        toolbar__DescriptionScroll.getViewport().setBackground(Color.WHITE);
        toolbar__DescriptionInput.setCaretPosition(0);
        toolbar__ButtonEdit.setEnabled(false);
        toolbar__ButtonDelete.setEnabled(false);
        toolbar__ExpiryInput.getJCalendar().setPreferredSize(new Dimension(365, 300));
        toolbar__ExpiryInput.getJCalendar().getDayChooser().getDayPanel().setBackground(Color.white);
        toolbar__ExpiryInput.getJCalendar().getDayChooser().getDayPanel().setLayout(new GridLayout(7, 7, -6, 0));
        toolbar__ExpiryInput.getJCalendar().getDayChooser().getDayPanel().getComponents();
        toolbar__CategoryInput.setBackground(Color.white);
        toolbar__SortInput.setBackground(Color.white);
        toolbar__SearchInput.setBackground(Color.white);
        toolbar__ThumbnailInput.setEditable(false);
    }

    public void resetToolbar(boolean state){
        toolbar__NameInput.setText("");
        toolbar__CategoryInput.setSelectedIndex(0);
        toolbar__QuantityInput.setText("");
        toolbar__PriceInput.setText("");
        toolbar__ExpiryInput.setDate(null);
        toolbar__ManafacturerInput.setText("");
        toolbar__ThumbnailInput.setText("");
        toolbar__DescriptionInput.setText("");
        toolbar__Alert.setText("");
        toolbar__ButtonAdd.setEnabled(state);
        toolbar__ButtonEdit.setEnabled(false);
        toolbar__ButtonDelete.setEnabled(false);
        toolbar__ButtonReset.setEnabled(state);
        toolbar__ThumbnailInput.setEditable(false);
        Services.setSelectedProduct(null);
    }

    public void initSearchField(){
        if (toolbar__SearchInput.getSelectedItem().toString().equals(ID)){
            toolbar__SearchStringInput.setVisible(true);
            toolbar__SearchMinInput.setText("");
            toolbar__SearchMinInput.setVisible(false);
            toolbar__SearchMaxInput.setText("");
            toolbar__SearchMaxInput.setVisible(false);
            toolbar__Decorate.setVisible(false);
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(NAME)){
            toolbar__SearchStringInput.setVisible(true);
            toolbar__SearchMinInput.setText("");
            toolbar__SearchMinInput.setVisible(false);
            toolbar__SearchMaxInput.setText("");
            toolbar__SearchMaxInput.setVisible(false);
            toolbar__Decorate.setVisible(false);
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(CATEGORY)){
            toolbar__SearchStringInput.setVisible(true);
            toolbar__SearchMinInput.setText("");
            toolbar__SearchMinInput.setVisible(false);
            toolbar__SearchMaxInput.setText("");
            toolbar__SearchMaxInput.setVisible(false);
            toolbar__Decorate.setVisible(false);
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(MANAFACTURER)){
            toolbar__SearchStringInput.setVisible(true);
            toolbar__SearchMinInput.setText("");
            toolbar__SearchMinInput.setVisible(false);
            toolbar__SearchMaxInput.setText("");
            toolbar__SearchMaxInput.setVisible(false);
            toolbar__Decorate.setVisible(false);
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(PRICE)){
            toolbar__SearchStringInput.setText("");
            toolbar__SearchStringInput.setVisible(false);
            toolbar__SearchMinInput.setVisible(true);
            toolbar__SearchMaxInput.setVisible(true);
            toolbar__Decorate.setVisible(true);
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(EXPIRY)){
            toolbar__SearchStringInput.setText("");
            toolbar__SearchStringInput.setVisible(false);
            toolbar__SearchMinInput.setVisible(true);
            toolbar__SearchMaxInput.setVisible(true);
            toolbar__Decorate.setVisible(true);
        }
    }

    private void resetSearchField(boolean state){
        toolbar__SearchStringInput.setText("");
        toolbar__SearchMinInput.setText("");
        toolbar__SearchMaxInput.setText("");
        toolbar__SortInput.setSelectedIndex(0);
        toolbar__SearchInput.setSelectedIndex(0);
        toolbar__ButtonSort.setEnabled(state);
        toolbar__ButtonSearch.setEnabled(state);
        toolbar__ButtonResetSearch.setEnabled(state);
        toolbar.repaint();
        toolbar.revalidate();
    }

    private boolean isModify(){
        boolean temp = false;
        try {
            if (Services.getInstance().getSelectedProduct() != null){
                Product product = Services.getInstance().getSelectedProduct();
                if (nameCheck == quantityCheck == priceCheck == manafacturerCheck){
                    temp = !(toolbar__NameInput.getText().equals(product.getName())&&
                            ((String)toolbar__CategoryInput.getSelectedItem()).equals(product.getCategory())&&
                            (Integer.parseInt(toolbar__QuantityInput.getText()) == product.getQuantity())&&
                            BigDecimalConverter.currencyParse(toolbar__PriceInput.getText()).equals(product.getPrice())&&
                            toolbar__ExpiryInput.getDate().equals(product.getExpDate())&&
                            toolbar__ManafacturerInput.getText().equals(product.getManafacturer())&&
                            toolbar__ThumbnailInput.getText().equals(product.getThumbnail())&&
                            toolbar__DescriptionInput.getText().equals(product.getDescription()));
                }
        }
        } catch (IOException | NumberFormatException | ParseException e) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        return temp;
    }

    public void initProductTemplates(){
        products__Main.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        products__Scroll.setBorder(null);
        products__Scroll.setVerticalScrollBar(new ScrollBar());
        products__Scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        products__Scroll.getViewport().setBackground(Color.WHITE);
        products__Scroll.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, p);
    }

    public void setProductTemplates(List<Product> products){
        products__Main.removeAll();
        productTemplates.clear();
        products.forEach(product -> {
            ProductTemplate temp = new ProductTemplate(product);
            productTemplates.add(temp);
            products__Main.add(temp);
        });
        products__Main.repaint();
        products__Main.revalidate();
    }

    private void resetProductTemplates(){
        products__Scroll.getVerticalScrollBar().setValue(0);
        productTemplates.forEach(pt ->  pt.setVisible(true));
    }

    private void initSort(){
        sort__Main.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        sort__Scroll.setBorder(null);
        sort__Scroll.setVerticalScrollBar(new ScrollBar());
        sort__Scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        sort__Scroll.getViewport().setBackground(Color.WHITE);
        sort__Scroll.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, p);
    }

    public void setSort(List<Product> products){
        sort__Main.removeAll();
        products.forEach(product -> sort__Main.add(new ProductTemplate(product)));
        sort__Main.validate();
        sort__Main.repaint();
        this.repaint();
        this.revalidate();
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

    private void initBarChart(String legend, List<ModelChart> modelCharts){
        statistic__BarChart.addLegend(legend, new Color(245, 189, 135));
        modelCharts.forEach(mc -> statistic__BarChart.addData(mc));
    }

    @SuppressWarnings("deprecation")
    private void setBarChart(){
        statistic__BarChart.clearData();
        if (statistic__QuantityStatistic.getSelectedItem().toString().equals(EXPIRY)){
            Map<Integer, Integer> expStatistic = new HashMap<>();
            try {
                Services.getInstance().getProducts().forEach(product -> {
                    int key = product.getExpDate().getYear() + 1900;
                    if (expStatistic.containsKey(key)){
                        expStatistic.replace(key, expStatistic.get(key) + product.getQuantity());
                    }
                    else{
                        expStatistic.put(key, product.getQuantity());
                    }
                });
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }

            List<ModelChart> modelCharts = new ArrayList<>();
            for (Entry<Integer, Integer> entry : expStatistic.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                modelCharts.add(new ModelChart(key + "", new double[]{value}));
            }
            initBarChart(EXPIRY, modelCharts);
            statistic__BarChart.repaint();
            statistic__BarChart.revalidate();
        }
        if (statistic__QuantityStatistic.getSelectedItem().toString().equals(CATEGORY)){
            Map<String, Integer> categoryStatistic = new HashMap<>();
            try {
                Services.getInstance().getProducts().forEach(product -> {
                    String key = product.getCategory();
                    if (categoryStatistic.containsKey(key)){
                        categoryStatistic.replace(key, categoryStatistic.get(key) + product.getQuantity());
                    }
                    else{
                        categoryStatistic.put(key, product.getQuantity());
                    }
                });
            } catch (IOException ex ){
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            List<ModelChart> modelCharts = new ArrayList<>();
            for (Entry<String, Integer> entry : categoryStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                modelCharts.add(new ModelChart(key, new double[]{value}));
            }
            initBarChart(CATEGORY, modelCharts);
            statistic__BarChart.repaint();
            statistic__BarChart.revalidate();
        }
        if (statistic__QuantityStatistic.getSelectedItem().toString().equals(MANAFACTURER)){
            Map<String, Integer> manafacturerStatistic = new HashMap<>();
            try {
                Services.getInstance().getProducts().forEach(product -> {
                    String key = product.getManafacturer();
                    if (manafacturerStatistic.containsKey(key)){
                        manafacturerStatistic.replace(key, manafacturerStatistic.get(key) + product.getQuantity());
                    }
                    else{
                        manafacturerStatistic.put(key, product.getQuantity());
                    }
                });
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            List<ModelChart> modelCharts = new ArrayList<>();
            for (Entry<String, Integer> entry : manafacturerStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                modelCharts.add(new ModelChart(key, new double[]{value}));
            }
            initBarChart(MANAFACTURER, modelCharts);
            statistic__BarChart.repaint();
            statistic__BarChart.revalidate();
        }
    }

    private void setPieChart(){
        statistic__PieChart.clearData();
        statistic__PieChart.setChartType(PieChart.PeiChartType.DONUT_CHART);
        if (statistic__PercentStatistic.getSelectedItem().toString().equals(CATEGORY)) {
            Map<String, Integer> categoryStatistic = new HashMap<>();
            for (int i = 0; i < toolbar__CategoryInput.getItemCount(); i++) {
                String key = toolbar__CategoryInput.getItemAt(i);
                int value = 0;
                try {
                    value = Services.getInstance().filterByCategory(key).size();
                } catch (IOException  e) {
                    showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                }
                categoryStatistic.put(key, value);
            }
            int ci = 0;
            for (Entry<String, Integer> entry : categoryStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                statistic__PieChart.addData(new ModelPieChart(key, value, CHART_COLOR[ci]));
                ci++;
            }
            statistic__PieChart.repaint();
            statistic__PieChart.revalidate();
        }
        if (statistic__PercentStatistic.getSelectedItem().toString().equals(PRICE)) {
            Map<String, Integer> priceStatistic = new HashMap<>();
            try {
                priceStatistic.put(PRICE_RANGES[0], Services.getInstance().filterByPrice(new BigDecimal("0"), new BigDecimal("1000000")).size());
                priceStatistic.put(PRICE_RANGES[1], Services.getInstance().filterByPrice(new BigDecimal("1000000"), new BigDecimal("5000000")).size());
                priceStatistic.put(PRICE_RANGES[2], Services.getInstance().filterByPrice(new BigDecimal("5000000"), new BigDecimal("10000000")).size());
                priceStatistic.put(PRICE_RANGES[3], Services.getInstance().filterByPrice(new BigDecimal("10000000"), new BigDecimal("20000000")).size());
                priceStatistic.put(PRICE_RANGES[4], Services.getInstance().filterByPrice(new BigDecimal("20000000"), new BigDecimal("-12345")).size());
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            int ci = 0;
            for (Entry<String, Integer> entry : priceStatistic.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                statistic__PieChart.addData(new ModelPieChart(key, value, CHART_COLOR[ci]));
                ci++;
            }
            statistic__PieChart.repaint();
            statistic__PieChart.revalidate();
        }
    }
    private void setOveralStatistic(){
        int totalProducts = 0;
        try {
            totalProducts = Services.getInstance().getProducts().size();
        } catch (IOException e) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        int totalQuantity = 0;
        int totalCategory = toolbar__CategoryInput.getItemCount();
        Set<String> manafacturer = new HashSet<>();
        try {
            for (Product product: Services.getInstance().getProducts()){
                totalQuantity += product.getQuantity();
                manafacturer.add(product.getManafacturer());
            }
        } catch (IOException  e) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        int totalManafaturer = manafacturer.size();
        statistic__TotalProductsStatistic.setText(STATISTIC_HEADER + totalProducts + STATISTIC_FOOTER);
        statistic__TotalQuantityStatistic.setText(STATISTIC_HEADER + totalQuantity + STATISTIC_FOOTER);
        statistic__CategoryStatistic.setText(STATISTIC_HEADER + totalCategory + STATISTIC_FOOTER);
        statistic__ManafacturerStatistic.setText(STATISTIC_HEADER + totalManafaturer + STATISTIC_FOOTER);
    }

    public void initStatistic(){
        statistic__Scroll.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, p);
        statistic__Scroll.setBorder(new LineBorder(new Color(246,251,249), 1));
        statistic__Scroll.setVerticalScrollBar(new ScrollBar());
        statistic__Scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        statistic__Scroll.getViewport().setBackground(Color.WHITE);
        statistic__Scroll.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, p);
        statistic__QuantityStatistic.setBackground(Color.WHITE);
        statistic__PercentStatistic.setBackground(Color.WHITE);
    }

    public void setStatistic(){
        statistic__QuantityStatistic.setSelectedIndex(0);
        statistic__PercentStatistic.setSelectedIndex(0);
        setOveralStatistic();
        setBarChart();
        setPieChart();
    }

    private void resetUserEdit(){
        usersTable.clearSelection();
        information__UsernameInput.setText("");
        information__RegisterDateInput.setText("");
        information__WriteInput.setSelected(false);
        information__ReadInput.setSelected(false);
        information__WriteInput.setEnabled(false);
        information__ReadInput.setEnabled(false);
        information__ButtonEdit.setEnabled(false);
        information__ButtonDelete.setEnabled(false);
        authorization__Information.repaint();
        authorization__Information.revalidate();
    }

    private void resetUserFilter(){
        usersTable.clearSelection();
        filter__UsernameInput.setText("");
        filter__RegisterDateMin.setText("");
        filter__RegisterDateMax.setText("");
        filter__WriteInput.setSelected(false);
        filter__ReadInput.setSelected(false);
        authorization__Filter.repaint();
        authorization__Filter.revalidate();
    }

    private boolean isUserModify(){
        boolean temp = false;
        try {
            if (Services.getInstance().getSelectedUser() != null){
                User user = Services.getInstance().getSelectedUser();
                temp = !(information__ReadInput.isSelected() == user.isRead() &&
                        information__WriteInput.isSelected() == user.isWrite());
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        return temp;
    }

    public void signOut(){
        CardLayout appCardLayout = (CardLayout) main__Pages.getParent().getParent().getParent().getLayout();
        appCardLayout.show(main__Pages.getParent().getParent().getParent(), "app__SignIn");
        CardLayout userCardLayout = (CardLayout) main__Pages.getLayout();
        userCardLayout.show(main__Pages, CARD_HOME);
        try {
            appController.setProductsTable(productsTable, Services.getInstance().getProducts());
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        resetProductTemplates();
        products__Scroll.getVerticalScrollBar().setValue(0);
        setStatistic();
        resetToolbar(true);
        resetSearchField(true);
        try {
            Services.getInstance().setCurrentUser(null);
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        accessPage = PAGES_HOME;
        this.repaint();
        this.revalidate();
    }

    private String IdGenerator(String category) throws IOException{
        if (!Services.getInstance().getSelectedProduct().getCategory().equals(toolbar__CategoryInput.getSelectedItem().toString())){
            String id = null;
            String prefix = null;
            String suffix;
            switch (category) {
                case "Điện thoại" ->{
                    prefix = "CP";
                }
                case "Máy tính" ->{
                    prefix = "PC";
                }
                case "Nội thất" ->{
                    prefix = "IT";
                }
                case "Gia dụng" ->{
                    prefix = "AL";
                }
                case "Trang trí" ->{
                    prefix = "DC";
                }
                case "Thời trang" ->{
                    prefix = "FS";
                }
                case "Thể thao" ->{
                    prefix = "SP";
                }
                case "Mỹ phẩm" ->{
                    prefix = "CM";
                }
                case "Thực phẩm" ->{
                    prefix = "FD";
                }
                default ->{
                }
            }
            try {
                Product product = Services.getInstance().filterByCategory(category).stream()
                        .max(java.util.Comparator.comparing(Product::getId))
                        .orElseThrow();
                suffix = product.getId().substring(2);
                suffix = String.format("%03d", Integer.parseInt(suffix) + 1);
                id = prefix + suffix;
            } catch (IOException ex) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            return id;
        }
        else{
            return Services.getInstance().getSelectedProduct().getId();
        }
    }
    
    private void initGeneral(){
        p.setBackground(new Color(246,251,249,0));
        toolbar__ThumbnailInput.setEditable(false);
        navbar__ButtonAuthorization.setName("Navbar__ButtonAuthorization");
        toolbar__ButtonFileChooser.setName("Toolbar__ButtonFileChooser");
        toolbar__SearchStringInput.setName("Toolbar__SearchStringInput");
        toolbar__SearchInput.setName("Toolbar__SearchInput");
        toolbar__SearchMinInput.setName("Toolbar__SearchMinInput");
        toolbar__SearchMaxInput.setName("Toolbar__SearchMaxInput");
        toolbar__SortInput.setName("Toolbar__SortInput");
        UserController.getInstance();
        UserController.setComponents(toolbar, navbar);
        addProductSelectionListener(new ProductSelectionListener());
        addUserSelectionListener(new UserSelectionListener());
    }

    private Main(){
        initComponents();
        initGeneral();
        initToolbar();
        initSearchField();
        initProductTemplates();
        initSort();
        initStatistic();
        accessPage = PAGES_HOME;
        this.appController = new Controller(home__Table);
        this.adminController = new Controller(authorization__Table);
    }

    public static Main getInstance(){
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

        toolbar = new javax.swing.JPanel();
        toolbar__NameTitle = new javax.swing.JLabel();
        toolbar__NameInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        toolbar__CategoryTitle = new javax.swing.JLabel();
        toolbar__CategoryInput = new javax.swing.JComboBox<>();
        toolbar__QuantityTitle = new javax.swing.JLabel();
        toolbar__QuantityInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        toolbar__PriceTitle = new javax.swing.JLabel();
        toolbar__PriceInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        toolbar__ExpiryTitle = new javax.swing.JLabel();
        toolbar__ExpiryInput = new com.toedter.calendar.JDateChooser();
        toolbar__ManafacturerTitle = new javax.swing.JLabel();
        toolbar__ManafacturerInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        toolbar__ButtonAdd = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        toolbar__ButtonEdit = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        toolbar__ButtonDelete = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        toolbar__Title = new javax.swing.JLabel();
        toolbar__ButtonReset = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        toolbar__SortTitle = new javax.swing.JLabel();
        toolbar__SortInput = new javax.swing.JComboBox<>();
        toolbar__ButtonSort = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        toolbar__SearchTitle = new javax.swing.JLabel();
        toolbar__SearchInput = new javax.swing.JComboBox<>();
        toolbar__SearchStringInput = new hvan.qlkh.utils.TextField(0, new Color(0, 0, 0, 200));
        toolbar__ButtonSearch = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        toolbar__SeparatorBottom = new javax.swing.JPanel();
        toolbar__SeparatorTop = new javax.swing.JPanel();
        toolbar__SearchMinInput = new hvan.qlkh.utils.TextField(0, new Color(0, 0, 0, 200));
        toolbar__SearchMaxInput = new hvan.qlkh.utils.TextField(0, new Color(0, 0, 0, 200));
        toolbar__Decorate = new javax.swing.JLabel();
        toolbar__Alert = new javax.swing.JLabel();
        toolbar__ButtonFileChooser = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        toolbar__ThumbnailInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        toolbar__ThumbnailTitle = new javax.swing.JLabel();
        toolbar__DescriptionTitle = new javax.swing.JLabel();
        toolbar__DescriptionScroll = new javax.swing.JScrollPane();
        toolbar__DescriptionInput = new javax.swing.JTextPane();
        toolbar__ButtonResetSearch = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        main = new javax.swing.JPanel();
        main__Pages = new Panel(40);
        pages__Home = new Panel(40);
        home__Table = new Panel(40);
        pages__Products = new Panel(40);
        products__Scroll = new javax.swing.JScrollPane();
        products__Main = new javax.swing.JPanel();
        pages__Statistic = new Panel(40);
        statistic__Scroll = new javax.swing.JScrollPane();
        statistic__Main = new javax.swing.JPanel();
        statistic__BarChart = new hvan.qlkh.chart.Chart();
        statistic__PieChart = new hvan.qlkh.chart.PieChart();
        statistic__Menu = new hvan.qlkh.utils.Panel(40);
        statistic__QuantityStatistic = new javax.swing.JComboBox<>();
        statistic__QuantityStatisticTitle = new javax.swing.JLabel();
        statistic__PercentStatisticTitle = new javax.swing.JLabel();
        statistic__PercentStatistic = new javax.swing.JComboBox<>();
        statistic__ButtonPercent = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        statistic__ButtonQuantity = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        statistic__Category = new hvan.qlkh.utils.Panel(40);
        statistic__CategoryThumb = new javax.swing.JLabel();
        statistic__CategoryTitle = new javax.swing.JLabel();
        statistic__CategoryStatistic = new javax.swing.JLabel();
        statistic__Manafacturer = new hvan.qlkh.utils.Panel(40);
        statistic__ManafacturerThumb = new javax.swing.JLabel();
        statistic__ManafacturerTitle = new javax.swing.JLabel();
        statistic__ManafacturerStatistic = new javax.swing.JLabel();
        statistic__TotalProducts = new hvan.qlkh.utils.Panel(40);
        statistic__TotalProductsThumb = new javax.swing.JLabel();
        statistic__TotalProductsTitle = new javax.swing.JLabel();
        statistic__TotalProductsStatistic = new javax.swing.JLabel();
        statistic__TotalQuanity = new hvan.qlkh.utils.Panel(40);
        statistic__TotalQuantityThumb = new javax.swing.JLabel();
        statistic__TotalQuantityTitle = new javax.swing.JLabel();
        statistic__TotalQuantityStatistic = new javax.swing.JLabel();
        pages__Sort = new Panel(40);
        sort__Scroll = new javax.swing.JScrollPane();
        sort__Main = new javax.swing.JPanel();
        pages__Authorization = new Panel(40);
        authorization__Table = new Panel(40);
        authorization__Information = new Panel(40);
        information__UsernameInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        information__UsernameTitle = new javax.swing.JLabel();
        information__RegisterDate = new javax.swing.JLabel();
        information__RegisterDateInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        information__ReadTitle = new javax.swing.JLabel();
        information__ReadInput = new javax.swing.JCheckBox();
        information__WriteTitle = new javax.swing.JLabel();
        information__WriteInput = new javax.swing.JCheckBox();
        information__ButtonDelete = new hvan.qlkh.utils.Button(35, new Color(255, 86, 86), new Color(195, 52, 52), new Color(149, 39, 39));
        information__ButtonEdit = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        information__ButtonReset = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        information__Title = new javax.swing.JLabel();
        authorization__Filter = new Panel(40);
        filter__UsernameTitle = new javax.swing.JLabel();
        filter__RegisterDateTitle = new javax.swing.JLabel();
        filter__ButtonFilter = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        filter__UsernameInput = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        filter__RegisterDateMin = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        filter__RegisterDateMax = new hvan.qlkh.utils.TextField(0, Color.BLACK);
        filter__Decorate = new javax.swing.JLabel();
        filter__ReadTitle = new javax.swing.JLabel();
        filter__ReadInput = new javax.swing.JCheckBox();
        filter__WriteTitle = new javax.swing.JLabel();
        filter__WriteInput = new javax.swing.JCheckBox();
        filter_Title = new javax.swing.JLabel();
        filter__ButtonReset = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        navbar = new hvan.qlkh.utils.Panel(40);
        navbar__ButtonHome = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        navbar__ButtonProducts = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        navbar__ButtonStatistic = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));
        navbar__ButtonSignOut = new hvan.qlkh.utils.Button(35, new Color(255, 86, 86), new Color(195, 52, 52), new Color(149, 39, 39));
        navbar__ButtonAuthorization = new hvan.qlkh.utils.Button(35, new Color(76, 175, 79, 200), new Color(76, 175, 79), new Color(56, 142, 59));

        setBackground(new Color(76, 175, 79, 200));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        toolbar.setBackground(new java.awt.Color(244, 244, 244));
        toolbar.setPreferredSize(new java.awt.Dimension(380, 800));

        toolbar__NameTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tên sản phẩm</div></html>");
        toolbar__NameTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        toolbar__NameInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        toolbar__NameInput.setMinimumSize(new java.awt.Dimension(26, 26));
        toolbar__NameInput.setPreferredSize(new java.awt.Dimension(230, 35));
        toolbar__NameInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                toolbar__NameInputCaretUpdate(evt);
            }
        });

        toolbar__CategoryTitle.setText("<html><div style=\"width: 60px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Danh mục</div></html>");
        toolbar__CategoryTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        toolbar__CategoryInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        toolbar__CategoryInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Điện thoại", "Máy tính", "Nội thất", "Gia dụng", "Trang trí", "Thời trang", "Thể thao", "Mỹ phẩm", "Thực phẩm" }));
        toolbar__CategoryInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar__CategoryInput.setPreferredSize(new java.awt.Dimension(230, 35));

        toolbar__QuantityTitle.setText("<html><div style=\"width: 60px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 56px; word-wrap: break-word\">Số lượng</div></html>");
        toolbar__QuantityTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        toolbar__QuantityInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        toolbar__QuantityInput.setPreferredSize(new java.awt.Dimension(230, 35));
        toolbar__QuantityInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                toolbar__QuantityInputCaretUpdate(evt);
            }
        });

        toolbar__PriceTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\">Đơn giá</div></html>");
        toolbar__PriceTitle.setMinimumSize(new java.awt.Dimension(105, 25));
        toolbar__PriceTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        toolbar__PriceInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        toolbar__PriceInput.setPreferredSize(new java.awt.Dimension(230, 35));
        toolbar__PriceInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                toolbar__PriceInputCaretUpdate(evt);
            }
        });
        toolbar__PriceInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                toolbar__PriceInputKeyReleased(evt);
            }
        });

        toolbar__ExpiryTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Hạn sử dụng</div></html>");
        toolbar__ExpiryTitle.setMinimumSize(new java.awt.Dimension(105, 20));
        toolbar__ExpiryTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        toolbar__ExpiryInput.setBackground(new java.awt.Color(255, 255, 255));
        toolbar__ExpiryInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar__ExpiryInput.setOpaque(false);
        toolbar__ExpiryInput.setPreferredSize(new java.awt.Dimension(230, 35));

        toolbar__ManafacturerTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Nhà sản xuất</div></html>");
        toolbar__ManafacturerTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        toolbar__ManafacturerInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        toolbar__ManafacturerInput.setPreferredSize(new java.awt.Dimension(230, 35));
        toolbar__ManafacturerInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                toolbar__ManafacturerInputCaretUpdate(evt);
            }
        });

        toolbar__ButtonAdd.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonAdd.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Thêm</div></html>");
        toolbar__ButtonAdd.setBorder(null);
        toolbar__ButtonAdd.setBorderPainted(false);
        toolbar__ButtonAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonAddMouseClicked(evt);
            }
        });

        toolbar__ButtonEdit.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonEdit.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Sửa</div></html>");
        toolbar__ButtonEdit.setBorder(null);
        toolbar__ButtonEdit.setBorderPainted(false);
        toolbar__ButtonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonEditMouseClicked(evt);
            }
        });

        toolbar__ButtonDelete.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonDelete.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Xóa</div></html>");
        toolbar__ButtonDelete.setBorder(null);
        toolbar__ButtonDelete.setBorderPainted(false);
        toolbar__ButtonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonDeleteMouseClicked(evt);
            }
        });

        toolbar__Title.setText("<html><div style=\"width: 290px; text-align: center; color: rgba(76, 175, 79, 0.8) ; font-size: 20px; font-family: Karla; font-weight: 500; line-height: 20px; word-wrap: break-word\">Quản lý kho hàng</div></html>");
        toolbar__Title.setPreferredSize(new java.awt.Dimension(380, 35));

        toolbar__ButtonReset.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonReset.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        toolbar__ButtonReset.setBorder(null);
        toolbar__ButtonReset.setBorderPainted(false);
        toolbar__ButtonReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonResetMouseClicked(evt);
            }
        });

        toolbar__SortTitle.setText("<html><div style=\"width: 85px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Sắp xếp theo:</div></html>");
        toolbar__SortTitle.setPreferredSize(new java.awt.Dimension(340, 30));

        toolbar__SortInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        toolbar__SortInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định", "Mã số", "Tên sản phẩm", "Số lượng tăng", "Số lượng giảm", "Đơn giá tăng", "Đơn giá giảm", "Hạn sử dụng" }));
        toolbar__SortInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar__SortInput.setPreferredSize(new java.awt.Dimension(230, 35));

        toolbar__ButtonSort.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonSort.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Áp dụng</div></html>");
        toolbar__ButtonSort.setBorder(null);
        toolbar__ButtonSort.setBorderPainted(false);
        toolbar__ButtonSort.setPreferredSize(new java.awt.Dimension(100, 35));
        toolbar__ButtonSort.setRolloverEnabled(false);
        toolbar__ButtonSort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonSortMouseClicked(evt);
            }
        });

        toolbar__SearchTitle.setText("<html><div style=\"width: 85px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tìm kiếm theo:</div></html>");
        toolbar__SearchTitle.setPreferredSize(new java.awt.Dimension(100, 35));

        toolbar__SearchInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        toolbar__SearchInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã số", "Tên sản phẩm", "Danh mục", "Đơn giá", "Hạn sử dụng", "Nhà sản xuất" }));
        toolbar__SearchInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar__SearchInput.setPreferredSize(new java.awt.Dimension(230, 35));
        toolbar__SearchInput.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                toolbar__SearchInputItemStateChanged(evt);
            }
        });

        toolbar__SearchStringInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        toolbar__SearchStringInput.setPreferredSize(new java.awt.Dimension(340, 35));
        toolbar__SearchStringInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                toolbar__SearchStringInputFocusGained(evt);
            }
        });
        toolbar__SearchStringInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                toolbar__SearchStringInputKeyPressed(evt);
            }
        });

        toolbar__ButtonSearch.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonSearch.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Tìm kiếm</div></html>");
        toolbar__ButtonSearch.setBorder(null);
        toolbar__ButtonSearch.setBorderPainted(false);
        toolbar__ButtonSearch.setPreferredSize(new java.awt.Dimension(100, 35));
        toolbar__ButtonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonSearchMouseClicked(evt);
            }
        });

        toolbar__SeparatorBottom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar__SeparatorBottom.setPreferredSize(new java.awt.Dimension(380, 1));

        javax.swing.GroupLayout toolbar__SeparatorBottomLayout = new javax.swing.GroupLayout(toolbar__SeparatorBottom);
        toolbar__SeparatorBottom.setLayout(toolbar__SeparatorBottomLayout);
        toolbar__SeparatorBottomLayout.setHorizontalGroup(
            toolbar__SeparatorBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        toolbar__SeparatorBottomLayout.setVerticalGroup(
            toolbar__SeparatorBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        toolbar__SeparatorTop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar__SeparatorTop.setPreferredSize(new java.awt.Dimension(380, 1));

        javax.swing.GroupLayout toolbar__SeparatorTopLayout = new javax.swing.GroupLayout(toolbar__SeparatorTop);
        toolbar__SeparatorTop.setLayout(toolbar__SeparatorTopLayout);
        toolbar__SeparatorTopLayout.setHorizontalGroup(
            toolbar__SeparatorTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        toolbar__SeparatorTopLayout.setVerticalGroup(
            toolbar__SeparatorTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        toolbar__SearchMinInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        toolbar__SearchMinInput.setPreferredSize(new java.awt.Dimension(135, 35));
        toolbar__SearchMinInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                toolbar__SearchMinInputFocusGained(evt);
            }
        });
        toolbar__SearchMinInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                toolbar__SearchMinInputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                toolbar__SearchMinInputKeyReleased(evt);
            }
        });

        toolbar__SearchMaxInput.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        toolbar__SearchMaxInput.setPreferredSize(new java.awt.Dimension(135, 35));
        toolbar__SearchMaxInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                toolbar__SearchMaxInputFocusGained(evt);
            }
        });
        toolbar__SearchMaxInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                toolbar__SearchMaxInputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                toolbar__SearchMaxInputKeyReleased(evt);
            }
        });

        toolbar__Decorate.setText("<html><div style=\"width: 85px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 12px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">đến</div></html>");
        toolbar__Decorate.setPreferredSize(new java.awt.Dimension(30, 35));

        toolbar__Alert.setText("<html><div style=\"text-align: center; width: 354px; color: red; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 16px; word-wrap: break-word\"></div></html>");

        toolbar__ButtonFileChooser.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonFileChooser.setText("<html><div style=\"text-align: center; color:white; font-size: 10px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Chọn</div></html>");
        toolbar__ButtonFileChooser.setBorder(null);
        toolbar__ButtonFileChooser.setBorderPainted(false);
        toolbar__ButtonFileChooser.setPreferredSize(new java.awt.Dimension(55, 35));
        toolbar__ButtonFileChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonFileChooserMouseClicked(evt);
            }
        });

        toolbar__ThumbnailInput.setEditable(false);
        toolbar__ThumbnailInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        toolbar__ThumbnailInput.setPreferredSize(new java.awt.Dimension(170, 35));

        toolbar__ThumbnailTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Ảnh minh họa</div></html>");
        toolbar__ThumbnailTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        toolbar__DescriptionTitle.setText("<html><div style=\"width: 90px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Mô tả</div></html>");
        toolbar__DescriptionTitle.setPreferredSize(new java.awt.Dimension(340, 25));

        toolbar__DescriptionScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        toolbar__DescriptionInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        toolbar__DescriptionScroll.setViewportView(toolbar__DescriptionInput);

        toolbar__ButtonResetSearch.setBackground(new Color(76, 175, 79, 200));
        toolbar__ButtonResetSearch.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        toolbar__ButtonResetSearch.setBorder(null);
        toolbar__ButtonResetSearch.setBorderPainted(false);
        toolbar__ButtonResetSearch.setPreferredSize(new java.awt.Dimension(100, 35));
        toolbar__ButtonResetSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbar__ButtonResetSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout toolbarLayout = new javax.swing.GroupLayout(toolbar);
        toolbar.setLayout(toolbarLayout);
        toolbarLayout.setHorizontalGroup(
            toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolbarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(toolbarLayout.createSequentialGroup()
                        .addComponent(toolbar__DescriptionTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(toolbarLayout.createSequentialGroup()
                        .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(toolbar__SearchStringInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(toolbarLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(toolbar__ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(toolbar__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(toolbar__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(toolbar__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(toolbar__SortTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(toolbarLayout.createSequentialGroup()
                                    .addComponent(toolbar__SortInput, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(toolbar__ButtonSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolbarLayout.createSequentialGroup()
                                            .addComponent(toolbar__ThumbnailTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(5, 5, 5)
                                            .addComponent(toolbar__ThumbnailInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(70, 70, 70))
                                        .addComponent(toolbar__DescriptionScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolbarLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(toolbar__Alert, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(toolbarLayout.createSequentialGroup()
                                            .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(toolbar__ExpiryTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(5, 5, 5)
                                            .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(toolbar__ExpiryInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__ManafacturerInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(toolbarLayout.createSequentialGroup()
                                            .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(toolbar__CategoryTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__NameTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__PriceTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__QuantityTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(5, 5, 5)
                                            .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(toolbar__CategoryInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__QuantityInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__NameInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(toolbar__PriceInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(toolbarLayout.createSequentialGroup()
                                            .addComponent(toolbar__SearchTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(toolbar__SearchInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(toolbarLayout.createSequentialGroup()
                                            .addComponent(toolbar__SearchMinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(20, 20, 20)
                                            .addComponent(toolbar__Decorate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(20, 20, 20)
                                            .addComponent(toolbar__SearchMaxInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(toolbar__ButtonFileChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(10, 10, 10))))
            .addGroup(toolbarLayout.createSequentialGroup()
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolbar__SeparatorBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__SeparatorTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(toolbarLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(toolbar__ButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(toolbar__ButtonResetSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(toolbar__Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        toolbarLayout.setVerticalGroup(
            toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolbarLayout.createSequentialGroup()
                .addComponent(toolbar__Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(toolbar__SeparatorTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__NameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__NameTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__CategoryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__CategoryInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(toolbar__QuantityTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__QuantityInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__PriceTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__PriceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolbar__ExpiryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ExpiryInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolbar__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ManafacturerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__ThumbnailInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ThumbnailTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ButtonFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(toolbar__DescriptionTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(toolbar__DescriptionScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(toolbar__Alert, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(toolbar__SeparatorBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(toolbar__SortTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__ButtonSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__SortInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__SearchTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__SearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(toolbar__SearchStringInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__SearchMinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__SearchMaxInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__Decorate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toolbar__ButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toolbar__ButtonResetSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        main.setBackground(new Color(76, 175, 79, 200));
        main.setPreferredSize(new java.awt.Dimension(800, 800));

        main__Pages.setBackground(new java.awt.Color(246, 251, 249));
        main__Pages.setOpaque(false);
        main__Pages.setPreferredSize(new java.awt.Dimension(770, 675));
        main__Pages.setLayout(new java.awt.CardLayout());

        pages__Home.setBackground(new java.awt.Color(246, 251, 249));
        pages__Home.setPreferredSize(new java.awt.Dimension(770, 690));

        home__Table.setBackground(new java.awt.Color(246, 251, 249));
        home__Table.setPreferredSize(new java.awt.Dimension(755, 640));
        home__Table.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout pages__HomeLayout = new javax.swing.GroupLayout(pages__Home);
        pages__Home.setLayout(pages__HomeLayout);
        pages__HomeLayout.setHorizontalGroup(
            pages__HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__HomeLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(home__Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        pages__HomeLayout.setVerticalGroup(
            pages__HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pages__HomeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(home__Table, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        main__Pages.add(pages__Home, "pages__Home");

        pages__Products.setBackground(new java.awt.Color(246, 251, 249));
        pages__Products.setPreferredSize(new java.awt.Dimension(770, 690));

        products__Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        products__Main.setBackground(new java.awt.Color(246, 251, 249));
        products__Main.setLayout(new java.awt.GridLayout(5, 0));
        products__Scroll.setViewportView(products__Main);

        javax.swing.GroupLayout pages__ProductsLayout = new javax.swing.GroupLayout(pages__Products);
        pages__Products.setLayout(pages__ProductsLayout);
        pages__ProductsLayout.setHorizontalGroup(
            pages__ProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__ProductsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(products__Scroll)
                .addGap(15, 15, 15))
        );
        pages__ProductsLayout.setVerticalGroup(
            pages__ProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__ProductsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(products__Scroll)
                .addGap(15, 15, 15))
        );

        main__Pages.add(pages__Products, "pages__Products");

        pages__Statistic.setBackground(new java.awt.Color(246, 251, 249));
        pages__Statistic.setPreferredSize(new java.awt.Dimension(770, 690));

        statistic__Scroll.setBackground(new java.awt.Color(246, 251, 249));
        statistic__Scroll.setBorder(null);
        statistic__Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        statistic__Scroll.setPreferredSize(new java.awt.Dimension(730, 690));

        statistic__Main.setBackground(new java.awt.Color(246, 251, 249));
        statistic__Main.setPreferredSize(new java.awt.Dimension(715, 650));

        statistic__BarChart.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        statistic__BarChart.setPreferredSize(new java.awt.Dimension(600, 320));

        statistic__PieChart.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        statistic__PieChart.setPreferredSize(new java.awt.Dimension(325, 325));

        statistic__Menu.setBackground(new java.awt.Color(255, 255, 255));
        statistic__Menu.setPreferredSize(new java.awt.Dimension(390, 200));

        statistic__QuantityStatistic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Danh mục", "Nhà sản xuất", "Hạn sử dụng" }));
        statistic__QuantityStatistic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        statistic__QuantityStatistic.setPreferredSize(new java.awt.Dimension(225, 35));

        statistic__QuantityStatisticTitle.setText("<html><div style=\"width: 300px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Thống kê số lượng sản phẩm theo:</div></html>");
        statistic__QuantityStatisticTitle.setPreferredSize(new java.awt.Dimension(240, 30));

        statistic__PercentStatisticTitle.setText("<html><div style=\"width: 300px; text-align: left; color:rgba(33, 43, 39, 0.7); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Thống kê tỉ lệ sản phẩm theo:</div></html>");
        statistic__PercentStatisticTitle.setPreferredSize(new java.awt.Dimension(240, 30));

        statistic__PercentStatistic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Danh mục", "Đơn giá" }));
        statistic__PercentStatistic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        statistic__PercentStatistic.setPreferredSize(new java.awt.Dimension(225, 35));

        statistic__ButtonPercent.setBackground(new Color(76, 175, 79, 200));
        statistic__ButtonPercent.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Áp dụng</div></html>");
        statistic__ButtonPercent.setPreferredSize(new java.awt.Dimension(100, 35));
        statistic__ButtonPercent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statistic__ButtonPercentMouseClicked(evt);
            }
        });

        statistic__ButtonQuantity.setBackground(new Color(76, 175, 79, 200));
        statistic__ButtonQuantity.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Áp dụng</div></html>");
        statistic__ButtonQuantity.setPreferredSize(new java.awt.Dimension(100, 35));
        statistic__ButtonQuantity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statistic__ButtonQuantityMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout statistic__MenuLayout = new javax.swing.GroupLayout(statistic__Menu);
        statistic__Menu.setLayout(statistic__MenuLayout);
        statistic__MenuLayout.setHorizontalGroup(
            statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__MenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statistic__MenuLayout.createSequentialGroup()
                        .addComponent(statistic__QuantityStatistic, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(statistic__ButtonQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statistic__MenuLayout.createSequentialGroup()
                        .addGroup(statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statistic__PercentStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statistic__QuantityStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statistic__MenuLayout.createSequentialGroup()
                        .addComponent(statistic__PercentStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(statistic__ButtonPercent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        statistic__MenuLayout.setVerticalGroup(
            statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__MenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(statistic__QuantityStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statistic__ButtonQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statistic__QuantityStatistic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(statistic__PercentStatisticTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(statistic__MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statistic__ButtonPercent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statistic__PercentStatistic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        statistic__Category.setBackground(new java.awt.Color(255, 255, 255));
        statistic__Category.setPreferredSize(new java.awt.Dimension(100, 140));

        statistic__CategoryThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-opened-folder-80.png"))); // NOI18N

        statistic__CategoryTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Tổng số danh mục:</div></html>");

        statistic__CategoryStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\"></div></html>");

        javax.swing.GroupLayout statistic__CategoryLayout = new javax.swing.GroupLayout(statistic__Category);
        statistic__Category.setLayout(statistic__CategoryLayout);
        statistic__CategoryLayout.setHorizontalGroup(
            statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__CategoryLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statistic__CategoryThumb)
                    .addGroup(statistic__CategoryLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statistic__CategoryStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statistic__CategoryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        statistic__CategoryLayout.setVerticalGroup(
            statistic__CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__CategoryLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(statistic__CategoryThumb)
                .addGap(5, 5, 5)
                .addComponent(statistic__CategoryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(statistic__CategoryStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        statistic__Manafacturer.setBackground(new java.awt.Color(255, 255, 255));
        statistic__Manafacturer.setPreferredSize(new java.awt.Dimension(100, 140));

        statistic__ManafacturerThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-manufacture-80.png"))); // NOI18N

        statistic__ManafacturerTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Tổng số nhà sản xuất:</div></html>");

        statistic__ManafacturerStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\"></div></html>");

        javax.swing.GroupLayout statistic__ManafacturerLayout = new javax.swing.GroupLayout(statistic__Manafacturer);
        statistic__Manafacturer.setLayout(statistic__ManafacturerLayout);
        statistic__ManafacturerLayout.setHorizontalGroup(
            statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statistic__ManafacturerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statistic__ManafacturerThumb)
                    .addGroup(statistic__ManafacturerLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statistic__ManafacturerStatistic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statistic__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        statistic__ManafacturerLayout.setVerticalGroup(
            statistic__ManafacturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__ManafacturerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(statistic__ManafacturerThumb)
                .addGap(5, 5, 5)
                .addComponent(statistic__ManafacturerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(statistic__ManafacturerStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        statistic__TotalProducts.setBackground(new java.awt.Color(255, 255, 255));
        statistic__TotalProducts.setPreferredSize(new java.awt.Dimension(185, 100));

        statistic__TotalProductsThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-product-80.png"))); // NOI18N

        statistic__TotalProductsTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Tổng số sản phẩm:</div></html>");

        statistic__TotalProductsStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\"></div></html>");

        javax.swing.GroupLayout statistic__TotalProductsLayout = new javax.swing.GroupLayout(statistic__TotalProducts);
        statistic__TotalProducts.setLayout(statistic__TotalProductsLayout);
        statistic__TotalProductsLayout.setHorizontalGroup(
            statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__TotalProductsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(statistic__TotalProductsThumb)
                .addGroup(statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statistic__TotalProductsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(statistic__TotalProductsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(statistic__TotalProductsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(statistic__TotalProductsStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        statistic__TotalProductsLayout.setVerticalGroup(
            statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__TotalProductsLayout.createSequentialGroup()
                .addGroup(statistic__TotalProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statistic__TotalProductsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(statistic__TotalProductsThumb))
                    .addGroup(statistic__TotalProductsLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(statistic__TotalProductsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(statistic__TotalProductsStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        statistic__TotalQuanity.setBackground(new java.awt.Color(255, 255, 255));
        statistic__TotalQuanity.setPreferredSize(new java.awt.Dimension(185, 100));

        statistic__TotalQuantityThumb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-bill-80.png"))); // NOI18N

        statistic__TotalQuantityTitle.setText("<html><div style=\"width: 55px; text-align: center; color:rgba(33, 43, 39, 0.7); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\">Số lượng sản phẩm:</div></html>");

        statistic__TotalQuantityStatistic.setText("<html><div style=\"width: 55px; text-align: center; color: red; font-size: 15px; font-family: Karla; font-weight: 500; line-height: 13px; word-wrap: break-word\"></div></html>");

        javax.swing.GroupLayout statistic__TotalQuanityLayout = new javax.swing.GroupLayout(statistic__TotalQuanity);
        statistic__TotalQuanity.setLayout(statistic__TotalQuanityLayout);
        statistic__TotalQuanityLayout.setHorizontalGroup(
            statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__TotalQuanityLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(statistic__TotalQuantityThumb)
                .addGap(12, 12, 12)
                .addGroup(statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statistic__TotalQuantityTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statistic__TotalQuantityStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        statistic__TotalQuanityLayout.setVerticalGroup(
            statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statistic__TotalQuanityLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(statistic__TotalQuanityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statistic__TotalQuantityThumb)
                    .addGroup(statistic__TotalQuanityLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(statistic__TotalQuantityTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(statistic__TotalQuantityStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout statistic__MainLayout = new javax.swing.GroupLayout(statistic__Main);
        statistic__Main.setLayout(statistic__MainLayout);
        statistic__MainLayout.setHorizontalGroup(
            statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__MainLayout.createSequentialGroup()
                .addGroup(statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statistic__MainLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(statistic__PieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(statistic__MainLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(statistic__Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(statistic__MainLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(statistic__TotalProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(statistic__TotalQuanity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(statistic__MainLayout.createSequentialGroup()
                        .addComponent(statistic__BarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addGroup(statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statistic__Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statistic__Manafacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );
        statistic__MainLayout.setVerticalGroup(
            statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic__MainLayout.createSequentialGroup()
                .addGroup(statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statistic__BarChart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(statistic__MainLayout.createSequentialGroup()
                        .addComponent(statistic__Category, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(statistic__Manafacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statistic__MainLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(statistic__MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statistic__TotalProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statistic__TotalQuanity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(statistic__Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(statistic__MainLayout.createSequentialGroup()
                        .addComponent(statistic__PieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5))))
        );

        statistic__Scroll.setViewportView(statistic__Main);

        javax.swing.GroupLayout pages__StatisticLayout = new javax.swing.GroupLayout(pages__Statistic);
        pages__Statistic.setLayout(pages__StatisticLayout);
        pages__StatisticLayout.setHorizontalGroup(
            pages__StatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__StatisticLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(statistic__Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        pages__StatisticLayout.setVerticalGroup(
            pages__StatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__StatisticLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(statistic__Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        main__Pages.add(pages__Statistic, "pages__Statistic");

        pages__Sort.setBackground(new java.awt.Color(246, 251, 249));
        pages__Sort.setPreferredSize(new java.awt.Dimension(770, 690));

        sort__Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        sort__Main.setBackground(new java.awt.Color(246, 251, 249));
        sort__Scroll.setViewportView(sort__Main);

        javax.swing.GroupLayout pages__SortLayout = new javax.swing.GroupLayout(pages__Sort);
        pages__Sort.setLayout(pages__SortLayout);
        pages__SortLayout.setHorizontalGroup(
            pages__SortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__SortLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(sort__Scroll)
                .addGap(15, 15, 15))
        );
        pages__SortLayout.setVerticalGroup(
            pages__SortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__SortLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(sort__Scroll)
                .addGap(15, 15, 15))
        );

        main__Pages.add(pages__Sort, "pages__Sort");

        pages__Authorization.setBackground(new java.awt.Color(246, 251, 249));
        pages__Authorization.setPreferredSize(new java.awt.Dimension(770, 690));

        authorization__Table.setBackground(new java.awt.Color(246, 251, 249));
        authorization__Table.setPreferredSize(new java.awt.Dimension(480, 650));

        javax.swing.GroupLayout authorization__TableLayout = new javax.swing.GroupLayout(authorization__Table);
        authorization__Table.setLayout(authorization__TableLayout);
        authorization__TableLayout.setHorizontalGroup(
            authorization__TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        authorization__TableLayout.setVerticalGroup(
            authorization__TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        authorization__Information.setBackground(new java.awt.Color(244, 244, 244));
        authorization__Information.setPreferredSize(new java.awt.Dimension(300, 300));

        information__UsernameInput.setEditable(false);
        information__UsernameInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        information__UsernameInput.setPreferredSize(new java.awt.Dimension(150, 35));

        information__UsernameTitle.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tên tài khoản:</div></html>");
        information__UsernameTitle.setPreferredSize(new java.awt.Dimension(100, 35));

        information__RegisterDate.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Ngày tạo:</div></html>");
        information__RegisterDate.setPreferredSize(new java.awt.Dimension(80, 35));

        information__RegisterDateInput.setEditable(false);
        information__RegisterDateInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        information__RegisterDateInput.setPreferredSize(new java.awt.Dimension(150, 35));

        information__ReadTitle.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Cho phép đọc:</div></html>");
        information__ReadTitle.setPreferredSize(new java.awt.Dimension(80, 35));

        information__ReadInput.setBackground(new java.awt.Color(255, 255, 255));
        information__ReadInput.setEnabled(false);
        information__ReadInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        information__ReadInput.setPreferredSize(new java.awt.Dimension(20, 35));
        information__ReadInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                information__ReadInputStateChanged(evt);
            }
        });

        information__WriteTitle.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Cho phép ghi:</div></html>");
        information__WriteTitle.setPreferredSize(new java.awt.Dimension(80, 35));

        information__WriteInput.setBackground(new java.awt.Color(255, 255, 255));
        information__WriteInput.setEnabled(false);
        information__WriteInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        information__WriteInput.setPreferredSize(new java.awt.Dimension(20, 35));
        information__WriteInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                information__WriteInputStateChanged(evt);
            }
        });

        information__ButtonDelete.setBackground(new java.awt.Color(255, 86, 86));
        information__ButtonDelete.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Xóa</div></html>");
        information__ButtonDelete.setBorder(null);
        information__ButtonDelete.setBorderPainted(false);
        information__ButtonDelete.setPreferredSize(new java.awt.Dimension(70, 35));
        information__ButtonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                information__ButtonDeleteMouseClicked(evt);
            }
        });

        information__ButtonEdit.setBackground(new Color(76, 175, 79, 200));
        information__ButtonEdit.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Sửa</div></html>");
        information__ButtonEdit.setBorder(null);
        information__ButtonEdit.setBorderPainted(false);
        information__ButtonEdit.setPreferredSize(new java.awt.Dimension(70, 35));
        information__ButtonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                information__ButtonEditMouseClicked(evt);
            }
        });

        information__ButtonReset.setBackground(new Color(76, 175, 79, 200));
        information__ButtonReset.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        information__ButtonReset.setBorder(null);
        information__ButtonReset.setBorderPainted(false);
        information__ButtonReset.setPreferredSize(new java.awt.Dimension(70, 35));
        information__ButtonReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                information__ButtonResetMouseClicked(evt);
            }
        });

        information__Title.setText("<html><div style=\"width: 125px; text-align: center; color:rgba(76, 175, 79, 0.8); font-size: 13px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Thông tin tài khoản</div></html>");
        information__Title.setPreferredSize(new java.awt.Dimension(160, 35));

        javax.swing.GroupLayout authorization__InformationLayout = new javax.swing.GroupLayout(authorization__Information);
        authorization__Information.setLayout(authorization__InformationLayout);
        authorization__InformationLayout.setHorizontalGroup(
            authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(authorization__InformationLayout.createSequentialGroup()
                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(authorization__InformationLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(authorization__InformationLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(information__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(information__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(information__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(authorization__InformationLayout.createSequentialGroup()
                                .addComponent(information__WriteTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(information__WriteInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(authorization__InformationLayout.createSequentialGroup()
                                .addComponent(information__ReadTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(information__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(authorization__InformationLayout.createSequentialGroup()
                                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(information__UsernameTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(information__RegisterDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(information__RegisterDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(information__UsernameInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(authorization__InformationLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(information__Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        authorization__InformationLayout.setVerticalGroup(
            authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(authorization__InformationLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(information__Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(information__UsernameTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(information__UsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(information__RegisterDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(information__RegisterDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(information__ReadTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(information__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(information__WriteTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(information__WriteInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(authorization__InformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(information__ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(information__ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(information__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        authorization__Filter.setBackground(new java.awt.Color(244, 244, 244));
        authorization__Filter.setPreferredSize(new java.awt.Dimension(300, 255));

        filter__UsernameTitle.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Tên tài khoản:</div></html>");
        filter__UsernameTitle.setPreferredSize(new java.awt.Dimension(100, 35));

        filter__RegisterDateTitle.setText("<html><div style=\"width: 75px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Ngày tạo:</div></html>");
        filter__RegisterDateTitle.setPreferredSize(new java.awt.Dimension(100, 35));

        filter__ButtonFilter.setBackground(new Color(76, 175, 79, 200));
        filter__ButtonFilter.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Lọc</div></html>");
        filter__ButtonFilter.setBorder(null);
        filter__ButtonFilter.setBorderPainted(false);
        filter__ButtonFilter.setPreferredSize(new java.awt.Dimension(70, 35));
        filter__ButtonFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filter__ButtonFilterMouseClicked(evt);
            }
        });

        filter__UsernameInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter__UsernameInput.setPreferredSize(new java.awt.Dimension(150, 35));
        filter__UsernameInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                filter__UsernameInputFocusGained(evt);
            }
        });
        filter__UsernameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                filter__UsernameInputKeyPressed(evt);
            }
        });

        filter__RegisterDateMin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter__RegisterDateMin.setPreferredSize(new java.awt.Dimension(100, 35));
        filter__RegisterDateMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                filter__RegisterDateMinFocusGained(evt);
            }
        });
        filter__RegisterDateMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                filter__RegisterDateMinKeyPressed(evt);
            }
        });

        filter__RegisterDateMax.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter__RegisterDateMax.setPreferredSize(new java.awt.Dimension(100, 35));
        filter__RegisterDateMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                filter__RegisterDateMaxFocusGained(evt);
            }
        });
        filter__RegisterDateMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                filter__RegisterDateMaxKeyPressed(evt);
            }
        });

        filter__Decorate.setText("<html><div style=\"width: 30px; text-align: center; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">đến</div></html>");
        filter__Decorate.setPreferredSize(new java.awt.Dimension(40, 35));

        filter__ReadTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Được phép đọc:</div></html>");
        filter__ReadTitle.setMinimumSize(new java.awt.Dimension(105, 35));
        filter__ReadTitle.setPreferredSize(new java.awt.Dimension(90, 35));

        filter__ReadInput.setBackground(new java.awt.Color(255, 255, 255));
        filter__ReadInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        filter__ReadInput.setPreferredSize(new java.awt.Dimension(20, 35));
        filter__ReadInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                filter__ReadInputStateChanged(evt);
            }
        });
        filter__ReadInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                filter__ReadInputKeyPressed(evt);
            }
        });

        filter__WriteTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 11px; font-family: Karla; font-weight: 400; line-height: 15px; word-wrap: break-word\">Được phép ghi:</div></html>");
        filter__WriteTitle.setPreferredSize(new java.awt.Dimension(105, 35));

        filter__WriteInput.setBackground(new java.awt.Color(255, 255, 255));
        filter__WriteInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        filter__WriteInput.setPreferredSize(new java.awt.Dimension(20, 35));
        filter__WriteInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                filter__WriteInputStateChanged(evt);
            }
        });
        filter__WriteInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                filter__WriteInputKeyPressed(evt);
            }
        });

        filter_Title.setText("<html><div style=\"width: 90px; text-align: center; color:rgba(76, 175, 79, 0.8); font-size: 13px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Lọc tài khoản</div></html>");
        filter_Title.setPreferredSize(new java.awt.Dimension(120, 35));

        filter__ButtonReset.setBackground(new Color(76, 175, 79, 200));
        filter__ButtonReset.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Hủy</div></html>");
        filter__ButtonReset.setBorder(null);
        filter__ButtonReset.setBorderPainted(false);
        filter__ButtonReset.setPreferredSize(new java.awt.Dimension(70, 35));
        filter__ButtonReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filter__ButtonResetMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout authorization__FilterLayout = new javax.swing.GroupLayout(authorization__Filter);
        authorization__Filter.setLayout(authorization__FilterLayout);
        authorization__FilterLayout.setHorizontalGroup(
            authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, authorization__FilterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filter__ButtonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(filter__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(authorization__FilterLayout.createSequentialGroup()
                .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(authorization__FilterLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filter__RegisterDateTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(authorization__FilterLayout.createSequentialGroup()
                                .addComponent(filter__UsernameTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(filter__UsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(authorization__FilterLayout.createSequentialGroup()
                                .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(filter__ReadTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(filter__WriteTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(filter__WriteInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(filter__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(authorization__FilterLayout.createSequentialGroup()
                                .addComponent(filter__RegisterDateMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(filter__Decorate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(filter__RegisterDateMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(authorization__FilterLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(filter_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        authorization__FilterLayout.setVerticalGroup(
            authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(authorization__FilterLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(filter_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filter__UsernameTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filter__UsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(authorization__FilterLayout.createSequentialGroup()
                        .addComponent(filter__RegisterDateTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filter__RegisterDateMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filter__Decorate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filter__RegisterDateMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(filter__ReadTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(filter__ReadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filter__WriteTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filter__WriteInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(authorization__FilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filter__ButtonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filter__ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout pages__AuthorizationLayout = new javax.swing.GroupLayout(pages__Authorization);
        pages__Authorization.setLayout(pages__AuthorizationLayout);
        pages__AuthorizationLayout.setHorizontalGroup(
            pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__AuthorizationLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(authorization__Table, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addGroup(pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pages__AuthorizationLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(authorization__Information, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pages__AuthorizationLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(authorization__Filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );
        pages__AuthorizationLayout.setVerticalGroup(
            pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pages__AuthorizationLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pages__AuthorizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pages__AuthorizationLayout.createSequentialGroup()
                        .addComponent(authorization__Information, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(authorization__Filter, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addComponent(authorization__Table, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        main__Pages.add(pages__Authorization, "pages__Authorization");

        navbar.setBackground(new java.awt.Color(246, 251, 249));
        navbar.setPreferredSize(new java.awt.Dimension(770, 70));

        navbar__ButtonHome.setBackground(new Color(76, 175, 79, 200));
        navbar__ButtonHome.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Trang chủ</div></html>");
        navbar__ButtonHome.setBorder(null);
        navbar__ButtonHome.setBorderPainted(false);
        navbar__ButtonHome.setPreferredSize(new java.awt.Dimension(110, 35));
        navbar__ButtonHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navbar__ButtonHomeMouseClicked(evt);
            }
        });

        navbar__ButtonProducts.setBackground(new Color(76, 175, 79, 200));
        navbar__ButtonProducts.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Kho hàng</div></html>");
        navbar__ButtonProducts.setBorder(null);
        navbar__ButtonProducts.setBorderPainted(false);
        navbar__ButtonProducts.setPreferredSize(new java.awt.Dimension(110, 35));
        navbar__ButtonProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navbar__ButtonProductsMouseClicked(evt);
            }
        });

        navbar__ButtonStatistic.setBackground(new Color(76, 175, 79, 200));
        navbar__ButtonStatistic.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Thống kê</div></html>");
        navbar__ButtonStatistic.setBorder(null);
        navbar__ButtonStatistic.setBorderPainted(false);
        navbar__ButtonStatistic.setPreferredSize(new java.awt.Dimension(110, 35));
        navbar__ButtonStatistic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navbar__ButtonStatisticMouseClicked(evt);
            }
        });

        navbar__ButtonSignOut.setBackground(new java.awt.Color(255, 86, 86));
        navbar__ButtonSignOut.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Đăng xuất</div></html>");
        navbar__ButtonSignOut.setBorder(null);
        navbar__ButtonSignOut.setBorderPainted(false);
        navbar__ButtonSignOut.setPreferredSize(new java.awt.Dimension(110, 35));
        navbar__ButtonSignOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navbar__ButtonSignOutMouseClicked(evt);
            }
        });

        navbar__ButtonAuthorization.setBackground(new Color(76, 175, 79, 200));
        navbar__ButtonAuthorization.setText("<html><div style=\"text-align: center; color:white; font-size: 12px; font-family: Karla; font-weight: 400; line-height: 160px; word-wrap: break-word\">Tài khoản</div></html>");
        navbar__ButtonAuthorization.setBorder(null);
        navbar__ButtonAuthorization.setBorderPainted(false);
        navbar__ButtonAuthorization.setPreferredSize(new java.awt.Dimension(110, 35));
        navbar__ButtonAuthorization.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navbar__ButtonAuthorizationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout navbarLayout = new javax.swing.GroupLayout(navbar);
        navbar.setLayout(navbarLayout);
        navbarLayout.setHorizontalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(navbar__ButtonHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(navbar__ButtonProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(navbar__ButtonStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(navbar__ButtonAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(navbar__ButtonSignOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navbar__ButtonHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(navbar__ButtonProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(navbar__ButtonStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(navbar__ButtonSignOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(navbar__ButtonAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        navbar__ButtonAuthorization.getAccessibleContext().setAccessibleName("Navbar__ButtonAuthorization");

        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(main);
        main.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(main__Pages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(main__Pages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
            .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
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

    private void toolbar__ButtonAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonAddMouseClicked
        try {
            if (Services.getInstance().getCurrentUser().isWrite()){
                if (toolbar__ButtonAdd.isEnabled()){
                    if (!nameCheck){
                        toolbar__Alert.setText(NAME_EXIST_ERROR_MESSAGE);
                    }
                    else{
                        if (!quantityCheck){
                            toolbar__Alert.setText(QUANTITY_FORMAT_ERROR_MESSAGE);
                        }
                        else {
                            if (!priceCheck){
                                toolbar__Alert.setText(PRICE_FORMAT_ERROR_MESSAGE);
                            }
                        }
                    }
                    if (toolbar__NameInput.getText().equals("")){
                        toolbar__Alert.setText(NAME_BLANK_ERROR_MESSAGE);
                    }
                    else{
                        if (toolbar__QuantityInput.getText().equals("")){
                            toolbar__Alert.setText(QUANTITY_BLANK_ERROR_MESSAGE);
                        }
                        else{
                            if(toolbar__PriceInput.getText().equals("")){
                                toolbar__Alert.setText(PRICE_BLANK_ERROR_MESSAGE);
                            }
                            else{
                                if (toolbar__ExpiryInput.getDate() == null){
                                    toolbar__Alert.setText(DATE_FORMAT_ERROR_MESSAGE);
                                }
                                else{
                                    if (toolbar__ManafacturerInput.getText().equals("")){
                                        toolbar__Alert.setText(MANAFACTURER_BLANK_ERROR_MESSAGE);
                                    }
                                    else{
                                        if (nameCheck == quantityCheck == priceCheck == manafacturerCheck){
                                            String path = toolbar__ThumbnailInput.getText();
                                            if (!path.equals("")){
                                                try {
                                                    File thumbnail = new File(IMAGES_DIR + path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".")) + ".png");
                                                    BufferedImage bufferedImage = ImageIO.read(new File(path));
                                                    ImageIO.write(Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 140, 180), "png", thumbnail);
                                                    toolbar__ThumbnailInput.setText(IMAGES_DIR + path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".")) + ".png");
                                                } catch (IOException ex) {
                                                    toolbar__ThumbnailInput.setText("");
                                                    showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                                                }
                                            }
                                            try {
                                                Product temp = new Product(IdGenerator((String) toolbar__CategoryInput.getSelectedItem()), toolbar__NameInput.getText(), (String) toolbar__CategoryInput.getSelectedItem(), Integer.parseInt(toolbar__QuantityInput.getText()), BigDecimalConverter.currencyParse(toolbar__PriceInput.getText()), toolbar__ExpiryInput.getDate(), toolbar__ManafacturerInput.getText());
                                                if(!toolbar__ThumbnailInput.getText().equals("") || toolbar__ThumbnailInput.getText() == null){
                                                    temp.setThumbnail(toolbar__ThumbnailInput.getText());
                                                }
                                                if (!toolbar__DescriptionInput.getText().equals("") || toolbar__DescriptionInput.getText() == null){
                                                    temp.setDescription(toolbar__DescriptionInput.getText());
                                                }
                                                Services.getInstance().create(temp);
                                                showMessage("Thêm mới sản phẩm thành công!", true);
                                            } catch (IOException | NumberFormatException | ParseException e) {
                                                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
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
                showMessage(AUTHORIZATION_ERROR_DIALOG_MESSAGE, false);
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__ButtonAddMouseClicked

    private void toolbar__NameInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_toolbar__NameInputCaretUpdate
        try {
            if (Services.getInstance().getCurrentUser().isWrite()) {
                if (toolbar__NameInput.getText().equals("")){
                    nameCheck = false;
                    toolbar__Alert.setText(NAME_BLANK_ERROR_MESSAGE);
                }
                else{
                    try {
                        if (Services.getInstance().getSelectedProduct() != null){
                            if (Services.getInstance().findByName(toolbar__NameInput.getText()) == null ||
                                    Services.getInstance().getSelectedProduct().getName().equals(toolbar__NameInput.getText())){
                                nameCheck = true;
                                toolbar__Alert.setText(NULL_MESSAGE);
                            }
                            else{
                                nameCheck = false;
                                toolbar__Alert.setText(NAME_EXIST_ERROR_MESSAGE);
                            }
                        }
                        else{
                            if (Services.getInstance().findByName(toolbar__NameInput.getText()) == null){
                                nameCheck = true;
                                toolbar__Alert.setText(NULL_MESSAGE);
                            }
                            else{
                                nameCheck = false;
                                toolbar__Alert.setText(NAME_EXIST_ERROR_MESSAGE);
                            }
                        }
                    } catch (IOException e) {
                        showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                    }
                }
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__NameInputCaretUpdate

    private void toolbar__QuantityInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_toolbar__QuantityInputCaretUpdate
        try {
            if (Services.getInstance().getCurrentUser().isWrite()){
                if (toolbar__QuantityInput.getText().equals("")) {
                    quantityCheck = false;
                    toolbar__Alert.setText(QUANTITY_BLANK_ERROR_MESSAGE);
                }
                else{
                    Pattern pattern = Pattern.compile("^\\d+$");
                    if (pattern.matcher(toolbar__QuantityInput.getText()).find()){
                        quantityCheck = true;
                        toolbar__Alert.setText(NULL_MESSAGE);
                    }
                    else{
                        quantityCheck = false;
                        toolbar__Alert.setText(QUANTITY_FORMAT_ERROR_MESSAGE);
                    }
                }
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__QuantityInputCaretUpdate
    
    private void toolbar__PriceInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_toolbar__PriceInputCaretUpdate
        try {
            if (Services.getInstance().getCurrentUser().isWrite()){
                if (toolbar__PriceInput.getText().equals("")) {
                    toolbar__Alert.setText(PRICE_BLANK_ERROR_MESSAGE);
                }
                else{
                    toolbar__Alert.setText(NULL_MESSAGE);
                }
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__PriceInputCaretUpdate

    private void toolbar__ButtonFileChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonFileChooserMouseClicked
        if (toolbar__ButtonFileChooser.isEnabled()){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(IMAGES_DIR));
            int res = fileChooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION){
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                toolbar__ThumbnailInput.setText(path);
            }
        }
    }//GEN-LAST:event_toolbar__ButtonFileChooserMouseClicked

    private void navbar__ButtonHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar__ButtonHomeMouseClicked
        accessPage = PAGES_HOME;
        try {
            appController.setProductsTable(productsTable, Services.getInstance().getProducts());
        } catch (IOException  e) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        toolbar__SortInput.setSelectedIndex(0);
        toolbar__SearchInput.setSelectedIndex(0);
        resetToolbar(true);
        resetSearchField(true);
        CardLayout c = (CardLayout) main__Pages.getLayout();
        c.show(main__Pages, CARD_HOME);
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_navbar__ButtonHomeMouseClicked

    private void navbar__ButtonProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar__ButtonProductsMouseClicked
        accessPage = PAGES_PRODUCTS;
        if (productSearchModify){
            resetProductTemplates();
        }
        resetToolbar(true);
        resetSearchField(true);
        toolbar__SortInput.setSelectedIndex(0);
        toolbar__SearchInput.setSelectedIndex(0);
        CardLayout c = (CardLayout) main__Pages.getLayout();
        c.show(main__Pages, CARD_PRODUCTS);
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_navbar__ButtonProductsMouseClicked

    private void navbar__ButtonStatisticMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar__ButtonStatisticMouseClicked
        accessPage = PAGES_STATISTIC;
        resetToolbar(true);
        resetSearchField(true);
        toolbar__SortInput.setSelectedIndex(0);
        toolbar__SearchInput.setSelectedIndex(0);
        setStatistic();
        CardLayout c = (CardLayout) main__Pages.getLayout();
        c.show(main__Pages, CARD_STATISTIC);
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_navbar__ButtonStatisticMouseClicked

    private void navbar__ButtonSignOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar__ButtonSignOutMouseClicked
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn đăng xuất tài khoản này?", TYPE_DIALOG_MESSAGE,
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        switch (response) {
            case JOptionPane.NO_OPTION -> {
            }
            case JOptionPane.YES_OPTION ->  {
                signOut();
            }
            case JOptionPane.CLOSED_OPTION -> {
            }
            default ->  {
            }
        }
    }//GEN-LAST:event_navbar__ButtonSignOutMouseClicked

    private void toolbar__ManafacturerInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_toolbar__ManafacturerInputCaretUpdate
        try {
            if (Services.getInstance().getCurrentUser().isWrite()){
                if (toolbar__ManafacturerInput.getText().equals("")){
                    manafacturerCheck = false;
                    toolbar__Alert.setText(MANAFACTURER_BLANK_ERROR_MESSAGE);
                }
                else{
                    manafacturerCheck = true;
                    toolbar__Alert.setText(NULL_MESSAGE);
                }
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__ManafacturerInputCaretUpdate

    private void toolbar__ButtonResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonResetMouseClicked
        if (toolbar__ButtonReset.isEnabled()){
            productsTable.clearSelection();
            Services.setSelectedProduct(null);
            resetToolbar(true);
        }
    }//GEN-LAST:event_toolbar__ButtonResetMouseClicked

    private void toolbar__ButtonEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonEditMouseClicked
        try {
            if (Services.getInstance().getCurrentUser().isWrite()){
                if (toolbar__ButtonEdit.isEnabled() && isModify()){
                    if (!nameCheck){
                        toolbar__Alert.setText(NAME_EXIST_ERROR_MESSAGE);
                    }
                    else{
                        if (!quantityCheck){
                            toolbar__Alert.setText(QUANTITY_FORMAT_ERROR_MESSAGE);
                        }
                        else {
                            if (!priceCheck){
                                toolbar__Alert.setText(PRICE_FORMAT_ERROR_MESSAGE);
                            }
                        }
                    }
                    if (toolbar__NameInput.getText().equals("")){
                        toolbar__Alert.setText(NAME_BLANK_ERROR_MESSAGE);
                    }
                    else{
                        if (toolbar__QuantityInput.getText().equals("")){
                            toolbar__Alert.setText(QUANTITY_BLANK_ERROR_MESSAGE);
                        }
                        else{
                            if(toolbar__PriceInput.getText().equals("")){
                                toolbar__Alert.setText(PRICE_BLANK_ERROR_MESSAGE);
                            }
                            else{
                                if (toolbar__ExpiryInput.getDate() == null){
                                    dateCheck = false;
                                    toolbar__Alert.setText(DATE_FORMAT_ERROR_MESSAGE);
                                }
                                else{
                                    if (toolbar__ManafacturerInput.getText().equals("")){
                                        toolbar__Alert.setText(MANAFACTURER_BLANK_ERROR_MESSAGE);
                                    }
                                    else{
                                        if (nameCheck == quantityCheck == priceCheck == dateCheck == manafacturerCheck){
                                            JDialog.setDefaultLookAndFeelDecorated(true);
                                            int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa thông tin sản phẩm này?", TYPE_DIALOG_MESSAGE,
                                                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                            switch (response) {
                                                case JOptionPane.NO_OPTION -> {
                                                }
                                                case JOptionPane.YES_OPTION ->  {
                                                    String path = toolbar__ThumbnailInput.getText();
                                                    if (!path.equals(Services.getInstance().getSelectedProduct().getThumbnail())) {
                                                        if (!path.equals("")){
                                                            try {
                                                                File thumbnail = new File(IMAGES_DIR + path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".")) + ".png");
                                                                BufferedImage bufferedImage = ImageIO.read(new File(path));
                                                                ImageIO.write(Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 140, 180), "png", thumbnail);
                                                                toolbar__ThumbnailInput.setText(IMAGES_DIR + path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".")) + ".png");
                                                            } catch (IOException ex) {
                                                                toolbar__ThumbnailInput.setText("");
                                                                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                                                            }
                                                        }
                                                    }
                                                    try {
                                                        String id  = IdGenerator((String) toolbar__CategoryInput.getSelectedItem());
                                                        String name = toolbar__NameInput.getText();
                                                        String category = toolbar__CategoryInput.getSelectedItem().toString();
                                                        int quantity = Integer.parseInt(toolbar__QuantityInput.getText());
                                                        BigDecimal price = BigDecimalConverter.currencyParse(toolbar__PriceInput.getText());
                                                        Date expDate = toolbar__ExpiryInput.getDate();
                                                        String manafacturer = toolbar__ManafacturerInput.getText();
                                                        String thumbnail = toolbar__ThumbnailInput.getText();
                                                        String description = toolbar__DescriptionInput.getText();
                                                        Product product = new Product(id, name, category, quantity, price, expDate, manafacturer, thumbnail, description);
                                                        Services.getInstance().update(Services.getInstance().getSelectedProduct().getId(), product);
                                                        showMessage("Sửa thông tin sản phẩm thành công!", true);
                                                    } catch (IOException | ParseException e) {
                                                        showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                                                    }
                                                }
                                                case JOptionPane.CLOSED_OPTION -> {
                                                }
                                                default ->  {
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
                showMessage(AUTHORIZATION_ERROR_DIALOG_MESSAGE, false);
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__ButtonEditMouseClicked

    private void toolbar__ButtonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonDeleteMouseClicked
        try {
            if (Services.getInstance().getCurrentUser().isWrite()){
                if (toolbar__ButtonDelete.isEnabled()){
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa sản phẩm này?", TYPE_DIALOG_MESSAGE,
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    switch (response) {
                        case JOptionPane.NO_OPTION -> {
                        }
                        case JOptionPane.YES_OPTION ->  {
                            try {
                                toolbar__ButtonAdd.setEnabled(true);
                                Services.getInstance().delete(Services.getInstance().getSelectedProduct().getId());
                                productsTable.clearSelection();
                                showMessage("Xóa thành công sản phẩm này", true);
                            } catch (IOException e) {
                                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                            }
                        }
                        case JOptionPane.CLOSED_OPTION -> {
                        }
                        default ->  {
                        }
                    }
                }
            }
            else{
                showMessage(AUTHORIZATION_ERROR_DIALOG_MESSAGE, false);
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__ButtonDeleteMouseClicked

    private void toolbar__ButtonSortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonSortMouseClicked
        if (toolbar__ButtonSort.isEnabled()){
            try {
                if (toolbar__SortInput.getSelectedItem().toString().equals("Mặc định")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().getProducts());
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_PRODUCTS);
                    }
                }
                if (toolbar__SortInput.getSelectedItem().toString().equals(ID)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().sort(new Comparator.IDComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(Services.getInstance().sort(new Comparator.IDComparator()));
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_SORT);
                    }
                }
                if (toolbar__SortInput.getSelectedItem().toString().equals(NAME)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().sort(new Comparator.NameComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(Services.getInstance().sort(new Comparator.NameComparator()));
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_SORT);
                    }
                }
                if (toolbar__SortInput.getSelectedItem().toString().equals("Số lượng tăng")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().sort(new Comparator.QuantityUpComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(Services.getInstance().sort(new Comparator.QuantityUpComparator()));
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_SORT);
                    }
                }
                if (toolbar__SortInput.getSelectedItem().toString().equals("Số lượng giảm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().sort(new Comparator.QuantityDownComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(Services.getInstance().sort(new Comparator.QuantityDownComparator()));
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_SORT);
                    }
                }
                if (toolbar__SortInput.getSelectedItem().toString().equals("Đơn giá tăng")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().sort(new Comparator.PriceUpComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(Services.getInstance().sort(new Comparator.PriceUpComparator()));
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_SORT);
                    }
                }
                if (toolbar__SortInput.getSelectedItem().toString().equals("Đơn giá giảm")){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().sort(new Comparator.PriceDownComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(Services.getInstance().sort(new Comparator.PriceDownComparator()));
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_SORT);
                    }
                }
                if (toolbar__SortInput.getSelectedItem().toString().equals(EXPIRY)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().sort(new Comparator.ExpiryComparator()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        setSort(Services.getInstance().sort(new Comparator.ExpiryComparator()));
                        CardLayout c = (CardLayout) main__Pages.getLayout();
                        c.show(main__Pages, CARD_SORT);
                    }
                }
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
        }
    }//GEN-LAST:event_toolbar__ButtonSortMouseClicked

    private void toolbar__SearchInputItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_toolbar__SearchInputItemStateChanged
        initSearchField();
        toolbar__SearchStringInput.setText("");
        toolbar__SearchMaxInput.setText("");
        toolbar__SearchMinInput.setText("");
    }//GEN-LAST:event_toolbar__SearchInputItemStateChanged

    private void toolbar__ButtonSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonSearchMouseClicked
        try {
        if (toolbar__SearchInput.getSelectedItem().toString().equals(ID)){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(NAME)){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(CATEGORY)){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(MANAFACTURER)){
            if (accessPage == PAGES_HOME){
                appController.setProductsTable(productsTable, Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
            }
            if (accessPage == PAGES_PRODUCTS){
                filter(Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
                productSearchModify = true;
            }
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(PRICE)){
            try {
                BigDecimal minPrice = BigDecimalConverter.currencyParse(toolbar__SearchMinInput.getText());
                BigDecimal maxPrice = BigDecimalConverter.currencyParse(toolbar__SearchMaxInput.getText());
                if (accessPage == PAGES_HOME){
                    appController.setProductsTable(productsTable, Services.getInstance().filterByPrice(minPrice, maxPrice));
                }
                if (accessPage == PAGES_PRODUCTS){
                    filter(Services.getInstance().filterByPrice(minPrice, maxPrice));
                    productSearchModify = true;
                }
            } catch (IOException | ParseException e) {
                showMessage(PRICE_FORMAT_ERROR_DIALOG_MESSAGE, false);
            }
        }
        if (toolbar__SearchInput.getSelectedItem().toString().equals(EXPIRY)){
            try {
                Date minDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMinInput.getText());
                Date maxDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMaxInput.getText());
                if (accessPage == PAGES_HOME){
                    appController.setProductsTable(productsTable, Services.getInstance().filterByExpiry(minDate, maxDate));
                }
                if (accessPage == PAGES_PRODUCTS){
                    filter(Services.getInstance().filterByExpiry(minDate, maxDate));
                    productSearchModify = true;
                }
            } catch (IOException | ParseException  e) {
                showMessage(EXPIRY_FORMAT_ERROR_DIALOG_MESSAGE, false);
            }
        }
        } catch (IOException e) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        products__Scroll.getVerticalScrollBar().setValue(0);
        if (accessPage == PAGES_PRODUCTS){
                CardLayout c = (CardLayout) main__Pages.getLayout();
                c.show(main__Pages, CARD_PRODUCTS);
        }
        initSearchField();
    }//GEN-LAST:event_toolbar__ButtonSearchMouseClicked

    private void statistic__ButtonPercentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statistic__ButtonPercentMouseClicked
        setPieChart();
    }//GEN-LAST:event_statistic__ButtonPercentMouseClicked

    private void toolbar__ButtonResetSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbar__ButtonResetSearchMouseClicked
        if (accessPage == PAGES_HOME){
            try {
                appController.setProductsTable(productsTable, Services.getInstance().getProducts());
            } catch (IOException  e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            resetSearchField(true);
        }
        else{
            resetProductTemplates();
            toolbar__SearchStringInput.setText("");
            toolbar__SearchMinInput.setText("");
            toolbar__SearchMaxInput.setText("");
        }
    }//GEN-LAST:event_toolbar__ButtonResetSearchMouseClicked

    private void toolbar__SearchStringInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_toolbar__SearchStringInputFocusGained
        toolbar__SearchStringInput.setText("");
    }//GEN-LAST:event_toolbar__SearchStringInputFocusGained

    private void toolbar__SearchMinInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_toolbar__SearchMinInputFocusGained
        toolbar__SearchMinInput.setText("");
    }//GEN-LAST:event_toolbar__SearchMinInputFocusGained

    private void toolbar__SearchMaxInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_toolbar__SearchMaxInputFocusGained
        toolbar__SearchMaxInput.setText("");
    }//GEN-LAST:event_toolbar__SearchMaxInputFocusGained

    private void statistic__ButtonQuantityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statistic__ButtonQuantityMouseClicked
        setBarChart();
    }//GEN-LAST:event_statistic__ButtonQuantityMouseClicked

    private void navbar__ButtonAuthorizationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar__ButtonAuthorizationMouseClicked
        try {
            accessPage = PAGES_AUTHORIZATION;
            resetToolbar(true);
            CardLayout c = (CardLayout) main__Pages.getLayout();
            c.show(main__Pages, CARD_AUTHORIZATION);
            usersTable.setBackground(Color.WHITE);
            adminController.setUsersTable(usersTable, Services.getInstance().getUsers());
            this.repaint();
            this.revalidate();
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_navbar__ButtonAuthorizationMouseClicked

    private void information__ButtonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_information__ButtonDeleteMouseClicked
        if (information__ButtonDelete.isEnabled()){
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa tài khoản này?", TYPE_DIALOG_MESSAGE,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            switch (response) {
                case JOptionPane.NO_OPTION -> {
                }
                case JOptionPane.YES_OPTION ->  {
                    try {
                        Services.getInstance().deleteUser(Services.getInstance().getSelectedUser().getUsername());
                        showMessage("Bạn đã xóa thành công tài khoản", true);
                    } catch (IOException ex) {
                        showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                    }
                }
                case JOptionPane.CLOSED_OPTION -> {
                }
                default ->  {
                }
            }
            resetUserEdit();
        }
    }//GEN-LAST:event_information__ButtonDeleteMouseClicked

    private void information__ButtonEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_information__ButtonEditMouseClicked
        if (information__ButtonEdit.isEnabled() && isUserModify()){
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa quyền cho tài khoản này?", TYPE_DIALOG_MESSAGE,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            switch (response) {
                case JOptionPane.NO_OPTION -> {
                }
                case JOptionPane.YES_OPTION ->  {
                    try {
                        Services.getInstance().getSelectedUser().setRead(information__ReadInput.isSelected());
                        Services.getInstance().getSelectedUser().setWrite(information__WriteInput.isSelected());
                        Services.getInstance().updateUser(Services.getInstance().getSelectedUser().getUsername(), Services.getInstance().getSelectedUser());
                        showMessage("Bạn đã chỉnh sửa thành công quyền cho tài khoản", true);
                    } catch (IOException ex) {
                        showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                    }
                }
                case JOptionPane.CLOSED_OPTION -> {
                }
                default ->  {
                }
            }
            resetUserEdit();
        }
    }//GEN-LAST:event_information__ButtonEditMouseClicked

    private void filter__ButtonFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filter__ButtonFilterMouseClicked
        String username = filter__UsernameInput.getText();
        Date dateMin = null;
        Date dateMax = null;
        if (!filter__RegisterDateMin.getText().equals("") && !filter__RegisterDateMax.getText().equals("")){
            try {
                dateMin = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMin.getText());
                dateMax = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMax.getText());
            } catch (ParseException e) {
                showMessage(CREATE_FORMAT_ERROR_DIALOG_MESSAGE, false);
            }
        }
        try {
            adminController.setUsersTable(usersTable, Services.getInstance().filter(username, dateMin, dateMax, filter__ReadInput.isSelected(), filter__WriteInput.isSelected()));
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        resetUserEdit();
    }//GEN-LAST:event_filter__ButtonFilterMouseClicked

    private void filter__ButtonResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filter__ButtonResetMouseClicked
        try {
            adminController.setUsersTable(usersTable, Services.getInstance().getUsers());
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
        resetUserFilter();
    }//GEN-LAST:event_filter__ButtonResetMouseClicked

    private void information__ButtonResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_information__ButtonResetMouseClicked
        resetUserEdit();
    }//GEN-LAST:event_information__ButtonResetMouseClicked

    private void information__ReadInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_information__ReadInputStateChanged
        if (information__WriteInput.isSelected()){
            information__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_information__ReadInputStateChanged

    private void information__WriteInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_information__WriteInputStateChanged
        if (information__WriteInput.isSelected()){
            information__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_information__WriteInputStateChanged

    private void filter__ReadInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_filter__ReadInputStateChanged
        if (filter__WriteInput.isSelected()){
            filter__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_filter__ReadInputStateChanged

    private void filter__WriteInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_filter__WriteInputStateChanged
        if (filter__WriteInput.isSelected()){
            filter__ReadInput.setSelected(true);
        }
    }//GEN-LAST:event_filter__WriteInputStateChanged

    private void filter__UsernameInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filter__UsernameInputFocusGained
        filter__UsernameInput.setText("");
        filter__WriteInput.setSelected(false);
        filter__ReadInput.setSelected(false);
    }//GEN-LAST:event_filter__UsernameInputFocusGained

    private void filter__RegisterDateMinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filter__RegisterDateMinFocusGained
        filter__RegisterDateMin.setText("");
        filter__WriteInput.setSelected(false);
        filter__ReadInput.setSelected(false);
    }//GEN-LAST:event_filter__RegisterDateMinFocusGained

    private void filter__RegisterDateMaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filter__RegisterDateMaxFocusGained
        filter__RegisterDateMax.setText("");
        filter__WriteInput.setSelected(false);
        filter__ReadInput.setSelected(false);
    }//GEN-LAST:event_filter__RegisterDateMaxFocusGained

    private void toolbar__SearchStringInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toolbar__SearchStringInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (toolbar__SearchInput.getSelectedItem().toString().equals(ID)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(NAME)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(CATEGORY)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(MANAFACTURER)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(PRICE)){
                    try {
                        BigDecimal minPrice = new BigDecimal(toolbar__SearchMinInput.getText());
                        BigDecimal maxPrice = new BigDecimal(toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, Services.getInstance().filterByPrice(minPrice, maxPrice));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(Services.getInstance().filterByPrice(minPrice, maxPrice));
                            productSearchModify = true;
                        }
                    } catch (IOException e) {
                        showMessage(PRICE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(EXPIRY)){
                    try {
                        Date minDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMinInput.getText());
                        Date maxDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, Services.getInstance().filterByExpiry(minDate, maxDate));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(Services.getInstance().filterByExpiry(minDate, maxDate));
                            productSearchModify = true;
                        }
                    } catch (IOException | ParseException  e) {
                        showMessage(EXPIRY_FORMAT_ERROR_DIALOG_MESSAGE, false);
                    }
                }
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
        if (accessPage == PAGES_PRODUCTS){
                CardLayout c = (CardLayout) main__Pages.getLayout();
                c.show(main__Pages, CARD_PRODUCTS);
        }
        initSearchField();
        }
    }//GEN-LAST:event_toolbar__SearchStringInputKeyPressed

    private void toolbar__SearchMinInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toolbar__SearchMinInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (toolbar__SearchInput.getSelectedItem().toString().equals(ID)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(NAME)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(CATEGORY)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(MANAFACTURER)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(PRICE)){
                    try {
                        BigDecimal minPrice = new BigDecimal(toolbar__SearchMinInput.getText());
                        BigDecimal maxPrice = new BigDecimal(toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, Services.getInstance().filterByPrice(minPrice, maxPrice));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(Services.getInstance().filterByPrice(minPrice, maxPrice));
                            productSearchModify = true;
                        }
                    } catch (IOException e) {
                        showMessage(PRICE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(EXPIRY)){
                    try {
                        Date minDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMinInput.getText());
                        Date maxDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, Services.getInstance().filterByExpiry(minDate, maxDate));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(Services.getInstance().filterByExpiry(minDate, maxDate));
                            productSearchModify = true;
                        }
                    } catch (IOException | ParseException  e) {
                        showMessage(EXPIRY_FORMAT_ERROR_DIALOG_MESSAGE, false);
                    }
                }
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
        if (accessPage == PAGES_PRODUCTS){
                CardLayout c = (CardLayout) main__Pages.getLayout();
                c.show(main__Pages, CARD_PRODUCTS);
        }
        initSearchField();
        }
    }//GEN-LAST:event_toolbar__SearchMinInputKeyPressed

    private void toolbar__SearchMaxInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toolbar__SearchMaxInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (toolbar__SearchInput.getSelectedItem().toString().equals(ID)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterById(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(NAME)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByName(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(CATEGORY)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByCategory(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(MANAFACTURER)){
                    if (accessPage == PAGES_HOME){
                        appController.setProductsTable(productsTable, Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
                    }
                    if (accessPage == PAGES_PRODUCTS){
                        filter(Services.getInstance().filterByManafacturer(toolbar__SearchStringInput.getText()));
                        productSearchModify = true;
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(PRICE)){
                    try {
                        BigDecimal minPrice = new BigDecimal(toolbar__SearchMinInput.getText());
                        BigDecimal maxPrice = new BigDecimal(toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, Services.getInstance().filterByPrice(minPrice, maxPrice));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(Services.getInstance().filterByPrice(minPrice, maxPrice));
                            productSearchModify = true;
                        }
                    } catch (IOException e) {
                        showMessage(PRICE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                    }
                }
                if (toolbar__SearchInput.getSelectedItem().toString().equals(EXPIRY)){
                    try {
                        Date minDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMinInput.getText());
                        Date maxDate = new SimpleDateFormat(DATE_FORMAT).parse(toolbar__SearchMaxInput.getText());
                        if (accessPage == PAGES_HOME){
                            appController.setProductsTable(productsTable, Services.getInstance().filterByExpiry(minDate, maxDate));
                        }
                        if (accessPage == PAGES_PRODUCTS){
                            filter(Services.getInstance().filterByExpiry(minDate, maxDate));
                            productSearchModify = true;
                        }
                    } catch (IOException | ParseException  e) {
                        showMessage(EXPIRY_FORMAT_ERROR_DIALOG_MESSAGE, false);
                    }
                }
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
        initSearchField();
        }
    }//GEN-LAST:event_toolbar__SearchMaxInputKeyPressed

    private void filter__UsernameInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filter__UsernameInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!filter__RegisterDateMin.getText().equals("") && !filter__RegisterDateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMin.getText());
                    dateMax = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMax.getText());
                } catch (ParseException e) {
                    showMessage(CREATE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                }
            }
            try {
                adminController.setUsersTable(usersTable, Services.getInstance().filter(username, dateMin, dateMax, filter__ReadInput.isSelected(), filter__WriteInput.isSelected()));
            } catch (IOException ex) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            resetUserEdit();
        }
    }//GEN-LAST:event_filter__UsernameInputKeyPressed

    private void filter__RegisterDateMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filter__RegisterDateMinKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!filter__RegisterDateMin.getText().equals("") && !filter__RegisterDateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMin.getText());
                    dateMax = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMax.getText());
                } catch (ParseException e) {
                    showMessage(CREATE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                }
            }
            try {
                adminController.setUsersTable(usersTable, Services.getInstance().filter(username, dateMin, dateMax, filter__ReadInput.isSelected(), filter__WriteInput.isSelected()));
            } catch (IOException ex) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            resetUserEdit();
        }
    }//GEN-LAST:event_filter__RegisterDateMinKeyPressed

    private void filter__RegisterDateMaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filter__RegisterDateMaxKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!filter__RegisterDateMin.getText().equals("") && !filter__RegisterDateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMin.getText());
                    dateMax = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMax.getText());
                } catch (ParseException e) {
                    showMessage(CREATE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                }
            }
            try {
                adminController.setUsersTable(usersTable, Services.getInstance().filter(username, dateMin, dateMax, filter__ReadInput.isSelected(), filter__WriteInput.isSelected()));
            } catch (IOException ex) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            resetUserEdit();
        }
    }//GEN-LAST:event_filter__RegisterDateMaxKeyPressed

    private void filter__ReadInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filter__ReadInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!filter__RegisterDateMin.getText().equals("") && !filter__RegisterDateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMin.getText());
                    dateMax = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMax.getText());
                } catch (ParseException e) {
                    showMessage(CREATE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                }
            }
            try {
                adminController.setUsersTable(usersTable, Services.getInstance().filter(username, dateMin, dateMax, filter__ReadInput.isSelected(), filter__WriteInput.isSelected()));
            } catch (IOException ex) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            resetUserEdit();
        }
    }//GEN-LAST:event_filter__ReadInputKeyPressed

    private void filter__WriteInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filter__WriteInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = filter__UsernameInput.getText();
            Date dateMin = null;
            Date dateMax = null;
            if (!filter__RegisterDateMin.getText().equals("") && !filter__RegisterDateMax.getText().equals("")){
                try {
                    dateMin = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMin.getText());
                    dateMax = new SimpleDateFormat(DATE_FORMAT).parse(filter__RegisterDateMax.getText());
                } catch (ParseException e) {
                    showMessage(CREATE_FORMAT_ERROR_DIALOG_MESSAGE, false);
                }
            }
            try {
                adminController.setUsersTable(usersTable, Services.getInstance().filter(username, dateMin, dateMax, filter__ReadInput.isSelected(), filter__WriteInput.isSelected()));
            } catch (IOException ex) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            resetUserEdit();
        }
    }//GEN-LAST:event_filter__WriteInputKeyPressed

    private void toolbar__PriceInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toolbar__PriceInputKeyReleased
        try {
            if (Services.getInstance().getCurrentUser().isWrite()){
                if (toolbar__PriceInput.getText().equals("")) {
                    priceCheck = false;
                    toolbar__Alert.setText(PRICE_BLANK_ERROR_MESSAGE);
                }
                else{
                    String price = toolbar__PriceInput.getText().replaceAll("\\.", "");
                    Pattern pattern = Pattern.compile("^\\d+$");
                    if (pattern.matcher(price).find()){
                        priceCheck = true;
                        toolbar__Alert.setText(NULL_MESSAGE);
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormat).getDecimalFormatSymbols();
                        decimalFormatSymbols.setCurrencySymbol("");
                        ((DecimalFormat) currencyFormat).setDecimalFormatSymbols(decimalFormatSymbols);
                        price = currencyFormat.format(new BigDecimal(price)).trim();
                        price = price.substring(0, price.length() - 1);
                        toolbar__PriceInput.setText(price);
                    }
                    else{
                        priceCheck = false;
                        toolbar__Alert.setText(PRICE_FORMAT_ERROR_MESSAGE);
                    }
                }
            }
        } catch (IOException ex) {
            showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
        }
    }//GEN-LAST:event_toolbar__PriceInputKeyReleased

    private void toolbar__SearchMinInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toolbar__SearchMinInputKeyReleased
        if (toolbar__SearchInput.getSelectedItem().toString().equals("Đơn giá")){
            String price = toolbar__SearchMinInput.getText().replaceAll("\\.", "");
            Pattern pattern = Pattern.compile("^\\d+$");
            if (pattern.matcher(price).find()){
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormat).getDecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("");
                ((DecimalFormat) currencyFormat).setDecimalFormatSymbols(decimalFormatSymbols);
                price = currencyFormat.format(new BigDecimal(price)).trim();
                price = price.substring(0, price.length() - 1);
                toolbar__SearchMinInput.setText(price);
            }
        }
    }//GEN-LAST:event_toolbar__SearchMinInputKeyReleased

    private void toolbar__SearchMaxInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toolbar__SearchMaxInputKeyReleased
        if (toolbar__SearchInput.getSelectedItem().toString().equals("Đơn giá")){
            String price = toolbar__SearchMaxInput.getText().replaceAll("\\.", "");
            Pattern pattern = Pattern.compile("^\\d+$");
            if (pattern.matcher(price).find()){
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormat).getDecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("");
                ((DecimalFormat) currencyFormat).setDecimalFormatSymbols(decimalFormatSymbols);
                price = currencyFormat.format(new BigDecimal(price)).trim();
                price = price.substring(0, price.length() - 1);
                toolbar__SearchMaxInput.setText(price);
            }
        }
    }//GEN-LAST:event_toolbar__SearchMaxInputKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel authorization__Filter;
    private javax.swing.JPanel authorization__Information;
    private javax.swing.JPanel authorization__Table;
    private javax.swing.JLabel filter_Title;
    private javax.swing.JButton filter__ButtonFilter;
    private javax.swing.JButton filter__ButtonReset;
    private javax.swing.JLabel filter__Decorate;
    private javax.swing.JCheckBox filter__ReadInput;
    private javax.swing.JLabel filter__ReadTitle;
    private javax.swing.JTextField filter__RegisterDateMax;
    private javax.swing.JTextField filter__RegisterDateMin;
    private javax.swing.JLabel filter__RegisterDateTitle;
    private javax.swing.JTextField filter__UsernameInput;
    private javax.swing.JLabel filter__UsernameTitle;
    private javax.swing.JCheckBox filter__WriteInput;
    private javax.swing.JLabel filter__WriteTitle;
    private javax.swing.JPanel home__Table;
    private javax.swing.JButton information__ButtonDelete;
    private javax.swing.JButton information__ButtonEdit;
    private javax.swing.JButton information__ButtonReset;
    private javax.swing.JCheckBox information__ReadInput;
    private javax.swing.JLabel information__ReadTitle;
    private javax.swing.JLabel information__RegisterDate;
    private javax.swing.JTextField information__RegisterDateInput;
    private javax.swing.JLabel information__Title;
    private javax.swing.JTextField information__UsernameInput;
    private javax.swing.JLabel information__UsernameTitle;
    private javax.swing.JCheckBox information__WriteInput;
    private javax.swing.JLabel information__WriteTitle;
    private javax.swing.JPanel main;
    private javax.swing.JPanel main__Pages;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton navbar__ButtonAuthorization;
    private javax.swing.JButton navbar__ButtonHome;
    private javax.swing.JButton navbar__ButtonProducts;
    private javax.swing.JButton navbar__ButtonSignOut;
    private javax.swing.JButton navbar__ButtonStatistic;
    private javax.swing.JPanel pages__Authorization;
    private javax.swing.JPanel pages__Home;
    private javax.swing.JPanel pages__Products;
    private javax.swing.JPanel pages__Sort;
    private javax.swing.JPanel pages__Statistic;
    private javax.swing.JPanel products__Main;
    private javax.swing.JScrollPane products__Scroll;
    private javax.swing.JPanel sort__Main;
    private javax.swing.JScrollPane sort__Scroll;
    private hvan.qlkh.chart.Chart statistic__BarChart;
    private javax.swing.JButton statistic__ButtonPercent;
    private javax.swing.JButton statistic__ButtonQuantity;
    private javax.swing.JPanel statistic__Category;
    private javax.swing.JLabel statistic__CategoryStatistic;
    private javax.swing.JLabel statistic__CategoryThumb;
    private javax.swing.JLabel statistic__CategoryTitle;
    private javax.swing.JPanel statistic__Main;
    private javax.swing.JPanel statistic__Manafacturer;
    private javax.swing.JLabel statistic__ManafacturerStatistic;
    private javax.swing.JLabel statistic__ManafacturerThumb;
    private javax.swing.JLabel statistic__ManafacturerTitle;
    private javax.swing.JPanel statistic__Menu;
    private javax.swing.JComboBox<String> statistic__PercentStatistic;
    private javax.swing.JLabel statistic__PercentStatisticTitle;
    private hvan.qlkh.chart.PieChart statistic__PieChart;
    private javax.swing.JComboBox<String> statistic__QuantityStatistic;
    private javax.swing.JLabel statistic__QuantityStatisticTitle;
    private javax.swing.JScrollPane statistic__Scroll;
    private javax.swing.JPanel statistic__TotalProducts;
    private javax.swing.JLabel statistic__TotalProductsStatistic;
    private javax.swing.JLabel statistic__TotalProductsThumb;
    private javax.swing.JLabel statistic__TotalProductsTitle;
    private javax.swing.JPanel statistic__TotalQuanity;
    private javax.swing.JLabel statistic__TotalQuantityStatistic;
    private javax.swing.JLabel statistic__TotalQuantityThumb;
    private javax.swing.JLabel statistic__TotalQuantityTitle;
    private javax.swing.JPanel toolbar;
    private javax.swing.JLabel toolbar__Alert;
    private javax.swing.JButton toolbar__ButtonAdd;
    private javax.swing.JButton toolbar__ButtonDelete;
    private javax.swing.JButton toolbar__ButtonEdit;
    private javax.swing.JButton toolbar__ButtonFileChooser;
    private javax.swing.JButton toolbar__ButtonReset;
    private javax.swing.JButton toolbar__ButtonResetSearch;
    private javax.swing.JButton toolbar__ButtonSearch;
    private javax.swing.JButton toolbar__ButtonSort;
    private javax.swing.JComboBox<String> toolbar__CategoryInput;
    private javax.swing.JLabel toolbar__CategoryTitle;
    private javax.swing.JLabel toolbar__Decorate;
    private javax.swing.JTextPane toolbar__DescriptionInput;
    private javax.swing.JScrollPane toolbar__DescriptionScroll;
    private javax.swing.JLabel toolbar__DescriptionTitle;
    private com.toedter.calendar.JDateChooser toolbar__ExpiryInput;
    private javax.swing.JLabel toolbar__ExpiryTitle;
    private javax.swing.JTextField toolbar__ManafacturerInput;
    private javax.swing.JLabel toolbar__ManafacturerTitle;
    private javax.swing.JTextField toolbar__NameInput;
    private javax.swing.JLabel toolbar__NameTitle;
    private javax.swing.JTextField toolbar__PriceInput;
    private javax.swing.JLabel toolbar__PriceTitle;
    private javax.swing.JTextField toolbar__QuantityInput;
    private javax.swing.JLabel toolbar__QuantityTitle;
    private javax.swing.JComboBox<String> toolbar__SearchInput;
    private javax.swing.JTextField toolbar__SearchMaxInput;
    private javax.swing.JTextField toolbar__SearchMinInput;
    private javax.swing.JTextField toolbar__SearchStringInput;
    private javax.swing.JLabel toolbar__SearchTitle;
    private javax.swing.JPanel toolbar__SeparatorBottom;
    private javax.swing.JPanel toolbar__SeparatorTop;
    private javax.swing.JComboBox<String> toolbar__SortInput;
    private javax.swing.JLabel toolbar__SortTitle;
    private javax.swing.JTextField toolbar__ThumbnailInput;
    private javax.swing.JLabel toolbar__ThumbnailTitle;
    private javax.swing.JLabel toolbar__Title;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    public void setProductFromSelected() throws ParseException{
        int row = productsTable.getSelectedRow();
        if (row >= 0) {
            toolbar__NameInput.setText(productsTable.getModel().getValueAt(row, 1).toString());
            toolbar__CategoryInput.setSelectedItem(productsTable.getModel().getValueAt(row, 2).toString());
            toolbar__QuantityInput.setText(productsTable.getModel().getValueAt(row, 3).toString());
            String price = BigDecimalConverter.currencyParse(productsTable.getModel().getValueAt(row, 4).toString()).toString();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormat).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) currencyFormat).setDecimalFormatSymbols(decimalFormatSymbols);
            price = currencyFormat.format(new BigDecimal(price)).trim();
            price = price.substring(0, price.length() - 1);
            toolbar__PriceInput.setText(price);
            toolbar__ExpiryInput.setDate(new SimpleDateFormat(DATE_FORMAT).parse(productsTable.getModel().getValueAt(row, 5).toString()));
            toolbar__ManafacturerInput.setText(productsTable.getModel().getValueAt(row, 6).toString());
            try {
                if (Services.getInstance().getSelectedProduct() != null){
                    if (Services.getInstance().getSelectedProduct().getThumbnail() != null){
                        toolbar__ThumbnailInput.setText(Services.getInstance().getSelectedProduct().getThumbnail());
                    }
                    else{
                        toolbar__ThumbnailInput.setText("");
                    }
                    if (Services.getInstance().getSelectedProduct().getDescription()!= null){
                        toolbar__DescriptionInput.setText(Services.getInstance().getSelectedProduct().getDescription());
                        toolbar__DescriptionInput.setCaretPosition(0);
                    }
                    else{
                        toolbar__DescriptionInput.setText("");
                    }
                }
            } catch (IOException e) {
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
        }
    }

    public void setUserFromSelected(){
        information__ReadInput.setEnabled(true);
        information__WriteInput.setEnabled(true);
        int row = usersTable.getSelectedRow();
        if (row >= 0) {
            information__UsernameInput.setText(usersTable.getModel().getValueAt(row, 1).toString());
            information__RegisterDateInput.setText(usersTable.getModel().getValueAt(row, 2).toString());
            information__ReadInput.setSelected(("x".equals(usersTable.getModel().getValueAt(row, 3).toString())));
            information__WriteInput.setSelected("x".equals(usersTable.getModel().getValueAt(row, 4).toString()));
            if (usersTable.getModel().getValueAt(row, 1).toString().equals("admin")){
                information__ReadInput.setEnabled(false);
                information__WriteInput.setEnabled(false);
                information__ButtonEdit.setEnabled(false);
                information__ButtonDelete.setEnabled(false);
            }
            else{
                information__ReadInput.setEnabled(true);
                information__WriteInput.setEnabled(true);
                information__ButtonEdit.setEnabled(true);
                information__ButtonDelete.setEnabled(true);
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
                showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
            }
            int row = productsTable.getSelectedRow();
            if (row >= 0) {
                try {
                    Services.setSelectedProduct(Services.getInstance().findById(productsTable.getModel().getValueAt(row, 0).toString()));
                } catch (IOException e) {
                    showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                }
            }
            toolbar__ButtonAdd.setEnabled(false);
            toolbar__ButtonEdit.setEnabled(true);
            toolbar__ButtonDelete.setEnabled(true);
        }
    }

    class UserSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            setUserFromSelected();
            int row = usersTable.getSelectedRow();
            if (row >= 0) {
                try {
                    Services.setSelectedUser(Services.getInstance().findByUsername(usersTable.getModel().getValueAt(row, 1).toString()));
                } catch (IOException ex) {
                    showMessage(UNKNOWN_ERROR_DIALOG_MESSAGE, false);
                }
            }
        }
    }
}
