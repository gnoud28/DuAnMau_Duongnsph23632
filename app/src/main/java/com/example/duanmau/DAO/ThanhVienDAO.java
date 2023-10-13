package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.Sach;
import com.example.duanmau.DTO.ThanhVien;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class ThanhVienDAO {
    MyDbHelper myDbHelper;

    public ThanhVienDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    //Lấy danh sách
    public ArrayList<ThanhVien> getDSTVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        return list;
    }

    //Thêm danh sách Thành Viên
    public boolean themTV(String maTV, String TenTV, String NamSinh ){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTV", maTV);
        contentValues.put("TenTV", TenTV);
        contentValues.put("NamSinh", NamSinh);

        long check = database.insert("ThanhVien", null, contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }

    //Cap nhat thanh vien
    public boolean capNhapTV(String maTV, String TenTV, String NamSinh){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenTV", TenTV);
        contentValues.put("NamSinh", NamSinh);
        long check = database.update("ThanhVien", contentValues, "maTV=?", new String[]{maTV});
        if (check == -1){
            return false;
        }
        return true;
    }

    //Xoa thanh vien
    public boolean xoaTV(String maTV){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PhieuMuon WHERE maTV=?", new String[]{maTV});
        if (cursor.getCount() != 0){
            return false;
        }
        long check = sqLiteDatabase.delete("ThanhVien", "maTV = ?", new String[]{maTV});
        if (check == 0){
            return false;
        }
        return true;
    }
}
