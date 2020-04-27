package com.pomac.seifelzahby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.model.SecondaryCategory;

import java.util.List;

public class SecondaryCategoriesAdapter extends RecyclerView.Adapter<SecondaryCategoriesAdapter.SecondaryCategoriesViewHolder> {

    private Context context;
    private List<SecondaryCategory> list;
    private OnSecondaryCategoryItemSelected onSecondaryCategoryItemSelected;

    public SecondaryCategoriesAdapter(Context context, List<SecondaryCategory> list,
                                      OnSecondaryCategoryItemSelected onSecondaryCategoryItemSelected) {
        this.context = context;
        this.list = list;
        this.onSecondaryCategoryItemSelected = onSecondaryCategoryItemSelected;
    }

    @NonNull
    @Override
    public SecondaryCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.secondary_category_item_layout, parent, false);
        return new SecondaryCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondaryCategoriesViewHolder holder, int position) {
        holder.title.setText(list.get(position).getName());
        holder.item.setOnClickListener(v -> onSecondaryCategoryItemSelected.onItemSelected(list.get(position).getId(), list.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SecondaryCategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RelativeLayout item;

        SecondaryCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemTitle);
            item = itemView.findViewById(R.id.item);
        }
    }
}
