package com.pomac.seifelzahby.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.adapters.OnProductSelected;
import com.pomac.seifelzahby.adapters.ProductsAdapter;
import com.pomac.seifelzahby.viewmodel.ProductsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment implements OnProductSelected {

    private int categoryId;
    private String categoryName;
    private int productId;
    private String productName;
    private String productDescription;
    private String productPrice;
    private String productImagePath;

    private int productQuantity;

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

        productQuantity = 1;

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
        ImageView incrementItems = getActivity().findViewById(R.id.incrementItems);
        ImageView decrementItems = getActivity().findViewById(R.id.decrementItems);
        TextView quantityTextView = getActivity().findViewById(R.id.quantityTextView);
        Button productDetailsButton = getActivity().findViewById(R.id.productDetailsButton);
        RecyclerView relatedProductsRecycleView = getActivity().findViewById(R.id.relatedProductsRecycleView);

        productCategoryMainTitle.setText(categoryName);
        backToProductsList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", categoryId);
            bundle.putString("categoryTitle", categoryName);
            findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.productsFragment, bundle);
        });

        incrementItems.setOnClickListener(v -> {
            productQuantity++;
            quantityTextView.setText(String.format(Locale.US, "%d", productQuantity));
            if (!decrementItems.isEnabled())
                decrementItems.setEnabled(true);
        });

        decrementItems.setOnClickListener(v -> {
            if (productQuantity > 1) {
                decrementItems.setEnabled(true);
                productQuantity--;
                quantityTextView.setText(String.format(Locale.US, "%d", productQuantity));
            } else {
                decrementItems.setEnabled(false);
            }
        });

        productDetailsButton.setOnClickListener(v -> {

        });

        Picasso.get()
                .load(productImagePath)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(productImageView);

        productDesctipionTextView.setText(productDescription);
        productPriceTextView.setText(String.format("%s رس", productPrice));

        relatedProductsRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));

//        relatedProductsRecycleView.setNestedScrollingEnabled(false);

        ProductsViewModel model = ViewModelProviders.of(this).get(ProductsViewModel.class);

        assert getActivity() != null;
        model.getProductsResponse(categoryId).observe(getActivity(), response -> {

            ProductsAdapter adapter = new ProductsAdapter(getContext(), response.getData(), this);
            relatedProductsRecycleView.setAdapter(adapter);

        });
    }

    @Override
    public void onProductSelected(Map<String, String> productData) {
//        Bundle bundle = new Bundle();
//
//        String productIdData = productData.get(Globals.PRODUCT_ID);
//        bundle.putInt(Globals.PRODUCT_ID, productIdData != null ? Integer.parseInt(productIdData) : 0);
//        bundle.putString(Globals.PRODUCT_NAME, productData.get(Globals.PRODUCT_NAME));
//        bundle.putString(Globals.PRODUCT_DESCRIPTION, productData.get(Globals.PRODUCT_DESCRIPTION));
//        bundle.putString(Globals.PRODUCT_IMAGE_PATH, productData.get(Globals.PRODUCT_IMAGE_PATH));
//        bundle.putString(Globals.PRODUCT_PRICE, productData.get(Globals.PRODUCT_PRICE));
//        bundle.putString(Globals.PRODUCT_CATEGORY_NAME, productData.get(Globals.PRODUCT_CATEGORY_NAME));
//        bundle.putInt(Globals.PRODUCT_CATEGORY_ID, categoryId);
//
//        assert getActivity() != null;
//        findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.productDetailsFragment, bundle);
    }
}
