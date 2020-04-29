package com.pomac.seifelzahby.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.view.AppNavigator;

import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity implements AppNavigator {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (menuItem -> {
                    menuItem.setChecked(true);
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.navigation_main:

                            findNavController(findViewById(R.id.nav_host)).navigate(R.id.mainFragment);
                            break;
                        case R.id.navigation_list:
                            findNavController(findViewById(R.id.nav_host)).navigate(R.id.listFragment);
                            break;
                        case R.id.navigation_shopping_cart:
                            findNavController(findViewById(R.id.nav_host)).navigate(R.id.shoppingCartFragment);
                            break;
                        case R.id.navigation_about:
                            findNavController(findViewById(R.id.nav_host)).navigate(R.id.aboutFragment);
                            break;
                    }
                    return false;
                });

    }

    @Override
    public void onSecondaryCategoryItemSelected(int itemId, String itemTitle) {
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", itemId);
        bundle.putString("categoryTitle", itemTitle);
        findNavController(findViewById(R.id.nav_host)).navigate(R.id.productsFragment, bundle);
    }

    @Override
    public void onCheckoutCompleted() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);
        findNavController(findViewById(R.id.nav_host)).navigate(R.id.mainFragment);
    }
}
