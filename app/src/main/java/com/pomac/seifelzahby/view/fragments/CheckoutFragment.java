package com.pomac.seifelzahby.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.view.AppNavigator;
import com.pomac.seifelzahby.viewmodel.CheckoutViewModel;

import java.util.HashMap;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckoutFragment extends Fragment {

    private ImageView backToShoppingCartScreen;

    private EditText checkoutNameEditText;
    private EditText checkoutMobileEditText;
    private EditText checkoutNotesEditText;
    private EditText checkoutAddressEditText;
    private Button checkoutSendButton;

    public CheckoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        checkoutNameEditText = view.findViewById(R.id.checkoutNameEditText);
        checkoutMobileEditText = view.findViewById(R.id.checkoutMobileEditText);
        checkoutNotesEditText = view.findViewById(R.id.checkoutNotesEditText);
        checkoutAddressEditText = view.findViewById(R.id.checkoutAddressEditText);
        checkoutSendButton = view.findViewById(R.id.checkoutSendButton);

        backToShoppingCartScreen = view.findViewById(R.id.backToShoppingCartScreen);

        assert getActivity() != null;

        AppNavigator appNavigator = (AppNavigator) getActivity();
        backToShoppingCartScreen.setOnClickListener(v -> findNavController(getActivity().findViewById(R.id.nav_host)).navigate(R.id.shoppingCartFragment));

        checkoutSendButton.setOnClickListener(v -> {
            if (checkoutNameEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل اسم المستخدم", Toast.LENGTH_LONG).show();
                return;
            }

            if (checkoutMobileEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل الموبايل", Toast.LENGTH_LONG).show();
                return;
            }

            if (checkoutNotesEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل وقت التوصيل", Toast.LENGTH_LONG).show();
                return;
            }

            if (checkoutAddressEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل عنوان التوصيل", Toast.LENGTH_LONG).show();
                return;
            }
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            String sessionCode = sharedPreferences.getString(Globals.SESSION_CODE, "");

            String name = checkoutNameEditText.getText().toString();
            String address = checkoutAddressEditText.getText().toString();
            String notes = checkoutNotesEditText.getText().toString();
            String mobile = checkoutMobileEditText.getText().toString();

            Map<String, String> checkoutData = new HashMap<>();
            checkoutData.put(Globals.CHECKOUT_NAME, name);
            checkoutData.put(Globals.CHECKOUT_NOTES, notes);
            checkoutData.put(Globals.CHECKOUT_MOBILE, mobile);
            checkoutData.put(Globals.CHECKOUT_ADDRESS, address);

            CheckoutViewModel checkoutViewModel = ViewModelProviders.of(this).get(CheckoutViewModel.class);

            checkoutViewModel.getCheckoutResponse(sessionCode, checkoutData).observe(getActivity(), response -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(Globals.SESSION_CODE);
                if (editor.commit()) {
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
                    appNavigator.onCheckoutCompleted();
                }
            });

        });
        return view;
    }
}
