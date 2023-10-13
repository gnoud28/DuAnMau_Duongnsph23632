package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.PhieuMuon;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class PhieuMuonDAO {
    MyDbHelper myDbHelper;
    public PhieuMuonDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    //Lay danh sach
    public ArrayList<PhieuMuon> getDSPMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT pm.maPhieu, pm.maTV, tv.TenTV, pm.maTT, tt.hoTen, pm.maSach, sc.TenSach, pm.NgayMuon, pm.TrangThai, pm.TienThue FROM PhieuMuon pm, ThanhVien tv, ThuThu tt, Sach sc WHERE pm.maTV= tv.maTV and pm.maTT = tt.maTT AND pm.maSach = sc.maSach ORDER BY pm.maPhieu DESC", null);
        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //Thay đổi trạng thái trả sách
    public boolean thaydoiTThai(String maPM){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TrangThai", "Đã trả");
        long check = sqLiteDatabase.update("PhieuMuon", contentValues, "maPhieu=?", new String[]{maPM});
        if (check == -1){
            return false;
        }
        return true;
    }

    //Thêm phiếu mượn
    public boolean themPMuon(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maPhieu", phieuMuon.getMaPM());
        contentValues.put("maTV", phieuMuon.getMaTV());
        contentValues.put("maTT", phieuMuon.getMaTT());
        contentValues.put("maSach", phieuMuon.getMaSach());
        contentValues.put("NgayMuon", phieuMuon.getNgay());
        contentValues.put("TrangThai", phieuMuon.getTrangThai());
        contentValues.put("TienThue", phieuMuon.getTien());

        long check = sqLiteDatabase.insert("PhieuMuon", null, contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }

    //Xoa phiếu mượn
    public boolean xoaPMuon(String maPM){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PhieuMuon WHERE maPhieu=?", new String[]{maPM});
        if (cursor.getCount() != 0){
            return false;
        }
        long check = sqLiteDatabase.delete("PhieuMuon", "maPhieu=?", new String[]{maPM});
        if (check == 0){
            return false;
        }return true;
    }


}
