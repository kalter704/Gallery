package com.yad.vasilii.gallery.presentation.ui.imageview;

import com.squareup.picasso.*;
import com.yad.vasilii.gallery.R;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

public class ImageViewFragment extends Fragment {

    private static final String ARG_URL = "param1";

    private String mImageUrl;

    public ImageViewFragment() {
    }

    public static ImageViewFragment newInstance(String imageUrl) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageUrl = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);

        ImageView imageView = view.findViewById(R.id.image_view);

        Picasso.get().load(mImageUrl).into(imageView);

        return view;
    }
}
