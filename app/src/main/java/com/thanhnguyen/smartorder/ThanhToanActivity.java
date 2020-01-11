package com.thanhnguyen.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thanhnguyen.smartorder.CustomAdapter.AdapterHienThiThanhToan;
import com.thanhnguyen.smartorder.DAO.BanAnDAO;
import com.thanhnguyen.smartorder.DAO.GoiMonDAO;
import com.thanhnguyen.smartorder.DTO.ThanhToanDTO;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity{

    GridView gridView;
    Button btnGuiBep,btnThanhToan;
    TextView txtTongTien;
    GoiMonDAO goiMonDAO;
    TextView tinhtrang;
    List<ThanhToanDTO> thanhToanDTOList;
    AdapterHienThiThanhToan adapterHienThiThanhToan;
    long tongtien = 0;
    BanAnDAO banAnDAO;
    int maban=0;
    String tenban = "x";
    DatabaseReference mData = FirebaseDatabase.getInstance("https://smartorder-e3187.firebaseio.com/").getReference();
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);
        tinhtrang = (TextView) findViewById(R.id.txtTinhTrang);
        gridView = (GridView) findViewById(R.id.gvThanhToan);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        btnGuiBep = (Button) findViewById(R.id.btnGuiBep);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);

        Intent intent = getIntent();
        fragmentManager = getSupportFragmentManager();
        maban = getIntent().getIntExtra("maban",0);
        final String tenban= banAnDAO.LayTenBanAn(maban);
        //tenban = intent.getStringExtra("tenban");
        mData.child("BanAn").child(tenban.trim()).child("TinhTrang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("AAA",dataSnapshot.getKey().toString() + " - " + dataSnapshot.getValue().toString());
                if(dataSnapshot.getValue().toString().trim().equals("false")){
                    tinhtrang.setText("Tình trạng: Chưa gửi nhà bếp");
                }
                else if (dataSnapshot.getValue().toString().trim().equals("true")){
                    tinhtrang.setText("Tình trạng: Đã gửi yêu cầu");
                }else if(dataSnapshot.getValue().toString().trim().equals("hoanthanh")){
                    tinhtrang.setText("Tình trạng: Món ăn đã hoàn thành");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //
        //setTinhTrang(tenban);
        //Toast.makeText(this, tenban.toString(), Toast.LENGTH_SHORT).show();
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("BanAn").child(tenban).removeValue();
                boolean kiemtra = banAnDAO.XoaBanAnTheoMa(maban);
                if(kiemtra){
                    Intent iTrangChu = new Intent(ThanhToanActivity.this, home.class);
                    startActivity(iTrangChu);
                    //gửi bảng thống kê nữa
                }else {
                }



            }
        });
        btnGuiBep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("BanAn").child(tenban).child("TinhTrang").setValue("true");
                for (int i=0;i<thanhToanDTOList.size();i++){
                    String tm = thanhToanDTOList.get(i).getTenMonAn().trim();
                    String sl = String.valueOf(thanhToanDTOList.get(i).getSoLuong()).trim();
                    mon x = new mon(tm,sl);
                    mData.child("BanAn").child(tenban).push().setValue(x);
                }
                Toast.makeText(ThanhToanActivity.this,"Gửi yêu cầu thành công",Toast.LENGTH_SHORT).show();
            }
        });


        if(maban != 0){

            HienThiThanhToan();

            for (int i=0; i < thanhToanDTOList.size() ; i++){
                int soluong = thanhToanDTOList.get(i).getSoLuong();
                int giatien = thanhToanDTOList.get(i).getGiaTien();
                tongtien += (soluong*giatien);
            }

            txtTongTien.setText(getResources().getString(R.string.tongcong) + tongtien + " VND");
        }

    }


    private void setTinhTrang(String x) {
        Log.d("AAA","ten ban: " + x);
        mData.child("XXX").setValue("XXX");
        mData.child("BanAn").child(x).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("AAA",dataSnapshot.getKey().toString() + " - " + dataSnapshot.getValue().toString());
                if(dataSnapshot.child("TinhTrang").getValue().toString().trim().equals("false")){
                    tinhtrang.setText("Tình trạng: Chưa gửi nhà bếp");
                }
                else if (dataSnapshot.getValue().toString().trim().equals("true")){
                    tinhtrang.setText("Tình trạng: Đã gửi yêu cầu");
                }else if(dataSnapshot.getValue().toString().trim().equals("hoanthanh")){
                    tinhtrang.setText("Tình trạng: Món ăn đã hoàn thành");
                }
                else {
                    tinhtrang.setText("Tình trạng: Đã chấp nhận");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void HienThiThanhToan(){
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        thanhToanDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this,R.layout.custom_layout_thanhtoan,thanhToanDTOList);
        gridView.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id){
//            case R.id.btnThanhToan: {
//                boolean kiemtra = banAnDAO.XoaBanAnTheoMa(maban);
//                if(kiemtra){
//                    Toast.makeText(this, "Xong!!", Toast.LENGTH_SHORT).show();
//                    Intent iTrangChu = new Intent(ThanhToanActivity.this, home.class);
//                    startActivity(iTrangChu);
//                    //gửi bảng thống kê nữa
//                }else {
//                    Toast.makeText(this, "Lỗi!", Toast.LENGTH_SHORT).show();
//                }
//                ;break;
//            }
//            case  R.id.btnGuiBep: {
//                Toast.makeText(this, "Gửi nhà bếp", Toast.LENGTH_SHORT).show();
//                break;
//
//                /*
//                code
//                 */
//            }
//        }
//    }
}
