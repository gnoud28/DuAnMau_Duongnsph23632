package com.example.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.DAO.ThuThuDAO;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    EditText tv_Username, tv_pass;
    Button btn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_Username = findViewById(R.id.tv_tendn);
        tv_pass = findViewById(R.id.tv_mkhau);
        btn_Login = findViewById(R.id.btn_login);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = tv_Username.getText().toString();
                String pass = tv_pass.getText().toString();
                if (thuThuDAO.dangNhap(user, pass)){
                    // Lưu
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("maTT", user);
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Username và password không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}