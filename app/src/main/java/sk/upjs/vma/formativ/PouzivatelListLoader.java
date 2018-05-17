package sk.upjs.vma.formativ;


import android.content.Context;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Pouzivatel;

public class PouzivatelListLoader extends AbstractObjectLoader<List<Pouzivatel>>{

    public PouzivatelListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Pouzivatel> loadInBackground() {
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        try {
            Call<List<Pouzivatel>> vystup = schemaJson.daj_zoznam_pouzivatelov();
            return vystup.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
