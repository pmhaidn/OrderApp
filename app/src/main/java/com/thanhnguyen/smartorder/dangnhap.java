package com.thanhnguyen.smartorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thanhnguyen.smartorder.DAO.NhanVienDAO;

public class dangnhap extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYDN,btnDangKyDN;
    EditText edTenDangNhapDN, edMatKhauDN;
    NhanVienDAO nhanVienDAO;

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
