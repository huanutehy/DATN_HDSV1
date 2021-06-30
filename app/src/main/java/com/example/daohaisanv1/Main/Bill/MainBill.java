package com.example.daohaisanv1.Main.Bill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.daohaisanv1.Adapter.Bill.AdapterBill;
import com.example.daohaisanv1.Class.Bill.Bill;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.daohaisanv1.Fragment.FragmentHome.objdh;

public class MainBill extends AppCompatActivity {
    //   public static ArrayList<objdonhang> objdh;
    AdapterBill adapdh;
    ListView listView;
    Toolbar toolbar;
    String urldh = ConnectServer.donhang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static SharedPreferences sharedPreferences1;
    public static SharedPreferences.Editor editor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoadon);
        sharedPreferences = this.getSharedPreferences("luutaikhoan", this.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sharedPreferences1 = this.getSharedPreferences("chitiet", this.MODE_PRIVATE);
        editor1 = sharedPreferences1.edit();

        listView = findViewById(R.id.lvdh);
        toolbar = findViewById(R.id.tbdonhang);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        event();
        getdonhang();
    }
    public void huydonhang(final int idsp) {
        RequestQueue connnect = Volley.newRequestQueue(this);
        StringRequest jsonArray = new StringRequest(Request.Method.POST, ConnectServer.huydonhang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse: ",response.toString() );
                if (response.equals("ok")) {
                    Toast.makeText(MainBill.this, "  Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show();
                    getdonhang();
                    finish();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainBill.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pra = new HashMap<>();
                pra.put("madonhang", String.valueOf(idsp));
                return pra;
            }
        };
        connnect.add(jsonArray);


    }

    public void event() {
        objdh = new ArrayList<Bill>();
        adapdh = new AdapterBill(MainBill.this, objdh);
        listView.setAdapter(adapdh);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor1.putInt("madonhang", objdh.get(position).getIddh());
                editor1.putString("tentk", objdh.get(position).getTenkh().toString());
                editor1.putString("ngay", objdh.get(position).getNgay().toString());
                editor1.putInt("sdt", objdh.get(position).getSdt());
                editor1.putString("diachi", objdh.get(position).getDiachigh().toString());
                editor1.putInt("phiship", objdh.get(position).getPhiship());
                editor1.putInt("tamtinh", objdh.get(position).getTamtinh());
                editor1.putInt("tongtien", objdh.get(position).getTt());
                editor1.putString("trangthai", objdh.get(position).getTrangthai().toString());
                editor1.commit();
                Intent intent = new Intent(MainBill.this, MainInfoBill.class);
               intent.putExtra("madonhang",objdh.get(position).getIddh() + "");
                intent.putExtra("sdt", "0" + objdh.get(position).getSdt() + "");
                intent.putExtra("tenkh", objdh.get(position).getTenkh().toString());
                intent.putExtra("diachi", objdh.get(position).getDiachigh().toString());
                intent.putExtra("ngay", objdh.get(position).getNgay().toString());
                intent.putExtra("phiship", objdh.get(position).getPhiship() + "đ");
                intent.putExtra("tamtinh", objdh.get(position).getTamtinh() + "đ");
                intent.putExtra("tongtien", objdh.get(position).getTt() + "đ");
                intent.putExtra("trangthai", " Đơn hàng " + objdh.get(position).getTrangthai().toString());
                startActivity(intent);
            }
        });
    }

    public void getdonhang() {
        RequestQueue connnect = Volley.newRequestQueue(this);
        StringRequest jsonArray = new StringRequest(Request.Method.POST, urldh, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("erro:", response.toString());
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idtk = jsonObject.getInt("idtaikhoan");
                        int madh = jsonObject.getInt("madonhang");
                        String hoten = jsonObject.getString("tenkh");
                        String date = jsonObject.getString("ngay");
                        int sdt = jsonObject.getInt("sdt");
                        String diachi = jsonObject.getString("diachi");
                        int phiship = jsonObject.getInt("phiship");
                        int tamtinh = jsonObject.getInt("tamtinh");
                        int sotien = jsonObject.getInt("tongtien");
                        String trangthai = jsonObject.getString("trangthai");

                        objdh.add(new Bill(idtk, madh, hoten, sdt, diachi, date, phiship, tamtinh, sotien, trangthai));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapdh.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainBill.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pra = new HashMap<>();
                int id = sharedPreferences.getInt("id", 0);
                Log.d(String.valueOf(id), "bbbbbbbbbbbbb");
                pra.put("idtaikhoan", String.valueOf(id));

                return pra;
            }
        };
        connnect.add(jsonArray);
    }
}


