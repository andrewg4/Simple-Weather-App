package ua.com.prologistic.openweathermapapp.model;


public class WeatherModel {
// база з тудуеп
    // джейсон з TheWatherApp
    // UrlConnection з WeatherApp
    //...

    private String weather;
    private String main;
    private String description;
    private String icon;
    private int humidity;
    private int temp;
    private int tempMin;
    private int tempMax;
    private String country;
    private String dt;
    private String city;

    @Override
    public String toString() {
        return "WeatherModel{" +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", humidity=" + humidity +
                ", temp=" + temp +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", country='" + country + '\'' +
                ", dt=" + dt +
                ", city='" + city + '\'' +
                '}';
    }

    public WeatherModel() {
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
