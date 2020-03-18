package com.smallshop.shop.component;

import com.smallshop.shop.service.UtilService;
import org.springframework.stereotype.Component;

@Component
public class ThymeMath {
    public double multiplyAndRound(double v1, double v2, int places){
        return UtilService.round(v1*v2, places);
    }
    public double multiplyAndRound(double v1, int v2, int places){
        return UtilService.round(v1*v2, places);
    }
    public String multiplyAndRoundStr(double v1, int v2, int places){
        double d = UtilService.round(v1*v2, places);
        return UtilService.roundToString(d, places);
    }
    public String multiplyAndRoundStr(double v1, double v2, int places){
        double d = UtilService.round(v1*v2, places);
        return UtilService.roundToString(d, places);
    }
}
