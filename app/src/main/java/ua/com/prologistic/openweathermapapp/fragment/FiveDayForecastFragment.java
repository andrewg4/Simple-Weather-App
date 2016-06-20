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
import ua.com.prologistic.openweathermapapp.adapter.FiveDayForecastAdapter;
import ua.com.prologistic.openweathermapapp.model.WeatherModel;
import ua.com.prologistic.openweathermapapp.network.ConnectionAgent;
import ua.com.prologistic.openweathermapapp.network.ConnectionHelper;
import ua.com.prologistic.openweathermapapp.util.FileHelper;
import ua.com.prologistic.openweathermapapp.util.JsonParser;
import ua.com.prologistic.openweathermapapp.util.PreferenceHelper;

public class FiveDayForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FiveDayForecastAdapter fiveDayAdapter;
    ConnectionHelper connectionHelper;

    PreferenceHelper prefHelper;
    List<WeatherModel> weatherList = new ArrayList<>();

    public FiveDayForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_five_day_forecast, container, false);
        connectionHelper = new ConnectionHelper(getActivity());

        fiveDayAdapter = new FiveDayForecastAdapter(getActivity(), weatherList);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_five_day_forecast);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fiveDayAdapter);

        updateWeather();
        return rootView;
    }

    public void updateWeather() {
        // if internet connection exists
        if (connectionHelper.isConnectingToInternet()) {

            FetchWeather fetchWeatherObj = new FetchWeather();
            prefHelper = PreferenceHelper.getInstance();

            String city = prefHelper.getString(PreferenceHelper.PREF_KEY_LOCATION);

            fetchWeatherObj.execute(city);

            // read data from local file if internet connection does not exists
        } else {
            if (FileHelper.isFileExists(getActivity(), FileHelper.FIVE_DAY_FORECAST_FILE)) {
                String json = FileHelper.readFromFile(getActivity(), FileHelper.FIVE_DAY_FORECAST_FILE);

                if (json.isEmpty()) {
                    showToast(getActivity().getString(R.string.error_connection_text));
                    return;
                }
                updateAdapter(JsonParser.getFiveDayWeatherData(json));
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    private class FetchWeather extends AsyncTask<String, Void, List<WeatherModel>> {

        ConnectionAgent connectionAgent = new ConnectionAgent();

        @Override
        protected List<WeatherModel> doInBackground(String... city) {
            String json = connectionAgent.getFiveDayWeatherData(city[0]);
            FileHelper.saveToFile(getActivity(), FileHelper.FIVE_DAY_FORECAST_FILE, json);

            return JsonParser.getFiveDayWeatherData(json);
        }

        @Override
        protected void onPostExecute(List<WeatherModel> weatherModels) {
            updateAdapter(weatherModels);
        }
    }

    // update adapter with parsed data
    private void updateAdapter(List<WeatherModel> weatherModels) {
        if (weatherModels.isEmpty()) {
            showToast(getActivity().getString(R.string.error_location_field));
        } else {
            fiveDayAdapter.clear();
            weatherList.addAll(weatherModels);
            fiveDayAdapter.notifyDataSetChanged();
        }
    }


}
