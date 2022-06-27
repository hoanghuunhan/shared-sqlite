package huunhan.hn.com.myaccount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherChildFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherChildFragment() {
        // Required empty public constructor
    }

    TextView tvWeather;
    ImageView ivWeather;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherChildFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherChildFragment newInstance(String param1, String param2) {
        WeatherChildFragment fragment = new WeatherChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static WeatherChildFragment newInstance(int pos) {
        WeatherChildFragment fragment = new WeatherChildFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String[] status = {"Cloudy", "Few Cloudy", "Rainy", "Storm", "Sunny"};
    int[] imgs = {R.drawable.ic_cloudy,R.drawable.ic_few_clouds,R.drawable.ic_rainy,R.drawable.ic_storm,R.drawable.ic_sunny};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWeather = view.findViewById(R.id.tvWeather);
        tvWeather.setText(status[getArguments().getInt("pos")]);
        ivWeather = view.findViewById(R.id.ivWeather);
        ivWeather.setImageResource(imgs[getArguments().getInt("pos")]);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_child, container, false);
    }
}