package com.example.daohaisanv1.Admin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Class.Product;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Navigation;
import com.example.daohaisanv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sanpham extends AppCompatActivity {
    String urlcaycanh = ConnectServer.trangchu;
    String urlxoa = ConnectServer.xoassp;
    ArrayList<Product> sp;
    RecyclerView recyclerView, resale;
    adapsanphamad adapcay;
    Toolbar tbsanpham;
    ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);
        //sp = new ArrayList<objsanpham>();
        tbsanpham = findViewById(R.id.tbsanpham);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tbsanpham.setNavigationIcon(R.drawable.back2);
        }
        tbsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sanpham.this, Navigation.class);
                startActivity(intent);
            }

        });
        recyclerView = findViewById(R.id.listview);
        add = findViewById(R.id.themsp);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sanpham.this, themsanpham.class);
                startActivity(intent);
            }
        });
        sp = new ArrayList<Product>();
        adapcay = new adapsanphamad(sanpham.this, sp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapcay);
        getcay();
    }

    public void xoasanpham(final int idsp) {
        RequestQueue connnect = Volley.newRequestQueue(this);
        StringRequest jsonArray = new StringRequest(Request.Method.POST, urlxoa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("ok")) {
                    Toast.makeText(sanpham.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    getcay();
                    Intent intent = new Intent(sanpham.this, Navigation.class);
                    startActivity(intent);
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(sanpham.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pra = new HashMap<>();
                pra.put("id", String.valueOf(idsp));
                return pra;
            }
        };
        connnect.add(jsonArray);


    }

    public void getcay() {

        RequestQueue connnect = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlcaycanh, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonsp = response.getJSONObject(i);
                        int id = jsonsp.getInt("id");
                        String tensp = jsonsp.getString("tensp");
                        int gia = jsonsp.getInt("giasp");
                        String igmsp = jsonsp.getString("igmsp");
                        String mota = jsonsp.getString("mota");

                        sp.add(new Product(id, tensp, gia, igmsp, mota));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                adapcay.notifyDataSetChanged();
                //Toast.makeText(getContext().getApplicationContext(), "" + ssale.size(), Toast.LENGTH_SHORT).show();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(sanpham.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
        connnect.add(jsonArray);

    }
}

