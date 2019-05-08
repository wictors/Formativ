package sk.upjs.vma.formativ.entity;

import java.io.Serializable;

public class Otazka implements Serializable {

    private int id;
    private String nazov;
    private int poradie;
    private int id_seria;
    private String moznost1;
    private String moznost2;
    private String moznost3;
    private String moznost4;
    private String moznost5;

    public Otazka() {
    }

    public Otazka(String nazov, int poradie, int id_seria, String moznost1,
                  String moznost2, String moznost3, String moznost4, String moznost5) {
        this.nazov = nazov;
        this.poradie = poradie;
        this.id_seria = id_seria;
        this.moznost1 = moznost1;
        this.moznost2 = moznost2;
        this.moznost3 = moznost3;
        this.moznost4 = moznost4;
        this.moznost5 = moznost5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public int getPoradie() {
        return poradie;
    }

    public void setPoradie(int poradie) {
        this.poradie = poradie;
    }

    public int getId_seria() {
        return id_seria;
    }

    public void setId_seria(int id_seria) {
        this.id_seria = id_seria;
    }

    public String getMoznost1() {
        return moznost1;
    }

    public void setMoznost1(String moznost1) {
        this.moznost1 = moznost1;
    }

    public String getMoznost2() {
        return moznost2;
    }

    public void setMoznost2(String moznost2) {
        this.moznost2 = moznost2;
    }

    public String getMoznost3() {
        return moznost3;
    }

    public void setMoznost3(String moznost3) {
        this.moznost3 = moznost3;
    }

    public String getMoznost4() {
        return moznost4;
    }

    public void setMoznost4(String moznost4) {
        this.moznost4 = moznost4;
    }

    public String getMoznost5() {
        return moznost5;
    }

    public void setMoznost5(String moznost5) {
        this.moznost5 = moznost5;
    }

    @Override
    public String toString() {
        return nazov;
    }
}
