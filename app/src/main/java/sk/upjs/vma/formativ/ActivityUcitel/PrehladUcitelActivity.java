package sk.upjs.vma.formativ.ActivityUcitel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import sk.upjs.vma.formativ.KliknutieSeriaListener;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Pouzivatel;
import sk.upjs.vma.formativ.entity.Seria;

public class PrehladUcitelActivity extends AppCompatActivity implements KliknutieSeriaListener{

    private Pouzivatel pouzivatel;
    private int idPouzivatela = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehlad_ucitel);

        pouzivatel = (Pouzivatel) getIntent().getSerializableExtra("Pouzivatel");
        idPouzivatela = pouzivatel.getId();

        if(jeTablet()){
            ZoznamSeriiUcitelFragment zoznamSeriiUcitelFragment =
                    (ZoznamSeriiUcitelFragment) getFragmentManager().findFragmentById(R.id.zoznam_serii_fragment);
            zoznamSeriiUcitelFragment.nastavListenerId(this, idPouzivatela);

        }else{
            ZoznamSeriiUcitelFragment zoznamSeriiUcitelFragment =
                    new ZoznamSeriiUcitelFragment();
            zoznamSeriiUcitelFragment.nastavListenerId(this, idPouzivatela);
            getFragmentManager().beginTransaction()
                    .replace(R.id.prehlad_ucitel_activity, zoznamSeriiUcitelFragment).commit();
        }


    }

    public boolean jeTablet(){
        return findViewById(R.id.prehlad_ucitel_activity) == null;
    }

    @Override
    public void klikSeria(Seria seria) {
        Log.d("TAG_SERIA", seria.getNazov());
    }
}
