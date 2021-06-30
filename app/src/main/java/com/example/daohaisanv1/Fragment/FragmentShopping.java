package com.example.daohaisanv1.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Adapter.AdapterProduct;
import com.example.daohaisanv1.Adapter.AdapterSale;
import com.example.daohaisanv1.Class.Product;
import com.example.daohaisanv1.Class.Sale;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Main.MainCart;
import com.example.daohaisanv1.Main.MainSearch;
import com.example.daohaisanv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentShopping extends Fragment {
    String urlsale = ConnectServer.sale;
    String urldd= ConnectServer.dodung;
    ArrayList<Sale> obj;
    AdapterSale adap;
    ArrayList<Product> objdd;
    AdapterProduct adapdd;
    private ProgressDialog pDialog;
    TextView tvsreach;
    RecyclerView recyclerView,recyclerView1;
    ImageButton gh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dungcu, container, false);
        gh= view.findViewById(R.id.daogiohang);

        recyclerView = view.findViewById(R.id.test);
        tvsreach=view.findViewById(R.id.tvsreach);
        recyclerView1= view.findViewById(R.id.dodung);
        obj = new ArrayList<Sale>();
        adap = new AdapterSale(getActivity(), obj);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adap);


        objdd= new ArrayList<Product>();
       adapdd= new AdapterProduct(getActivity(),objdd);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView1.setAdapter(adapdd);
       getcay();
        getdodung();
        event();
        return view;
    }
    public void event(){
        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MainCart.class);
                startActivity(intent);
            }
        });
            tvsreach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getActivity(), MainSearch.class);
                    startActivity(intent);
                }
            });
    }

    public void getcay(){
        RequestQueue connnect = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlsale, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonsp = response.getJSONObject(i);
                        int id = jsonsp.getInt("id");
                        String tensp = jsonsp.getString("tencay");
                        int gia = jsonsp.getInt("gia");
                   //     int giam = jsonsp.getInt("giamoi");
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
        connnect.add(jsonArray);

    }
    public void getdodung(){
        RequestQueue connnect = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urldd, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonsp = response.getJSONObject(i);
                        int id = jsonsp.getInt("id");
                        String tensp = jsonsp.getString("tenhang");
                        int gia = jsonsp.getInt("gia");
                        String igmsp = jsonsp.getString("img");
                        String mota = jsonsp.getString("mota");

                        objdd.add(new Product(id, tensp, gia, igmsp, mota));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapdd.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
        connnect.add(jsonArray);

    }
}