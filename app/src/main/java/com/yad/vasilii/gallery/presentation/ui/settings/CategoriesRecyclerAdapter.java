package com.yad.vasilii.gallery.presentation.ui.settings;

import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import butterknife.*;

public class CategoriesRecyclerAdapter
        extends RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoryViewHolder> {

    private List<Category> mCategories;

    private OnCategoryRecyclerListener mListener;

    public CategoriesRecyclerAdapter() {
        mCategories = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(mCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
        notifyDataSetChanged();
    }

    public void setListener(OnCategoryRecyclerListener listener) {
        mListener = listener;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.delete)
        ImageView mDelete;

        private Category mCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mDelete.setOnClickListener(v -> delete(mCategory));
        }

        public void bind(Category category) {
            mCategory = category;
            mTitle.setText(category.getTitle());
        }
    }

    private void delete(Category category) {
        if (mListener != null) {
            mListener.onDelete(category);
        }
    }

    public interface OnCategoryRecyclerListener {

        void onDelete(Category category);

    }
}
