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


public class FiveDayForecastAdapter extends RecyclerView.Adapter<FiveDayForecastAdapter.WeatherViewHolder> {

    List<WeatherModel> items = new ArrayList<>();
    Context context;

    public FiveDayForecastAdapter(Context context, List<WeatherModel> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public FiveDayForecastAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fiveday_weather_row, parent, false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FiveDayForecastAdapter.WeatherViewHolder holder, int position) {
        WeatherModel weatherModel = items.get(position);
        String degree = context.getString(R.string.degree_c);

        holder.dt.setText(weatherModel.getDt());
        String richTempString = String.valueOf(weatherModel.getTemp())
                + degree
                + context.getString(R.string.celsius);;
        holder.temp.setText(richTempString);
        String humidityString = context.getString(R.string.humidity)
                + String.valueOf(weatherModel.getHumidity())
                + context.getString(R.string.percent);
        holder.humidity.setText(humidityString);
        holder.icon.setImageResource(FileHelper.getResourceId(weatherModel.getIcon()));
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView dt, temp, humidity;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            dt = (TextView) itemView.findViewById(R.id.fd_dt);
            temp = (TextView) itemView.findViewById(R.id.fd_temp);
            humidity = (TextView) itemView.findViewById(R.id.fd_humidity);
            icon = (ImageView) itemView.findViewById(R.id.fd_weather_icon);
        }
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
