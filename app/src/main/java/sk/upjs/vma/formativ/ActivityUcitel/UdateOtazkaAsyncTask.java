package sk.upjs.vma.formativ.ActivityUcitel;


import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Otazka;

public class UdateOtazkaAsyncTask extends AsyncTask<Otazka, Void, Boolean> {

    private KliknutieSeriaListener listener;

    void nastavListener(KliknutieSeriaListener listener){
        this.listener = listener;
    }


    @Override
    protected Boolean doInBackground(Otazka... otazkas) {
        Otazka otazka = otazkas[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Call<Boolean> vystup = schemaJson.updateOtazka(otazka);
        boolean vysledok;
        try {
            vysledok = vystup.execute().body();
            return vysledok;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            listener.pridanaOtazka();
        }
    }
}
