package com.example.daohaisanv1.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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

public class FragmentFavourite extends Fragment {
    String urlyeuthich = ConnectServer.yeuthich;
    Toolbar toolbar;
    AdapterFavourite like;
    ListView lv;
    Button btyt;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_yeuthich, container, false);

        sharedPreferences = getActivity().getSharedPreferences("luutaikhoan", getActivity().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        lv = view.findViewById(R.id.lvyt);

        anhxa();
        getsanpham();
        xoa();
        return view;
    }

    private void anhxa() {
        yt = new ArrayList<Favourite>();
        like = new AdapterFavourite(getActivity(), yt);
        lv.setAdapter(like);

    }


    public void xoa() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Bạn có muốn bỏ thích sản phẩm này?");

                builder.setNegativeButton("Bỏ thích", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        yt.remove(position);
                        like.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Bạn đã bỏ thích ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return false;
            }
        });
    }

    private void getsanpham() {

        RequestQueue connnect = Volley.newRequestQueue(getActivity());
        StringRequest jsonArray = new StringRequest(Request.Method.POST, urlyeuthich, new Response.Listener<String>() {
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
