package com.pomac.seifelzahby.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;

import java.util.Locale;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private TextView itemsNumber;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        itemsNumber = view.findViewById(R.id.itemsNumber);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getActivity() != null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Globals.ITEMS_NUMBER)) {
            itemsNumber.setVisibility(View.VISIBLE);
            itemsNumber.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Globals.ITEMS_NUMBER, 0)));
        } else {
            itemsNumber.setVisibility(View.GONE);
        }
        itemsNumber.setOnClickListener(v -> findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.shoppingCartFragment));
    }
}
