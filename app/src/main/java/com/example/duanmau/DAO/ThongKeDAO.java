package com.example.duanmau.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.Sach;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class ThongKeDAO {
    MyDbHelper myDbHelper;

    public ThongKeDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    //Lay top 10
    public ArrayList<Sach> top10(){
        ArrayList<Sach> arrayList  = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.maSach, bs.TenSach, COUNT(pm.maSach) FROM PhieuMuon pm, Sach bs WHERE pm.maSach = bs.maSach GROUP BY pm.maSach, bs.TenSach ORDER BY COUNT(pm.maSach) DESC LIMIT 10", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                arrayList.add(new Sach(cursor.getString(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    public int getDoanhThu(String ngayBD, String ngayKT){
        ngayBD = ngayBD.replace("/", "");
        ngayKT = ngayKT.replace("/", "");
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT SUM(TienThue) FROM PhieuMuon WHERE substr(NgayMuon,7)||substr(NgayMuon,4,2)||substr(NgayMuon,1,2) between ? and ?", new String[]{ngayBD, ngayKT});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
