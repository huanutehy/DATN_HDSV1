package com.example.daohaisanv1.Main.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.daohaisanv1.Navigation;
import com.example.daohaisanv1.R;

import java.util.HashMap;
import java.util.Map;

public class MainUpdateProfile extends AppCompatActivity {
    String gioitinh;
    EditText edtmk, edthoten, edtsđt, edtdiachi, edtemail, edgioitinh;
    TextView tvtk;
    SharedPreferences luutaikhoan;
    Button btnluu, btnhuy;
     Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatetaikhoan);
        toolbar = findViewById(R.id.toolupdate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AnhXa();
        luutaikhoan = getSharedPreferences("luutaikhoan", Context.MODE_PRIVATE);
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Suathongtin();
            }
        });


    }

    private void Suathongtin() {
        final String hoten = edthoten.getText().toString().trim();
        final String sđt = edtsđt.getText().toString().trim();
        final String diachi = edtdiachi.getText().toString().trim();
        final String email = edtemail.getText().toString().trim();
        final String matkhau = edtmk.getText().toString().trim();
        final String gt = edgioitinh.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConnectServer.updatetaikhoan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e( "onResponse: ",response.toString() );
                if (response.equals("success")) {
                    Toast.makeText(MainUpdateProfile.this, "Cập nhật tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainUpdateProfile.this, Navigation.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainUpdateProfile.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                String TenTk = luutaikhoan.getString("taikhoan", "");
                Log.d("run", TenTk);
                param.put("taikhoan", TenTk);
                param.put("hoten", hoten);
                param.put("sdt", sđt);
                param.put("diachi", diachi);
                param.put("gmail", email);
                param.put("matkhau", matkhau);
                param.put("gioitinh", gt);
                return param;
            }

        };
        requestQueue.add(stringRequest);
    }


    private void AnhXa() {
        btnluu = (Button) findViewById(R.id.btnluuthongtin);
        tvtk = (TextView) findViewById(R.id.edtaikhoan);
        edtmk = (EditText) findViewById(R.id.edmatkhau);
        edthoten = (EditText) findViewById(R.id.edhoten);
        edtsđt = (EditText) findViewById(R.id.edsdt);
        edtdiachi = (EditText) findViewById(R.id.eddiachi);
        edtemail = (EditText) findViewById(R.id.edemail);
        edgioitinh = findViewById(R.id.edgioitinh);

        luutaikhoan = getSharedPreferences("luutaikhoan", Context.MODE_PRIVATE);
        //String TenTk=luutaikhoan.getString("tk","");
        tvtk.setText(luutaikhoan.getString("taikhoan", ""));
        edthoten.setText(luutaikhoan.getString("hoten", ""));
        edgioitinh.setText(luutaikhoan.getString("gioitinh", ""));
        edtsđt.setText(String.valueOf(luutaikhoan.getInt("sdt", 0)));
        edtdiachi.setText(luutaikhoan.getString("diachi", ""));
        edtemail.setText(luutaikhoan.getString("gmail", ""));
        edtmk.setText(luutaikhoan.getString("matkhau", ""));

    }
}

