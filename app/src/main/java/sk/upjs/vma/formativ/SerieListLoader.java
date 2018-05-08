package sk.upjs.vma.formativ;


import android.content.Context;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Seria;

public class SerieListLoader extends AbstractObjectLoader<List<Seria>> {

    private int id;

    public SerieListLoader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    public List<Seria> loadInBackground() {
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        try {
            Call<List<Seria>> vystup = schemaJson.daj_zoznam_serii(id);
            return vystup.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
