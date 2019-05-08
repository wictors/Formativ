package sk.upjs.vma.formativ.entity;


import java.io.Serializable;

public class UspesnostSerie implements Serializable {

    private int id;
    private int id_seria;
    private int id_pouzivatel;
    private int uspesnost;

    public UspesnostSerie(){    }

    public UspesnostSerie(int seria, int pouzivatel, int uspech){
        this.id_seria = seria;
        this.id_pouzivatel = pouzivatel;
        this.uspesnost = uspech;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_seria() {
        return id_seria;
    }

    public void setId_seria(int id_seria) {
        this.id_seria = id_seria;
    }

    public int getId_pouzivatel() {
        return id_pouzivatel;
    }

    public void setId_pouzivatel(int id_pouzivatel) {
        this.id_pouzivatel = id_pouzivatel;
    }

    public int getUspesnost() {
        return uspesnost;
    }

    public void setUspesnost(int uspesnost) {
        this.uspesnost = uspesnost;
    }
}
