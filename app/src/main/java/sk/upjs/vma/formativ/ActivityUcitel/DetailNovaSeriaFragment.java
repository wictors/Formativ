package sk.upjs.vma.formativ.ActivityUcitel;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import sk.upjs.vma.formativ.OtazkyListLoader;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Seria;


public class DetailNovaSeriaFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Otazka>> {

    private ListView zoznamOtazok_listView;
    private TextView id_TextView;
    private EditText nazovNovejSerie_EditText;
    private Switch spustenaNova_Switch;
    private Button pridajOtazku_Button;
    private Button uloz_Button;
    private Spinner typOtazky_Spinner;
    private ArrayAdapter<Otazka> listAdapter;
    private Seria seria;
    private Context context;
    private View view;
    private Dialog pridanieOtazky_dialog;
    private int pocetOtazok;
    private KliknutieSeriaListener listener;
    private int idPouzivatel;

    public DetailNovaSeriaFragment() {}

    public void nastavPremennu(Seria seria, KliknutieSeriaListener listener, int idPouzivatel){
        this.listener = listener;
        this.seria = seria;
        this.idPouzivatel = idPouzivatel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detail_nova_seria, container, false);
        context = view.getContext();

        zoznamOtazok_listView = view.findViewById(R.id.zoznamOtazok_listView);
        listAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        zoznamOtazok_listView.setAdapter(listAdapter);

        id_TextView = (TextView) view.findViewById(R.id.ID_textView);
        nazovNovejSerie_EditText = (EditText) view.findViewById(R.id.nazovNovejSerie_editText);
        spustenaNova_Switch = (Switch) view.findViewById(R.id.spustenaNova_switch);
        pridajOtazku_Button = (Button) view.findViewById(R.id.pridajOtazku_button);
        uloz_Button = (Button) view.findViewById(R.id.ulozSeriuOtazky_Button);
        typOtazky_Spinner = (Spinner) view.findViewById(R.id.typOtazky_spinner);

        if (seria != null) {
            nastavPremenne();
            nacitajOtazky();
        }

