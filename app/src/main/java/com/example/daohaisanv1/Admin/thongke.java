package com.example.daohaisanv1.Admin;

import android.graphics.Color;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class thongke extends AppCompatActivity {
    ArrayList<objthongke> mang;
    ArrayList<objthongke>mang1;
    BarChart barChart;
    Toolbar toolbar;
    Button btnnam;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke2);
        anhxa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mang = new ArrayList<>();
        mang1 = new ArrayList<>();
        Getdata();
        ActionBar();

    }
    private void anhxa() {
        barChart = findViewById(R.id.bieudo);
        toolbar = (Toolbar) findViewById(R.id.tool_thongke);


    }
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void Getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ConnectServer.thongke, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject ob = response.getJSONObject(i);
                                mang.add(new objthongke(
                                        ob.getString("thang"),
                                        ob.getInt("tongtien")

                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("JSON", mang.toString());
                        ArrayList<BarEntry> v = new ArrayList<>();
                        int thang1 = 0;
                        int tongtien1 = 0;
                        int thang2 = 0;
                        int tongtien2 = 0;
//                        int thang3 = Integer.parseInt(mang.get(2).getThoigian());
                        // int tongtien3 = mang.get(2).getTongtien();
                        int thang4 = Integer.parseInt(mang.get(0).getThoigian());
                        int tongtien4 = mang.get(0).getTongtien();
                        int thang5 =Integer.parseInt(mang.get(1).getThoigian());
                        int tongtien5 = mang.get(1).getTongtien();
                        int thang6 =Integer.parseInt(mang.get(2).getThoigian());
                        int tongtien6 =  mang.get(2).getTongtien();

                        int thang7 = 0;
                        int tongtien7 = 0;
                        int thang8 = 0;
                        int tongtien8 = 0;
                        int thang9 = 0;
                        int tongtien9 = 0;
                        int thang10 = 0;
                        int tongtien10 = 0;
                        int thang11 = 0;
                        int tongtien11 = 0;
                        int thang12 = 0;
                        int tongtien12 = 0;

                        v.add(new BarEntry(thang1, tongtien1));
                        v.add(new BarEntry(thang2, tongtien2));
                        //  v.add(new BarEntry(thang3, tongtien3));
                        v.add(new BarEntry(thang4, tongtien4));
                        v.add(new BarEntry(thang5, tongtien5));
                        v.add(new BarEntry(thang6, tongtien6));
                        v.add(new BarEntry(thang7, tongtien7));
                        v.add(new BarEntry(thang8, tongtien8));
                        v.add(new BarEntry(thang9, tongtien9));
                        v.add(new BarEntry(thang10, tongtien10));
                        v.add(new BarEntry(thang11, tongtien11));
                        v.add(new BarEntry(thang12, tongtien12));

                        BarDataSet barDataSet = new BarDataSet(v, "Tháng");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(10f);
                        BarData barData = new BarData(barDataSet);
                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.getDescription().setText("Biểu đồ doanh thu bán được theo từng tháng");
                        barChart.animateY(4000);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(thongke.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);


    }
    public void Nam(final String query){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConnectServer.thongkenam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String thoigian = jsonObject.getString("ngay");
                            int tongtiennam = jsonObject.getInt("tongtien");

                            Toast.makeText(thongke.this, "năm  "+thoigian, Toast.LENGTH_SHORT).show();
                            Toast.makeText(thongke.this, "tổng tiền"+tongtiennam, Toast.LENGTH_SHORT).show();

                            mang1.add(new objthongke(thoigian,tongtiennam));

                            ArrayList<BarEntry> dataValu = new ArrayList<>();
                            for(int x = 0; x < mang1.size();x++) {
                                dataValu.add(new BarEntry(Float.parseFloat(mang1.get(0).getThoigian()), mang1.get(0).getTongtien()));
                                BarDataSet barDataSet = new BarDataSet(dataValu, "Thống kê doanh thu theo từng năm ");

                                barDataSet.setValueTextColor(Color.BLACK);
                                barDataSet.setValueTextSize(15f);
                                BarData barData = new BarData(barDataSet);
                                barChart.setFitBars(true);
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barChart.setData(barData);
                                barChart.getDescription().setText("Biểu đồ doanh thu theo từng năm");
                                barChart.animateY(4000);

                                XAxis xAxis = barChart.getXAxis();
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setGranularity(1);
                                xAxis.setGranularityEnabled(true);
                                barChart.setDragEnabled(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loiii", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("query_user", query);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
