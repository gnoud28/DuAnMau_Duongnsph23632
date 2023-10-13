package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanmau.Adapter.ThongKeAdapter;
import com.example.duanmau.DAO.ThongKeDAO;
import com.example.duanmau.DTO.Sach;
import com.example.duanmau.R;

import java.util.ArrayList;


public class Top_10_Fragment extends Fragment {



    RecyclerView recyclerView;
    ThongKeDAO top10_dao;
    ThongKeAdapter top10_adapter;
    ArrayList<Sach> arrayList;


    public Top_10_Fragment() {
        // Required empty public constructor
    }



    public static Top_10_Fragment newInstance() {
        Top_10_Fragment fragment = new Top_10_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_top_10_, container, false);
        recyclerView = view.findViewById(R.id.recyclerTop);
        top10_dao = new ThongKeDAO(getContext());
        arrayList = top10_dao.top10();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        top10_adapter = new ThongKeAdapter(getContext(), arrayList);
        recyclerView.setAdapter(top10_adapter);
        return view;
    }




}