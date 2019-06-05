package sk.upjs.vma.formativ.ActivityUcitel;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import sk.upjs.vma.formativ.OtazkyListLoader;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Seria;
import sk.upjs.vma.formativ.entity.UspesnostSerie;


public class DetailSerieUcitelFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Otazka>>, SwipeRefreshLayout.OnRefreshListener {

    private KliknutieSeriaListener listener;
    private Seria seria;
    private Context context;
    private TextView id_TextView;
    private TextView nazovSerie_TextView;
    private Switch spustenaSeriaDetail_Switch;
    private TextView pocetOdpovedi_TextView;
    private TextView pocetSpravnych_TextView;
    private Button editSeria_Button;
    private ListView zoznamOtazokDetail_ListView;
    private ArrayAdapter<Otazka> listAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int idPouzivatel;
    private List<UspesnostSerie> uspesnosti;


    public DetailSerieUcitelFragment() {
        // Required empty public constructor
    }

    public void nastavListener(KliknutieSeriaListener listener){
        this.listener = listener;
    }

    public void nastavSeriu(Seria seria, int idPouzivatel, List<UspesnostSerie> list){
        this.seria = seria;
        this.idPouzivatel = idPouzivatel;
        this.uspesnosti = list;
    }

    public void nastavDetail(Seria seria, int idPouzivatel, List<UspesnostSerie> list){
        this.seria = seria;
        this.idPouzivatel = idPouzivatel;
        this.uspesnosti = list;
        refresh();
        nastavUspesnost();
    }

    private void nastavUspesnost() {
        if (uspesnosti != null){
            int celkovyPocet = uspesnosti.size();
            int percento = 0;
            int vyslednePercento;
            for(UspesnostSerie uspech : uspesnosti){
                percento += uspech.getUspesnost();
            }
            vyslednePercento = percento/celkovyPocet;
            pocetOdpovedi_TextView.setText(Integer.toString(celkovyPocet));
            pocetSpravnych_TextView.setText(Integer.toString(vyslednePercento) + "%");
        }
    }

    private void refresh(){
        if(seria != null) {
            String idecko = Integer.toString(seria.getId());
            id_TextView.setText(idecko);
            nazovSerie_TextView.setText(seria.getNazov());
            spustenaSeriaDetail_Switch.setChecked(false);
            if (Objects.equals(seria.getSpustena(), "ano")) {
                spustenaSeriaDetail_Switch.performClick();
            }
            nacitajOtazky();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_serie_ucitel, container, false);
        context = view.getContext();

        zoznamOtazokDetail_ListView = view.findViewById(R.id.zoznamOtazokDetail_listView);
        listAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        zoznamOtazokDetail_ListView.setAdapter(listAdapter);
        zoznamOtazokDetail_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Otazka otazka = (Otazka) adapterView.getAdapter().getItem(i);
                listener.ukazOdpovede(otazka);
            }
        });

        id_TextView = (TextView) view.findViewById(R.id.ID_textView);
        nazovSerie_TextView = (TextView) view.findViewById(R.id.nazovSerie_TextView);
        pocetOdpovedi_TextView = (TextView) view.findViewById(R.id.pocetOdpovedi_textView);
        pocetSpravnych_TextView = (TextView) view.findViewById(R.id.pocetSpravnych_textView);
        editSeria_Button = (Button) view.findViewById(R.id.editSeria_button);
        spustenaSeriaDetail_Switch = (Switch) view.findViewById(R.id.spustenaDetailUcitel_Switch);
        spustenaSeriaDetail_Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spustena;
                if (spustenaSeriaDetail_Switch.isChecked()){
                    spustena = "ano";
                    seria.setSpustena(spustena);
                }else{
                    spustena = "nie";
                    seria.setSpustena(spustena);
                }
                UpdateSpustenaSeriaAsyncTask updateSpustenaSeriaAsyncTask = new UpdateSpustenaSeriaAsyncTask();
                updateSpustenaSeriaAsyncTask.execute(seria);
            }
        });

        editSeria_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
                listener.uspesnePridanaSeria(seria);
            }
        });
        refresh();
        nastavUspesnost();
        return view;
    }

    private void nacitajOtazky() {getLoaderManager().restartLoader(0, Bundle.EMPTY, this);}

    @Override
    public Loader<List<Otazka>> onCreateLoader(int id, Bundle bundle) {
        return new OtazkyListLoader(context,seria.getId(), idPouzivatel);
    }

    @Override
    public void onLoadFinished(Loader<List<Otazka>> loader, List<Otazka> otazkas) {
        listAdapter.clear();
        listAdapter.addAll(otazkas);
        Log.d("OTAZKY", Integer.toString(otazkas.size()));
    }

    @Override
    public void onLoaderReset(Loader<List<Otazka>> loader) {
        listAdapter.clear();
    }

    @Override
    public void onRefresh() {
        nacitajOtazky();
    }
}
