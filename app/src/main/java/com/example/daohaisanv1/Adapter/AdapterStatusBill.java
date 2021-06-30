package com.example.daohaisanv1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Class.StatusBill;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Main.Bill.MainInfoBill;
import com.example.daohaisanv1.Main.Bill.MainStatusBill;
import com.example.daohaisanv1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.daohaisanv1.Fragment.FragmentHome.objdh;

public class AdapterStatusBill extends BaseAdapter {
    private MainStatusBill context;
    public String trangthai;
    private ArrayList<StatusBill> arrayxacnhan;
    private ArrayList<StatusBill> arrayListsearch;
    static int getidctdh = 0;
    public static int getid = 0;

    public AdapterStatusBill(MainStatusBill context, ArrayList<StatusBill> arraysanpham) {
        this.context = context;
        this.arrayxacnhan = arraysanpham;
        this.arrayListsearch = new ArrayList<>();
        this.arrayListsearch.addAll(arraysanpham);
    }

    @Override
    public int getCount() {
        return arrayxacnhan.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayxacnhan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        private ImageView imgtvhinhanh;

        private TextView tvmadh, tvtenkh, tvtensp, tvgia, tvngaymua, tvtrangthai;
        private Button btsend, btxoadh;
        private Spinner spntt;

//        private EditText edttrangthai;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.itemtrangthai, null);
            viewHolder.imgtvhinhanh = (ImageView) convertView.findViewById(R.id.trangthai);
            viewHolder.tvmadh = (TextView) convertView.findViewById(R.id.tvxnmadonhang);
            viewHolder.tvtenkh = (TextView) convertView.findViewById(R.id.tvxntenkh);
            viewHolder.tvgia = (TextView) convertView.findViewById(R.id.tvxntongtien);
            viewHolder.tvngaymua = (TextView) convertView.findViewById(R.id.ttvdiachi);
            viewHolder.tvtrangthai = (TextView) convertView.findViewById(R.id.tvxntrangthai);
            viewHolder.btsend = convertView.findViewById(R.id.btnsend);
            viewHolder.btxoadh = convertView.findViewById(R.id.btnxoa);
            viewHolder.spntt = convertView.findViewById(R.id.spntt);
//            viewHolder.edttrangthai = convertView.findViewById(R.id.edttrangthai);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final StatusBill xacnhan = (StatusBill) getItem(position);

        // DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvmadh.setText(xacnhan.getIddh() + "");
        viewHolder.tvtenkh.setText(xacnhan.getTenkh());
        viewHolder.tvngaymua.setText(xacnhan.getDiachigh());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvgia.setText(decimalFormat.format(xacnhan.getTt()) + " VND");
        trangthai = viewHolder.tvtrangthai.getText().toString().trim();
        //  viewHolder.tvmadh.setText( xacnhan.getIddh() + "");
        viewHolder.tvtrangthai.setText(xacnhan.getTrangthai());
        final ViewHolder finalViewHolder2 = viewHolder;
        final ViewHolder finalViewHolder3 = viewHolder;
        final ViewHolder finalViewHolder5 = viewHolder;
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
                //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String size = finalViewHolder5.spntt.getSelectedItem().toString();
                if (trangthai.contains("hoantat")) {
                    finalViewHolder5.spntt.setEnabled(false);
                }
                int idctdh = arrayxacnhan.get(position).getIddh();
                getidctdh = idctdh;
                final RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ConnectServer.updatetrangthai, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finalViewHolder2.tvtrangthai.setText(finalViewHolder3.spntt.getSelectedItem().toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("madonhang", String.valueOf(getidctdh));
                        param.put("trangthai", size);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        viewHolder.btxoadh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idctdh = arrayxacnhan.get(position).getIddh();
                getid = idctdh;
                Dialogxoataikhoan();
            }

        });
        String[] lol = new String[]{"Đã xác nhận", "Đang giao hàng", "Hủy đơn hàng", "Hoàn Tất"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, lol);
        viewHolder.spntt.setAdapter(arrayAdapter);
        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayxacnhan.clear();
        if (charText.length() == 0) {
            arrayxacnhan.addAll(arrayListsearch);
            Log.d("arr", arrayListsearch + "");
        } else {
            for (StatusBill wp : arrayListsearch) {
                if (wp.getTensp().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrayxacnhan.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void Dialogxoataikhoan() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn có muốn xóa sản  phẩm không?");
        dialog.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.xoadh();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void xoadh() {
    }

}