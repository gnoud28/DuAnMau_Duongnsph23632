package com.example.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DTO.LoaiSach;
import com.example.duanmau.DTO.Sach;
import com.example.duanmau.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {

    Context context;
    private ArrayList<Sach> arrayList;
    private SachDAO sachDAO;
    private ArrayList<HashMap<String, Object>> listHM;

    public SachAdapter(Context context, ArrayList<Sach> arrayList, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO) {
        this.context = context;
        this.arrayList = arrayList;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtma.setText("Mã sách: "+arrayList.get(position).getMaSach());
        holder.txtten.setText("Tên sách: "+arrayList.get(position).getTenSach());
        holder.txtGiaThue.setText("Giá thuê: "+arrayList.get(position).getGia());
        holder.txtmaLoai.setText("Mã loại: "+arrayList.get(position).getMaLS());
        holder.txttenLoai.setText("Tên loại: "+arrayList.get(position).getTenLoai());
        holder.suaS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsuaSach(arrayList.get(holder.getAdapterPosition()));
            }
        });
        holder.xoaS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = sachDAO.xoaSach(arrayList.get(holder.getAdapterPosition()).getMaSach());
                        if (check == false){
                            Toast.makeText(context, "XÓa thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            reLoadBSach();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("CANCEL", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtma, txtten, txtGiaThue, txtmaLoai, txttenLoai;
        private ImageView suaS, xoaS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtma = itemView.findViewById(R.id.tv_dsmasach);
            txtten = itemView.findViewById(R.id.tv_dstensach);
            txtGiaThue = itemView.findViewById(R.id.tv_giaThue);
            txtmaLoai = itemView.findViewById(R.id.tv_dsmaLoai);
            txttenLoai = itemView.findViewById(R.id.tv_dstenLoai);
            suaS = itemView.findViewById(R.id.img_suaS);
            xoaS = itemView.findViewById(R.id.img_xoaS);
        }
    }

    private void dialogsuaSach(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_sach, null);
        TextView tvma = view.findViewById(R.id.tvsua_maSach);
        EditText edt_ten = view.findViewById(R.id.ed_sua_tenSach);
        EditText edt_tien = view.findViewById(R.id.ed_sua_gia);
        Spinner spinner = view.findViewById(R.id.spn_sualoaiSach);

        tvma.setText("Mã sách: "+sach.getMaSach());
        edt_ten.setText(sach.getTenSach());
        edt_tien.setText(String.valueOf(sach.getGia()));
        builder.setView(view);

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHM, android.R.layout.simple_list_item_1, new String[]{"TenLoai"}, new int[]{android.R.id.text1});
        spinner.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;
        for (HashMap<String, Object> item : listHM){
            if ((int) item.get("maLoai") == sach.getMaLS()){
                position = index;
            }
            index++;
        }
        spinner.setSelection(position);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String maSach = sach.getMaSach();
                String tenSach = edt_ten.getText().toString();
                int tien = Integer.parseInt(edt_tien.getText().toString());
                HashMap<String, Object> hMap = (HashMap<String, Object>) spinner.getSelectedItem();
                int maLoai = (int) hMap.get("maLoai");

                boolean  check = sachDAO.capNhatSach(maSach, tenSach, tien, maLoai);
                if (check){
                    reLoadBSach();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);

        builder.show();
    }

    private ArrayList<HashMap<String, Object>> getDSLoai(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
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

    private void reLoadBSach(){
        arrayList.clear();
        arrayList = sachDAO.getDSSach();
        notifyDataSetChanged();
    }
}
