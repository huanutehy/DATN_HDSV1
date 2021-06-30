package com.example.daohaisanv1.Main.Customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.example.daohaisanv1.Adapter.AdapterCustomer;
import com.example.daohaisanv1.Class.Customer;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Main.Oder.MainOrder;
import com.example.daohaisanv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainCustomer extends AppCompatActivity {
    Button addkh;
    ListView lvkh;
    Toolbar toolbar;
    AdapterCustomer adapkhachhang;
    ArrayList<Customer> kh;
    String urlkh = ConnectServer.khachhang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        toolbar = findViewById(R.id.tbkhachhang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sharedPreferences = MainCustomer.this.getSharedPreferences("luutaikhoan", MainCustomer.this.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        anhxa();
        event();
        getkhachhang();
    }

    public void anhxa() {
        addkh = (Button) findViewById(R.id.addkhachhang);
        lvkh = (ListView) findViewById(R.id.lvdiachi);
        kh = new ArrayList<Customer>();
        adapkhachhang = new AdapterCustomer(MainCustomer.this, kh);
        lvkh.setAdapter(adapkhachhang);
    }

    public void event() {
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, MainOrder.class);
                startActivity(intent);
            }

        });
        addkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, MainAddCustomer.class);
                startActivity(intent);
            }
        });
        lvkh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(MainCustomer.this, MainOrder.class);
                intent.putExtra("hoten", kh.get(i).getHoten().toString());
                intent.putExtra("sdt", "0" + kh.get(i).getSdt() + "");
                intent.putExtra("diachi", kh.get(i).getDiachi().toString());
                startActivity(intent);

            }
        });
    }

    private void getkhachhang() {
        RequestQueue connnect = Volley.newRequestQueue(this);
        StringRequest jsonArray = new StringRequest(Request.Method.POST,urlkh, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse: ", response.toString());
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        Log.d("erro:", jsonArray.toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idtk = jsonObject.getInt("idtk");
                        int id = jsonObject.getInt("idkh");
                        String hoten = jsonObject.getString("hoten");
                        int sdt = jsonObject.getInt("sdt");
                        String diachi = jsonObject.getString("diachi");
                        kh.add(new Customer(idtk, id, hoten, sdt, diachi));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapkhachhang.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainCustomer.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("erro:", error.toString());

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pra = new HashMap<>();
                int id = sharedPreferences.getInt("id", 0);
                pra.put("idtk", String.valueOf(id));
                return pra;
            }
        };
        connnect.add(jsonArray);
    }
}