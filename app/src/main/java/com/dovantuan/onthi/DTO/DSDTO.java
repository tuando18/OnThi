package com.dovantuan.onthi.DTO;

public class DSDTO {
    private int id;
    private String ngaythi;
    private String ca;
    private String phong;
    private String tenmon;

    public DSDTO() {
    }

    public DSDTO(String ngaythi, String ca, String phong, String tenmon) {
        this.ngaythi = ngaythi;
        this.ca = ca;
        this.phong = phong;
        this.tenmon = tenmon;
    }

    public DSDTO(int id, String ngaythi, String ca, String phong, String tenmon) {
        this.id = id;
        this.ngaythi = ngaythi;
        this.ca = ca;
        this.phong = phong;
        this.tenmon = tenmon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgaythi() {
        return ngaythi;
    }

    public void setNgaythi(String ngaythi) {
        this.ngaythi = ngaythi;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }
}
