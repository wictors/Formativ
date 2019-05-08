package sk.upjs.vma.formativ.entity;


import java.io.Serializable;

public class Odpoved implements Serializable {

    private int id;
    private int id_otazka;
    private int id_pouzivatel;
    private String odpoved;
    private String spravnost;

    public Odpoved(int id_otazka, int id_pouzivatel, String odpoved, String spravnost){
        this.id_otazka = id_otazka;
        this.id_pouzivatel = id_pouzivatel;
        this.odpoved = odpoved;
        this.spravnost = spravnost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_otazka() {
        return id_otazka;
    }

    public void setId_otazka(int id_otazka) {
        this.id_otazka = id_otazka;
    }

    public int getId_pouzivatel() {
        return id_pouzivatel;
    }

    public void setId_pouzivatel(int id_pouzivatel) {
        this.id_pouzivatel = id_pouzivatel;
    }

    public String getOdpoved() {
        return odpoved;
    }

    public void setOdpoved(String odpoved) {
        this.odpoved = odpoved;
    }

    public String getSpravnost() {
        return spravnost;
    }

    public void setSpravnost(String spravnost) {
        this.spravnost = spravnost;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id_otazka);
        builder.append(id_pouzivatel);
        builder.append(odpoved);
        builder.append(spravnost);
        return builder.toString();
    }
}
