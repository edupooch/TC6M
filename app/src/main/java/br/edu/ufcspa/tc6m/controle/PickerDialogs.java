package br.edu.ufcspa.tc6m.controle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by edupooch on 16/02/16.
 */
public class PickerDialogs extends android.support.v4.app.DialogFragment{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DateSettings dateSettings = new DateSettings(getActivity());

        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getActivity(),dateSettings,ano,mes,dia);
        return super.onCreateDialog(savedInstanceState);
    }
}


