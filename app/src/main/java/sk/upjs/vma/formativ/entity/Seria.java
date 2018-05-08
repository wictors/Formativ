package sk.upjs.vma.formativ.entity;


import java.io.Serializable;

public class
Seria implements Serializable {

    private int id;
    private String nazov;
    private String spustena;

    public Seria(int id, String nazov, String spustena) {
        this.id = id;
        this.nazov = nazov;
        this.spustena = spustena;
    }

    public Seria() {
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

    public String isSpustena() {
        return spustena;
    }

    public void setSpustena(String spustena) {
        this.spustena = spustena;
    }

    @Override
    public String toString() {
        return nazov;
    }
}
