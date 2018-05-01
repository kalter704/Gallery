package com.yad.vasilii.gallery.presentation.ui.settings;


import com.yad.vasilii.gallery.R;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.widget.*;

import butterknife.*;

public class InputCategoryDialogFragment extends DialogFragment {

    @BindView(R.id.title)
    EditText mTitle;

    private OnDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input_category, null);
        ButterKnife.bind(this, view);
        return new AlertDialog.Builder(getContext()).setView(view)
                .setPositiveButton(R.string.add_button, (dialog, which) -> {
                    if (mListener != null) {
                        mListener.add(mTitle.getText().toString());
                    }
                }).setNegativeButton(R.string.cancel_button, (dialog, which) -> {
                    onCancel(dialog);
                    if (mListener != null) {
                        mListener.cancel();
                    }
                }).create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogListener) {
            mListener = (OnDialogListener) context;
        }
    }

    public interface OnDialogListener {

        void add(String title);

        void cancel();

    }

}
