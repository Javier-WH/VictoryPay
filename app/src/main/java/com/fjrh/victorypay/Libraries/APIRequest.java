package com.fjrh.victorypay.Libraries;

import java.io.IOException;
import java.util.HashMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class APIRequest {
    private final String API_URL = "https://s3.amazonaws.com/dolartoday/data.json";

    public HashMap<String, Double> getUSDValues() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        Response response = client.newCall(request).execute();
        String json = response.body().string();

        JSONObject jsonObject = new JSONObject(json);
        JSONObject usdObject = jsonObject.getJSONObject("USD");

        HashMap<String, Double> usdValues = new HashMap<>();
        usdValues.put("transferencia", usdObject.getDouble("transferencia"));
        usdValues.put("transfer_cucuta", usdObject.getDouble("transfer_cucuta"));
        usdValues.put("efectivo", usdObject.getDouble("efectivo"));
        usdValues.put("efectivo_real", usdObject.getDouble("efectivo_real"));
        usdValues.put("efectivo_cucuta", usdObject.getDouble("efectivo_cucuta"));
        usdValues.put("promedio", usdObject.getDouble("promedio"));
        usdValues.put("promedio_real", usdObject.getDouble("promedio_real"));
        usdValues.put("cencoex", usdObject.getDouble("cencoex"));
        usdValues.put("sicad1", usdObject.getDouble("sicad1"));
        usdValues.put("sicad2", usdObject.getDouble("sicad2"));
        usdValues.put("bitcoin_ref", usdObject.getDouble("bitcoin_ref"));
        usdValues.put("localbitcoin_ref", usdObject.getDouble("localbitcoin_ref"));
        usdValues.put("dolartoday", usdObject.getDouble("dolartoday"));

        return usdValues;
    }
}