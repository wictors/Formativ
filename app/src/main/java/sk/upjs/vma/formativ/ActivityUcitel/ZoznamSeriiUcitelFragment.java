package sk.upjs.vma.formativ.ActivityUcitel;


import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.SerieListLoader;
import sk.upjs.vma.formativ.entity.Seria;

import static sk.upjs.vma.formativ.R.id.ranklabel;


public class ZoznamSeriiUcitelFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Seria>>, SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private ArrayAdapter<Seria> listAdapter;
    private int idPouzivatela;
    private KliknutieSeriaListener listener;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog pridanieSerieDialog;
    private boolean jeTablet;
    private boolean uprava;
    private Seria seria;
    private boolean prvykrat;
    private ArrayList<Seria> zoznamOznac = new ArrayList<>();


    public ZoznamSeriiUcitelFragment() {
        // Required empty public constructor
    }

    public void nastavListenerId (KliknutieSeriaListener listener, int id, Boolean jeTablet,
                                  Boolean uprava, Seria seria){
        this.listener = listener;
        this.idPouzivatela = id;
        this.jeTablet = jeTablet;
        this.prvykrat = true;
        this.uprava = uprava;
        this.seria = seria;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoznam_serii_ucitel, container, false);
        context = view.getContext();

        listView = view.findViewById(R.id.zoznam_serii_list);
        listAdapter = new ArrayAdapter<Seria>(context,R.layout.listseria_item,R.id.ranklabel);
        listView.setAdapter(listAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                actionMode.setTitle("Vybrané: " + checkedCount);
                if (b) {
                    zoznamOznac.add(listAdapter.getItem(i));
                }else{
                    zoznamOznac.remove(listAdapter.getItem(i));
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.multiple_delete_menu, menu);
                zoznamOznac.clear();
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        listener.vymazSerie(zoznamOznac);
                        actionMode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Seria seria = (Seria) adapterView.getAdapter().getItem(i);
                listener.klikSeria(seria);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.zoznam_serii_ucitel_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaSeria();
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.zoznam_serii_ucitel_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        nacitajSerie();

        return view;
    }

    private void novaSeria() {
        pridanieSerieDialog = new Dialog(context);
        pridanieSerieDialog.setTitle("Nová Séria");
        pridanieSerieDialog.setContentView(R.layout.nova_seria_dialog);
        final Switch spustenaSwitch = (Switch) pridanieSerieDialog.findViewById(R.id.nova_seria_dialog_switch);
        final EditText nazovSeriePole = (EditText) pridanieSerieDialog.findViewById(R.id.nova_seria_dialog_edittext);
        Button pridaj = (Button) pridanieSerieDialog.findViewById(R.id.nova_seria_dialog_button_pridaj);
        Button zrus = (Button) pridanieSerieDialog.findViewById(R.id.nova_seria_dialog_button_zrus);

        zrus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pridanieSerieDialog.cancel();
            }
        });

        pridaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazovNovejSerie = nazovSeriePole.getText().toString();
                if (TextUtils.isEmpty(nazovNovejSerie)){
                    View focusView = null;
                    nazovSeriePole.setError("Chyba nazov !");
                    focusView = nazovSeriePole;
                    focusView.requestFocus();
                }else{
                    String spustena = "nie";
                    if (spustenaSwitch.isChecked()){
                        spustena = "ano";
                    }
                    Seria novaSeriaUloh = new Seria(nazovNovejSerie, spustena);
                    listener.novaSeria(novaSeriaUloh);
                    pridanieSerieDialog.cancel();
                }
            }
        });
        pridanieSerieDialog.show();
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
        if (!uprava) {
            if (prvykrat) {
                if (seria == null) {
                    if (jeTablet && !listAdapter.isEmpty()) {
                        listener.klikSeria(listAdapter.getItem(0));
                    }
                } else {
                    prvykrat = false;
                    prepni();
                    return;
                }
                prvykrat = false;
            }
        }
    }

    private void prepni() {
        if (jeTablet) {
            listener.klikSeria(seria);
        }
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

    public void refresh(boolean uprava){
        onRefresh();
        this.uprava = uprava;
    }
}
