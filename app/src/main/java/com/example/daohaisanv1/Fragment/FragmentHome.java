package com.example.daohaisanv1.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.daohaisanv1.Adapter.AdapterBanner;
import com.example.daohaisanv1.Adapter.AdapterProduct;
import com.example.daohaisanv1.Adapter.AdapterSale;
import com.example.daohaisanv1.Class.Bill.Bill;
import com.example.daohaisanv1.Class.Cart;
import com.example.daohaisanv1.Class.Customer;
import com.example.daohaisanv1.Class.Favourite;
import com.example.daohaisanv1.Class.Product;
import com.example.daohaisanv1.Class.Sale;
import com.example.daohaisanv1.Class.SlidePhoto;
import com.example.daohaisanv1.ConnectServer.ConnectServer;
import com.example.daohaisanv1.Main.MainCart;
import com.example.daohaisanv1.Main.MainSearch;
import com.example.daohaisanv1.Main.Sale.MainSale;
import com.example.daohaisanv1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class FragmentHome extends Fragment {

    ViewFlipper viewFlipper;
    Toolbar toolbar;
    RecyclerView recyclerView, resale;
    AdapterProduct adaptersanpham;
    ImageButton gh;
    TextView xemthem, tvsreach;
    ArrayList<Sale> ssale;
    AdapterSale sgg;
    public static ArrayList<Product> sp;
    public static ArrayList<Favourite> yt;

    String urlsanpham = ConnectServer.trangchu;
    String urlsale = ConnectServer.sale;

    public static ArrayList<Cart> listgh;
    public static ArrayList<Customer> mangkhachHang;
    public static ArrayList<Bill> objdh;
    public static ArrayList<Product> vc;
    private ViewPager viewPagerSlidePhoto;
    private Timer mTimer;
    private View mView;
    private AdapterBanner slidePhotoAdapter;

    private CircleIndicator circleIndicator;
    List<SlidePhoto> listSlidePhoto = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);


        toolbar = view.findViewById(R.id.toolbarmanhinh);
        recyclerView = view.findViewById(R.id.rcv_product);
        resale = view.findViewById(R.id.review);
        gh = view.findViewById(R.id.giohang);
        xemthem = (Button) view.findViewById(R.id.xemthem);
        tvsreach = view.findViewById(R.id.tvsreach);


        viewPagerSlidePhoto = view.findViewById(R.id.vp_slide_photo);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        listSlidePhoto = getListSlidePhoto();


        ssale = new ArrayList<Sale>();
        sgg = new AdapterSale(getActivity(), ssale);
        resale.setHasFixedSize(true);
        resale.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        resale.setAdapter(sgg);
        sp = new ArrayList<Product>();
        adaptersanpham = new AdapterProduct(getActivity(), sp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adaptersanpham);
        if (listgh != null) {
        } else {
            listgh = new ArrayList<Cart>();
        }
        if (mangkhachHang != null) {

        } else {
            mangkhachHang = new ArrayList<Customer>();
        }
        if (yt != null) {

        } else {
            yt = new ArrayList<Favourite>();
        }
        if (vc != null) {

        } else {
            vc = new ArrayList<Product>();
        }
        if (objdh != null) {

        } else {
            objdh = new ArrayList<Bill>();
        }
        getsanpham();
        getsale();
        event();
        setDataSlidePhotoAdapter();
        timkiem();
        return view;
    }

    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu) {
        getLayoutInflater().inflate(R.menu.sreach, (ViewGroup) menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptersanpham.filter(s.trim());
                return false;
            }
        });
        return true;
    }
    // Set Adapter cho viewPagerSlidePhoto
    private void setDataSlidePhotoAdapter() {


        slidePhotoAdapter = new AdapterBanner(listSlidePhoto, this);
        viewPagerSlidePhoto.setAdapter(slidePhotoAdapter);
        circleIndicator.setViewPager(viewPagerSlidePhoto);
        slidePhotoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        // Auto chuyển các slide photo
        autoSildeImage();
    }

    // Lấy Product để vào slide
    private List<SlidePhoto> getListSlidePhoto() {

        listSlidePhoto.add(new SlidePhoto("https://theme.hstatic.net/1000030244/1000643430/14/slide_5.png?v=5452"));
        listSlidePhoto.add(new SlidePhoto("https://theme.hstatic.net/1000030244/1000643430/14/slide_1.png?v=5452"));
        listSlidePhoto.add(new SlidePhoto("https://theme.hstatic.net/1000030244/1000643430/14/slide_4.png?v=5452"));
//
        return listSlidePhoto;


    }



    private void autoSildeImage() {
        if (listSlidePhoto == null || listSlidePhoto.isEmpty() || viewPagerSlidePhoto == null) {
            return;
        }
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPagerSlidePhoto.getCurrentItem();
                        int totalItem = listSlidePhoto.size() - 1;

                        // Nếu item hiện tại chưa phải cuối cùng
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPagerSlidePhoto.setCurrentItem(currentItem);
                        } else {
                            viewPagerSlidePhoto.setCurrentItem(0);
                        }
                    }
                });
            }

            // xử lý thêm để set time
        }, 500, 3000);
    }


    public void event() {

        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainCart.class);
                startActivity(intent);
            }
        });

        xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainSale.class);

                startActivity(intent);

            }
        });

    }


    public void timkiem() {
        tvsreach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainSearch.class);
                startActivity(intent);
            }
        });


    }

    public void Quangcao() {
        ArrayList<String> QC = new ArrayList<>();
        QC.add("https://1989.com.vn/wp-content/uploads/2019/03/top30.jpg");
        QC.add("https://sendalongphung.com/wp-content/uploads/2018/09/0-cay-canh-trong-trong-nha.jpg");
        QC.add("https://bancongxanh.com/wp-content/uploads/2019/04/Gi%C3%A1-c%C3%A2y-c%E1%BA%A3nh-v%C4%83n-ph%C3%B2ng-%C4%90%C3%A0-N%E1%BA%B5ng-m%E1%BB%9Bi-nh%E1%BA%A5t-2019.jpg");
        QC.add("https://ncppb.com/wp-content/uploads/2019/04/top-10-website-ban-cay-canh.jpg");
        QC.add("https://afamilycdn.com/2019/9/5/33-1567649196717622170194-crop-15676492145021350614745.jpg");
        QC.add("https://afamilycdn.com/2019/10/2/20190530beginnerplants0007-1570002818813385100229-crop-157000282538490310658.jpg");
        QC.add("https://kienviet.net/wp-content/uploads/2019/05/H1-e1559014099672.jpg");
        for (int i = 0; i < QC.size(); i++) {
            ImageView image = new ImageView(getActivity());
            Picasso.get().load(QC.get(i)).into(image);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(image);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.hieuung);
        Animation animation_out = AnimationUtils.loadAnimation(getActivity(), R.anim.hieuung2);
        viewFlipper.setInAnimation(animation);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void getsale() {
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
                        // int giam = jsonsp.getInt("giamoi");
                        int sale = jsonsp.getInt("sale");
                        String igmsp = jsonsp.getString("igmcay");
                        String mota = jsonsp.getString("mota");

                        ssale.add(new Sale(id, tensp, gia, sale, igmsp, mota));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                sgg.notifyDataSetChanged();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        resale.setLayoutManager(layoutManager);
    }

    private void getsanpham() {

        RequestQueue connnect = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlsanpham, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonsp = response.getJSONObject(i);
                        int id = jsonsp.getInt("id");
                        String tensp = jsonsp.getString("tensp");
                        int gia = jsonsp.getInt("giasp");
                        String igmsp = jsonsp.getString("igmsp");
                        String mota = jsonsp.getString("mota");

                        sp.add(new Product(id, tensp, gia, igmsp, mota));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                adaptersanpham.notifyDataSetChanged();
                Toast.makeText(getContext().getApplicationContext(), "" + ssale.size(), Toast.LENGTH_SHORT).show();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e( "aaaaaaaaaaaaaaaaa: ",error.toString() );
                    }
                }
        );
        connnect.add(jsonArray);

    }
}