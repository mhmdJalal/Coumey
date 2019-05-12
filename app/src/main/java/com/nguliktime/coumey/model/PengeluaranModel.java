package com.nguliktime.coumey.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PengeluaranModel extends RealmObject{

    @PrimaryKey private Integer id;
    private String nama;
    private Integer jumlah;
    private Integer harga;
    private Integer subtotal;
    private Integer idBulan;
    private String tanggal;
    private String bulan;

    @Override
    public Realm getRealm() {
        return super.getRealm();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getBulan() {
        return bulan;
    }

    public void setIdBulan(Integer idBulan) {
        this.idBulan = idBulan;
    }

    public Integer getIdBulan() {
        return idBulan;
    }
}
