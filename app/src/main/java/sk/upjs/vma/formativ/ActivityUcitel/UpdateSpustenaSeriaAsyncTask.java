package sk.upjs.vma.formativ.ActivityUcitel;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Seria;

public class UpdateSpustenaSeriaAsyncTask extends AsyncTask<Seria,Void,Boolean> {
    @Override
    protected Boolean doInBackground(Seria... strings) {
        Seria seria = strings[0];
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        Call<Boolean> vystup = schemaJson.updateSpustena(seria);
        boolean vysledok;
        try {
            vysledok = vystup.execute().body();
            return vysledok;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
