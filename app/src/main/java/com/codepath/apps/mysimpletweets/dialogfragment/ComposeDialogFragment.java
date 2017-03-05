package com.codepath.apps.mysimpletweets.dialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;

/**
 * Created by kelly79126 on 2017/3/5.
 */

public class ComposeDialogFragment extends DialogFragment implements Button.OnClickListener {

    EditText etStatus;
    TextView tvCharacterCount;
    Button btnSave;
    Button btnCancel;
    public static final int CHARACTER_LIMIT = 140;

    public interface ComposeDialogListener {
        void onFinishComposeDialog(String status);
    }

    public ComposeDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ComposeDialogFragment newInstance(String title) {
        ComposeDialogFragment frag = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvCharacterCount.setText(String.valueOf(CHARACTER_LIMIT - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_compose, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etStatus = (EditText) view.findViewById(R.id.et_status);
        tvCharacterCount = (TextView) view.findViewById(R.id.tv_character_count);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        etStatus.addTextChangedListener(mTextEditorWatcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_save:
                ComposeDialogListener listener = (ComposeDialogListener) getActivity();
                listener.onFinishComposeDialog(etStatus.getText().toString());
                dismiss();
                break;
        }
    }
}
