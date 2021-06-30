package com.example.daohaisanv1.sanphamdamua;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.daohaisanv1.Adapter.AdapterFavourite;
import com.example.daohaisanv1.Class.Favourite;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.daohaisanv1.Fragment.FragmentHome.yt;

public class sanphamdamua extends AppCompatActivity {
    Toolbar toolbar;
    String urlspyeuthich = ConnectServer.yeuthich;
    // public static ArrayList<objyeuthich> yt;
    AdapterFavourite like;
    ListView lv;
    Button btyt;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanphamdamua);
        toolbar = findViewById(R.id.tbaddkh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sharedPreferences = com.example.daohaisanv1.sanphamdamua.sanphamdamua.this.getSharedPreferences("luutaikhoan", sanphamdamua.this.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        lv = findViewById(R.id.lvdm);

        anhxa();
        getsanpham();


    }

    private void anhxa() {
        yt = new ArrayList<Favourite>();
        like = new AdapterFavourite(sanphamdamua.this, yt);
        lv.setAdapter(like);

    }
    public void bothich(final int idsp) {
        RequestQueue connnect = Volley.newRequestQueue(this);
        StringRequest jsonArray = new StringRequest(Request.Method.POST, ConnectServer.bothich, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse: ",response.toString() );
                if (response.equals("ok")) {
                    Toast.makeText(sanphamdamua.this, "  Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show();
                    getsanpham();

                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(sanphamdamua.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pra = new HashMap<>();
                pra.put("idyt", String.valueOf(idsp));
                return pra;
            }
        };
        connnect.add(jsonArray);


    }
    public void getsanpham() {

        RequestQueue connnect = Volley.newRequestQueue(sanphamdamua.this);
        StringRequest jsonArray = new StringRequest(Request.Method.POST, urlspyeuthich, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("erro:", jsonArray.toString());
                        JSONObject jsonsp = jsonArray.getJSONObject(i);
                        int idtk = jsonsp.getInt("idtk");
                        int id = jsonsp.getInt("idyt");
                        String tensp = jsonsp.getString("tensp");
                        int gia = jsonsp.getInt("gia");
                        String igmsp = jsonsp.getString("img");
                        String mota = jsonsp.getString("mota");
                        yt.add(new Favourite(idtk, id, tensp, gia, igmsp, mota));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                like.notifyDataSetChanged();
                // Toast.makeText(getContext().getApplicationContext(), ""+yt.size(), Toast.LENGTH_SHORT).show();

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("erro:", error.toString());
                        //     Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
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
