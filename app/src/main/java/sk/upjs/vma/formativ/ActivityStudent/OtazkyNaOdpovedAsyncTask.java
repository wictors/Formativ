package sk.upjs.vma.formativ.ActivityStudent;


import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Seria;

public class OtazkyNaOdpovedAsyncTask extends AsyncTask<Seria,Void,List<Otazka>> {

    private KliknutieSeriaStudentListener listener;
    private int idPouzivatela;
    private List<Otazka> otazky;


    void nastavListener(KliknutieSeriaStudentListener listener, int id){
        this.listener = listener;
        this.idPouzivatela = id;

    }

    @Override
    protected List<Otazka> doInBackground(Seria... serias) {
        Seria seria = serias[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        try {
            Call<List<Otazka>> vystup = schemaJson.daj_zoznam_otazok(seria.getId(), idPouzivatela);
            otazky = vystup.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return otazky;
    }

    @Override
    protected void onPostExecute(List<Otazka> otazkas) {
        super.onPostExecute(otazkas);
        listener.zpracujOtazky(otazkas);
    }
}
