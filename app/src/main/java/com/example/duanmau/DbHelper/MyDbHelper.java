package com.example.duanmau.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(Context context){
        super(context, "DUANMAU", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String ThuThu= "create table ThuThu (" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(ThuThu);

        String insThuThu = "INSERT INTO ThuThu(maTT, hoTen, matKhau) VALUES('admin', 'Nguyen Son D', '12345')";
        db.execSQL(insThuThu);

        String ThanhVien= "create table ThanhVien (" +
                "maTV TEXT PRIMARY KEY , " +
                "TenTV TEXT NOT NULL, " +
                "NamSinh TEXT NOT NULL)";
        db.execSQL(ThanhVien);

        String insThanhVien = "INSERT INTO ThanhVien(maTV, TenTV, NamSinh) VALUES('TV01', 'Nguyen Van A', '1990'), ('TV02', 'Tran Thi B', '1987'), ('TV03', 'Hoang Van C', '2000')";
        db.execSQL(insThanhVien);

        String LoaiSach= "create table LoaiSach (" +
                "maLoai INTEGER PRIMARY KEY, " +
                "TenLoai TEXT NOT NULL )";
        db.execSQL(LoaiSach);

        String insLoaiSach = "INSERT INTO LoaiSach(maLoai, TenLoai) VALUES(1, 'Công Nghệ Thông Tin'), (2, 'Kinh Tế'), (3, 'Kinh Doanh')";
        db.execSQL(insLoaiSach);

        String Sach= "create table Sach (" +
                "maSach TEXT PRIMARY KEY, " +
                "TenSach TEXT NOT NULL, " +
                "GiaThue INTEGER NOT NULL, " +
                "maLS INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(Sach);

        String insSach = "INSERT INTO Sach(maSach, TenSach, GiaTHue, maLS) VALUES('S001', 'Lập trình Java 1', 2000, 1), ('S002', 'Kinh Tế Vĩ Mô', 5000, 2), ('SAT1', 'Chiến lược Marketing', 4675, 3)";
        db.execSQL(insSach);

        //pm.maPhieu, pm.maTV, tv.TenTV, pm.maTT, tt.hoTen, pm.maSach, sc.TenSach, pm.NgayMuon, pm.TrangThai, pm.TienThue
        String PhieuMuon= "create table PhieuMuon (" +
                        "maPhieu TEXT PRIMARY KEY, " +
                        " maTV TEXT REFERENCES Thanhvien(maTV),"+
                        " maTT TEXT REFERENCES ThuThu(maTT),"+
                        " maSach TEXT REFERENCES Sach(maSach),"+
                        " NgayMuon TEXT NOT NULL,"+
                        " TrangThai TEXT NOT NULL,"+
                        " TienThue INTEGER NOT NULL)";
        db.execSQL(PhieuMuon);

        String insPhieuMuon = "INSERT INTO PhieuMuon VALUES('PM01', 'TV01', 'TT01', 'S001', '29/08/2023', 'Chưa trả', 20000)";
        db.execSQL(insPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            String dropThuThu = "DROP TABLE IF EXISTS ThuThu";
            db.execSQL(dropThuThu);
            String dropThanhVien = "DROP TABLE IF EXISTS ThanhVien";
            db.execSQL(dropThanhVien);
            String dropLoaiSach = "DROP TABLE IF EXISTS LoaiSach";
            db.execSQL(dropLoaiSach);
            String dropSach = "DROP TABLE IF EXISTS Sach";
            db.execSQL(dropSach);
            String dropPhieuMuon = "DROP TABLE IF EXISTS PhieuMuon";
            db.execSQL(dropPhieuMuon);

            onCreate(db);
        }
    }
}
