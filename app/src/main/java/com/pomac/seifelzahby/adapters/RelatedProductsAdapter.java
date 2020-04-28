package com.pomac.seifelzahby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.model.Product;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatedProductsAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;
    private OnProductSelected onProductSelectedHandler;

    public RelatedProductsAdapter(Context context, List<Product> products, OnProductSelected handler) {
        this.context = context;
        this.products = products;
        this.onProductSelectedHandler = handler;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.products_item_layout, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.get()
                .load(products.get(position).getImagePath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.productImage);
        holder.productTitle.setText(products.get(position).getName());

        holder.productPrice.setText(String.format("%sرس", products.get(position).getPrice()));

        holder.productDetailsButton.setOnClickListener(v -> {
            Map<String, String> productData = new HashMap<>();

            productData.put(Globals.PRODUCT_ID, String.valueOf(products.get(position).getId()));
            productData.put(Globals.PRODUCT_NAME, products.get(position).getName());
            productData.put(Globals.PRODUCT_DESCRIPTION, products.get(position).getDescription());
            productData.put(Globals.PRODUCT_IMAGE_PATH, products.get(position).getImagePath());
            productData.put(Globals.PRODUCT_PRICE, products.get(position).getPrice());
            productData.put(Globals.PRODUCT_CATEGORY_NAME, products.get(position).getCategoryName());

            onProductSelectedHandler.onProductSelected(productData);
        });
        return view;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        Button productDetailsButton;

        ViewHolder(View itemView) {
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDetailsButton = itemView.findViewById(R.id.productDetailsButton);
        }
    }
}
