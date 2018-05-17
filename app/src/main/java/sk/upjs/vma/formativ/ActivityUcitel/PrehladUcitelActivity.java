package sk.upjs.vma.formativ.ActivityUcitel;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sk.upjs.vma.formativ.KliknutieSeriaListener;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Pouzivatel;
import sk.upjs.vma.formativ.entity.Seria;
import sk.upjs.vma.formativ.prihlasenie.LoginActivity;

public class PrehladUcitelActivity extends AppCompatActivity implements KliknutieSeriaListener{

    private Pouzivatel pouzivatel;
    private int idPouzivatela = 1;
    private static final String KEY_POUZIVATEL = "pouzivatel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehlad_ucitel);

        if(savedInstanceState == null) {
            pouzivatel = (Pouzivatel) getIntent().getSerializableExtra("Pouzivatel");
        }else{
            pouzivatel = (Pouzivatel) savedInstanceState.getSerializable(KEY_POUZIVATEL);

        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_POUZIVATEL, pouzivatel);
    }

    public boolean jeTablet(){
        return findViewById(R.id.prehlad_ucitel_activity) == null;
    }

    @Override
    public void klikSeria(Seria seria) {
        Toast.makeText(this, seria.getNazov(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void novaSeria() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_action_bar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.vyhladavanie)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.odhlasenie:
                odhlasit();
                return true;
            case R.id.vyhladavanie:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void odhlasit() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
