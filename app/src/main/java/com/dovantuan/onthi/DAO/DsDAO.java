package com.dovantuan.onthi.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dovantuan.onthi.DTO.DSDTO;
import com.dovantuan.onthi.Dbhelper.MyDbhelper;

import java.util.ArrayList;

public class DsDAO {
    MyDbhelper dbHelper;

    public DsDAO(Context context) {
        this.dbHelper = new MyDbhelper(context);
    }

    public ArrayList<DSDTO> getAll(){
        ArrayList<DSDTO>list =new ArrayList<>();
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from tb_ds_thi", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    DSDTO dsdto = new DSDTO();
                    dsdto.setId(cursor.getInt(0));
                    dsdto.setNgaythi(cursor.getString(1));
                    dsdto.setCa(cursor.getString(2));
                    dsdto.setPhong(cursor.getString(3));
                    dsdto.setTenmon(cursor.getString(4));

                    list.add(dsdto);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi", e);
        }
        return list;
    }
    public boolean addRow(DSDTO dsdto) {
        Log.d("zzzz", "add: chưa add" + getAll().size());

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("ngay_thi", dsdto.getNgaythi());
        values.put("ca", dsdto.getCa());
        values.put("phong", dsdto.getPhong());
        values.put("ten_mon", dsdto.getTenmon());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("tb_ds_thi", null,values);

        return check != -1;
    }

    public boolean removeRow(int id){
        SQLiteDatabase sqLiteDatabase =dbHelper.getWritableDatabase();
        int row = sqLiteDatabase.delete("tb_ds_thi", "id = ?", new String[]{String.valueOf(id)});
        return  row!=-1;
    }
    public  boolean updateRow( DSDTO dsdto ){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ngay_thi", dsdto.getNgaythi());
        values.put("ca", dsdto.getCa());
        values.put("phong", dsdto.getPhong());
        values.put("ten_mon", dsdto.getTenmon());

        int check = database.update("tb_ds_thi", values, "id = ?", new String[]{String.valueOf(dsdto.getId())});
        return  check!=-1;
    }
}
