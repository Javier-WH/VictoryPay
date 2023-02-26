package com.fjrh.victorypay.Libraries;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadJson {
    private static BufferedReader reader = null;

    public static String readFile(Context context, String fileName) throws IOException {

        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));


        String content = "";
        String line;

        while ((line = reader.readLine()) != null) {
            content = content + line;
        }

        return content;
    }

}
