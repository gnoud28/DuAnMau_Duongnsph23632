package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.LoaiSach;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class LoaiSachDAO {
    MyDbHelper myDbHelper;

    public LoaiSachDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    //Lấy danh sách
    public ArrayList<LoaiSach> getDSLSach(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM LoaiSach", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    //Thêm
    public boolean themLS(int maLoai, String tenLoai){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", maLoai);
        contentValues.put("TenLoai", tenLoai);
        long check = database.insert("LoaiSach", null, contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }


    //Xoa loại sách
    //1-xoa thanh cong, 0-xoa that bai, -1-sach ton tai trong bang sach
    public int xoaLSach(int maLoai){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Sach WHERE maLS=?", new String[]{String.valueOf(maLoai)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = database.delete("LoaiSach", "maLoai=?", new String[]{String.valueOf(maLoai)});
        if (check == 0){
            return 0;
        }
        return 1;
    }

    //Sua loai sach
    public boolean SuaLSach(int maLoai, String TenLoai){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai", TenLoai);
        long check = sqLiteDatabase.update("LoaiSach", contentValues, "maLoai=?", new String[]{String.valueOf(maLoai)});
        if (check == -1){
            return false;
        }
        return true;
    }

}
