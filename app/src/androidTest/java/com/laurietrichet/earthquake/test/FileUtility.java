package com.laurietrichet.earthquake.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by laurie on 28/10/2014.
 */
public class FileUtility {

    public static String file2String(InputStream file){

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(file));

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return stringBuilder.toString();
    }
}
