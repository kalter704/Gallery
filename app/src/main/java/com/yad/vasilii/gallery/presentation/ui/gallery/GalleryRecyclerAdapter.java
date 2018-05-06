package com.yad.vasilii.gallery.presentation.ui.gallery;

import com.squareup.picasso.*;
import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.domain.models.*;

import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import butterknife.*;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter {

    private final int TYPE_ITEM = 1;

    private final int TYPE_LOADING = 2;

    private final int TYPE_ERROR = 3;

    private final int LOAD_MORE_NUMBER_ITEMS_BEFORE_END = 7;

    private List<Image> mImages;

    private int mImageHeight;

    private boolean mWithFooter;

    private boolean mError;

    private final Picasso mPicasso;

    private OnLoadMoreListener mLoadMoreListener;

    private OnItemClickListener mOnItemClickListener;

    public GalleryRecyclerAdapter(Picasso picasso) {
        init();
        mPicasso = picasso;
    }

    public GalleryRecyclerAdapter(Picasso picasso, boolean withFooter) {
        init();
        mPicasso = picasso;
        mWithFooter = withFooter;
    }

    private void init() {
        mImages = new ArrayList<>();
        mWithFooter = false;
        mError = false;
        mImageHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_LOADING) {
            view = inflateView(parent, R.layout.item_list_load_more);
            return new LoadMoreViewHolder(view);
        } else if (viewType == TYPE_ERROR) {
            view = inflateView(parent, R.layout.item_try_again);
            return new ErrorViewHolder(view, mLoadMoreListener);
        } else {
            view = inflateView(parent, R.layout.item_gallery_list, mImageHeight);
            return new GalleryViewHolder(view, mOnItemClickListener, mPicasso);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            ((GalleryViewHolder) holder).bind(mImages.get(position));
        }
        if (position >= (getItemCount() - LOAD_MORE_NUMBER_ITEMS_BEFORE_END)
                && mLoadMoreListener != null && !mError) {
            mLoadMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        int size = mImages.size();
        if (mWithFooter) {
            size++;
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithFooter && isPositionFooter(position)) {
            if (mError) {
                return TYPE_ERROR;
            } else {
                return TYPE_LOADING;
            }
        }
        return TYPE_ITEM;
    }

    private View inflateView(ViewGroup parent, @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    private View inflateView(ViewGroup parent, @LayoutRes int layout, int height) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(layoutParams);

        return view;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setWithFooter(boolean withFooter) {
        mWithFooter = withFooter;
    }

    public boolean isPositionFooter(int position) {
        return mWithFooter && (getItemCount() - 1) == position;
    }

    public void addImages(List<Image> images) {
        mImages.addAll(images);
        notifyDataSetChanged();
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImageHeight(int imageHeight) {
        mImageHeight = imageHeight;
    }

    public void showNoNetwork() {
        mError = true;
        notifyDataSetChanged();
    }

    public void showLoading() {
        mError = false;
        notifyDataSetChanged();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView mImageView;

        private final Picasso mPicasso;

        public GalleryViewHolder(View itemView, OnItemClickListener clickListener,
                Picasso picasso) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickListener != null) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
            mPicasso = picasso;
        }

        public void bind(Image image) {
            mPicasso.load(image.getPreviewImageUrl())
                    .placeholder(R.drawable.ic_wallpaper)
                    .error(R.drawable.ic_error_outline)
                    .into(mImageView);
        }
    }

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ErrorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.try_again)
        Button mTryAgain;

        public ErrorViewHolder(View itemView, OnLoadMoreListener loadMoreListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTryAgain.setOnClickListener((v) -> {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
            });
        }
    }

    public interface OnLoadMoreListener {

        void onLoadMore();

    }

    public interface OnItemClickListener {

        void onClick(int position);

    }
}
