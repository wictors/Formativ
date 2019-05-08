package sk.upjs.vma.formativ.ActivityStudent;


import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;


public class PridanieSerieAsyncTask extends AsyncTask<String,Void, Boolean> {

    private KliknutieSeriaStudentListener listener;
    private int idPouzivatela;

    void nastavListener(KliknutieSeriaStudentListener listener, int id){
        this.listener = listener;
        this.idPouzivatela = id;
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        String cislo = strings[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        int seria = Integer.parseInt(cislo);
        Call<Boolean> vystup = schemaJson.pridajSeriuStudent(seria, idPouzivatela);
        Boolean vysledok;
        try {
            vysledok = vystup.execute().body();
            return vysledok;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.uspesnePridanaSeria(aBoolean);
    }
}
