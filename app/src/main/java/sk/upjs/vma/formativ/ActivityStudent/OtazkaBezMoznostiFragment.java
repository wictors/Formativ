package sk.upjs.vma.formativ.ActivityStudent;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;


public class OtazkaBezMoznostiFragment extends Fragment {

    private KliknutieSeriaStudentListener listener;
    private Otazka otazka;
    private int idPouzivatel;
    private Context context;
    private TextView textOtazky;
    private EditText odp;
    private Button odpovedat;
    private String spravneOdpovedeVStringu;


    public void nastavListener(KliknutieSeriaStudentListener listener, Otazka otazka, int idPouzivatel) {
        this.listener = listener;
        this.otazka = otazka;
        this.idPouzivatel = idPouzivatel;
    }

    public void nastavOtazku(Otazka otazka){
        this.otazka = otazka;
        nastavPremenne();
    }


    public OtazkaBezMoznostiFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otazka_bez_moznosti, container, false);
        context = view.getContext();

        textOtazky = (TextView) view.findViewById(R.id.znenieOtazky_bezMozn_textView);
        odp = (EditText) view.findViewById(R.id.odpoved_bezMozn_editText);

        odpovedat = (Button) view.findViewById(R.id.odpovedaj_bezMozn_button);
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
            spravneOdpovedeVStringu = otazka.getMoznost1();
        }
    }

    private void spracujOdpoved() {
        String odpovd;
        if (odp.getText() != null){
            odpovd = odp.getText().toString();
        }else{
            odpovd = "";
        }
        Odpoved odpoved;
        if (odpovd.equals(spravneOdpovedeVStringu)) {
            odpoved = new Odpoved(otazka.getId(), idPouzivatel, odpovd, "ano" );
        }else{
            odpoved = new Odpoved(otazka.getId(), idPouzivatel, odpovd, "nie" );
        }
        listener.odpovedam(odpoved);
    }

}
