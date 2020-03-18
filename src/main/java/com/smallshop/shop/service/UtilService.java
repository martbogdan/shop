package com.smallshop.shop.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilService {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String roundToString (double value, int places) {
        String result = String.valueOf(value);
        while (result.substring(result.indexOf(".")).length()<(places+1)){
            result = result + "0";
        }
        return result;
    }
}
