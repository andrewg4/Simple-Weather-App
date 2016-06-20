package ua.com.prologistic.openweathermapapp.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.com.prologistic.openweathermapapp.R;
import ua.com.prologistic.openweathermapapp.adapter.TodayForecastAdapter;
import ua.com.prologistic.openweathermapapp.model.WeatherModel;
import ua.com.prologistic.openweathermapapp.network.ConnectionAgent;
import ua.com.prologistic.openweathermapapp.network.ConnectionHelper;
import ua.com.prologistic.openweathermapapp.util.FileHelper;
import ua.com.prologistic.openweathermapapp.util.JsonParser;
import ua.com.prologistic.openweathermapapp.util.PreferenceHelper;

public class TodayForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TodayForecastAdapter todayAdapter;

    ConnectionHelper connectionHelper;
    PreferenceHelper prefHelper;
    List<WeatherModel> weatherList = new ArrayList<>();

    public TodayForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today_forecast, container, false);
        connectionHelper = new ConnectionHelper(getActivity());

        todayAdapter = new TodayForecastAdapter(getActivity(), weatherList);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_today_forecast);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(todayAdapter);

        updateWeather();
        return rootView;
    }

    public void updateWeather() {
        // if internet connection exist
        if (connectionHelper.isConnectingToInternet()) {
            FetchWeather fetchWeatherObj = new FetchWeather();
            prefHelper = PreferenceHelper.getInstance();

            String city = prefHelper.getString(PreferenceHelper.PREF_KEY_LOCATION);

            fetchWeatherObj.execute(city);

            // read data from local file if internet connection does not exists
        } else {
            if (FileHelper.isFileExists(getActivity(), FileHelper.TODAY_FORECAST_FILE)) {
                String json = FileHelper.readFromFile(getActivity(), FileHelper.TODAY_FORECAST_FILE);

                if (json.isEmpty()) {
                    showToast(getActivity().getString(R.string.error_connection_text));
                    return;
                }
                updateAdapter(JsonParser.getWeatherData(json));
            }
        }
    }


    private class FetchWeather extends AsyncTask<String, Void, WeatherModel> {

        ConnectionAgent connectionAgent = new ConnectionAgent();

        @Override
        protected WeatherModel doInBackground(String... city) {
            String json = connectionAgent.getTodayWeatherData(city[0]);
            FileHelper.saveToFile(getActivity(), FileHelper.TODAY_FORECAST_FILE, json);

            return JsonParser.getWeatherData(json);
        }

        @Override
        protected void onPostExecute(WeatherModel weatherModel) {
            updateAdapter(weatherModel);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    // update adapter with parsed data
    private void updateAdapter(WeatherModel weatherModel) {
        if (weatherModel.getDt() == null) {
            showToast(getActivity().getString(R.string.error_location_field));
        } else {
            todayAdapter.clear();
            weatherList.add(weatherModel);
            todayAdapter.notifyDataSetChanged();
        }
    }


}
