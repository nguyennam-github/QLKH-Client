 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.services;

import hvan.qlkh.dao.ProductDAO;
import hvan.qlkh.models.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 *
 * @author PC
 */
public class ProductService{

    private static ProductService instance;
    private static Product selectedProduct;
    @SuppressWarnings("deprecation")
    private Locale vn = new Locale("vi", "VN");

    private ProductService() {
    }

    public static synchronized ProductService getInstance(){
        if(instance == null){
            instance = new ProductService();
        }
        return instance;
    }

    public List<Product> getProducts(){
        return ProductDAO.getInstance().readProducts();
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
