package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.Sach;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class SachDAO{
    MyDbHelper myDbHelper;
    public SachDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    //Lấy danh sách
    public ArrayList<Sach> getDSSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT bs.maSach, bs.TenSach, bs.GiaThue, bs.maLS, ls.TenLoai FROM Sach bs, LoaiSach ls WHERE bs.maLS = ls.maLoai", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //Thêm
    public boolean themSach(String maSach, String TenSach, int TienThue, int maLS){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSach", maSach);
        contentValues.put("TenSach", TenSach);
        contentValues.put("GiaTHue", TienThue);
        contentValues.put("maLS", maLS);
        long check = database.insert("Sach", null, contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }

    //CAp nhật sách
    public boolean capNhatSach(String maSach, String tenSach, int tienThue, int maLoai){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenSach", tenSach);
        contentValues.put("GiaTHue", tienThue);
        contentValues.put("maLS", maLoai);

        long check = sqLiteDatabase.update("Sach", contentValues, "maSach=?", new String[]{maSach});
        if (check == -1){
            return false;
        }
        return true;
    }

    //Xoa sach
    public boolean xoaSach(String maSach){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PhieuMuon WHERE maSach=?", new String[]{maSach});
        if (cursor.getCount() != 0){
            return false;
        }
        long check = sqLiteDatabase.delete("Sach", "maSach=?", new String[]{maSach});
        if (check == 0){
            return false;
        }return true;
    }
}
