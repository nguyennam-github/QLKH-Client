/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.services;

import hvan.qlkh.controllers.Controller;
import hvan.qlkh.models.Product;
import hvan.qlkh.models.ProductList;
import hvan.qlkh.models.User;
import hvan.qlkh.models.UserList;
import hvan.qlkh.utils.Table;
import hvan.qlkh.views.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class Services{

    @SuppressWarnings("deprecation")
    private Locale vn = new Locale("vi", "VN");

    private static Services instance;
    private static Product selectedProduct;
    private volatile List<Product> products = new ArrayList<>();
    private static User currentUser;
    private static User selectedUser;
    private volatile List<User> users = new ArrayList<>();
    private volatile BufferedWriter os;
    private volatile BufferedReader is;
    private static Controller appController = Main.getInstance().getAppController();
    private static Table productsTable = Main.getInstance().getProductsTable();
    private static Controller adminController = Main.getInstance().getAdminController();
    private static Table usersTable = Main.getInstance().getUsersTable();

    private void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
    public static synchronized Services getInstance() throws IOException{
        if(instance == null){
            instance = new Services();
        }
        return instance;
    }

    private Services() throws IOException {
        Socket socket = new Socket("localhost", 3000);
        os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        setUpSocket();
    }

    private void setUpSocket() {
        Thread thread;
        try {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        String message;
                        while (true) {
                            message = is.readLine();
                            if(message==null){
                                break;
                            }
                            else{
                                String method = message.substring(0, message.indexOf("/"));
                                String payload = message.substring(message.indexOf("/") + 1);
                                if(method.equals("Reset")){
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(payload);
                                    while (true) {
                                        String line = is.readLine();
                                        if (line.trim().equals("")){
                                            break;
                                        }
                                        sb.append(line);
                                    }
                                    StringReader sr = new StringReader(sb.toString());
                                    JAXBContext jaxbContext = JAXBContext.newInstance(ProductList.class);
                                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                                    ProductList pl = (ProductList) unmarshaller.unmarshal(sr);
                                    products.clear();
                                    if (pl != null &&  (pl.getProducts() != null)) {
                                        products = pl.getProducts();
                                    }
                                    try {
                                        appController.setProductsTable(productsTable, products);
                                        Main.getInstance().resetToolbar(true);
                                        Main.getInstance().setSort(products);
                                        Main.getInstance().setStatistic();
                                        Main.getInstance().setProductTemplates(products);
                                    } catch (Exception e) {
                                    }
                                }
                                if(method.equals("Reset-User")){
                                    String username = payload.substring(0, payload.indexOf("/"));
                                    String data = payload.substring(payload.indexOf("/") + 1);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(data);
                                    while (true) {
                                        String line = is.readLine();
                                        if (line.trim().equals("")){
                                            break;
                                        }
                                        sb.append(line);
                                    }
                                    StringReader sr = new StringReader(sb.toString());
                                    JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
                                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                                    UserList ul = (UserList) unmarshaller.unmarshal(sr);
                                    users.clear();
                                    if (ul != null &&  (ul.getUsers() != null)) {
                                        users = ul.getUsers();
                                    }
                                    try {
                                        adminController.setUsersTable(usersTable, users);
                                        if (username.equals(getCurrentUser().getUsername())) {
                                            Main.getInstance().signOut();
                                        }
                                        setSelectedUser(null);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    } catch (IOException | JAXBException e) {
                    }
                }
            };
            thread.start();
        } catch (Exception e) {
        }
    }

    public void get() throws IOException{
        String message = "Get/";
        write(message);
    }

    public void create(Product product) throws IOException{
        String message;
        StringWriter sw = new StringWriter();
        JAXB.marshal(product, sw);
        String xmlString = sw.toString();
        message = "Create/" + xmlString;
        write(message);
    }

    public void update(String id, Product product) throws IOException{
        String message;
        StringWriter sw = new StringWriter();
        JAXB.marshal(product, sw);
        String xmlString = sw.toString();
        message = "Update/" + id + "/" + xmlString;
        write(message);
    }

    public void delete(String id) throws IOException{
        String message = "Delete/" + id;
        write(message);
    }

    public void getUser() throws IOException{
        String message = "Get-User/";
        write(message);
    }

    public void createUser(User user) throws IOException{
        String message;
        StringWriter sw = new StringWriter();
        JAXB.marshal(user, sw);
        String xmlString = sw.toString();
        message = "Create-User/" + xmlString;
        write(message);
    }

    public void updateUser(String username, User user) throws IOException{
        String message;
        StringWriter sw = new StringWriter();
        JAXB.marshal(user, sw);
        String xmlString = sw.toString();
        message = "Update-User/" + username+ "/" + xmlString;
        write(message);
    }

    public void deleteUser(String username) throws IOException{
        String message = "Delete-User/" + username;
        write(message);
    }

    public List<Product> getProducts(){
        return products;
    }

    public List<User> getUsers() {
        return users;
    }

    public Product getSelectedProduct(){
        return selectedProduct;
    }

    public static void setSelectedProduct(Product product){
        Services.selectedProduct = product;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        Services.currentUser = currentUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public static void setSelectedUser(User selectedUser) {
        Services.selectedUser = selectedUser;
    }

    public Product findById(String id){
        return getProducts().stream()
            .filter(temp -> id.equals(temp.getId()))
            .findAny()
            .orElse(null);
    }

    public Product findByName(String name){
        return getProducts().stream()
            .filter(temp -> name.equals(temp.getName()))
            .findAny()
            .orElse(null);
    }

    public List<Product> sort(Comparator<Product> comparator){
        List<Product> temp = new ArrayList<>();
        temp.addAll(getProducts());
        Collections.sort(temp, comparator);
        return temp;
    }

    public List<Product> filterById(String id){
        return getProducts().stream()
                .filter(product -> product.getId().toLowerCase(vn).contains(id.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByName(String name){
        return getProducts().stream()
                .filter(product -> product.getName().toLowerCase(vn).contains(name.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByCategory(String category){
        return getProducts().stream()
                .filter(product -> product.getCategory().toLowerCase(vn).contains(category.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByManafacturer(String manafacturer){
        return getProducts().stream()
                .filter(product -> product.getManafacturer().toLowerCase(vn).contains(manafacturer.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPrice(BigDecimal minPrice, BigDecimal maxPrice){
        if (maxPrice.compareTo(new BigDecimal("-12345")) > 0){
        return getProducts().stream()
                .filter(product -> product.getPrice().compareTo(maxPrice) <= 0)
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                .collect(Collectors.toList());
        }
        else{
            if (maxPrice.compareTo(new BigDecimal("-12345")) == 0) {
                return getProducts().stream()
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                .collect(Collectors.toList());
            }
            else{
                return getProducts().stream()
                .filter(product -> product.getPrice().compareTo(maxPrice) <= 0)
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                .collect(Collectors.toList());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public List<Product> filterByExpiry(Date dateMin, Date dateMax){
        dateMax.setHours(23);
        dateMax.setMinutes(59);
        dateMax.setSeconds(59);
        return getProducts().stream()
                .filter(product -> product.getExpDate().compareTo(dateMax) <= 0)
                .filter(product -> product.getExpDate().compareTo(dateMin) >= 0)
                .collect(Collectors.toList());
    }
    public User findByUsername(String username){
        return getUsers().stream()
            .filter(temp -> username.equals(temp.getUsername()))
            .findAny()
            .orElse(null);
    }

    @SuppressWarnings("deprecation")
    public List<User> filter(String username, Date dateMin, Date dateMax, boolean read, boolean write){
        List<User> temp = getUsers();
        if (!username.equals("")){
            temp = temp.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase().trim()))
                .collect(Collectors.toList());
        }
        if (dateMin != null && dateMax != null){
            dateMax.setHours(23);
            dateMax.setMinutes(59);
            dateMax.setSeconds(59);
            temp = temp.stream()
                    .filter(user -> user.getRegister().compareTo(dateMin) >= 0)
                    .filter(user -> user.getRegister().compareTo(dateMax) <= 0)
                    .collect(Collectors.toList());
        }
        if (read){
            if (write){
                return temp.stream()
                    .filter(user -> user.isWrite() == write)
                    .collect(Collectors.toList());
            }
            else{
                return temp.stream()
                    .filter(user -> user.isRead() == read)
                    .collect(Collectors.toList());
            }
        }
        else{
            return temp.stream()
                    .filter(user -> user.isRead() == read)
                    .filter(user -> user.isWrite() == write)
                    .collect(Collectors.toList()); 
        }
    }
}
