package sk.upjs.vma.formativ.ActivityUcitel;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Otazka;

public class OdpovedeMoznostiFragment extends Fragment {

    private Otazka otazka;
    private String[] moznosti;
    private int[] pocetnost;
    private int[] spravne;
    private View view;
    private Context context;
    private TextView znenieOtazky;
    private TextView moznost1;
    private TextView moznost2;
    private TextView moznost3;
    private TextView moznost4;
    private TextView moznost5;



    public OdpovedeMoznostiFragment() {
        // Required empty public constructor
    }

    public void nastavOdpovede(Otazka otazka, String[] moznosti, int[] pocetnost, int[] spravne){
        this.otazka = otazka;
        this.moznosti = moznosti;
        this.pocetnost = pocetnost;
        this.spravne = spravne;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_odpovede_moznosti, container, false);
        context = view.getContext();

        znenieOtazky = view.findViewById(R.id.pocenostZnenieOtazky_textView);
        if (otazka != null){znenieOtazky.setText(otazka.getNazov());}

        moznost1 = view.findViewById(R.id.pocetnost1Moznost_textView);
        moznost2 = view.findViewById(R.id.pocetnost2Moznost_textView);
        moznost3 = view.findViewById(R.id.pocetnost3Moznost_textView);
        moznost4 = view.findViewById(R.id.pocetnost4Moznost_textView);
        moznost5 = view.findViewById(R.id.pocetnost5Moznost_textView);
        if (moznosti != null) {
            oznacSpravne();
            for (int i = 0; i < 5; i++) {
                switch (i) {
                    case 0: {
                        moznost1.setText(poskladajText(i));
                    }
                    case 1: {
                        moznost2.setText(poskladajText(i));
                    }
                    case 2: {
                        if (!moznosti[2].equals("")) {
                            moznost3.setText(poskladajText(i));
                        } else {
                            moznost3.setText("");
                        }
                    }
                    case 3: {
                        if (!moznosti[3].equals("")) {
                            moznost4.setText(poskladajText(i));
                        } else {
                            moznost4.setText("");
                        }
                    }
                    case 4: {
                        if (!moznosti[4].equals("")) {
                            moznost5.setText(poskladajText(i));
                        } else {
                            moznost5.setText("");
                        }
                    }
                }
            }
        }

        return view;
    }

    private void oznacSpravne() {
        for (int i=0; i<5; i++){
            if(spravne[i] == 1){moznost1.setBackgroundColor(Color.GREEN);}
            if(spravne[i] == 2){moznost2.setBackgroundColor(Color.GREEN);}
            if(spravne[i] == 3){moznost3.setBackgroundColor(Color.GREEN);}
            if(spravne[i] == 4){moznost4.setBackgroundColor(Color.GREEN);}
            if(spravne[i] == 5){moznost5.setBackgroundColor(Color.GREEN);}
        }
    }

    private String poskladajText(int i){
        StringBuilder sb = new StringBuilder();
        sb.append(moznosti[i]);
        sb.append(": ");
        sb.append(pocetnost[i]);
        sb.append(" krát");
        return sb.toString();
    }

}
