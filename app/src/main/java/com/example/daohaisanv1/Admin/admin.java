package com.example.daohaisanv1.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.daohaisanv1.Main.Bill.MainStatusBill;
import com.example.daohaisanv1.Main.Login.MainUpdateProfile;
import com.example.daohaisanv1.Navigation;
import com.example.daohaisanv1.R;

import org.simple.eventbus.EventBus;

public class admin extends Fragment {

    Button tk, sp,hd, user, btndangxuat;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin, container, false);
        sharedPreferences = getContext().getSharedPreferences("luutaikhoan", getContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tk = view.findViewById(R.id.thongke);
        sp = view.findViewById(R.id.adsanpham);
        hd= view.findViewById(R.id.hoadon);
        user = view.findViewById(R.id.taikhoann);
        btndangxuat= view.findViewById(R.id.dangxuatt);


        click();
        dangxuat();
        return view;

    }
    public void dangxuat() {

        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.commit();

                EventBus.getDefault().post(true, "loginSuccess");
                Intent intent = new Intent(getContext(), Navigation.class);
                startActivity(intent);

                Toast.makeText(getContext(), "Bạn Đã Đăng Xuất! ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void click() {
       user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainUpdateProfile.class);
                startActivity(intent);
            }
        });
        tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), thongke.class);
                startActivity(intent);
            }
        });
        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), sanpham.class);
                startActivity(intent);
            }
        });
        hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainStatusBill.class);
                startActivity(intent);
            }
        });
    }
}