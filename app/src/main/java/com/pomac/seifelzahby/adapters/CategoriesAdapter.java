package com.pomac.seifelzahby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.seifelzahby.R;
import com.pomac.seifelzahby.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categories;
    private OnCategorySelected onCategorySelected;
    private OnSecondaryCategoryItemSelected onSecondaryCategoryItemSelected;

    public CategoriesAdapter(Context context, List<Category> categories,
                             OnCategorySelected onCategorySelected,
                             OnSecondaryCategoryItemSelected onSecondaryCategoryItemSelected) {
        this.categories = categories;
        this.context = context;
        this.onCategorySelected = onCategorySelected;
        this.onSecondaryCategoryItemSelected = onSecondaryCategoryItemSelected;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Picasso.get()
                .load(categories.get(position).getImagePath())
                .into(holder.categoryImage);

        holder.categoryTitle.setText(categories.get(position).getName());
        holder.categoryItem.setOnClickListener(v -> {
            SecondaryCategoriesAdapter adapter =
                    new SecondaryCategoriesAdapter(context,
                            categories.get(position).getSecondaryCategories(),
                            onSecondaryCategoryItemSelected);
            onCategorySelected.onCategorySelected(adapter);
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryTitle;
        LinearLayout categoryItem;

        CategoryViewHolder(View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryItem = itemView.findViewById(R.id.categoryItem);
        }
    }
}
