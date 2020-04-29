package com.pomac.seifelzahby.adapters;

import android.content.Context;
import android.util.Log;
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
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> items;
    private OnUpdateCartItem updateHandler;
    private OnDeleteCartItem deleteHandler;

    public CartAdapter(Context context, List<CartItem> items, OnUpdateCartItem handler, OnDeleteCartItem deleteHandler) {
        this.context = context;
        this.items = items;
        this.updateHandler = handler;
        this.deleteHandler = deleteHandler;
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
        holder.quantityTextView.setText(items.get(position).getQuantity());
        holder.incrementItems.setOnClickListener(v -> {
            int qunatity = Integer.parseInt(holder.quantityTextView.getText().toString());
            qunatity++;
            holder.quantityTextView.setText(String.format(Locale.US, "%d", qunatity));
            updateHandler.updateCartItem(items.get(position).getId(), qunatity);

        });

        holder.decrementItems.setOnClickListener(v -> {
            int qunatity = Integer.parseInt(holder.quantityTextView.getText().toString());
            if (qunatity > 1) {
                qunatity--;
            }
            holder.quantityTextView.setText(String.format(Locale.US, "%d", qunatity));
            updateHandler.updateCartItem(items.get(position).getId(), qunatity);
//            notifyItemChanged(position);
//            notifyItemRangeChanged(position, items.size());

        });

        holder.removeCartItem.setOnClickListener(v -> {
            deleteHandler.deleteCartItem(items.get(position).getId());
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        });
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
        TextView quantityTextView;
        ImageView incrementItems;
        ImageView decrementItems;
        ImageView removeCartItem;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemDescription = itemView.findViewById(R.id.cartItemDescription);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            incrementItems = itemView.findViewById(R.id.incrementItems);
            decrementItems = itemView.findViewById(R.id.decrementItems);
            removeCartItem = itemView.findViewById(R.id.removeCartItem);
        }
    }
}
