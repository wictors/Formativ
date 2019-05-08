package sk.upjs.vma.formativ.ActivityUcitel;


import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Seria;

public class UpdateSeriaAsyncTask extends AsyncTask<Seria,Void,Boolean> {

    private KliknutieSeriaListener listener;
    private Seria seria;

    public void nastavListener (KliknutieSeriaListener listener, Seria seria){
        this.listener = listener;
        this.seria = seria;
    }

    @Override
    protected Boolean doInBackground(Seria... serias) {
        Seria seria = serias[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Call<Boolean> vystup = schemaJson.updateSeria(seria);
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
        listener.klikSeria(seria);
    }
}
