package com.thanhnguyen.smartorder;

public class mon {
    String tenmon;
    String soluong;

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public mon(String tenmon, String soluong) {
        this.tenmon = tenmon;
        this.soluong = soluong;
    }
}
