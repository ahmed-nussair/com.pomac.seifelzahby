package com.pomac.seifelzahby.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.adapters.CartAdapter;
import com.pomac.seifelzahby.adapters.OnDeleteCartItem;
import com.pomac.seifelzahby.adapters.OnUpdateCartItem;
import com.pomac.seifelzahby.view.activities.MainActivity;
import com.pomac.seifelzahby.viewmodel.CartViewModel;
import com.pomac.seifelzahby.viewmodel.DeletingFromCartViewModel;
import com.pomac.seifelzahby.viewmodel.UpdatingCartViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends Fragment implements OnUpdateCartItem, OnDeleteCartItem {

    private String sessionCode;

    private RecyclerView cartItemsRecyclerView;
    private TextView noItemTextView;
    private TextView errorTextView;
    private MainActivity activity;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        cartItemsRecyclerView = view.findViewById(R.id.cartItemsRecyclerView);
        noItemTextView = view.findViewById(R.id.noItemTextView);
        errorTextView = view.findViewById(R.id.errorTextView);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences = activity.getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Globals.SESSION_CODE)) {

            sessionCode = sharedPreferences.getString(Globals.SESSION_CODE, "");

            CartViewModel cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

            cartViewModel.getCartResponse(sessionCode).observe(activity, response -> {
                CartAdapter adapter = new CartAdapter(getActivity(), response.getData(), this, this);
                cartItemsRecyclerView.setAdapter(adapter);
                cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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

    @Override
    public void updateCartItem(int cartItemId, int quantity) {
        assert getActivity() != null;
        UpdatingCartViewModel updatingCartViewModel = ViewModelProviders.of(this).get(UpdatingCartViewModel.class);
        updatingCartViewModel.getUpdatingCartResponse(cartItemId, quantity, sessionCode)
                .observe(getActivity(), response -> Toast.makeText(getActivity(),
                        response.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public void deleteCartItem(int cartId) {
        assert getActivity() != null;
        DeletingFromCartViewModel deletingFromCartViewModel = ViewModelProviders.of(this).get(DeletingFromCartViewModel.class);
        deletingFromCartViewModel.getDeletingCartItemResponse(cartId, sessionCode)
                .observe(getActivity(), response -> Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show());
    }
}
