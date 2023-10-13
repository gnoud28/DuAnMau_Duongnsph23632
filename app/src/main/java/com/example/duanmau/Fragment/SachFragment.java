package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.Adapter.LSachAdapter;
import com.example.duanmau.Adapter.SachAdapter;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DTO.LoaiSach;
import com.example.duanmau.DTO.Sach;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SachFragment extends Fragment {

    ArrayList<Sach> arrayList;
    SachDAO sachDAO;
    RecyclerView recyclerView;
    SachAdapter sachAdapter;
    FloatingActionButton actionButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public SachFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SachFragment newInstance() {
        SachFragment fragment = new SachFragment();
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
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        recyclerView = view.findViewById(R.id.recyclerSach);
        actionButton = view.findViewById(R.id.floataddSach);
        loadData();

       actionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialogthemSach();
           }
       });

        return view;
    }

    private void dialogthemSach(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);
        EditText edt_ma = view.findViewById(R.id.ed_them_maSach);
        EditText edt_ten = view.findViewById(R.id.ed_them_tenSach);
        EditText edt_tien = view.findViewById(R.id.ed_them_gia);
        Spinner spinner = view.findViewById(R.id.spn_loaiSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), getDSLoai(), android.R.layout.simple_list_item_1, new String[]{"TenLoai"}, new int[]{android.R.id.text1});
        spinner.setAdapter(simpleAdapter);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String maSach = edt_ma.getText().toString();
                String tenSach = edt_ten.getText().toString();
                int tien = Integer.parseInt(edt_tien.getText().toString());
                HashMap<String, Object> hMap = (HashMap<String, Object>) spinner.getSelectedItem();
                int maLoai = (int) hMap.get("maLoai");

                boolean  check = sachDAO.themSach(maSach, tenSach, tien, maLoai);
                if (check){
                    loadData();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    private ArrayList<HashMap<String, Object>> getDSLoai(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> arrayList = loaiSachDAO.getDSLSach();
        ArrayList<HashMap<String, Object>> listHs = new ArrayList<>();

        for (LoaiSach loaiSach : arrayList){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLoai", loaiSach.getMaLoai());
            hashMap.put("TenLoai", loaiSach.getTenLoai());
            listHs.add(hashMap);
        }
        return listHs;
    }

    private void loadData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        sachDAO = new SachDAO(getContext());
        ArrayList<Sach> arrayList = sachDAO.getDSSach();
        sachAdapter = new SachAdapter(getContext(), arrayList, getDSLoai(), sachDAO);
        recyclerView.setAdapter(sachAdapter);
    }
}