package sk.upjs.vma.formativ.Admin;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Pouzivatel;

public class RegistraciaAsyncTask extends AsyncTask<Pouzivatel,Void,Boolean> {

    private RegistrujListener listener;

    void nastavListener(RegistrujListener listener){
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Pouzivatel... pouzivatels) {
        Pouzivatel pouzivatel = pouzivatels[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Call<Boolean> vystup = schemaJson.registruj(pouzivatel);
        try {
            boolean vysledok = vystup.execute().body();
            return vysledok;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
