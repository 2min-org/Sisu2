package com.sanjit.sisu2.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.sanjit.sisu2.R;

public class sec_doc extends AppCompatDialogFragment {
    private EditText sec_doc;
    private sec_doc_listener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.spec_doc_layout,null);
        sec_doc = view.findViewById(R.id.sec_doc);
        builder.setView(view)
                .setTitle("Enter Your Specialization")
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("ok", (dialog, which) -> {
                    String spec_doc = sec_doc.getText().toString();
                    listener.applyTexts(spec_doc);
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (sec_doc_listener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context+"must implement sec_doc_listener");
        }
    }

    public interface sec_doc_listener{
        void applyTexts(String spec_doc);
    }
}
