package com.dovantuan.onthi.Dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbhelper extends SQLiteOpenHelper {

    static String nameDB = "TB_DS";
    static int versionDB = 1;

    public MyDbhelper(Context context) {
        super(context, nameDB, null, versionDB);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_ds = "CREATE TABLE tb_ds_thi ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ngay_thi TEXT(10)," +
                " ca TEXT(10)," +
                " phong TEXT(15)," +
                "ten_mon TEXT (50) UNIQUE NOT NULL)";
        db.execSQL(sql_ds);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS tb_ds_thi");
            onCreate(db);
        }
    }
}
