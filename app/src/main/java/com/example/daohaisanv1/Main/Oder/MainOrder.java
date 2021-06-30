package com.example.daohaisanv1.Main.Oder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.example.daohaisanv1.Adapter.AdapterOder;
import com.example.daohaisanv1.Adapter.AdapterSwitch;
import com.example.daohaisanv1.Class.Customer;
import com.example.daohaisanv1.Class.Switch;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Main.Customer.MainCustomer;
import com.example.daohaisanv1.Main.MainCart;
import com.example.daohaisanv1.Navigation;
import com.example.daohaisanv1.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.daohaisanv1.Fragment.FragmentHome.listgh;

public class MainOrder extends AppCompatActivity {
    Button btkhachhang, btthanhtoan;
    ListView listView;
    TextView donhang, sdt, dc, tendv, date, chon;
    public static TextView gia, phiship, ten, tamtinh;
    Toolbar toolbar;
    String urlkh = ConnectServer.khachhang;
    Customer kh;
    Random random;
    Spinner spinner;
    public static List<Switch> vct = new ArrayList<>();

    String urldonhang = ConnectServer.adddhoadon;
    String urlchitiet = ConnectServer.adddchitiethoadon;

    AdapterOder adapterGioHang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        sharedPreferences = this.getSharedPreferences("luutaikhoan", this.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = findViewById(R.id.toolBarthanhtoan);
        setSupportActionBar(toolbar);
     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        anhxa();
        getDataChiTiet();
        giatien();
        date();
        //   getdonvi();
        tamtinh();
    }

    private void date() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        date.setText(new StringBuffer().append(year).append("-").append(month).append("-").append(day));
    }

    private void getDataChiTiet() {
        Intent intent = getIntent();
        String ten1 = intent.getStringExtra("hoten");
        ten.setText(ten1);

        String sdt1 = intent.getStringExtra("sdt");
        sdt.setText(sdt1);
        String diachi1 = intent.getStringExtra("diachi");
        dc.setText(diachi1);
        ;
    }

    public static void giatien() {

        int tongTien = 0;

        for (int i = 0; i < listgh.size(); i++) {
            tongTien += listgh.get(i).tongTien();
        }

        // DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia.setText(tongTien + 30000 + "");

        Log.d("giatien: ",tongTien+300000+"");
        //}
    }

    public static void tamtinh() {
        int tongTien = 0;

        for (int i = 0; i < listgh.size(); i++) {
            tongTien += listgh.get(i).tongTien();
        }
        // DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tamtinh.setText(tongTien+"");
        Log.d( "tamtinh: ",tongTien+"");

    }

    public void anhxa() {
        btkhachhang = findViewById(R.id.ttkhachhang);
        listView = (ListView) findViewById(R.id.lvthanhtoan);
        donhang = findViewById(R.id.donhang);
        ten = findViewById(R.id.tthoten);
        date = findViewById(R.id.date);
        sdt = findViewById(R.id.ttsdt);
        dc = findViewById(R.id.ttdc);
        chon = findViewById(R.id.chondv);
        gia = findViewById(R.id.sotien);
        tendv = findViewById(R.id.ten);
        tamtinh = findViewById(R.id.tamtinh);
        phiship = findViewById(R.id.tvphisip);


        spinner = findViewById(R.id.vanchuyen);
        String[] dcs = {"Ninja Van", "Viettel Express", "Grap Express", "NowShip", "Hay để tớ ship"};
        int flags[] = {R.drawable.ninjavan, R.drawable.viettel, R.drawable.grap, R.drawable.now, R.drawable.username};
        AdapterSwitch vc = new AdapterSwitch(getApplicationContext(), flags, dcs);

        //    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dc);
        spinner.setAdapter(vc);
        btthanhtoan = findViewById(R.id.bthoadon);
        random = new Random();
        donhang.setText(String.valueOf(random.nextInt(100000)));
        adapterGioHang = new AdapterOder(getApplicationContext(), listgh);
        listView.setAdapter(adapterGioHang);


        btthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ThanhToan();
                doThanhToan();

            }
        });
        btkhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainOrder.this, MainCustomer.class);
                startActivity(intent);
            }
        });

    }

    private void doThanhToan() {
        final String dh = donhang.getText().toString().trim();
        final String ten1 = ten.getText().toString();
        final String sdt1 = sdt.getText().toString();
        final String diachi = dc.getText().toString();
        final String date1 = date.getText().toString().trim();
        final String phiship1 = phiship.getText().toString().trim();
        final String tamtinh1 = tamtinh.getText().toString().trim();
        final String tien = gia.getText().toString().trim();

        if (ten1.isEmpty() || sdt1.isEmpty() || diachi.isEmpty()) {
            Toast.makeText(this, "Bạn chưa nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Xác nhận thanh toán ");
        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urldonhang, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.d("lỗi", response.toString());
                        if (response.equals("ok")) {
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchitiet, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //    Log.d("lỗi1", response.toString());
                                    if (response.equals("ok")) {
                                        Toast.makeText(MainOrder.this, "Thanh toan thành công!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainOrder.this, Navigation.class));
                                        listgh.clear();
                                        Toast.makeText(MainOrder.this, "Mời bạn tiếp tục mua hàng!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(MainOrder.this, "Thêm đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray jsonArray = new JSONArray();
                                    for (int i = 0; i < listgh.size(); i++) {
                                        JSONObject jsonOject = new JSONObject();
                                        try {

                                            jsonOject.put("idhd", donhang.getText().toString().trim());
                                            jsonOject.put("masp", listgh.get(i).getIdgh());
                                            jsonOject.put("tensp", listgh.get(i).getTensp());
                                            jsonOject.put("imgsp", listgh.get(i).getImgsp());
                                            jsonOject.put("giasp", listgh.get(i).getGia() + "");
                                            jsonOject.put("soluong", listgh.get(i).getSl() + "");

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
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainOrder.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> pra = new HashMap<>();
                        int idtk = sharedPreferences.getInt("id", 0);
                        Log.e("getParams: ", String.valueOf(idtk));
                        pra.put("idtaikhoan", String.valueOf(idtk));
                        pra.put("madonhang", dh);
                        pra.put("tenkh", ten1);
                        pra.put("sdt", sdt1);
                        pra.put("diachi", diachi);
                        pra.put("ngay", date1);
                        pra.put("phiship", phiship1);
                        pra.put("tamtinh", tamtinh1);
                        pra.put("tongtien", tien + "");

                        return pra;
                    }
                };
                requestQueue.add(stringRequest);
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

}