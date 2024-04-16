/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable{

    private static final long serialVersionUID = 1L;
    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "category")
    private String category;
    @XmlElement(name = "quantity")
    private int quantity;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "exp-date")
    private Date expDate;
    @XmlElement(name = "manafacturer")
    private String manafacturer;
    @XmlElement(name = "thumbnail")
    private String thumbnail;
    @XmlElement(name = "description")
    private String description;
    public Product() {
    }

    public Product(String id, String name, String category, int quantity, BigDecimal price, Date expDate, String manafacturer) {
        super();
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.expDate = expDate;
        this.manafacturer = manafacturer;
    }

    public Product(String id, String name, String category, int quantity, BigDecimal price, Date expDate, String manafacturer, String thumbnail, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.expDate = expDate;
        this.manafacturer = manafacturer;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getManafacturer() {
        return manafacturer;
    }

    public void setManafacturer(String manafacturer) {
        this.manafacturer = manafacturer;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
