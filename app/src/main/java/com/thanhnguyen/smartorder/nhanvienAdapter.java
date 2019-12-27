package com.thanhnguyen.smartorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class nhanvienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<nhanvien> nhanvienList;

    public nhanvienAdapter(Context context, int show, ArrayList<nhanvien> arrayListNV) {
        this.context=context;
        this.layout= show;
        this.nhanvienList= arrayListNV;
    }

    @Override
    public int getCount() {
        return nhanvienList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {
        TextView txttdn, txttennv, txtsdt;
        ImageView btnsua, btnxoa;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txttdn = (TextView) view.findViewById(R.id.tdn);
            holder.txttennv = (TextView) view.findViewById(R.id.hoten);
            holder.txtsdt = (TextView) view.findViewById(R.id.sdt);
            holder.btnxoa = (ImageView) view.findViewById(R.id.btnxoa);
            holder.btnsua = (ImageView) view.findViewById(R.id.btnsua);
            view.setTag(holder);
        }
        else{
            holder= (ViewHolder) view.getTag();

        }
        nhanvien nv= nhanvienList.get(position);
        holder.txttdn.setText(nv.getTdn());
        holder.txttennv.setText(nv.getTennv());
        holder.txtsdt.setText(nv.getSdt());
        return view;
    }
}
