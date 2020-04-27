package com.pomac.seifelzahby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.model.Product;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private Context context;
    private List<Product> products;
    private OnProductSelected onProductSelectedHandler;

    public ProductsAdapter(Context context, List<Product> products, OnProductSelected onProductSelected) {
        this.context = context;
        this.products = products;
        this.onProductSelectedHandler = onProductSelected;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_item_layout, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        Button productDetailsButton;

        ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDetailsButton = itemView.findViewById(R.id.productDetailsButton);
        }
    }
}
