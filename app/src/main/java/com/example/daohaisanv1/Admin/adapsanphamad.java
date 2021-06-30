package com.example.daohaisanv1.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Class.Product;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

;

public class adapsanphamad extends RecyclerView.Adapter<adapsanphamad.ViewHolder> {
    Context context;
    ArrayList<Product> listsp;
    ArrayList<Product> sreachhome;



    public adapsanphamad(sanpham applicationContext, ArrayList<Product> sp) {
        this.context = applicationContext;
        this.listsp = sp;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemsanpham, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Product sp = listsp.get(position);
        holder.tvTen.setText(sp.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setText("Giá: " + decimalFormat.format(sp.getGt()) + " VNĐ");
        Picasso.get().load(sp.getImgsp()).centerCrop().resize(150, 150).into(holder.imvHinh);
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoa(sp.getTensp(),sp.getIdsp());

            }
        });
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,updatesanpham.class);
                intent.putExtra("updatesanpham",sp);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listsp.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imvHinh;
        public TextView tvTen, tvGia;
        public Button xoa, sua;


        public ViewHolder(View itemView) {
            super(itemView);
            imvHinh = (ImageView) itemView.findViewById(R.id.adHinhsp);
            tvTen = (TextView) itemView.findViewById(R.id.adTenSP);
            tvGia = (TextView) itemView.findViewById(R.id.adGiaSp);
            xoa = (Button) itemView.findViewById(R.id.xoa);
            sua = (Button) itemView.findViewById(R.id.sua);


        }
    }
    public void xoa(String ten, final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn có muốn xoá " + ten +" ?");

        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                context.xoasanpham(id);
                xoasanpham(id);
//                context.getcay();
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
    public void xoasanpham(final int idsp) {
        RequestQueue connnect = Volley.newRequestQueue(context);
        StringRequest jsonArray = new StringRequest(Request.Method.POST, ConnectServer.xoassp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("ok")) {
                    Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();

                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

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


}