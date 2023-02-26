package com.fjrh.victorypay.Libraries;

import android.content.Context;

import com.fjrh.victorypay.dataBases.params.Params;

import java.util.HashMap;

public class FetchManager {
    // "http://192.168.1.105:4000";
    private  String defaultProtocol = "http";
    private  String defaultAddress = "192.168.1.105";
    private  String defaultPort = "4000";

    private Context context;
    private Params paramsTable;
    private HashMap<String, String> params;

    public FetchManager(Context context){
        this.context = context;
        paramsTable = new Params(context);
        params = new HashMap<>();
    }

    public void checkFetching(){
        //si no existe una dirección de servidor. entonces genera una por defecto
        if(!params.containsKey("serverProtocol")){
            setServerProtocol(defaultProtocol);
        }
        if(!params.containsKey("serverPort")){
            setServerPort(defaultPort);
        }
        if(!params.containsKey("serverAddress")){
            setServerAddress(defaultAddress);
        }

    }

    public void setServerPort(String port){
        paramsTable.insertParam("serverPort", port);
    }
    public void setServerProtocol(String protocol){
        paramsTable.insertParam("serverProtocol", protocol);
    }
    public void setServerAddress(String address){
        paramsTable.insertParam("serverAddress", address);
    }

    public String getServerPort(){
        params = paramsTable.getParams();
        if(params.containsKey("serverPort")){
            return params.get("serverPort");
        }
        return null;
    }

    public String getServerProtocol(){
        params = paramsTable.getParams();
        if(params.containsKey("serverProtocol")){
            return params.get("serverProtocol");
        }
        return null;
    }

    public String getServerAddress(){
        params = paramsTable.getParams();
        if(params.containsKey("serverAddress")){
            return params.get("serverAddress");
        }
        return null;
    }

    public String getFetchinAddress(){
        params = paramsTable.getParams();
        if(params.containsKey("serverAddress") && params.containsKey("serverProtocol") && params.containsKey("serverPort")){
            return getServerProtocol()+"://"+getServerAddress()+":"+getServerPort();
        }
        return null;
    }

}
