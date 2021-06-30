package com.example.daohaisanv1.Adapter.Bill;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.daohaisanv1.Admin.donhangadmin;
import com.example.daohaisanv1.Class.Bill.Bill;
import com.example.daohaisanv1.Main.Bill.MainBill;
import com.example.daohaisanv1.Main.Bill.MainInfoBill;
import com.example.daohaisanv1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.daohaisanv1.Fragment.FragmentHome.objdh;


public class AdapterBill extends BaseAdapter {
    MainBill context;
    donhangadmin context1;
    ArrayList<Bill> listdonhang = new ArrayList<>();

    public AdapterBill(MainBill MainBill, ArrayList<Bill> objdh) {
        this.context = MainBill;
        this.listdonhang = objdh;
    }


    public AdapterBill(donhangadmin donhangadmin, ArrayList<Bill> objdh) {
        this.context1 = donhangadmin;
        this.listdonhang = objdh;
    }

    @Override
    public int getCount() {
        return listdonhang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView madh, tenkh, dckh, tongt, tt,date;
        TextView huy;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_orderinfo, null);

            viewHolder.madh = (TextView) convertView.findViewById(R.id.tv_iddh);
            viewHolder.tenkh = (TextView) convertView.findViewById(R.id.tv_hitory_product_name);
            viewHolder.dckh = (TextView) convertView.findViewById(R.id.tv_hitory_product_num);
            viewHolder.tongt = (TextView) convertView.findViewById(R.id.tv_hitory_product_price);
            viewHolder.tt = (TextView) convertView.findViewById(R.id.tv_hitory_product_status);
            viewHolder.date=(TextView) convertView.findViewById(R.id.tv_hitory_product_date);
            viewHolder.huy = (TextView) convertView.findViewById(R.id.txthuydonhang);
            final Bill kh = listdonhang.get(position);

            viewHolder.madh.setText(kh.getIddh() + "");
            viewHolder.tenkh.setText(kh.getTenkh());
            viewHolder.dckh.setText(kh.getDiachigh());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            viewHolder.tongt.setText(decimalFormat.format(kh.getTt()) + " VND");
            viewHolder.date.setText(kh.getNgay());
            viewHolder.tt.setText(kh.getTrangthai());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainInfoBill.class);
                    // intent.putExtra("chitiethoadon",arrayxacnhan.get(position));

                    intent.putExtra("madonhang",objdh.get(position).getIddh() + "");
                    intent.putExtra("sdt", "0" + objdh.get(position).getSdt() + "");
                    intent.putExtra("tenkh", objdh.get(position).getTenkh().toString());
                    intent.putExtra("diachi", objdh.get(position).getDiachigh().toString());
                    intent.putExtra("ngay", objdh.get(position).getNgay().toString());
                    intent.putExtra("phiship", objdh.get(position).getPhiship() + "đ");
                    intent.putExtra("tamtinh", objdh.get(position).getTamtinh() + "đ");
                    intent.putExtra("tongtien", objdh.get(position).getTt() + "đ");
                    intent.putExtra("trangthai", " Đơn hàng " + objdh.get(position).getTrangthai().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            viewHolder.huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    huy(kh.getIddh());

                }
            });

            convertView.setTag(viewHolder);
        }
        return convertView;
    }

    public void huy(final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn có hủy đơn hàng : " + id + " ?");

        builder.setNegativeButton(" Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                context.huydonhang(id);
                context.getdonhang();
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

}
