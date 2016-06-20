package ua.com.prologistic.openweathermapapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.com.prologistic.openweathermapapp.R;
import ua.com.prologistic.openweathermapapp.model.WeatherModel;
import ua.com.prologistic.openweathermapapp.util.FileHelper;

public class TodayForecastAdapter extends RecyclerView.Adapter<TodayForecastAdapter.WeatherViewHolder> {

    List<WeatherModel> items = new ArrayList<>();
    Context context;

    public TodayForecastAdapter(Context context, List<WeatherModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public TodayForecastAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_weather_row, parent, false);

        return new WeatherViewHolder(view);
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView cityCountry, dt, main, temp, tempMinMax, humidity;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            cityCountry = (TextView) itemView.findViewById(R.id.t_city_country);
            dt = (TextView) itemView.findViewById(R.id.t_dt);
            main = (TextView) itemView.findViewById(R.id.t_main);
            temp = (TextView) itemView.findViewById(R.id.t_temp);
            tempMinMax = (TextView) itemView.findViewById(R.id.t_min_max);
            humidity = (TextView) itemView.findViewById(R.id.t_humidity);
            icon = (ImageView) itemView.findViewById(R.id.t_weather_icon);
        }
    }

    @Override
    public void onBindViewHolder(TodayForecastAdapter.WeatherViewHolder holder, int position) {
        WeatherModel weatherModel = items.get(position);
        String degree = context.getString(R.string.degree_c);

        String cityCountryText = weatherModel.getCity() + ", " + weatherModel.getCountry();
        holder.cityCountry.setText(cityCountryText);
        holder.dt.setText(weatherModel.getDt());
        holder.main.setText(weatherModel.getMain());
        String richTempString = String.valueOf(weatherModel.getTemp())
                + degree
                + context.getString(R.string.celsius);
        holder.temp.setText(richTempString);
        String humidityString = context.getString(R.string.humidity)
                + String.valueOf(weatherModel.getHumidity())
                + context.getString(R.string.percent);
        holder.humidity.setText(humidityString);
        holder.tempMinMax.setText(String.valueOf(weatherModel.getTempMin() + degree +
                " - " + weatherModel.getTempMax() + degree));
        holder.icon.setImageResource(FileHelper.getResourceId(weatherModel.getIcon()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        if (items.size() > 0) {
            items.clear();
        }
    }
}
