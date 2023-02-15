package com.fjrh.victorypay.Libraries;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class Venezuela {


    public static ArrayList<String> getEstados(Context context) throws IOException, JSONException {

        ArrayList<String> estados = new ArrayList<>();

        String jsonFileContent = ReadJson.readFile(context, "venezuela.json");
        JSONArray jsonArray = new JSONArray(jsonFileContent);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String estado = jsonObject.getString("estado");
            estados.add(estado);
        }

        return estados;
    }

    //

    public static ArrayList<String> getMunicipios(Context context, String estado) throws IOException, JSONException {
        ArrayList<String> municipios = new ArrayList<>();
        try {
            String jsonFileContent = ReadJson.readFile(context, "venezuela.json");
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
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return municipios;
    }

    //
    public static ArrayList<String> getParroquias(Context context, String estado, String municipio) throws IOException, JSONException {
        ArrayList<String> parroquias = new ArrayList<>();
        try {
            String jsonFileContent = ReadJson.readFile(context, "venezuela.json");
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
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return parroquias;
    }

    //
    public static ArrayList<String> getCiudades(Context context, String estado) throws IOException, JSONException {
        ArrayList<String> ciudades = new ArrayList<>();
        try {
            String jsonFileContent = ReadJson.readFile(context, "venezuela.json");
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
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return ciudades;
    }


}
