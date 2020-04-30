package com.pomac.seifelzahby.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.adapters.MainSliderAdapter;
import com.pomac.seifelzahby.view.AppNavigator;

import java.util.Locale;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private TextView itemsNumber;
    private TextView searchEditText;
    private ImageView searchImageView;
    private AppNavigator navigator;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        itemsNumber = view.findViewById(R.id.itemsNumber);
        searchEditText = view.findViewById(R.id.searchEditText);
        searchImageView = view.findViewById(R.id.searchImageView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Globals.ITEMS_NUMBER)) {
            itemsNumber.setVisibility(View.VISIBLE);
            itemsNumber.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Globals.ITEMS_NUMBER, 0)));
        } else {
            itemsNumber.setVisibility(View.GONE);
        }
        ViewPager mainSlider = getActivity().findViewById(R.id.mainSlider);
        TabLayout tabDots = getActivity().findViewById(R.id.tabDots);

        tabDots.setupWithViewPager(mainSlider);
        mainSlider.setAdapter(new MainSliderAdapter(getContext()));

        itemsNumber.setOnClickListener(v -> navigator.onNavigateToShoppingCart());

        searchImageView.setOnClickListener(v -> {
            if (!searchEditText.getText().toString().trim().equals("")) {
                Bundle bundle = new Bundle();
                bundle.putString(Globals.SEARCH_KEYWORD, searchEditText.getText().toString());
                findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.searchFragment, bundle);
            }
        });
    }
}
