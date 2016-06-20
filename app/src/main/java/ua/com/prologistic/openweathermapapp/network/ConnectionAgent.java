package ua.com.prologistic.openweathermapapp.network;


import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.com.prologistic.openweathermapapp.BuildConfig;

public class ConnectionAgent {

    // openweathermap api key in global gradle.properties file
    public static final String OPENWEATHERMAP_API_KEY = BuildConfig.OPENWEATHERMAP_API_KEY;
    // api urls
    String FIVE_DAY_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    String TODAY_FORECAST_URL = "http://api.openweathermap.org/data/2.5/weather?";
    String FIND_LOCATION_URL = "http://api.openweathermap.org/data/2.5/find?";

    // query params
    String QUERY_PARAM = "q";
    String FORMAT_PARAM = "mode";
    String UNITS_PARAM = "units";
    String DAYS_PARAM = "cnt";
    String API_KEY = "appid";

    String format = "json";
    String units = "metric";
    String count = "5";

    // open the connection to OpenWeatherMap and parse json
    public String connectToUrl(URL url) {
        String json = "";
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            json = inputStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return json;
    }

    // convert inputStream to String
    private String inputStreamToString(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            if (inputStream == null) {
                return "";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            if (builder.length() == 0) {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    public String getTodayWeatherData(String city) {
        try {
            Uri builtUri = buildTodayForecastUri(city);
            String spec = builtUri.toString();
            URL url = new URL(spec);

            return connectToUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getFiveDayWeatherData(String city) {
        try {
            Uri builtUri = buildFiveDayForecastUri(city);
            String spec = builtUri.toString();
            URL url = new URL(spec);

            return connectToUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Uri buildFiveDayForecastUri(String city) {
        return Uri.parse(FIVE_DAY_FORECAST_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAM, city)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(DAYS_PARAM, count)
                .appendQueryParameter(API_KEY, OPENWEATHERMAP_API_KEY)
                .build();
    }

    public Uri buildTodayForecastUri(String city) {
        return Uri.parse(TODAY_FORECAST_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, city)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(API_KEY, OPENWEATHERMAP_API_KEY)
                .build();
    }
}
