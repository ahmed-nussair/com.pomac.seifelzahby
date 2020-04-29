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
import com.pomac.seifelzahby.adapters.CartAdapter;
import com.pomac.seifelzahby.viewmodel.CartViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends Fragment {

    public ShoppingCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        RecyclerView cartItemsRecyclerView = getActivity().findViewById(R.id.cartItemsRecyclerView);
        TextView noItemTextView = getActivity().findViewById(R.id.noItemTextView);
        TextView errorTextView = getActivity().findViewById(R.id.errorTextView);

        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Globals.SESSION_CODE)) {

            String sessionCode = sharedPreferences.getString(Globals.SESSION_CODE, "");

            CartViewModel cartViewModel = ViewModelProviders.of(getActivity()).get(CartViewModel.class);

            cartViewModel.getCartResponse(sessionCode).observe(getActivity(), response -> {
                CartAdapter adapter = new CartAdapter(getActivity(), response.getData());
                cartItemsRecyclerView.setAdapter(adapter);
            });

            cartItemsRecyclerView.setVisibility(View.VISIBLE);
            noItemTextView.setVisibility(View.GONE);
            errorTextView.setVisibility(View.GONE);

        } else {
            cartItemsRecyclerView.setVisibility(View.GONE);
            noItemTextView.setVisibility(View.VISIBLE);
            errorTextView.setVisibility(View.GONE);
        }

    }
}
