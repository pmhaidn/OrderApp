package com.thanhnguyen.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NhaBepNhanDL extends AppCompatActivity {
    DatabaseReference mData = FirebaseDatabase.getInstance("https://smartorder-e3187.firebaseio.com/").getReference();
    ListView lvBanAn;
    QLBanAnAdapter adapter;
    Button btnBack1;
    ArrayList<QLBanAn> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_bep_nhan_dl);
        lvBanAn = (ListView) findViewById(R.id.lvBanAn);
        list = new ArrayList<>();
        addBan();
        btnBack1= (Button) findViewById(R.id.btnBack1);
        adapter = new QLBanAnAdapter(this,R.layout.nhabep,list);

        adapter.notifyDataSetChanged();
        lvBanAn.setAdapter(adapter);
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iTrangChu = new Intent(NhaBepNhanDL.this, home.class);
                startActivity(iTrangChu);
            }
        });
        lvBanAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent MoBan = new Intent(NhaBepNhanDL.this, NhaBepHienMon.class);
                //MoBan.putExtra("monan",list.get(position).getTenban().toString().trim());
                MoBan.putExtra("tenmon",list.get(position).getTenban());
                startActivity(MoBan);
                //Toast.makeText(NhaBepNhanDL.this,list.get(position).getTenban(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBan() {
        mData.child("BanAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey().toString().trim();
                if (dataSnapshot.child("TinhTrang").getValue().toString().trim().equals("true") || dataSnapshot.child("TinhTrang").getValue().toString().trim().equals("cho")){
                    list.add(new QLBanAn(key));
                    adapter.notifyDataSetChanged();
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
