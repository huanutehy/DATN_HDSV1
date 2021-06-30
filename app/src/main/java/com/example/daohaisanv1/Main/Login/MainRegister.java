package com.example.daohaisanv1.Main.Login;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.R;

import java.util.HashMap;
import java.util.Map;

public class MainRegister extends AppCompatActivity {
    EditText TK, MK, sdt, diachi, hoten, email , ngay,gt;
    RadioButton radioButton_nam, radioButton_nu;
    TextView bt_register;
    String str_id,str_tk, str_mk, str_hoten, str_diachi, str_sdt, str_gioitinh, str_email, str_ngay,str_gt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_dangky);
        TK = (EditText) findViewById(R.id.edtaikhoan);
        MK = (EditText) findViewById(R.id.password);
        sdt = findViewById(R.id.phone);
        hoten = findViewById(R.id.fullName);
        ngay= findViewById(R.id.date);
        email = findViewById(R.id.Email);
        bt_register =  findViewById(R.id.registerBtn);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Register() {
        if (TK.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        } else if (MK.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {

            str_hoten = hoten.getText().toString().trim();
            str_ngay = ngay.getText().toString().trim();
            str_email = email.getText().toString().trim();
            str_sdt = sdt.getText().toString().trim();
            str_tk = TK.getText().toString().trim();
            str_mk = MK.getText().toString().trim();
            StringRequest request = new StringRequest(Request.Method.POST, ConnectServer.dangky, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e( "aaaaaaa: ", response.toString());
                    if (response.contains("register")) {
                        Toast.makeText(MainRegister.this, "ok", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainLogin.class));
                    } else if (response.trim().contains("tontai")) {
                        Toast.makeText(MainRegister.this, "Tên tài khoản đã được sử dụng !", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainRegister.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("bbbb",error.toString());
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("taikhoan", str_tk);
                    params.put("matkhau", str_mk);
                    params.put("hoten", str_hoten);
                    params.put("sdt", str_sdt);
                    params.put("date", str_ngay);
                    params.put("gmail", str_email);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainRegister.this);
            requestQueue.add(request);
        }

    }
    CompoundButton.OnCheckedChangeListener listenerRadio = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                str_gioitinh = compoundButton.getText().toString().trim();
            }
        }
    };}

