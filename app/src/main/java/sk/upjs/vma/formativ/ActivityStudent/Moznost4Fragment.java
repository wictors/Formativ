package sk.upjs.vma.formativ.ActivityStudent;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Arrays;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;


public class Moznost4Fragment extends Fragment {

    private KliknutieSeriaStudentListener listener;
    private Otazka otazka;
    private int idPouzivatel;
    private Context context;
    private TextView textOtazky;
    private CheckBox moznost1;
    private CheckBox moznost2;
    private CheckBox moznost3;
    private CheckBox moznost4;
    private Button odpovedat;
    private String spravneOdpovedeVStringu;
    private ArrayList<String> spravneOdpovede = new ArrayList<>();
    private ArrayList<String> odpovede  = new ArrayList<>();



    public void nastavListener(KliknutieSeriaStudentListener listener, Otazka otazka, int idPouzivatel) {
        this.listener = listener;
        this.otazka = otazka;
        this.idPouzivatel = idPouzivatel;
    }

    public void nastavOtazku(Otazka otazka){
        this.otazka = otazka;
        nastavPremenne();
    }

    public Moznost4Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moznost4, container, false);
        context = view.getContext();

        textOtazky = (TextView) view.findViewById(R.id.nazovOtazky_Student_moznost4);
        moznost1 = (CheckBox) view.findViewById(R.id.moznost1_student_moznost4_checkBox);
        moznost2 = (CheckBox) view.findViewById(R.id.moznost2_student_moznost4_checkBox);
        moznost3 = (CheckBox) view.findViewById(R.id.moznost3_student_moznost4_checkBox);
        moznost4 = (CheckBox) view.findViewById(R.id.moznost4_student_moznost4_checkBox);
        odpovedat = (Button) view.findViewById(R.id.odpovedat_moznost4_button);
        odpovedat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spracujOdpoved();
            }
        });

        if (otazka != null) {
            nastavPremenne();
        }
        return  view;
    }

    private void nastavPremenne() {
        if(otazka != null){
            textOtazky.setText(otazka.getNazov());
            moznost1.setText(spravnaOdpoved(otazka.getMoznost1()));
            moznost2.setText(spravnaOdpoved(otazka.getMoznost2()));
            moznost3.setText(spravnaOdpoved(otazka.getMoznost3()));
            moznost4.setText(spravnaOdpoved(otazka.getMoznost4()));
            spravneOdpovedeVStringu = spojOdpovede(spravneOdpovede);
        }
    }

    private CharSequence spravnaOdpoved(String moznost) {
        if (moznost.length() >= 3) {
            String koniec = moznost.substring(moznost.length()-2, moznost.length());
            if (koniec.equals("/s")) {
                spravneOdpovede.add(moznost.substring(0, moznost.length() - 2));
                return moznost.substring(0, moznost.length() - 2);
            }
        }
        return moznost;
    }

    private void spracujOdpoved() {
        if (moznost1.isChecked()){ odpovede.add(moznost1.getText().toString());}
        if (moznost2.isChecked()){ odpovede.add(moznost2.getText().toString());}
        if (moznost3.isChecked()){ odpovede.add(moznost3.getText().toString());}
        if (moznost4.isChecked()){ odpovede.add(moznost4.getText().toString());}
        String odpovd = spojOdpovede(odpovede);
        Odpoved odpoved;
        if (odpovd.equals(spravneOdpovedeVStringu)) {
            odpoved = new Odpoved(otazka.getId(), idPouzivatel, odpovd, "ano" );
        }else{
            odpoved = new Odpoved(otazka.getId(), idPouzivatel, odpovd, "nie" );
        }
        listener.odpovedam(odpoved);
    }

    private String spojOdpovede(ArrayList<String> list){
        StringBuilder sb = new StringBuilder();
        if (!list.isEmpty()) {
            for (String s : list) {
                sb.append(s);
                sb.append("/");
            }
        }
        return sb.toString();
    }


}
