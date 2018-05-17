package sk.upjs.vma.formativ.Admin;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import sk.upjs.vma.formativ.KliknutiePouzivatelListener;
import sk.upjs.vma.formativ.PouzivatelListLoader;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Pouzivatel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZoznamPouzivatelovAdminFragment extends Fragment 
        implements LoaderManager.LoaderCallbacks<List<Pouzivatel>>, SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private ArrayAdapter<Pouzivatel> listAdapter;
    private KliknutiePouzivatelListener listener;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;


    public ZoznamPouzivatelovAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoznam_pouzivatelov_admin, container, false);
        context = view.getContext();

        listView = view.findViewById(R.id.zoznam_pouzivatelov_admin_list);
        listAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pouzivatel pouzivatel = (Pouzivatel) adapterView.getAdapter().getItem(i);
                listener.klik(pouzivatel);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.zoznam_pouzivatelov_admin_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novyPouzivatel();
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.zoznam_pouzivatelov_admin_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        nacitajPouzivatelov();
        
        
        return view;
    }

    private void nacitajPouzivatelov() {
        getLoaderManager().restartLoader(0, Bundle.EMPTY, this);
    }

    private void novyPouzivatel() {
        listener.registrujNoveho();
    }

    public void setListener(PrehladAdminActivity prehladAdminActivity) {
        this.listener = prehladAdminActivity;
    }

    @Override
    public Loader<List<Pouzivatel>> onCreateLoader(int i, Bundle bundle) {
        return new PouzivatelListLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<List<Pouzivatel>> loader, List<Pouzivatel> pouzivatels) {
        listAdapter.clear();
        listAdapter.addAll(pouzivatels);
    }

    @Override
    public void onLoaderReset(Loader<List<Pouzivatel>> loader) {
        listAdapter.clear();
    }

    @Override
    public void onRefresh() {
        nacitajPouzivatelov();
        swipeRefreshLayout.setRefreshing(false);
    }
}
