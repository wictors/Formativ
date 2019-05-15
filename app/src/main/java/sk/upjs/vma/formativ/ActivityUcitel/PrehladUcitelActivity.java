package sk.upjs.vma.formativ.ActivityUcitel;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Pouzivatel;
import sk.upjs.vma.formativ.entity.Seria;
import sk.upjs.vma.formativ.entity.UspesnostSerie;
import sk.upjs.vma.formativ.prihlasenie.LoginActivity;

public class PrehladUcitelActivity extends AppCompatActivity implements KliknutieSeriaListener{

    private Pouzivatel pouzivatel;
    private Seria seria;
    private int idPouzivatela;
    private boolean upravaSerie = false;
    private static final String KEY_POUZIVATEL = "Pouzivatel";
    private static final String KEY_SERIA = "Seria";
    private static final String KEY_UPRAVA = "Uprava";
    private ConnectivityManager cm;
    private ZoznamSeriiUcitelFragment zoznamSeriiUcitelFragment;
    private DetailSerieUcitelFragment detailSerieUcitelFragment;
    private DetailNovaSeriaFragment detailNovaSeriaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehlad_ucitel);

        if(savedInstanceState == null) {
            pouzivatel = (Pouzivatel) getIntent().getSerializableExtra("Pouzivatel");
        }else{
            pouzivatel = (Pouzivatel) savedInstanceState.getSerializable(KEY_POUZIVATEL);
            seria = (Seria) savedInstanceState.getSerializable(KEY_SERIA);
            upravaSerie = (Boolean) savedInstanceState.getSerializable(KEY_UPRAVA);

        }
        idPouzivatela = pouzivatel.getId();
        if(jeTablet()){
            zoznamSeriiUcitelFragment = (ZoznamSeriiUcitelFragment)
                    getFragmentManager().findFragmentById(R.id.zoznam_serii_Fragment);
            zoznamSeriiUcitelFragment.nastavListenerId(this, idPouzivatela, true, upravaSerie, seria);

            if (upravaSerie){
                uspesnePridanaSeria(this.seria);
            }else {
                detailSerieUcitelFragment = new DetailSerieUcitelFragment();
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, detailSerieUcitelFragment).commit();
                detailSerieUcitelFragment.nastavListener(this);
            }
            //upravaSerie = false;
            zoznamSeriiUcitelFragment.refresh(upravaSerie);

        }else{
            zoznamSeriiUcitelFragment =
                    new ZoznamSeriiUcitelFragment();
            zoznamSeriiUcitelFragment.nastavListenerId(this, idPouzivatela, false, upravaSerie, seria);
            getFragmentManager().beginTransaction()
                    .replace(R.id.prehlad_ucitel_activity, zoznamSeriiUcitelFragment).commit();
            //upravaSerie = false;
            if (upravaSerie){
                uspesnePridanaSeria(this.seria);
            }
        }

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_POUZIVATEL, pouzivatel);
        if (seria != null){
            outState.putSerializable(KEY_SERIA, seria);
        }
        outState.putSerializable(KEY_UPRAVA, upravaSerie);
    }

    public boolean jeTablet(){
        return findViewById(R.id.prehlad_ucitel_activity) == null;
    }

    @Override
    public void klikSeria(Seria seria) {
        this.seria = seria;
        if (upravaSerie) {
            onBackPressed();
        }
        UspesnostiAsyncTask uspesnostiAsyncTask = new UspesnostiAsyncTask();
        uspesnostiAsyncTask.nastavListener(this, seria.getId());
        uspesnostiAsyncTask.execute();
    }

    @Override
    public void uspesnosti(List<UspesnostSerie> uspesnostSeries) {
        if (upravaSerie){
            onBackPressed();
        }
        if (!jeTablet()){
            detailSerieUcitelFragment = new DetailSerieUcitelFragment();
            detailSerieUcitelFragment.nastavListener(this);
            detailSerieUcitelFragment.nastavSeriu(seria, idPouzivatela, uspesnostSeries);
            getFragmentManager().beginTransaction()
                    .replace(R.id.prehlad_ucitel_activity,detailSerieUcitelFragment).addToBackStack(null).commit();

        }else{
            detailSerieUcitelFragment.nastavDetail(seria, idPouzivatela, uspesnostSeries);
        }
    }

    @Override
    public void updateOtazka(Otazka otazka) {
        if(jeInternet()){
            UdateOtazkaAsyncTask udateOtazkaAsyncTask = new UdateOtazkaAsyncTask();
            udateOtazkaAsyncTask.nastavListener(this);
            udateOtazkaAsyncTask.execute(otazka);
        }else{
            Toast.makeText(this, "Ziadne internetove pripojenie !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void odstranOtazku(Otazka editOtazka) {
        if (jeInternet()){
            ZmazOtazkaAsyncTask zmazOtazkaAsyncTask = new ZmazOtazkaAsyncTask();
            zmazOtazkaAsyncTask.nastavListener(this);
            zmazOtazkaAsyncTask.execute(editOtazka);
        }else {
            Toast.makeText(this, "Ziadne internetove pripojenie !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void novaSeria(Seria seria) {
        if(jeInternet()){
            SeriaAsyncTask seriaAsyncTask = new SeriaAsyncTask();
            seriaAsyncTask.nastavListener(this, idPouzivatela);
            seriaAsyncTask.execute(seria);
        }else{
            Toast.makeText(this, "Ziadne internetove pripojenie !", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void uspesnePridanaSeria(Seria vystup) {
        if (vystup != null){
            this.upravaSerie = true;
            this.seria = vystup;
            detailNovaSeriaFragment = new DetailNovaSeriaFragment();
            detailNovaSeriaFragment.nastavPremennu(vystup, this, idPouzivatela);
            if(jeTablet()){
                getFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,detailNovaSeriaFragment).addToBackStack(null).commit();
            }else{
                getFragmentManager().beginTransaction()
                        .replace(R.id.prehlad_ucitel_activity,detailNovaSeriaFragment).addToBackStack(null).commit();
            }
        }else{
            Toast.makeText(this, "Seriu sa nepodarilo pridat !", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void pridajOtazkuDoSerie(Otazka otazka) {
        if(jeInternet()){
            PridajOtazkuAsyncTask pridajOtazkuAsyncTask = new PridajOtazkuAsyncTask();
            pridajOtazkuAsyncTask.nastavListener(this);
            pridajOtazkuAsyncTask.execute(otazka);
        }else{
            Toast.makeText(this, "Ziadne internetove pripojenie !", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (upravaSerie){
            upravaSerie = false;
        }
    }

    @Override
    public void updateSeria(Seria seria) {
        upravaSerie = false;
        if(jeInternet()){
            UpdateSeriaAsyncTask updateSeriaAsyncTask = new UpdateSeriaAsyncTask();
            updateSeriaAsyncTask.nastavListener(this, seria);
            updateSeriaAsyncTask.execute(seria);
        }else{
            Toast.makeText(this, "Ziadne internetove pripojenie !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void pridanaOtazka() {
        detailNovaSeriaFragment.nacitajOtazky();
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

    private boolean jeInternet() {
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void vymazSerie(ArrayList<Seria> zoznamOznac) {
        Log.d("ZMAZANA SERIA: ", Integer.toString(zoznamOznac.size()));
        ZmazSerieAsyncTask zmazSerieAsyncTask = new ZmazSerieAsyncTask();
        zmazSerieAsyncTask.nastavListener(this, zoznamOznac);
        zmazSerieAsyncTask.execute();
    }

    @Override
    public void serieZmazane() {
        zoznamSeriiUcitelFragment.refresh(upravaSerie);
    }
}
