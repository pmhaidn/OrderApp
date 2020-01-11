package com.thanhnguyen.smartorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thanhnguyen.smartorder.DAO.NhanVienDAO;

import java.util.HashMap;
import java.util.Map;

public class dangnhap extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYDN,btnDangKyDN;
    EditText edTenDangNhapDN, edMatKhauDN;
    NhanVienDAO nhanVienDAO;
    String url="http://vanthanh97.000webhostapp.com/android/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        btnDangKyDN = (Button) findViewById(R.id.btndk_dn);
        btnDongYDN = (Button) findViewById(R.id.btndn_dn);
        edMatKhauDN = (EditText) findViewById(R.id.edmk_dn);
        edTenDangNhapDN = (EditText) findViewById(R.id.edTendn_dn);

        nhanVienDAO = new NhanVienDAO(this);
        btnDongYDN.setOnClickListener(this);
        btnDangKyDN.setOnClickListener(this);
    }



    private void btnDongY(){
        //login(url);
        String sTenDangNhap = edTenDangNhapDN.getText().toString();
        String sMatKhau = edMatKhauDN.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDangNhap, sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);
        if(kiemtra != 0){
            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen",maquyen);
            editor.commit();

            Intent iTrangChu = new Intent(dangnhap.this, home.class);
            iTrangChu.putExtra("tendn",edTenDangNhapDN.getText().toString());
            iTrangChu.putExtra("manhanvien",kiemtra);
            startActivity(iTrangChu);
        }else{
            //Toast.makeText(dangnhap.this,"Đăng nhập thất bại !",Toast.LENGTH_SHORT).show();
        }
    }

    private void btnDangKy(){
        Intent iDangKy = new Intent(dangnhap.this, dangky.class);
        iDangKy.putExtra("landautien",1);
        startActivity(iDangKy);
    }
    public void login(String url) {
        if(edTenDangNhapDN.length()!=0 && edMatKhauDN.length()!=0){
            RequestQueue requestQueue = Volley.newRequestQueue(dangnhap.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("true")){
                                Intent intent = new Intent(dangnhap.this,home.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(dangnhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                                edMatKhauDN.setText("");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(dangnhap.this, "Kết nối sever thất bại!", Toast.LENGTH_SHORT).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("tendn",edTenDangNhapDN.getText().toString().trim());
                    params.put("mk",edMatKhauDN.getText().toString().trim());
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(dangnhap.this,"Dữ liệu trống!",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.btndn_dn:
                btnDongY();
                ;break;

            case R.id.btndk_dn:
                btnDangKy();
                ;break;
        }
    }
}
