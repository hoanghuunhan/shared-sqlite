package huunhan.hn.com.myaccount;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class WeatherAdapterPager extends FragmentStatePagerAdapter {
    public WeatherAdapterPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return WeatherChildFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
