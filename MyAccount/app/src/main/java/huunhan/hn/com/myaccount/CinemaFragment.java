package huunhan.hn.com.myaccount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CinemaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CinemaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CinemaFragment() {
        // Required empty public constructor
    }

    RecyclerView rvCinema;
    ArrayList<Cinema> arrayCinema;
    CinemaAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CinemaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CinemaFragment newInstance(String param1, String param2) {
        CinemaFragment fragment = new CinemaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    private void getDataCinema() {
        arrayCinema = new ArrayList<>();
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
        arrayCinema.add(new Cinema("O B L I V I O N", R.drawable.poster));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDataCinema();
        rvCinema = view.findViewById(R.id.rvCinema);
        adapter = new CinemaAdapter(getContext(), arrayCinema);
        rvCinema.setAdapter(adapter);
        rvCinema.setLayoutManager(new GridLayoutManager(getContext(),3));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cinema, container, false);
    }
}