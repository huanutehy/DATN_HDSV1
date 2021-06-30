package com.example.daohaisanv1.Main.Bill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Adapter.Bill.AdapterInfoBill;
import com.example.daohaisanv1.Class.Bill.Bill;
import com.example.daohaisanv1.Class.Bill.InfoBill;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Main.Oder.MainOrder;
import com.example.daohaisanv1.Navigation;
import com.example.daohaisanv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.daohaisanv1.Fragment.FragmentHome.listgh;

public class MainInfoBill extends AppCompatActivity {
    TextView donhang, sdt, dc, trangthai, date, chon;
    TextView gia, phiship, ten, tamtinh, tongtien;
    ArrayList<InfoBill> objdh;
    Bill sp;
    ListView listView;
    AdapterInfoBill adtt;
    Button chot, huy;
    String urltt = ConnectServer.thanhtoan;
    String urldh = ConnectServer.cthoadon;
    Toolbar toolbar;
    //    private SharedPreferences sharedPreferences1;
//    private SharedPreferences.Editor editor1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiethoadon);
        sharedPreferences = this.getSharedPreferences("luutaikhoan", this.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        MainBill.sharedPreferences1 = this.getSharedPreferences("chitiet", this.MODE_PRIVATE);
        MainBill.editor1 = MainBill.sharedPreferences1.edit();
        toolbar = findViewById(R.id.toolBarchitiet);
        toolbar.setNavigationIcon(R.drawable.back2);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
        anhxa();
        getcay();
        getDataChiTiet1();


    }

    private void ThanhToan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Xác nhận chốt đơn ");
        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                RequestQueue queue = Volley.newRequestQueue(MainInfoBill.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urltt, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("load ", response.toString());
                        if (response.trim().equals("ok")) {
                            Toast.makeText(MainInfoBill.this, "Chốt đơn thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainInfoBill.this, Navigation.class));
                            listgh.clear();
                        } else {
                            Toast.makeText(MainInfoBill.this, "thất bại! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainInfoBill.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < listgh.size(); i++) {
                            JSONObject jsonOject = new JSONObject();
                            try {
                                int idtk = sharedPreferences.getInt("id", 0);
                                Log.e("getParams: ", String.valueOf(idtk));
                                jsonOject.put("idtaikhoan", String.valueOf(idtk));
                                jsonOject.put("madonhang", donhang.getText().toString().trim());
                                jsonOject.put("masp", listgh.get(i).getIdgh());
                                jsonOject.put("tensp", listgh.get(i).getTensp());
                                jsonOject.put("giasp", listgh.get(i).getGia() + "");
                                jsonOject.put("soluong", listgh.get(i).getSl() + "");
                                jsonOject.put("tenkh", ten.getText().toString().trim());
                                jsonOject.put("sdt", sdt.getText().toString().trim());
                                jsonOject.put("diachi", dc.getText().toString().trim());
                                jsonOject.put("ngay", date.getText().toString().trim());
                                jsonOject.put("phiship", phiship.getText().toString().trim());
                                jsonOject.put("tamtinh", tamtinh.getText().toString().trim());
                                jsonOject.put("tongtien", gia.getText().toString().trim());
                                jsonArray.put(jsonOject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        HashMap<String, String> param = new HashMap<>();
                        param.put("json", jsonArray.toString());
                        return param;
                    }
                };

                queue.add(stringRequest);

            }
        });

        builder.setNegativeButton("Không ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainOrder.class));
            }
        });
        builder.show();
    }


    public void anhxa() {

        listView = (ListView) findViewById(R.id.lvinfodonhang);
        objdh = new ArrayList<InfoBill>();
        adtt = new AdapterInfoBill(MainInfoBill.this, objdh);
        listView.setAdapter(adtt);

        donhang = (TextView) findViewById(R.id.ctdonhang);
        ten = findViewById(R.id.cthoten);
        date = findViewById(R.id.date);
        sdt = findViewById(R.id.ctsdt);
        dc = findViewById(R.id.ctdc);
        trangthai = findViewById(R.id.tinhtrang);
        gia = findViewById(R.id.ctsotien);
        tamtinh = findViewById(R.id.cttamtinh);
        phiship = findViewById(R.id.ctphisip);
        tongtien = findViewById(R.id.ctsotien);
    }

    private void getcay() {
        RequestQueue connnect = Volley.newRequestQueue(this);
        StringRequest jsonArray = new StringRequest(Request.Method.POST, urldh, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = MainBill.sharedPreferences1.getInt("madonhang", 5);
                Log.e("run: ", id + " ---- " + response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("erro:", jsonArray.toString());
                        JSONObject jsonsp = jsonArray.getJSONObject(i);
                        int idct = jsonsp.getInt("idchitiet");
                        int id1 = jsonsp.getInt("idhd");
                        int masp = jsonsp.getInt("masp");
                        String tensp = jsonsp.getString("tensp");
                        int gia = jsonsp.getInt("giasp");
                        String igmsp = jsonsp.getString("imgsp");
                        int sl = jsonsp.getInt("soluong");
                        objdh.add(new InfoBill(idct, id1, masp, tensp, gia, igmsp, sl));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adtt.notifyDataSetChanged();
                // Toast.makeText(getContext().getApplicationContext(), ""+yt.size(), Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("erro:", error.toString());

                        Toast.makeText(MainInfoBill.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pra = new HashMap<>();
                //  int id = MainBill.sharedPreferences1.getInt("madonhang", 0);
                Intent intent = getIntent();
                String dh = intent.getStringExtra("madonhang");
                Log.d(String.valueOf(dh), "aaaaaaaaa");
                pra.put("idhd", String.valueOf(dh));
                return pra;
            }
        };
        connnect.add(jsonArray);

    }



    private void getDataChiTiet1() {
        Intent intent = getIntent();
        String dh = intent.getStringExtra("madonhang");
        donhang.setText(dh);
        String ten1 = intent.getStringExtra("tenkh");
        ten.setText(ten1);
        String sdt1 = intent.getStringExtra("sdt");
        sdt.setText(sdt1);
        String diachi1 = intent.getStringExtra("diachi");
        dc.setText(diachi1);
        String ngay = intent.getStringExtra("ngay");
        date.setText(ngay);
        String tt = intent.getStringExtra("tongtien");
        tongtien.setText(tt);
        String tam = intent.getStringExtra("tamtinh");
        tamtinh.setText(tam);
        String phi = intent.getStringExtra("phiship");
        phiship.setText(phi);
        String trthai = intent.getStringExtra("trangthai");
        trangthai.setText(trthai);

        ;
    }

}
