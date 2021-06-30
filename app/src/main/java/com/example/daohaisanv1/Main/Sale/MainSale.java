package com.example.daohaisanv1.Main.Sale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Adapter.AdapterSale;
import com.example.daohaisanv1.Class.Sale;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Main.MainCart;
import com.example.daohaisanv1.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainSale extends AppCompatActivity {
    String urlsale = ConnectServer.sale;

    ArrayList<Sale> obj;
    AdapterSale adap;
    private ProgressDialog pDialog;
    RecyclerView recyclerView,recyclerView1;
    ImageButton gh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giamgia);
        gh= findViewById(R.id.daogiohang);

        recyclerView = findViewById(R.id.giamgia);
        obj = new ArrayList<Sale>();
        adap = new AdapterSale(MainSale.this, obj);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainSale.this, 2));
        recyclerView.setAdapter(adap);
        getcay();
        event();
    }  public void event(){
        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainSale.this, MainCart.class);
                startActivity(intent);
            }
        });
    }
    public void getcay(){
        RequestQueue connnect = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlsale, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonsp = response.getJSONObject(i);
                        int id = jsonsp.getInt("id");
                        String tensp = jsonsp.getString("tencay");
                        int gia = jsonsp.getInt("gia");
                        int sale = jsonsp.getInt("sale");
                        String igmsp = jsonsp.getString("igmcay");
                        String mota = jsonsp.getString("mota");

                        obj.add(new Sale(id, tensp, gia, sale,  igmsp, mota));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adap.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainSale.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
        connnect.add(jsonArray);

    }

}
