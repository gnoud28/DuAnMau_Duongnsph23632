package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.Adapter.LSachAdapter;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DTO.LoaiSach;
import com.example.duanmau.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoaiSachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaiSachFragment extends Fragment {
    RecyclerView recyclerView;
    LoaiSachDAO loaiSachDAO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public LoaiSachFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoaiSachFragment newInstance() {
        LoaiSachFragment fragment = new LoaiSachFragment();
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
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        recyclerView = view.findViewById(R.id.recyclerLS);
        EditText ed_ma = view.findViewById(R.id.ed_tmaLoai);
        EditText ed_ten = view.findViewById(R.id.ed_ttenLoai);
        Button btn_Them = view.findViewById(R.id.btn_themLS);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach>arrayList = loaiSachDAO.getDSLSach();
        LSachAdapter adapter = new LSachAdapter(getContext(), arrayList,loaiSachDAO);
        recyclerView.setAdapter(adapter);

        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maloai = Integer.parseInt(ed_ma.getText().toString());
                String tenloai = ed_ten.getText().toString();
                if (loaiSachDAO.themLS(maloai, tenloai)){
                    //Thông báo
                    loadData();
                    ed_ma.setText("");
                    ed_ten.setText("");
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void loadData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach>arrayList = loaiSachDAO.getDSLSach();
        LSachAdapter adapter = new LSachAdapter(getContext(), arrayList,loaiSachDAO);
        recyclerView.setAdapter(adapter);
    }
}