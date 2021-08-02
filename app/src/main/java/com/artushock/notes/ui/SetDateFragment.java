package com.artushock.notes.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.artushock.notes.R;

import java.util.Calendar;

public class SetDateFragment extends Fragment {

    private Button saveDateButton;
    private DatePicker datePicker;

    public SetDateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_set_date, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        datePicker = view.findViewById(R.id.set_date_date_picker);
        saveDateButton = view.findViewById(R.id.save_date_btn);
        saveDateButton.setOnClickListener(this::saveDateButtonHandling);
    }

    private void saveDateButtonHandling(View v) {
        getParentFragmentManager().popBackStack();

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        Bundle result = new Bundle();
        result.putLong("addNoteDate", calendar.getTimeInMillis());
        getParentFragmentManager().setFragmentResult("requestForAddNoteDate", result);
        getParentFragmentManager().popBackStack();
    }
}