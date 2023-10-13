package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DAO.PhieuMuonDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.DAO.ThuThuDAO;
import com.example.duanmau.Fragment.DoanhThuFragment;
import com.example.duanmau.Fragment.LoaiSachFragment;
import com.example.duanmau.Fragment.PhieuMuonFragment;
import com.example.duanmau.Fragment.SachFragment;
import com.example.duanmau.Fragment.ThanhVienFragment;
import com.example.duanmau.Fragment.Top_10_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.id_toolBar);
        frameLayout = findViewById(R.id.id_frameLayout);
        navigationView = findViewById(R.id.id_navigation);
        drawerLayout = findViewById(R.id.drawerlayout);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.phieumuon:
                        fragment = new PhieuMuonFragment();
                        break;
                    case R.id.sach:
                        fragment = new SachFragment();
                        break;
                    case R.id.loaisach:
                        fragment = new LoaiSachFragment();
                        break;
                    case R.id.thanhvien:
                        fragment = new ThanhVienFragment();
                        break;
                    case R.id.top:
                        fragment = new Top_10_Fragment();
                        break;
                    case R.id.doanhthu:
                        fragment = new DoanhThuFragment();
                        break;
                    case R.id.doiMK:
                        showDialogDoiMK();
                        break;
                    case R.id.dangxuat:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    default:
                        fragment = new PhieuMuonFragment();
                        break;
                }


                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.id_frameLayout, fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());

//                replaceFragment(new LoaiSachFragment());
//                navigationView.getMenu().findItem(R.id.sach);
                return false;
            }

        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_frameLayout, fragment);
        transaction.addToBackStack(null); // Nếu bạn muốn thêm vào Back Stack
        setTitle("Sách");
        transaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogDoiMK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doi_mkhua, null);

        EditText ed_mkcu = view.findViewById(R.id.ed_mkhaucu);
        EditText ed_mkmoi = view.findViewById(R.id.ed_mkhaumoi);
        EditText ed_nhaplai = view.findViewById(R.id.ed_nhaplai);
        builder.setView(view);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("CANCEL", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkcu = ed_mkcu.getText().toString();
                String mkmoi = ed_mkmoi.getText().toString();
                String nlmkmoi = ed_nhaplai.getText().toString();

                if (mkmoi.equals(nlmkmoi)){
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    String maTT = sharedPreferences.getString("maTT", "");
                    //cập nhật
                    ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                    boolean check = thuThuDAO.capNhapMK(maTT, mkcu, mkmoi);
                    if (check){
                        Toast.makeText(MainActivity.this, "Đổi mật khẩu thành cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}