package ua.com.prologistic.openweathermapapp.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.com.prologistic.openweathermapapp.model.WeatherModel;

public class JsonParser {

    // openweathermap forecast constants
    private static final String SYS = "sys";
    private static final String COUNTRY = "country";
    private static final String DT = "dt";
    private static final String NAME = "name";
    private static final String WEATHER = "weather";
    private static final String DESCRIPTION = "description";
    private static final String MAIN_WEATHER = "main";
    private static final String ICON = "icon";
    private static final String MAIN = "main";
    private static final String HUMIDITY = "humidity";

    private static final String TEMP = "temp";
    private static final String TEMP_MIN = "temp_min";
    private static final String TEMP_MAX = "temp_max";
    public static final String MIN = "min";
    public static final String MAX = "max";
    public static final String DAY = "day";

    public static final String LIST = "list";
    public static final String CITY = "city";

    // parse json data from input String
    public static WeatherModel getWeatherData(String json) {
        WeatherModel weather = new WeatherModel();
        if (checkJsonString(json)) {
            return weather;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);

            // get sys info object
            JSONObject sysObj = jsonObject.getJSONObject(SYS);
            weather.setCountry(sysObj.getString(COUNTRY));
            weather.setCity(jsonObject.getString(NAME));

            // convert unix timestamp to valid date
            weather.setDt(getValidDateString(jsonObject.getLong(DT)));

            JSONArray jsonArray = jsonObject.getJSONArray(WEATHER);

            // array with just 1 element
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.setDescription(jsonWeather.getString(DESCRIPTION));
            weather.setMain(jsonWeather.getString(MAIN_WEATHER));
            weather.setIcon(jsonWeather.getString(ICON));

            // main forecast object
            JSONObject mainObj = jsonObject.getJSONObject(MAIN);
            weather.setHumidity(mainObj.getInt(HUMIDITY));
            weather.setTempMin((int) mainObj.getDouble(TEMP_MIN));
            weather.setTempMax((int) mainObj.getDouble(TEMP_MAX));
            weather.setTemp((int) mainObj.getDouble(TEMP));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weather;
    }

    // parse json data
    public static List<WeatherModel> getFiveDayWeatherData(String json) {
        List<WeatherModel> weatherList = new ArrayList<>();
        if (checkJsonString(json)) {
            return weatherList;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);

            // get common data for each day
            JSONObject city = jsonObject.getJSONObject(CITY);
            String cityName = city.getString(NAME);
            String countryName = city.getString(COUNTRY);

            // foreach day in list
            JSONArray jsonArray = jsonObject.getJSONArray(LIST);
            for (int i = 0; i < jsonArray.length(); i++) {
                WeatherModel weatherModel = new WeatherModel();
                JSONObject jsonDayObject = jsonArray.getJSONObject(i);

                // convert unix timestamp to valid date
                weatherModel.setDt(getValidDateString(jsonDayObject.getLong(DT)));

                // get first element from 'weather' array
                JSONObject weatherObject = jsonDayObject.getJSONArray(WEATHER).getJSONObject(0);
                weatherModel.setDescription(weatherObject.getString(DESCRIPTION));
                weatherModel.setMain(weatherObject.getString(MAIN_WEATHER));
                weatherModel.setIcon(weatherObject.getString(ICON));

                weatherModel.setHumidity(jsonDayObject.getInt(HUMIDITY));

                // get data from 'temp' forecast object
                JSONObject tempObject = jsonDayObject.getJSONObject(TEMP);
                weatherModel.setTempMin((int) (tempObject.getDouble(MIN)));
                weatherModel.setTempMax((int) (tempObject.getDouble(MAX)));
                weatherModel.setTemp((int) tempObject.getDouble(DAY));

                // add common data
                weatherModel.setCity(cityName);
                weatherModel.setCountry(countryName);

                weatherList.add(weatherModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherList;
    }

    // convert unix timestamp to valid date
    public static String getValidDateString(long time) {
        int secondsToMillisecondValue = 1000;
        Date date = new Date(time * secondsToMillisecondValue);
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMMM d");

        return format.format(date);
    }

    public static boolean checkJsonString(String json) {
        return json.isEmpty() || json.contains("Not found city");
    }


}
