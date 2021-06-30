package com.example.daohaisanv1.Main.Sale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.daohaisanv1.Class.Cart;
import com.example.daohaisanv1.Class.Sale;
import com.example.daohaisanv1.Main.MainCart;
import com.example.daohaisanv1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import static com.example.daohaisanv1.Fragment.FragmentHome.listgh;

public class MainInfoSale extends AppCompatActivity {
    Toolbar toolbar;

    Sale sl;
    ImageView img;
    TextView ten_sp, gia_sp, mota_sp,ctsl;
    EditText edgiatri;
    Button tang, giam;
    Button addgh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietsale);
        toolbar = findViewById(R.id.toolBarthongtinchitietsanphamsale);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        anhxa();
        themgh();
        soluong();
        sale();
    }

    private void anhxa() {
        img = (ImageView) findViewById(R.id.imageView);
        ten_sp= (TextView) findViewById(R.id.tensp);
        gia_sp = (TextView) findViewById(R.id.giathanh);
        mota_sp = (TextView) findViewById(R.id.dacdiem);
        tang = findViewById(R.id.butontang);
        giam = findViewById(R.id.butongiam);
        edgiatri = findViewById(R.id.edtgiatri);
        ctsl=findViewById(R.id.ctsale);
        addgh = findViewById(R.id.addgiohang);


    }

    public void sale() {
        Intent intent = getIntent();
        sl = (Sale) intent.getSerializableExtra("homesale");
        Picasso.get().load(sl.getImgspsale()).into(img);
        ten_sp.setText(sl.getTenspsale());
        ctsl.setText(sl.getSale()+" %");
        DecimalFormat decimalformat = new DecimalFormat("###,###,###");
        gia_sp.setText(decimalformat.format(sl.sale()) + " VNĐ");
        mota_sp.setText(sl.getMota());
    }


    public void soluong() {
        tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int slmoi = Integer.parseInt(edgiatri.getText().toString()) + 1;

                edgiatri.setText(slmoi + "");


                if (slmoi > 100) {
                    tang.setVisibility(View.INVISIBLE);
                    giam.setVisibility(View.VISIBLE);
                    edgiatri.setText(slmoi + "");
                } else {
                    tang.setVisibility(View.VISIBLE);
                    giam.setVisibility(View.VISIBLE);
                    edgiatri.setText(slmoi + "");
                }
            }
        });
        giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int slmoi = Integer.parseInt(edgiatri.getText().toString()) - 1;

                edgiatri.setText(slmoi + "");
                if (slmoi < 1) {
                    tang.setVisibility(View.VISIBLE);
                    giam.setVisibility(View.INVISIBLE);
                    edgiatri.setText(slmoi + "");
                } else {
                    tang.setVisibility(View.VISIBLE);
                    giam.setVisibility(View.VISIBLE);
                    edgiatri.setText(slmoi + "");
                }
            }
        });

    }

    private void themgh() {
        addgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if (listgh.size()>0) {
                boolean exist = false;
                for (int i = 0; i < listgh.size(); i++) {
                    if (listgh.get(i).getIdgh() == sl.getIdspsale()) {
                        int soLuong = Integer.parseInt(edgiatri.getText().toString().trim());
                        int sl = soLuong + listgh.get(i).getSl();
                        if (sl > 100) {
                            listgh.get(i).setSl(100);
                        } else {
                            listgh.get(i).setSl(sl);
                        }
                        exist = true;
                    }
                }
                if (listgh.size() > 0) {
                    int soLuong = Integer.parseInt(edgiatri.getText().toString().trim());
                    Cart gioHang = new Cart(sl.getIdspsale(), sl.getTenspsale(), sl.getImgspsale(), sl.sale(), soLuong);
                    listgh.add(gioHang);


                } else {
                    int soLuong = Integer.parseInt(edgiatri.getText().toString().trim());
                    Cart gioHang = new Cart(sl.getIdspsale(), sl.getTenspsale(), sl.getImgspsale(), sl.sale(), soLuong);
                    listgh.add(gioHang);
                }
                startActivity(new Intent(MainInfoSale.this, MainCart.class));
                //  }
            }
        });
    }


}
