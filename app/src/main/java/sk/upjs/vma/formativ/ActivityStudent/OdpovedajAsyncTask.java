package sk.upjs.vma.formativ.ActivityStudent;


import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import sk.upjs.vma.formativ.DB.RetroFitFactory;
import sk.upjs.vma.formativ.DB.SchemaJson;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.UspesnostSerie;

public class OdpovedajAsyncTask extends AsyncTask<Void,Void,Boolean> {

    private KliknutieSeriaStudentListener listener;
    private UspesnostSerie uspesnostSerie;
    private ArrayList<Odpoved> odpovede;
    private Call<Boolean> vystupOdpovede;
    private Call<Boolean> vystupUspesnosti;
    private Boolean odpoved;
    private Boolean uspesnost;

    public void nastavListener(KliknutieSeriaStudentListener listener,
                               UspesnostSerie uspesnostSerie, ArrayList<Odpoved> odpovede){
        this.listener = listener;
        this.uspesnostSerie = uspesnostSerie;
        this.odpovede = odpovede;
    }

    @Override
    protected Boolean doInBackground(Void... odpoveds) {
        SchemaJson schemaJson = RetroFitFactory.getSchema();
        try {
            for (Odpoved odp: odpovede){
                vystupOdpovede = schemaJson.odpovedaj(odp);
                odpoved = vystupOdpovede.execute().body();
                if (!odpoved){
                    return false;
                }
            }
            vystupUspesnosti = schemaJson.uspesnostSerie(uspesnostSerie);
            uspesnost = vystupUspesnosti.execute().body();
            if (!uspesnost){
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
