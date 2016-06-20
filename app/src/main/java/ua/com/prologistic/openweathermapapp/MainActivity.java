package ua.com.prologistic.openweathermapapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ua.com.prologistic.openweathermapapp.adapter.TabAdapter;
import ua.com.prologistic.openweathermapapp.dialog.LocationDialogFragment;
import ua.com.prologistic.openweathermapapp.fragment.FiveDayForecastFragment;
import ua.com.prologistic.openweathermapapp.fragment.TodayForecastFragment;
import ua.com.prologistic.openweathermapapp.util.PreferenceHelper;

public class MainActivity extends AppCompatActivity implements LocationDialogFragment.LocationChangeListener {

    FragmentManager fragmentManager;
    PreferenceHelper preferenceHelper;

    TodayForecastFragment todayForecastFragment;
    FiveDayForecastFragment fiveDayForecastFragment;

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        preferenceHelper = PreferenceHelper.getInstance();
        preferenceHelper.init(this);

        fragmentManager = getFragmentManager();

        setUI();
    }

    private void setUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.today_forecast));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.five_day_forecast));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabAdapter tabAdapter = new TabAdapter(fragmentManager, 2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        todayForecastFragment = (TodayForecastFragment) tabAdapter.getItem(TabAdapter.TODAY_FORECAST_FRAGMENT_POSITION);
        fiveDayForecastFragment = (FiveDayForecastFragment) tabAdapter.getItem(TabAdapter.FIVE_DAY_FRAGMENT_POSITION);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            LocationDialogFragment locationDialogFragment = new LocationDialogFragment();
            locationDialogFragment.show(fragmentManager, "LocationDialogFragment");
            return true;
        }
        if (id == R.id.refresh) {
            onLocationChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public void onLocationChanged() {
        todayForecastFragment.updateWeather();
        fiveDayForecastFragment.updateWeather();
        Snackbar.make(coordinatorLayout, "Refreshed", Snackbar.LENGTH_LONG).show();
    }
}
