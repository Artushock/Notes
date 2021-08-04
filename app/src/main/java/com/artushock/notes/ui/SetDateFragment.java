package com.artushock.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.artushock.notes.R;

import java.util.Calendar;

public class SetDateFragment extends Fragment {

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
        DatePicker datePicker = view.findViewById(R.id.set_date_date_picker);
        Button saveDateButton = view.findViewById(R.id.save_date_btn);
        saveDateButton.setOnClickListener(v -> saveDateButtonHandling(datePicker));
    }

    private void saveDateButtonHandling(DatePicker datePicker) {
        getParentFragmentManager().popBackStack(); //

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        Bundle result = new Bundle();
        result.putLong(AddNoteFragment.KEY_ADD_NOTE_DATE, calendar.getTimeInMillis());
        getParentFragmentManager().setFragmentResult(AddNoteFragment.REQUEST_KEY_FOR_ADD_NOTE_DATE, result);
        getParentFragmentManager().popBackStack();
    }
}