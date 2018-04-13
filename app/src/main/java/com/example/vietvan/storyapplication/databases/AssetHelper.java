package com.example.vietvan.storyapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by VietVan on 23/03/2018.
 */

public class AssetHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "storyv2.db";
    public static final int VERSION = 1;

    public AssetHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
}
