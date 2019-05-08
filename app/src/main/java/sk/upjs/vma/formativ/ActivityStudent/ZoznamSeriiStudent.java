package sk.upjs.vma.formativ.ActivityStudent;


import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.SerieListLoader;
import sk.upjs.vma.formativ.entity.Seria;


public class ZoznamSeriiStudent extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Seria>>, SwipeRefreshLayout.OnRefreshListener  {

    private KliknutieSeriaStudentListener listener;
    private int idPouzivatela;
    private boolean jeTablet;
    private View view;
    private Context context;
    private ArrayAdapter<Seria> listAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private Dialog pridanieSerieDialog;
    private FloatingActionButton pridajSeriu;

    public ZoznamSeriiStudent() {    }

    public void nastavListenerId (KliknutieSeriaStudentListener listener, int id, Boolean jeTablet){
        this.listener = listener;
        this.idPouzivatela = id;
        this.jeTablet = jeTablet;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_zoznam_serii_student, container, false);
        context = view.getContext();

        listView = view.findViewById(R.id.zoznam_serii_student_list);
        listAdapter = new ArrayAdapter<Seria>(context, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Seria seria = (Seria) adapterView.getAdapter().getItem(i);
                listener.klikSeria(seria);
            }
        });
        if (jeTablet && !listAdapter.isEmpty()){
            listener.klikSeria(listAdapter.getItem(0));
        }

        pridajSeriu = (FloatingActionButton) view.findViewById(R.id.zoznam_serii_student_floating_button);
        pridajSeriu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pridajSeriu();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.zoznam_serii_student_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        nacitajSerie();

        return view;
    }

    public void nacitajSerie() {
        getLoaderManager().restartLoader(0, Bundle.EMPTY, this);
    }

    private void pridajSeriu(){
        pridanieSerieDialog = new Dialog(context);
        pridanieSerieDialog.setTitle("Pridaj Sériu !");
        pridanieSerieDialog.setContentView(R.layout.pridaj_seria_student_dialog);
        final EditText cisloSerie = (EditText) pridanieSerieDialog.findViewById(R.id.cisloSerieStudent_editText);
        Button pridajButton = (Button) pridanieSerieDialog.findViewById(R.id.pridajSeriuStudent_button);

        pridajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cislo = cisloSerie.getText().toString();
                if (TextUtils.isEmpty(cislo)){
                    View focusView = null;
                    cisloSerie.setError("Chyba nazov !");
                    focusView = cisloSerie;
                    focusView.requestFocus();
                }else{
                    listener.pridajSeriu(cislo);
                    pridanieSerieDialog.cancel();
                }
            }
        });
        pridanieSerieDialog.show();
    }

    @Override
    public Loader<List<Seria>> onCreateLoader(int i, Bundle bundle) {
        return new SerieListLoader(context,idPouzivatela);
    }

    @Override
    public void onLoadFinished(Loader<List<Seria>> loader, List<Seria> serias) {
        listAdapter.clear();
        listAdapter.addAll(serias);
    }

    @Override
    public void onLoaderReset(Loader<List<Seria>> loader) {
        listAdapter.clear();
    }

    @Override
    public void onRefresh() {
        nacitajSerie();
        swipeRefreshLayout.setRefreshing(false);
    }
}
