package sk.upjs.vma.formativ.ActivityUcitel;


import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;

public class ZoznamOdpovediAsyncTask extends AsyncTask<Otazka,Void,List<Odpoved>> {

    PrehladOdpovediListener listener;
    List<Odpoved> list;

    public void nastavListener(PrehladOdpovediListener listener){
        this.listener = listener;
    }

    @Override
    protected List<Odpoved> doInBackground(Otazka... otazkas) {
        Otazka otazka = otazkas[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Call<List<Odpoved>> vystup = schemaJson.dajOdpovedeUcitelovi(otazka.getId());
        try {
            list = vystup.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    protected void onPostExecute(List<Odpoved> odpoveds) {
        super.onPostExecute(odpoveds);
        listener.spracujOdpovede(odpoveds);
    }
}
