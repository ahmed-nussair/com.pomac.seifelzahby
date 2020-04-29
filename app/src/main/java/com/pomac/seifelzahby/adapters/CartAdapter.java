package com.pomac.seifelzahby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.model.CartItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> items;

    public CartAdapter(Context context, List<CartItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Picasso.get()
                .load(items.get(position).getProduct().getImagePath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.cartItemImage);
        holder.cartItemName.setText(items.get(position).getProduct().getName());
        holder.cartItemDescription.setText(items.get(position).getProduct().getDescription());
        holder.cartItemPrice.setText(String.format("%sرس", items.get(position).getProduct().getPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView cartItemImage;
        TextView cartItemName;
        TextView cartItemDescription;
        TextView cartItemPrice;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemDescription = itemView.findViewById(R.id.cartItemDescription);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
        }
    }
}
