package com.pomac.seifelzahby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.pomac.seifelzahby.R;
import com.squareup.picasso.Picasso;

public class MainSliderAdapter extends PagerAdapter {
    private Context context;

    public MainSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.main_slider_item_layout, container, false);
        ImageView imageView = view.findViewById(R.id.image);


        try {

            Picasso.get()
                    .load(R.drawable.slider_image)
                    .placeholder(R.drawable.slider_image)
                    .error(R.drawable.slider_image)
                    .into(imageView);


        } catch (Exception ex) {
            imageView.setImageResource(R.drawable.slider_image);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
