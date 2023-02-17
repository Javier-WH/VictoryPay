package com.fjrh.victorypay.dataBases;

import java.util.HashMap;

public class Bank {

    public static String getBankName(String code){

        HashMap<String, String> bankList = new HashMap<String, String>();
        bankList.put("0001", "Banco Central de Venezuela");
        bankList.put("0102", "Banco de Venezuela (BDV)");
        bankList.put("0104", "Banco Venezolano de Crédito (BVC)");
        bankList.put("0105", "Banco Mercantil");
        bankList.put("0108", "Banco Provincial (BBVA)");
        bankList.put("0114", "Bancaribe");
        bankList.put("0115", "Banco Exterior");
        bankList.put("0128", "Banco Caroní");
        bankList.put("0134", "Banesco Banco Universal");
        bankList.put("0137", "Sofitasa");
        bankList.put("0138", "Banco Plaza");
        bankList.put("0146", "Bangente");
        bankList.put("0151", "Banco Fondo Común (BFC)");
        bankList.put("0156", "100% Banco");
        bankList.put("0157", "Del Sur Banco Universal");
        bankList.put("0163", "Banco del Tesoro");
        bankList.put("0166", "Banco Agrícola de Venezuela");
        bankList.put("0168", "Bancrecer");
        bankList.put("0169", "Mi Banco, Banco Microfinanciero C.A");
        bankList.put("0171", "Banco Activo");
        bankList.put("0172", "Bancamiga");
        bankList.put("0174", "Banplus");
        bankList.put("0175", "Banco Bicentenario del Pueblo");
        bankList.put("0177", "Banco de la Fuerza Armada Nacional Bolivariana (BANFANB)");
        bankList.put("0191", "Banco Nacional de Crédito (BNC)");

        if(bankList.containsKey(code)) {
            return bankList.get(code);
        }else{
            return "";
        }
    }

}
