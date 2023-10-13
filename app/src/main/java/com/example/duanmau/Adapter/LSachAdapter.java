package com.example.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DTO.LoaiSach;
import com.example.duanmau.R;

import java.util.ArrayList;

public class LSachAdapter extends RecyclerView.Adapter<LSachAdapter.ViewHolder> {

    private Context context;
    ArrayList<LoaiSach> arrayList;
    LoaiSachDAO loaiSachDAO;

    public LSachAdapter(Context context, ArrayList<LoaiSach> arrayList, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.arrayList = arrayList;
        this.loaiSachDAO = loaiSachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMLoai.setText("Mã loại: "+ arrayList.get(position).getMaLoai());
        holder.txtTenLoai.setText("Tên loại: "+ arrayList.get(position).getTenLoai());

        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuaLSach(arrayList.get(holder.getAdapterPosition()));
            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = loaiSachDAO.xoaLSach(arrayList.get(holder.getAdapterPosition()).getMaLoai());
                        switch (check){
                            case 1:
                                arrayList.clear();
                                arrayList = loaiSachDAO.getDSLSach();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Xóa không thành công vì có sách trong loại sách", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).setNegativeButton("CANCEL", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMLoai, txtTenLoai;
        ImageView sua, xoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMLoai = itemView.findViewById(R.id.tv_maloai);
            txtTenLoai = itemView.findViewById(R.id.tv_tenloai);
            sua = itemView.findViewById(R.id.img_suaLS);
            xoa = itemView.findViewById(R.id.img_xoaLS);
        }
    }

    private void dialogSuaLSach(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_loaisach, null);
        TextView suama = view.findViewById(R.id.ed_smLoai);
        EditText suaTen = view.findViewById(R.id.ed_stLoai);
        suama.setText("Mã loại: "+loaiSach.getMaLoai());
        suaTen.setText(loaiSach.getTenLoai());
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int maLoai = loaiSach.getMaLoai();
                String tenLoai = suaTen.getText().toString();
                boolean check = loaiSachDAO.SuaLSach(maLoai, tenLoai);
                if (check){
                    reLoadLS();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    private void reLoadLS(){
        arrayList.clear();
        arrayList = loaiSachDAO.getDSLSach();
        notifyDataSetChanged();
    }
}
