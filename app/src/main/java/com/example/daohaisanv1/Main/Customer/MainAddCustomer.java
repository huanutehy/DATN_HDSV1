package com.example.daohaisanv1.Main.Customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Random;

public class MainAddCustomer extends AppCompatActivity {
    EditText id, hoten, sdt, diachi;
    Button addkh;

    Random random;
    String url = ConnectServer.addkhachhang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        sharedPreferences = MainAddCustomer.this.getSharedPreferences("luutaikhoan", MainAddCustomer.this.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        id = (EditText) findViewById(R.id.idadd);
        hoten = (EditText) findViewById(R.id.hoten);
        sdt = (EditText) findViewById(R.id.sdt);
        diachi = (EditText) findViewById(R.id.diachi);
        addkh = (Button) findViewById(R.id.themkhachhang);
        random = new Random();
        id.setText(String.valueOf(random.nextInt(100000)));
        event();
    }

    public void event() {
        Toolbar bar = findViewById(R.id.tbaddkh);
        bar.setTitle("Hủy bỏ");
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAddCustomer.this, MainCustomer.class);
                startActivity(intent);
            }
        });
        addkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = id.getText().toString().trim();
                String ten = hoten.getText().toString().trim();
                String sodt = sdt.getText().toString().trim();
                String dc = diachi.getText().toString().trim();
                if (idd.isEmpty() || ten.isEmpty() || sodt.isEmpty() || dc.isEmpty()) {
                    Toast.makeText(MainAddCustomer.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();

                } else {
                    getkhachhang();
                }
            }
        });
    }

    public void getkhachhang() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e( "onResponse: ",response.toString() );
                if (response.equals("ok")) {
                    Toast.makeText(MainAddCustomer.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainAddCustomer.this, MainCustomer.class));
                } else {
                    Toast.makeText(MainAddCustomer.this, "lỗi ", Toast.LENGTH_SHORT).show();
                }

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainAddCustomer.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pra = new HashMap<>();
                int id1 = sharedPreferences.getInt("id", 0);
                pra.put("idtk", String.valueOf(id1));
                pra.put("idkh", id.getText().toString().trim());
                pra.put("hoten", hoten.getText().toString().trim());
                pra.put("sdt", sdt.getText().toString().trim());
                pra.put("diachi", diachi.getText().toString().trim());
                return pra;
            }
        };
        requestQueue.add(stringRequest);
    }

}
