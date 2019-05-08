package sk.upjs.vma.formativ.ActivityUcitel;


import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Seria;

public class SeriaAsyncTask extends AsyncTask<Seria,Void,Seria> {

    private KliknutieSeriaListener listener;
    private int idPouzivatela;

    void nastavListener(KliknutieSeriaListener listener, int id){
        this.listener = listener;
        this.idPouzivatela = id;

    }

    @Override
    protected Seria doInBackground(Seria... serias) {
        Seria seria = serias[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Call<List<Seria>> vystup = schemaJson.vytvor_seriu(seria,idPouzivatela);
        try {
            List<Seria> vysledok = vystup.execute().body();
            if(vysledok != null) {
                for (Seria vystupnaSeria : vysledok) {
                    return vystupnaSeria;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Seria seria) {
        listener.uspesnePridanaSeria(seria);
    }
}
