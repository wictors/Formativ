package sk.upjs.vma.formativ.Admin;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import sk.upjs.vma.formativ.ActivityUcitel.ZoznamSeriiUcitelFragment;
import sk.upjs.vma.formativ.KliknutiePouzivatelListener;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Pouzivatel;
import sk.upjs.vma.formativ.prihlasenie.LoginActivity;

public class PrehladAdminActivity extends AppCompatActivity implements KliknutiePouzivatelListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehlad_admin);

        if(jeTablet()){
            ZoznamPouzivatelovAdminFragment zoznamPouzivatelovAdminFragment =
                    (ZoznamPouzivatelovAdminFragment) getFragmentManager().findFragmentById(R.id.zoznam_pouzivatelov_admin_fragment);
            zoznamPouzivatelovAdminFragment.setListener(this);
        }else{
            ZoznamPouzivatelovAdminFragment zoznamPouzivatelovAdminFragment =
                    new ZoznamPouzivatelovAdminFragment();
            zoznamPouzivatelovAdminFragment.setListener(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.prehlad_admin_activity, zoznamPouzivatelovAdminFragment).commit();
        }
    }

    private boolean jeTablet() {
        return findViewById(R.id.prehlad_admin_activity) == null;
    }


    @Override
    public void klik(Pouzivatel pouzivatel) {

    }

    @Override
    public void registrujNoveho() {
        Intent intent = new Intent(this, RegistraciaActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_action_bar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.vyhladavanie)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.odhlasenie:
                odhlasit();
                return true;
            case R.id.vyhladavanie:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void odhlasit() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