        pridajOtazku_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vytvorOtazku();
            }
        });
        uloz_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSeriu();
            }
        });

        return view;
    }



    private void updateSeriu() {
        String nazovNovejSerie = nazovNovejSerie_EditText.getText().toString();
        if (TextUtils.isEmpty(nazovNovejSerie)){
            View focusView = null;
            nazovNovejSerie_EditText.setError("Chyba nazov !");
            focusView = nazovNovejSerie_EditText;
            focusView.requestFocus();
        }else {
            seria.setSpustena("nie");
            if (spustenaNova_Switch.isChecked()) {
                seria.setSpustena("ano");
            }
        }
        seria.setNazov(nazovNovejSerie);
        listener.updateSeria(seria);
        getActivity().getFragmentManager().popBackStack();
    }


    private void vytvorOtazku() {
        String typ = typOtazky_Spinner.getSelectedItem().toString();

        if (typ.equals("Otázka s možnosťami")){
            otazkaMoznosti();
        }
        if (typ.equals("Otázka bez možností")){
            otazkaBezMoznosti();
        }
        if (typ.equals("Otázka bez hodnotenia")){
            spatnaVazba();
        }
    }

    private void nastavPremenne(){
        if (seria != null) {
            String idecko = Integer.toString(seria.getId());
            id_TextView.setText(idecko);
            nazovNovejSerie_EditText.setText(seria.getNazov());
            if (Objects.equals(seria.getSpustena(), "ano")) {
                spustenaNova_Switch.performClick();
            }
        }
    }


    public void nacitajOtazky(){getLoaderManager().restartLoader(0, Bundle.EMPTY, this);}

    @Override
    public Loader<List<Otazka>> onCreateLoader(int id, Bundle args) {
        return new OtazkyListLoader(context,seria.getId(), idPouzivatel);
    }

    @Override
    public void onLoadFinished(Loader<List<Otazka>> loader, List<Otazka> otazky) {
        listAdapter.clear();
        listAdapter.addAll(otazky);
        pocetOtazok = otazky.size();
    }

    @Override
    public void onLoaderReset(Loader<List<Otazka>> loader) {
        listAdapter.clear();
    }

    private void spatnaVazba() {
        pridanieOtazky_dialog = new Dialog(context);
        pridanieOtazky_dialog.setTitle("Spätná väzba");
        pridanieOtazky_dialog.setContentView(R.layout.vytvor_spatnavazba_dialog);

        final EditText nazovOtazky = (EditText) pridanieOtazky_dialog.findViewById(R.id.otazka_spatnavazba_editText);
        Button vytvorit = (Button) pridanieOtazky_dialog.findViewById(R.id.vytvorit_spatnavazba_button);
        vytvorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View focusView = null;
                String znenieOtazky = nazovOtazky.getText().toString();
                if (TextUtils.isEmpty(znenieOtazky)) {
                    nazovOtazky.setError("Chyba znenie otazky !");
                    focusView = nazovOtazky;
                    focusView.requestFocus();
                }else{
                    Otazka otazka = new Otazka(znenieOtazky,pocetOtazok+1,seria.getId(),null,null,null,null,null);
                    listener.pridajOtazkuDoSerie(otazka);
                    pridanieOtazky_dialog.cancel();
                }
            }
        });
        pridanieOtazky_dialog.show();
        Window window = pridanieOtazky_dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    private void otazkaBezMoznosti(){
        pridanieOtazky_dialog = new Dialog(context);
        pridanieOtazky_dialog.setTitle("Otazka bez moznosti");
        pridanieOtazky_dialog.setContentView(R.layout.vytvor_otazka_dialog);

        final EditText nazovOtazky = (EditText) pridanieOtazky_dialog.findViewById(R.id.otazka_otazka_editText);
        final EditText spravnaOdpoved = (EditText) pridanieOtazky_dialog.findViewById(R.id.spravna_otazka_editText2);

        Button vytvorit = (Button) pridanieOtazky_dialog.findViewById(R.id.vytvorit_otazka_button);
        vytvorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View focusView = null;
                String znenieOtazky = nazovOtazky.getText().toString();
                if (TextUtils.isEmpty(znenieOtazky)){
                    nazovOtazky.setError("Chyba znenie otazky !");
                    focusView = nazovOtazky;
                    focusView.requestFocus();
                }else{
                    String spravna = spravnaOdpoved.getText().toString();
                    if (TextUtils.isEmpty(spravna)){
                        spravnaOdpoved.setError("Chyba spravna odpoved !");
                        focusView = spravnaOdpoved;
                        focusView.requestFocus();
                    }else {
                        Otazka otazka = new Otazka(znenieOtazky,pocetOtazok+1,seria.getId(),spravna,null,null,null,null);
                        listener.pridajOtazkuDoSerie(otazka);
                        pridanieOtazky_dialog.cancel();
                    }
                }
            }
        });
        pridanieOtazky_dialog.show();
        Window window = pridanieOtazky_dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    private void otazkaMoznosti(){
        pridanieOtazky_dialog = new Dialog(context);
        pridanieOtazky_dialog.setTitle("Otazka s moznostami");
        pridanieOtazky_dialog.setContentView(R.layout.vytvor_otazka_moznosti_dialog);

        final EditText nazovOtazky = (EditText) pridanieOtazky_dialog.findViewById(R.id.nazovOtazka_editText);
        final EditText moznost1 = (EditText) pridanieOtazky_dialog.findViewById(R.id.moznost1_editText);
        final EditText moznost2 = (EditText) pridanieOtazky_dialog.findViewById(R.id.moznost2_editText);
        final EditText moznost3 = (EditText) pridanieOtazky_dialog.findViewById(R.id.moznost3_editText);
        final EditText moznost4 = (EditText) pridanieOtazky_dialog.findViewById(R.id.moznost4_editText);
        final EditText moznost5 = (EditText) pridanieOtazky_dialog.findViewById(R.id.moznost5_editText);

        final CheckBox spravna1 = (CheckBox) pridanieOtazky_dialog.findViewById(R.id.spravna1_checkBox);
        final CheckBox spravna2 = (CheckBox) pridanieOtazky_dialog.findViewById(R.id.spravna2_checkBox);
        final CheckBox spravna3 = (CheckBox) pridanieOtazky_dialog.findViewById(R.id.spravna3_checkBox);
        final CheckBox spravna4 = (CheckBox) pridanieOtazky_dialog.findViewById(R.id.spravna4_checkBox);
        final CheckBox spravna5 = (CheckBox) pridanieOtazky_dialog.findViewById(R.id.spravna5_checkBox);

        Button ulozOtazku = (Button) pridanieOtazky_dialog.findViewById(R.id.vytvorOtazku_Button);

        ulozOtazku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean jeZadanaSpravna = false;
                boolean jeMoznost3 = false;
                boolean jeMoznost4 = false;
                boolean chyba = false;
                String znenieOtazky = nazovOtazky.getText().toString();
                if(TextUtils.isEmpty(znenieOtazky)){
                    View focusView = null;
                    nazovOtazky.setError("Chyba znenie otazky !");
                    focusView = nazovOtazky;
                    focusView.requestFocus();
                }else{
                    View focusView = null;
                    String znenieMoznost1 = moznost1.getText().toString();
                    if (!TextUtils.isEmpty(znenieMoznost1)){
                        if (spravna1.isChecked()) {
                            jeZadanaSpravna = true;
                            znenieMoznost1 += "/s";
                        }
                    }else {
                        moznost1.setError("Chyba moznost !");
                        focusView = moznost1;
                        chyba = true;
                    }

                    String znenieMoznost2 = moznost2.getText().toString();
                    if (!TextUtils.isEmpty(znenieMoznost2)){
                        if (spravna2.isChecked()) {
                            jeZadanaSpravna = true;
                            znenieMoznost2 += "/s";
                        }
                    }else {
                        moznost2.setError("Chyba moznost !");
                        focusView = moznost2;
                        chyba = true;
                    }

                    String znenieMoznost3 = moznost3.getText().toString();
                    if (!TextUtils.isEmpty(znenieMoznost3)){
                        jeMoznost3 = true;
                        if (spravna3.isChecked()) {
                            jeZadanaSpravna = true;
                            znenieMoznost3 += "/s";
                        }
                    }

                    String znenieMoznost4 = moznost4.getText().toString();
                    if (!TextUtils.isEmpty(znenieMoznost4)){
                        if (jeMoznost3) {
                            jeMoznost4 = true;
                            if (spravna4.isChecked()) {
                                jeZadanaSpravna = true;
                                znenieMoznost4 += "/s";
                            }
                        }else{
                            moznost3.setError("Chyba moznost !");
                            focusView = moznost3;
                            chyba = true;
                        }
                    }

                    String znenieMoznost5 = moznost5.getText().toString();
                    if (!TextUtils.isEmpty(znenieMoznost5)){
                        if (jeMoznost4) {
                            if (spravna5.isChecked()) {
                                jeZadanaSpravna = true;
                                znenieMoznost5 += "/s";
                            }
                        }else{
                            moznost4.setError("Chyba moznost !");
                            focusView = moznost4;
                            chyba = true;
                        }
                    }
                    if (chyba){
                        focusView.requestFocus();
                    }else{
                        if (!jeZadanaSpravna){
                            nazovOtazky.setError("Chyba spravna odpoved !");
                            focusView = nazovOtazky;
                            focusView.requestFocus();
                        }else{
                            Otazka otazka = new Otazka(znenieOtazky,pocetOtazok+1,seria.getId(),znenieMoznost1,znenieMoznost2,
                                    znenieMoznost3,znenieMoznost4,znenieMoznost5);
                            listener.pridajOtazkuDoSerie(otazka);
                            pridanieOtazky_dialog.cancel();
                        }
                    }

                }
            }
        });
        pridanieOtazky_dialog.show();
        Window window = pridanieOtazky_dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }
}
