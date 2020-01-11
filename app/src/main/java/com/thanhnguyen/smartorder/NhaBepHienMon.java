package com.thanhnguyen.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NhaBepHienMon extends AppCompatActivity {

    DatabaseReference mData = FirebaseDatabase.getInstance("https://smartorder-e3187.firebaseio.com/").getReference();
    ArrayList<String> arrayList;
    ArrayAdapter adapter;
    String tendn ="";
    ListView dsmon;
    Button btnBack;
    boolean check = false;
   // SharedPreferences
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_bep_hien_mon);
        dsmon = (ListView) findViewById(R.id.lvMonAn);
        btnBack= (Button) findViewById(R.id.btnBack);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        tendn = intent.getStringExtra("tenmon");
        adapter = new ArrayAdapter(NhaBepHienMon.this,
                android.R.layout.simple_list_item_1,
                arrayList
        );
        final Button x = (Button) findViewById(R.id.button);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check){
                    x.setText("Hoàn thành");
                    mData.child("BanAn").child(tendn).child("TinhTrang").setValue("cho");
                }else{
                    mData.child("BanAn").child(tendn).child("TinhTrang").setValue("hoanthanh");
                    Intent intent = new Intent(NhaBepHienMon.this,NhaBepNhanDL.class);
                    startActivity(intent);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iTrangChu = new Intent(NhaBepHienMon.this, home.class);
                startActivity(iTrangChu);
            }
        });
        adapter.notifyDataSetChanged();
        HienMon();
        //Toast.makeText(NhaBepHienMon.this,tendn,Toast.LENGTH_SHORT).show();
        //Log.d("AAA",tendn);
        dsmon.setAdapter(adapter);
    }

    private void HienMon() {

        mData.child("BanAn").child(tendn.trim()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey().trim();
                Toast.makeText(NhaBepHienMon.this,key,Toast.LENGTH_SHORT).show();
                if(!key.equals("TinhTrang")){
                    Log.d("AAA","x - " + key);
                    String tm = dataSnapshot.child("tenmon").getValue().toString();
                    String sl = dataSnapshot.child("soluong").getValue().toString();
                    Log.d("AAA",tm+sl);
                    arrayList.add(tm + " - số lượng: " + sl);
                    adapter.notifyDataSetChanged();
                    dsmon.setAdapter(adapter);
                }else {
                    if(dataSnapshot.getValue().toString().trim().equals("true")){
                        Button x = (Button) findViewById(R.id.button);
                        x.setText("Chấp nhận");
                        check = false;
                    }
                    else if(dataSnapshot.getValue().toString().trim().equals("cho")){
                        Button x = (Button) findViewById(R.id.button);
                        x.setText("Hoàn thành");
                        check = true;
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
