package com.fjrh.victorypay.Libraries;

import java.text.DecimalFormat;

public class NumberFormater {

    public static String setFormat(Double number, int decimals){

        StringBuilder pattern = new StringBuilder("#.");
        for(int i = 0 ; i < decimals ; i++){
            pattern.append("#");
        }

        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(number);
    }
}
