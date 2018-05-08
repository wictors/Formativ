package sk.upjs.vma.formativ.entity;

import java.io.Serializable;

public class Pouzivatel implements Serializable {

    private int id;
    private String meno;
    private String priezvisko;
    private String prihlasovacie_meno;
    private String heslo;
    private String rola;

    public Pouzivatel() {
    }

    public Pouzivatel(int id, String meno, String priezvisko, String prihlasovacie_meno, String heslo, String rola) {
        this.id = id;
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.prihlasovacie_meno = prihlasovacie_meno;
        this.heslo = heslo;
        this.rola = rola;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public void setPrihlasovacie_meno(String prihlasovacie_meno) {
        this.prihlasovacie_meno = prihlasovacie_meno;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public int getId() {
        return id;
    }

    public String getMeno() {
        return meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public String getPrihlasovacie_meno() {
        return prihlasovacie_meno;
    }

    public String getHeslo() {
        return heslo;
    }

    public String getRola() {
        return rola;
    }

    @Override
    public String toString() {
        return "Pouzivatel{" +
                "id=" + id +
                ", meno='" + meno + '\'' +
                ", priezvisko='" + priezvisko + '\'' +
                ", prihlasovacie_meno='" + prihlasovacie_meno + '\'' +
                ", heslo='" + heslo + '\'' +
                ", rola='" + rola + '\'' +
                '}';
    }
}
