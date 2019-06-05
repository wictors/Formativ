package sk.upjs.vma.formativ.ActivityStudent;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;


public class SpatnaVazbaFragment extends Fragment {

    private KliknutieSeriaStudentListener listener;
    private Otazka otazka;
    private int idPouzivatel;
    private Context context;
    private TextView textOtazky;
    private EditText odp;
    private Button odpovedat;


    public void nastavListener(KliknutieSeriaStudentListener listener, Otazka otazka, int idPouzivatel) {
        this.listener = listener;
        this.otazka = otazka;
        this.idPouzivatel = idPouzivatel;
    }

    public void nastavOtazku(Otazka otazka){
        this.otazka = otazka;
        nastavPremenne();
    }


    public SpatnaVazbaFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spatna_vazba, container, false);
        context = view.getContext();

        textOtazky = (TextView) view.findViewById(R.id.znenieOtazky_spatnaVazba_textView);
        odp = (EditText) view.findViewById(R.id.odpoved_spatnaVazba_editText);

        odpovedat = (Button) view.findViewById(R.id.odpovedaj_spatnaVazba_button);
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

        odpoved = new Odpoved(otazka.getId(), idPouzivatel, odpovd, "odp" );

        listener.odpovedam(odpoved);
    }

}
