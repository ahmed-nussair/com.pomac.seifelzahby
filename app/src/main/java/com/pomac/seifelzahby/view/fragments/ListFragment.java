package com.pomac.seifelzahby.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.adapters.OnCategorySelected;
import com.pomac.seifelzahby.adapters.CategoriesAdapter;
import com.pomac.seifelzahby.adapters.OnSecondaryCategoryItemSelected;
import com.pomac.seifelzahby.adapters.SecondaryCategoriesAdapter;
import com.pomac.seifelzahby.view.AppNavigator;
import com.pomac.seifelzahby.viewmodel.CategoriesViewModel;

import java.util.Locale;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements OnCategorySelected, OnSecondaryCategoryItemSelected {

    private boolean secondaryCategoriesIntialized;
    private AppNavigator appNavigator;

    private RecyclerView secondaryCategoriesRecyclerView;

    private TextView itemsNumber;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        itemsNumber = view.findViewById(R.id.itemsNumber);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        secondaryCategoriesIntialized = false;
        appNavigator = (AppNavigator) getActivity();

        assert getActivity() != null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Globals.ITEMS_NUMBER)) {
            itemsNumber.setVisibility(View.VISIBLE);
            itemsNumber.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Globals.ITEMS_NUMBER, 0)));
        } else {
            itemsNumber.setVisibility(View.GONE);
        }
        CategoriesViewModel model = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        RecyclerView categoriesRecyclerView = getActivity().findViewById(R.id.categoriesRecyclerView);
        secondaryCategoriesRecyclerView = getActivity().findViewById(R.id.secondaryCategoriesRecyclerView);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        secondaryCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        model.getCategoriesResponse().observe(getActivity(), response -> {


            CategoriesAdapter adapter = new CategoriesAdapter(getContext(), response.getData(), this, this);
            categoriesRecyclerView.setAdapter(adapter);

            if (!secondaryCategoriesIntialized) {
                secondaryCategoriesIntialized = true;
                SecondaryCategoriesAdapter secondaryCategoriesAdapter =
                        new SecondaryCategoriesAdapter(getContext(), response.getData().get(0).getSecondaryCategories(), this);
                onCategorySelected(secondaryCategoriesAdapter);
            }


        });

        itemsNumber.setOnClickListener(v -> findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.shoppingCartFragment));
    }

    @Override
    public void onCategorySelected(SecondaryCategoriesAdapter adapter) {

        secondaryCategoriesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(int itemId, String itemTitle) {
        appNavigator.onSecondaryCategoryItemSelected(itemId, itemTitle);
    }
}
