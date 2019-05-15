package sk.upjs.vma.formativ.ActivityUcitel;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;

import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Seria;

public class ZmazSerieAsyncTask extends AsyncTask<Void,Void,Boolean> {

    private KliknutieSeriaListener listener;
    private ArrayList<Seria> zoznam;
    private Call<Boolean> vystupZmazane;
    private Boolean zmazane;

    void nastavListener(KliknutieSeriaListener listener, ArrayList<Seria> oznam){
        this.zoznam = oznam;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Log.d("ASYNC: ", "PRED LOOPOM");
        Log.d("ASYNC: ",    Integer.toString(zoznam.size()));
        for(Seria ser: zoznam){
            vystupZmazane = schemaJson.vymazSerie(ser.getId());
            Log.d("ZMAZANA SERIA: ", Integer.toString(ser.getId()));

            try {
                zmazane = vystupZmazane.execute().body();
                if (!zmazane){
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.serieZmazane();
    }
}
