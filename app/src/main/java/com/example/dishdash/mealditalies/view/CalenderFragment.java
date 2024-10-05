package com.example.dishdash.mealditalies.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.dishdash.R;

import java.util.Calendar;


public class CalenderFragment extends DialogFragment {
    private OnFoodClickListener listener;

    public void setOnDateSelectedListener(OnFoodClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_calender2, null);

        // Get references to the views
        DatePicker datePicker = view.findViewById(R.id.datePicker);
        ImageButton btnSelectDate = view.findViewById(R.id.btnSelectDate);

        // Create dialog
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(view);

        // Set default date to current date
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

        datePicker.setMinDate(calendar.getTimeInMillis());

        // Handle button click to select date
        btnSelectDate.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            String selectedDate = day + "/" + month + "/" + year;

            // Call the listener to notify the parent fragment
            if (listener != null) {
                listener.onDateSelected(selectedDate);
            }
            dialog.dismiss();
        });

        return dialog;
    }
}
