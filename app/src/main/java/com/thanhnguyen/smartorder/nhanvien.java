package com.thanhnguyen.smartorder;

public class nhanvien {

    private String tdn;
    private String tennv;
    private String ngaysinh;
    private String sdt;
    private String anh;
    public void setTdn(String tdn) {
        this.tdn = tdn;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public nhanvien(String tdn, String tennv, String ngaysinh, String sdt, String anh) {
        this.tdn = tdn;
        this.tennv = tennv;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.anh = anh;
    }

    public  String getTdn() {
        return tdn;
    }

    public String getTennv() {
        return tennv;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public String getAnh() {
        return anh;
    }



}
