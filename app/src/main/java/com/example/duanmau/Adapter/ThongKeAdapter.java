package com.example.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DTO.Sach;
import com.example.duanmau.R;

import java.util.ArrayList;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ViewHolder>{

    Context context;
    ArrayList<Sach> arrayList;

    public ThongKeAdapter(Context context, ArrayList<Sach> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmaS.setText("Mã sách: "+arrayList.get(position).getMaSach());
        holder.txttenS.setText("Tên sách: "+arrayList.get(position).getTenSach());
        holder.txtsoLuong.setText("Số lượt mượn: "+String.valueOf(arrayList.get(position).getSoLuongMuon()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaS, txttenS, txtsoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaS = itemView.findViewById(R.id.tv_topma);
            txttenS = itemView.findViewById(R.id.tv_topten);
            txtsoLuong = itemView.findViewById(R.id.tv_soluong);
        }
    }
}
