package sk.upjs.vma.formativ;


import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Seria;

public class OtazkyListLoader extends AbstractObjectLoader <List<Otazka>> {

    private int idSerie;
    private int idPouzivatel;
    private List<Otazka> list = new ArrayList<>();

    public OtazkyListLoader(Context context, int idSerie, int idPouzivatel) {
        super(context);
        this.idSerie = idSerie;
        this.idPouzivatel = idPouzivatel;
    }

    @Override
    public List<Otazka> loadInBackground() {
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        try {
            Call<List<Otazka>> vystup = schemaJson.daj_zoznam_otazok(idSerie, idPouzivatel);
            return vystup.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
