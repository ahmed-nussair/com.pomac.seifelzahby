package com.pomac.seifelzahby.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.adapters.OnProductSelected;
import com.pomac.seifelzahby.adapters.ProductsAdapter;
import com.pomac.seifelzahby.view.AppNavigator;
import com.pomac.seifelzahby.viewmodel.ProductsViewModel;

import java.util.Locale;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements OnProductSelected {

    private int categoryId;
    private TextView itemsNumber;

    private AppNavigator navigator;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        itemsNumber = view.findViewById(R.id.itemsNumber);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;
        categoryId = getArguments().getInt("categoryId", -1);
        String categoryTitle = getArguments().getString("categoryTitle");

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();
        itemsNumber.setOnClickListener(v -> navigator.onNavigateToShoppingCart());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Globals.ITEMS_NUMBER)) {
            itemsNumber.setVisibility(View.VISIBLE);
            itemsNumber.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Globals.ITEMS_NUMBER, 0)));
        } else {
            itemsNumber.setVisibility(View.GONE);
        }
        RecyclerView productsRecyclerView = getActivity().findViewById(R.id.productsRecyclerView);
        TextView categoryTitleTextView = getActivity().findViewById(R.id.categoryMainTitle);
        ImageView back = getActivity().findViewById(R.id.back);

        categoryTitleTextView.setText(categoryTitle);

        back.setOnClickListener(v -> findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.mainFragment));


        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        ProductsViewModel model = ViewModelProviders.of(this).get(ProductsViewModel.class);

        assert getActivity() != null;
        model.getProductsResponse(categoryId).observe(getActivity(), response -> {

            ProductsAdapter adapter = new ProductsAdapter(getContext(), response.getData(), this);
            productsRecyclerView.setAdapter(adapter);

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
        bundle.putInt(Globals.PRODUCT_CATEGORY_ID, categoryId);

        assert getActivity() != null;
        findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.productDetailsFragment, bundle);
    }
}
