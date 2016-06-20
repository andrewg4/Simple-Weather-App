package ua.com.prologistic.openweathermapapp.util;


import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ua.com.prologistic.openweathermapapp.R;

public class FileHelper {

    public static final String TODAY_FORECAST_FILE = "today.json";
    public static final String FIVE_DAY_FORECAST_FILE = "fiveday.json";

    // save json to local file
    public static void saveToFile(Context context, String fileName, String jsonString) {
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(context.getCacheDir(), fileName);

            outputStream = new FileOutputStream(file);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read json from local file
    public static String readFromFile(Context context, String fileName) {
        if (!isFileExists(context, fileName)) return "";

        StringBuffer buffer = new StringBuffer();
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(context.getCacheDir(), fileName);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static boolean isFileExists(Context context, String fileName) {
        File file = new File(context.getCacheDir(), fileName);
        return file.exists();
    }

    // set drawable for every type of icon
    public static int getResourceId(String icon) {
        if (icon == null) {
            return R.drawable.d01;
        }
        icon = icon.replace("d", "").replace("n", "");
        System.out.println("icon = " + icon);
        switch (icon) {
            case "01":
                return R.drawable.d01;
            case "02":
                return R.drawable.d02;
            case "03":
                return R.drawable.d03;
            case "04":
                return R.drawable.d04;
            case "09":
                return R.drawable.d09;
            case "10":
                return R.drawable.d10;
            case "11":
                return R.drawable.d11;
            case "13":
                return R.drawable.d13;
            case "50":
                return R.drawable.d50;
            default:
                return R.drawable.d01;
        }
    }
}
