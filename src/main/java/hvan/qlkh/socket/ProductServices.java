/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.socket;

import hvan.qlkh.models.Product;
import hvan.qlkh.models.ProductList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
 * @author PC
 */
public class ProductServices {
    
    private static ProductServices instance;
    private static Product selectedProduct;
    private static List<Product> products = new ArrayList<>();
    private Locale vn = new Locale("vi", "VN");
    private ProductServices() throws IOException, ClassNotFoundException, JAXBException{
        products = get();
    }
    
    public static ProductServices getInstance() throws IOException, JAXBException, ClassNotFoundException{
        if (instance == null){
            instance = new ProductServices();
        }
        return instance;
    }
    
    public Product getSelectedProduct(){
        return selectedProduct;
    }

    public static void setSelectedProduct(Product product){
        ProductServices.selectedProduct = product;
    }
    
    public List<Product> getProducts(){
        return products;
    }
    
    public List<Product> get() throws IOException, ClassNotFoundException, JAXBException{
        String request;
        String response;
   
        request = "get/";
        //Tạo OutputStream nối với Socket
        try (//
        //Tạo socket cho client kết nối đến server qua ID address và port number
            Socket client = new Socket("localhost", 6543)) {
            //Tạo OutputStream nối với Socket
            ObjectOutputStream oos =
                    new ObjectOutputStream(client.getOutputStream());
            //Tạo inputStream nối với Socket
            ObjectInputStream ois =
                    new ObjectInputStream(client.getInputStream());
            //
            //Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
            oos.writeObject(request);
            //Đọc tin từ Server thông qua InputSteam đã nối với socket
            response = ois.readObject().toString();
            StringReader sr = new StringReader(response);
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ProductList pl = (ProductList) unmarshaller.unmarshal(sr);
            products.clear();
            if (pl != null){
                if (pl.products != null) {
                    products = pl.products;
                }
            }
        }
        return products;
    }

    public List<Product> create(Product product) throws IOException, JAXBException, ClassNotFoundException{
        String request;
        String response;
    
        StringWriter sw = new StringWriter();
        JAXB.marshal(product, sw);
        String xmlString = sw.toString();
        request = "create/" + xmlString;
        //Tạo OutputStream nối với Socket
        try (//
        //Tạo socket cho client kết nối đến server qua ID address và port number
            Socket client = new Socket("localhost", 6543)) {
            //Tạo OutputStream nối với Socket
            ObjectOutputStream oos =
                    new ObjectOutputStream(client.getOutputStream());
            //Tạo inputStream nối với Socket
            ObjectInputStream ois =
                    new ObjectInputStream(client.getInputStream());
            //
            //Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
            oos.writeObject(request);
            //Đọc tin từ Server thông qua InputSteam đã nối với socket
            response = ois.readObject().toString();
            StringReader sr = new StringReader(response);
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ProductList pl = (ProductList) unmarshaller.unmarshal(sr);
            products.clear();
            if (pl != null){
                if (pl.products != null) {
                    products = pl.products;
                }
            }
        }
        return products;
    }
    
    public List<Product> update(String id, Product product) throws IOException, JAXBException, ClassNotFoundException{
        String request;
        String response;
    
        StringWriter sw = new StringWriter();
        JAXB.marshal(product, sw);
        String xmlString = sw.toString();
        request = "update/" + id + "/" + xmlString;
        //Tạo OutputStream nối với Socket
        try (//
        //Tạo socket cho client kết nối đến server qua ID address và port number
            Socket client = new Socket("localhost", 6543)) {
            //Tạo OutputStream nối với Socket
            ObjectOutputStream oos =
                    new ObjectOutputStream(client.getOutputStream());
            //Tạo inputStream nối với Socket
            ObjectInputStream ois =
                    new ObjectInputStream(client.getInputStream());
            //
            //Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
            oos.writeObject(request);
            //Đọc tin từ Server thông qua InputSteam đã nối với socket
            response = ois.readObject().toString();
            StringReader sr = new StringReader(response);
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ProductList pl = (ProductList) unmarshaller.unmarshal(sr);
            products.clear();
            if (pl != null){
                if (pl.products != null) {
                    products = pl.products;
                }
            }
        }
        return products;
    }
    
    public List<Product> delete(String id) throws IOException, JAXBException, ClassNotFoundException{
        String request;
        String response;
    
        request = "delete/" + id;
        //Tạo OutputStream nối với Socket
        try (//
        //Tạo socket cho client kết nối đến server qua ID address và port number
            Socket client = new Socket("localhost", 6543)) {
            //Tạo OutputStream nối với Socket
            ObjectOutputStream oos =
                    new ObjectOutputStream(client.getOutputStream());
            //Tạo inputStream nối với Socket
            ObjectInputStream ois =
                    new ObjectInputStream(client.getInputStream());
            //
            //Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
            oos.writeObject(request);
            //Đọc tin từ Server thông qua InputSteam đã nối với socket
            response = ois.readObject().toString();
            StringReader sr = new StringReader(response);
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ProductList pl = (ProductList) unmarshaller.unmarshal(sr);
            products.clear();
            if (pl != null){
                if (pl.products != null) {
                    products = pl.products;
                }
            }
        }
        return products;
    }
    public Product findById(String id){
        return products.stream()
            .filter(temp -> id.equals(temp.getId()))
            .findAny()
            .orElse(null);
    }

    public Product findByName(String name){
        return products.stream()
            .filter(temp -> name.equals(temp.getName()))
            .findAny()
            .orElse(null);
    }

    public List<Product> sort(Comparator<Product> comparator){
        List<Product> temp = new ArrayList<>();
        temp.addAll(products);
        Collections.sort(temp, comparator);
        return temp;
    }

    public List<Product> filterById(String id){
        return products.stream()
                .filter(product -> product.getId().toLowerCase(vn).contains(id.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByName(String name){
        return products.stream()
                .filter(product -> product.getName().toLowerCase(vn).contains(name.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByCategory(String category){
        return products.stream()
                .filter(product -> product.getCategory().toLowerCase(vn).contains(category.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByManafacturer(String manafacturer){
        return products.stream()
                .filter(product -> product.getManafacturer().toLowerCase(vn).contains(manafacturer.toLowerCase(vn)))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPrice(BigDecimal minPrice, BigDecimal maxPrice){
        if (maxPrice.compareTo(new BigDecimal("-12345")) > 0){
        return products.stream()
                .filter(product -> product.getPrice().compareTo(maxPrice) <= 0)
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                .collect(Collectors.toList());
        }
        else{
            if (maxPrice.compareTo(new BigDecimal("-12345")) == 0) {
                return products.stream()
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                .collect(Collectors.toList());
            }
            else{
                return products.stream()
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
        return products.stream()
                .filter(product -> product.getExpDate().compareTo(dateMax) <= 0)
                .filter(product -> product.getExpDate().compareTo(dateMin) >= 0)
                .collect(Collectors.toList());
    }
}
