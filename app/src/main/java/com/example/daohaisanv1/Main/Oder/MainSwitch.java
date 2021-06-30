package com.example.daohaisanv1.Main.Oder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Adapter.AdapterSwitch;
import com.example.daohaisanv1.Class.Cart;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainSwitch extends AppCompatActivity {
    Button addkh;
    ListView lvvc;
    Toolbar toolbar;
    AdapterSwitch advc;
    ArrayList<Cart> vc;
    String urlvc = ConnectServer.vanchuyen;
    private static Integer gia=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanchuyen);
        lvvc=findViewById(R.id.lvvanchuyen);
        vc = new ArrayList<Cart>();
        lvvc.setAdapter(advc);
        getkhachhang();
        lv();
    }
    private void lv(){
        lvvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(MainSwitch.this, MainOrder.class);


                startActivity(intent);

            }
        });
    }

    private void getkhachhang() {
       // final int[] gia = {0};
        RequestQueue connnect = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlvc, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String ten = jsonObject.getString("tendonvi");
                        gia = jsonObject.getInt("phiship");
                        String img = jsonObject.getString("imgdv");
                       // vc.add(new objgiohang(id, ten, gia, img));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                advc.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainSwitch.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        connnect.add(jsonArray);
    }
}

