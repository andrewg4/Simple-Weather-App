package ua.com.prologistic.openweathermapapp.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import ua.com.prologistic.openweathermapapp.fragment.FiveDayForecastFragment;
import ua.com.prologistic.openweathermapapp.fragment.TodayForecastFragment;


public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public static final int TODAY_FORECAST_FRAGMENT_POSITION = 0;
    public static final int FIVE_DAY_FRAGMENT_POSITION = 1;

    private TodayForecastFragment todayForecastFragment;
    private FiveDayForecastFragment fiveDayForecastFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        todayForecastFragment = new TodayForecastFragment();
        fiveDayForecastFragment = new FiveDayForecastFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return todayForecastFragment;
            case 1:
                return fiveDayForecastFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
