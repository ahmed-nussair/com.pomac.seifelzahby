package com.pomac.seifelzahby.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.adapters.OnProductSelected;
import com.pomac.seifelzahby.adapters.ProductsAdapter;
import com.pomac.seifelzahby.view.AppNavigator;
import com.pomac.seifelzahby.viewmodel.SearchViewModel;

import java.util.Locale;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements OnProductSelected {

    private TextView itemsNumber;
    private ImageView exitSearchResult;
    private TextView noSearchResultTextView;
    private RecyclerView searchResultsRecyclerView;

    private AppNavigator navigator;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        itemsNumber = view.findViewById(R.id.itemsNumber);
        exitSearchResult = view.findViewById(R.id.exitSearchResult);
        noSearchResultTextView = view.findViewById(R.id.noSearchResultTextView);
        searchResultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null && getArguments() != null;
        navigator = (AppNavigator) getActivity();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Globals.ITEMS_NUMBER)) {
            itemsNumber.setVisibility(View.VISIBLE);
            itemsNumber.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Globals.ITEMS_NUMBER, 0)));
        } else {
            itemsNumber.setVisibility(View.GONE);
        }

        itemsNumber.setOnClickListener(v -> navigator.onNavigateToShoppingCart());

        exitSearchResult.setOnClickListener(v -> findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.mainFragment));

        String keyword = getArguments().getString(Globals.SEARCH_KEYWORD);
        SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        assert getActivity() != null;
        searchViewModel.getSearchResponse(keyword).observe(getActivity(), response -> {
            if (response.getData().isEmpty()) {
                searchResultsRecyclerView.setVisibility(View.GONE);
                noSearchResultTextView.setVisibility(View.VISIBLE);
            } else {
                searchResultsRecyclerView.setVisibility(View.VISIBLE);
                noSearchResultTextView.setVisibility(View.GONE);

                ProductsAdapter adapter = new ProductsAdapter(getActivity(), response.getData(), this);
                searchResultsRecyclerView.setAdapter(adapter);
                searchResultsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
            }
        });
    }

    @Override
    public void onProductSelected(Map<String, String> productData) {
        Bundle bundle = new Bundle();

        String productIdData = productData.get(Globals.PRODUCT_ID);
        bundle.putInt(Globals.PRODUCT_ID, productIdData != null ? Integer.parseInt(productIdData) : 0);
        bundle.putString(Globals.PRODUCT_NAME, productData.get(Globals.PRODUCT_NAME));
        bundle.putString(Globals.PRODUCT_DESCRIPTION, productData.get(Globals.PRODUCT_DESCRIPTION));
        bundle.putString(Globals.PRODUCT_IMAGE_PATH, productData.get(Globals.PRODUCT_IMAGE_PATH));
        bundle.putString(Globals.PRODUCT_PRICE, productData.get(Globals.PRODUCT_PRICE));
        bundle.putString(Globals.PRODUCT_CATEGORY_NAME, productData.get(Globals.PRODUCT_CATEGORY_NAME));
//        bundle.putInt(Globals.PRODUCT_CATEGORY_ID, categoryId);

        assert getActivity() != null;
        findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.productDetailsFragment, bundle);
    }
}
