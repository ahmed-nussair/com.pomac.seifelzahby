package com.pomac.seifelzahby.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.adapters.MainSliderAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        ViewPager mainSlider = getActivity().findViewById(R.id.mainSlider);
        TabLayout tabDots = getActivity().findViewById(R.id.tabDots);

        tabDots.setupWithViewPager(mainSlider);
        mainSlider.setAdapter(new MainSliderAdapter(getContext()));


    }
}
