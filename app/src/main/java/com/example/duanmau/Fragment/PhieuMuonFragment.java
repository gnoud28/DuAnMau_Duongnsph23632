package com.example.duanmau.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.Adapter.PhieuMuonAdapter;
import com.example.duanmau.DAO.PhieuMuonDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.DTO.PhieuMuon;
import com.example.duanmau.DTO.Sach;
import com.example.duanmau.DTO.ThanhVien;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhieuMuonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhieuMuonFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private PhieuMuonAdapter phieuMuonAdapter;
    PhieuMuonDAO phieuMuonDAO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public PhieuMuonFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PhieuMuonFragment newInstance() {
        PhieuMuonFragment fragment = new PhieuMuonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        recyclerView = view.findViewById(R.id.recyclerQLPM);
        actionButton = view.findViewById(R.id.floataddPM);
        loadData();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemPhieuMuon();
            }
        });

        return view;
    }
    
    private void dialogThemPhieuMuon(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_phieumuon, null);
        EditText edt_maPM = view.findViewById(R.id.ed_tpmuon);
        Spinner spnTVien = view.findViewById(R.id.spn_tvien);
        Spinner spnSach = view.findViewById(R.id.spn_sach);
        EditText edtTien = view.findViewById(R.id.ed_ttien);

        getDataTV(spnTVien);
        getDataSach(spnSach);
        builder.setView(view);

        builder.setPositiveButton("THÊM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String maPM = edt_maPM.getText().toString();
                // Lấy mã thành viên
                HashMap<String, Object> hsMapTV = (HashMap<String, Object>) spnTVien.getSelectedItem();
                String maTV = (String) hsMapTV.get("maTV");
                //Lấy mã sách
                HashMap<String, Object> hashMapSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                String maSach = (String) hashMapSach.get("maSach");

                int tien = Integer.parseInt(edtTien.getText().toString());
                themPhieuMuon(maPM, maTV, maSach, tien);
            }
        });
        builder.setNegativeButton("CANCEL", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void themPhieuMuon(String maPM, String maTV, String maSach, int tien){
        //Lấy mã thủ thư
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maTT = sharedPreferences.getString("maTT", "");
        //Lấy ngày hiện tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(maPM, maTV, maTT, maSach, ngay, "Chưa trả", tien);
        boolean ktra = phieuMuonDAO.themPMuon(phieuMuon);
        if (ktra){
            loadData();
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataTV(Spinner spnTVien){
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> list = thanhVienDAO.getDSTVien();
        ArrayList<HashMap<String, Object>> listHashMap = new ArrayList<>();
        for (ThanhVien tVien : list){
            HashMap<String, Object> hMap = new HashMap<>();
            hMap.put("maTV", tVien.getMaTV());
            hMap.put("TenTV", tVien.getTenTV());
            listHashMap.add(hMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHashMap, android.R.layout.simple_list_item_1, new String[]{"TenTV"}, new int[]{android.R.id.text1});
        spnTVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getDSSach();
        ArrayList<HashMap<String, Object>> listHashMap = new ArrayList<>();
        for (Sach sach : list){
            HashMap<String, Object> hMap = new HashMap<>();
            hMap.put("maSach", sach.getMaSach());
            hMap.put("TenSach", sach.getTenSach());
            hMap.put("GiaThue", sach.getGia());
            listHashMap.add(hMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHashMap, android.R.layout.simple_list_item_1, new String[]{"TenSach"}, new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void loadData(){
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        ArrayList<PhieuMuon> list = phieuMuonDAO.getDSPMuon();

        //set adapter
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), list, phieuMuonDAO);
        recyclerView.setAdapter(phieuMuonAdapter);
    }
}