package sk.upjs.vma.formativ.ActivityUcitel;


import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.UspesnostSerie;

public class UspesnostiAsyncTask extends AsyncTask<Void,Void,List<UspesnostSerie>> {

    private KliknutieSeriaListener listener;
    private int idSeria;
    private List<UspesnostSerie> list;

    void nastavListener(KliknutieSeriaListener listener, int id){
        this.listener = listener;
        this.idSeria = id;
    }

    @Override
    protected List<UspesnostSerie> doInBackground(Void... voids) {
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Call<List<UspesnostSerie>> vystup = schemaJson.dajUspesnosti(idSeria);
        try {
            list = vystup.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<UspesnostSerie> uspesnostSeries) {
        super.onPostExecute(uspesnostSeries);
        listener.uspesnosti(uspesnostSeries);
    }
}
