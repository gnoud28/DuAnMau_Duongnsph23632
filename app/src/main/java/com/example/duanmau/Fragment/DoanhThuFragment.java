package com.example.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duanmau.DAO.ThongKeDAO;
import com.example.duanmau.R;

import java.util.Calendar;

public class DoanhThuFragment extends Fragment {



    public DoanhThuFragment() {
        // Required empty public constructor
    }


    public static DoanhThuFragment newInstance() {
        DoanhThuFragment fragment = new DoanhThuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        EditText ed_ngayBD = view.findViewById(R.id.ed_ngayBD);
        EditText ed_ngayKT = view.findViewById(R.id.ed_ngayKT);
        Button btn_ThongKe = view.findViewById(R.id.btn_thongke);
        TextView txtKetQua = view.findViewById(R.id.tv_thongke);
        //Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();

        ed_ngayBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ed_ngayBD.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        ed_ngayKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ed_ngayKT.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btn_ThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String ngayBD = ed_ngayBD.getText().toString();
                String ngayKT = ed_ngayKT.getText().toString();
                int doanhThu = thongKeDAO.getDoanhThu(ngayBD, ngayKT);
                txtKetQua.setText("Doanh thu từ " +ngayBD+ " đến " +ngayKT+ ": "+ doanhThu + " VND");
            }
        });
        return view;
    }
}