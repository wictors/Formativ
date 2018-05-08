package sk.upjs.vma.formativ.ActivityUcitel;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import sk.upjs.vma.formativ.KliknutieSeriaListener;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.SerieListLoader;
import sk.upjs.vma.formativ.entity.Seria;


public class ZoznamSeriiUcitelFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Seria>> {

    private ListView listView;
    private ArrayAdapter<Seria> listAdapter;
    private int idPouzivatela;
    private KliknutieSeriaListener listener;
    private Context context;


    public ZoznamSeriiUcitelFragment() {
        // Required empty public constructor
    }

    public void nastavListenerId (KliknutieSeriaListener listener, int id){
        this.listener = listener;
        idPouzivatela = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoznam_serii_ucitel, container, false);
        context = view.getContext();

        listView = view.findViewById(R.id.zoznam_serii_list);
        listAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Seria seria = (Seria) adapterView.getAdapter().getItem(i);
                listener.klikSeria(seria);
            }
        });

        nacitajSerie();

        return view;
    }

    private void nacitajSerie() {
        getLoaderManager().restartLoader(0, Bundle.EMPTY, this);
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
}
