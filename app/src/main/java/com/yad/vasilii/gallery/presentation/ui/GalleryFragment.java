package com.yad.vasilii.gallery.presentation.ui;

import com.arellomobile.mvp.*;
import com.yad.vasilii.gallery.*;

import android.os.*;
import android.view.*;
import android.widget.*;

public class GalleryFragment extends MvpAppCompatFragment {

    private static final String ARG_TITLE = "arg_title";

    private String mTitle;

//    private OnFragmentInteractionListener mListener;

    public GalleryFragment() {}

    public static GalleryFragment newInstance(String title) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ((TextView) view.findViewById(R.id.title)).setText(mTitle);
        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public String getTitle() {
        return mTitle;
    }

//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
}
