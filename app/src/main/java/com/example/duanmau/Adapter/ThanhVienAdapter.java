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

import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.DTO.ThanhVien;
import com.example.duanmau.R;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{

    Context context;
    ArrayList<ThanhVien>arrayList;
    ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> arrayList, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.arrayList = arrayList;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanh_vien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmaTV.setText("Mã thành viên: "+arrayList.get(position).getMaTV());
        holder.txttenTV.setText("Tên thành viên: "+arrayList.get(position).getTenTV());
        holder.txtnsinh.setText("Năm sinh: "+arrayList.get(position).getNSinh());
        holder.suaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuaTV(arrayList.get(holder.getAdapterPosition()));
            }
        });
        holder.xoaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Bạn có chắc chắn muốn xóa không? ");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = thanhVienDAO.xoaTV(arrayList.get(holder.getAdapterPosition()).getMaTV());
                        if (check == false) {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            loadData();
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
        private TextView txtmaTV, txttenTV, txtnsinh;
        private ImageView suaTV, xoaTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaTV = itemView.findViewById(R.id.tv_dsmaTV);
            txttenTV = itemView.findViewById(R.id.tv_dstenTV);
            txtnsinh = itemView.findViewById(R.id.tv_dsnsinh);
            suaTV = itemView.findViewById(R.id.img_suaTV);
            xoaTV = itemView.findViewById(R.id.img_xoaTV);
        }
    }

    private void dialogSuaTV(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_tvien, null);
        builder.setView(view);

        TextView txt_maTV = view.findViewById(R.id.tv_sua_maTV);
        EditText edt_tenTV = view.findViewById(R.id.ed_sua_tenTV);
        EditText edt_NSinh = view.findViewById(R.id.ed_sua_NSinh);
        txt_maTV.setText("Mã TV: "+ thanhVien.getMaTV());
        edt_tenTV.setText(thanhVien.getTenTV());
        edt_NSinh.setText(thanhVien.getNSinh());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ma = thanhVien.getMaTV();
                String tenTv = edt_tenTV.getText().toString();
                String namSinh = edt_NSinh.getText().toString();

                boolean check = thanhVienDAO.capNhapTV(ma, tenTv, namSinh);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }



    private void loadData(){
        arrayList.clear();
        arrayList = thanhVienDAO.getDSTVien();
        notifyDataSetChanged();
    }
}
