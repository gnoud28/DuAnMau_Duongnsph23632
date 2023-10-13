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
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.Adapter.ThanhVienAdapter;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.DTO.ThanhVien;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThanhVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThanhVienFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ThanhVien>arrayList;
    ThanhVienDAO thanhVienDAO;
    ThanhVienAdapter thanhVienAdapter;
    FloatingActionButton actionButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public ThanhVienFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ThanhVienFragment newInstance() {
        ThanhVienFragment fragment = new ThanhVienFragment();
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
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        recyclerView = view.findViewById(R.id.recyclerQLTV);
        reloadTV();

        actionButton = view.findViewById(R.id.floataddTV);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemThanhVien();
            }

        });

        return view;
    }

    private void dialogThemThanhVien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_tvien, null);
        builder.setView(view);
        EditText edt_ma = view.findViewById(R.id.ed_them_maTV);
        EditText edt_ten = view.findViewById(R.id.ed_them_tenTV);
        EditText edt_nsinh = view.findViewById(R.id.ed_them_NSinh);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String maTV = edt_ma.getText().toString();
                String tenTV = edt_ten.getText().toString();
                String nsinh = edt_nsinh.getText().toString();

                boolean check = thanhVienDAO.themTV(maTV, tenTV, nsinh);
                if (check){
                    reloadTV();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    private void reloadTV(){
        thanhVienDAO = new ThanhVienDAO(getContext());
        arrayList = thanhVienDAO.getDSTVien();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() );
        recyclerView.setLayoutManager(layoutManager);
        thanhVienAdapter = new ThanhVienAdapter(getContext(), arrayList, thanhVienDAO);
        recyclerView.setAdapter(thanhVienAdapter);
    }
}