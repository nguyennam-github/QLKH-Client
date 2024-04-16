/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author PC
 */
public class BigDecimalConverter {

    private BigDecimalConverter() {
    }

    private static BigDecimal parse(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat decimalFormat) {
            decimalFormat.setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d., ]",""));
    }

    public static BigDecimal currencyParse(String currency) throws ParseException{
        @SuppressWarnings("deprecation")
        Locale vn = new Locale("vi", "VN");
        return parse(currency, vn);
    }
}
