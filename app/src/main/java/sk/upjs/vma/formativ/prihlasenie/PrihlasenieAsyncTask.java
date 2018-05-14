package sk.upjs.vma.formativ.prihlasenie;


import android.os.AsyncTask;
import android.util.Log;


import org.mindrot.jbcrypt.BCrypt;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Pouzivatel;

import java.io.IOException;
import java.util.List;

public class PrihlasenieAsyncTask extends AsyncTask<String,Void,Pouzivatel> {

    private PrihlasPouzivatelaListener listener;

    PrihlasenieAsyncTask() {
    }

    void nastavListener(PrihlasPouzivatelaListener listener){
        this.listener = listener;
    }


    @Override
    protected Pouzivatel doInBackground(String... strings) {
        String meno = strings[0];
        String heslo = strings[1];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        try {
            Call<List<Pouzivatel>> vystup = schemaJson.kontrola_prihlasenia(meno);
            List<Pouzivatel> pouzivatelia = vystup.execute().body();
            if (pouzivatelia != null) {
                for (Pouzivatel pouzivatel : pouzivatelia) {
                    Log.d("OVEROVANIE", "Zacinam kontrolu hesla");
                    if (kontrolaHesla(heslo,pouzivatel.getHeslo())) {
                        Log.d("OVEROVANIE", "Skoncil kontrolu hesla");
                        return pouzivatel;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Pouzivatel pouzivatel) {
        listener.prihlasPouzivatela(pouzivatel);
    }

    private static boolean kontrolaHesla(String vlozeneHeslo, String hash) {
        boolean overenie = false;
        overenie = BCrypt.checkpw(vlozeneHeslo,hash);
        return(overenie);
    }
}

