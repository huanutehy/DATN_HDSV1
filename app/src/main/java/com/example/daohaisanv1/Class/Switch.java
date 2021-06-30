package com.example.daohaisanv1.Class;

public class Switch {
    private int idvc;
    private String ten;
    private int phiship;
    private String hinh;


    public int getIdvc() {
        return idvc;
    }

    public void setIdvc(int idvc) {
        this.idvc = idvc;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getPhiship() {
        return phiship;
    }

    public void setPhiship(int phiship) {
        this.phiship = phiship;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Switch(int idvc, String ten, int phiship, String hinh) {
        super();

        this.idvc = idvc;
        this.ten = ten;
        this.phiship = phiship;
        this.hinh = hinh;
    }




}
