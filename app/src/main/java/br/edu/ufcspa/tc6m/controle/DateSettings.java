package br.edu.ufcspa.tc6m.controle;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by edupooch on 16/02/16.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener {

    Context context;

    public DateSettings(Context context) {
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(context, "Data:" + dayOfMonth + "/" + monthOfYear + "/" + year, Toast.LENGTH_LONG).show();

    }
}
