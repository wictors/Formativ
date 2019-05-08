package sk.upjs.vma.formativ.ActivityStudent;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sk.upjs.vma.formativ.OtazkyListLoader;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Pouzivatel;
import sk.upjs.vma.formativ.entity.Seria;
import sk.upjs.vma.formativ.entity.UspesnostSerie;
import sk.upjs.vma.formativ.prihlasenie.LoginActivity;

public class Prehlad_Student extends AppCompatActivity implements KliknutieSeriaStudentListener{

    private Pouzivatel pouzivatel;
    private Seria seria;
    private boolean odpovedaSa = false;
    private Otazka otazka;
    private ArrayList<Otazka> otazky;
    private ArrayList<Odpoved> odpovede = new ArrayList<>();

    private int idPouzivatela;
    private static final String KEY_POUZIVATEL = "Pouzivatel";
    private static final String KEY_SERIA = "Seria";
    private static final String KEY_OTAZKA = "Otazka";
    private static final String KEY_ODPOVEDASA = "OdpovedaSa";
    private static final String KEY_OTAZKY = "Otazky";
    private static final String KEY_ODPOVEDE = "Odpovede";
    private ConnectivityManager cm;
    private ZoznamSeriiStudent zoznamSeriiStudent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehlad__student);

        if(savedInstanceState == null) {
            pouzivatel = (Pouzivatel) getIntent().getSerializableExtra("Pouzivatel");
        }else{
            pouzivatel = (Pouzivatel) savedInstanceState.getSerializable(KEY_POUZIVATEL);
            odpovedaSa = (Boolean) savedInstanceState.getSerializable(KEY_ODPOVEDASA);
            if (odpovedaSa) {
                seria = (Seria) savedInstanceState.getSerializable(KEY_SERIA);
                otazka = (Otazka) savedInstanceState.getSerializable(KEY_OTAZKA);
                otazky = (ArrayList<Otazka>) savedInstanceState.getSerializable(KEY_OTAZKY);
                odpovede = (ArrayList<Odpoved>) savedInstanceState.getSerializable(KEY_ODPOVEDE);
            }
        }
        idPouzivatela = pouzivatel.getId();
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(jeTablet()){
            zoznamSeriiStudent = (ZoznamSeriiStudent)
                    getFragmentManager().findFragmentById(R.id.zoznam_serii_student_Fragment);
            zoznamSeriiStudent.nastavListenerId(this,idPouzivatela,jeTablet());
            zoznamSeriiStudent.onRefresh();
            UvodnyTextFragment uvodnyTextFragment = new UvodnyTextFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutStudent, uvodnyTextFragment).commit();

        }else{
            zoznamSeriiStudent = new ZoznamSeriiStudent();
            zoznamSeriiStudent.nastavListenerId(this,idPouzivatela,jeTablet());
            getFragmentManager().beginTransaction()
                    .replace(R.id.activity_prehlad__student, zoznamSeriiStudent).commit();

        }
        if (odpovedaSa){
            zpracujOtazky(otazky);
        }


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_POUZIVATEL, pouzivatel);
        outState.putSerializable(KEY_ODPOVEDASA, odpovedaSa);
        if (odpovedaSa) {
            outState.putSerializable(KEY_SERIA, seria);
            outState.putSerializable(KEY_OTAZKA, otazka);
            outState.putSerializable(KEY_OTAZKY, otazky);
            outState.putSerializable(KEY_ODPOVEDE, odpovede);
        }
    }

    public boolean jeTablet(){
        return findViewById(R.id.activity_prehlad__student) == null;
    }


    @Override
    public void klikSeria(Seria seria) {
        if (jeInternet()){
            if (odpovede != null){odpovede.clear();}
            if (otazky != null){otazky.clear();}
            this.seria = seria;
            odpovedaSa = false;
            OtazkyNaOdpovedAsyncTask otazkyNaOdpovedAsyncTask = new OtazkyNaOdpovedAsyncTask();
            otazkyNaOdpovedAsyncTask.nastavListener(this, idPouzivatela);
            otazkyNaOdpovedAsyncTask.execute(seria);
        }
    }

    @Override
    public void pridajSeriu(String cislo) {
        if(jeInternet()){
            PridanieSerieAsyncTask pridanieSerieAsyncTask = new PridanieSerieAsyncTask();
            pridanieSerieAsyncTask.nastavListener(this, idPouzivatela);
            pridanieSerieAsyncTask.execute(cislo);
        }else{
            Toast.makeText(this, "Ziadne internetove pripojenie !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void uspesnePridanaSeria(Boolean aBoolean) {
        if (aBoolean){
            zoznamSeriiStudent.nacitajSerie();
        }else{
            Toast.makeText(this, "Chybne cislo serie !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void odpovedam(Odpoved odpoved) {
        odpovede.add(odpoved);
        getFragmentManager().popBackStackImmediate();
        Log.d("ODPOVEDAM",  odpoved.toString());
        otazky.remove(otazka);
        zpracujOtazky(otazky);
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


    public void zpracujOtazky(List<Otazka> otazky) {
        if (otazky != null) {
            this.otazky = new ArrayList<>(otazky);
            Log.d("OTAZKY", "POCET: " + this.otazky.size());
            if (otazky.isEmpty() && odpovede.isEmpty()) {
                Toast.makeText(this, "Seriu ste uz vyplnili !", Toast.LENGTH_LONG).show();
                return;
            }
            if (otazky.isEmpty() && !odpovede.isEmpty()) {
                vyhodnotSeriu();
            }
            for (Otazka otazka : otazky) {
                int pocetMoznosti = dajPocetMoznosti(otazka);
                switch (pocetMoznosti) {
                    case 0:
                        odpovedaSa = true;
                        this.otazka = otazka;
                        SpatnaVazbaFragment spatnaVazbaFragment = new SpatnaVazbaFragment();
                        spatnaVazbaFragment.nastavListener(this, otazka, idPouzivatela);
                        if (jeTablet()){
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayoutStudent,spatnaVazbaFragment).addToBackStack(null).commit();
                        }else{
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.activity_prehlad__student,spatnaVazbaFragment).addToBackStack(null).commit();
                        }
                        break;
                    case 1:
                        odpovedaSa = true;
                        this.otazka = otazka;
                        OtazkaBezMoznostiFragment otazkaBezMoznostiFragment = new OtazkaBezMoznostiFragment();
                        otazkaBezMoznostiFragment.nastavListener(this, otazka, idPouzivatela);
                        if (jeTablet()){
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayoutStudent,otazkaBezMoznostiFragment).addToBackStack(null).commit();
                        }else{
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.activity_prehlad__student,otazkaBezMoznostiFragment).addToBackStack(null).commit();
                        }
                        break;
                    case 2:
                        odpovedaSa = true;
                        this.otazka = otazka;
                        Moznost2Fragment moznost2Fragment = new Moznost2Fragment();
                        moznost2Fragment.nastavListener(this, otazka, idPouzivatela);
                        if (jeTablet()){
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayoutStudent,moznost2Fragment).addToBackStack(null).commit();
                        }else{
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.activity_prehlad__student,moznost2Fragment).addToBackStack(null).commit();
                        }
                        break;
                    case 3:
                        odpovedaSa = true;
                        this.otazka = otazka;
                        Moznost3Fragment moznost3Fragment = new Moznost3Fragment();
                        moznost3Fragment.nastavListener(this, otazka, idPouzivatela);
                        if (jeTablet()){
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayoutStudent,moznost3Fragment).addToBackStack(null).commit();
                        }else{
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.activity_prehlad__student,moznost3Fragment).addToBackStack(null).commit();
                        }
                        break;
                    case 4:
                        odpovedaSa = true;
                        this.otazka = otazka;
                        Moznost4Fragment moznost4Fragment = new Moznost4Fragment();
                        moznost4Fragment.nastavListener(this, otazka, idPouzivatela);
                        if (jeTablet()){
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayoutStudent,moznost4Fragment).addToBackStack(null).commit();
                        }else{
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.activity_prehlad__student,moznost4Fragment).addToBackStack(null).commit();
                        }
                        break;
                    case 5:
                        odpovedaSa = true;
                        this.otazka = otazka;
                        Moznost5Fragment moznost5Fragment = new Moznost5Fragment();
                        moznost5Fragment.nastavListener(this, otazka, idPouzivatela);
                        if (jeTablet()){
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayoutStudent,moznost5Fragment).addToBackStack(null).commit();
                        }else{
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.activity_prehlad__student,moznost5Fragment).addToBackStack(null).commit();
                        }
                        break;
                }
                return;
            }
        }
    }

    private void vyhodnotSeriu() {
        odpovedaSa = false;
        int pocetOdpovedi = odpovede.size();
        Log.d("ODPOVEDE", "Pocet: " + odpovede.size());
        int spravne = 0;
        for (Odpoved odpoved: odpovede){
            if (odpoved.getSpravnost().equals("ano")){spravne++;}
        }
        double vysledok = (double) spravne/pocetOdpovedi;
        vysledok = vysledok*100;
        int percento = (int) vysledok;
        UspesnostSerie uspesnostSerie = new UspesnostSerie(seria.getId(),idPouzivatela,percento);
        OdpovedajAsyncTask odpovedajAsyncTask = new OdpovedajAsyncTask();
        odpovedajAsyncTask.nastavListener(this, uspesnostSerie, odpovede);
        odpovedajAsyncTask.execute();
        Toast.makeText(this, "Spravne ste odpovedali na: " + percento + "%", Toast.LENGTH_LONG).show();
    }

    private int dajPocetMoznosti(Otazka otazka) {
        int pocet = 0;
        if (!TextUtils.isEmpty(otazka.getMoznost1())){pocet++;}
        if (!TextUtils.isEmpty(otazka.getMoznost2())){pocet++;}
        if (!TextUtils.isEmpty(otazka.getMoznost3())){pocet++;}
        if (!TextUtils.isEmpty(otazka.getMoznost4())){pocet++;}
        if (!TextUtils.isEmpty(otazka.getMoznost5())){pocet++;}
        return pocet;
    }
}
