/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.utils;

import hvan.qlkh.models.Product;

/**
 *
 * @author PC
 */
public class Comparator {

    private Comparator() {
    }

    public static class IDComparator implements java.util.Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getId().compareTo(p2.getId());
        }
    }

    public static class NameComparator implements java.util.Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getName().compareTo(p2.getName());
        }
    }

    public static class QuantityUpComparator implements java.util.Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getQuantity() - p2.getQuantity();
        }
    }

    public static class QuantityDownComparator implements java.util.Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p2.getQuantity() - p1.getQuantity();
        }
    }

    public static class PriceUpComparator implements java.util.Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getPrice().compareTo(p2.getPrice());
        }
    }

    public static class PriceDownComparator implements java.util.Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p2.getPrice().compareTo(p1.getPrice());
        }
    }

    public static class ExpiryComparator implements java.util.Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getExpDate().compareTo(p2.getExpDate());
        }
    }

}
