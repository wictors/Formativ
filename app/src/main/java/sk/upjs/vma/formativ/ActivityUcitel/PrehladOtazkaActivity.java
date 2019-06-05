package sk.upjs.vma.formativ.ActivityUcitel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;

public class PrehladOtazkaActivity extends AppCompatActivity implements PrehladOdpovediListener {

    private Otazka otazka;
    private String KEY_OTAZKA = "Otazka";
    private String KEY_ODPOVEDE = "Odpovede";
    private ArrayList<Odpoved> odpovede = new ArrayList<>();
    private ProgressBar progressBar;
    private int pocetMoznosti = 0;
    private int[] pocetMoznost = new int[5];
    private String[] moznosti = new String[5];
    private int[] spravne = new int[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehlad_otazka);
        setTitle("Odpovede");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pocetMoznosti = 0;

        progressBar = findViewById(R.id.prehlad_Otazka_progressBar);

        if(savedInstanceState == null) {
            otazka = (Otazka) getIntent().getSerializableExtra("Otazka");
        }else {
            otazka = (Otazka) savedInstanceState.getSerializable(KEY_OTAZKA);
            odpovede = (ArrayList<Odpoved>) savedInstanceState.getSerializable(KEY_ODPOVEDE);
        }
        dajPocetMoznosti();

        if(savedInstanceState == null || odpovede == null) {
            ZoznamOdpovediAsyncTask zoznamOdpovediAsyncTask = new ZoznamOdpovediAsyncTask();
            zoznamOdpovediAsyncTask.nastavListener(this);
            zoznamOdpovediAsyncTask.execute(otazka);
        }else{
            spracujOdpovede(odpovede);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_OTAZKA, otazka);
        outState.putSerializable(KEY_ODPOVEDE, odpovede);
    }

    private void dajPocetMoznosti() {
        if (!TextUtils.isEmpty(otazka.getMoznost1())){pocetMoznosti++;}
        if (!TextUtils.isEmpty(otazka.getMoznost2())){pocetMoznosti++;}
        if (!TextUtils.isEmpty(otazka.getMoznost3())){pocetMoznosti++;}
        if (!TextUtils.isEmpty(otazka.getMoznost4())){pocetMoznosti++;}
        if (!TextUtils.isEmpty(otazka.getMoznost5())){pocetMoznosti++;}
    }

    @Override
    public void spracujOdpovede(List<Odpoved> odpoveds) {
        odpovede = (ArrayList<Odpoved>) odpoveds;
        spravne = new int[5];
        if (pocetMoznosti < 2){
            zobrazOdpovede();
        }else{
            int pocet = pocetMoznosti;
            while (pocet > 0){
                switch (pocet){
                    case 1:{
                        moznosti[0] = zkontrolujMoznosti(otazka.getMoznost1(),1);
                    }
                    case 2:{
                        moznosti[1] = zkontrolujMoznosti(otazka.getMoznost2(), 2);
                    }
                    case 3:{
                        moznosti[2] = zkontrolujMoznosti(otazka.getMoznost3(),3);
                    }
                    case 4:{
                        moznosti[3] = zkontrolujMoznosti(otazka.getMoznost4(),4);
                    }
                    case 5:{
                        moznosti[4] = zkontrolujMoznosti(otazka.getMoznost5(),5);
                    }
                }
                pocet--;
            }
            spocitajOdpovede();
        }
    }

    private void spocitajOdpovede() {
        String odpovedVStringu;
        String subString;
        int zaciatok;
        int koniec;
        if(odpovede != null) {
            for (Odpoved odpoved : odpovede) {
                odpovedVStringu = odpoved.getOdpoved();
                zaciatok = 0;
                for (int i = 0; i < odpovedVStringu.length(); i++) {
                    if (odpovedVStringu.charAt(i) == '/') {
                        koniec = i;
                        if (koniec > zaciatok) {
                            subString = odpovedVStringu.substring(zaciatok, koniec);
                            pripocitaj(subString);
                            zaciatok = koniec + 1;
                        }
                    }
                }
            }
        }
        zobrazVysledky();
    }



    private void pripocitaj(String subString) {
        for (int i = 0; i<pocetMoznosti; i++){
            if (subString.equals(moznosti[i])){
                pocetMoznost[i]++;
            }
        }
    }

    private void zobrazVysledky() {
        Log.d("Spravne", Arrays.toString(spravne));
        OdpovedeMoznostiFragment odpovedeMoznostiFragment = new OdpovedeMoznostiFragment();
        odpovedeMoznostiFragment.nastavOdpovede(otazka, moznosti, pocetMoznost,spravne);
        progressBar.setVisibility(View.GONE);
        getFragmentManager().beginTransaction().replace(R.id.prehlad_otazka_activity, odpovedeMoznostiFragment).commit();
    }

    private void zobrazOdpovede() {
        TextoveOdpovedeUcitelFragment textoveOdpovedeUcitelFragment = new TextoveOdpovedeUcitelFragment();
        if (odpovede == null){odpovede = new ArrayList<>();}
        textoveOdpovedeUcitelFragment.nastavOdpovede(odpovede,otazka);
        progressBar.setVisibility(View.GONE);
        getFragmentManager().beginTransaction().replace(R.id.prehlad_otazka_activity, textoveOdpovedeUcitelFragment).commit();
    }

    private String zkontrolujMoznosti(String moznost, int i) {
        if (moznost.length() >= 3) {
            String koniec = moznost.substring(moznost.length()-2, moznost.length());
            if (koniec.equals("/s")) {
                spravne[i-1] = i;
                return moznost.substring(0, moznost.length() - 2);
            }
        }
        return moznost;
    }
}
