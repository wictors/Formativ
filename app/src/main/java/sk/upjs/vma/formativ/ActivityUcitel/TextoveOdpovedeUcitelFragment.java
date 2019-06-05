package sk.upjs.vma.formativ.ActivityUcitel;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;


public class TextoveOdpovedeUcitelFragment extends Fragment {

    private ArrayList<Odpoved> odpovede;
    private Otazka otazka;
    private View view;
    private Context context;
    private ListView listView;
    private TextView znenieOtazky;
    private ArrayAdapter<Odpoved> arrayAdapter;

    public void nastavOdpovede(ArrayList<Odpoved> odpovede, Otazka otazka){
        this.otazka = otazka;
        this.odpovede = odpovede;
    }

    public TextoveOdpovedeUcitelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_textove_odpovede_ucitel, container, false);
        context = view.getContext();

        znenieOtazky = view.findViewById(R.id.otazkaTextoveOdpovede_TextView);
        if (otazka != null){znenieOtazky.setText(otazka.getNazov());}

        listView = view.findViewById(R.id.zoznam_text_odpovedi_ucitel);
        arrayAdapter = new ArrayAdapter<Odpoved>(context, android.R.layout.simple_list_item_1);
        arrayAdapter.clear();
        if (odpovede != null){arrayAdapter.addAll(odpovede);}
        listView.setAdapter(arrayAdapter);

        return view;
    }

}
