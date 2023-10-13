package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DbHelper.MyDbHelper;

public class ThuThuDAO {
    MyDbHelper myDbHelper;
    public ThuThuDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    //Hàm đăng nhập
    public boolean dangNhap(String maTT, String matKhau){
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?", new String[]{maTT, matKhau});
        if (cursor.getCount() != 0){
            return true;
        }
        return false;
    }

    //Cập nhật mật khẩu
    public boolean capNhapMK(String user, String mkhaucu, String mkhaumoi){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?", new String[]{user, mkhaucu});
        if (cursor.getCount() > 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matKhau", mkhaumoi);
            long check = database.update("ThuThu", contentValues, "maTT=?", new String[]{user});
            if (check == -1){
                return false;
            }
            return true;
        }
        return false;
    }
}
