package com.artushock.notes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.artushock.notes.R;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetDateFragment extends Fragment {

    public static final String TAG = "[Arts_SetDateFragment]";

    public static final String REQUEST_KEY_FOR_EDITED_DATE = "REQUEST_KEY_FOR_EDITED_DATE";
    public static final String KEY_EDITED_DATE = "KEY_EDITED_DATE";
    private static final String EDITED_DATE_KEY = "EDITED_DATE_KEY";

    private long date;

    public SetDateFragment() {
    }

    public static Fragment newInstance(long date) {
        SetDateFragment setDateFragment = new SetDateFragment();
        Bundle args = new Bundle();
        args.putLong(EDITED_DATE_KEY, date);
        setDateFragment.setArguments(args);
        Log.d(TAG, "date = " + date);
        return setDateFragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            date = getArguments().getLong(EDITED_DATE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_date, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        Log.d(TAG, "date = " + date);
        DatePicker datePicker = view.findViewById(R.id.set_date_date_picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        Log.d(TAG, "gotten Y,M,D " + year + ", " + month +", " + day + ".");

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                date = calendar.getTimeInMillis();
                Log.d(TAG, "Changed date " + date);
            }
        });



        Button saveDateButton = view.findViewById(R.id.save_date_btn);
        saveDateButton.setOnClickListener(v -> saveDateButtonHandling());
    }

    private void saveDateButtonHandling() {
        //getParentFragmentManager().popBackStack(); //
        Log.d(TAG, "date = " + date);
        Bundle result = new Bundle();
        result.putLong(KEY_EDITED_DATE, date);
        getParentFragmentManager().setFragmentResult(REQUEST_KEY_FOR_EDITED_DATE, result);
        getParentFragmentManager().popBackStack();
    }
}