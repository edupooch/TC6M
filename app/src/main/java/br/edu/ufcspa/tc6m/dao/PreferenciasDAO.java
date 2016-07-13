package br.edu.ufcspa.tc6m.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by edupooch on 30/04/16.
 */
public class PreferenciasDAO extends SQLiteOpenHelper {

    public PreferenciasDAO(Context context) {
        super(context, "Preferencias", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
