package com.fjrh.victorypay.Libraries;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class Venezuela {
    Context context;
    String jsonFileContent;

    public Venezuela(Context context){
        this.context = context;

        try {
            jsonFileContent = ReadJson.readFile(context, "venezuela.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getEstados() throws  JSONException {

        ArrayList<String> estados = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonFileContent);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String estado = jsonObject.getString("estado");
            estados.add(estado);
        }

        return estados;
    }

    //

    public ArrayList<String> getMunicipios(String estado) throws  JSONException {
        ArrayList<String> municipios = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(jsonFileContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("estado").equals(estado)) {
                    JSONArray municipiosArray = jsonObject.getJSONArray("municipios");

                    for (int j = 0; j < municipiosArray.length(); j++) {
                        JSONObject municipioObject = municipiosArray.getJSONObject(j);
                        String municipioName = municipioObject.getString("municipio");
                        municipios.add(municipioName);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return municipios;
    }

    //
    public ArrayList<String> getParroquias(String estado, String municipio) throws JSONException {
        ArrayList<String> parroquias = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(jsonFileContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("estado").equals(estado)) {
                    JSONArray municipiosArray = jsonObject.getJSONArray("municipios");
                    
                    for (int j = 0; j < municipiosArray.length(); j++) {
                        
                        JSONObject municipioObject = municipiosArray.getJSONObject(j);
                        
                        if (municipioObject.getString("municipio").equals(municipio)) {
                            
                            JSONArray parroquiasArray = municipioObject.getJSONArray("parroquias");
                            for (int k = 0; k < parroquiasArray.length(); k++) {
                                parroquias.add(parroquiasArray.getString(k));
                            }
                            
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parroquias;
    }

    //
    public ArrayList<String> getCiudades(String estado) throws JSONException {
        ArrayList<String> ciudades = new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(jsonFileContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("estado").equals(estado)) {
                    JSONArray cities = jsonObject.getJSONArray("ciudades");
                    for (int j = 0; j < cities.length(); j++) {
                        ciudades.add(cities.getString(j));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ciudades;
    }


}
