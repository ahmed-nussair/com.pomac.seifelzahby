package com.pomac.seifelzahby.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.squareup.picasso.Picasso;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    private int categoryId;
    private String categoryName;
    private int productId;
    private String productName;
    private String productDescription;
    private String productPrice;
    private String productImagePath;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;
        categoryId = getArguments().getInt(Globals.PRODUCT_CATEGORY_ID, -1);
        categoryName = getArguments().getString(Globals.PRODUCT_CATEGORY_NAME);
        productId = getArguments().getInt(Globals.PRODUCT_ID, -1);
        productName = getArguments().getString(Globals.PRODUCT_NAME);
        productDescription = getArguments().getString(Globals.PRODUCT_DESCRIPTION);
        productPrice = getArguments().getString(Globals.PRODUCT_PRICE);
        productImagePath = getArguments().getString(Globals.PRODUCT_IMAGE_PATH);

        assert getActivity() != null;
        TextView productCategoryMainTitle = getActivity().findViewById(R.id.productCategoryMainTitle);
        ImageView backToProductsList = getActivity().findViewById(R.id.backToProductsList);
        ImageView productImageView = getActivity().findViewById(R.id.productDetailsImage);
        TextView productDesctipionTextView = getActivity().findViewById(R.id.productDescription);
        TextView productPriceTextView = getActivity().findViewById(R.id.productDetailsPrice);

        productCategoryMainTitle.setText(categoryName);
        backToProductsList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", categoryId);
            bundle.putString("categoryTitle", categoryName);
            findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.productsFragment, bundle);
        });

        Picasso.get()
                .load(productImagePath)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(productImageView);

        productDesctipionTextView.setText(productDescription);
        productPriceTextView.setText(String.format("%s رس", productPrice));
    }
}
