 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.services;

import hvan.qlkh.controllers.Controller;
import hvan.qlkh.dao.ProductDAO;
import hvan.qlkh.models.Product;
import hvan.qlkh.models.ProductList;
import hvan.qlkh.utils.Table;
import hvan.qlkh.views.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author PC
 */
public class ProductService{

    private static ProductService instance;
    private static Product selectedProduct;
    private static volatile List<Product> products = new ArrayList<>();
    @SuppressWarnings("deprecation")
    private Locale vn = new Locale("vi", "VN");
    
    private static Thread thread;
    private static volatile BufferedWriter os;
    private static volatile BufferedReader is;
    private int id;
    Controller appController = Main.getInstance().getAppController();
    Table productsTable = Main.getInstance().getProductsTable();
    
    private ProductService() throws IOException {
        Socket socketOfClient = new Socket("localhost", 7777);
        os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
        is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
        setUpSocket();
    }
    
    private void setUpSocket() {
        try {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
//                            // Gửi yêu cầu kết nối tới Server đang lắng nghe
//                            // trên máy 'localhost' cổng 7777.
//                            Socket socketOfClient = new Socket("localhost", 7777);
//                            System.out.println("Kết nối thành công!");
//                            // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
//                            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
//                            // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
//                            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
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
                                    if (pl != null){
                                        if (pl.products != null) {
                                            products = pl.products;
                                        }
                                    }
                                    try {
                                        appController.setProductsTable(productsTable, products);
                                        Main.getInstance().initSort();
                                        Main.getInstance().initStatistic();
                                        Main.getInstance().resetToolbar(true);
                                        Main.getInstance().setProductTemplates(products);
                                    } catch (Exception e) {
                                    }
                                }
                            }

                        }
    //                    os.close();
    //                    is.close();
    //                    socketOfClient.close();
                    } catch (UnknownHostException e) {
                    } catch (IOException e) {
                    } catch (JAXBException ex) {
                        Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            thread.start();
        } catch (Exception e) {
        }
    }
    
    private void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
    public static synchronized ProductService getInstance() throws IOException{
        if(instance == null){
            instance = new ProductService(); 
        }
        return instance;
    }
    
    public void get() throws IOException, JAXBException{
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
    
    public List<Product> getProducts(){
        return products;
    }

    public Product getSelectedProduct(){
        return selectedProduct;
    }

    public static void setSelectedProduct(Product product){
        ProductService.selectedProduct = product;
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

    public List<Product> filterByExpiry(Date dateMin, Date dateMax){
        dateMax.setHours(23);
        dateMax.setMinutes(59);
        dateMax.setSeconds(59);
        return getProducts().stream()
                .filter(product -> product.getExpDate().compareTo(dateMax) <= 0)
                .filter(product -> product.getExpDate().compareTo(dateMin) >= 0)
                .collect(Collectors.toList());
    }
}
