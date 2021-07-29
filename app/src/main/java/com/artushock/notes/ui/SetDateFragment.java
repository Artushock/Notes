package com.artushock.notes.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.artushock.notes.R;

public class SetDateFragment extends Fragment {

    private Button saveDateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_set_date, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        saveDateButton = view.findViewById(R.id.save_date_btn);
        saveDateButton.setOnClickListener(this::saveDateButtonHandling);
    }

    private void saveDateButtonHandling(View v) {
        //TODO
        Toast.makeText(getContext(), "From saveDateButtonHandling", Toast.LENGTH_SHORT).show();
    }
}