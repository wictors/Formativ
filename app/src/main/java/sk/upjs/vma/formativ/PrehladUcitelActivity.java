package sk.upjs.vma.formativ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PrehladUcitelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehlad_ucitel);

        if(jeTablet()){
            ZoznamSeriiUcitelFragment zoznamSeriiUcitelFragment =
                    (ZoznamSeriiUcitelFragment) getFragmentManager().findFragmentById(R.id.zoznam_serii_fragment);
        }else{
            ZoznamSeriiUcitelFragment zoznamSeriiUcitelFragment =
                    new ZoznamSeriiUcitelFragment();

            getFragmentManager().beginTransaction()
                    .replace(R.id.prehlad_ucitel_activity, zoznamSeriiUcitelFragment).commit();
        }


    }

    public boolean jeTablet(){
        if(findViewById(R.id.prehlad_ucitel_activity) == null){
            return true;
        }
        return false;
    }
}
